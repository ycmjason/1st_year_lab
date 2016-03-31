public class Game{
    private Bowl[] bowls = new Bowl[12];
    private Player[] players = new Player[2];
    private int turn = 0;
    
    public int getCurrentPlayer(){
        return turn+1;
    }

    public void swapPlayers(){
        turn^=1;
    }

    public boolean isValidMove(int bowl){
        return (turn*6+1 <= bowl && bowl <= (turn+1)*6)
                && bowl>0;
    }
    public boolean canCurrentPlayerMove(){
        
    }
}
