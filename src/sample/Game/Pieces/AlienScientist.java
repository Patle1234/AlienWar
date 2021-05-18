package sample.Game.Pieces;

import sample.Game.BoardSpot;

import java.util.ArrayList;

public class AlienScientist extends Piece{
    private int x;
    private int y;
    private int numPortals=0;
    private boolean side;
    private ArrayList<BoardSpot> possibleScientistMoves= new ArrayList();
    private ArrayList<BoardSpot> radiusOne= new ArrayList();
    private ArrayList<BoardSpot> possibleCaptures= new ArrayList();
    private ArrayList<BoardSpot> possiblePortals= new ArrayList();

    public AlienScientist(int x, int y,boolean side){
        this.x=x;
        this.y=y;
        this.side=side;
    }
    public void possibleMove(int[][] board, Piece[][] pieceSpots ,int direction){//scientist movement
        possibleScientistMoves.clear();
        possibleCaptures.clear();
        for (int r = x - direction; r <= x + direction  && r < board.length ; r++) {
            for (int c = y - direction; c <= y + direction  && c < board.length; c++) {
                if(r>=0 && c>=0) {
                        if(!(Math.abs(x-r)==1) && !(Math.abs(y -c)==1)) {//if the space between the spot is greater than 1, then ...
                    if (board[r][c] == 0 || board[r][c] == 1|| board[r][c] == 14) {
                                possibleScientistMoves.add(new BoardSpot(r, c));
                    } else if (board[r][c] == 3 || board[r][c] == 4 || board[r][c] == 5 || board[r][c] == 6 || board[r][c] == 7 || board[r][c]== 8 ||board[r][c] == 9) {
                          if(!(pieceSpots[r][c].getSide()==side)) {
                                      possibleCaptures.add(new BoardSpot(r, c));
                                  }
                              }
                          }
                }
            }
        }
    }
    public void possiblePortal(int[][] board, Piece[][] pieceSpots) {//checks all of the spots that a portal can be placed
        possiblePortals.clear();
        for(int i=0; i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if(board[i][j]==0 || board[i][j]==1){
                    possiblePortals.add(new BoardSpot(i, j));
                }
            }
        }
        for(int i=0;i<possiblePortals.size();i++){
            board[possiblePortals.get(i).getX()][possiblePortals.get(i).getY()]=13;
        }
        numPortals++;
    }
    public ArrayList<BoardSpot> getPossiblePortals() {
        return possiblePortals;
    }
    public void setSpot(int x,int y){
        this.x=x;
        this.y=y;
    }
    public boolean ifCanPortal(){
        if(numPortals<4) {
            return true;
        }
        return false;
    }
    public boolean getSide(){
        return side;
    }
    public int getType(){
        return 7;
    }
    public void showPossible(int[][]tempGrid){
        for(int i=0;i<possibleScientistMoves.size();i++){
            tempGrid[possibleScientistMoves.get(i).getX()][possibleScientistMoves.get(i).getY()]=2;
        }
        for(int i=0;i<possibleCaptures.size();i++){
            tempGrid[possibleCaptures.get(i).getX()][possibleCaptures.get(i).getY()]=10;
        }
    }
    public ArrayList<BoardSpot> getPossibleMoves(){
        return possibleScientistMoves;
    }
    public ArrayList<BoardSpot> getPossibleCaptures(){
        return possibleCaptures;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

}
