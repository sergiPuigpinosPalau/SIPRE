package exceptions;

public class NotValidePrescriptionException extends Exception{
    public NotValidePrescriptionException(){
        super("Couldn't find any ePrescription associated to this patient");
    }
}
