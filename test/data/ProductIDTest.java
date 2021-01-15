package data;

import exceptions.InvalidUPCFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductIDTest {
    ProductID productID;
    String prID;

    @BeforeEach
    void setUp() throws InvalidUPCFormat {
        productID = new ProductID("954125684521");
    }

    @Test
    public void equalsTest() {
        prID="954125684521";
        assertEquals(prID, this.productID.getUPC());
    }

    @Test
    public void invalidProductIDTest() {
        assertThrows(InvalidUPCFormat.class, () -> {
            new ProductID("1244321243");
        });
        assertThrows(InvalidUPCFormat.class, () -> {
            new ProductID("AJKSDFH5");
        });
        assertThrows(InvalidUPCFormat.class, () -> {
            new ProductID("DSJKFHJKDSBDSHJKDSHAFKJ");
        });
        assertThrows(InvalidUPCFormat.class, () -> {
            new ProductID("");
        });
    }
}