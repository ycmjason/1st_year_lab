package turtle;

import turtle.util.*;

public class ClusterTurtle implements Turtle{
  Turtle[] turtles;

  public ClusterTurtle(Turtle[] turtles){
    this.turtles = turtles;
  }

  @Override
  public void setPen(Pen p){
    for(Turtle t : turtles){
      t.setPen(p);
    }
  }
  @Override
  public void setBrush(char c){
    for(Turtle t : turtles){
      t.setBrush(c);
    }
  }
  @Override
  public void rotate(Rotation r, int times){
    for(Turtle t : turtles){
      t.rotate(r,times);
    }
  }
  @Override
  public void move(int n){
    for(Turtle t : turtles){
      t.move(n);
    }
  }
}
