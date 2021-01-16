package data;

import exceptions.InvalidUPCFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductIDTest {
    ProductID productID;

    @BeforeEach
    void setUp() throws InvalidUPCFormat {
        productID = new ProductID("954125684521");
    }

    @Test
    public void equalsTest() throws InvalidUPCFormat {
        assertEquals(new ProductID("954125684521"), this.productID);
        assertNotEquals(new ProductID("954125684881"), this.productID);
    }

    @Test
    public void invalidProductIDTest() {
        assertThrows(InvalidUPCFormat.class, () -> new ProductID("1244321243"));
        assertThrows(InvalidUPCFormat.class, () -> new ProductID("AJKSDFH5"));
        assertThrows(InvalidUPCFormat.class, () -> new ProductID("DSJKFHJKDSBDSHJKDSHAFKJ"));
        assertThrows(InvalidUPCFormat.class, () -> new ProductID(""));
    }
}