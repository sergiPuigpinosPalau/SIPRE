package exceptions;

public class AnyKeyWordMedicineException extends Exception{
    public AnyKeyWordMedicineException(){
        super("No products found with the provided keyword");
    }
}
