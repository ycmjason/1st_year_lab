package turtle;

import turtle.util.*;

public class BouncyTurtle extends AbstractTurtle{

  public BouncyTurtle(int x, int y, Paper paper){
    this(x, y, DEFAULT_DIRECTION, DEFAULT_PEN, paper, DEFAULT_BRUSH);
  }
  public BouncyTurtle(int x, int y, Direction dir, Pen pen, Paper paper, char brush){
    super(x,y,dir,pen,paper,brush);
  }

  @Override
  protected boolean outOfBoundHandler(int x, int y){
    if (paper.isInBound(x,y)){
      super.x=x;
      super.y=y;
      mark();
      return true;
    }else {
      direction = direction.bounce();
      mark();
      return true;
    }
  }
}
