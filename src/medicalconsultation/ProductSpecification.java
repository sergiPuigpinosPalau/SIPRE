package medicalconsultation;

import data.ProductID;
import exceptions.InvalidPriceFormat;
import exceptions.StringTooLongException;

import java.math.BigDecimal;

public class ProductSpecification {
    private ProductID UPCcode;
    private String description;
    private BigDecimal price;
    private static int MAX_DESC_SIZE = 2000;        //So text doesn't overflow it's container

    public ProductSpecification(ProductID UPCcode, String description, BigDecimal price) throws InvalidPriceFormat, StringTooLongException{
        if (UPCcode==null || description==null || price==null)
            throw new IllegalArgumentException();
        this.UPCcode = UPCcode;
        if (description.length() > MAX_DESC_SIZE)
            throw new StringTooLongException("Description is too long, text shouldn't exceed " + MAX_DESC_SIZE + " characters");
        this.description = description;
        checkPriceFormat(price);
        this.price = price;
    }

    private void checkPriceFormat(BigDecimal inPrice)throws InvalidPriceFormat {
        if (inPrice.scale() > 2 || inPrice.signum() < 0)
            throw new InvalidPriceFormat();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSpecification productSpecification = (ProductSpecification) o;
        return UPCcode.equals(productSpecification.UPCcode) && description.equals(productSpecification.description) && price.equals(productSpecification.price);
        //TODO mirar altres
        //TODO nse que havies fet, pero mas recordar que sa de comparar tots els valors
    }

    public ProductID getUPCcode() {
        return UPCcode;
    }

    public void setUPCcode(ProductID UPCcode) {
        if (UPCcode==null)
            throw new IllegalArgumentException();
        this.UPCcode = UPCcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description==null)
            throw new IllegalArgumentException();
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) throws InvalidPriceFormat{
        if (price==null)
            throw new IllegalArgumentException();
        checkPriceFormat(price);
        this.price = price;
    }

    public static int getMaxDescSize() {
        return MAX_DESC_SIZE;
    }

    public static void setMaxDescSize(int maxDescSize) {
        MAX_DESC_SIZE = maxDescSize;
    }
}
