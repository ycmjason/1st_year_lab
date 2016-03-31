public class Bowl{
    private int stones;
    private int stack;
    
    public Bowl(int stones){
        this.stones = stones;
        stack = 0;
    }

    public int getStones(){
        return stones;
    }

    public int takeAllStones(){
        int originalStones = stones;
        stones = 0;
        return originalStones;
    }
    
    public void depositeStone(){
        stack++;
    }
    public int updateAndGetScore(){
        stones += stack;
        stack = 0;
        return stones;
    }
}
