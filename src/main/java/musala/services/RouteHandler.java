package musala.services;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;


public class RouteHandler {
	
	
	
	/* Drone Routes */	   
    public static HttpHandler registerDrone() {
        return DroneService.registerDrone();
    }
    public static HttpHandler listAllDrones() {
        return DroneService.listAllDrones();
    }
    
    public static HttpHandler loadDrone() {
        return DroneService.loadDrone();
    }
    
    public static HttpHandler fetchDroneLoads() {
        return DroneService.fetchDroneLoads();
    }
    
    public static HttpHandler fetchDroneBattery() {
        return DroneService.fetchDroneBattery();
    }
    
    public static HttpHandler listAllAvailableDrones() {
        return DroneService.listAllAvailableDrones();
    }
    
    public static HttpHandler dispatchDrone() {
        return DroneService.dispatchDrone();
    }
    
    
	
    
    public static HttpHandler notFoundHandler() {
        return (HttpServerExchange exchange) -> { 
            exchange.setStatusCode(404);
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
            exchange.getResponseSender().send("{\"error\": \"Invalid , Api not found\"}");
        };
    }
    

}
