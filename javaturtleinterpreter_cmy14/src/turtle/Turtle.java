package turtle;

import turtle.util.*;

public interface Turtle{
  void setPen(Pen p);
  void setBrush(char c);
  void rotate(Rotation r, int times);
  void move(int n);
}
