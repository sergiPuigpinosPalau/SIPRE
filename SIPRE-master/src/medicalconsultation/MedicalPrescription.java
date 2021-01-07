package medicalconsultation;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import exceptions.IncorrectTakingGuidelinesException;
import exceptions.ProductAlreadyAdded;
import exceptions.ProductNotInPrescription;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Package for the classes involved in the use case Suply next dispensing
 */

public class MedicalPrescription {// A class that represents medical prescription

    private int prescCode;
    private Date prescDate;
    private Date endDate;
    private HealthCardID hcID; // the healthcard ID of the patient
    private DigitalSignature eSign; // the eSignature of the doctor
    private final List<MedicalPrescriptionLine> prescriptionLines;
    private static final int NUM_OF_INSTRUCTIONS = 6;    //In case they add more instructions
                                                         //Constant because if someone edits it, they'll need to change some code to implement these new instructions

    public MedicalPrescription(HealthCardID hcID) {
        if (hcID==null)
            throw new IllegalArgumentException();
        this.hcID = hcID;               //No need to do any check because HealthCardId already does it
        this.prescriptionLines = new LinkedList<>();
    }

    public void addLine(ProductID prodID, String[] instruc) throws IncorrectTakingGuidelinesException, ProductAlreadyAdded {
        if (prodID==null || instruc == null )
            throw new IllegalArgumentException();

        if (instruc.length < NUM_OF_INSTRUCTIONS)
            throw new IncorrectTakingGuidelinesException("Missing Information");
        //Check if prescription already has this product
        try{
            getPrescriptionLineFromProdID(prodID);
        }catch (ProductNotInPrescription ex){
            prescriptionLines.add(new MedicalPrescriptionLine(prodID, instruc));
            return;
        }
        throw new ProductAlreadyAdded();
    }

    public void modifyLine(ProductID prodID, String[] instruc) throws ProductNotInPrescription, IncorrectTakingGuidelinesException {
        if (prodID==null && instruc == null)
            throw new IllegalArgumentException();
        if (instruc.length < NUM_OF_INSTRUCTIONS)
            throw new IncorrectTakingGuidelinesException("Missing Information");
        getPrescriptionLineFromProdID(prodID).modifyPrescriptionLine(instruc);
    }

    public void removeLine(ProductID prodID) throws ProductNotInPrescription {
        prescriptionLines.remove(getPrescriptionLineFromProdID(prodID));
    }

    private MedicalPrescriptionLine getPrescriptionLineFromProdID(ProductID prodID) throws ProductNotInPrescription {
        for (MedicalPrescriptionLine prescriptionLine:prescriptionLines) {
            if (prescriptionLine.getProductID().equals(prodID))
                return prescriptionLine;
        }
        throw new ProductNotInPrescription();
    }

    public int getPrescCode() {
        return prescCode;
    }

    public void setPrescCode(int prescCode) {
        this.prescCode = prescCode;
    }

    public Date getPrescDate() {
        return prescDate;
    }

    public void setPrescDate(Date prescDate) {
        if (prescDate==null)
            throw new IllegalArgumentException();
        this.prescDate = prescDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        if (endDate==null)
            throw new IllegalArgumentException();
        this.endDate = endDate;
    }

    public HealthCardID getHcID() {
        return hcID;
    }

    public void setHcID(HealthCardID hcID) {
        if (hcID==null)
            throw new IllegalArgumentException();
        this.hcID = hcID;
    }

    public DigitalSignature geteSign() {
        return eSign;
    }

    public void seteSign(DigitalSignature eSign) {
        if (eSign==null)
            throw new IllegalArgumentException();
        this.eSign = eSign;
    }

    public List<MedicalPrescriptionLine> getPrescriptionLines() {
        return prescriptionLines;
    }

    public static int getNumOfInstructions() {
        return NUM_OF_INSTRUCTIONS;
    }
}
