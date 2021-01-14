package data;

import data.testInterfaces.EmptyInterfaceProductIDTest;
import exceptions.InvalidUPCFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductIDTest implements EmptyInterfaceProductIDTest {
    ProductID productID;
    String prID;

    @BeforeEach
    void setUp() throws InvalidUPCFormat {
        productID = new ProductID("954125684521");
    }


    @Test
    @Override
    public void EmptyProductIDTest() throws InvalidUPCFormat {

        prID="954125684521";
        assertEquals(prID, this.productID.getUPC());
        assertThrows(InvalidUPCFormat.class, () -> {
            new ProductID("1244321243");
        });
        assertThrows(InvalidUPCFormat.class, () -> {
            new ProductID("");
        });

    }
}