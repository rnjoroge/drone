package musala.usecase;

import java.util.ArrayList;


import java.util.Arrays;
import java.util.List;

import musala.dto.DroneRegReqDTO;
import musala.entity.Medication;
import musala.entity.Result;
import musala.exceptions.BussinessException;


/**
 * This class contains the method for validating request for Medicine validation during drone load
 */

public class MedicineRegistrationValidation {
	
	 /**
     * Returns a Result of SUCCESS or FAIL 
     *
     * @param  
     * @return Result
     */
	
	
	public static <U> Result<U> validate (String name ,int weight ,String code ,String image) {
		try {
			List<String> errors = new ArrayList<String>();
			if(name==null) {
				errors.add(" Medicine name is required ");
			}
			
			if(!name.chars().allMatch(Character::isLetter)) {
				errors.add(" Medicine name should contain only letters ");
			}
					
			if(code==null) {
				errors.add(" Medicine code is required ");
			}
			
			if(!code.chars().anyMatch(Character::isUpperCase)) {
				errors.add(" Medicine code  should contain only uppercase letters ");	
			}
					
			if(image==null) {
				errors.add(" Medicine image is required ");
			}
					
			if(weight < 0) {
				errors.add(" Medicine weight is required and should be greater than 0");
			}
			if(errors.size() > 0) {		
				return (Result<U>)Result.FAIL(new BussinessException("medicine_validation","Error in medicine validation","validation",errors));
			}
		
		 return (Result<U>)Result.OK(true);
		}
		catch (Exception ex) {
			return (Result<U>)Result.FAIL(ex);
		}
		
	}
	
	public static int calculateTotalWeightOfMedications (List<Medication> medications) {
		return  medications.stream().mapToInt(i -> i.weight).sum();
	}

}
