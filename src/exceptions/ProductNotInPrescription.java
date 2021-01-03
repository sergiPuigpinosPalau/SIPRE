package exceptions;

public class ProductNotInPrescription extends Exception{
    public ProductNotInPrescription(){
        super("Product not found in Medical Prescription");
    }
}
