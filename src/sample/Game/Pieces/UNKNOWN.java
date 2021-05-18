package sample.Game.Pieces;
import sample.Game.BoardSpot;
import java.util.ArrayList;

public class UNKNOWN extends Piece{
    private int x;
    private int y;
    private String currentMovement="UNKNOWN";
    private boolean side;//0 is yellow and 1 is orange
    private ArrayList<BoardSpot> possibleUNKNOWNMoves= new ArrayList();
    private ArrayList<BoardSpot> possibleCaptures= new ArrayList();

    public UNKNOWN(int x, int y,boolean side){
        this.x=x;
        this.y=y;
        this.side=side;
    }

    public void possibleMove(int[][] board, Piece[][] pieceSpots ,int direction){
        possibleUNKNOWNMoves.clear();
        possibleCaptures.clear();
        if(currentMovement.equals("UNKNOWN")){
            UNKNOWNMovment(board, pieceSpots, direction);
        }else if(currentMovement.equals("Builder")){
            System.out.println("pickBuilder");
            builderMovement(board, pieceSpots, 10);
        }else if(currentMovement.equals("Scientist")){
            System.out.println("pickScientist");

            scientistMovement(board,pieceSpots,2);
        }else if(currentMovement.equals("Egg")){
            System.out.println("pickEgg");

            eggMovement(board,pieceSpots,1);
        }

    }
    public void builderMovement(int[][] board, Piece[][] pieceSpots ,int direction){
        for(int rRight=x;rRight<=x+direction&& rRight<board.length;rRight++){
            if(!(rRight==x)) {
                if (board[rRight][y] == 0 || board[rRight][y] == 1||board[rRight][y]==14) {
                    possibleUNKNOWNMoves.add(new BoardSpot(rRight, y));
                } else if (board[rRight][y] == 3 || board[rRight][y] == 4 || board[rRight][y] == 5 || board[rRight][y] == 6 || board[rRight][y] == 7 || board[rRight][y] == 8 || board[rRight][y] == 9) {
                    if(!(pieceSpots[rRight][y].getSide()==side)) {
                        possibleCaptures.add(new BoardSpot(rRight, y));
                    }
                    break;
                }
            }
        }

        for(int rLeft=x;rLeft>=x-direction&& rLeft>=0;rLeft--){
            if(!(rLeft==x)) {
                if (board[rLeft][y] == 0 || board[rLeft][y] == 1||board[rLeft][y]==14) {
                    possibleUNKNOWNMoves.add(new BoardSpot(rLeft, y));
                } else if (board[rLeft][y] == 3 || board[rLeft][y] == 4 || board[rLeft][y] == 5 || board[rLeft][y] == 6 || board[rLeft][y] == 7 || board[rLeft][y] == 8 || board[rLeft][y] == 9) {
                    if(!(pieceSpots[rLeft][y].getSide()==side)) {
                        possibleCaptures.add(new BoardSpot(rLeft, y));
                    }
                    break;
                }
            }
        }
        for(int cRight=y;cRight<=y+direction&& cRight<board.length;cRight++){
            if(!(cRight==y)) {
                if (board[x][cRight] == 0 || board[x][cRight] == 1||board[x][cRight]==14) {
                    possibleUNKNOWNMoves.add(new BoardSpot(x, cRight));
                } else if (board[x][cRight] == 3 || board[x][cRight] == 4 || board[x][cRight] == 5 || board[x][cRight] == 6 || board[x][cRight] == 7 || board[x][cRight] == 8 || board[x][cRight] == 9) {
                    if(!(pieceSpots[x][cRight].getSide()==side)) {
                        possibleCaptures.add(new BoardSpot(x, cRight));
                    }
                    break;
                }
            }
        }
        for(int cLeft=y;cLeft>=y-direction&& cLeft>=0;cLeft--){
            if(!(cLeft==y)) {
                if (board[x][cLeft] == 0 || board[x][cLeft] == 1||board[x][cLeft]==14) {
                    possibleUNKNOWNMoves.add(new BoardSpot(x, cLeft));
                } else if (board[x][cLeft] == 3 || board[x][cLeft] == 4 || board[x][cLeft] == 5 || board[x][cLeft] == 6 || board[x][cLeft] == 7 || board[x][cLeft] == 8 || board[x][cLeft] == 9) {
                    if(!(pieceSpots[x][cLeft].getSide()==side)) {
                        possibleCaptures.add(new BoardSpot(x, cLeft));
                    }
                    break;
                }
            }
        }
    }
    public void eggMovement(int[][] board, Piece[][] pieceSpots ,int direction){
        for (int r = x - direction; r <= x + direction  && r < board.length ; r++) {
            for (int c = y - direction; c <= y + direction  && c < board.length; c++) {
                if(r>=0 && c>=0) {
                    if(!(r==x)|| !(c==y)) {
                        if (board[r][c] == 0 || board[r][c] == 1||board[r][c]==14) {
                            possibleUNKNOWNMoves.add(new BoardSpot(r, c));
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
    public void scientistMovement(int[][] board, Piece[][] pieceSpots ,int direction){
        for (int r = x - direction; r <= x + direction  && r < board.length ; r++) {
            for (int c = y - direction; c <= y + direction  && c < board.length; c++) {
                if(r>=0 && c>=0) {
                    if(!(Math.abs(x-r)==1) || !(Math.abs(y -c)==1)) {
                        if(!(Math.abs(x-r)==1) && !(Math.abs(y -c)==1)) {
                            if (board[r][c] == 0 || board[r][c] == 1||board[r][c]==14) {
                                possibleUNKNOWNMoves.add(new BoardSpot(r, c));
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
    }
    public void UNKNOWNMovment(int[][] board, Piece[][] pieceSpots ,int direction){
        boolean ifHit=false;
        int i = 0;
        int j = 0;
        int c;
        int r;
        r=x-i;
        c=y-j;
        for(int a=0;a<4;a++){
            if(a==0){
                r=x-i;
                c=y-j;
            }else if(a==1){
                r=i+x;
                c=j+y;
            }else if(a==2){
                r=x-i;
                c=j+y;
            }else if(a==3){
                r=i+x;
                c=y-j;
            }
            while (r < board.length &&c< board.length&&r>=0/*r>=1*/ &&c>=0&&!ifHit) {
                i++;
                j++;
                if (board[r][c] == 0 || board[r][c] == 1||board[r][c]==14) {
                    possibleUNKNOWNMoves.add(new BoardSpot(r, c));
                } else if (board[r][c] == 3 || board[r][c] == 4 || board[r][c] == 5 || board[r][c] == 6 || board[r][c] == 7 || board[r][c] == 8 || board[r][c] == 9) {
                    if(!(r==x) &&!(c==y)) {
                        if (!(pieceSpots[r][c].getSide() == side)) {
                            possibleCaptures.add(new BoardSpot(r, c));
                        }
                        ifHit = true;
                    }
                }else if(board[r][c]==12||board[r][c]==15){
                    ifHit=true;
                }
                if (a == 0) {
                    r = x - i;
                    c = y - j;
                } else if (a == 1) {
                    r = i + x;
                    c = j + y;
                } else if (a == 2) {
                    r = x - i;
                    c = j + y;
                } else if (a == 3) {
                    r = i + x;
                    c = y - j;

                }
            }
            ifHit=false;
            i=0;
            j=0;
        }
    }

    public void setCurrentMovement(String x){
        currentMovement=x;
    }
    public boolean getSide(){
        return side;
    }
    public int getType(){
        return 5;
    }
    public void setSpot(int x,int y){
        this.x=x;
        this.y=y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public ArrayList<BoardSpot> getPossibleMoves(){
        return possibleUNKNOWNMoves;
    }
    public ArrayList<BoardSpot> getPossibleCaptures(){
        return possibleCaptures;
    }
    public void showPossible(int[][]tempGrid){
        for(int i=0;i<possibleUNKNOWNMoves.size();i++){
            tempGrid[possibleUNKNOWNMoves.get(i).getX()][possibleUNKNOWNMoves.get(i).getY()]=2;

        }
        for(int i=0;i<possibleCaptures.size();i++){
            tempGrid[possibleCaptures.get(i).getX()][possibleCaptures.get(i).getY()]=10;
        }
    }



}
