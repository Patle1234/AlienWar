package sample.Game.Pieces;
import sample.Game.BoardSpot;
import java.util.ArrayList;

public class AlienHealer extends Piece{
    private int x;
    private int y;
    private int typeHeal=1;
    private boolean ifCanHeal=true;
    private boolean side;//false is yellow and true is orange
    private ArrayList<BoardSpot> possibleHealerMoves= new ArrayList();
    private ArrayList<BoardSpot> possibleCaptures= new ArrayList();
    private ArrayList<BoardSpot> possibleHealingLocations= new ArrayList();

    public AlienHealer(int x, int y,boolean side){
        this.x=x;
        this.y=y;
        this.side=side;
    }
    public void possibleMove(int[][] board, Piece[][] pieceSpots ,int direction) {//healer movement
        possibleHealerMoves.clear();
        possibleCaptures.clear();
        for (int r = x - direction; r <= x + direction && r < board.length; r++) {
            for (int c = y - direction; c <= y + direction && c < board.length; c++) {
                if (r >= 0 && c >= 0) {
                      if(r==x|| c==y){
                        if (board[r][c] == 0 || board[r][c] == 1||board[r][c]==14) {
                            possibleHealerMoves.add(new BoardSpot(r, c));
                        } else if (board[r][c] == 3 || board[r][c] == 4 || board[r][c] == 5 || board[r][c] == 6 || board[r][c] == 7 || board[r][c] == 8 || board[r][c] == 9) {
                            if (!(pieceSpots[r][c].getSide() == side)) {
                                possibleCaptures.add(new BoardSpot(r, c));
                            }
                        }
                    }
                }
            }
        }
    }
    public void possibleHealLocations(int[][] board, Piece[][] pieceSpots) {//gets all of the possible locations that a piece being healed can be revived
        possibleHealingLocations.clear();
        for(int i=0; i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if(board[i][j]==0 || board[i][j]==1){
                    possibleHealingLocations.add(new BoardSpot(i, j));
                }
            }
        }
        for(int i=0;i<possibleHealingLocations.size();i++){
            board[possibleHealingLocations.get(i).getX()][possibleHealingLocations.get(i).getY()]=16;
        }
    }
    public ArrayList<BoardSpot> getPossibleHeals(){ return possibleHealingLocations;}
    public void setSpot(int x,int y){
        this.x=x;
        this.y=y;
    }
    public boolean ifCanHeal(){
        System.out.println(ifCanHeal+"pppppppp");return ifCanHeal;
    }
    public void heal(){
        ifCanHeal=false;
    }
    public int getTypeHeal(){
        return typeHeal;
    }
    public void setTypeHeal(int x){
        typeHeal=x;
    }
    public boolean getSide(){
        return side;
    }
    public int getType(){
        return 8;
    }
    public void showPossible(int[][]tempGrid){
        for(int i=0;i<possibleHealerMoves.size();i++){
            tempGrid[possibleHealerMoves.get(i).getX()][possibleHealerMoves.get(i).getY()]=2;
        }
            for(int i=0;i<possibleCaptures.size();i++){
                tempGrid[possibleCaptures.get(i).getX()][possibleCaptures.get(i).getY()]=10;
            }
    }
    public ArrayList<BoardSpot> getPossibleCaptures(){
        return possibleCaptures;
    }
    public ArrayList<BoardSpot> getPossibleMoves(){
        return possibleHealerMoves;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
