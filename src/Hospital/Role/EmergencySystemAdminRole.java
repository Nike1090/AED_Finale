/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hospital.Role;

import EmergencySystem.EmergencySystem;
import EmergencySystem.Enterprise.Enterprise;
import EmergencySystem.Network.Network;
import Hospital.UserAccount.UserAccount;
import UI.EmergencyManagementSystem.EmergencyManagementSystemWorkSpace;
import javax.swing.JPanel;

/**
 *
 *  
 */
public class EmergencySystemAdminRole extends Role
{

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, EmergencySystem system, Network network,Enterprise enterprise) {
        return new EmergencyManagementSystemWorkSpace(userProcessContainer, system, account);
    }

 


    
}
