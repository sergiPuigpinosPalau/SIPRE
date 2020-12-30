package data;

public class ProductID {
    private final String UPC;

    public ProductID(String code) {
        this.UPC = code;
    }

    public String getUPC() {
        return UPC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductID prodID = (ProductID) o;
        return UPC.equals(prodID.UPC);
    }

    @Override
    public int hashCode() {
        return UPC.hashCode();
    }

    @Override
    public String toString() {
        return "ProductID{" + "UPC='" + UPC + '\'' + '}';
    }
}
