package exceptions;

public class IncorrectEndingDateException extends Exception{
    public IncorrectEndingDateException(){
        super("Invalid Date provided");
    }
}
