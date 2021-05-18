package sample;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
public class Controller {
    @FXML
    Button playButton, tutorialButton;
    @FXML
    private void playGame(){
        Main.setPane(1);
    }
//    @FXML
//    private void showTutorial(){
//        Main.setPane(4);
//    }
    @FXML
    private void showPlayers(){
        Main.setPane(3);
    }
}
