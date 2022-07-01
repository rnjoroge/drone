# Drones

  Drone Service
  
## Running the application


1. Building the application from source with maven

  ```bash
     mvn clean package
     java -jar target/musal-drone-0.0.1-SNAPSHOT.jar
  ```

The default api port is 8383 
Baseurl for local host http://127.0.0.1:8383


## Api Documentation



  
1 . Register a Drone
  

```http
POST {base_url}/api/v1/drone/registration
```
   Drone Registration Request

```javascript
{
	   "serialNumber":"testDrone",
	   "model":"Lightweight",
	   "weightLimit":200,
	   "batteryCapacity":50,
	   "state":"LOADING"
}
```



## Tests

Test are available under src/test/java directory
     