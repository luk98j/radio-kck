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
    private Rotate rotation = new Rotate();
    private static final double pivotX = 35;
    private static final double pivotY = 35;
    private boolean statusAddStations = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        radioChannels = new RadioChannels();
        radio = new Radio(false);
        rotation.setPivotX(pivotX);
        rotation.setPivotY(pivotY);
        rotation.setAngle(150);
        knob.getTransforms().addAll(rotation);

        buttonChannelOne.setOnAction(event -> {
            if (statusAddStations) {
                radio.setFirstStation(radio.getActualFrequency());
                setActualTextOnLCD("Correctly written");
                statusAddStations = false;
            } else {
                if(radio.isTurnedOn()) {
                    setFavStation(radioChannels.playMusic(radio.getFirstStation()));
                }
            }
        });
        buttonChannelTwo.setOnAction(event -> {
            if (statusAddStations) {
                radio.setSecondStation(radio.getActualFrequency());
                setActualTextOnLCD("Correctly written");
                statusAddStations = false;
            } else {
                if(radio.isTurnedOn()) {
                    setFavStation(radioChannels.playMusic(radio.getSecondStation()));
                }
            }
        });
        buttonChannelThree.setOnAction(event -> {
            if (statusAddStations) {
                radio.setThirdStation(radio.getActualFrequency());
                setActualTextOnLCD("Correctly written");
                statusAddStations = false;
            } else {
                if(radio.isTurnedOn()) {
                    setFavStation(radioChannels.playMusic(radio.getThirdStation()));
                }
            }
        });
        buttonChannelFour.setOnAction(event -> {
            if (statusAddStations) {
                radio.setFourthStation(radio.getActualFrequency());
                setActualTextOnLCD("Correctly written");
                statusAddStations = false;
            } else {
                if(radio.isTurnedOn()) {
                    setFavStation(radioChannels.playMusic(radio.getFourthStation()));
                }
            }
        });
        buttonRDS.setOnAction(event -> {
            if(radio.isTurnedOn()){
                radioChannels.findNearestStation(radio.getActualFrequency());
            }
        });
    }
    public void rotation(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if(rotation.getAngle() != 300.0) {
                radioChannels.volumeUp();
                rotation.setAngle(rotation.getAngle() + 15);
            }
        } else if (event.getButton().equals(MouseButton.SECONDARY)) {
            if(rotation.getAngle() != 0.0) {
                radioChannels.volumeDown();
                rotation.setAngle(rotation.getAngle() - 15);
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
            BigDecimal bd = new BigDecimal(Double.sum(radio.getActualFrequency(), 0.1)).setScale(1, RoundingMode.HALF_EVEN);
            radio.setActualFrequency(bd.doubleValue());
            setActualTextOnLCD(Double.toString(radio.getActualFrequency()));
            checkStation();
        }
    }

    public void setFrequencyDown(){
        if(radio.isTurnedOn()) {
            BigDecimal bd = new BigDecimal(radio.getActualFrequency() - 0.1).setScale(1, RoundingMode.HALF_EVEN);
            radio.setActualFrequency(bd.doubleValue());
            setActualTextOnLCD(Double.toString(radio.getActualFrequency()));
            checkStation();
        }
    }

    public void addFavStation(){
        if(radio.isTurnedOn()) {
            statusAddStations = true;
            String tempText = screenLCD.getText();
            System.out.println(tempText);
            actionOnDelay("Store stations", 1);
            setActualTextOnLCD(tempText);
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
}
