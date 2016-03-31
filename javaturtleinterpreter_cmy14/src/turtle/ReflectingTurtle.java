package turtle;

import turtle.util.*;

public class ReflectingTurtle extends AbstractTurtle{

  public ReflectingTurtle(int x, int y, Paper paper){
    this(x, y, DEFAULT_DIRECTION, DEFAULT_PEN, paper, DEFAULT_BRUSH);
  }
  public ReflectingTurtle(int x, int y, Direction dir, Pen pen, Paper paper, char brush){
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
      switch (direction){
        case N:
        case E:
        case S:
        case W:
          direction = direction.bounce();
          break;

        case NE:
          if(paper.isTop(super.x, super.y)){

            if(paper.isRight(super.x, super.y)){
              direction = direction.bounce();
            }else{
              direction = Direction.SE;
              super.x++;
            }

          }else{
            direction = Direction.NW;
            super.y++;
          }
          break;

        case SE:
          if(paper.isBottom(super.x, super.y)){

            if(paper.isRight(super.x, super.y)){
              direction = direction.bounce();
            }else{
              direction = Direction.NE;
              super.x ++;
            }

          }else{
            direction = Direction.SW;
            super.y--;
          }
          break;

        case NW:
          if(paper.isTop(super.x, super.y)){

            if(paper.isLeft(super.x, super.y)){
              direction = direction.bounce();
            }else{
              direction = Direction.SW;
              super.x --;
            }

          }else{
            direction = Direction.NE;
            super.y++;
          }
          break;

        case SW:
        default:
          if(paper.isBottom(super.x, super.y)){

            if(paper.isLeft(super.x, super.y)){
              direction = direction.bounce();
            }else{
              direction = Direction.NW;
              super.x --;
            }

          }else{
            direction = Direction.SE;
            super.y--;
          }
          break;
      }
      mark();
      return true;
    }
  }
}
