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

        pautaCheck = new TakingGuideline(DayMoment.valueOf(instrucio[0]),Float.valueOf(instrucio[1]),instrucio[2],Float.valueOf(instrucio[3]),Float.valueOf(instrucio[4]),FqUnit.valueOf(instrucio[5]));
        pautaNueva = new TakingGuideline(DayMoment.valueOf("AFTERBREAKFAST"),Float.valueOf(instrucio[1]),"instucio",Float.valueOf(instrucio[3]),Float.valueOf(instrucio[4]),FqUnit.valueOf(instrucio[5]));


    }

    @Test
    @DisplayName("Povar que el objete no es crea null i es crei corectamen sese cap  Exception.")
    void createMedicalPrescriptionLine() throws IncorrectTakingGuidelinesException {

        p2 = new MedicalPrescriptionLine(producto,instrucio);
        assertNotNull(p2);


        assertThrows(IllegalArgumentException.class, () -> {
            p2 = new MedicalPrescriptionLine(producto,null);
        });

        assertThrows(IncorrectTakingGuidelinesException.class, () -> {
            p2 = new MedicalPrescriptionLine(producto,new String[]{"Hola", "12", "Tomar con agua.", "13", "23", "HOUR"});
        });

        assertThrows(IncorrectTakingGuidelinesException.class, () -> {
            p2 = new MedicalPrescriptionLine(producto,new String[]{"AFTERDINNER", "12", "Tomar con agua.", "t", "23", "HOUR"});
        });


    }

    @Test
    @DisplayName("Povar que es pot acedir al contigut corectamen.")
    void getMedicalPrescriptionLine() throws InvalidUPCFormat {


        assertEquals(productoCheck.getUPC(),p.getProductID().getUPC());

        assertEquals(pautaCheck.getDayMoment(),p.getGuideline().getDayMoment());
        assertEquals(pautaCheck.getDuration(),p.getGuideline().getDuration());
        assertEquals(pautaCheck.getInstructions(),p.getGuideline().getInstructions());

        assertEquals(pautaCheck.getPosology().getDose(),p.getGuideline().getPosology().getDose());
        assertEquals(pautaCheck.getPosology().getFreq(),p.getGuideline().getPosology().getFreq());
        assertEquals(pautaCheck.getPosology().getFreqUnit(),p.getGuideline().getPosology().getFreqUnit());
    }

    @Test
    @DisplayName("Povar la funcio set().")
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
    @DisplayName("Povar que es pot modifica un MedicalPrescriptionLine amb la funcio moifiy corectamen.")
    void modifyMedicalPrescriptionLine() throws IncorrectTakingGuidelinesException {

        instrucio = new String[]{"AFTERBREAKFAST", "12.5f", "Tomar con agua.", "24", "24", "HOUR"};
        Posology posologiaOK = new Posology(24,24, FqUnit.HOUR);
        TakingGuideline pautaOK  = new TakingGuideline(DayMoment.AFTERBREAKFAST,12.5f,"Toma el medicamento exactamente como te lo hayan recetado.",24,24,FqUnit.DAY);


        p.modifyPrescriptionLine(instrucio);

        assertEquals(DayMoment.valueOf(instrucio[0]),p.getGuideline().getDayMoment());
        assertEquals(Float.valueOf(instrucio[1]),p.getGuideline().getDuration());
        assertEquals(instrucio[2],p.getGuideline().getInstructions());

        assertEquals(Float.valueOf(instrucio[3]),p.getGuideline().getPosology().getDose());
        assertEquals(Float.valueOf(instrucio[4]),p.getGuideline().getPosology().getFreq());
        assertEquals(FqUnit.valueOf(instrucio[5]),p.getGuideline().getPosology().getFreqUnit());

    }


}