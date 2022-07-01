package musala.repository;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.tinylog.Logger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import musala.entity.Drone;
import musala.entity.Result;
import musala.exceptions.BussinessException;






public class DroneRepoImpl<U> implements IDroneRepository {
	
	private List<Drone> droneList = new ArrayList<Drone>();

	@Override
	public Result<U> saveDrone(Drone drone) {
		droneList.add(drone);
		return (Result<U>)Result.OK(drone);
	}

	@Override
	public Result<U> updateDrone(Drone drone) {
	   int indx = this.indeofObj(drone);
	   if(indx == -1) {
		   return (Result<U>) Result.FAIL(new BussinessException("NOT_EXIST",String.format("The drone with serialnumber %s does not exists ", drone.getSerialNumber()),"database",null));
	   }else {
		   droneList.set(indx, drone);
		   return (Result<U>) Result.OK(drone);
	   }
	}

	@Override
	public Result<U> findOne(String serialNumber) {
		   for (int counter = 0; counter < this.droneList.size(); counter++) { 		      
	           if (this.droneList.get(counter).getSerialNumber().equals(serialNumber)) {		
	        	   return (Result<U>) Result.OK(this.droneList.get(counter));
	           }
	     } 
		   return (Result<U>) Result.OK(null);
	}

	@Override
	public Result<U> findAll() {

		return (Result<U>)Result.OK(this.droneList);
	}
	
	private int indeofObj (Drone drone) {
		
		   for (int counter = 0; counter < this.droneList.size(); counter++) { 		      
		           if (this.droneList.get(counter).getSerialNumber().equals(drone.getSerialNumber())) {		
		        	   return counter;
		           }
		     } 
		   return -1;
		
	}

	@Override
	public Result<U>  findAllAvailable() {
		// TODO Auto-generated method stub
		 Predicate<Drone> bystatus = drone -> drone.getState().equals("IDLE");
		 List<Drone> filteredByResult = droneList.stream()
				    .filter(act -> Objects.nonNull(act))
				    .filter(bystatus)
	                .collect(Collectors.toList());
		 
		 return (Result<U>)Result.OK(filteredByResult);
	}
	

}
