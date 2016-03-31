package ticks;

import simulation.Event;
import simulation.Simulation;

public class Tick implements Event<Ticks>{
  @Override
  public void invoke(Ticks simulation) {
    System.out.println("Tick at: " + simulation.getCurrentTime());
  }
}
