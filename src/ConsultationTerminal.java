import data.HealthCardID;
import medicalconsultation.MedicalPrescription;
import medicalconsultation.ProductSpecification;
import services.HealthNationalService;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ConsultationTerminal {
    //Implements "Crear línea de prescripción" use case
    //Class responsible of managing input events (controlador fachada)

    HealthCardID currentPatientHealthCardID; //TODO local o global
    MedicalPrescription currentePrescription;
    HealthNationalService HNS; //SNS //TODO li passa el constructor suposo?


    public void initRevision() throws HealthCardException, NotValidePrescriptionException, ConnectException {
        this.currentPatientHealthCardID = getHealthCardID(); //TODO implementar component ScheduledVisitAgenda que conte HealthCardID getHealthCardID() throws HealthCardException;
        this.currentePrescription = HNS.getePrescription(currentPatientHealthCardID);
    }

    public void initPrescriptionEdition() throws AnyCurrentPrescriptionException, NotFinishedTreatmentException {
        Scanner scanner = new Scanner(System.in);
        while (??){
            System.out.print("Enter the keyword for the desired product: ");
            searchForProducts(scanner.next());
        }
    }

    public void searchForProducts(String keyWord) throws AnyKeyWordMedicineException, ConnectException {
        List<ProductSpecification> listOfProducts = HNS.getProductsByKW(keyWord);
        //TODO que fer amb el que te retorna?
    }

    public void selectProduct(int option) throws AnyMedicineSearchException, ConnectException {
        HNS.getProductSpecific(option);
    }

    public void enterMedicineGuidelines(String[] instruc) throws AnySelectedMedicineException, IncorrectTakingGuidelinesException {

    }

    public void enterTreatmentEndingDate(Date date) throws IncorrectEndingDateException {

    }

    public void sendePrescription() throws ConnectException, NotValidePrescription, eSignatureException, NotCompletedMedicalPrescription {

    }

    public void printePresc() throws printingException {

    }

    ??? // Other methods, apart from the input events
}

