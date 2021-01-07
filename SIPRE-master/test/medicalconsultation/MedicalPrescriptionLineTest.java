package medicalconsultation;

import data.ProductID;
import exceptions.IncorrectTakingGuidelinesException;
import exceptions.InvalidUPCFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MedicalPrescriptionLineTest {

    MedicalPrescriptionLine p, p2 ;
    ProductID producto;
    String[] instrucio;
    @BeforeEach
    void setUp() throws InvalidUPCFormat {
        producto = new ProductID("123456789123");
        instrucio = new String[]{"AFTERDINNER", "12", "Tomar con agua.", "13", "23", "HOUR"};
    }

    @Test
    void createMedicalPrescriptionLine() throws IncorrectTakingGuidelinesException {

        p = new MedicalPrescriptionLine(producto,instrucio);
        assertNotNull(p);

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

}