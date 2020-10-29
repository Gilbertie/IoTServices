Command to compile IotApplication.java on windows:
javac -cp ".;./org.json.jar" IoTApplication.java

Command to run IotApplication.class on windows:
java -cp ".;./org.json.jar" IoTApplication [parameter: 1/2/3/4]
parameter explanation:
1 represents entity1-service1: lightFor5Seconds
2 represents entity2-service1: buzzFor5Seconds
3 represents entity3-service1: countPushOfButtonFor5Seconds
4 represenets entity4-service1: countTiltOfSwitchFor5Seconds
other inputs are invalid inputs