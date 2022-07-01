package musala.tasks;

import java.io.BufferedWriter;


import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimerTask;

import org.tinylog.Logger;

import musala.entity.Drone;
import musala.entity.Result;
import musala.repository.IDroneRepository;


/**
 * This class contains the task that runs to check Drone and record drone battery level
 */

public class DroneBatteryLevelTask extends TimerTask {
	
	private static IDroneRepository droneRepo;
	Path currentRelativePath = Paths.get("");
	String filePath = currentRelativePath.toAbsolutePath().toString();
	private String taskFileName="tasklog.log";
	FileWriter fileWriter;
    BufferedWriter bufferFileWriter ;
    private static String newLine = System.getProperty("line.separator");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	
	public DroneBatteryLevelTask(IDroneRepository droneRepo) {
		this.droneRepo =droneRepo;
		this.init();
	
	}
	
	private void init() {
		try {
			 this.fileWriter  = new FileWriter(this.filePath+"/"+taskFileName,true);
			 this.bufferFileWriter= new BufferedWriter(this.fileWriter);

		}
		catch(Exception e) {
			e.printStackTrace();
			 Logger.error(e);
		}
	}

	@Override
	public void run() {	
	    	try {
				 
	    		Result<List<Drone>> droneListResult =this.droneRepo.findAll();
	    		 Logger.error(" fetching drone list ");
	    		if(droneListResult.isSuccess) {  
	    			List<Drone> droneList = droneListResult.getValue();
	    			Logger.error(" Saving Task ");
	    			   for (int counter = 0; counter < droneList.size(); counter++) { 	
	    				   LocalDateTime now = LocalDateTime.now();  
	    				   String batteryLog = "{"+ "Timestamp:"+"'"+dtf.format(now)+"'"+","+ " serialNumber:"+"'"+droneList.get(counter).getSerialNumber().toString()+"'" +","+" batteryLevel:" + 	  String.valueOf(droneList.get(counter).getBatteryCapacity())+"},";				   
	    				   this.fileWriter.append(batteryLog); 
	    				   this.fileWriter.append(newLine);
	    				   this.bufferFileWriter.flush();
	    				   
	    			    }     			
	    		}
	    	
		
			}
			catch(Exception e) {
				e.printStackTrace();
				 Logger.error(e);
			}
	}

}
