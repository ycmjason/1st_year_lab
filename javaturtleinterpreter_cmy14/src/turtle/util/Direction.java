package turtle.util;

public enum Direction {
  N,NE,E,SE,S,SW,W,NW;
  private static final int NO_OF_DIRECTIONS = 8;

  public Direction rotate(Rotation r, int n){
    Direction d = this;
    for (int i=0; i<n; i++){
      d = d.rotateOne(r);
    }
    return d;
  }
  
  private Direction rotateOne(Rotation r){
    Direction[] sequence = new Direction[]{N,NE,E,SE,S,SW,W,NW};
    Direction impossibleReturn = N;
    for(int i=0; i<NO_OF_DIRECTIONS; i++){
      if(this==sequence[i]){

        if(i==0 && r==Rotation.LEFT){
          return sequence[NO_OF_DIRECTIONS-1];
        }

        if(i==NO_OF_DIRECTIONS-1 && r==Rotation.RIGHT){
          return sequence[0];
        }

        return r==Rotation.RIGHT?sequence[i+1]:sequence[i-1];

      }
    }
    assert false;
    return impossibleReturn;
  }
  public Direction bounce(){
    return rotate(Rotation.LEFT,4);
  }
}
