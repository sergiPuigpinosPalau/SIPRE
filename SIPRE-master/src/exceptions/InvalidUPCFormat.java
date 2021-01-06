package exceptions;

public class InvalidUPCFormat extends Exception{
    public InvalidUPCFormat(){
        super("Invalid UPC format");
    }
}
