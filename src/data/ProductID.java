package data;

import exceptions.InvalidUPCFormat;

public class ProductID {
    private final String UPC;

    public ProductID(String code) throws InvalidUPCFormat {
        if (code == null)
            throw new IllegalArgumentException();
        checkUPCFormat(code);
        this.UPC = code;
    }

    private void checkUPCFormat(String code) throws InvalidUPCFormat {
        if (code.length() < 12)
            throw new InvalidUPCFormat();

        for (char numb:code.toCharArray()) {
            if (!Character.isDigit(numb))
                throw new InvalidUPCFormat();
        }
    }

    public String getUPC() {
        return UPC;
    }

    @Override
    public boolean equals(Object o) {
        //if (this == o) return true;
       // if (o == null || getClass() != o.getClass()) return false;
        if ( o == null ) return false;
        ProductID prodID = (ProductID) o;

        return this.getUPC().equals(prodID.getUPC());
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
