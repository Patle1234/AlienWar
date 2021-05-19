package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

public class Main extends Application {
    static AnchorPane root;
    static ArrayList<AnchorPane> pane= new ArrayList();
    static int currentIndex=0;
    Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
    double width=screenSize.getWidth();
    double height=screenSize.getHeight()-70;

    @Override
    public void start(Stage primaryStage) throws Exception{
        root=FXMLLoader.load(getClass().getResource("anchor.fxml"));

        pane.add(FXMLLoader.load(getClass().getResource("menu.fxml")));//0
        pane.add(FXMLLoader.load(getClass().getResource("game.fxml")));//1
        pane.add(FXMLLoader.load(getClass().getResource("tutorial.fxml")));//2
        pane.add(FXMLLoader.load(getClass().getResource("playerDatabase.fxml")));//3
        pane.add(FXMLLoader.load(getClass().getResource("howToPlay.fxml")));//4

        pane.add(FXMLLoader.load(getClass().getResource("trooperTutorial.fxml")));//5
        pane.add(FXMLLoader.load(getClass().getResource("eggTutorial.fxml")));//6
        pane.add(FXMLLoader.load(getClass().getResource("healerTutorial.fxml")));//7
        pane.add(FXMLLoader.load(getClass().getResource("scientistTutorial.fxml")));//8
        pane.add(FXMLLoader.load(getClass().getResource("builderTutorial.fxml")));//9
        pane.add(FXMLLoader.load(getClass().getResource("unknownTutorial.fxml")));//10
        pane.add(FXMLLoader.load(getClass().getResource("leaderTutorial.fxml")));//11
        pane.add(FXMLLoader.load(getClass().getResource("pieceTutorial.fxml")));//12


        root.getChildren().add(pane.get(0));
        primaryStage.setTitle("Alien Wars");
        primaryStage.setScene(new Scene(root, 952.0, 719.0));

        primaryStage.show();
    }

    public static void setPane(int index){
        root.getChildren().remove(pane.get(currentIndex));
        root.getChildren().add(pane.get(index));
        currentIndex=index;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
