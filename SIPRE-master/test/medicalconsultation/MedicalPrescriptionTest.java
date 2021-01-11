package medicalconsultation;

import data.HealthCardID;
import data.ProductID;
import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MedicalPrescriptionTest {

    ProductID producto1,producto2 ;
    String[] instrucio1,instrucio2, instrucioModificat;
    MedicalPrescription prescription;
    List<MedicalPrescriptionLine> respuestaLines;

    @BeforeEach
    void setUp() throws InvalidUPCFormat, InvalidCIPFormat {

        prescription = new MedicalPrescription(new HealthCardID("BBBBBBBBAR444851805874780033"));

        producto1 = new ProductID("123456789123");
        instrucio1 = new String[]{"AFTERDINNER", "12", "Tomar con agua.", "13", "23", "WEEK"};

        //productoModificat = new ProductID("123456789123");
        instrucioModificat = new String[]{"AFTERDINNER", "12", "Cadena", "15", "23", "HOUR"};

        producto2 = new ProductID("123456789125");
        instrucio2 = new String[]{"AFTERDINNER", "12", "Tomar con agua.", "13", "23", "WEEK"};

    }


    @Test
    void addLine() throws IncorrectTakingGuidelinesException, ProductAlreadyAdded{

        prescription.addLine(producto1, instrucio1);

        respuestaLines=prescription.getPrescriptionLines();


        assertEquals(producto1.getUPC(),respuestaLines.get(0).getProductID().getUPC());
        assertEquals(12,respuestaLines.get(0).getGuideline().getDuration());
        assertEquals("Tomar con agua.",respuestaLines.get(0).getGuideline().getInstructions());
        assertEquals(DayMoment.AFTERDINNER,respuestaLines.get(0).getGuideline().getDayMoment());
        assertNotEquals(FqUnit.DAY,respuestaLines.get(0).getGuideline().getPosology().getFreqUnit());
        assertEquals(13,respuestaLines.get(0).getGuideline().getPosology().getDose());
        assertEquals(23,respuestaLines.get(0).getGuideline().getPosology().getFreq());

        assertEquals(1,prescription.getPrescriptionLines().size());

        assertThrows(IllegalArgumentException.class, () -> {
            prescription.addLine(new ProductID(null),instrucioModificat);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            prescription.addLine(producto1,null);
        });

        assertThrows(IncorrectTakingGuidelinesException.class, () -> {
            prescription.addLine(producto1,new String[]{"AFTERDINNER", "12", "Tomar con agua.", "13", "23"});
        });

    }

    @Test
    void modifyLine() throws IncorrectTakingGuidelinesException, ProductNotInPrescription, ProductAlreadyAdded {
        prescription.addLine(producto1, instrucio1);
        prescription.modifyLine(producto1,instrucioModificat);

        respuestaLines=prescription.getPrescriptionLines();
        assertNotEquals("Tomar con agua.",respuestaLines.get(0).getGuideline().getInstructions());
        assertEquals("Cadena",respuestaLines.get(0).getGuideline().getInstructions());
        assertNotEquals(FqUnit.DAY,respuestaLines.get(0).getGuideline().getPosology().getFreqUnit());
        assertEquals(FqUnit.HOUR,respuestaLines.get(0).getGuideline().getPosology().getFreqUnit());
        assertNotEquals(13,respuestaLines.get(0).getGuideline().getPosology().getDose());
        assertEquals(15,respuestaLines.get(0).getGuideline().getPosology().getDose());
        assertEquals(23,respuestaLines.get(0).getGuideline().getPosology().getFreq());


        assertEquals(1,prescription.getPrescriptionLines().size());

        // coprobar con un id que no existe.
        assertThrows(ProductNotInPrescription.class, () -> {
            prescription.modifyLine(new ProductID("123456789144"),instrucioModificat);
        });

        assertThrows(IncorrectTakingGuidelinesException.class, () -> {
            prescription.modifyLine(producto1,new String[]{ "14", "Tomar con agua.", "13", "23"});
        });




    }

    @Test
    void removeLine() throws IncorrectTakingGuidelinesException, ProductAlreadyAdded, ProductNotInPrescription {

        prescription.addLine(producto1, instrucio1);
        prescription.addLine(producto2, instrucio2);
        respuestaLines=prescription.getPrescriptionLines();

        // coprobar con un id que no existe.
        assertThrows(ProductNotInPrescription.class, () -> {
            prescription.removeLine(new ProductID("123456789144"));
        });

        assertEquals(2,prescription.getPrescriptionLines().size());

        prescription.removeLine(producto2);

        assertEquals(1,prescription.getPrescriptionLines().size());
        prescription.removeLine(producto1);

        assertThrows(ProductNotInPrescription.class, () -> {
            prescription.removeLine(producto2);
        });

        assertEquals(0,prescription.getPrescriptionLines().size());



    }

    
}