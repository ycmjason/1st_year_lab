package simulation;

import java.util.Queue;
import java.util.PriorityQueue;

public abstract class Simulation<S>{
  private double virtualTime;
  protected Queue<ScheduledEvent<S>> scheduledEvents = new PriorityQueue<ScheduledEvent<S>>();

  protected abstract boolean stop();

  public void schedule(Event<S> e, double offset){
    ScheduledEvent<S> se = new ScheduledEvent<S>(e, offset);
    scheduledEvents.offer(se);
  }

  public void simulate(){
    ScheduledEvent<S> se;
    while (!stop()){
      se = scheduledEvents.poll();
      virtualTime = se.getOffset();
      se.getEvent().invoke(getState());
    }
  }

  protected abstract S getState();

  public double getCurrentTime(){
    return virtualTime;
  }
}
