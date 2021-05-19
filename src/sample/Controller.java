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
    @FXML
    private void showTutorial(){
        Main.setPane(2);
    }
    @FXML
    private void showHowTo() {
        Main.setPane(4);
    }
    @FXML
    private void showPlayers(){
        Main.setPane(3);
    }
    @FXML
    private void showBuilderTutorial(){
        Main.setPane(9);
    }
    @FXML
    private void showScientistTutorial(){
        Main.setPane(8);
    }
    @FXML
    private void showLeaderTutorial(){
        Main.setPane(11);
    }
    @FXML
    private void showTrooperTutorial(){
        Main.setPane(5);
    }
    @FXML
    private void showHealerTutorial(){
        Main.setPane(7);
    }
    @FXML
    private void showUnknownTutorial(){
        Main.setPane(10);
    }
    @FXML
    private void showEggTutorial(){
        Main.setPane(6);
    }
    @FXML
    private void showPieceTutorial(){
        Main.setPane(12);
    }


    @FXML
    private void showMenu(){//returns back to the main screen
        Main.setPane(0);
    }
}
