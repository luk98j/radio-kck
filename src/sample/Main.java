package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("RADIO");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("sample/style/stylesheet.css");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.getIcons().add(new Image("sample/assets/logo.png"));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
