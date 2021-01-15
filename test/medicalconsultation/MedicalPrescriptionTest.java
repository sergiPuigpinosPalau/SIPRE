package medicalconsultation;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MedicalPrescriptionTest {

    ProductID producto1, producto2;
    String[] instrucio1, instrucio2, instrucioModificat;
    MedicalPrescription prescription;
    List<MedicalPrescriptionLine> respuestaLines;

    @BeforeEach
    void setUp() throws InvalidUPCFormat, InvalidCIPFormat {
        prescription = new MedicalPrescription(new HealthCardID("BBBBBBBBAR444851805874780033"));
        producto1 = new ProductID("123456789123");
        instrucio1 = new String[]{"AFTERDINNER", "12", "Tomar con agua.", "13", "23", "WEEK"};
        instrucioModificat = new String[]{"AFTERDINNER", "12", "Cadena", "15", "23", "HOUR"};
        producto2 = new ProductID("123456789125");
        instrucio2 = new String[]{"AFTERDINNER", "12", "Tomar con agua.", "13", "23", "WEEK"};
    }

    @Test
    @DisplayName("Test addLine works as intended")
    void addLine() throws IncorrectTakingGuidelinesException, ProductAlreadyAdded {
        //Check instructions are added correctly to the prescription line
        prescription.addLine(producto1, instrucio1);
        respuestaLines = prescription.getPrescriptionLines();
        assertEquals(producto1.getUPC(), respuestaLines.get(0).getProductID().getUPC());
        assertEquals(12, respuestaLines.get(0).getGuideline().getDuration());
        assertEquals("Tomar con agua.", respuestaLines.get(0).getGuideline().getInstructions());
        assertEquals(DayMoment.AFTERDINNER, respuestaLines.get(0).getGuideline().getDayMoment());
        assertNotEquals(FqUnit.DAY, respuestaLines.get(0).getGuideline().getPosology().getFreqUnit());
        assertEquals(13, respuestaLines.get(0).getGuideline().getPosology().getDose());
        assertEquals(23, respuestaLines.get(0).getGuideline().getPosology().getFreq());
        //Check that the amount of prescription lines matches
        assertEquals(1, prescription.getPrescriptionLines().size());
        //Case where provided productID is null
        assertThrows(IncorrectTakingGuidelinesException.class, () -> prescription.addLine(null, instrucioModificat));
        //Case where instructions provided are null
        assertThrows(IncorrectTakingGuidelinesException.class, () -> prescription.addLine(producto1, null));
        //Case where there's missing information
        assertThrows(IncorrectTakingGuidelinesException.class, () -> prescription.addLine(producto1, new String[]{"AFTERDINNER", "12", "Tomar con agua.", "13", "23"}));
        //Case where you add a product which was already added
        assertThrows(ProductAlreadyAdded.class, () -> prescription.addLine(producto1, instrucio1));
    }

    @Test
    @DisplayName("Test modifyLine works as intended")
    void modifyLine() throws IncorrectTakingGuidelinesException, ProductNotInPrescription, ProductAlreadyAdded {
        //Check that prescription lines have been modified
        prescription.addLine(producto1, instrucio1);
        prescription.modifyLine(producto1, instrucioModificat);
        respuestaLines = prescription.getPrescriptionLines();
        assertNotEquals("Tomar con agua.", respuestaLines.get(0).getGuideline().getInstructions());
        assertEquals("Cadena", respuestaLines.get(0).getGuideline().getInstructions());
        assertNotEquals(FqUnit.DAY, respuestaLines.get(0).getGuideline().getPosology().getFreqUnit());
        assertEquals(FqUnit.HOUR, respuestaLines.get(0).getGuideline().getPosology().getFreqUnit());
        assertNotEquals(13, respuestaLines.get(0).getGuideline().getPosology().getDose());
        assertEquals(15, respuestaLines.get(0).getGuideline().getPosology().getDose());
        assertEquals(23, respuestaLines.get(0).getGuideline().getPosology().getFreq());
        //Check that the amount of prescription lines matches
        assertEquals(1, prescription.getPrescriptionLines().size());
        //Case where provided productID is null
        assertThrows(IncorrectTakingGuidelinesException.class, () -> prescription.modifyLine(null, instrucioModificat));
        //Case where instructions provided are null
        assertThrows(IncorrectTakingGuidelinesException.class, () -> prescription.modifyLine(producto1, null));
        //Case where you try to modify a line with a productID that doesn't exist
        assertThrows(ProductNotInPrescription.class, () -> prescription.modifyLine(new ProductID("123456789144"), instrucioModificat));
        //Case where there's missing information
        assertThrows(IncorrectTakingGuidelinesException.class, () -> prescription.modifyLine(producto1, new String[]{"14", "Tomar con agua.", "13", "23"}));
    }

    @Test
    @DisplayName("Test removeLine works as intended")
    void removeLine() throws IncorrectTakingGuidelinesException, ProductAlreadyAdded, ProductNotInPrescription {
        prescription.addLine(producto1, instrucio1);
        prescription.addLine(producto2, instrucio2);
        respuestaLines = prescription.getPrescriptionLines();
        //Case where you try to remove a line which doesn't exist
        assertThrows(ProductNotInPrescription.class, () -> prescription.removeLine(new ProductID("123456789144")));
        //Case where instructions provided are null
        assertThrows(ProductNotInPrescription.class, () -> prescription.removeLine(null));
        //Check that it removes the line
        prescription.removeLine(producto2);
        assertEquals(1, prescription.getPrescriptionLines().size());
        prescription.removeLine(producto1);
        //Case where you try to remove a line which has already been removed
        assertThrows(ProductNotInPrescription.class, () -> prescription.removeLine(producto2));
        //Check that the amount of prescription lines matches
        assertEquals(0, prescription.getPrescriptionLines().size());
    }

    @Test
    @DisplayName("Check null setters and others")
    void addCostant() throws InvalidCIPFormat {
        prescription = new MedicalPrescription(new HealthCardID("BBBBBBBBAR444851805874780037"));
        //Check setters and getters
        prescription.setEndDate(new Date(-604075999750L));
        assertEquals("Fri Nov 10 10:06:40 CET 1950", prescription.getEndDate().toString());
        byte[] signatura = "Any String you want".getBytes();
        prescription.seteSign(new DigitalSignature(signatura));
        assertEquals(signatura, prescription.geteSign().getSignature());
        //Check nulls
        assertThrows(IllegalArgumentException.class, () -> prescription.setEndDate(null));
        assertThrows(IllegalArgumentException.class, () -> prescription.setPrescDate(null));
        assertThrows(IllegalArgumentException.class, () -> prescription.seteSign(null));
        assertThrows(IllegalArgumentException.class, () -> prescription.setHcID(null));
    }

    @Test
    @DisplayName("Check equal")
    public void equalsTest() throws InvalidUPCFormat, InvalidCIPFormat {
        MedicalPrescription mp2 = new MedicalPrescription(new HealthCardID("BBBBBBBBAR444851805874780033"));
        assertEquals(prescription, mp2);
        mp2.setHcID(new HealthCardID("BBBBBBBBAR444851805874785588"));
        assertNotEquals(prescription, mp2);
    }
}