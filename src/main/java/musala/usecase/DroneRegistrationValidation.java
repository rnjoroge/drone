package musala.usecase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import musala.dto.DroneRegReqDTO;
import musala.entity.Drone;
import musala.entity.Result;
import musala.exceptions.BussinessException;


/**
 * This class contains the method for validating request for drone registration
 */

public class DroneRegistrationValidation {
	
	private static String droneModels[] = new String[] { "Lightweight", "Middleweight", "Cruiserweight", "Heavyweight"};
	private static String droneStatus[] = new String[] { "IDLE", "LOADING", "LOADED", "DELIVERING", "DELIVERED", "RETURNING" };
	
	 /**
     * Returns a Result of SUCCESS or FAIL 
     *
     * @param <T> The type of the DroneRegReqDTO
     * @return Result
     */
	
	
	public static <U> Result<U> validate (DroneRegReqDTO droneRegReqDTO) {
		try {
			
			List<String> errors = new ArrayList<String>();
	
			if(droneRegReqDTO.getSerialNumber()==null) {
				errors.add(" Drone serial number is required ");
			}
			
			if(droneRegReqDTO.getModel()==null) {
				errors.add(" Drone model is required ");
			}
			
			if(droneRegReqDTO.getState()==null) {
				errors.add(" Drone state is required ");
			}
			
			if(droneRegReqDTO.getBatteryCapacity() > 100) {
				droneRegReqDTO.setBatteryCapacity(100.00);
			}
			
			if(droneRegReqDTO.getWeightLimit()==null) {
				errors.add(" Drone weigthlimit is required ");
			}
			if(droneRegReqDTO.getWeightLimit() > 500) {
				errors.add(" The maximum drone weight limit should be 500 ");
			}
			
				
			int lengthOfSerialNumber = droneRegReqDTO.getSerialNumber().replace(" ", "").length();
			if(lengthOfSerialNumber > 100) {
				errors.add(" Length of drone serial number should max 100 characters ");
			}
			boolean modelMatch = Arrays.stream(droneModels).anyMatch(droneRegReqDTO.getModel()::equals);
			if(!modelMatch) {
				errors.add(" Drone Model should be either of  Lightweight, Middleweight, Cruiserweight, Heavyweight ");
			}
			
			boolean statusMatch = Arrays.stream(droneStatus).anyMatch(droneRegReqDTO.getState()::equals);
			if(!statusMatch) {
				errors.add(" Drone status should be either of  IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING ");
			}
			
			if(droneRegReqDTO.getState().equals("LOADING") && droneRegReqDTO.getBatteryCapacity() <=25) {
				errors.add(" Drone in status LOADING should have a battery level of above 25% ");
			}

			if(errors.size() > 0) {		
				return (Result<U>)Result.FAIL(new BussinessException("drone_validation","Error in drone validation","validation",errors));
			}
			
			return (Result<U>)Result.OK(droneRegReqDTO);
		}
		catch (Exception ex) {
			return (Result<U>)Result.FAIL(ex);
		}
		
	}

}
