package turtle;

import turtle.util.*;

public class ContinuousTurtle extends AbstractTurtle{

  public ContinuousTurtle(int x, int y, Paper paper){
    this(x, y, DEFAULT_DIRECTION, DEFAULT_PEN, paper, DEFAULT_BRUSH);
  }
  public ContinuousTurtle(int x, int y, Direction dir, Pen pen, Paper paper, char brush){
    super(x,y,dir,pen,paper,brush);
  }

  @Override
  protected boolean outOfBoundHandler(int x, int y){
    super.x=x;
    super.y=y;
    if (paper.isInBound(x,y)){
      mark();
      return true;
    }else {
      return true;
    }
  }
}
