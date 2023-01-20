package home;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class DashboardController {

    @FXML
    Label nameLabel;

    @FXML
    Label dateLabel;

    @FXML
    ImageView dayImage;

    @FXML
    ImageView nightImage;

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

}
