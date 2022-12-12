/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FireStation;

import EmergencySystem.Enterprise.Enterprise;

/**
 *
 * @author prane
 */
public class FireStation extends Enterprise{
    
    String fireStation;
    String fireStationLocation;
    float distanceFromEmergencyLocation;
    float timeTakenToReachTheAccidentLoc;
    
    
    public FireStation(String name,EnterpriseType type){
        
   super(name,type); 
   
    }
    
     public String getFireStation() {
        return fireStation;
    }

    public void setFireStation(String fireStation) {
        this.fireStation = fireStation;
    }

   public String getFireStationLocation() {
        return fireStationLocation;
    }

    public void setFireStationLocation(String hospitalLocation) {
        this.fireStationLocation = hospitalLocation;
    }


//    public OrganisationDirectory getHospitalOrganisationDirectoryObject() {
//        return hospitalOrganisationDirectoryObject;
//    }
//
//    public void setHospitalOrganisationDirectoryObject(OrganisationDirectory hospitalOrganisationDirectoryObject) {
//        this.hospitalOrganisationDirectoryObject = hospitalOrganisationDirectoryObject;
//    }

    @Override
    public String toString() {
        return fireStation;
    }

    public float getDistanceFromEmergencyLocation() {
        return distanceFromEmergencyLocation;
    }

    public void setDistanceFromEmergencyLocation(float distanceFromEmergencyLocation) {
        this.distanceFromEmergencyLocation = distanceFromEmergencyLocation;
    }

    public float getTimeTakenToReachTheAccidentLoc() {
        return timeTakenToReachTheAccidentLoc;
    }

    public void setTimeTakenToReachTheAccidentLoc(float timeTakenToReachTheAccidentLoc) {
        this.timeTakenToReachTheAccidentLoc = timeTakenToReachTheAccidentLoc;
    }

    
    
    
}
