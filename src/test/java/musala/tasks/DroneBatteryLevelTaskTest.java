package musala.tasks;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Timer;

import org.junit.jupiter.api.Test;

import musala.entity.Drone;
import musala.entity.Result;
import musala.repository.DroneRepoImpl;

class DroneBatteryLevelTaskTest {

	@Test
	void testDroneBatteryLevelTask() {
		
	   	try {
	   		DroneRepoImpl repo = new DroneRepoImpl();
		   	Drone drone = new Drone();
			  drone.setBatteryCapacity(100.0);
	 	      drone.setModel("Lightweight");
	 	      drone.setSerialNumber("testSerial");
	 	      drone.setWeightLimit(100);
	 	      drone.setState("IDLE");
	 	  
	 	    repo.saveDrone(drone);
	 	      drone.setSerialNumber("testSerial1");
	 	     repo.saveDrone(drone);  
	 	     
	 	    long delay = 1000L;
		   	new Timer().schedule(new DroneBatteryLevelTask(repo), delay);
			Thread.sleep(delay * 10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
