package turtle;

import turtle.util.*;

public class WrappingTurtle extends AbstractTurtle{

  public WrappingTurtle(int x, int y, Paper paper){
    this(x, y, DEFAULT_DIRECTION, DEFAULT_PEN, paper, DEFAULT_BRUSH);
  }
  public WrappingTurtle(int x, int y, Direction dir,
      Pen pen, Paper paper, char brush){
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
      if( x < 0 ){
        x += paper.getWidth();
      }else if( x >= paper.getWidth() ){
        x -= paper.getWidth();
      }
      if( y < 0 ){
        y += paper.getHeight();
      }else if ( y >= paper.getHeight() ){
        y -= paper.getHeight();
      }

      super.x = x;
      super.y = y;

      mark();
      return true;
    }
  }
}
