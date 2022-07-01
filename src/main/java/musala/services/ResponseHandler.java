package musala.services;


import org.json.JSONObject;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import musala.exceptions.BussinessException;

public class ResponseHandler {

	public static void successResponse(HttpServerExchange exchange, String data) {
		exchange.setStatusCode(200);
		exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
		exchange.getResponseSender().send("{\"status\":\"success\",\"data\":" + data + "}");
	}

	public static void errorResponse(HttpServerExchange exchange, Exception Error) {

		exchange.setStatusCode(500);
		exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
		JSONObject errResponse = new JSONObject();
				   errResponse.put("status", "error");
				   errResponse.put("message", Error.getMessage());

		exchange.getResponseSender().send(errResponse.toString());
	}
	
	public static void businessErrorResponse(HttpServerExchange exchange, BussinessException Error) {

		exchange.setStatusCode(400);
		exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
		JSONObject errResponse = new JSONObject();
		           errResponse.put("status", "error");
		           errResponse.put("code", Error.getCode());
		           errResponse.put("type", Error.getType());
		           errResponse.put("message", Error.getMessage());
		           errResponse.put("errors", Error.getErrors());
		exchange.getResponseSender().send(errResponse.toString());
	}
}