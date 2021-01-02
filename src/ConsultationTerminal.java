import data.DigitalSignature;
import data.HealthCardID;
import exceptions.*;
import medicalconsultation.MedicalPrescription;
import medicalconsultation.ProductSpecification;
import services.HealthNationalService;
import services.ScheduledVisitAgenda;

import java.net.ConnectException;
import java.util.Date;
import java.util.List;


public class ConsultationTerminal {
    //Implements "Crear línea de prescripción" use case
    //Class responsible of managing input events (controlador fachada)

    private MedicalPrescription currentePrescription;
    private DigitalSignature doctorSignature;
    private List<ProductSpecification> listOfProducts;
    private ProductSpecification selectedProduct;
    private HealthNationalService HNS; //SNS
    private ScheduledVisitAgenda SVA;

    public ConsultationTerminal(DigitalSignature doctorSignature, HealthNationalService HNS, ScheduledVisitAgenda SVA) {
        this.doctorSignature = doctorSignature;
        this.HNS = HNS;//TODO preguntar
        this.SVA = SVA;
    }

    public void initRevision() throws HealthCardException, NotValidePrescriptionException, ConnectException {
        HealthCardID currentPatientHealthCardID = SVA.getHealthCardID();
        this.currentePrescription = HNS.getePrescription(currentPatientHealthCardID);
    }

    public void initPrescriptionEdition() throws AnyCurrentPrescriptionException, NotFinishedTreatmentException {
        Date currentDate = new java.util.Date();
        if (currentePrescription.getEndDate().after(currentDate))
            throw new NotFinishedTreatmentException();
        if (currentePrescription == null)
            throw new AnyCurrentPrescriptionException();
    }

    public void searchForProducts(String keyWord) throws AnyKeyWordMedicineException, ConnectException {
        listOfProducts = HNS.getProductsByKW(keyWord);
    }

    public void selectProduct(int option) throws AnyMedicineSearchException, ConnectException {
        selectedProduct = HNS.getProductSpecific(option);
    }

    public void enterMedicineGuidelines(String[] instruc) throws AnySelectedMedicineException, IncorrectTakingGuidelinesException, ProductAlreadyAdded {
        if (selectedProduct == null || !listOfProducts.contains(selectedProduct))
            throw new AnySelectedMedicineException();
        currentePrescription.addLine(selectedProduct.getUPCcode(), instruc);
    }

    public void enterTreatmentEndingDate(Date date) throws IncorrectEndingDateException {
        Date currentDate = new java.util.Date();
        Date futureDate = Date.from(currentDate.toInstant().plusSeconds(1576800000));
        if (date.before(currentDate) || date.after(futureDate))
            throw new IncorrectEndingDateException();
        currentePrescription.setEndDate(date);
        currentePrescription.setPrescDate(currentDate);
    }

    public void sendePrescription() throws ConnectException, NotValidePrescription, eSignatureException, NotCompletedMedicalPrescription {
        currentePrescription.seteSign(doctorSignature);
        HNS.sendePrescription(currentePrescription);
    }

    public void printePresc() throws printingException { }

    public HealthNationalService getHNS() {
        return HNS;
    }

    public void setHNS(HealthNationalService HNS) {
        this.HNS = HNS;
    }

    public ScheduledVisitAgenda getSVA() {
        return SVA;
    }

    public void setSVA(ScheduledVisitAgenda SVA) {
        this.SVA = SVA;
    }

    public DigitalSignature getDoctorSignature() {
        return doctorSignature;
    }

    public void setDoctorSignature(DigitalSignature doctorSignature) {
        this.doctorSignature = doctorSignature;
    }

    // Other methods, apart from the input events
}

