package ssq;

import simulation.Event;
import simulation.Simulation;
import ssq.Service;
import java.util.Random;

public class SingleServerQueue extends Simulation<SingleServerQueue>{
  private double nextDepartureTime;
  private double timeEnd;
  private int populationCount;

  double totalLength;

  @Override
  protected boolean stop() {
    return scheduledEvents.size()<=0 || scheduledEvents.peek().getOffset() >= timeEnd;
  }

  @Override
  protected SingleServerQueue getState(){
    return this;
  }

  public void log(double length){
    totalLength += length;
  }

  public double getTimeEnd(){
    return timeEnd;
  }

  public double nextDepartureTime(){
    return nextDepartureTime;
  }

  public void incPopulation(){
    populationCount++;
  }
  public void decPopulation(){
    populationCount--;
  }
  public int getPopulation(){
    return populationCount;
  }
  public boolean isTillBusy(){
    return getPopulation() > 0;
  }

  public void scheduleDepart(double time){
    nextDepartureTime = time;
    schedule(new ServiceEnd(), time);
  }

  public double getMeanQueueLength(){
    return (double)totalLength/timeEnd;
  }

  public static void main(String[] args){
    assert args.length>0: "Please put the argument";

    SingleServerQueue ssq = new SingleServerQueue();

    Random rand = new Random(Long.parseLong(args[0]));
    ssq.timeEnd = Double.parseDouble(args[1]);

    double timeNow = rand.nextDouble();
    
    while (timeNow <= ssq.timeEnd){
      ssq.schedule(new Service(), timeNow);
      timeNow += rand.nextDouble(); //random inter-arrival time
    }
    ssq.simulate();
    System.out.println("SIMULATION COMPLETE - the mean queue length was "
        + ssq.getMeanQueueLength());

  }
}
