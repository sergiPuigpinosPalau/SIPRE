package exceptions;

public class InvalidPriceFormat extends Exception{
    public InvalidPriceFormat(){
        super("Invalid Price format or value");
    }
}
