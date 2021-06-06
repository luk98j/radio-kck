package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private ImageView knob;

    private Rotate rotation = new Rotate();
    private static final double pivotX = 162;
    private static final double pivotY = 151;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rotation.setPivotX(pivotX);
        rotation.setPivotY(pivotY);
        knob.getTransforms().addAll(rotation);
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
}
