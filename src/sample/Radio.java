package sample;

public class Radio {
    private boolean turnedOn;
    private double actualFrequency;
    private static final double MIN_FREQUENCY = 87.5;
    private static final double MAX_FREQUENCY = 102.5;

    public Radio(boolean turnedOn) {
        this.turnedOn = turnedOn;
        this.actualFrequency = MIN_FREQUENCY;
    }

    public boolean isTurnedOn() {
        return turnedOn;
    }

    public void setTurnedOn(boolean turnedOn) {
        this.turnedOn = turnedOn;
    }

    public double getActualFrequency() {
        return actualFrequency;
    }

    public void setActualFrequency(double actualFrequency) {
        this.actualFrequency = actualFrequency;
    }
}
