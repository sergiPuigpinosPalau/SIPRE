package exceptions;

public class AnySelectedMedicineException extends Exception{
    public AnySelectedMedicineException(){
        super("Product not yet selected from the list");
    }
}
