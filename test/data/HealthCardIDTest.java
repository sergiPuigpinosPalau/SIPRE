package data;


import exceptions.InvalidCIPFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class HealthCardIDTest {

    HealthCardID healthCardID;
    String healthID;

    @BeforeEach
    void setUp() throws InvalidCIPFormat {
        healthCardID = new HealthCardID("BBBBBBBBAR444851805874780033");
    }

    @Test
    public void equalsTest() {
        healthID= "BBBBBBBBAR444851805874780033";
        assertEquals(healthID, this.healthCardID.getPersonalID());
    }

    @Test
    public void invalidCodeTest() {
        assertThrows(InvalidCIPFormat.class, () -> {
            new HealthCardID("BBBBBBBBAR123456789144");
        });
        assertThrows(InvalidCIPFormat.class, () -> {
            new HealthCardID("AAAAABBBAR123456789144");
        });
        assertThrows(InvalidCIPFormat.class, () -> {
            new HealthCardID("5698732158123456789144");
        });
        assertThrows(InvalidCIPFormat.class, () -> {
            new HealthCardID("AAAAAAAAAAAAAAAAAAAAAA");
        });
        assertThrows(InvalidCIPFormat.class, () -> {
            new HealthCardID("BBBBBBBBAR123456789144BBBBBBBBAR123456789144BBBBBBBBAR123456789144");
        });
        assertThrows(InvalidCIPFormat.class, () -> {
            new HealthCardID("");
        });
    }
}
