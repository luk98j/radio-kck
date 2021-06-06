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

    private Radio radio;
    private Rotate rotation = new Rotate();
    private static final double pivotX = 162;
    private static final double pivotY = 151;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        radio = new Radio(false);
        rotation.setPivotX(pivotX);
        rotation.setPivotY(pivotY);
        //knob.getTransforms().addAll(rotation);
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

}
