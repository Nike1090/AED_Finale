/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Hospital.Role;


import EmergencySystem.EmergencySystem;
import EmergencySystem.Enterprise.Enterprise;
import EmergencySystem.Network.Network;
import Hospital.UserAccount.UserAccount;
import UI.Hospital.DoctorWorkArea;
import javax.swing.JPanel;

/**
 *
 * @author Komal
 */
public class DoctorRole extends Role{

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, EmergencySystem system, Network network, Enterprise enterprise) {
      return new DoctorWorkArea( userProcessContainer,  account,  system,  network,  enterprise);
    }

  



   
    
    
}
