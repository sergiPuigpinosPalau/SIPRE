package medicalconsultation;

import data.ProductID;
import exceptions.IncorrectTakingGuidelinesException;

public class MedicalPrescriptionLine {
    private TakingGuideline guideline;
    private ProductID productID;

    public MedicalPrescriptionLine(ProductID prodID, String[] instruc) throws IncorrectTakingGuidelinesException{
        if (prodID==null || instruc==null)
            throw new IllegalArgumentException();
        this.productID = prodID;
        //Check that is given all values
        for (String inst: instruc) {
            if (inst.isEmpty())
                throw new IncorrectTakingGuidelinesException("Invalid instruction's format");
        }
        //Check values and create instance
        try {
            FqUnit freqUnit = FqUnit.valueOf(instruc[5]);
            DayMoment dayMoment = DayMoment.valueOf(instruc[0]);
            this.guideline = new TakingGuideline(dayMoment, Float.parseFloat(instruc[1]), instruc[2], Float.parseFloat(instruc[3]), Float.parseFloat(instruc[4]), freqUnit);
        } catch (IllegalArgumentException ex){
            throw new IncorrectTakingGuidelinesException("Invalid instruction's format");
        }
    }

    public void modifyPrescriptionLine(String[] instruc) throws IncorrectTakingGuidelinesException{
        guideline.modifyGuideline(instruc);
    }

    public TakingGuideline getGuideline() {
        return guideline;
    }

    public void setGuideline(TakingGuideline guideline) {
        if (guideline==null)
            throw new IllegalArgumentException();
        this.guideline = guideline;
    }

    public ProductID getProductID() {
        return productID;
    }

    public void setProductID(ProductID productID) {
        if (productID==null)
            throw new IllegalArgumentException();
        this.productID = productID;
    }
}
