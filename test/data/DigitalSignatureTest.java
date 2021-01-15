package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class DigitalSignatureTest {
    DigitalSignature digitalSignature;
    byte[] signature = "DoctorSignature".getBytes();

    @BeforeEach
    void setUp() {
        digitalSignature = new DigitalSignature(signature);
    }

    @Test
    public void equalsTest() {
        assertEquals(new DigitalSignature(signature), this.digitalSignature);
        assertNotEquals(new DigitalSignature("otherSignature".getBytes(StandardCharsets.UTF_8)), this.digitalSignature);
    }

    @Test
    public void nullDigitalSignatureTest() {
        assertThrows(IllegalArgumentException.class, () -> new DigitalSignature(null));
    }
}
