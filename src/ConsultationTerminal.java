import data.HealthCardID;
import data.ProductID;
import exceptions.*;
import medicalconsultation.MedicalPrescription;
import medicalconsultation.ProductSpecification;
import services.HealthNationalService;

import java.net.ConnectException;
import java.util.Date;
import java.util.List;


public class ConsultationTerminal {
    //Implements "Crear línea de prescripción" use case
    //Class responsible of managing input events (controlador fachada)

    MedicalPrescription currentePrescription;
    List<ProductSpecification> listOfProducts;
    ProductSpecification selectedProduct;
    HealthNationalService HNS; //SNS


    public void initRevision() throws HealthCardException, NotValidePrescriptionException, ConnectException {
        HealthCardID currentPatientHealthCardID = getHealthCardID(); //TODO implementar component ScheduledVisitAgenda que conte HealthCardID getHealthCardID() throws HealthCardException;
        this.currentePrescription = HNS.getePrescription(currentPatientHealthCardID);
    }

    public void initPrescriptionEdition(ProductID pID) throws AnyCurrentPrescriptionException, NotFinishedTreatmentException {
        //TODO??
    }

    public void searchForProducts(String keyWord) throws AnyKeyWordMedicineException, ConnectException {
        listOfProducts = HNS.getProductsByKW(keyWord);
    }

    public void selectProduct(int option) throws AnyMedicineSearchException, ConnectException {
        selectedProduct = HNS.getProductSpecific(option);
    }

    public void enterMedicineGuidelines(String[] instruc) throws AnySelectedMedicineException, IncorrectTakingGuidelinesException {
        if (selectedProduct == null || !listOfProducts.contains(selectedProduct))
            throw new AnySelectedMedicineException();
        currentePrescription.addLine(selectedProduct.getUPCcode(), instruc);
    }

    public void enterTreatmentEndingDate(Date date) throws IncorrectEndingDateException {
        Date currentDate = new java.util.Date();
        if (date.before(currentDate))                   //TODO fecha proporcionada sea incorrecta???
            throw new IncorrectEndingDateException();
        currentePrescription.setEndDate(date);
        currentePrescription.setPrescDate(currentDate);
    }

    public void sendePrescription() throws ConnectException, NotValidePrescription, eSignatureException, NotCompletedMedicalPrescription {
        currentePrescription.seteSign();//TODO ??
        HNS.sendePrescription(currentePrescription);
        //TODO que fer amb la prescripcio actualitzada?
    }

    public void printePresc() throws printingException {
        //TODO llenca un printing exc o un not impl excep o borrar
    }

    public HealthNationalService getHNS() {
        return HNS;
    }

    public void setHNS(HealthNationalService HNS) {
        this.HNS = HNS;
    }

    // Other methods, apart from the input events
}

