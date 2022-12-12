/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmergencySystem.Emergency;

import Drone.DroneDirectory;
import Student.Student;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 *  
 */
public class Emergency  {
    
    private static int emergencyId;
    int count;
    private String emergencyStatus;
    private int rtoNumber;
    private String locationOfEmergency;
    private String callersPhoneNumber;
    private String natureOfEmergency;
    private String description;
    private Student student;
    private DroneDirectory droneDirectoryObject;
    private int priority;
    private Date reportedTime;
    private Date reolvedTime;
    private String name;
    private String licensePlateURL;
    private Date droneAlerted;
    private long totalTimeToReachDrone;
    
//
      private Date hospitalAlerted;
      private Date fireStationAlerted;
     private Date ambulanceDispatched;
     private Date fireEngineDispatched;
      private long totalTimeToDispatchAmbulance;
      private long totalTimeToDispatchFireEngine;
//    private float timeForTheAmbulanceToReachTheAccidentalLocation;
     private Date alertOnCallDoctor;
     private Date onCallDoctorIsConnectedViaCamera;
     private long totalTimeForDoctorToGetComnnected;
     private Date policeAlerted;
    private Date alertedThePatientEmergencyContact;
    private long totatTimeTakenByPoliceToAlertEmergencyContact;
//    private long totalTime;
   
    public Emergency()
    {
    count++;
    emergencyId=count;
    droneDirectoryObject=new DroneDirectory();
    student=new Student();
    
   
    }
    
    
    
    public Emergency(String name) {
        this.name = name;
    }

     public int getEmergencyId() {
        return emergencyId;
    }

    public void setEmergencyId(int emergencyid) {
        emergencyId = emergencyid;
    }
    
    public String getEmergencyStatus() {
        return emergencyStatus;
    }

    public void setEmergencyStatus(String emergencyStatus) {
        this.emergencyStatus = emergencyStatus;
    }

    public int getRtoNumber() {
        return rtoNumber;
    }

    public void setRtoNumber(int rtoNumber) {
        this.rtoNumber = rtoNumber;
    }

    public String getLocationOfEmergency() {
        return locationOfEmergency;
    }

    public void setLocationOfEmergency(String locationOfEmergency) {
        this.locationOfEmergency = locationOfEmergency;
    }

    public String getCallersPhoneNumber() {
        return callersPhoneNumber;
    }

    public void setCallersPhoneNumber(String callersPhoneNumber) {
        this.callersPhoneNumber = callersPhoneNumber;
    }

    public String getNatureOfEmergency() {
        return natureOfEmergency;
    }

    public void setNatureOfEmergency(String natureOfEmergency) {
        this.natureOfEmergency = natureOfEmergency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DroneDirectory getDroneDirectoryObject() {
        return droneDirectoryObject;
    }

    public void setDroneDirectoryObject(DroneDirectory droneDirectoryObject) {
        this.droneDirectoryObject = droneDirectoryObject;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return  locationOfEmergency;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


    public Date getReportedTime() {
        return reportedTime;
    }

    public void setReportedTime(Date reportedTime) {
        this.reportedTime = reportedTime;
    }

    public Date getReolvedTime() {
        return reolvedTime;
    }

    public void setReolvedTime(Date reolvedTime) {
        this.reolvedTime = reolvedTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicensePlateURL() {
        return licensePlateURL;
    }

    public void setLicensePlateURL(String licensePlateURL) {
        this.licensePlateURL = licensePlateURL;
    }

//    public Date getEmergencyReachedToTheDrone() {
//        return emergencyReachedToTheDrone;
//    }
//
//    public void setEmergencyReachedToTheDrone(Date emergencyReachedToTheDrone) {
//        this.emergencyReachedToTheDrone = emergencyReachedToTheDrone;
//    }
//
    public long getTotalTimeToReachDrone() {
        return totalTimeToReachDrone;
    }

    public void setTotalTimeToReachDrone(long totalTimeToReachDrone) {
        this.totalTimeToReachDrone = totalTimeToReachDrone;
    }

    public Date getHospitalAlerted() {
        return hospitalAlerted;
    }
    
    public Date getFireStationAlerted() {
    return fireStationAlerted;
    }

    public void setHospitalAlerted(Date hospitalAlerted) {
        this.hospitalAlerted = hospitalAlerted;
    }
    
    public void setFireStationAlerted(Date fireStationAlerted) {
        this.fireStationAlerted = fireStationAlerted;
    }
    
    

    public Date getAmbulanceDispatched() {
        return ambulanceDispatched;
    }
    
    public Date getFireEngineDispatched(){
        return fireEngineDispatched;
            }

    public void setAmbulanceDispatched(Date ambulanceDispatched) {
        this.ambulanceDispatched = ambulanceDispatched;
    }
    
      public void setFireEngineDispatched(Date fireEngineDispatched) {
        this.fireEngineDispatched = fireEngineDispatched;
    }
    

    public long getTotalTimeToDispatchAmbulance() {
        return totalTimeToDispatchAmbulance;
    }
    
    public long getTotalTimeToDispatchFireEngine(){
        return totalTimeToDispatchFireEngine;
    }

    public void setTotalTimeToDispatchAmbulance(long totalTimeToDispatchAmbulance) {
        this.totalTimeToDispatchAmbulance = totalTimeToDispatchAmbulance;
    }
    
    public void setTotalTimeToDispatchFireEngine(long totalTimeToDispatchFireEngine){
        this.totalTimeToDispatchFireEngine = totalTimeToDispatchFireEngine;
    }
//
//    public float getTimeForTheAmbulanceToReachTheAccidentalLocation() {
//        return timeForTheAmbulanceToReachTheAccidentalLocation;
//    }
//
//    public void setTimeForTheAmbulanceToReachTheAccidentalLocation(float timeForTheAmbulanceToReachTheAccidentalLocation) {
//        this.timeForTheAmbulanceToReachTheAccidentalLocation = timeForTheAmbulanceToReachTheAccidentalLocation;
//    }
//
    public Date getAlertOnCallDoctor() {
        return alertOnCallDoctor;
    }

    public void setAlertOnCallDoctor(Date alertOnCallDoctor) {
        this.alertOnCallDoctor = alertOnCallDoctor;
    }

  

    public Date getOnCallDoctorIsConnectedViaCamera() {
        return onCallDoctorIsConnectedViaCamera;
    }

    public void setOnCallDoctorIsConnectedViaCamera(Date onCallDoctorIsConnectedViaCamera) {
        this.onCallDoctorIsConnectedViaCamera = onCallDoctorIsConnectedViaCamera;
    }

    public long getTotalTimeForDoctorToGetComnnected() {
        return totalTimeForDoctorToGetComnnected;
    }

    public void setTotalTimeForDoctorToGetComnnected(long totalTimeForDoctorToGetComnnected) {
        this.totalTimeForDoctorToGetComnnected = totalTimeForDoctorToGetComnnected;
    }

    public Date getPoliceAlerted() {
        return policeAlerted;
    }

    public void setPoliceAlerted(Date policeAlerted) {
        this.policeAlerted = policeAlerted;
    }

    public Date getAlertedThePatientEmergencyContact() {
        return alertedThePatientEmergencyContact;
    }

    public void setAlertedThePatientEmergencyContact(Date alertedThePatientEmergencyContact) {
        this.alertedThePatientEmergencyContact = alertedThePatientEmergencyContact;
    }

    public long getTotatTimeTakenByPoliceToAlertEmergencyContact() {
        return totatTimeTakenByPoliceToAlertEmergencyContact;
    }

    public void setTotatTimeTakenByPoliceToAlertEmergencyContact(long totatTimeTakenByPoliceToAlertEmergencyContact) {
        this.totatTimeTakenByPoliceToAlertEmergencyContact = totatTimeTakenByPoliceToAlertEmergencyContact;
    }

    public Date getDroneAlerted() {
        return droneAlerted;
    }

    public void setDroneAlerted(Date psapAlerted) {
        this.droneAlerted = psapAlerted;
    }

  
 

     public enum EmergencyType{
        MEDICALEMERGENCY("Medical Emergency"), FIREEMERGENCY("Fire Emergency"),ACCIDENTEMERGENCY("Accident Emergency");
        private String value;
        private EmergencyType(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }
    


 
 
    
}
