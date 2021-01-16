package data;


import exceptions.InvalidCIPFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HealthCardIDTest {

    HealthCardID healthCardID;

    @BeforeEach
    void setUp() throws InvalidCIPFormat {
        healthCardID = new HealthCardID("BBBBBBBBAR444851805874780033");
    }

    @Test
    public void equalsTest() throws InvalidCIPFormat {
        assertEquals(new HealthCardID("BBBBBBBBAR444851805874780033"), this.healthCardID);
        assertNotEquals(new HealthCardID("BBBBBBBBAR444851805874789999"), this.healthCardID);
    }

    @Test
    public void invalidCodeTest() {
        assertThrows(InvalidCIPFormat.class, () -> new HealthCardID("BBBBBBBBAR123456789144"));
        assertThrows(InvalidCIPFormat.class, () -> new HealthCardID("AAAAABBBAR123456789144"));
        assertThrows(InvalidCIPFormat.class, () -> new HealthCardID("5698732158123456789144"));
        assertThrows(InvalidCIPFormat.class, () -> new HealthCardID("AAAAAAAAAAAAAAAAAAAAAA"));
        assertThrows(InvalidCIPFormat.class, () -> new HealthCardID("BBBBBBBBAR123456789144BBBBBBBBAR123456789144BBBBBBBBAR123456789144"));
        assertThrows(InvalidCIPFormat.class, () -> new HealthCardID(""));
    }
}
