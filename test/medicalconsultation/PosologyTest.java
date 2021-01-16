package medicalconsultation;

import exceptions.IncorrectTakingGuidelinesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PosologyTest {

    FqUnit hora, day, arrgNull;
    String[] instrucio, instrucioNoValida1, instrucioNoValida2, instrucioNoValida3;

    @BeforeEach
    void setUp() {
        hora = FqUnit.HOUR;
        day = FqUnit.DAY;
        arrgNull = null;
        instrucio = new String[]{"AFTERDINNER", "12", "Tomar con agua.", "13", "23", "HOUR"};
        instrucioNoValida1 = new String[]{"AFTERDINNER", "12", "Tomar con agua.", "Hola", "23", "HOUR"};
        instrucioNoValida2 = new String[]{"AFTERDINNER", "12", "Tomar con agua.", "12", "23", "Hola"};
        instrucioNoValida3 = new String[]{"AFTERDINNER", "12", "Tomar con agua.", "12", "", "Hola"};
    }

    @Test
    @DisplayName("Check that the posology gets created without any errors")
    void createPosology() {
        new Posology(24, 24, hora);
        assertThrows(IllegalArgumentException.class, () -> new Posology(24, 24, null));
    }

    @Test
    @DisplayName("Check information stored matches")
    void getPosology() {
        Posology p = new Posology(24, 24, hora);
        assertEquals(24, p.getDose());
        assertEquals(24, p.getFreq());
        assertEquals(hora, p.getFreqUnit());
    }

    @Test
    @DisplayName("Check setters")
    void setPosology() {
        Posology p = new Posology(24, 24, hora);
        p.setDose(2.5f);
        assertNotEquals(2, p.getDose());
        assertEquals(2.5f, p.getDose());
        p.setFreq(10);
        assertEquals(10, p.getFreq());
        p.setFreqUnit(day);
        assertEquals(day, p.getFreqUnit());
        assertThrows(IllegalArgumentException.class, () -> p.setFreqUnit(arrgNull));
    }

    @Test
    @DisplayName("Check modify function")
    void modifyPosology() throws IncorrectTakingGuidelinesException {
        Posology p = new Posology(24, 24, hora);
        assertThrows(IncorrectTakingGuidelinesException.class, () -> p.modifyPosology(instrucioNoValida1));
        assertThrows(IncorrectTakingGuidelinesException.class, () -> p.modifyPosology(instrucioNoValida2));
        p.modifyPosology(instrucio);
        assertEquals(Float.valueOf(instrucio[3]), p.getDose());
        assertEquals(Float.valueOf(instrucio[4]), p.getFreq());
        assertEquals(FqUnit.valueOf(instrucio[5]), p.getFreqUnit());
    }

    @Test
    @DisplayName("Check modify function with empty arguments")
    void modifyPosologyEmptyArgum() throws IncorrectTakingGuidelinesException {
        Posology p = new Posology(24, 24, hora);
        p.modifyPosology(instrucio);
        p.modifyPosology(new String[]{"", "", "", "", "", ""});
        assertEquals(Float.valueOf(instrucio[3]), p.getDose());
        assertEquals(Float.valueOf(instrucio[4]), p.getFreq());
        assertEquals(FqUnit.valueOf(instrucio[5]), p.getFreqUnit());
    }

    @Test
    @DisplayName("Check equal")
    public void equalsTest() {
        Posology p = new Posology(24, 24, hora);
        Posology p2 = new Posology(24, 24, hora);
        assertEquals(p, p2);
        p2.setDose(70);
        assertNotEquals(p, p2);
    }
}