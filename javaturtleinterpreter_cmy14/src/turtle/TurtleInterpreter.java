package turtle;

import java.util.Scanner;
import java.io.PrintStream;
import java.util.Map;
import java.util.HashMap;

import turtle.util.*;

public class TurtleInterpreter{
  private static final int ANGLE_BASE_MUTIPLE = 45;
  private Scanner commands;
  private PrintStream outputStream;
  private Paper paper = new Paper();
  private Map<String, Turtle> turtles = new HashMap<String, Turtle>();

  public TurtleInterpreter(Scanner input, PrintStream output){
    commands    = input;
    outputStream= output;
  }
  
  public void execute(){
    while(commands.hasNext()){
      switch(commands.next()){
        case "paper":
          executePaper(commands.nextInt(), commands.nextInt());
          break;
        case "new":
          String type = commands.next();
          String name = commands.next();
          executeNew(type, name);
          break;
        case "pen":
          executePen(commands.next(),commands.next());
          break;
        case "move":
          executeMove(commands.next(),commands.nextInt());
          break;
        case "left":
          executeRotate(commands.next(), Rotation.LEFT,
              (int) (commands.nextInt()/ANGLE_BASE_MUTIPLE));
          break;
        case "right":
          executeRotate(commands.next(), Rotation.RIGHT,
              (int) (commands.nextInt()/ANGLE_BASE_MUTIPLE));
          break;
        case "show":
          executeShow();
          break;
      }
    }
  }

  public void executePaper(int width, int height){
    paper = new Paper(width, height);
  }

  public Turtle executeNew(String type, String name){
    Turtle turtle;

    switch(type){
      case "cluster":
        turtle = newClusterTurtle(name, commands.nextInt());
        break;

      default:
        turtle = newAbstractTurtle(type, commands.nextInt(), commands.nextInt());
        break;
    }

    turtles.put(name, turtle);

    return turtle;
  }

  private Turtle newAbstractTurtle(String type, int x, int y){
    switch(type){
      case "continuous":
        return new ContinuousTurtle(x,y,paper);

      case "bouncy":
        return new BouncyTurtle(x,y,paper);

      case "reflecting":
        return new ReflectingTurtle(x,y,paper);

      case "wrapping":
        return new WrappingTurtle(x,y,paper);

      case "normal":
      default:
        return new NormalTurtle(x,y,paper);
    }
  }

  private Turtle newClusterTurtle(String name, int n){
    Turtle[] children = new Turtle[n];

    for(int i=0; i<n; i++){
      switch(commands.next()){
        case "new":
          children[i] = executeNew(commands.next(),
              clusterChildNamingScheme(name, commands.next()));

          break;

        default:
          i--;
          break;
      }
    }
    return new ClusterTurtle(children);
  }

  private String clusterChildNamingScheme(String mother, String child){
    return mother+"."+child;
  }

  

  public void executePen(String name, String s){
    Turtle t = turtles.get(name);
    switch(s){
      case "up":
        t.setPen(Pen.UP);
        break;
      case "down":
        t.setPen(Pen.DOWN);
        break;
      default:
        t.setBrush(s.charAt(0));
        break;
    }
  }

  public void executeMove(String name, int distance){
    turtles.get(name).move(distance);
  }
  public void executeRotate(String name, Rotation r, int times){
    turtles.get(name).rotate(r, times);    

  }
  public void executeShow(){
    outputStream.print(paper);
    outputStream.println();
    outputStream.println();
  }
}
