package musala.usecase;

import java.util.ArrayList;
import java.util.List;
import org.tinylog.Logger;
import musala.dto.DroneDispatchReqDTO;
import musala.dto.DroneRegReqDTO;
import musala.dto.LoadDroneReqDTO;
import musala.entity.Drone;
import musala.entity.Medication;
import musala.entity.Result;
import musala.exceptions.BussinessException;
import musala.repository.IDroneRepository;


/**
 * This class contains the various business requirements of the drone 
 */

public class DroneUseCases<U> {
	
	private static IDroneRepository droneRepo;
	
	public DroneUseCases (IDroneRepository droneRepo) {
		this.droneRepo=droneRepo;
	}
	
    /**
    * Returns a Result of SUCCESS or FAIL 
    * Method to Register a Drone
    * @param <T> The type of the DroneRegReqDTO
    * @return Result
    */
	
	public  Result<U> registerDrone (DroneRegReqDTO droneRegReqDTO) {	
		try {
			Result<DroneRegReqDTO> validationResult =DroneRegistrationValidation.validate(droneRegReqDTO);
			if(validationResult.isFailure) {
				return (Result<U>) validationResult;
			}
			Result<Drone> droneExistsResult=this.droneRepo.findOne(droneRegReqDTO.getSerialNumber());
			if(droneExistsResult.isFailure  ) {
				return (Result<U>) droneExistsResult;
			}
			if(droneExistsResult.getValue()!=null) {
				return (Result<U>) Result.FAIL(new BussinessException("EXIST",String.format("The drone with serialnumber %s already exists ", droneRegReqDTO.getSerialNumber()),"business",null));
			}
			Drone drone = new Drone();
			List<Medication> medications = new ArrayList<Medication>();
			drone.setBatteryCapacity(droneRegReqDTO.getBatteryCapacity());
			drone.setCurrentLoad(medications);
			drone.setModel(droneRegReqDTO.getModel());
			drone.setSerialNumber(droneRegReqDTO.getSerialNumber());
			drone.setState(droneRegReqDTO.getState());
			drone.setWeightLimit(droneRegReqDTO.getWeightLimit());
			Logger.info("Saving drone ");
			Result<Drone> savedroneResult=this.droneRepo.saveDrone(drone);
			Logger.info("Drone saved  ");
			return (Result<U>) savedroneResult;
		}
		catch (Exception ex) {
			 Logger.error(ex);
			return (Result<U>)Result.FAIL(ex);
		}
		
	}
	
    /**
    * Returns a Result of SUCCESS or FAIL 
    * Method to Dispatch a Drone
    * @param <T> The type of the droneDispatchReqDTO
    * @return Result
    */
	
	public Result<U> dispatchDrone (DroneDispatchReqDTO droneDispatchReqDTO) {	
		try {
			if(droneDispatchReqDTO.getSerialNumber()==null) {
				return (Result<U>) Result.FAIL(new BussinessException("drone_serial_required","The drone serialNumber is required ","business",null));
			}
			Result<Drone> droneExistsResult=this.droneRepo.findOne(droneDispatchReqDTO.getSerialNumber());
			if(droneExistsResult.isFailure  ) {
				return (Result<U>) droneExistsResult;
			}
			if(droneExistsResult.getValue()==null) {
				return (Result<U>) Result.FAIL(new BussinessException("not_exists",String.format("The drone with serialNumber %s does not exist ", droneDispatchReqDTO.getSerialNumber()),"business",null));
			}
			
			Drone drone = droneExistsResult.getValue();
			if(drone.getCurrentLoad().size() == 0) {
				return (Result<U>) Result.FAIL(new BussinessException("invalid_drone_load","The drone does not contain any load ","business",null));
			}
			if(drone.getState().equals("LOADED") || drone.getState().equals("LOADING") ) {		
				drone.setState("DELIVERING");
				Result<Drone> updateDroneResult=this.droneRepo.updateDrone(drone);			
				return (Result<U>) updateDroneResult;			
			}
			return (Result<U>) Result.FAIL(new BussinessException("invalid_status",String.format("The drone has invalid state for dispatch %s it should be LOADING OR LOADED ", drone.getState()),"business",null));
			
	
		}
		catch (Exception ex) {
			 Logger.error(ex);
			return (Result<U>)Result.FAIL(ex);
		}
		
	}
	
    /**
    * Returns a Result of SUCCESS or FAIL 
    * Method to Load a drone with medicine as the payload
    * @param <T> The type of the loadDroneReqDTO
    * @return Result
    */
	
	public Result<U> loadDrone (LoadDroneReqDTO loadDroneReqDTO) {	
		try {
			
			if(loadDroneReqDTO.getSerialNumber()==null) {
				return (Result<U>) Result.FAIL(new BussinessException("drone_serial_required","The drone serialNumber is required ","business",null));
			}
			
			if(loadDroneReqDTO.getLoad().size() == 0) {
				return (Result<U>) Result.FAIL(new BussinessException("load_required","At least one drone medical load is required ","business",null));
			}
			
			Result<Drone> droneExistsResult=this.droneRepo.findOne(loadDroneReqDTO.getSerialNumber());
			if(droneExistsResult.isFailure  ) {
				return (Result<U>) droneExistsResult;
			}
			if(droneExistsResult.getValue() == null) {
				return (Result<U>) Result.FAIL(new BussinessException("not_exists",String.format("The drone with serialnumber %s does not exists  ", loadDroneReqDTO.getSerialNumber()),"business",null));
			}
			
			for(Medication load : loadDroneReqDTO.getLoad())
			{
				Result<Medication> medvalidationResult = MedicineRegistrationValidation.validate(load.name, load.weight, load.code, load.image);
				if(medvalidationResult.isFailure) {
					return (Result<U>) medvalidationResult;
				}
			}
				
			Drone drone = droneExistsResult.getValue();
			
			if(drone.getState().equals("LOADED")) {
				return (Result<U>) Result.FAIL(new BussinessException("drone_loaded","The drone is already loaded  ","business",null));
			}
			      
			int currentTotalLoad = MedicineRegistrationValidation.calculateTotalWeightOfMedications(drone.getCurrentLoad());
			int newTotalLoad = MedicineRegistrationValidation.calculateTotalWeightOfMedications(loadDroneReqDTO.getLoad());
			
			int TotalLoadWeight = currentTotalLoad + newTotalLoad;
			if(TotalLoadWeight > drone.getWeightLimit()) {
				return (Result<U>) Result.FAIL(new BussinessException("drone_weight_limit","The total weight of the load exceeds the drone weight limit ","business",null));
			}
			
			if(TotalLoadWeight==drone.getWeightLimit()) {
				drone.setState("LOADED");
			}else {
				drone.setState("LOADING");
			}

			drone.getCurrentLoad().addAll(loadDroneReqDTO.getLoad());
			Result<Drone> updateDroneResult=this.droneRepo.updateDrone(drone);
			
			return (Result<U>) updateDroneResult;
		}
		catch (Exception ex) {
			 Logger.error(ex);
			return (Result<U>)Result.FAIL(ex);
		}
		
	}
	
    /**
    * Returns a Result of SUCCESS or FAIL 
    * Method to Fetch the payload of a particular drone
    * @param String serialNumber
    * @return Result
    */
	
	
	public Result<U> fetchDroneLoads (String serialNumber) {	
		try {
			if(serialNumber==null) {
				return (Result<U>) Result.FAIL(new BussinessException("drone_serial_required","The drone serialNumber is required ","business",null));
			}
			Result<Drone> droneExistsResult=this.droneRepo.findOne(serialNumber);
			if(droneExistsResult.isFailure  ) {
				return (Result<U>) droneExistsResult;
			}
			if(droneExistsResult.getValue()==null) {
				return (Result<U>) Result.FAIL(new BussinessException("not_exists",String.format("The drone with serialNumber %s does not exist ", serialNumber),"business",null));
			}
			Drone drone = droneExistsResult.getValue();
			
			return (Result<U>)Result.OK(drone.getCurrentLoad());
		}
		catch (Exception ex) {
			 Logger.error(ex);
			return (Result<U>)Result.FAIL(ex);
		}
		
	}
	
	
    /**
    * Returns a Result of SUCCESS or FAIL 
    * Method to Fetch the drone battery level of a particular drone
    * @param String serialNumber
    * @return Result
    */
	
	
	public Result<U> fetchDroneBattery (String serialNumber) {	
		try {
			if(serialNumber==null) {
				return (Result<U>) Result.FAIL(new BussinessException("drone_serial_required","The drone serialNumber is required ","business",null));
			}
			Result<Drone> droneExistsResult=this.droneRepo.findOne(serialNumber);
			if(droneExistsResult.isFailure  ) {
				return (Result<U>) droneExistsResult;
			}
			if(droneExistsResult.getValue()==null) {
				return (Result<U>) Result.FAIL(new BussinessException("not_exists",String.format("The drone with serialNumber %s does not exist ", serialNumber),"business",null));
			}
			Drone drone = droneExistsResult.getValue();
			
			return (Result<U>)Result.OK(drone.getBatteryCapacity());
		
		}
		catch (Exception ex) {
			Logger.error(ex);
			return (Result<U>)Result.FAIL(ex);
		}
		
	}

	
    /**
    * Returns a Result of SUCCESS or FAIL 
    * Method to List all Drones
    * @return Result
    */
	
	public Result<U> listAllDrones () {	
		return this.droneRepo.findAll();
	}
	
    /**
    * Returns a Result of SUCCESS or FAIL 
    * Method to List all Drones available 
    * @return Result
    */
	public Result<U> listAllAvailableDrones () {	
		return this.droneRepo.findAllAvailable();
	}




}
