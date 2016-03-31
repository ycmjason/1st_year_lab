package turtle;

import turtle.util.*;

public abstract class AbstractTurtle implements Turtle{
  protected static final char DEFAULT_BRUSH='*';
  protected static final Pen DEFAULT_PEN=Pen.UP;
  protected static final Direction DEFAULT_DIRECTION=Direction.N;

  protected int x,y;
  protected char brush;
  protected Direction direction;
  protected Pen pen;
  protected Paper paper;

  public AbstractTurtle(int x, int y, Paper paper){
    this(x, y, DEFAULT_DIRECTION, DEFAULT_PEN, paper, DEFAULT_BRUSH);
  }
  public AbstractTurtle(int x, int y, Direction dir, Pen pen,
      Paper paper, char brush){
    this.x=x;
    this.y=y;
    this.direction=dir;
    this.pen=pen;
    this.paper=paper;
    this.brush=brush;
  }

  @Override
  public void setPen(Pen p){
    pen = p;
  }
  @Override
  public void setBrush(char c){
    brush = c;
  }
  @Override
  public void rotate(Rotation r, int times){
    direction = direction.rotate(r, times);
  }
  @Override
  public void move(int n){
    for(int i=0; i<n && moveOne(); i++);
  }

  //return true if move is valid
  private boolean moveOne(){
    int x=this.x, y=this.y;
    switch(direction){
      case N:
        y+=1;
        break;
      case NE:
        y+=1;
        x+=1;
        break;
      case E:
        x+=1;
        break;
      case SE:
        y-=1;
        x+=1;
        break;
      case S:
        y-=1;
        break;
      case SW:
        y-=1;
        x-=1;
        break;
      case W:
        x-=1;
        break;
      case NW:
        y+=1;
        x-=1;
        break;
    }
    return outOfBoundHandler(x, y);
  }
  protected void mark(){
    if (pen!=Pen.DOWN){ return;}
    paper.mark(x,y,brush);
  }

  abstract boolean outOfBoundHandler(int x, int y);
}
