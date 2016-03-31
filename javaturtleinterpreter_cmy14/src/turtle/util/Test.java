package turtle.util;

public class Test{
  public static void main(String[] args){
    Direction d = Direction.N;
    for(int i=0; i<10;i++){
      System.out.println(d);
      d = d.rotate(Rotation.LEFT, i);
    }
  }
}
