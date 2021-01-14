package medicalconsultation;

import data.ProductID;
import exceptions.IncorrectTakingGuidelinesException;
import exceptions.InvalidUPCFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MedicalPrescriptionLineTest {

    MedicalPrescriptionLine p, p2 ;
    ProductID producto, productoNuevo;
    String[] instrucio;

    ProductID productoCheck;
    TakingGuideline pautaCheck ,pautaNueva;


    @BeforeEach
    void setUp() throws InvalidUPCFormat, IncorrectTakingGuidelinesException {
        producto = new ProductID("123456789123");
        instrucio = new String[]{"AFTERDINNER", "12", "Tomar con agua.", "13", "23", "HOUR"};
        p = new MedicalPrescriptionLine(producto,instrucio);
        productoCheck = new ProductID("123456789123");
        pautaCheck = new TakingGuideline(DayMoment.valueOf(instrucio[0]),Float.parseFloat(instrucio[1]),instrucio[2],Float.parseFloat(instrucio[3]),Float.parseFloat(instrucio[4]),FqUnit.valueOf(instrucio[5]));
        pautaNueva = new TakingGuideline(DayMoment.valueOf("AFTERBREAKFAST"),Float.parseFloat(instrucio[1]),"instucio",Float.parseFloat(instrucio[3]),Float.parseFloat(instrucio[4]),FqUnit.valueOf(instrucio[5]));
    }

    @Test
    @DisplayName("Check that the prescription line gets created without any errors")
    void createMedicalPrescriptionLine() {
        //Check nulls
        assertThrows(IllegalArgumentException.class, () -> {
            p2 = new MedicalPrescriptionLine(null,new String[]{"Hola", "12", "Tomar con agua.", "13", "23", "HOUR"});
        });
        assertThrows(IllegalArgumentException.class, () -> {
            p2 = new MedicalPrescriptionLine(producto,null);
        });
        //Case where user enters invalid guidelines
        assertThrows(IncorrectTakingGuidelinesException.class, () -> {
            p2 = new MedicalPrescriptionLine(producto,new String[]{"Hola", "12", "Tomar con agua.", "13", "23", "HOUR"});
        });
        assertThrows(IncorrectTakingGuidelinesException.class, () -> {
            p2 = new MedicalPrescriptionLine(producto,new String[]{"AFTERDINNER", "12", "Tomar con agua.", "t", "23", "HOUR"});
        });
        assertThrows(IncorrectTakingGuidelinesException.class, () -> {
            p2 = new MedicalPrescriptionLine(producto,new String[]{"", "12", "", "t", "23", "HOUR"});
        });
        assertThrows(IncorrectTakingGuidelinesException.class, () -> {
            p2 = new MedicalPrescriptionLine(producto,new String[]{"DINN", "ggg", "Tomar con agua.", "t", "23", "NO"});
        });
        assertThrows(IncorrectTakingGuidelinesException.class, () -> {
            p2 = new MedicalPrescriptionLine(producto,new String[]{"t", "23", "NO"});
        });
    }

    @Test
    @DisplayName("Check information stored matches")
    void getMedicalPrescriptionLine() {
        assertEquals(productoCheck.getUPC(),p.getProductID().getUPC());
        assertEquals(pautaCheck.getDayMoment(),p.getGuideline().getDayMoment());
        assertEquals(pautaCheck.getDuration(),p.getGuideline().getDuration());
        assertEquals(pautaCheck.getInstructions(),p.getGuideline().getInstructions());
        assertEquals(pautaCheck.getPosology().getDose(),p.getGuideline().getPosology().getDose());
        assertEquals(pautaCheck.getPosology().getFreq(),p.getGuideline().getPosology().getFreq());
        assertEquals(pautaCheck.getPosology().getFreqUnit(),p.getGuideline().getPosology().getFreqUnit());
    }

    @Test
    @DisplayName("Check setters")
    void setMedicalPrescriptionLine() throws InvalidUPCFormat {
        productoNuevo = new ProductID("123456789133");
        p.setProductID(productoNuevo);
        assertEquals(productoNuevo.getUPC(),p.getProductID().getUPC());
        assertEquals(pautaCheck.getDayMoment(),p.getGuideline().getDayMoment());
        assertEquals(pautaCheck.getDuration(),p.getGuideline().getDuration());
        assertEquals(pautaCheck.getInstructions(),p.getGuideline().getInstructions());
        assertEquals(pautaCheck.getPosology().getDose(),p.getGuideline().getPosology().getDose());
        assertEquals(pautaCheck.getPosology().getFreq(),p.getGuideline().getPosology().getFreq());
        assertEquals(pautaCheck.getPosology().getFreqUnit(),p.getGuideline().getPosology().getFreqUnit());
        p.setGuideline(pautaNueva);
        assertEquals(pautaNueva.getDayMoment(), p.getGuideline().getDayMoment());
        assertEquals(pautaNueva.getDuration(), p.getGuideline().getDuration());
        assertEquals(pautaNueva.getInstructions(), p.getGuideline().getInstructions());
        assertEquals(pautaNueva.getPosology().getFreqUnit(), p.getGuideline().getPosology().getFreqUnit());
        assertEquals(pautaNueva.getPosology().getFreq(), p.getGuideline().getPosology().getFreq());
        assertEquals(pautaNueva.getPosology().getDose(), p.getGuideline().getPosology().getDose());
        assertNotEquals(pautaCheck.getDayMoment(),p.getGuideline().getDayMoment());
        assertNotEquals(pautaCheck.getInstructions(),p.getGuideline().getInstructions());
        assertEquals(productoNuevo.getUPC(),p.getProductID().getUPC());
        assertNotEquals(producto.getUPC(),p.getProductID().getUPC());
    }

    @Test
    @DisplayName("Check modify function")
    void modifyMedicalPrescriptionLine() throws IncorrectTakingGuidelinesException {
        instrucio = new String[]{"AFTERBREAKFAST", "12.5f", "Tomar con agua.", "24", "24", "HOUR"};
        p.modifyPrescriptionLine(instrucio);
        assertEquals(DayMoment.valueOf(instrucio[0]),p.getGuideline().getDayMoment());
        assertEquals(Float.valueOf(instrucio[1]),p.getGuideline().getDuration());
        assertEquals(instrucio[2],p.getGuideline().getInstructions());
        assertEquals(Float.valueOf(instrucio[3]),p.getGuideline().getPosology().getDose());
        assertEquals(Float.valueOf(instrucio[4]),p.getGuideline().getPosology().getFreq());
        assertEquals(FqUnit.valueOf(instrucio[5]),p.getGuideline().getPosology().getFreqUnit());
        //Case where user enters invalid guidelines
        assertThrows(IncorrectTakingGuidelinesException.class, () -> {
            p.modifyPrescriptionLine(new String[]{"Hola", "12", "Tomar con agua.", "13", "23", "HOUR"});
        });
        assertThrows(IncorrectTakingGuidelinesException.class, () -> {
            p.modifyPrescriptionLine(new String[]{"AFTERDINNER", "12", "Tomar con agua.", "t", "23", "HOUR"});
        });
        assertThrows(IncorrectTakingGuidelinesException.class, () -> {
            p.modifyPrescriptionLine(new String[]{"DINN", "ggg", "Tomar con agua.", "t", "23", "NO"});
        });
        assertThrows(IncorrectTakingGuidelinesException.class, () -> {
            p.modifyPrescriptionLine(new String[]{"t", "23", "NO"});
        });
    }

    @Test
    @DisplayName("Check modify function with empty arguments")
    void modifyMedicalPrescriptionLineWithEmptyArguments() throws IncorrectTakingGuidelinesException {
        //Case where user doesn't want to change
        instrucio = new String[]{"AFTERDINNER", "12", "Tomar con agua.", "13", "23", "HOUR"};
        p.modifyPrescriptionLine(new String[]{"", "", "", "", "", ""});
        assertEquals(DayMoment.valueOf(instrucio[0]),p.getGuideline().getDayMoment());
        assertEquals(Float.valueOf(instrucio[1]),p.getGuideline().getDuration());
        assertEquals(instrucio[2],p.getGuideline().getInstructions());
        assertEquals(Float.valueOf(instrucio[3]),p.getGuideline().getPosology().getDose());
        assertEquals(Float.valueOf(instrucio[4]),p.getGuideline().getPosology().getFreq());
        assertEquals(FqUnit.valueOf(instrucio[5]),p.getGuideline().getPosology().getFreqUnit());
    }

    @Test
    void testSetters() {
        assertThrows(IllegalArgumentException.class, () -> {
            p.setGuideline(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            p.setProductID(null);
        });
    }

}