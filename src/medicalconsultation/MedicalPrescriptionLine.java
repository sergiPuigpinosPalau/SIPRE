package medicalconsultation;

import data.ProductID;
import exceptions.IncorrectTakingGuidelinesException;

public class MedicalPrescriptionLine {
    private TakingGuideline guideline;
    private ProductID productID;

    public MedicalPrescriptionLine(ProductID prodID, String[] instruc) throws IncorrectTakingGuidelinesException{
        this.productID = prodID;
        //Check that is given all values
        for (String inst: instruc) {
            if (inst.isEmpty())
                throw new IncorrectTakingGuidelinesException("Invalid instruction's format");
        }

        try {
            FqUnit freqUnit = FqUnit.valueOf(instruc[5]);
            this.guideline = new TakingGuideline(Float.parseFloat(instruc[0]), Float.parseFloat(instruc[1]), instruc[2], Float.parseFloat(instruc[3]), Float.parseFloat(instruc[4]), freqUnit);
        } catch (IllegalArgumentException ex){
            throw new IncorrectTakingGuidelinesException("Invalid instruction's format");
        }
        //TODO TEST these can throw IncorrectTakingGuidelinesException
    }

    public void modifyPrescriptionLine(String[] instruc) throws IncorrectTakingGuidelinesException{   //TODO IncorrectTakingGuidelinesException TEST
        guideline.modifyGuideline(instruc);
    }

    public TakingGuideline getGuideline() {
        return guideline;
    }

    public void setGuideline(TakingGuideline guideline) {
        this.guideline = guideline;
    }

    public ProductID getProductID() {
        return productID;
    }

    public void setProductID(ProductID productID) {
        this.productID = productID;
    }
}
