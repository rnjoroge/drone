package musala_drone.musal_drone;

import io.undertow.Undertow;
import io.undertow.server.RoutingHandler;
import musala.services.DroneService;
import musala.services.RouteHandler;


/**
 * 
 * The Main entry point of the applications
 *
 */


public class App 
{
	public static void main(String[] args) {
		
	     new DroneService();
	     
		  RoutingHandler Handler =  new RoutingHandler()
			 .post("/api/v1/drone/registration", RouteHandler.registerDrone())	
			 .post("/api/v1/drone/load", RouteHandler.loadDrone())
			 .post("/api/v1/drone/dispatch", RouteHandler.dispatchDrone())
			 
			 .get("/api/v1/drone/available", RouteHandler.listAllAvailableDrones())		 
			 .get("/api/v1/drone/list",RouteHandler.listAllDrones())
			 .get("/api/v1/drone/load/{serialNumber}",RouteHandler.fetchDroneLoads())
			 .get("/api/v1/drone/battery/{serialNumber}",RouteHandler.fetchDroneBattery())	
		     .setFallbackHandler(RouteHandler.notFoundHandler());
				  
	      Undertow server = Undertow.builder()
	                 .addHttpListener(8383, "0.0.0.0",Handler)
	                 .build();
	              server.start();

		}
}
