package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DigitalSignatureTest {
    DigitalSignature digitalSignature;
    byte[] signature = "Any String you want".getBytes();

    @BeforeEach
    void setUp(){
        digitalSignature = new DigitalSignature(signature);
    }

    @Test
    public void equalsTest() {
        assertEquals(signature, this.digitalSignature.getSignature());
    }

    @Test
    public void nullDigitalSignatureTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DigitalSignature(null);}
        );
    }
}
