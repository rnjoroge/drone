package musala.usecase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import musala.dto.DroneRegReqDTO;
import musala.entity.Drone;
import musala.entity.Result;
import musala.repository.DroneRepoImpl;

class DroneUseCasesTest {



	@Test
	@DisplayName("TestCase usecase register drone")
	void testRegisterDrone() {
 
		DroneRepoImpl repo = new DroneRepoImpl();
		DroneUseCases droneUseCases = new DroneUseCases(repo);
		DroneRegReqDTO droneRegReqDTO = new DroneRegReqDTO();
		
		droneRegReqDTO.setBatteryCapacity(0.0);
		droneRegReqDTO.setModel("test");
		droneRegReqDTO.setState("state");
		droneRegReqDTO.setWeightLimit(1000);
		
		Result<Drone> registerDroneResult =droneUseCases.registerDrone(droneRegReqDTO);
		assertEquals(true,  registerDroneResult.isFailure);
		

	}

	@Test
	@DisplayName("TestCase usecase dispatch drone")
	void testDispatchDrone() {
		assertEquals(true,  true);
	}

	@Test
	@DisplayName("TestCase usecase load drone")
	void testLoadDrone() {
		assertEquals(true,  true);
	}

	@Test
	@DisplayName("TestCase usecase fetch drone loads")
	void testFetchDroneLoads() {
		assertEquals(true,  true);
	}

	@Test
	@DisplayName("TestCase usecase fetch drone batery")
	void testFetchDroneBattery() {
		assertEquals(true,  true);
	}

}
