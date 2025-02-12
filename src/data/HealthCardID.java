package data;

import exceptions.InvalidCIPFormat;

/**
 * The personal identifying code in the National Health Service.
 */
final public class HealthCardID {
    private final String personalID;

    public HealthCardID(String code) throws InvalidCIPFormat {
        if (code == null)
            throw new IllegalArgumentException();
        checkCIPFormat(code);
        this.personalID = code;
    }

    private void checkCIPFormat(String code) throws InvalidCIPFormat {
        if (code.length() < 28)
            throw new InvalidCIPFormat();

        if (!code.startsWith("BBBBBBBB") || !Character.isUpperCase(code.charAt(8)) || !Character.isUpperCase(code.charAt(9)))
            throw new InvalidCIPFormat();

        for (char numb : code.substring(10, 28).toCharArray()) {
            if (!Character.isDigit(numb))
                throw new InvalidCIPFormat();
        }
    }

    public String getPersonalID() {
        return personalID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HealthCardID hcardID = (HealthCardID) o;
        return personalID.equals(hcardID.personalID);
    }

    @Override
    public int hashCode() {
        return personalID.hashCode();
    }

    @Override
    public String toString() {
        return "HealthCardID{" + "personal code='" + personalID + '\'' + '}';
    }

}

