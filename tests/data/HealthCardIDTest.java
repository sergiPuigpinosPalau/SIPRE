package data;


import data.testInterfaces.EmptyInterfaceHealthCardIDTest;
import exceptions.InvalidCIPFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HealthCardIDTest implements EmptyInterfaceHealthCardIDTest {

    HealthCardID healthCardID;
    String healthID;

    @BeforeEach
    void setUp() throws InvalidCIPFormat {
        healthCardID = new HealthCardID("BBBBBBBBAR444851805874780033");
    }


    @Test
    @Override
    public void EmptyHealthCardIDTest() throws InvalidCIPFormat {

        healthID= "BBBBBBBBAR444851805874780033";
        assertEquals(healthID, this.healthCardID.getPersonalID());
        assertThrows(InvalidCIPFormat.class, () -> {
            new HealthCardID("BBBBBBBBAR123456789144");
        });
        assertThrows(InvalidCIPFormat.class, () -> {
            new HealthCardID("");
        });

    }
}
