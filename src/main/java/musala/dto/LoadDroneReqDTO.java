package musala.dto;

import java.util.List;

import musala.entity.Medication;

public class LoadDroneReqDTO {
	
	private String serialNumber;
	private List<Medication> load ;

	
	 public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public List<Medication> getLoad() {
		return load;
	}
	public void setLoad(List<Medication> load) {
		this.load = load;
	}

}
