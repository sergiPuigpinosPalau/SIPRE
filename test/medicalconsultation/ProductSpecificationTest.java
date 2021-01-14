package medicalconsultation;

import data.ProductID;
import exceptions.InvalidPriceFormat;
import exceptions.InvalidUPCFormat;
import exceptions.ProductNotInPrescription;
import exceptions.StringTooLongException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductSpecificationTest {
    ProductSpecification produc, producA, producB;
    String descripcionOK ,descripcionNoValid;
    BigDecimal priceOK,priceNoValid1, priceNoValid2, priceNoValid3;

    @BeforeEach
    void setUp() {
        descripcionOK="LLLorem ipsum ...";
        descripcionNoValid="Muuuy lejos, más allá de las montañas de palabras, alejados de los países de las vocales y las consonantes, viven los textos simulados. Viven aislados en casas de letras, en la costa de la semántica, un gran océano de lenguas. Un riachuelo llamado Pons fluye por su pueblo y los abastece con las normas necesarias. Hablamos de un país paraisomático en el que a uno le caen pedazos de frases asadas en la boca. Ni siquiera los todopoderosos signos de puntuación dominan a los textos simulados; una vida, se puede decir, poco ortográfica. Pero un buen día, una pequeña línea de texto simulado, llamada Lorem Ipsum, decidió aventurarse y salir al vasto mundo de la gramática. El gran Oxmox le desanconsejó hacerlo, ya que esas tierras estaban llenas de comas malvadas, signos de interrogación salvajes y puntos y coma traicioneros, pero el texto simulado no se dejó atemorizar. Empacó sus siete versales, enfundó su inicial en el cinturón y se puso en camino. Cuando ya había escalado las primeras colinas de las montañas cursivas, se dio media vuelta para dirigir su mirada por última vez, hacia su ciudad natal Letralandia, el encabezamiento del pueblo Alfabeto y el subtítulo de su propia calle, la calle del renglón. Una pregunta retórica se le pasó por la mente y le puso melancólico, pero enseguida reemprendió su marcha. De nuevo en camino, se encontró con una copia. La copia advirtió al pequeño texto simulado de que en el lugar del que ella venía, la habían reescrito miles de veces y que todo lo que había quedado de su original era la palabra \"y\", así que más le valía al pequeño texto simulado volver a su país, donde estaría mucho más seguro. Pero nada de lo dicho por la copia pudo convencerlo, de manera que al cabo de poco tiempo, unos pérfidos redactores publicitarios lo encontraron y emborracharon con Longe y Parole para llevárselo después a su agencia, donde abusaron de él para sus proyectos, una y otra vez. Y si aún no lo han reescrito, lo siguen utilizando hasta ahora. Muy lejos";
        priceOK = new BigDecimal("12.2");
        priceNoValid1 = new BigDecimal("12.222");
        priceNoValid2 = new BigDecimal("-12");
        priceNoValid3 = new BigDecimal("-12.333");
    }

    @Test
    @DisplayName("Povar que el objete no es crea null i es crei corectamen sese cap  Exception.")
    void createProductSpecification() throws InvalidPriceFormat, StringTooLongException, InvalidUPCFormat {
        produc = new ProductSpecification(new ProductID("123456789123"),descripcionOK,priceOK);
        assertNotNull(produc);
        assertThrows(IllegalArgumentException.class, () -> {
            produc = new ProductSpecification(null,descripcionOK,null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            produc = new ProductSpecification(new ProductID("123456789123"),null,priceOK);
        });
        assertThrows(StringTooLongException.class, () -> {
            produc = new ProductSpecification(new ProductID("123456789123"),descripcionNoValid,priceOK);//TODO potser moure-ho al productID
        });
        assertThrows(InvalidPriceFormat.class, () -> {
            produc = new ProductSpecification(new ProductID("123456789123"),descripcionOK,priceNoValid1);
        });
        assertThrows(InvalidPriceFormat.class, () -> {
            produc = new ProductSpecification(new ProductID("123456789123"),descripcionOK,priceNoValid2);
        });
        assertThrows(InvalidPriceFormat.class, () -> {
            produc = new ProductSpecification(new ProductID("123456789123"),descripcionOK,priceNoValid3);
        });
    }

    @Test
    @DisplayName("Povar que es pot acedir al contigut corectamen.")
    void getProductSpecification() throws InvalidUPCFormat, InvalidPriceFormat, StringTooLongException {
        produc = new ProductSpecification(new ProductID("123456789123"),descripcionOK,priceOK);
        assertEquals(new ProductID("123456789123").getUPC(),produc.getUPCcode().getUPC());
        assertEquals(descripcionOK,produc.getDescription());
        assertEquals(priceOK,produc.getPrice());
    }

    @Test
    @DisplayName("Povar la funcio set().")
    void setProductSpecification() throws InvalidUPCFormat, InvalidPriceFormat, StringTooLongException {
        //UPC
        produc = new ProductSpecification(new ProductID("123456789123"),descripcionOK,priceOK);
        produc.setUPCcode(new ProductID("123456789124"));
        assertNotEquals(new ProductID("123456789123").getUPC(),produc.getUPCcode().getUPC());
        assertEquals(new ProductID("123456789124").getUPC(),produc.getUPCcode().getUPC());
        //Description
        String descripcionNew = "Muy lejos ... ";
        produc.setDescription(descripcionNew);
        assertNotEquals(descripcionOK,produc.getDescription());
        assertEquals(descripcionNew,produc.getDescription());
        //Price
        BigDecimal priceNew = new BigDecimal("14.2");
        produc.setPrice(priceNew);
        assertEquals(priceNew,produc.getPrice());
        assertNotEquals(priceOK,produc.getPrice());
    }

    @Test
    @DisplayName("Povar que 2 productes son iguals.")
    void equals() throws InvalidUPCFormat, InvalidPriceFormat, StringTooLongException {
        producA = new ProductSpecification(new ProductID("123456789123"),descripcionOK,priceOK);
        producB = new ProductSpecification(new ProductID("123456789123"),descripcionOK,priceOK);
        assertEquals(producB, producA);
        producB.setDescription("hola");
        assertNotEquals(producB, producA);
        assertNotEquals(producA, null);
    }


}