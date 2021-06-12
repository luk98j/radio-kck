package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Radio {
    private boolean turnedOn;
    private double actualFrequency;
    private static final double MIN_FREQUENCY = 87.5;
    private static final double MAX_FREQUENCY = 102.5;
    private double firstStation;
    private double secondStation;
    private double thirdStation;
    private double fourthStation;
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

    public double getFirstStation() {
        return firstStation;
    }

    public void setFirstStation(double firstStation) {
        this.firstStation = firstStation;
    }

    public double getSecondStation() {
        return secondStation;
    }

    public void setSecondStation(double secondStation) {
        this.secondStation = secondStation;
    }

    public double getThirdStation() {
        return thirdStation;
    }

    public void setThirdStation(double thirdStation) {
        this.thirdStation = thirdStation;
    }

    public double getFourthStation() {
        return fourthStation;
    }

    public void setFourthStation(double fourthStation) {
        this.fourthStation = fourthStation;
    }
}
