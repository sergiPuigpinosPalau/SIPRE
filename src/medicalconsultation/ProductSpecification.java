package medicalconsultation;

import data.ProductID;
import exceptions.InvalidPriceFormat;

import java.math.BigDecimal;

public class ProductSpecification {
    private ProductID UPCcode;
    private String description;
    private BigDecimal price;

    public ProductSpecification(ProductID UPCcode, String description, BigDecimal price) throws InvalidPriceFormat{
        this.UPCcode = UPCcode;
        this.description = description;
        if (price.scale() > 2 || price.signum() < 0)
            throw new InvalidPriceFormat();
        this.price = price;
    }

    private void checkPriceFormat()throws InvalidPriceFormat {
        if (price.scale() > 2 || price.signum() < 0)
            throw new InvalidPriceFormat();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSpecification productSpecification = (ProductSpecification) o;
        return UPCcode.equals(productSpecification.UPCcode);
    }

    public ProductID getUPCcode() {
        return UPCcode;
    }

    public void setUPCcode(ProductID UPCcode) {
        this.UPCcode = UPCcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) throws InvalidPriceFormat{
        checkPriceFormat();
        this.price = price;
    }
}
