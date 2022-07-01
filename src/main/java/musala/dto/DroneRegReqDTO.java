package musala.dto;

public class DroneRegReqDTO {
	
	   private String serialNumber;
	   private String model;
	   private Integer weightLimit;
	   private double batteryCapacity;
	   private String state;
	
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Integer getWeightLimit() {
		return weightLimit;
	}
	public void setWeightLimit(Integer weightLimit) {
		this.weightLimit = weightLimit;
	}
	public double getBatteryCapacity() {
		return batteryCapacity;
	}
	public void setBatteryCapacity(double batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}


}
