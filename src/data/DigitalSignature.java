package data;

import java.util.Arrays;

public class DigitalSignature {
    private final byte[] signature;

    public DigitalSignature(byte[] code) {
        this.signature = code;
    }

    public byte[] getSignature() {
        return signature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DigitalSignature sign = (DigitalSignature) o;
        return Arrays.equals(signature, sign.signature);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(signature);
    }

    @Override
    public String toString() {
        return "DigitalSignature{" + "signature='" + Arrays.toString(signature) + '\'' + '}';
    }

    //TODO Potser comprovar algo relacionat amb bytes que pugui fallar?
}
