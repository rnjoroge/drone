package musala.entity;

import musala.exceptions.BussinessException;


/**
 * 
 */

public class Result<T> {

	  public Boolean isSuccess;
	  public Boolean isFailure;
	  private T error;
	  private T _value;
	  
	  Result (boolean isSuccess ,T value ,T error) {
		   this._value=value;
		   this.isSuccess=isSuccess;
		   this.isFailure=!isSuccess;
		   this.error=error;
	  }
	  
	  public T getValue () {
		    return this._value;
	  }
	  
	  public BussinessException errorValue () {
		  return (BussinessException) this.error;
	  }
	  
	  public static <T> Result<T> OK(T value) {
		   return new Result<T> (true, value, null);
	  }
	  
	  public static <T> Result<T> FAIL(T error) {
		   return new Result<T>(false, null, error);
	  }
}
