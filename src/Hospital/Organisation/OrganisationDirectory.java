/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hospital.Organisation;
import FireStation.Organisation.FireEngineOrganization;
import Hospital.Organisation.Organisation.Type;
import java.util.ArrayList;

/**
 *
 *  
 */
public class OrganisationDirectory {
    private ArrayList<Organisation> organizationList;

    public OrganisationDirectory() {
        organizationList = new ArrayList<>();
    }

    public ArrayList<Organisation> getOrganisationList() {
        return organizationList;
    }
    
     public Organisation createOrganisation(Type type){
        Organisation organisation = null;
        if (type.getValue().equals(Type.DOCTOR.getValue())){
            organisation = new DoctorOrganization();
            organizationList.add(organisation);
        }
        else if (type.getValue().equals(Type.AMBULANCE.getValue())){
            organisation = new AmbulanceOrganisation();
            organizationList.add(organisation);
        }
        else if (type.getValue().equals(Type.DRONE.getValue())){
            organisation = new DroneOrganisation();
            organizationList.add(organisation);
        }else if (type.getValue().equals(Type.FIREENGINE.getValue())){
            organisation = new FireEngineOrganization();
            organizationList.add(organisation);
        }
        
        
        return organisation;
    }
    
    
}
