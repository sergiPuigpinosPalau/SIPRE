package medicalconsultation;

import data.ProductID;

public class ProductSpecification {
    private ProductID UPCcode;
    private String description;
    private float price;

    public ProductSpecification(ProductID UPCcode, String description, float price) {
        this.UPCcode = UPCcode;
        this.description = description;
        this.price = price;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
