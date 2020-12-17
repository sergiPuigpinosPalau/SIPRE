import java.util.Date;

public class ConsultationTerminal {
    ???

    public void initRevision() {

    } throws HealthCardException, NotValidePrescriptionException, ConnectException;

    public void initPrescriptionEdition() {

    } throws AnyCurrentPrescriptionException, NotFinishedTreatmentException;

    public void searchForProducts(String keyWord) {

    } throws AnyKeyWordMedicineException, ConnectException;

    public void selectProduct(int option) {

    } throws AnyMedicineSearchException, ConnectException;

    public void enterMedicineGuidelines(String[] instruc) {

    } throws AnySelectedMedicineException, IncorrectTakingGuidelinesException;

    public void enterTreatmentEndingDate(Date date) {

    } throws IncorrectEndingDateException;

    public void sendePrescription() {

    } throws ConnectException, NotValidePrescription, eSignatureException, NotCompletedMedicalPrescription;

    public void printePresc() {

    } throws printingException;

    ??? // Other methods, apart from the input events
}

