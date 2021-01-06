package medicalconsultation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Array;

import static org.junit.jupiter.api.Assertions.*;

class PosologyTest {
    Posology p;
    FqUnit hora, day, arrgNull;
    @BeforeEach
    void setUp() {
        hora = FqUnit.HOUR;
        day = FqUnit.DAY;
        arrgNull = null;
        p = new Posology(24,24, hora);
    }

    @Test
    void createPosology() {
        Posology p2 = new Posology(24,24, hora);
        assertNotNull(p2);

        assertThrows(IllegalArgumentException.class, () -> {
            FqUnit fqUnitNULL =null;
            Posology pautaAmbNUL = new Posology(24,24,fqUnitNULL );
           });
    }

    @Test
    void getPosology() {
        assertEquals(24,p.getDose());
        assertEquals(24,p.getFreq());
        assertEquals(hora,p.getFreqUnit());
    }

    @Test
    void setPosology() {
        p.setDose(2.5f);
        assertNotEquals(2,p.getDose());
        assertEquals(2.5f,p.getDose());
        p.setFreq(10);
        assertEquals(10,p.getFreq());
        p.setFreqUnit(day);
        assertEquals(day,p.getFreqUnit());
    }

    @Test
    void passesaNULLArgumentPosology() {

        assertThrows(IllegalArgumentException.class, () -> {
            p.setFreqUnit(arrgNull);
        });

    }


}