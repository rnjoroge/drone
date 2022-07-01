package musala.entity;

public class EventLog {
	
	public String droneSerialNumber ;
	public double droneBatteryCapacity ;
	public String eventName ;
	public String eventDateTime ;
	
	
	public String getDroneSerialNumber() {
		return droneSerialNumber;
	}
	public void setDroneSerialNumber(String droneSerialNumber) {
		this.droneSerialNumber = droneSerialNumber;
	}
	public double getDroneBatteryCapacity() {
		return droneBatteryCapacity;
	}
	public void setDroneBatteryCapacity(double droneBatteryCapacity) {
		this.droneBatteryCapacity = droneBatteryCapacity;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventDateTime() {
		return eventDateTime;
	}
	public void setEventDateTime(String eventDateTime) {
		this.eventDateTime = eventDateTime;
	}
	
}
