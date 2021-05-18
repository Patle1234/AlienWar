package sample.Game.Pieces;

import sample.Game.BoardSpot;


import java.util.ArrayList;

public class AlienTrooper extends Piece{
    private int x;
    private int y;
    private boolean side;//false is yellow and true is orange
    private boolean ifFirstMove=true;
    private boolean ifAtEnd=false;
    private ArrayList<BoardSpot> possibleTrooperMoves= new ArrayList();
    private ArrayList<BoardSpot> possibleCaptures= new ArrayList();

    public AlienTrooper(int x, int y,boolean side){
        this.x=x;
        this.y=y;
        this.side=side;
    }
    public void possibleMove(int[][] board, Piece[][] pieceSpots ,int direction){//trooper movement
        possibleTrooperMoves.clear();
        possibleCaptures.clear();
        if(ifFirstMove){//checks the movement for the first turn(checks the first 2 spots in front)
            if(direction<0) {
                for (int r = x - 1; r >= x -2 && r < board.length; r--) {
                    if (r >= 0) {
                        if (board[r][y] == 0 || board[r][y] == 1||board[r][y]==14){
                            possibleTrooperMoves.add(new BoardSpot(r, y));
                        }else{
                            break;
                        }
                    }
                }
            }else{
                for (int r = x + 1; r <= x +2 && r < board.length; r++) {
                    if (r >= 0) {
                        if (board[r][y] == 0 || board[r][y] == 1||board[r][y]==14) {
                            possibleTrooperMoves.add(new BoardSpot(r, y));
                        }else{
                            break;
                        }
                    }
                }
            }
        }else {
            if ((x + direction >= 0) && (x + direction < board.length) && (board[x + direction][y] == 0 || board[x + direction][y] == 1|| board[x + direction][y] == 14)) {
                possibleTrooperMoves.add(new BoardSpot(x + direction, y));
                if(x+direction==9||x+direction==0){
                    ifAtEnd=true;
                }
            }
        }
        int tempX;
        int tempY=y+1;
        if(side){
            tempX=x+1;
        }else {
            tempX = x - 1;
        }
        for(int i=0;i<2;i++) {//checks for possible captures(diagonals)
            if (tempX>=0 && tempX<board.length && tempY>=0 && tempY<board.length) {
                if (board[tempX][tempY] == 3 || board[tempX][tempY]  == 4|| board[tempX][tempY]  == 5 || board[tempX][tempY]  == 6 || board[tempX][tempY]  == 7 ||board[tempX][tempY] == 8 || board[tempX][tempY] == 9) {
                    if(!(pieceSpots[tempX][tempY].getSide()==side)) {
                        possibleCaptures.add(new BoardSpot(tempX, tempY));
                    }
                }
            }
            tempY=y-1;
        }
    }

    public void setSpot(int x,int y) {
        this.x = x;
        this.y = y;
        if (ifFirstMove) {
            ifFirstMove = false;
        }
    }
    public boolean ifAtEnd(){
        return ifAtEnd;
    }
    public boolean getSide(){
        return side;
    }
    public int getType(){
        return 4;
    }
    public void showPossible(int[][]tempGrid){
        for(int i=0;i<possibleTrooperMoves.size();i++){
            tempGrid[possibleTrooperMoves.get(i).getX()][possibleTrooperMoves.get(i).getY()]=2;
        }
        for(int i=0;i<possibleCaptures.size();i++){
            tempGrid[possibleCaptures.get(i).getX()][possibleCaptures.get(i).getY()]=10;
        }
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public ArrayList<BoardSpot> getPossibleCaptures(){
        return possibleCaptures;
    }
    public ArrayList<BoardSpot> getPossibleMoves(){
        return possibleTrooperMoves;
    }
}
