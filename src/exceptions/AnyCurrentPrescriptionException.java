package exceptions;

public class AnyCurrentPrescriptionException extends Exception{
    public AnyCurrentPrescriptionException(){
        super("No prescription in course");
    }
}
