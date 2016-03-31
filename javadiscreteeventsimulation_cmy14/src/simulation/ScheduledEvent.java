package simulation;

public class ScheduledEvent<E> implements Comparable<ScheduledEvent>{
  private Event<E> event;
  private double offset;

  public ScheduledEvent(Event<E> event, double offset){
    this.event = event;
    this.offset = offset;
  }

  public Event<E> getEvent(){
    return event;
  }
  public double getOffset(){
    return offset;
  }

  public int compareTo(ScheduledEvent se){
    if(offset == se.offset){
      return 0;
    }else{
      return (offset < se.offset) ? -1 : 1;
    }
  }
}
