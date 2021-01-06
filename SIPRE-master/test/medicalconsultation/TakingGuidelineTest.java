package medicalconsultation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TakingGuidelineTest {
    TakingGuideline pauta;
    Posology posologia , posologiaFalse, posologiaSet;
    @BeforeEach
    void setUp() {
        pauta = new TakingGuideline(DayMoment.AFTERBREAKFAST,12.5f,"Toma el medicamento exactamente como te lo hayan recetado.",24,24,FqUnit.DAY);
        posologia = new Posology(24,24,FqUnit.DAY);
        posologiaFalse = new Posology(14,14,FqUnit.MONTH);
        posologiaSet = new Posology(10,10,FqUnit.HOUR);
    }

    @Test
    void createTakingGuideline() {
        TakingGuideline CreaPauta = new TakingGuideline(DayMoment.AFTERDINNER,24, "Tomar con agua.",12,12.2f, FqUnit.DAY);
        assertNotNull(CreaPauta);

        assertThrows(IllegalArgumentException.class, () -> {
            TakingGuideline pautaAmbNulls = new TakingGuideline(null,24,null,23, 21,null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            TakingGuideline pautaAmbNulls = new TakingGuideline(null,24,"String.",23, 21,FqUnit.DAY);
        });

    }

    @Test
    void getTakingGuideline() {

        pauta.getDayMoment();
        assertEquals(DayMoment.AFTERBREAKFAST,pauta.getDayMoment());
        // assertEquals(NULL,pauta.getDayMoment()); coprobar la exepcion


        assertEquals(24,pauta.getDuration());
        assertEquals("Toma el medicamento exactamente como te lo hayan recetado.",pauta.getInstructions());

        assertEquals(posologia.getFreqUnit(),pauta.getPosology().getFreqUnit());
        assertEquals(posologia.getFreq(),pauta.getPosology().getFreq());
        assertEquals(posologia.getDose(),pauta.getPosology().getDose());

        assertNotEquals(posologiaFalse.getFreqUnit(),pauta.getPosology().getFreqUnit());
        assertNotEquals(posologiaFalse.getFreq(),pauta.getPosology().getFreq());
        assertNotEquals(posologiaFalse.getDose(),pauta.getPosology().getDose());

    }

    @Test
    void setTakingGuideline() {

        pauta.setDayMoment(DayMoment.DURINGBREAKFAST);
        assertEquals(DayMoment.DURINGBREAKFAST,pauta.getDuration());

        pauta.setDuration(2.3f);
        assertEquals(2.3f,pauta.getDuration());

        pauta.setInstructions("String .");
        assertEquals("String.",pauta.getInstructions());

        pauta.setPosology(posologiaSet);
        assertEquals(posologiaSet.getFreqUnit(),pauta.getPosology().getFreqUnit());
        assertEquals(posologiaSet.getFreq(),pauta.getPosology().getFreq());
        assertEquals(posologiaSet.getDose(),pauta.getPosology().getDose());


    }

    @Test
    void passesaNULLArgumentTakingGuideline() {

        posologiaSet = null;
        assertThrows(IllegalArgumentException.class, () -> {
            pauta.setPosology(posologiaSet);
        });

        String instructio = null;

        assertThrows(IllegalArgumentException.class, () -> {
            pauta.setInstructions(instructio);
        });


        assertThrows(IllegalArgumentException.class, () -> {
            pauta.setDayMoment(null);
        });

    }

}