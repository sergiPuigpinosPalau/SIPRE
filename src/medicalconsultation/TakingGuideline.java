package medicalconsultation;

import exceptions.IncorrectTakingGuidelinesException;

public class TakingGuideline { // Represents the taking guidelines of a medicine

    private float dayMoment;//TODO change
    private float duration;
    private String instructions;
    private Posology posology;

    public TakingGuideline(float dayMoment, float duration, String instructions, float dose, float freq, FqUnit freqUnit) {
        this.dayMoment = dayMoment;
        this.duration = duration;
        this.instructions = instructions;
        this.posology = new Posology(dose, freq, freqUnit);
    }

    public void modifyGuideline(String[] instruc) throws IncorrectTakingGuidelinesException{
        try{
            dayMoment = Float.parseFloat(instruc[0]);
        } catch (java.lang.NumberFormatException ex){
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

    public float getDayMoment() {
        return dayMoment;
    }

    public void setDayMoment(float dayMoment) {
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
        this.instructions = instructions;
    }

    public Posology getPosology() {
        return posology;
    }

    public void setPosology(Posology posology) {
        this.posology = posology;
    }
}