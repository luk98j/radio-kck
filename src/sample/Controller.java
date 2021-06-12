package sample;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private static double actualFrequency;
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
    private static final double pivotX = 162;
    private static final double pivotY = 151;
    private boolean statusAddStations = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        radioChannels = new RadioChannels();
        radio = new Radio(false);
        rotation.setPivotX(pivotX);
        rotation.setPivotY(pivotY);
        //knob.getTransforms().addAll(rotation);
        buttonChannelOne.setOnAction(event -> {
            if(statusAddStations){
                radio.setFirstStation(radio.getActualFrequency());
                statusAddStations = false;
            } else {
                radioChannels.playMusic(radio.getFirstStation());
            }
        });
        buttonChannelTwo.setOnAction(event -> {
            if(statusAddStations){
                radio.setSecondStation(radio.getActualFrequency());
                statusAddStations = false;
            } else {
                radioChannels.playMusic(radio.getSecondStation());
            }
        });
        buttonChannelThree.setOnAction(event -> {
            if(statusAddStations){
                radio.setThirdStation(radio.getActualFrequency());
                statusAddStations = false;
            } else {
                radioChannels.playMusic(radio.getThirdStation());
            }
        });
        buttonChannelFour.setOnAction(event -> {
            if(statusAddStations){
                radio.setFourthStation(radio.getActualFrequency());
                statusAddStations = false;
            } else {
                radioChannels.playMusic(radio.getFourthStation());
            }
        });

    }
    public void rotation(MouseEvent event) {
        System.out.println(rotation.getAngle());
//        if (event.getButton().toString().equals(PRIMARY_VALUE)) {
//            if(rotation.getAngle() != 300.0) {
//                System.out.println("LEWY");
//                rotation.setAngle(rotation.getAngle() + 15);
//            }
//        } else if (event.getButton().toString().equals(SECONDARY_VALUE)) {
//            if(rotation.getAngle() != 0.0) {
//                System.out.println("PRAWY");
//                rotation.setAngle(rotation.getAngle() - 15);
//            }
//        }
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

    private void openActualChannel(){
        actionOnDelay(Double.toString(radio.getActualFrequency()),2);
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
        String tempText = screenLCD.getText();
        System.out.println(tempText);
        statusAddStations = true;
        actionOnDelay("Store stations",2);
        screenLCD.setText(tempText);
    }

    private void checkStation(){
        String channel = radioChannels.playMusic(radio.getActualFrequency());
        if(!channel.isEmpty()){
            setActualTextOnLCD(channel);
        }
    }

}
