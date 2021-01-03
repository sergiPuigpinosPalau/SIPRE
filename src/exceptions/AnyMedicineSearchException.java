package exceptions;

public class AnyMedicineSearchException extends Exception{
    public AnyMedicineSearchException(){
        super("No product search has been done");
    }
}
