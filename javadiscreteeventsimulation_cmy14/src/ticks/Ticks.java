package ticks;

import simulation.Simulation;
import ticks.Tick;

public class Ticks extends Simulation<Ticks>{
  @Override
  protected boolean stop() {
    return scheduledEvents.size() <= 0;
  }
  @Override
  protected Ticks getState(){
    return this;
  }

  public static void main(String[] args){
    assert args.length>0: "Please put the argument";
    int n = Integer.parseInt(args[0]);
    Ticks ticks = new Ticks();
    for (int i = 1; i < n; i++){
      ticks.schedule(new Tick(), i);
    }
    ticks.simulate();
  }
}
