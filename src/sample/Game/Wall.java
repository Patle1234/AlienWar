package sample.Game;

public class Wall {
    private int x;
    private int y;
    private int numTurns;

    public Wall(int x,int y) {
        this.x = x;
        this.y = y;
        numTurns=0;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void addTurn(){
        numTurns++;
    }
    public int getNumTurns(){
        return numTurns;
    }
}

