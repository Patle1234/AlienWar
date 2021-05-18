package sample;

import connectivity.ConnectionClass;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import sample.Game.Wall;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class AddPlayer {
    @FXML
    TextField playerNameEnter;
    @FXML
    Button selectNameBtn,deleteUserBtn,addOptionBtn,statBtn;
    @FXML
    Label winLbl,lossLbl,rankLbl;
    @FXML
    ListView displayPlayers;
    public static ArrayList<String> allPlayers= new ArrayList();


    public void initialize() {
        populateList();
        for(int i=0;i<displayPlayers.getItems().size();i++) {
            allPlayers.add(displayPlayers.getItems().get(i).toString());
        }
    }
    @FXML
    private void allowAddUser() {
        playerNameEnter.clear();
        playerNameEnter.setVisible(true);
        selectNameBtn.setVisible(true);
        rankLbl.setVisible(false);
        winLbl.setVisible(false);
        lossLbl.setVisible(false);
    }
    @FXML
    private void showStats() {//gets the stats for a player from the database and displays it
        playerNameEnter.setVisible(false);
        selectNameBtn.setVisible(false);
        rankLbl.setVisible(true);
        winLbl.setVisible(true);
        lossLbl.setVisible(true);
        String name = "";
        for (int i = 0; i < displayPlayers.getSelectionModel().getSelectedItem().toString().length(); i++) {
            if (displayPlayers.getSelectionModel().getSelectedItem().toString().substring(i, i + 1).equals("|")) {
                name = displayPlayers.getSelectionModel().getSelectedItem().toString().substring(1, i - 1);
            }
        }
        ConnectionClass connectNow = new ConnectionClass();
        Connection connectDB = connectNow.getConnection();
        try {
            String connectQuery = "SELECT UserName, Losses, Wins,RankScore FROM playerinfo";
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            while (queryOutput.next()) {
                if (queryOutput.getString("UserName").equals(name)) {
                    winLbl.setText("Wins: " + queryOutput.getInt("Wins"));
                    lossLbl.setText("Losses: " + queryOutput.getInt("Losses"));
                    rankLbl.setText("RankScore: " + queryOutput.getInt("RankScore"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void addUser() {//adds a user to the database
        allPlayers.add(playerNameEnter.getText());
        String connectQuery="INSERT INTO `playerinfo` (`UserName`) VALUES ('"+playerNameEnter.getText()+"')";
        ConnectionClass connectNow= new ConnectionClass();
        Connection connectDB=connectNow.getConnection();
        try{
            Statement statement=connectDB.createStatement();
            statement.executeUpdate(connectQuery);
        }catch(Exception e){
            e.printStackTrace();
        }
        playerNameEnter.setVisible(false);
        selectNameBtn.setVisible(false);
        populateList();
    }

    @FXML
    private void deleteUser(){//deletes a user from the database
        rankLbl.setVisible(false);
        winLbl.setVisible(false);
        lossLbl.setVisible(false);
        String selected="";
        for(int i=0;i<displayPlayers.getSelectionModel().getSelectedItem().toString().length();i++){
            if(displayPlayers.getSelectionModel().getSelectedItem().toString().substring(i,i+1).equals("|")){
                selected=displayPlayers.getSelectionModel().getSelectedItem().toString().substring(1,i-1);
            }
        }
        String connectQuery="DELETE FROM playerinfo WHERE UserName = '"+selected+"'";
        ConnectionClass connectNow= new ConnectionClass();
        Connection connectDB=connectNow.getConnection();
        try{
            Statement statement=connectDB.createStatement();
           statement.executeUpdate(connectQuery);
        }catch(Exception e){
            e.printStackTrace();
        }
        populateList();
    }

    @FXML
    private void populateList(){//gets all the names from the database and adds it to a listview
        displayPlayers.getItems().clear();
        String connectQuery="SELECT UserName, RankScore FROM playerinfo";

        ConnectionClass connectNow= new ConnectionClass();
         Connection connectDB=connectNow.getConnection();
        try{
            Statement statement=connectDB.createStatement();
            ResultSet queryOutput=statement.executeQuery(connectQuery);
            while (queryOutput.next()){
                displayPlayers.getItems().add("("+queryOutput.getString("UserName")+" | "+(queryOutput.getString("RankScore"))+")");//display each villain from that tier
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public static ArrayList<String> getPlayerNames(){
        return allPlayers;
    }
    @FXML
    private void showMenu(){
        Main.setPane(0);
    }

}
