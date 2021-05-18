package sample.Game.Pieces;

import javafx.scene.layout.AnchorPane;
import sample.Game.BoardSpot;

import java.util.ArrayList;

public class AlienEgg extends Piece{
    private int x;
    private int y;
    private boolean side;//0 is yellow and 1 is orange
    private ArrayList<BoardSpot> possibleEggMoves= new ArrayList();
    private ArrayList<BoardSpot> possibleCaptures= new ArrayList();

    public AlienEgg(int x, int y,boolean side){
        this.x=x;
        this.y=y;
        this.side=side;
    }

    public void possibleMove(int[][] board, Piece[][] pieceSpots ,int direction){//egg movement
        possibleEggMoves.clear();
        possibleCaptures.clear();
            for (int r = x - direction; r <= x + direction  && r < board.length ; r++) {
            for (int c = y - direction; c <= y + direction  && c < board.length; c++) {
                if(r>=0 && c>=0) {
                    if(!(r==x)|| !(c==y)) {
                        if (board[r][c] == 0 || board[r][c] == 1|| board[r][c] == 14) {
                            possibleEggMoves.add(new BoardSpot(r, c));
                        } else if (board[r][c] == 3 || board[r][c] == 4 || board[r][c] == 5 || board[r][c] == 6 || board[r][c] == 7 || board[r][c] == 8 || board[r][c] == 9) {
                            if(!(pieceSpots[r][c].getSide()==side)) {
                                possibleCaptures.add(new BoardSpot(r, c));
                            }
                        }
                    }
                }
            }
        }
    }

    public void setSpot(int x,int y){
        this.x=x;
        this.y=y;
    }
    public boolean getSide(){
        return side;
    }
    public int getType(){
        return 3;
    }
    public void showPossible(int[][]tempGrid){
        for(int i=0;i<possibleEggMoves.size();i++){
            tempGrid[possibleEggMoves.get(i).getX()][possibleEggMoves.get(i).getY()]=2;
        }
        for(int i=0;i<possibleCaptures.size();i++){
            tempGrid[possibleCaptures.get(i).getX()][possibleCaptures.get(i).getY()]=10;
        }
    }
    public ArrayList<BoardSpot> getPossibleCaptures(){
        return possibleCaptures;
    }
    public ArrayList<BoardSpot> getPossibleMoves(){
        return possibleEggMoves;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

}
