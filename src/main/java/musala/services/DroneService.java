package musala.services;

import java.util.List;
import java.util.Timer;

import org.tinylog.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.undertow.io.Receiver.FullBytesCallback;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import musala.dto.DroneDispatchReqDTO;
import musala.dto.DroneRegReqDTO;
import musala.dto.LoadDroneReqDTO;
import musala.entity.Drone;
import musala.entity.Medication;
import musala.entity.Result;
import musala.repository.DroneRepoImpl;
import musala.tasks.DroneBatteryLevelTask;
import musala.usecase.DroneUseCases;



public class DroneService {
	
	static DroneRepoImpl repo = new DroneRepoImpl();
	static DroneUseCases droneUseCases = new DroneUseCases(repo);
	
	public DroneService () {
		new Timer().schedule(new DroneBatteryLevelTask(repo), 0, 3000);
	}
	
    public static HttpHandler registerDrone() {
        return (HttpServerExchange exchange) -> {
        	try {    
                exchange.getRequestReceiver().receiveFullBytes(new FullBytesCallback() {
                    @Override
                    public void handle(HttpServerExchange exchange, byte[] message) {  
                       	try {
                       		DroneRegReqDTO droneRegReqDTO= new ObjectMapper().readValue(new String(message), DroneRegReqDTO.class);
                       		Result<Drone> registerDroneResult = droneUseCases.registerDrone(droneRegReqDTO);
                       		if(registerDroneResult.isSuccess) {	
                       		   ResponseHandler.successResponse(exchange,"{\"message\": \"Drone registration success"+"\"}");
                       		}else {
                       			ResponseHandler.businessErrorResponse(exchange, registerDroneResult.errorValue());
                       		}
				         
						} catch (Exception e) {
							 Logger.error(e);
			        		 ResponseHandler.errorResponse(exchange, e);
						}                   
                    }  
                });        
        	}
        	catch (Exception e) {
        		 Logger.error(e);
        		 exchange.getResponseSender().send(e.getMessage());
        	}

            
        };
    }
    
    public static HttpHandler loadDrone() {
        return (HttpServerExchange exchange) -> {
        	try {    
                exchange.getRequestReceiver().receiveFullBytes(new FullBytesCallback() {
                    @Override
                    public void handle(HttpServerExchange exchange, byte[] message) {  
                       	try {
                       		LoadDroneReqDTO loadDroneReqDTO= new ObjectMapper().readValue(new String(message), LoadDroneReqDTO.class);
                       		Result<Drone> loadDroneResult = droneUseCases.loadDrone(loadDroneReqDTO);
                       		if(loadDroneResult.isSuccess) {	
                       		   ResponseHandler.successResponse(exchange,"{\"message\": \"Drone loaded successfully"+"\"}");
                       		}else {
                       			ResponseHandler.businessErrorResponse(exchange, loadDroneResult.errorValue());
                       		}
				         
						} catch (Exception e) {
							 Logger.error(e);
			        		 ResponseHandler.errorResponse(exchange, e);
						}                   
                    }  
                });        
        	}
        	catch (Exception e) {
        		 Logger.error(e);
        		 exchange.getResponseSender().send(e.getMessage());
        	}

            
        };
    }
    
    
    
    public static HttpHandler dispatchDrone() {
        return (HttpServerExchange exchange) -> {
        	try {    
                exchange.getRequestReceiver().receiveFullBytes(new FullBytesCallback() {
                    @Override
                    public void handle(HttpServerExchange exchange, byte[] message) {  
                       	try {
                       		DroneDispatchReqDTO droneDispatchReqDTO= new ObjectMapper().readValue(new String(message), DroneDispatchReqDTO.class);
                       		Result<Drone> dispatchDroneResult = droneUseCases.dispatchDrone(droneDispatchReqDTO);
                       		if(dispatchDroneResult.isSuccess) {	
                       		   ResponseHandler.successResponse(exchange,"{\"message\": \"Drone dispatched successfully"+"\"}");
                       		}else {
                       			ResponseHandler.businessErrorResponse(exchange, dispatchDroneResult.errorValue());
                       		}
				         
						} catch (Exception e) {
							 Logger.error(e);
			        		 ResponseHandler.errorResponse(exchange, e);
						}                   
                    }  
                });        
        	}
        	catch (Exception e) {
        		 Logger.error(e);
        		 exchange.getResponseSender().send(e.getMessage());
        	}

            
        };
    }
    
    
    
    
	public static HttpHandler listAllDrones() {
		return (HttpServerExchange exchange) -> {
			try {
				Result<List<Drone>> droneListResult =droneUseCases.listAllDrones();
				if(droneListResult.isSuccess) {
					 ObjectMapper objectMapper = new ObjectMapper();
					ResponseHandler.successResponse(exchange, objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(droneListResult.getValue()));
				}else {
					ResponseHandler.businessErrorResponse(exchange, droneListResult.errorValue());	
				}
				
			} catch (Exception e) {
				Logger.error(e);
				exchange.getResponseSender().send(e.getMessage());
			}

		};
	}
	
	
	public static HttpHandler listAllAvailableDrones() {
		return (HttpServerExchange exchange) -> {
			try {
				Result<List<Drone>> droneListResult =droneUseCases.listAllAvailableDrones();
				if(droneListResult.isSuccess) {
					 ObjectMapper objectMapper = new ObjectMapper();
					ResponseHandler.successResponse(exchange, objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(droneListResult.getValue()));
				}else {
					ResponseHandler.businessErrorResponse(exchange, droneListResult.errorValue());	
				}
				
			} catch (Exception e) {
				Logger.error(e);
				exchange.getResponseSender().send(e.getMessage());
			}

		};
	}
	
	
	
	public static HttpHandler fetchDroneLoads() {
		return (HttpServerExchange exchange) -> {
			try {
				String serialNumber = exchange.getQueryParameters().get("serialNumber").getFirst();
				Result<List<Medication>> droneLoadResult =droneUseCases.fetchDroneLoads(serialNumber);
				if(droneLoadResult.isSuccess) {
					 ObjectMapper objectMapper = new ObjectMapper();
					ResponseHandler.successResponse(exchange, objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(droneLoadResult.getValue()));
				}else {
					ResponseHandler.businessErrorResponse(exchange, droneLoadResult.errorValue());	
				}
				
			} catch (Exception e) {
				Logger.error(e);
				exchange.getResponseSender().send(e.getMessage());
			}

		};
	}
	
	
	public static HttpHandler fetchDroneBattery() {
		return (HttpServerExchange exchange) -> {
			try {
				String serialNumber = exchange.getQueryParameters().get("serialNumber").getFirst();
				Result<List<Medication>> droneBatteryResult =droneUseCases.fetchDroneBattery(serialNumber);
				if(droneBatteryResult.isSuccess) {
					 ResponseHandler.successResponse(exchange,"{\"battery_level\": \""+droneBatteryResult.getValue()+"\"}");

				}else {
					ResponseHandler.businessErrorResponse(exchange, droneBatteryResult.errorValue());	
				}
				
			} catch (Exception e) {
				Logger.error(e);
				exchange.getResponseSender().send(e.getMessage());
			}

		};
	}

}
