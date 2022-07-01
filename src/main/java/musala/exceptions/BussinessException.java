package musala.exceptions;

import java.util.List;

import musala.entity.Drone;

public class BussinessException extends Exception {

		public String getType() {
		return type;
	}

		private static final long serialVersionUID = 1L;
		private final String code;
		private final List<String> errors;
		private final String type;
		
		public BussinessException(String code,String message,String type,List<String> errors) {
			super(message);
			this.code = code;
			this.errors=errors;
			this.type=type;
		}
		
		public String getCode() {
			return code;
		}

		public List<String> getErrors() {
			return errors;
		}

}
