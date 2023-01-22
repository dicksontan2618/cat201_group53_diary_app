package home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

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

    @FXML
    Button diaryButton;

    @FXML
    Text quote;

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

    public void randomQuote(){
        String[] quotes = {
                "Life is too short to wait, so just do it.",
                "The present is a gift, don't take for granted.",
                "Happiness is a mind, not something artificial.",
                "Dream it big, and work ultimately hard for it.",
                "Simplicity is the ultimate sophistication.",
                "Be yourself; everyone else is already taken.",
                "The best revenge and redemption is massive success.",
                "The way to do great work is love what you do.",
                "The most difficult thing is the decision to act.",
        };
        Random rand = new Random();
        int quoteIndex = rand.nextInt(quotes.length);
        quote.setText("\"" + quotes[quoteIndex] + "\"");
    }


    public void switchToCalendar (ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        root = loader.load();

        stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Calendar");
        scene = new Scene(root);
        scene.getStylesheets().add("Application.css");
        stage.setScene(scene);
        stage.show();
    }

    public void switchToToDo (ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("todo.fxml"));
        root = loader.load();

        stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        stage.setTitle("To-Do List");
        scene = new Scene(root);
        scene.getStylesheets().add("todo.css");
        stage.setScene(scene);
        stage.show();
    }

    public void switchToDiary (ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("diary.fxml"));
        root = loader.load();

        stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Diary");
        scene = new Scene(root);
        //scene.getStylesheets().add("todo.css");
        stage.setScene(scene);
        stage.show();
    }

}
