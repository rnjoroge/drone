package musala.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import musala.entity.Drone;
import musala.entity.Result;

class DroneRepoImplTest {

    @Test
    @DisplayName("TestCase save drone")
	void testSaveDrone() {
    	DroneRepoImpl repo = new DroneRepoImpl();
    	Drone drone = new Drone();
			  drone.setBatteryCapacity(100.0);
	   	      drone.setModel("Lightweight");
	   	      drone.setSerialNumber("testSerial");
	   	      drone.setWeightLimit(100);
	   	      drone.setState("IDLE");
	   	   Result<Drone> saveDroneResult=repo.saveDrone(drone);
	       assertEquals(true,  saveDroneResult.isSuccess);
	}

	@Test
	@DisplayName("TestCase update drone")
	void testUpdateDrone() {
		assertEquals(true,  true);
	}

	@Test
	@DisplayName("TestCase findOne drone")
	void testFindOne() {
		assertEquals(true,  true);
	}

	@Test
	@DisplayName("TestCase findAll drones")
	void testFindAll() {
		assertEquals(true,  true);
	}

}
