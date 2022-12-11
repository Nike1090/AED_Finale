/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hospital.Role;

import EmergencySystem.EmergencySystem;
import EmergencySystem.Enterprise.Enterprise;
import EmergencySystem.Network.Network;
import UI.FireDepartment.FireEngineWorkArea;
import Hospital.UserAccount.UserAccount;
import javax.swing.JPanel;

/**
 *
 * @author prane
 */
public class FireEngineRole extends Role {
    
    
        @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, EmergencySystem system, Network network, Enterprise enterprise) {
        return new FireEngineWorkArea( userProcessContainer,  account,  system,  network,  enterprise);
    
}
}