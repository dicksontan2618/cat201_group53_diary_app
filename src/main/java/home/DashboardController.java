package home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    Label nameLabel;

    @FXML
    Label dateLabel;

    @FXML
    ImageView dayImage;

    @FXML
    ImageView nightImage;

    @FXML
    Button calendarButton;

    @FXML
    Button toDoButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void displayName(String username){
        nameLabel.setText("Good Day, "+ username + ".");
    }

    public void displayDate(String date){
        dateLabel.setText(date);
    }

    public void hideDay(){
        dayImage.setVisible(false);
    }

    public void hideNight(){
        nightImage.setVisible(false);
    }


    public void switchToCalendar (ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        root = loader.load();

        stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("Application.css");
        stage.setScene(scene);
        stage.show();
    }

    public void switchToToDo (ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("todo.fxml"));
        root = loader.load();

        stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("todo.css");
        stage.setScene(scene);
        stage.show();
    }

}
