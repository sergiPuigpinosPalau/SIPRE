package exceptions;

public class HealthCardException extends Exception{
    public HealthCardException(){
        super("HNS couldn't find the patient's id");
    }
}
