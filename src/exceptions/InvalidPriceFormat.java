package exceptions;

public class InvalidPriceFormat extends Exception{
    public InvalidPriceFormat(String invalid){
        super("Invalid Price format or value");
    }
}
