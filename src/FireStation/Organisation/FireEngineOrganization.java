/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FireStation.Organisation;

import Hospital.Organisation.Organisation;
import Hospital.Role.FireEngineRole;
import Hospital.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author prane
 */
public class FireEngineOrganization extends Organisation{
    
    
    public FireEngineOrganization(){
    
        super(Organisation.Type.FIREENGINE.getValue());
    }
    
     public ArrayList<Role> getSupportedRole() {
       
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new FireEngineRole());
        return roles;
    
    }
    
}
