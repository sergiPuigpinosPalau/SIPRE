package medicalconsultation;

import exceptions.IncorrectTakingGuidelinesException;

import java.util.Objects;

public class TakingGuideline { // Represents the taking guidelines of a medicine

    private DayMoment dayMoment;
    private float duration;
    private String instructions;
    private Posology posology;

    public TakingGuideline(DayMoment dayMoment, float duration, String instructions, float dose, float freq, FqUnit freqUnit) {
        if (dayMoment==null || instructions==null || freqUnit==null)
            throw new IllegalArgumentException();
        this.dayMoment = dayMoment;
        this.duration = duration;
        this.instructions = instructions;
        this.posology = new Posology(dose, freq, freqUnit);
    }

    public void modifyGuideline(String[] instruc) throws IncorrectTakingGuidelinesException{
        try{
            dayMoment = DayMoment.valueOf(instruc[0]);
        } catch (IllegalArgumentException ex){
            if (!instruc[0].equals(""))
                throw new IncorrectTakingGuidelinesException("Invalid instruction's format");
        }

        try{
            duration = Float.parseFloat(instruc[1]);
        } catch (java.lang.NumberFormatException ex){
            if (!instruc[1].equals(""))
                throw new IncorrectTakingGuidelinesException("Invalid instruction's format");
        }

        if (!instruc[2].equals(""))
            instructions = instruc[2];

        posology.modifyPosology(instruc);
    }

    public DayMoment getDayMoment() {
        return dayMoment;
    }

    public void setDayMoment(DayMoment dayMoment) {
        if (dayMoment==null)
            throw new IllegalArgumentException();
        this.dayMoment = dayMoment;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        if (instructions==null)
            throw new IllegalArgumentException();
        this.instructions = instructions;
    }

    public Posology getPosology() {
        return posology;
    }

    public void setPosology(Posology posology) {
        if (posology==null)
            throw new IllegalArgumentException();
        this.posology = posology;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TakingGuideline that = (TakingGuideline) o;
        return Float.compare(that.duration, duration) == 0 && dayMoment == that.dayMoment && Objects.equals(instructions, that.instructions) && Objects.equals(posology, that.posology);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayMoment, duration, instructions, posology);
    }
}