package turtle;

public class Paper{
  private static final int DEFAULT_GRID_SIZE=10;
  private char[][] grid;
  public Paper(){
    this(DEFAULT_GRID_SIZE,DEFAULT_GRID_SIZE);
  }
  public Paper(int width, int height){
    grid = new char[width][height];
    for (int i=0; i<width; i++){
      for (int j=0; j<height; j++){
        grid[i][j]= ' ';
      }
    }

  }
  public int getWidth(){
    return grid.length;
  }
  public int getHeight(){
    return grid[0].length;
  }
  public boolean isLeft(int x, int y){
    return x==0;
  }
  public boolean isRight(int x, int y){
    return x==getWidth()-1;
  }
  public boolean isTop(int x, int y){
    return y==getHeight()-1;
  }
  public boolean isBottom(int x, int y){
    return y==0;
  }
  public boolean isInBound(int x, int y){
    if(x>=0 && x<getWidth() &&
       y>=0 && y<getHeight()){
      return true;
    }else{
      return false;
    }
  }
  public void mark(int x, int y, char c){
    if(!isInBound(x,y)){ return;}
    grid[x][y]=c;
  }
  public String toString(){
    String s="";
    for(int y=getHeight()-1; y>=0; y--){
      for(int x=0; x<getWidth(); x++){
        s+=grid[x][y];
      }
      if(y!=0){
        s+='\n';
      }
    }
    return s;
  }
}
