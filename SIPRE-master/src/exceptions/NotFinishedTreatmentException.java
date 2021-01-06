package exceptions;

public class NotFinishedTreatmentException extends Exception{
    public NotFinishedTreatmentException(){
        super("Treatment hasn't finished yet");
    }
}
