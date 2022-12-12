/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Hospital.Role;


import EmergencySystem.EmergencySystem;
import EmergencySystem.Enterprise.Enterprise;
import EmergencySystem.Network.Network;
import Hospital.UserAccount.UserAccount;
import UI.Hospital.DoctorWorkSpace;
import javax.swing.JPanel;

/**
 *
 *  
 */
public class DoctorRole extends Role{

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, EmergencySystem system, Network network, Enterprise enterprise) {
      return new DoctorWorkSpace( userProcessContainer,  account,  system,  network,  enterprise);
    }

  



   
    
    
}
