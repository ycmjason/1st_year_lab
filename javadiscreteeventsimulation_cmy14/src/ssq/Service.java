package ssq;

import simulation.Event;
import simulation.Simulation;

import ssq.SingleServerQueue;

class Service implements Event<SingleServerQueue> {
  private final double SERVICE_DURATION = 0.25;

  @Override
  public void invoke(SingleServerQueue ssq) {
    double currentTime = ssq.getCurrentTime();
    double serviceExecuteTime, departTime;

    //schedule depart
    serviceExecuteTime = (ssq.isTillBusy()) ? ssq.nextDepartureTime() : currentTime;
    departTime = serviceExecuteTime + SERVICE_DURATION;
    if (departTime > ssq.getTimeEnd()){
      departTime = ssq.getTimeEnd();
    }
    ssq.scheduleDepart(departTime);


    ssq.incPopulation();
    ssq.log(departTime-currentTime);
    System.out.println("Arrival at " + currentTime +
        ", new population = " + ssq.getPopulation());

  }

}

class ServiceEnd implements Event<SingleServerQueue> {
  @Override
  public void invoke(SingleServerQueue ssq) {
    ssq.decPopulation();
    System.out.println("Departure at " + ssq.getCurrentTime() +
        ", new population = " + ssq.getPopulation());
  }

}
