package medicalconsultation;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import exceptions.IncorrectTakingGuidelinesException;
import exceptions.ProductNotInPrescription;

import java.util.Date;
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
    private List<MedicalPrescriptionLine> prescriptionLines;

    //TODO ??? // Its components, that is, the set of medical prescription lines

    public MedicalPrescription(int prescCode, Date prescDate, Date endDate, HealthCardID hcID, DigitalSignature eSign) {
        this.prescCode = prescCode;
        this.prescDate = prescDate;
        this.endDate = endDate;
        this.hcID = hcID;
        this.eSign = eSign;
    }

    public void addLine(ProductID prodID, String[] instruc) throws IncorrectTakingGuidelinesException {
        if (instruc.length < 6)
            throw new IncorrectTakingGuidelinesException("Missing Information");
        prescriptionLines.add(new MedicalPrescriptionLine(prodID, instruc));
    }

    public void modifyLine(ProductID prodID, String[] instruc) throws ProductNotInPrescription, IncorrectTakingGuidelinesException {
        if (instruc.length < 6)
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

    //TODO ??? // the getters and setters
}
