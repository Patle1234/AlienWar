package sample.Game;

public class Player {
    private int rankScore;
    private String userName;
    private int wins;
    private int losses;
    public Player(String name, int score,int w, int l){
        rankScore=score;
        userName=name;
        wins=w;
        losses=l;
    }

    public int getWins(){
        return wins;
    }
    public int getLosses(){
        return losses;
    }
    public String getUserName(){
        return userName;
    }
    public int getRankScore(){
        return rankScore;
    }
}
