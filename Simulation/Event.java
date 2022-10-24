package Simulation;

public class Event {
	
	//data fields
	private String eventType;
	private int userID;
	private int clock;
	
	public Event() {
		this(0, 0, null);
	}
	
	public Event(int clock, int id, String eventType) {
		this.clock = clock;
		this.userID = id;
		this.eventType = eventType;
	}
	
	public String getType() {
		return this.eventType;
	}
	
	public int getClock() {
		return this.clock;
	}
	
	public String toString() {
		return "type: " + eventType + ", ID: " + userID + ", clock: " +  clock;
	}
	
	public int getID() {
		return this.userID;
	}
}
