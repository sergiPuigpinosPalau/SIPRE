package medicalconsultation;

import data.ProductID;
import exceptions.IncorrectTakingGuidelinesException;

import java.util.Objects;

public class MedicalPrescriptionLine {
    private TakingGuideline guideline;
    private ProductID productID;

    public MedicalPrescriptionLine(ProductID prodID, String[] instruc) throws IncorrectTakingGuidelinesException{
        if (prodID==null || instruc==null)
            throw new IllegalArgumentException();
        this.productID = prodID;
        checkInstructionsLength(instruc);
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
        checkInstructionsLength(instruc);
        guideline.modifyGuideline(instruc);
    }

    private void checkInstructionsLength(String[] instruc) throws IncorrectTakingGuidelinesException{
        if (instruc.length < MedicalPrescription.getNumOfInstructions())
            throw new IncorrectTakingGuidelinesException("Missing instructions");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalPrescriptionLine that = (MedicalPrescriptionLine) o;
        return Objects.equals(guideline, that.guideline) && Objects.equals(productID, that.productID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guideline, productID);
    }
}
