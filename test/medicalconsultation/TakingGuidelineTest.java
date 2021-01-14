package medicalconsultation;

import exceptions.IncorrectTakingGuidelinesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TakingGuidelineTest {

    Posology posologia , posologiaFalse, posologiaSet;
    String[] instrucio,instrucioNoValida1,instrucioNoValida2;
    @BeforeEach
    void setUp() {
        posologia = new Posology(24,24,FqUnit.DAY);
        posologiaFalse = new Posology(14,14,FqUnit.MONTH);
        posologiaSet = new Posology(10,10,FqUnit.HOUR);
        instrucio = new String[]{"AFTERDINNER", "12", "Tomar con agua.", "13", "23", "WEEK"};
        instrucioNoValida1 = new String[]{"Hola", "12", "Tomar con agua.", "12", "23", "HOUR"};
        instrucioNoValida2 = new String[]{"AFTERDINNER", "A", "Hey", "12", "23", "Hola"};
    }

    @Test
    @DisplayName("Povar que el objete no es crea null i es crei corectamen sese cap  Exception.")
    void createTakingGuideline() {
        new TakingGuideline(DayMoment.AFTERDINNER,24, "Tomar con agua.",12,12.2f, FqUnit.DAY);
        assertThrows(IllegalArgumentException.class, () -> {
            new TakingGuideline(null,24,null,23, 21,null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new TakingGuideline(null,24,"String.",23, 21,FqUnit.DAY);
        });
    }

    @Test
    @DisplayName("Povar que es pot acedir al contigut corectamen.")
    void getTakingGuideline() {
        TakingGuideline pauta = new TakingGuideline(DayMoment.AFTERBREAKFAST,12.5f,"Toma el medicamento exactamente como te lo hayan recetado.",24,24,FqUnit.DAY);
        assertEquals(DayMoment.AFTERBREAKFAST,pauta.getDayMoment());
        assertEquals(12.5f,pauta.getDuration());
        assertEquals("Toma el medicamento exactamente como te lo hayan recetado.",pauta.getInstructions());
        assertEquals(posologia.getFreqUnit(),pauta.getPosology().getFreqUnit());
        assertEquals(posologia.getFreq(),pauta.getPosology().getFreq());
        assertEquals(posologia.getDose(),pauta.getPosology().getDose());
        assertNotEquals(posologiaFalse.getFreqUnit(),pauta.getPosology().getFreqUnit());
        assertNotEquals(posologiaFalse.getFreq(),pauta.getPosology().getFreq());
        assertNotEquals(posologiaFalse.getDose(),pauta.getPosology().getDose());
    }

    @Test
    @DisplayName("Povar la funcio set().")
    void setTakingGuideline() {
        TakingGuideline pauta = new TakingGuideline(DayMoment.AFTERBREAKFAST,12.5f,"Toma el medicamento exactamente como te lo hayan recetado.",24,24,FqUnit.DAY);
        pauta.setDayMoment(DayMoment.DURINGBREAKFAST);
        assertEquals(DayMoment.DURINGBREAKFAST,pauta.getDayMoment());
        pauta.setDuration(2.3f);
        assertEquals(2.3f,pauta.getDuration());
        pauta.setInstructions("String.");
        assertEquals("String.",pauta.getInstructions());
        pauta.setPosology(posologiaSet);
        assertEquals(posologiaSet.getFreqUnit(),pauta.getPosology().getFreqUnit());
        assertEquals(posologiaSet.getFreq(),pauta.getPosology().getFreq());
        assertEquals(posologiaSet.getDose(),pauta.getPosology().getDose());
    }

    @Test
    @DisplayName("Povar que es pot modifica un Posology amb la funcio moifiy corectamen.")
    void modifyPosology() throws IncorrectTakingGuidelinesException {
        TakingGuideline pauta = new TakingGuideline(DayMoment.AFTERBREAKFAST,12.5f,"Toma el medicamento exactamente como te lo hayan recetado.",24,24,FqUnit.DAY);
        pauta.modifyGuideline(instrucio);
        assertEquals(DayMoment.valueOf(instrucio[0]),pauta.getDayMoment());
        assertEquals(Float.valueOf(instrucio[1]),pauta.getDuration());
        assertEquals(instrucio[2],pauta.getInstructions());
    }

    @Test
    @DisplayName("Povar que modifyPosology retorna les exepcionts.")
    void modifyExceptionPosology() {
        TakingGuideline pauta = new TakingGuideline(DayMoment.AFTERBREAKFAST,12.5f,"Toma el medicamento exactamente como te lo hayan recetado.",24,24,FqUnit.DAY);
        assertThrows(IncorrectTakingGuidelinesException.class, () -> {
            pauta.modifyGuideline(instrucioNoValida1);
        });
        assertThrows(IncorrectTakingGuidelinesException.class, () -> {
            pauta.modifyGuideline(instrucioNoValida2);
        });
    }

    @Test
    void testSetters() {
        TakingGuideline pauta = new TakingGuideline(DayMoment.AFTERBREAKFAST,12.5f,"Toma el medicamento exactamente como te lo hayan recetado.",24,24,FqUnit.DAY);
        assertThrows(IllegalArgumentException.class, () -> {
            pauta.setPosology(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            pauta.setInstructions(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            pauta.setDayMoment(null);
        });
    }

}