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


2 . Load a Drone with Medicine
    
   loading a drone with medication items
  

```http
POST {base_url}/api/v1/drone/load
```
   Drone Load Request
   
   
```javascript
{
	"serialNumber":"test",
	"load":[{
		     "name":"penicillin" ,
	         "weight":150 ,
             "code":"PEN" ,
	         "image":"img"
         	}
		]
}
```

3 . Fetch Drone Loads

 checking loaded medication items for a given drone

```http
GET {base_url}/api/v1/drone/load/{serialNumber}

```

4 . Fetch Drone Battery Level

 check drone battery level for a given drone

```http
GET {base_url}/api/v1/drone/battery/{serialNumber}

```

5 . Fetch Available Drones

 checking available drones for loading

```http
GET {base_url}/api/v1/drone/available

```

6 . Dispatch a Drone
  

```http
POST {base_url}/api/v1/drone/registration
```
   Drone Dispatch Request

```javascript
{
	"serialNumber":"test"
}
```




## Tests

Test are available under src/test/java directory
     