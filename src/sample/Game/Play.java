package sample.Game;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import connectivity.ConnectionClass;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import sample.AddPlayer;
import sample.Game.Pieces.*;
import sample.Main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class Play {
    //Vars
    private int x=10;
    private int y=10;
    public int numPlayers=1;
    boolean turn;
    int yellowEggFood=0;
    int orangeEggFood=0;
    String currentPiece;
    int currentPieceX;
    int currentPieceY;
    boolean other = true;
    int col;
    int row;
    Player player1;
    Player player2;
    //Arrays
    Button[][] board = new Button[x][y];
    int[][] gameGrid = new int[x][y];//0 is white, 1 is black, 2 is possible move, 3 is egg, 4 is trooper, 5 is UNKNOWN, 6 is Builder,7 is scientist, 8 is healer, 9 is leader, 10 is the possible caputures,11 is possible walls, 12 is a wall, 13 is possible portal, 14 is start portal, 15 is end portal, 16 is possible heal locations
    Piece[][] pieceGrid = new Piece[x][y];
    private ArrayList<Piece> orangePieces= new ArrayList();
    private ArrayList<Piece> yellowPieces= new ArrayList();
    private ArrayList<Piece> capturedOrangePieces= new ArrayList();
    private ArrayList<Piece> capturedYellowPieces= new ArrayList();
    private ArrayList<Wall> wallArray= new ArrayList();
    private ArrayList<Portal> portalArray= new ArrayList();
    private ImageView[][] pieceImg = new ImageView [x][y];
    //FXML
    @FXML
    GridPane gPane;
    @FXML
    Label  p1Name,p2Name, turnLbl, lbl1,lbl2, winnerLbl;
    @FXML
    Button superSelectBtn1, superSelectBtn2,selectBtn1,selectBtn2,playerOptionBtn,startGameBtn;
    @FXML
    ChoiceBox superChoice1,superChoice2,chooseP1,chooseP2;

    //Sets up the game
    public void initialize() {
        //Creates board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Button();
                board[i][j].setPrefHeight(60);
                board[i][j].setMinSize(0, 0);
                board[i][j].setPrefWidth(60);
                gPane.add(board[i][j], j, i);
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j].setOnMouseClicked(z);
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {

                if (other) {
                    board[i][j].setStyle("-fx-background-color:#FFFFFF");
                    gameGrid[i][j] = 0;
                } else {
                    board[i][j].setStyle("-fx-background-color:#000000");
                    gameGrid[i][j] = 1;
                }
                other = !other;

            }
            other = !other;
        }
        //Turn
        if(myRand(0,1)==0){
            turn=false;
        }else{
            turn=true;
        }
        if(turn){
            turnLbl.setText("turn: "+2);
        }else{
            turnLbl.setText("turn: "+1);
        }
        //Create Pieces
        for (int i = 0; i < pieceImg.length; i++) {
            for (int j = 0; j < pieceImg[0].length; j++) {
                pieceImg[i][j] = new ImageView();
                pieceImg[i][j].setFitHeight(60);
                pieceImg[i][j].setFitWidth(60);
                gPane.add(pieceImg[i][j], j, i);
                if(i==1){
                    yellowPieces.add(pieceGrid[i][j]=new AlienTrooper(i,j,true));
                    gameGrid[i][j]=4;
                }else if (i == 0 && j == 4) {
                    yellowPieces.add(pieceGrid[i][j]=new AlienEgg(i, j,true));
                    gameGrid[i][j]=3;
                }else if (i == 0 && j == 5) {
                    yellowPieces.add(pieceGrid[i][j]=new AlienHealer(i, j,true));
                    gameGrid[i][j]=8;
                }else if((i==0 &&j==9) || (i==0&&j==0)){
                    yellowPieces.add(pieceGrid[i][j]=new AlienBuilder(i, j,true));
                    gameGrid[i][j]=6;
                } else if((i==0 &&j==3) || (i==0&&j==6)){
                    yellowPieces.add(pieceGrid[i][j]=new UNKNOWN(i, j,true));
                    gameGrid[i][j]=5;
                }else if((i==0 &&j==2) || (i==0&&j==7)){
                    yellowPieces.add(pieceGrid[i][j]=new AlienScientist(i, j,true));
                    gameGrid[i][j]=7;
                }else if(i==8){
                    orangePieces.add(pieceGrid[i][j]=new AlienTrooper(i,j,false));
                    gameGrid[i][j]=4;
                 } else if (i == 9 && j == 4) {
                    orangePieces.add(pieceGrid[i][j]=new AlienEgg(i, j,false));
                    gameGrid[i][j]=3;
                }else if (i == 9 && j == 5) {
                    orangePieces.add(pieceGrid[i][j]=new AlienHealer(i, j,false));
                    gameGrid[i][j]=8;
                }else if((i==9 &&j==9) || (i==9&&j==0)){
                    orangePieces.add(pieceGrid[i][j]=new AlienBuilder(i, j,false));
                    gameGrid[i][j]=6;
                }else if((i==9 &&j==3) || (i==9&&j==6)){
                    orangePieces.add(pieceGrid[i][j]=new UNKNOWN(i, j,false));
                    gameGrid[i][j]=5;
                }else if((i==9 &&j==2) || (i==9&&j==7)){
                    orangePieces.add(pieceGrid[i][j]=new AlienScientist(i, j,false));
                    gameGrid[i][j]=7;
                }
                updateScreen();
            }
        }
        gPane.setVisible(true);
        winnerLbl.setVisible(false);
        gPane.setDisable(true);
        chooseP1.setVisible(true);
        chooseP2.setVisible(false);
        selectBtn2.setVisible(false);
        playerOptionBtn.setVisible(true);
        selectBtn1.setVisible(true);
        superSelectBtn2.setVisible(false);
        superSelectBtn1.setVisible(false);
        superChoice1.setVisible(false);
        superChoice2.setVisible(false);
        startGameBtn.setVisible(true);
        turnLbl.setVisible(false);
        lbl1.setVisible(false);
        lbl2.setVisible(false);
    }

    //Check for all the possible thing that happen when a player clicks on the board
    EventHandler z = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent t) {
            row = GridPane.getRowIndex(((Button) t.getSource()));//row of the image you clicked on
            col = GridPane.getColumnIndex(((Button) t.getSource()));//coloum of the image clicked on
            int currentX;
            int currentY;
            for (int i = 0; i < gameGrid.length; i++){
                for (int j = 0; j < gameGrid[0].length; j++){
                    if (gameGrid[row][col] != 0 && gameGrid[row][col] != 1 && gameGrid[row][col] != 2 && gameGrid[row][col] != 10&& gameGrid[row][col] != 11&& gameGrid[row][col] != 12&& gameGrid[row][col] != 13&& gameGrid[row][col] != 14&& gameGrid[row][col] !=16){//if the player clicked on a piece
                        if(!(pieceGrid[i][j]==null)) {
                            if (pieceGrid[i][j].getSide() == turn) {
                                if (i == row && j == col) {
                                    if (gameGrid[i][j] == gameGrid[row][col]){
                                        clearPossible();
                                        if (gameGrid[row][col] == 3) {//when click on egg
                                            currentPiece = "Egg";
                                            pieceGrid[row][col].possibleMove(gameGrid,pieceGrid, 1);
                                            pieceGrid[i][j].showPossible(gameGrid);
                                        } else if (gameGrid[row][col] == 4) {//when click on trooper
                                            currentPiece = "Trooper";
                                            if (pieceGrid[row][col].getSide()) {
                                                pieceGrid[row][col].possibleMove(gameGrid,pieceGrid, 1);//CHANGE THE DIRECTION OF THE PIECE WHEN DO BLACK
                                            } else {
                                                pieceGrid[row][col].possibleMove(gameGrid,pieceGrid, -1);//CHANGE THE DIRECTION OF THE PIECE WHEN DO BLACK
                                            }
                                            pieceGrid[i][j].showPossible(gameGrid);
                                        } else if (gameGrid[row][col] == 5) {//when click on UNKNOWN
                                            currentPiece = "UNKNOWN";
                                            pieceGrid[row][col].possibleMove(gameGrid,pieceGrid, 10);//CHANGE THE DIRECTION OF THE PIECE WHEN DO BLACK
                                            pieceGrid[i][j].showPossible(gameGrid);
                                                if (turn){
                                                    superChoice2.getItems().clear();
                                                    lbl2.setText("Pick A Movement Choice");
                                                    superChoice2.getItems().add("UNKNOWN");
                                                    superChoice2.getItems().add("Scientist");
                                                    superChoice2.getItems().add("Egg");
                                                    superChoice2.getItems().add("Builder");
                                                    superSelectBtn2.setText("Select");
                                                } else {
                                                    superChoice1.getItems().clear();
                                                    lbl1.setText("Pick A Movement Choice");
                                                    superChoice1.getItems().add("UNKNOWN");
                                                    superChoice1.getItems().add("Scientist");
                                                    superChoice1.getItems().add("Egg");
                                                    superChoice1.getItems().add("Builder");
                                                    superSelectBtn1.setText("Select");
                                                }
                                        } else if (gameGrid[row][col] == 6) {//when click on builder
                                            currentPiece = "Builder";
                                            pieceGrid[row][col].possibleMove(gameGrid,pieceGrid, 10);//CHANGE THE DIRECTION OF THE PIECE WHEN DO BLACK
                                            pieceGrid[i][j].showPossible(gameGrid);
                                            if(pieceGrid[i][j].canBuild()) {
                                                if (turn) {
                                                    lbl2.setText("Build A Wall");
                                                    superChoice2.setVisible(false);
                                                    superSelectBtn2.setText("Build Wall");

                                                } else {
                                                    lbl1.setText("Build A Wall");
                                                    superChoice1.setVisible(false);
                                                    superSelectBtn1.setText("Build Wall");
                                                }
                                            }
                                        }else if(gameGrid[row][col] == 7) {//when click on scientist
                                            currentPiece = "Scientist";
                                            pieceGrid[row][col].possibleMove(gameGrid, pieceGrid,2);
                                            pieceGrid[i][j].showPossible(gameGrid);
                                            if(pieceGrid[row][col].ifCanPortal()){
                                                if(turn){
                                                    lbl2.setText("Make A Portal");
                                                    superChoice2.setVisible(false);
                                                    superSelectBtn2.setText("Set Start Portal");
                                                }else {
                                                    lbl1.setText("Make A Portal");
                                                    superChoice1.setVisible(false);
                                                    superSelectBtn1.setText("Set Start Portal");
                                                }
                                            }
                                        } else if (gameGrid[row][col] == 8) {//when click on healer
                                            currentPiece = "Healer";
                                            pieceGrid[row][col].possibleMove(gameGrid, pieceGrid,1);
                                            pieceGrid[i][j].showPossible(gameGrid);
                                            boolean addTrooper=true;
                                            boolean addUNKNOWN=true;
                                            boolean addBuilder=true;
                                            boolean addScientist=true;
                                            if(pieceGrid[row][col].ifCanHeal()) {
                                                if (turn) {
                                                    superChoice2.getItems().clear();
                                                    lbl2.setText("You can heal a piece");
                                                    for (int a = 0; a < capturedYellowPieces.size(); a++) {
                                                        if (capturedYellowPieces.get(a).getType() == 4 && addTrooper) {
                                                            superChoice2.getItems().add("Trooper");
                                                            addTrooper = false;
                                                        } else if (capturedYellowPieces.get(a).getType() == 5 && addUNKNOWN) {
                                                            superChoice2.getItems().add("UNKNOWN");
                                                            addUNKNOWN = false;
                                                        } else if (capturedYellowPieces.get(a).getType() == 6 && addBuilder) {
                                                            superChoice2.getItems().add("Builder");
                                                            addBuilder = false;
                                                        } else if (capturedYellowPieces.get(a).getType() == 7 && addScientist) {
                                                            superChoice2.getItems().add("Scientist");
                                                            addScientist = false;
                                                        }
                                                    }
                                                } else {
                                                    superChoice1.getItems().clear();
                                                    lbl1.setText("You can heal a piece");
                                                    for (int a = 0; a < capturedOrangePieces.size(); a++) {
                                                        if (capturedOrangePieces.get(a).getType() == 4 && addTrooper) {
                                                            superChoice1.getItems().add("Trooper");
                                                            addTrooper = false;
                                                        } else if (capturedOrangePieces.get(a).getType() == 5 && addUNKNOWN) {
                                                            superChoice1.getItems().add("UNKNOWN");
                                                            addUNKNOWN = false;
                                                        } else if (capturedOrangePieces.get(a).getType() == 6 && addBuilder) {
                                                            superChoice1.getItems().add("Builder");
                                                            addBuilder = false;
                                                        } else if (capturedOrangePieces.get(a).getType() == 7 && addScientist) {
                                                            superChoice1.getItems().add("Scientist");
                                                            addScientist = false;
                                                        }
                                                    }
                                                }
                                            }
                                        } else if (gameGrid[row][col] == 9) {//when click on leader
                                            currentPiece = "Leader";
                                            pieceGrid[row][col].possibleMove(gameGrid,pieceGrid, 10);
                                            pieceGrid[i][j].showPossible(gameGrid);
                                        }
                                        currentPieceX = row;
                                        currentPieceY = col;
                                    }
                                }
                            }
                        }
                        }else if (gameGrid[row][col] == 2){//if the player clicked on a possible move location
                             currentX=row;
                             currentY=col;
                             //check if piece steps on portal
                            for(int a=0;a<portalArray.size();a+=2){
                                if(portalArray.get(a).getX()==row && portalArray.get(a).getY()==col){
                                    currentX=portalArray.get(a+1).getX();
                                    currentY=portalArray.get(a+1).getY();
                                    gameGrid[portalArray.get(i).getX()][portalArray.get(i).getY()]=2;
                                    portalArray.remove(a);
                                    portalArray.remove(a);
                                }
                            }
                                if (currentPiece.equals("Egg")) {//if moving a egg
                                    gameGrid[currentX][currentY] = 3;
                                } else if (currentPiece.equals("Trooper")) {//if moving a trooper
                                    if(pieceGrid[currentPieceX][currentPieceY].ifAtEnd()){//if reaches the end, then transforms to another type
                                        superChoice2.getItems().clear();
                                        superChoice1.getItems().clear();
                                        if (turn) {
                                            lbl2.setText("Pick A Character");
                                            superChoice2.getItems().add("UNKNOWN");
                                            superChoice2.getItems().add("Scientist");
                                            superChoice2.getItems().add("Builder");
                                            superChoice2.getItems().add("Healer");
                                        }else{
                                            lbl1.setText("Pick A Character");
                                            superChoice1.getItems().add("UNKNOWN");
                                            superChoice1.getItems().add("Scientist");
                                            superChoice1.getItems().add("Builder");
                                            superChoice1.getItems().add("Healer");
                                        }
                                        break;
                                    }
                                    gameGrid[currentX][currentY] = 4;
                                } else if (currentPiece.equals("UNKNOWN")) {//if moving UNKNOWN
                                    gameGrid[currentX][currentY] = 5;
                                } else if (currentPiece.equals("Builder")) {//if moving builder
                                    gameGrid[currentX][currentY] = 6;
                                } else if (currentPiece.equals("Scientist")) {//if moving scientist
                                    gameGrid[currentX][currentY] = 7;
                                } else if (currentPiece.equals("Healer")) {//if moving healer
                                    gameGrid[currentX][currentY] = 8;
                                } else if (currentPiece.equals("Leader")) {//if moving leader
                                    gameGrid[currentX][currentY] = 9;
                                }
                                pieceGrid[currentPieceX][currentPieceY].setSpot(currentX, currentY);
                                pieceGrid[currentX][currentY] = pieceGrid[currentPieceX][currentPieceY];
                                if ((currentPieceX % 2 == 0 && !(currentPieceY % 2 == 0)) || (!(currentPieceX % 2 == 0) && currentPieceY % 2 == 0)) {
                                    gameGrid[currentPieceX][currentPieceY] = 1;
                                } else {
                                    gameGrid[currentPieceX][currentPieceY] = 0;
                                }
                                pieceGrid[currentPieceX][currentPieceY] = null;
                                currentPieceX = currentX;
                                currentPieceY = currentY;
                                switchTurn();
                                clearPossible();
                    }else if(gameGrid[row][col]==10){//if player wants to capture
                        //moves the captured piece into captured array
                        //checks if egg can transform into the leader
                        if (pieceGrid[row][col].getSide()) {
                            yellowPieces.remove(pieceGrid[row][col]);
                            capturedYellowPieces.add(pieceGrid[row][col]);
                            if(pieceGrid[row][col].getType()==5 ||pieceGrid[row][col].getType()==6||pieceGrid[row][col].getType()==7||pieceGrid[row][col].getType()==8) {
                                yellowEggFood++;
                                if(yellowEggFood==4){
                                    for(int a=0;a<board.length;a++){
                                        for(int b=0; b< board.length;b++){
                                            if(!(pieceGrid[a][b]==null) &&pieceGrid[a][b].getType()==3 && !(pieceGrid[a][b].getSide())){
                                                gameGrid[a][b]=9;
                                                pieceGrid[a][b]=new AlienLeader(a, b,false);
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            orangePieces.remove(pieceGrid[row][col]);
                            capturedOrangePieces.add(pieceGrid[row][col]);
                            if(pieceGrid[row][col].getType()==5 ||pieceGrid[row][col].getType()==6||pieceGrid[row][col].getType()==7||pieceGrid[row][col].getType()==8){
                                orangeEggFood++;
                                if(orangeEggFood==4){
                                    for(int a=0;a<board.length;a++){
                                        for(int b=0; b< board.length;b++){
                                            if(!(pieceGrid[a][b]==null) &&pieceGrid[a][b].getType()==3 && pieceGrid[a][b].getSide() ){
                                                gameGrid[a][b]=9;
                                                pieceGrid[a][b]=new AlienLeader(a, b,true);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        //check what type of piece it is an move it
                        if (currentPiece.equals("Egg")) {
                            gameGrid[row][col] = 3;
                        } else if (currentPiece.equals("Trooper")) {
                            gameGrid[row][col] = 4;
                        } else if (currentPiece.equals("UNKNOWN")) {
                            gameGrid[row][col] = 5;
                        } else if (currentPiece.equals("Builder")) {
                            gameGrid[row][col] = 6;
                        } else if (currentPiece.equals("Scientist")) {
                            gameGrid[row][col] = 7;
                        } else if (currentPiece.equals("Healer")) {
                            gameGrid[row][col] = 8;
                        } else if (currentPiece.equals("Leader")) {
                            gameGrid[row][col] = 9;
                        }
                        pieceGrid[currentPieceX][currentPieceY].setSpot(row, col);

                        if ((currentPieceX % 2 == 0 && !(currentPieceY % 2 == 0)) || (!(currentPieceX % 2 == 0) && currentPieceY % 2 == 0)) {
                            gameGrid[currentPieceX][currentPieceY] = 1;
                        } else {
                            gameGrid[currentPieceX][currentPieceY] = 0;
                        }
                        pieceGrid[row][col]=pieceGrid[currentPieceX][currentPieceY];
                        pieceGrid[currentPieceX][currentPieceY] = null;
                        currentPieceX = row;
                        currentPieceY = col;
                        switchTurn();
                        clearPossible();
                    }else if(gameGrid[row][col]==11){//if player clicks on a possible wall, places a wall
                        gameGrid[row][col] = 12;
                        wallArray.add(new Wall(row, col));
                        switchTurn();
                        clearPossible();
                    }else if(gameGrid[row][col]==13){//if player click on a possible portal, creates either a start or end portal
                        if(portalArray.size()%2==0){
                            gameGrid[row][col]=14;
                            portalArray.add(new Portal(row,col,false));
                            if(turn){
                                superSelectBtn2.setText("Select End Portal");
                                superChoice2.setVisible(false);
                            }else {
                                superSelectBtn1.setText("Select End Portal");
                                superChoice1.setVisible(false);
                            }
                        }else{
                            gameGrid[row][col]=15;
                            portalArray.add(new Portal(row,col,true,portalArray.get(portalArray.size()-1)));
                            switchTurn();
                        }
                        clearPossible();
                    }else if(gameGrid[row][col]==16){//places the piece being healed at specified location
                        gameGrid[row][col]=pieceGrid[currentPieceX][currentPieceY].getTypeHeal();
                        if(turn){
                            for(int a=0;a<capturedYellowPieces.size();a++){
                                if(capturedYellowPieces.get(a).getType()==pieceGrid[currentPieceX][currentPieceY].getTypeHeal()){
                                    pieceGrid[row][col]=capturedYellowPieces.get(a);
                                    yellowPieces.add(capturedYellowPieces.get(a));
                                    capturedYellowPieces.remove(capturedYellowPieces.get(a));
                                }
                            }
                        }else{
                            for(int a=0;a<capturedOrangePieces.size();a++){
                                if(capturedOrangePieces.get(a).getType()==pieceGrid[currentPieceX][currentPieceY].getTypeHeal()){
                                    pieceGrid[row][col]=capturedOrangePieces.get(a);
                                    orangePieces.add(capturedOrangePieces.get(a));
                                    capturedOrangePieces.remove(capturedOrangePieces.get(a));
                                }
                            }
                        }
                        pieceGrid[row][col].setSpot(row,col);
                        switchTurn();
                        clearPossible();
                    }
                }
            }
            updateScreen();
            if(turn &&numPlayers==1){
                computerPlayer();
            }
        }
    };

    //used to switch turn
    private void switchTurn(){
        turn=!turn;
        if(turn){
            turnLbl.setText("Turn: 2");
            if(numPlayers==2) {
                lbl1.setText("");
                superChoice1.getItems().clear();
                superSelectBtn1.setText("");
                superChoice1.setVisible(false);
                superSelectBtn1.setVisible(false);
                superChoice2.setVisible(true);
                superSelectBtn2.setVisible(true);
            }
        }else{
            turnLbl.setText("Turn: 1");
            lbl2.setText("");
            superChoice2.getItems().clear();
            superSelectBtn2.setText("");
            superChoice2.setVisible(false);
            superSelectBtn2.setVisible(false);
            superChoice1.setVisible(true);
            superSelectBtn1.setVisible(true);
        }
        //check if wall or portal need to be taken off the board
        if(wallArray.size()>0){
            for(int i=0;i<wallArray.size();i++){
                wallArray.get(i).addTurn();
                if(wallArray.get(i).getNumTurns()==6){
                    gameGrid[wallArray.get(i).getX()][wallArray.get(i).getY()]=2;
                    wallArray.remove(i);
                    clearPossible();
                }
            }
        }
        if(portalArray.size()>0){
            for(int i=0;i<portalArray.size()-1;i++){
                portalArray.get(i).addTurn();
                if(!portalArray.get(i).ifPortal()){
                    gameGrid[portalArray.get(i).getX()][portalArray.get(i).getY()]=2;
                    gameGrid[portalArray.get(i+1).getX()][portalArray.get(i+1).getY()]=2;
                    portalArray.remove(i);
                    portalArray.remove(i);
                }
            }
        }
        //checks for end game
        if(yellowPieces.size()==0) {
            endGame(1);

        }else if(orangePieces.size()==0){
            endGame(2);
        }
    }

    //clears all of the spots on the board that are used for player input
    private void clearPossible(){
        boolean ifPortal=false;
        for (int i = 0; i < gameGrid.length; i++) {
            for (int j = 0; j < gameGrid[0].length; j++) {
                if (gameGrid[i][j] == 2) {
                    for(int a=0;a<portalArray.size();a+=2){
                        if(portalArray.get(a).getX()==i && portalArray.get(a).getY()==j){
                            ifPortal=true;
                            break;
                        }
                    }
                    if(ifPortal){
                        gameGrid[i][j]=14;
                        ifPortal=false;
                    }else{
                        if((j%2==0 && !(i%2==0)) ||(!(j%2==0) && i%2==0)){
                            gameGrid[i][j] =1;
                        } else{
                            gameGrid[i][j] =0;
                        }
                    }
                }else if(gameGrid[i][j]==10){
                    if(!(pieceGrid[i][j]==null)){
                        gameGrid[i][j]=pieceGrid[i][j].getType();
                    }
                }else if(gameGrid[i][j]==11){
                    if((j%2==0 && !(i%2==0)) ||(!(j%2==0) && i%2==0)){
                        gameGrid[i][j] =1;
                    } else{
                        gameGrid[i][j] =0;
                    }
                }else if(gameGrid[i][j]==13){
                    if((j%2==0 && !(i%2==0)) ||(!(j%2==0) && i%2==0)){
                        gameGrid[i][j] =1;
                    } else{
                        gameGrid[i][j] =0;
                    }
                }else if(gameGrid[i][j]==16){
                    if((j%2==0 && !(i%2==0)) ||(!(j%2==0) && i%2==0)){
                        gameGrid[i][j] =1;
                    } else{
                        gameGrid[i][j] =0;
                    }
                }
                other=!other;
            }
            other = !other;
        }

    }

    //End game code
    private void endGame(int winner){
        if(winner==1){
            winnerLbl.setText("Winner: "+player1.getUserName());
            if(numPlayers==1){
                updatePlayerInfo(player1.getUserName(),"win",player1.getWins()+1);
                updatePlayerInfo(player1.getUserName(),"RS", player1.getRankScore()+10);
            }else{
                updatePlayerInfo(player1.getUserName(),"win",player1.getWins()+1);
                updatePlayerInfo(player2.getUserName(),"lose",player2.getLosses()+1);
                updatePlayerInfo(player1.getUserName(),"RS", player1.getRankScore()+10);
                updatePlayerInfo(player2.getUserName(),"RS", player2.getRankScore()-10);
            }
        }else if(winner==2){
            if(numPlayers==1){
                winnerLbl.setText("Winner: Computer");
                updatePlayerInfo(player1.getUserName(),"Losses",player1.getLosses()+1);
            }else {
                winnerLbl.setText("Winner: " + player2.getUserName());
                updatePlayerInfo(player2.getUserName(),"win",player2.getWins()+1);
                updatePlayerInfo(player1.getUserName(),"lose",player1.getLosses()+1);
                updatePlayerInfo(player2.getUserName(),"RS", player2.getRankScore()+10);
                updatePlayerInfo(player1.getUserName(),"RS", player1.getRankScore()-10);
            }
        }
        //clear arrays
        capturedOrangePieces.clear();
        capturedYellowPieces.clear();
        yellowPieces.clear();
        orangePieces.clear();
        portalArray.clear();
        wallArray.clear();
        player2=null;
        player1=null;
        for(int i=0;i<pieceGrid.length;i++){
            for(int j=0;j<pieceGrid[0].length;j++){
                pieceGrid[i][j]=null;
            }
        }
        for(int i=0;i<gameGrid.length;i++){
            for(int j=0;j<gameGrid[0].length;j++){
                gameGrid[i][j]=0;
            }
        }

        //reset all containers/controls
        winnerLbl.setVisible(true);
        gPane.setVisible(false);
        chooseP1.setVisible(false);
        p1Name.setVisible(false);
        p1Name.setText("");
        p2Name.setVisible(false);
        p1Name.setText("");
        chooseP1.getItems().clear();
        chooseP2.getItems().clear();
        chooseP2.setVisible(false);
        playerOptionBtn.setVisible(false);
        selectBtn1.setVisible(false);
        selectBtn1.setText("");
        selectBtn2.setText("");
        selectBtn2.setVisible(false);
        superSelectBtn2.setVisible(false);
        superSelectBtn2.setText("");
        superSelectBtn1.setText("");
        superSelectBtn1.setVisible(false);
        superChoice1.setVisible(false);
        superChoice2.setVisible(false);
        superChoice2.getItems().clear();
        superChoice1.getItems().clear();
        turnLbl.setVisible(false);
        lbl1.setVisible(false);
        lbl1.setText("");
        lbl2.setText("");
        lbl2.setVisible(false);
    }

    //computer player
    private void computerPlayer() {
        ArrayList<BoardSpot> pSpots = new ArrayList<>();
        ArrayList<BoardSpot> pKills = new ArrayList<>();
        Piece pToMove = null;
        BoardSpot spotToKill = null;
        boolean ifMoved = false;
        int highestValueSave = 0;
        int highestValueKill = 0;
        int randSpots;
        //gets all of the possible moves the computer
        for (int i = 0; i < yellowPieces.size(); i++) {
            if (yellowPieces.get(i).getType() == 3) {
                yellowPieces.get(i).possibleMove(gameGrid, pieceGrid, 1);
            } else if (yellowPieces.get(i).getType() == 4) {
                yellowPieces.get(i).possibleMove(gameGrid, pieceGrid, 1);
            } else if (yellowPieces.get(i).getType() == 5) {
                yellowPieces.get(i).possibleMove(gameGrid, pieceGrid, 10);
            } else if (yellowPieces.get(i).getType() == 6) {
                yellowPieces.get(i).possibleMove(gameGrid, pieceGrid, 10);
            } else if (yellowPieces.get(i).getType() == 7) {
                yellowPieces.get(i).possibleMove(gameGrid, pieceGrid, 2);
            } else if (yellowPieces.get(i).getType() == 8) {
                yellowPieces.get(i).possibleMove(gameGrid, pieceGrid, 1);
            }
            yellowPieces.get(i).showPossible(gameGrid);
        }
        //puts possible moves in pSpots, and possible kills in pKills
        for (int i = 0; i < gameGrid.length; i++) {
            for (int j = 0; j < gameGrid[0].length; j++) {
                if (gameGrid[i][j] == 2) {
                    pSpots.add(new BoardSpot(i, j));
                } else if (gameGrid[i][j] == 10) {
                    pKills.add(new BoardSpot(i, j));
                }
            }
        }
        //killing
        if (pKills.size() > 0) {
            //finds the highest value piece that the computer can caputure
            for (int i = 0; i < orangePieces.size(); i++) {
                for (int j = 0; j < pKills.size(); j++) {
                    if (pKills.get(j).getX() == orangePieces.get(i).getX() && pKills.get(j).getY() == orangePieces.get(i).getY()) {
                        if (orangePieces.get(i).getType() == 9 && 7 > highestValueKill) {
                            System.out.println("killing a leader");
                            spotToKill = pKills.get(j);
                            highestValueKill = 7;
                        } else if (orangePieces.get(i).getType() == 3 && 6 > highestValueKill) {
                            System.out.println("killing a egg");
                            spotToKill = pKills.get(j);
                            highestValueKill = 6;
                        } else if (orangePieces.get(i).getType() == 8 && 5 > highestValueKill) {
                            System.out.println("killing a healer");
                            spotToKill = pKills.get(j);
                            highestValueKill = 5;
                        } else if (orangePieces.get(i).getType() == 6 && 4 > highestValueKill) {
                            System.out.println("killing a builder");
                            spotToKill = pKills.get(j);
                            highestValueKill = 4;
                        } else if (orangePieces.get(i).getType() == 7 && 3 > highestValueKill) {
                            System.out.println("killing a scientist");
                            spotToKill = pKills.get(j);
                            highestValueKill = 3;
                        } else if (orangePieces.get(i).getType() == 5 && 2 > highestValueKill) {
                            System.out.println("killing a UNKNOWN");
                            spotToKill = pKills.get(j);
                            highestValueKill = 2;
                        } else if (orangePieces.get(i).getType() == 4 && 1 > highestValueKill) {
                            System.out.println("killing a trooper");
                            spotToKill = pKills.get(j);
                            highestValueKill = 1;
                        }
                        break;
                    }
                }
            }
            //find the piece the does the caputring
            for (int i = 0; i < yellowPieces.size(); i++) {
                ArrayList<BoardSpot> tempMoves = yellowPieces.get(i).getPossibleCaptures();
                for (int j = 0; j < tempMoves.size(); j++) {
                    if (tempMoves.get(j).getX() == spotToKill.getX() && tempMoves.get(j).getY() == spotToKill.getY()) {
                        pToMove = yellowPieces.get(i);
                        break;
                    }
                }
            }
            //checks if the AlienEgg can turn into a AlienLeader
            for (int i = 0; i < orangePieces.size(); i++) {
                if (orangePieces.get(i).getX() == spotToKill.getX() && orangePieces.get(i).getY() == spotToKill.getY()) {
                    if (orangePieces.get(i).getType() == 5 || orangePieces.get(i).getType() == 6 || orangePieces.get(i).getType() == 7 || orangePieces.get(i).getType() == 8) {
                        yellowEggFood++;
                        if (yellowEggFood == 4) {
                            for (int a = 0; a < board.length; a++) {
                                for (int b = 0; b < board.length; b++) {
                                    if (!(pieceGrid[a][b] == null) && pieceGrid[a][b].getType() == 3 && (pieceGrid[a][b].getSide())) {
                                        gameGrid[a][b] = 9;
                                        pieceGrid[a][b] = new AlienLeader(a, b, true);
                                    }
                                }
                            }
                        }
                    }
                    capturedOrangePieces.add(orangePieces.get(i));
                    orangePieces.remove(orangePieces.get(i));
                }
            }
            pieceGrid[pToMove.getX()][pToMove.getY()] = null;
            if ((pToMove.getX() % 2 == 0 && !(pToMove.getY() % 2 == 0)) || (!(pToMove.getX() % 2 == 0) && pToMove.getY() % 2 == 0)) {
                gameGrid[pToMove.getX()][pToMove.getY()] = 1;
            } else {
                gameGrid[pToMove.getX()][pToMove.getY()] = 0;
            }
            pToMove.setSpot(spotToKill.getX(), spotToKill.getY());
            pieceGrid[pToMove.getX()][pToMove.getY()] = pToMove;
            gameGrid[pToMove.getX()][pToMove.getY()] = pToMove.getType();
        } else {
            double rand = Math.random();
            //saving
            //gets all of the possible moves of the opponent
            ArrayList<Piece> needSave = new ArrayList<>();
            for (int i = 0; i < orangePieces.size(); i++) {
                for (int k = 0; k < orangePieces.size(); k++) {
                    if (orangePieces.get(k).getType() == 3) {
                        orangePieces.get(k).possibleMove(gameGrid, pieceGrid, 1);
                    } else if (orangePieces.get(k).getType() == 4) {
                        orangePieces.get(k).possibleMove(gameGrid, pieceGrid, -1);
                    } else if (orangePieces.get(k).getType() == 5) {
                        orangePieces.get(k).possibleMove(gameGrid, pieceGrid, 10);
                    } else if (orangePieces.get(k).getType() == 6) {
                        orangePieces.get(k).possibleMove(gameGrid, pieceGrid, 10);
                    } else if (orangePieces.get(k).getType() == 7) {
                        orangePieces.get(k).possibleMove(gameGrid, pieceGrid, 2);
                    } else if (orangePieces.get(k).getType() == 8) {
                        orangePieces.get(k).possibleMove(gameGrid, pieceGrid, 1);
                    }
                }
                //finds the highest possible piece that can be saved and puts it in pToMove
                ArrayList<BoardSpot> tempMoves = orangePieces.get(i).getPossibleCaptures();
                for (int j = 0; j < tempMoves.size(); j++) {
                    if (gameGrid[tempMoves.get(j).getX()][tempMoves.get(j).getY()] == 9 && 7 > highestValueSave) {
                        highestValueSave = 7;
                        pToMove = pieceGrid[tempMoves.get(j).getX()][tempMoves.get(j).getY()];
                    } else if (gameGrid[tempMoves.get(j).getX()][tempMoves.get(j).getY()] == 3 && 6 > highestValueSave) {
                        highestValueSave = 6;
                        pToMove = pieceGrid[tempMoves.get(j).getX()][tempMoves.get(j).getY()];
                    } else if (gameGrid[tempMoves.get(j).getX()][tempMoves.get(j).getY()] == 8 && 5 > highestValueSave) {
                        highestValueSave = 5;
                        pToMove = pieceGrid[tempMoves.get(j).getX()][tempMoves.get(j).getY()];
                    } else if (gameGrid[tempMoves.get(j).getX()][tempMoves.get(j).getY()] == 6 && 4 > highestValueSave) {
                        highestValueSave = 4;
                        pToMove = pieceGrid[tempMoves.get(j).getX()][tempMoves.get(j).getY()];
                    } else if (gameGrid[tempMoves.get(j).getX()][tempMoves.get(j).getY()] == 7 && 3 > highestValueSave) {
                        highestValueSave = 3;
                        pToMove = pieceGrid[tempMoves.get(j).getX()][tempMoves.get(j).getY()];
                    } else if (gameGrid[tempMoves.get(j).getX()][tempMoves.get(j).getY()] == 5 && 2 > highestValueSave) {
                        highestValueSave = 2;
                        pToMove = pieceGrid[tempMoves.get(j).getX()][tempMoves.get(j).getY()];
                    } else if (gameGrid[tempMoves.get(j).getX()][tempMoves.get(j).getY()] == 4 && 1 > highestValueSave) {
                        highestValueSave = 1;
                        pToMove = pieceGrid[tempMoves.get(j).getX()][tempMoves.get(j).getY()];
                    }
                    if (!needSave.contains(pieceGrid[tempMoves.get(j).getX()][tempMoves.get(j).getY()])) {
                        needSave.add(pieceGrid[tempMoves.get(j).getX()][tempMoves.get(j).getY()]);
                    }
                    break;
                }
            }
            boolean pieceSaved=false;
            if (needSave.size() > 0) {
                while (!pieceSaved && needSave.size() > 0) {
                    if (!(pToMove == null)) {
                        ArrayList<BoardSpot> x = pToMove.getPossibleMoves();
                        pToMove.showPossible(gameGrid);
                        randSpots = myRand(0, x.size() - 2);
                        if (x.size() > 0) {
                            pieceGrid[pToMove.getX()][pToMove.getY()] = null;
                            if ((pToMove.getX() % 2 == 0 && !(pToMove.getY() % 2 == 0)) || (!(pToMove.getX() % 2 == 0) && pToMove.getY() % 2 == 0)) {
                                gameGrid[pToMove.getX()][pToMove.getY()] = 1;
                            } else {
                                gameGrid[pToMove.getX()][pToMove.getY()] = 0;
                            }
                            pToMove.setSpot(x.get(randSpots).getX(), x.get(randSpots).getY());
                            pieceGrid[pToMove.getX()][pToMove.getY()] = pToMove;
                           // pToMove.showPossible(gameGrid);
                            gameGrid[pToMove.getX()][pToMove.getY()] = pToMove.getType();
                            pieceSaved = true;
                            ifMoved = true;
                        } else if (needSave.size() > 1) {
                            needSave.remove(pToMove);
                            pToMove = needSave.get(0);
                        } else {
                            break;
                        }
                    }
                }
                needSave.clear();
            } else if (rand > .5) {//supers
                //getting all of the peices that use a turn to move and put in an array
                ArrayList<Piece> tempArray = new ArrayList<>();
                for (int i = 0; i < yellowPieces.size(); i++) {
                    if (yellowPieces.get(i).getType() == 5 || yellowPieces.get(i).getType() == 6 || yellowPieces.get(i).getType() == 7 || yellowPieces.get(i).getType() == 8) {
                        tempArray.add(yellowPieces.get(i));
                    }
                }
                if (tempArray.size() > 0) {
                    int randSpot = myRand(0, tempArray.size() - 1);
                    pToMove = tempArray.get(randSpot);
                    if (pToMove.getType() == 6) {//builder super
                        currentPiece = "Builder";
                        if (pToMove.canBuild()) {
                            pToMove.possibleWalls(gameGrid, pieceGrid);
                            ArrayList<BoardSpot> tempWalls = pToMove.getPossibleWalls();
                            int randWall = myRand(0, tempWalls.size() - 1);
                            pToMove.setIfBuild(false);
                            gameGrid[tempWalls.get(randWall).getX()][tempWalls.get(randWall).getY()] = 12;
                            wallArray.add(new Wall(tempWalls.get(randWall).getX(), tempWalls.get(randWall).getY()));
                            ifMoved = true;
                        }
                    } else if (pToMove.getType() == 7) {//scientist super
                        if (pToMove.ifCanPortal()) {
                            pToMove.possiblePortal(gameGrid, pieceGrid);
                            ArrayList<BoardSpot> tempPortals = pToMove.getPossiblePortals();
                            int randStartPortal = myRand(0, tempPortals.size() - 1);
                            int randEndPortal = myRand(0, tempPortals.size() - 1);
                            gameGrid[tempPortals.get(randStartPortal).getX()][tempPortals.get(randStartPortal).getY()] = 14;
                            portalArray.add(new Portal(tempPortals.get(randStartPortal).getX(), tempPortals.get(randStartPortal).getY(), false));
                            gameGrid[tempPortals.get(randEndPortal).getX()][tempPortals.get(randEndPortal).getY()] = 15;
                            portalArray.add(new Portal(tempPortals.get(randEndPortal).getX(), tempPortals.get(randEndPortal).getY(), true, portalArray.get(portalArray.size() - 1)));
                            ifMoved = true;
                        }
                    } else if (pToMove.getType() == 5) {//UNKNOWN super
                        int randMovement = myRand(0, 3);
                        if (randMovement == 0) {
                            pieceGrid[currentPieceX][currentPieceY].setCurrentMovement("UNKNOWN");
                        } else if (randMovement == 1) {
                            pieceGrid[currentPieceX][currentPieceY].setCurrentMovement("Builder");
                        } else if (randMovement == 2) {
                            pieceGrid[currentPieceX][currentPieceY].setCurrentMovement("Scientist");
                        } else if (randMovement == 3) {
                            pieceGrid[currentPieceX][currentPieceY].setCurrentMovement("Egg");
                        }
                    } else if (pToMove.getType() == 8) {//healer
                        if (pToMove.ifCanHeal() && capturedYellowPieces.size() > 0) {
                            int highestValueHeal = 0;
                            for (int i = 0; i < capturedYellowPieces.size(); i++) {
                                if (capturedYellowPieces.get(i).getType() == 6 && 6 > highestValueHeal) {
                                    highestValueHeal = 6;
                                } else if (capturedYellowPieces.get(i).getType() == 7 && 7 > highestValueHeal) {
                                    highestValueHeal = 7;
                                } else if (capturedYellowPieces.get(i).getType() == 5 && 5 > highestValueHeal) {
                                    highestValueHeal = 5;
                                } else if (capturedYellowPieces.get(i).getType() == 4 && 4 > highestValueHeal) {
                                    highestValueHeal = 4;
                                }
                            }
                            pToMove.possibleHealLocations(gameGrid, pieceGrid);
                            ArrayList<BoardSpot> tempHeals = pToMove.getPossibleHeals();
                            int randHealSpot = myRand(0, tempHeals.size() - 1);
                            gameGrid[tempHeals.get(randHealSpot).getX()][tempHeals.get(randHealSpot).getY()] = highestValueHeal;
                            for (int a = 0; a < capturedYellowPieces.size(); a++) {
                                if (capturedYellowPieces.get(a).getType() == highestValueHeal) {
                                    pieceGrid[tempHeals.get(randHealSpot).getX()][tempHeals.get(randHealSpot).getY()] = capturedYellowPieces.get(a);
                                    yellowPieces.add(capturedYellowPieces.get(a));
                                    capturedYellowPieces.remove(capturedYellowPieces.get(a));
                                }
                            }
                            pieceGrid[tempHeals.get(randHealSpot).getX()][tempHeals.get(randHealSpot).getY()].setSpot(tempHeals.get(randHealSpot).getX(), tempHeals.get(randHealSpot).getY());
                            pToMove.heal();
                            ifMoved = true;
                        }
                    }
                }
            }
            if (rand < .5 || !ifMoved) {//random movement
                System.out.println("EVERY SINGLE A"+ pSpots.size());
                System.out.println("random movement");
                randSpots = myRand(0, pSpots.size() - 1);
                for (int i = 0; i < yellowPieces.size(); i++) {
                    ArrayList<BoardSpot> tempMoves = yellowPieces.get(i).getPossibleMoves();
                    for (int j = 0; j < tempMoves.size(); j++) {
                        if (tempMoves.get(j).getX() == pSpots.get(randSpots).getX() && tempMoves.get(j).getY() == pSpots.get(randSpots).getY()) {
                            pToMove = yellowPieces.get(i);
                            System.out.println("goes in here");
                            break;
                        }
                    }
                }
                pieceGrid[pToMove.getX()][pToMove.getY()] = null;
                if ((pToMove.getX() % 2 == 0 && !(pToMove.getY() % 2 == 0)) || (!(pToMove.getX() % 2 == 0) && pToMove.getY() % 2 == 0)) {
                    gameGrid[pToMove.getX()][pToMove.getY()] = 1;
                } else {
                    gameGrid[pToMove.getX()][pToMove.getY()] = 0;
                }
                pToMove.setSpot(pSpots.get(randSpots).getX(), pSpots.get(randSpots).getY());
                pieceGrid[pToMove.getX()][pToMove.getY()] = pToMove;
                gameGrid[pToMove.getX()][pToMove.getY()] = pToMove.getType();
            }
            //checks if the egg can transform into the leader
            for (int i = 0; i < yellowPieces.size(); i++) {
                if (yellowPieces.get(i).getType() == 4) {
                    if (yellowPieces.get(i).getX() == 9) {
                        int randChoice = myRand(5, 8);

                        if ((yellowPieces.get(i).getX() % 2 == 0 && !(yellowPieces.get(i).getY() % 2 == 0)) || (!(yellowPieces.get(i).getX() % 2 == 0) && yellowPieces.get(i).getY() % 2 == 0)) {
                            gameGrid[yellowPieces.get(i).getX()][yellowPieces.get(i).getY()] = 1;
                        } else {
                            gameGrid[yellowPieces.get(i).getX()][yellowPieces.get(i).getY()] = 0;
                        }
                        yellowPieces.get(i).setSpot(9, yellowPieces.get(i).getY());
                        if (randChoice == 5) {
                            pieceGrid[yellowPieces.get(i).getX()][yellowPieces.get(i).getY()] = new UNKNOWN(yellowPieces.get(i).getX(), yellowPieces.get(i).getY(), true);
                        } else if (randChoice == 6) {
                            pieceGrid[yellowPieces.get(i).getX()][yellowPieces.get(i).getY()] = new AlienBuilder(yellowPieces.get(i).getX(), yellowPieces.get(i).getY(), true);
                        } else if (randChoice == 7) {
                            pieceGrid[yellowPieces.get(i).getX()][yellowPieces.get(i).getY()] = new AlienScientist(yellowPieces.get(i).getX(), yellowPieces.get(i).getY(), true);
                        } else if (randChoice == 8) {
                            pieceGrid[yellowPieces.get(i).getX()][yellowPieces.get(i).getY()] = new AlienHealer(yellowPieces.get(i).getX(), yellowPieces.get(i).getY(), true);
                        }
                        gameGrid[yellowPieces.get(i).getX()][yellowPieces.get(i).getY()] = randChoice;
                    }
                }
            }
        }
        pKills.clear();
        pSpots.clear();
        clearPossible();
        updateScreen();
        switchTurn();
    }


    private int myRand(int lowerBound, int upperBound){//picks a random number
        int rand=(int)(Math.random() * (upperBound - lowerBound+1))+lowerBound;
        return rand;
    }

    private void updateScreen() {//updates the images/colors on the board
        for (int i = 0; i < gameGrid.length; i++) {
            for (int j = 0; j < gameGrid[0].length; j++) {
                if(gameGrid[i][j]==0){
                    board[i][j].setStyle("-fx-background-color: #572f61");
                }else if(gameGrid[i][j]==1){
                    board[i][j].setStyle("-fx-background-color: #44C146");
                }else if (gameGrid[i][j] == 2) {
                    board[i][j].setStyle("-fx-background-color:#0000FF");
                }else if (gameGrid[i][j] == 10) {
                    board[i][j].setStyle("-fx-background-color:#008000");
                }else if (gameGrid[i][j] == 11) {
                    board[i][j].setStyle("-fx-background-color:#FFA500");
                }else if (gameGrid[i][j] == 12) {
                    board[i][j].setStyle("-fx-background-image: url(resources/wall.png);"+ "-fx-background-size: 60px 60px;"+ "-fx-background-repeat: no-repeat;"+ "-fx-background-position: center;");
                }else if(gameGrid[i][j]==13){
                    board[i][j].setStyle("-fx-background-color:#800080");
                }else if(gameGrid[i][j]==14){
                    board[i][j].setStyle("-fx-background-image: url(resources/startPortal.png);"+ "-fx-background-size: 60px 60px;"+ "-fx-background-repeat: no-repeat;"+ "-fx-background-position: center;");
                }else if(gameGrid[i][j]==15){
                    board[i][j].setStyle("-fx-background-image: url(resources/endPortal.png);"+ "-fx-background-size: 60px 60px;"+ "-fx-background-repeat: no-repeat;"+ "-fx-background-position: center;");
                }else if(gameGrid[i][j]==16){
                    board[i][j].setStyle("-fx-background-color:#000080");
                }else if(gameGrid[i][j]==3){
                    if(pieceGrid[i][j].getSide()) {
                        board[i][j].setStyle("-fx-background-image: url(resources/YellowAlienEgg.png);"+ "-fx-background-size: 60px 60px;"+ "-fx-background-repeat: no-repeat;"+ "-fx-background-position: center;");
                    }else {
                        board[i][j].setStyle("-fx-background-image: url(resources/OrangeAlienEgg.png);"+ "-fx-background-size: 60px 60px;"+ "-fx-background-repeat: no-repeat;"+ "-fx-background-position: center;");
                    }
                }else if(gameGrid[i][j]==4){
                    if(pieceGrid[i][j].getSide()) {
                        board[i][j].setStyle("-fx-background-image: url(resources/YellowAlienTrooper.png);"+ "-fx-background-size: 60px 60px;"+ "-fx-background-repeat: no-repeat;"+ "-fx-background-position: center;");
                    }else {
                        board[i][j].setStyle("-fx-background-image: url(resources/OrangeAlienTrooper.png);" + "-fx-background-size: 60px 60px;" + "-fx-background-repeat: no-repeat;" + "-fx-background-position: center;");
                    }
                }else if(gameGrid[i][j]==5){
                    if(pieceGrid[i][j].getSide()) {
                        board[i][j].setStyle("-fx-background-image: url(resources/YellowUNKNOWN.png);"+ "-fx-background-size: 60px 60px;"+ "-fx-background-repeat: no-repeat;"+ "-fx-background-position: center;");
                    }else {
                        board[i][j].setStyle("-fx-background-image: url(resources/OrangeUNKNOWN.png);"+ "-fx-background-size: 60px 60px;"+ "-fx-background-repeat: no-repeat;"+ "-fx-background-position: center;");
                    }
                }else if(gameGrid[i][j]==6){
                    if(pieceGrid[i][j].getSide()) {
                        board[i][j].setStyle("-fx-background-image: url(resources/YellowAlienBuilder.png);"+ "-fx-background-size: 60px 60px;"+ "-fx-background-repeat: no-repeat;"+ "-fx-background-position: center;");
                    }else {
                        board[i][j].setStyle("-fx-background-image: url(resources/OrangeAlienBuilder.png);"+ "-fx-background-size: 60px 60px;"+ "-fx-background-repeat: no-repeat;"+ "-fx-background-position: center;");
                    }
                }else if(gameGrid[i][j]==7){
                    if(pieceGrid[i][j].getSide()) {
                        board[i][j].setStyle("-fx-background-image: url(resources/YellowAlienScientist.png);"+ "-fx-background-size: 60px 60px;"+ "-fx-background-repeat: no-repeat;"+ "-fx-background-position: center;");
                    }else {
                        board[i][j].setStyle("-fx-background-image: url(resources/OrangeAlienScientist.png);"+ "-fx-background-size: 60px 60px;"+ "-fx-background-repeat: no-repeat;"+ "-fx-background-position: center;");
                    }
                }else if(gameGrid[i][j]==8){
                    if(pieceGrid[i][j].getSide()) {
                        board[i][j].setStyle("-fx-background-image: url(resources/YellowAlienHealer.png);"+ "-fx-background-size: 60px 60px;"+ "-fx-background-repeat: no-repeat;"+ "-fx-background-position: center;");
                    }else {
                        board[i][j].setStyle("-fx-background-image: url(resources/OrangeAlienHealer.png);" + "-fx-background-size: 60px 60px;" + "-fx-background-repeat: no-repeat;" + "-fx-background-position: center;");
                    }
                }else if(gameGrid[i][j]==9){
                    if(pieceGrid[i][j].getSide()) {
                        board[i][j].setStyle("-fx-background-image: url(resources/YellowAlienLeader.jpg);"+ "-fx-background-size: 60px 60px;"+ "-fx-background-repeat: no-repeat;"+ "-fx-background-position: center;");
                    }else {
                        board[i][j].setStyle("-fx-background-image: url(resources/OrangeAlienLeader.jpg);"+ "-fx-background-size: 60px 60px;"+ "-fx-background-repeat: no-repeat;"+ "-fx-background-position: center;");
                    }
                }

            }
        }
        gPane.setGridLinesVisible(true);
    }

    @FXML
    private void giveSuperChoice1(){//sets up/asks player 1 if want to super
        clearPossible();
        if(currentPiece.equals("Builder")){
            if(pieceGrid[currentPieceX][currentPieceY].canBuild()) {
                pieceGrid[currentPieceX][currentPieceY].possibleWalls(gameGrid, pieceGrid);
                pieceGrid[currentPieceX][currentPieceY].setIfBuild(false);
            }
        }else if(currentPiece.equals("UNKNOWN")){
            if(superChoice1.getValue().equals("UNKNOWN")) {
                pieceGrid[currentPieceX][currentPieceY].setCurrentMovement("UNKNOWN");
            }else if(superChoice1.getValue().equals("Builder")){
                pieceGrid[currentPieceX][currentPieceY].setCurrentMovement("Builder");
            }else if(superChoice1.getValue().equals("Scientist")){
                pieceGrid[currentPieceX][currentPieceY].setCurrentMovement("Scientist");

            }else if(superChoice1.getValue().equals("Egg")){
                pieceGrid[currentPieceX][currentPieceY].setCurrentMovement("Egg");
            }
            switchTurn();
        }else if(currentPiece.equals("Trooper")){
            if ((currentPieceX % 2 == 0 && !(currentPieceY % 2 == 0)) || (!(currentPieceX % 2 == 0) && currentPieceY % 2 == 0)) {
                gameGrid[currentPieceX][currentPieceY] = 1;
            } else {
                gameGrid[currentPieceX][currentPieceY] = 0;
            }
            pieceGrid[currentPieceX][currentPieceY].setSpot(0,currentPieceY);
            currentPieceX=0;

            if(superChoice1.getValue().equals("UNKNOWN")) {
                pieceGrid[currentPieceX][currentPieceY]=new UNKNOWN(currentPieceX, currentPieceY,false);
                gameGrid[currentPieceX][currentPieceY]=5;
                currentPiece="UNKNOWN";
            }else if(superChoice1.getValue().equals("Builder")){
                pieceGrid[currentPieceX][currentPieceY]=new AlienBuilder(currentPieceX, currentPieceY,false);
                gameGrid[currentPieceX][currentPieceY]=6;
                currentPiece="Builder";
            }else if(superChoice1.getValue().equals("Scientist")){
                pieceGrid[currentPieceX][currentPieceY]=new AlienScientist(currentPieceX, currentPieceY,false);
                gameGrid[currentPieceX][currentPieceY]=7;
                currentPiece="Scientist";
            }else if(superChoice1.getValue().equals("Healer")) {
                pieceGrid[currentPieceX][currentPieceY]=new AlienHealer(currentPieceX, currentPieceY,false);
                gameGrid[currentPieceX][currentPieceY]=8;
                currentPiece="Healer";
            }
            clearPossible();
            switchTurn();
        }else if(currentPiece.equals("Scientist")){
            pieceGrid[currentPieceX][currentPieceY].possiblePortal(gameGrid, pieceGrid);
        }else if(currentPiece.equals("Healer")){
            if(pieceGrid[currentPieceX][currentPieceY].ifCanHeal()) {

                if (superChoice1.getValue().equals("Trooper")) {
                    pieceGrid[currentPieceX][currentPieceY].setTypeHeal(4);
                } else if (superChoice1.getValue().equals("UNKNOWN")) {
                    pieceGrid[currentPieceX][currentPieceY].setTypeHeal(5);
                } else if (superChoice1.getValue().equals("Builder")) {
                    pieceGrid[currentPieceX][currentPieceY].setTypeHeal(6);
                } else if (superChoice1.getValue().equals("Scientist")) {
                    pieceGrid[currentPieceX][currentPieceY].setTypeHeal(7);
                }
                clearPossible();
                pieceGrid[currentPieceX][currentPieceY].possibleHealLocations(gameGrid,pieceGrid);
                pieceGrid[currentPieceX][currentPieceY].heal();
            }
        }
        updateScreen();
    }

    @FXML
    private void giveSuperChoice2(){//ask/sets up if player 2 wants to super
        clearPossible();
        if(currentPiece.equals("Builder")){//builder super
            if(pieceGrid[currentPieceX][currentPieceY].canBuild()) {
                pieceGrid[currentPieceX][currentPieceY].possibleWalls(gameGrid, pieceGrid);
                pieceGrid[currentPieceX][currentPieceY].setIfBuild(false);
            }
        }else if(currentPiece.equals("UNKNOWN")){//UNKNOWN super
            if(superChoice2.getValue().equals("UNKNOWN")) {
                pieceGrid[currentPieceX][currentPieceY].setCurrentMovement("UNKNOWN");
            }else if(superChoice2.getValue().equals("Builder")){
                pieceGrid[currentPieceX][currentPieceY].setCurrentMovement("Builder");
            }else if(superChoice2.getValue().equals("Scientist")){
                pieceGrid[currentPieceX][currentPieceY].setCurrentMovement("Scientist");

            }else if(superChoice2.getValue().equals("Egg")){
                pieceGrid[currentPieceX][currentPieceY].setCurrentMovement("Egg");
            }
            switchTurn();
        }else if(currentPiece.equals("Trooper")){//Trooper super
            if ((currentPieceX % 2 == 0 && !(currentPieceY % 2 == 0)) || (!(currentPieceX % 2 == 0) && currentPieceY % 2 == 0)) {
                gameGrid[currentPieceX][currentPieceY] = 1;
            }else{
                gameGrid[currentPieceX][currentPieceY] = 0;
            }
            pieceGrid[currentPieceX][currentPieceY].setSpot(9,currentPieceY);
            currentPieceX=9;

            if(superChoice2.getValue().equals("UNKNOWN")){
                pieceGrid[currentPieceX][currentPieceY]=new UNKNOWN(currentPieceX, currentPieceY,true);
                gameGrid[currentPieceX][currentPieceY]=5;
                currentPiece="UNKNOWN";
            }else if(superChoice2.getValue().equals("Builder")){
                pieceGrid[currentPieceX][currentPieceY]=new AlienBuilder(currentPieceX, currentPieceY,true);
                gameGrid[currentPieceX][currentPieceY]=6;
                currentPiece="Builder";
            }else if(superChoice2.getValue().equals("Scientist")){
                pieceGrid[currentPieceX][currentPieceY]=new AlienScientist(currentPieceX, currentPieceY,true);
                gameGrid[currentPieceX][currentPieceY]=7;
                currentPiece="Scientist";
            }else if(superChoice2.getValue().equals("Healer")) {
                pieceGrid[currentPieceX][currentPieceY]=new AlienHealer(currentPieceX, currentPieceY,true);
                gameGrid[currentPieceX][currentPieceY]=8;
                currentPiece="Healer";
            }
            clearPossible();
            switchTurn();
        }else if(currentPiece.equals("Scientist")){//Scientist super
            pieceGrid[currentPieceX][currentPieceY].possiblePortal(gameGrid, pieceGrid);
        }else if(currentPiece.equals("Healer")){//Healer super
            if(pieceGrid[currentPieceX][currentPieceY].ifCanHeal()) {
                if (superChoice2.getValue().equals("Trooper")) {
                    pieceGrid[currentPieceX][currentPieceY].setTypeHeal(4);
                } else if (superChoice2.getValue().equals("UNKNOWN")) {
                    pieceGrid[currentPieceX][currentPieceY].setTypeHeal(5);
                } else if (superChoice2.getValue().equals("Builder")) {
                    pieceGrid[currentPieceX][currentPieceY].setTypeHeal(6);
                } else if (superChoice2.getValue().equals("Scientist")) {
                    pieceGrid[currentPieceX][currentPieceY].setTypeHeal(7);
                }
                clearPossible();
                pieceGrid[currentPieceX][currentPieceY].possibleHealLocations(gameGrid, pieceGrid);
                pieceGrid[currentPieceX][currentPieceY].heal();
            }
        }
        updateScreen();
    }

    @FXML
    private void choosePlayer1(){//chooses the first player
        String name="";
        int wins;
        int losses;
        int score=0;
        for(int i=0;i<chooseP1.getValue().toString().length();i++){
            if(chooseP1.getValue().toString().substring(i,i+1).equals("|")){
                name=chooseP1.getValue().toString().substring(1,i-1);
            }
        }
        for(int i=0;i<chooseP1.getValue().toString().length();i++){
            if(chooseP1.getValue().toString().substring(i,i+1).equals("|")){
                score=Integer.parseInt(chooseP1.getValue().toString().substring(i+2,chooseP1.getValue().toString().length()-1));
            }
        }
        chooseP2.getItems().remove("("+name+" | "+score+")");
        wins=getPlayerInfo(name,"Wins");
        losses=getPlayerInfo(name,"Losses");
        p1Name.setText(name);
        chooseP2.setVisible(true);
        selectBtn2.setVisible(true);
        chooseP1.setVisible(false);
        selectBtn1.setVisible(false);
        player1=new Player(name,score,wins,losses);
    }
    @FXML
    private void choosePlayer2(){//choose the second player
        String name="";
        int score=0;
        int wins;
        int losses;
        for(int i=0;i<chooseP2.getValue().toString().length();i++){
            if(chooseP2.getValue().toString().substring(i,i+1).equals("|")){
                name=chooseP2.getValue().toString().substring(1,i-1);
            }
        }

        for(int i=0;i<chooseP2.getValue().toString().length();i++){
            if(chooseP2.getValue().toString().substring(i,i+1).equals("|")){
                score=Integer.parseInt(chooseP2.getValue().toString().substring(i+2,chooseP2.getValue().toString().length()-1));
            }
        }
        wins=getPlayerInfo(name,"Wins");
        losses=getPlayerInfo(name,"Losses");
        chooseP2.setVisible(false);
        selectBtn2.setVisible(false);
        chooseP1.getItems().remove("("+name+" | "+score+")");
        p2Name.setText(name);
        player2=new Player(name,score,wins,losses);
    }
    @FXML
    private void setPlayers(){//starts the game
        if(player2==null){
            numPlayers=1;
        }else{
            numPlayers=2;
        }
        chooseP2.setVisible(false);
        selectBtn2.setVisible(false);
        superSelectBtn2.setVisible(true);
        superSelectBtn1.setVisible(true);
        superChoice1.setVisible(true);
        superChoice2.setVisible(true);
        superSelectBtn2.setVisible(true);
        superSelectBtn1.setVisible(true);
        superChoice1.setVisible(true);
        superChoice2.setVisible(true);
        turnLbl.setVisible(true);
        lbl1.setVisible(true);
        lbl2.setVisible(true);
        gPane.setDisable(false);
        startGameBtn.setVisible(false);
        System.out.println("number players: "+numPlayers);
        if(turn &&numPlayers==1){
            computerPlayer();
        }
    }
    @FXML
    private void setChoiceBox(){//updates the choice box with all of the player
        playerOptionBtn.setVisible(false);
        chooseP2.getItems().clear();
        chooseP1.getItems().clear();
        ArrayList<String> tempNames=AddPlayer.getPlayerNames();
        for(int i=0;i< tempNames.size();i++){
            chooseP1.getItems().add(tempNames.get(i));
            chooseP2.getItems().add(tempNames.get(i));
        }
    }
    @FXML
    private void updatePlayerInfo(String userName,String update, int value) {//updates the player info
        String connectQuery="";
        if (update.equals("win")) {
             connectQuery="UPDATE `playerinfo` SET Wins ="+value+" WHERE UserName = ('"+userName+"')";
        }else if(update.equals("lose")){
             connectQuery="UPDATE `playerinfo` SET Losses ="+value+" WHERE UserName = ('"+userName+"')";
        }else if(update.equals("RS")){
             connectQuery="UPDATE `playerinfo` SET RankScore ="+value+" WHERE UserName = ('"+userName+"')";
        }
        ConnectionClass connectNow= new ConnectionClass();
        Connection connectDB=connectNow.getConnection();
        try{
            Statement statement=connectDB.createStatement();
            statement.executeUpdate(connectQuery);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private int getPlayerInfo(String name,String column){//gets the player info
        int value=0;
        String connectQuery="SELECT UserName, "+column+" FROM playerinfo";
        ConnectionClass connectNow= new ConnectionClass();
        Connection connectDB=connectNow.getConnection();
        try{
            Statement statement=connectDB.createStatement();
            ResultSet queryOutput=statement.executeQuery(connectQuery);
            while (queryOutput.next()){
                if(queryOutput.getString("UserName").equals(name)) {
                    value = queryOutput.getInt(column);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return value;
    }
    @FXML
    private void showMenu(){//returns back to the main screen
        Main.setPane(0);
        endGame(0);
        initialize();
    }
}