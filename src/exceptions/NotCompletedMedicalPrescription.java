package exceptions;

public class NotCompletedMedicalPrescription extends Exception{
    public NotCompletedMedicalPrescription(){
        super("Medical Prescription is incomplete");
    }
}
