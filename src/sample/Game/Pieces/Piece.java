package sample.Game.Pieces;
import sample.Game.BoardSpot;

import java.util.ArrayList;

public class Piece {
    //all functions are place holders for overidding with sub-classes
    public int getX(){
        return 0;
    }
    public int getY() {
        return 0;
    }
    public void setSpot(int x,int y){}
    public boolean getSide(){
        return true;
    }
    public void showPossible(int[][]tempGrid){}
    public int getType(){
        return 0;
    }
    public void possibleMove(int[][] board, Piece[][] pieceSpots , int direction){}
    public ArrayList<BoardSpot> getPossibleMoves(){
        return new ArrayList<>();
    }
    public ArrayList<BoardSpot> getPossibleCaptures(){
        return new ArrayList<>();
    }
    public ArrayList<BoardSpot> getPossibleWalls(){return new ArrayList<>();}
    public ArrayList<BoardSpot> getPossiblePortals(){return new ArrayList<>();}
    public ArrayList<BoardSpot> getPossibleHeals(){return new ArrayList<>();}
    public boolean ifCanHeal(){return false;}
    public void heal(){}
    public boolean canBuild(){return false;}//PLACEHOLDER
    public void setIfBuild(boolean x){}
    public int getTypeHeal(){
        return 0;
    }
    public void setTypeHeal(int x){}
    public boolean ifAtEnd() {return false;}
    public void setCurrentMovement(String x){}
    public void possibleWalls(int[][] board, Piece[][] pieceSpots) {}
    public void possibleHealLocations(int[][] board, Piece[][] pieceSpots) {}
    public boolean ifCanPortal(){return false;}
    public void possiblePortal(int[][] board, Piece[][] pieceSpots){}
}
