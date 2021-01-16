package medicalconsultation;

import exceptions.IncorrectTakingGuidelinesException;

import java.util.Objects;

public class Posology { // A class that represents the posology of a medicine

    private float dose;
    private float freq;
    private FqUnit freqUnit;

    public Posology(float dose, float freq, FqUnit freqUnit) {
        if (freqUnit == null)
            throw new IllegalArgumentException();
        this.dose = dose;
        this.freq = freq;
        this.freqUnit = freqUnit;
    }

    public void modifyPosology(String[] instruc) throws IncorrectTakingGuidelinesException {
        try {
            dose = Float.parseFloat(instruc[3]);
        } catch (java.lang.NumberFormatException ex) {
            if (!instruc[3].equals(""))
                throw new IncorrectTakingGuidelinesException("Invalid instruction's format");
        }

        try {
            freq = Float.parseFloat(instruc[4]);
        } catch (java.lang.NumberFormatException ex) {
            if (!instruc[4].equals(""))
                throw new IncorrectTakingGuidelinesException("Invalid instruction's format");
        }

        try {
            freqUnit = FqUnit.valueOf(instruc[5]);
        } catch (IllegalArgumentException ex) {
            if (!instruc[5].equals(""))
                throw new IncorrectTakingGuidelinesException("Invalid instruction's format");
        }
    }

    public float getDose() {
        return dose;
    }

    public void setDose(float dose) {
        this.dose = dose;
    }

    public float getFreq() {
        return freq;
    }

    public void setFreq(float freq) {
        this.freq = freq;
    }

    public FqUnit getFreqUnit() {
        return freqUnit;
    }

    public void setFreqUnit(FqUnit freqUnit) {
        if (freqUnit == null)
            throw new IllegalArgumentException();
        this.freqUnit = freqUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Posology posology = (Posology) o;
        return Float.compare(posology.dose, dose) == 0 && Float.compare(posology.freq, freq) == 0 && freqUnit == posology.freqUnit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dose, freq, freqUnit);
    }
}