package exceptions;

public class ProductAlreadyAdded extends Exception{
    public ProductAlreadyAdded(){
        super("Product already added in the prescription");
    }
}
