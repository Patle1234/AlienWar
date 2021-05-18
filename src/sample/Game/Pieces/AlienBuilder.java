package sample.Game.Pieces;

//import sample.BoardSpot;

import sample.Game.BoardSpot;

import java.util.ArrayList;

public class AlienBuilder extends Piece {
    private int x;
    private int y;
    private boolean ifBuild=true;
    private boolean side;//0 is yellow and 1 is orange
    private ArrayList<BoardSpot> possibleBuilderMoves= new ArrayList();
    private ArrayList<BoardSpot> possibleCaptures= new ArrayList();
    private ArrayList<BoardSpot> possibleWalls= new ArrayList();

    public AlienBuilder(int x, int y,boolean side){
        this.x=x;
        this.y=y;
        this.side=side;
    }

    public void possibleMove(int[][] board, Piece[][] pieceSpots ,int direction){//builder movement
        possibleBuilderMoves.clear();
        possibleCaptures.clear();
        for(int rRight=x;rRight<=x+direction&& rRight<board.length;rRight++){
            if(!(rRight==x)) {
                if (board[rRight][y] == 0 || board[rRight][y] == 1|| board[rRight][y] == 14) {
                    possibleBuilderMoves.add(new BoardSpot(rRight, y));
                } else if (board[rRight][y] == 3 || board[rRight][y] == 4 || board[rRight][y] == 5 || board[rRight][y] == 6 || board[rRight][y] == 7 || board[rRight][y] == 8 || board[rRight][y] == 9) {
                    if(!(pieceSpots[rRight][y].getSide()==side)) {
                        possibleCaptures.add(new BoardSpot(rRight, y));
                    }
                    break;
                }else{
                    break;
                }
            }
        }

        for(int rLeft=x;rLeft>=x-direction&& rLeft>=0;rLeft--){
            if(!(rLeft==x)) {
                if (board[rLeft][y] == 0 || board[rLeft][y] == 1|| board[rLeft][y] == 14) {
                    possibleBuilderMoves.add(new BoardSpot(rLeft, y));
                } else if (board[rLeft][y] == 3 || board[rLeft][y] == 4 || board[rLeft][y] == 5 || board[rLeft][y] == 6 || board[rLeft][y] == 7 || board[rLeft][y] == 8 || board[rLeft][y] == 9) {
                    if(!(pieceSpots[rLeft][y].getSide()==side)) {
                        possibleCaptures.add(new BoardSpot(rLeft, y));
                    }
                    break;
                }else{
                    break;
                }
            }
        }
        for(int cRight=y;cRight<=y+direction&& cRight<board.length;cRight++){
            if(!(cRight==y)) {
                if (board[x][cRight] == 0 || board[x][cRight] == 1|| board[x][cRight] == 14) {
                    possibleBuilderMoves.add(new BoardSpot(x, cRight));
                } else if (board[x][cRight] == 3 || board[x][cRight] == 4 || board[x][cRight] == 5 || board[x][cRight] == 6 || board[x][cRight] == 7 || board[x][cRight] == 8 || board[x][cRight] == 9) {
                        if(!(pieceSpots[x][cRight].getSide()==side)) {
                            possibleCaptures.add(new BoardSpot(x, cRight));
                        }
                    break;
                }else{
                    break;
                }
            }
        }
        for(int cLeft=y;cLeft>=y-direction&& cLeft>=0;cLeft--){
            if(!(cLeft==y)) {
                if (board[x][cLeft] == 0 || board[x][cLeft] == 1|| board[x][cLeft] == 14) {
                    possibleBuilderMoves.add(new BoardSpot(x, cLeft));
                } else if (board[x][cLeft] == 3 || board[x][cLeft] == 4 || board[x][cLeft] == 5 || board[x][cLeft] == 6 || board[x][cLeft] == 7 || board[x][cLeft] == 8 || board[x][cLeft] == 9) {
                        if(!(pieceSpots[x][cLeft].getSide()==side)) {
                            possibleCaptures.add(new BoardSpot(x, cLeft));
                        }
                    break;
                }else{
                    break;
                }
            }
        }
    }

    public void possibleWalls(int[][] board, Piece[][] pieceSpots) {//finds all of the possible spots to place a wall
        possibleWalls.clear();
        for(int i=0; i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if(board[i][j]==0 || board[i][j]==1){
                    possibleWalls.add(new BoardSpot(i, j));
                }
            }
        }
        for(int i=0;i<possibleWalls.size();i++){
            board[possibleWalls.get(i).getX()][possibleWalls.get(i).getY()]=11;
        }
    }

    public ArrayList<BoardSpot> getPossibleWalls(){
        return possibleWalls;
    }

    public void setIfBuild(boolean x){
        ifBuild=x;
    }

    public boolean canBuild(){
        return ifBuild;
    }
    public void setSpot(int x,int y){
        this.x=x;
        this.y=y;
        if(!ifBuild){
            ifBuild=true;
        }
    }

    public int getType(){
        return 6;
    }

    public boolean getSide(){
        return side;
    }
    public void showPossible(int[][] board){
        for(int i=0;i<possibleBuilderMoves.size();i++){
            board[possibleBuilderMoves.get(i).getX()][possibleBuilderMoves.get(i).getY()]=2;
        }
        for(int i=0;i<possibleCaptures.size();i++){
            board[possibleCaptures.get(i).getX()][possibleCaptures.get(i).getY()]=10;
        }
    }
    public ArrayList<BoardSpot> getPossibleMoves(){
        return possibleBuilderMoves;
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
