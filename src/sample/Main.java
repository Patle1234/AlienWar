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
        pane.add(FXMLLoader.load(getClass().getResource("menu.fxml")));
        pane.add(FXMLLoader.load(getClass().getResource("game.fxml")));
        pane.add(FXMLLoader.load(getClass().getResource("tutorial.fxml")));
        pane.add(FXMLLoader.load(getClass().getResource("playerDatabase.fxml")));



        root.getChildren().add(pane.get(0));
        primaryStage.setTitle("Alien Wars");
        primaryStage.setScene(new Scene(root, width, height));

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
