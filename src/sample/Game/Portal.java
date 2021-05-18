package sample.Game;

public class Portal {
    private boolean type;//if true then it is a end portal, but if it is false then it is a start portal
    private int x;
    private int y;
    private int numPortals=6;
    private boolean ifCanPortal=true;
    private Portal startPortal;
    public Portal(int x,int y, boolean type) {
        this.x = x;
        this.y = y;
        this.type=type;
    }
    public Portal(int x,int y, boolean type,Portal start) {
        this.x = x;
        this.y = y;
        this.type=type;
        startPortal=start;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void addTurn(){
        numPortals--;
        if(numPortals==0){
            ifCanPortal=false;
        }
    }
    public boolean ifPortal(){
        return ifCanPortal;
    }


}
