package exceptions;

public class NotValidePrescription extends Exception{
    public NotValidePrescription(){
        super("HNS couldn't validate the ePrescription");
    }
}
