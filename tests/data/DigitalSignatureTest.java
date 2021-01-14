package data;

import data.testInterfaces.EmptyInterfaceDigitalSignatureTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DigitalSignatureTest implements EmptyInterfaceDigitalSignatureTest {
    DigitalSignature digitalSignature;
    byte[] eSign;
    byte[] signatura = "Any String you want".getBytes();

    @BeforeEach
    void setUp(){
        digitalSignature = new DigitalSignature(signatura);
    }


    @Test
    @Override
    public void EmptyDigitalSignatureTest() {

        assertEquals(signatura, this.digitalSignature.getSignature());
        assertThrows(IllegalArgumentException.class, () -> {
            new DigitalSignature(eSign);}
        );


    }
}
