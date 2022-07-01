package musala.repository;

import java.util.Date;
import java.util.List;

import musala.entity.Drone;
import musala.entity.Result;




public interface IDroneRepository<U> {
	
	Result<U> saveDrone (Drone drone)  ;
	Result<U> updateDrone (Drone drone)  ;
	Result<U> findOne(String serialNumber)  ;
	Result<U> findAll ()  ;
	Result<U> findAllAvailable ()  ;

}
