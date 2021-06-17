package sample;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private ImageView knob;
    @FXML
    private Button switchOn;
    @FXML
    private Button frequencyUp;
    @FXML
    private Button frequencyDown;
    @FXML
    private Button buttonRDS;
    @FXML
    private Button buttonChannelOne;
    @FXML
    private Button buttonChannelTwo;
    @FXML
    private Button buttonChannelThree;
    @FXML
    private Button buttonChannelFour;
    @FXML
    private Button buttonFavourite;
    @FXML
    private TextField screenLCD;

    private RadioChannels radioChannels;
    private Radio radio;
    private final Rotate rotation = new Rotate();
    private static final double pivotX = 35;
    private static final double pivotY = 35;
    private boolean statusAddStations = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        radioChannels = new RadioChannels();
        radio = new Radio(false);
        setKnobAndPivot();
        buttonChannelOne.setOnAction(event -> {
            if (statusAddStations) {
                radio.setFirstStation(radio.getActualFrequency());
                if(radio.getFirstStation() == radio.getActualFrequency()) {
                    actionAfterSaving();
                }
            } else {
                if(radio.isTurnedOn()) {
                    setFavStation(radioChannels.playMusic(radio.getFirstStation()));
                }
            }
        });
        buttonChannelTwo.setOnAction(event -> {
            if (statusAddStations) {
                radio.setSecondStation(radio.getActualFrequency());
                if(radio.getSecondStation() == radio.getActualFrequency()) {
                    actionAfterSaving();
                }
            } else {
                if(radio.isTurnedOn()) {
                    setFavStation(radioChannels.playMusic(radio.getSecondStation()));
                }
            }
        });
        buttonChannelThree.setOnAction(event -> {
            if (statusAddStations) {
                radio.setThirdStation(radio.getActualFrequency());
                if(radio.getThirdStation() == radio.getActualFrequency()) {
                    actionAfterSaving();
                }
            } else {
                if(radio.isTurnedOn()) {
                    setFavStation(radioChannels.playMusic(radio.getThirdStation()));
                }
            }
        });
        buttonChannelFour.setOnAction(event -> {
            if (statusAddStations) {
                radio.setFourthStation(radio.getActualFrequency());
                if(radio.getFourthStation() == radio.getActualFrequency()) {
                    actionAfterSaving();
                }
            } else {
                if(radio.isTurnedOn()) {
                    setFavStation(radioChannels.playMusic(radio.getFourthStation()));
                }
            }
        });

        buttonRDS.setOnAction(event -> {
            if(radio.isTurnedOn()){
                Map.Entry<String, Double> station = radioChannels.findNearestStation(radio.getActualFrequency());
                setActualTextOnLCD(station.getKey());
                radio.setActualFrequency(station.getValue());
            }
        });
    }

    public void rotation(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if(rotation.getAngle() != 300.0) {
                radioChannels.volumeUp();
                rotation.setAngle(rotation.getAngle() + 30);
            }
        } else if (event.getButton().equals(MouseButton.SECONDARY)) {
            if(rotation.getAngle() != 0.0) {
                radioChannels.volumeDown();
                rotation.setAngle(rotation.getAngle() - 30);
            }
        }
    }

    public void changeRadioState(){
        if(radio.isTurnedOn()){
            radioChannels.stopAll();
            radio.setTurnedOn(false);
            setActualTextOnLCD("TURN OFF");
            closedActualChannel();
        } else {
            radio.setTurnedOn(true);
            setActualTextOnLCD("TURN ON");
            openActualChannel();
        }
    }


    public void setFrequencyUp(){
        if(radio.isTurnedOn()) {
            BigDecimal bd = BigDecimal.valueOf(Double.sum(radio.getActualFrequency(), 0.1)).setScale(1, RoundingMode.HALF_EVEN);
            radio.setActualFrequency(bd.doubleValue());
            setActualTextOnLCD(Double.toString(radio.getActualFrequency()));
            checkStation();
        }
    }

    public void setFrequencyDown(){
        if(radio.isTurnedOn()) {
            BigDecimal bd = BigDecimal.valueOf(radio.getActualFrequency() - 0.1).setScale(1, RoundingMode.HALF_EVEN);
            radio.setActualFrequency(bd.doubleValue());
            setActualTextOnLCD(Double.toString(radio.getActualFrequency()));
            checkStation();
        }
    }

    public void addFavStation(){
        if(radio.isTurnedOn()) {
            statusAddStations = true;
            actionOnDelay("Store stations",1);
        }
    }

    private void checkStation(){
        String channel = radioChannels.playMusic(radio.getActualFrequency());
        if(!channel.isEmpty()){
            setActualTextOnLCD(channel);
        }
    }

    private void setFavStation(String nameOfStation){
        double frequency = radioChannels.getStationFrequency(nameOfStation);
        radio.setActualFrequency(frequency);
        setActualTextOnLCD(Double.toString(radio.getActualFrequency()));
        checkStation();
    }

    private void openActualChannel(){
        actionOnDelay(Double.toString(radio.getActualFrequency()),2);
        radioChannels.playMusic(radio.getActualFrequency());
    }
    private void closedActualChannel(){
        actionOnDelay("",2);
    }

    private void setActualTextOnLCD(String text){
        screenLCD.setText(text);
    }

    private void actionOnDelay(String text, int delay){
        PauseTransition pause = new PauseTransition(Duration.seconds(delay));
        pause.setOnFinished(event -> setActualTextOnLCD(text));
        pause.play();
    }

    private void setKnobAndPivot(){
        rotation.setPivotX(pivotX);
        rotation.setPivotY(pivotY);
        rotation.setAngle(150);
        knob.getTransforms().addAll(rotation);
    }

    private void actionAfterSaving(){
        setActualTextOnLCD("Correctly written");
        statusAddStations = false;
    }
}
