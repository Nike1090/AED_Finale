/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.FireDepartment;

import EmergencySystem.Emergency.Emergency;
import EmergencySystem.EmergencySystem;
import EmergencySystem.Enterprise.Enterprise;
import EmergencySystem.Network.Network;
import Employee.Ambulance;
import Employee.Doctor;
import Employee.Employee;
import FireStation.FireStation;
import FireStation.Organisation.FireEngineOrganization;
import Hospital.Organisation.Organisation;
import Hospital.Organisation.Organisation.Type;
import Hospital.Role.FireDepartmentAdminRole;
import Hospital.Role.FireEngineRole;
import Employee.FireEngine;
import Hospital.UserAccount.UserAccount;
import Hospital.WorkQueue.Emergency911DepartmentWorkRequest;
import Hospital.WorkQueue.WorkRequest;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Komal
 */
public class FireDepartmentWorkArea extends javax.swing.JPanel {

    /**
     * Creates new form HospitalEnterpriseWorkArea
     */
    private JPanel userProcessContainer;
    private UserAccount account;
    private EmergencySystem system;
    private Network network;
   
    private FireStation firestation;
    private Emergency e;
    public FireDepartmentWorkArea(JPanel userProcessContainer, UserAccount account, EmergencySystem system, Network network, Enterprise enterprise) {
        initComponents();
        this.userProcessContainer=userProcessContainer;
        this.account=account;
        this.system=system;
        this.network=network;
        
        this.firestation = (FireStation)enterprise;
        alertambulanceBtn.setEnabled(false);
        populateOrganisationCombo();
        populateWorkRequestTable();
        populateFireEngineTable();
        populateOrganisationTable();
        
       
        
    }
    
    
    public void populateFireEngineTable()
    {
        DefaultTableModel model = (DefaultTableModel) FireEngineTable.getModel();
        model.setRowCount(0);
        for(Network n:system.getNetworkList())
        {
            for(FireStation f:n.getFireStationList())
            {
               for(UserAccount ua:f.getUserAccountDirectory().getUserAccountList())
               {
                   if(ua==account)
                   {
                       for(Organisation org:f.getOrganizationDirectory().getOrganisationList())
                       {
                           if(org instanceof FireEngineOrganization)
                           {
                               for(Employee e:org.getEmployeeDirectory().getEmployeeList())
                               {
                                   if(((FireEngine)e).getAvailability().equalsIgnoreCase("Available"))
                                   {
                                     Object[] row = new Object[2];
                                     row[0]= ((FireEngine)e);
                                     row[1] = ((FireEngine)e).getAvailability();
                                     model.addRow(row);
                                   }
                                     
                               }
                           }
                       }
                   }
               }
            }
        }
        {
           
        }
        
    }
   public void populateWorkRequestTable()
   {
       DefaultTableModel model = (DefaultTableModel) emergencyTable.getModel();
        
        model.setRowCount(0);
        
        for (WorkRequest workRequest:account.getWorkQueue().getWorkRequestList()){
            Object[] row = new Object[4];
            if(((Emergency911DepartmentWorkRequest) workRequest).getEmergency().getPriority()>=7)
            {
            row[2] = "Critical";
           
            }
            
            else if(((Emergency911DepartmentWorkRequest) workRequest).getEmergency().getPriority()<7 &&((Emergency911DepartmentWorkRequest) workRequest).getEmergency().getPriority()>4)
            {
                row[2]="Moderate";
            }
            
            else
            {
                 row[2]="Trivial";
            }
            row[0]= ((Emergency911DepartmentWorkRequest) workRequest);
            row[1]=((Emergency911DepartmentWorkRequest) workRequest).getEmergency();
            row[3] = ((Emergency911DepartmentWorkRequest) workRequest).getEmergency().getNatureOfEmergency();
           
            model.addRow(row);
        }
   }
    
    public FireStation getFireStation()
    {
        FireStation firestation=null;
        for(FireStation fs:network.getFireStationList())
        {
            for(UserAccount ua:fs.getUserAccountDirectory().getUserAccountList())
            {
                if(ua==account)
                {
                    firestation=fs;
                }
            }
        }
        return firestation;
    }
    public void populateOrganisationCombo()
      {
           organisationCombo.removeAllItems();
        for (Type type : Organisation.Type.values())
        {
            if( (type.getValue().equals(Type.FIREENGINE.getValue())))
                organisationCombo.addItem(type);
        }
      }
    
    public void populateOrganisationTable()
    {
    DefaultTableModel model = (DefaultTableModel) FireEngineOrgTable.getModel();
   
              model.setRowCount(0);

       
        for (Organisation organisation : firestation.getOrganizationDirectory().getOrganisationList())
        {
            
            Object[] row = new Object[1];
            row[0] = organisation;
            
            model.addRow(row);
        }
        if(FireEngineOrgTable.getRowCount()>=2)
        {
            addOrgBtn.setEnabled(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header = new javax.swing.JLabel();
        enterpriseWorkArea = new javax.swing.JTabbedPane();
        workQueue = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        emergencyTable = new javax.swing.JTable();
        processEmergencyBtn = new javax.swing.JButton();
        alertAmbulance = new javax.swing.JPanel();
        alertambulanceBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        FireEngineTable = new javax.swing.JTable();
        manageOrgPanel = new javax.swing.JPanel();
        organisationLabel = new javax.swing.JLabel();
        organisationCombo = new javax.swing.JComboBox();
        addOrgBtn = new javax.swing.JButton();
        nameLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        nameTF = new javax.swing.JTextField();
        usernameTF = new javax.swing.JTextField();
        createEmployee = new javax.swing.JButton();
        passwordTF = new javax.swing.JPasswordField();
        nameErr = new javax.swing.JLabel();
        userNameErr = new javax.swing.JLabel();
        passwordErr = new javax.swing.JLabel();
        availErr = new javax.swing.JLabel();
        specialityErr = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        organisationTable = new javax.swing.JScrollPane();
        FireEngineOrgTable = new javax.swing.JTable();

        setBackground(new java.awt.Color(102, 255, 204));

        header.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        header.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/FireDepartment/Images/admin.png"))); // NOI18N
        header.setText("Fire Department Enterprise Admin Work Area");

        workQueue.setBackground(new java.awt.Color(102, 255, 204));
        workQueue.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        emergencyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sender", "Location of emergency", "Crticality", "Nature of emergency"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(emergencyTable);
        if (emergencyTable.getColumnModel().getColumnCount() > 0) {
            emergencyTable.getColumnModel().getColumn(1).setMinWidth(250);
            emergencyTable.getColumnModel().getColumn(1).setPreferredWidth(250);
            emergencyTable.getColumnModel().getColumn(1).setMaxWidth(250);
        }

        processEmergencyBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        processEmergencyBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/FireDepartment/Images/Processing.png"))); // NOI18N
        processEmergencyBtn.setText("Process the emergency");
        processEmergencyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                processEmergencyBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout workQueueLayout = new javax.swing.GroupLayout(workQueue);
        workQueue.setLayout(workQueueLayout);
        workQueueLayout.setHorizontalGroup(
            workQueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(workQueueLayout.createSequentialGroup()
                .addGroup(workQueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(workQueueLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(workQueueLayout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addComponent(processEmergencyBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        workQueueLayout.setVerticalGroup(
            workQueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(workQueueLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(processEmergencyBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(293, Short.MAX_VALUE))
        );

        enterpriseWorkArea.addTab("Work queue", workQueue);

        alertAmbulance.setBackground(new java.awt.Color(102, 255, 204));
        alertAmbulance.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        alertambulanceBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        alertambulanceBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/FireDepartment/Images/firetruck.png"))); // NOI18N
        alertambulanceBtn.setText("Alert Fire Engine");
        alertambulanceBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alertambulanceBtnActionPerformed(evt);
            }
        });

        FireEngineTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ambulance Name", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(FireEngineTable);

        javax.swing.GroupLayout alertAmbulanceLayout = new javax.swing.GroupLayout(alertAmbulance);
        alertAmbulance.setLayout(alertAmbulanceLayout);
        alertAmbulanceLayout.setHorizontalGroup(
            alertAmbulanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(alertAmbulanceLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(alertambulanceBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(254, 254, 254))
            .addGroup(alertAmbulanceLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        alertAmbulanceLayout.setVerticalGroup(
            alertAmbulanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(alertAmbulanceLayout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(alertambulanceBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(306, Short.MAX_VALUE))
        );

        enterpriseWorkArea.addTab("Alert Fire Engine", alertAmbulance);

        manageOrgPanel.setBackground(new java.awt.Color(102, 255, 204));
        manageOrgPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        organisationLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        organisationLabel.setText("Organisation");

        organisationCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        organisationCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                organisationComboActionPerformed(evt);
            }
        });

        addOrgBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        addOrgBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/FireDepartment/Images/add.png"))); // NOI18N
        addOrgBtn.setText("Add Organisation");
        addOrgBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addOrgBtnActionPerformed(evt);
            }
        });

        nameLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        nameLabel.setText("Name:");

        usernameLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        usernameLabel.setText("Username:");

        passwordLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        passwordLabel.setText("Password:");

        nameTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nameTFFocusGained(evt);
            }
        });
        nameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTFActionPerformed(evt);
            }
        });

        usernameTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                usernameTFFocusGained(evt);
            }
        });
        usernameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameTFActionPerformed(evt);
            }
        });

        createEmployee.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        createEmployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/FireDepartment/Images/user 24.png"))); // NOI18N
        createEmployee.setText("Create employee");
        createEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createEmployeeActionPerformed(evt);
            }
        });

        passwordTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwordTFFocusGained(evt);
            }
        });

        nameErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        nameErr.setForeground(new java.awt.Color(102, 102, 102));

        userNameErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        userNameErr.setForeground(new java.awt.Color(102, 102, 102));

        passwordErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        passwordErr.setForeground(new java.awt.Color(102, 102, 102));

        availErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        availErr.setForeground(new java.awt.Color(102, 102, 102));

        specialityErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        specialityErr.setForeground(new java.awt.Color(102, 102, 102));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("Select the organisation");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        FireEngineOrgTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Organisation"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        organisationTable.setViewportView(FireEngineOrgTable);

        javax.swing.GroupLayout manageOrgPanelLayout = new javax.swing.GroupLayout(manageOrgPanel);
        manageOrgPanel.setLayout(manageOrgPanelLayout);
        manageOrgPanelLayout.setHorizontalGroup(
            manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageOrgPanelLayout.createSequentialGroup()
                .addContainerGap(182, Short.MAX_VALUE)
                .addGroup(manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageOrgPanelLayout.createSequentialGroup()
                        .addGroup(manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passwordLabel)
                            .addGroup(manageOrgPanelLayout.createSequentialGroup()
                                .addGroup(manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(usernameLabel)
                                    .addComponent(nameLabel))
                                .addGap(69, 69, 69)
                                .addGroup(manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(nameErr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(nameTF)
                                    .addComponent(usernameTF)
                                    .addComponent(userNameErr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(passwordTF)
                                    .addComponent(passwordErr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(specialityErr, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(manageOrgPanelLayout.createSequentialGroup()
                                .addComponent(createEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(availErr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(187, 187, 187))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageOrgPanelLayout.createSequentialGroup()
                        .addGroup(manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(addOrgBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(manageOrgPanelLayout.createSequentialGroup()
                                .addComponent(organisationLabel)
                                .addGap(18, 18, 18)
                                .addComponent(organisationCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(223, 223, 223))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageOrgPanelLayout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(220, 220, 220))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageOrgPanelLayout.createSequentialGroup()
                        .addComponent(organisationTable, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(159, 159, 159))))
        );
        manageOrgPanelLayout.setVerticalGroup(
            manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageOrgPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(organisationCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(organisationLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addOrgBtn)
                .addGap(18, 18, 18)
                .addComponent(organisationTable, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameErr, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel)
                    .addComponent(usernameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userNameErr, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(passwordTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwordErr, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageOrgPanelLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(availErr, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(manageOrgPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(createEmployee)))
                .addGap(35, 35, 35)
                .addComponent(specialityErr, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
        );

        enterpriseWorkArea.addTab("Manage Organisations", manageOrgPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(250, 250, 250)
                        .addComponent(header))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(enterpriseWorkArea, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(header)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(enterpriseWorkArea, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addOrgBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addOrgBtnActionPerformed
        // TODO add your handling code here:
        Type type = (Type) organisationCombo.getSelectedItem();
        firestation.getOrganizationDirectory().createOrganisation(type);
        populateOrganisationTable();
    }//GEN-LAST:event_addOrgBtnActionPerformed

    private void createEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createEmployeeActionPerformed
        // TODO add your handling code here:
        boolean userNameIsUnique = true;
//        specialityErr.setVisible(false);
//        specialityLabel.setVisible(false);
//        specialityTF.setVisible(false);
        String username = usernameTF.getText().trim();
         char[] passwordCharArray = passwordTF.getPassword();
        String password = String.valueOf(passwordCharArray).trim(); 
        
       
        
        
        
//        if(nameTF.getText().trim().matches("[a-zA-Z0-9 ]+")&&username.matches("[a-zA-Z0-9]+")&&password.matches("[a-zA-Z0-9]+")&&availabilityTF.getText().trim().matches("[a-zA-Z0-9]+"))
//        {
//            userNameIsUnique=system.checkIfUserNameIsUnique(username, password);
//            if(userNameIsUnique==true)
//            {
                int rowNumber=FireEngineOrgTable.getSelectedRow();
                if(rowNumber>=0)
                {
                    Organisation org=(Organisation) FireEngineOrgTable.getValueAt(rowNumber,0);
                     if(org instanceof FireEngineOrganization)
                    {
                        
                        
                        if(nameTF.getText().trim().matches("[a-zA-Z0-9 ]+")&&username.matches("[a-zA-Z0-9]+")&&password.matches("[a-zA-Z0-9]+"))
                        {
//                         userNameIsUnique=system.checkIfUserNameIsUnique(username, password);
                        if(userNameIsUnique==true)
                        {
                        FireEngine a=new FireEngine();
                        a.setName(nameTF.getText().trim());
                        org.getEmployeeDirectory().createEmployee(a);
                        org.getUserAccountDirectory().createUserAccount(username, password, a,new FireEngineRole());
                        JOptionPane.showMessageDialog(this,"The FireEngine employee is created successfully");
                        nameTF.setText("");
                        usernameTF.setText("");
                        passwordTF.setText("");
                        
                        }
                        
                        else
                        {
                            userNameErr.setText("This username already exists"); 
                        }
                         
                        }
                        
                        else
                        {
                        if(!nameTF.getText().trim().matches("[a-zA-Z0-9 ]+"))
                           {
                            nameErr.setText("Enter a valid name");
                           }
            
                        if(!username.matches("[a-zA-Z0-9]+"))
                            {
                            userNameErr.setText("Enter a valid username");
                            }
            
                        if(!password.matches("[a-zA-Z0-9]+"))
                            {
                            passwordErr.setText("Enter a valid password");
                        
                            }
                        
              
                        }
                            
        
                    }
        }
        
        else
        {
            JOptionPane.showMessageDialog(this,"Select an organisation from the table");
        }
          
          //  }
            
//            else
//            {
//               userNameErr.setText("This username already exists"); 
//            }
    //    }
        
//        else
//        {
//            if(!nameTF.getText().trim().matches("[a-zA-Z0-9 ]+"))
//            {
//            nameErr.setText("Enter a valid name");
//            }
//            
//            if(!username.matches("[a-zA-Z0-9]+"))
//            {
//                userNameErr.setText("Enter a valid username");
//            }
//            
//            if(!password.matches("[a-zA-Z0-9]+"))
//            {
//                passwordErr.setText("Enter a valid password");
//                        
//            }
      //  }
        
        
        
        
    }//GEN-LAST:event_createEmployeeActionPerformed

    private void alertambulanceBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alertambulanceBtnActionPerformed
        // TODO add your handling code here:
        int rowSelected=FireEngineTable.getSelectedRow();
        int select=emergencyTable.getSelectedRow();
        WorkRequest request=null;
        if(select>=0)
        {
            request = (WorkRequest) emergencyTable.getValueAt(select, 0);
           
        }
        
        else
        {
        JOptionPane.showMessageDialog(this,"Select an emergency from the emergency table");
        }
        if(rowSelected>=0)
        {
             Emergency emer=(Emergency) emergencyTable.getValueAt(select, 1);
            FireEngine fe=(FireEngine) FireEngineTable.getValueAt(rowSelected,0);
           
                for(Organisation org:firestation.getOrganizationDirectory().getOrganisationList())
                {
                    if(org instanceof FireEngineOrganization)
                    {
                        for(UserAccount user:org.getUserAccountDirectory().getUserAccountList())
                        {
                            if((user.getEmployee()==fe)&&(!emer.getEmergencyStatus().equalsIgnoreCase("Assigned to FireEngine")))
                            {
                          
                                request.setSender(account);
                                request.setReceiver(user);
                                emer.setEmergencyStatus("Assigned to FireEgnine");
                                user.getWorkQueue().getWorkRequestList().add(request);
                                ((FireEngine)user.getEmployee()).setAvailability("Not available");
                                 JOptionPane.showMessageDialog(this,"The FireEngine "+fe.getName()+ " has been alerted about the emergency");
                                 populateFireEngineTable();
                                 Date d=new Date();
                                 emer.setFireEngineDispatched(d);
                                 emer.setTotalTimeToDispatchFireEngine((emer.getFireEngineDispatched().getTime()-emer.getFireStationAlerted().getTime())/ 1000 % 60);
                                System.err.println("The time from FireStation alerted to FireEngine dipatched"+emer.getTotalTimeToDispatchFireEngine());
                                 break;
                            }
                            
                            else
                            {
                                if(emer.getEmergencyStatus().equalsIgnoreCase("Assigned to FireEngine"))
                                {
                                JOptionPane.showMessageDialog(this, "For this emergency the FireEngine has already been alerted");
                                break;
                                }
                                
                            }
                        }
                    }
                }
            
        }
        
        else{
        JOptionPane.showMessageDialog(this,"Choose a FireEngine from the table");
        }
    }//GEN-LAST:event_alertambulanceBtnActionPerformed

    private void processEmergencyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_processEmergencyBtnActionPerformed
        // TODO add your handling code here:
        int rowSelected=emergencyTable.getSelectedRow();
        if(rowSelected>=0)
        {
            e=(Emergency) emergencyTable.getValueAt(rowSelected, 1);
            JOptionPane.showMessageDialog(this, "Alert the FireEngine");
             alertambulanceBtn.setEnabled(true);
        }
        
        else
        {
            JOptionPane.showMessageDialog(this,"Choose the emergency from the table");
            
        }
    }//GEN-LAST:event_processEmergencyBtnActionPerformed

    private void usernameTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usernameTFFocusGained
        // TODO add your handling code here:
        userNameErr.setText("");
    }//GEN-LAST:event_usernameTFFocusGained

    private void passwordTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordTFFocusGained
        // TODO add your handling code here:
        passwordErr.setText("");
    }//GEN-LAST:event_passwordTFFocusGained

    private void organisationComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_organisationComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_organisationComboActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        int rowSelected=FireEngineOrgTable.getSelectedRow();
        if(rowSelected>=0)
        {
                    FireEngineOrgTable.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                          public void mouseClicked(java.awt.event.MouseEvent evt) 
                          {
                          int row = FireEngineOrgTable.rowAtPoint(evt.getPoint());
                          int col = FireEngineOrgTable.columnAtPoint(evt.getPoint());
                          if (row >= 0 && col >= 0)
                            {
                               nameTF.setEditable(true);
                               usernameTF.setEditable(true);
                               passwordTF.setEditable(true);
                               

                            }
                          if(row >= 1 && col >= 0)
                          {
                              nameTF.setEditable(true);
                               usernameTF.setEditable(true);
                               passwordTF.setEditable(true);
                               
                              
                          }
                          }
                    });         
        }
        
        else
        {
            JOptionPane.showMessageDialog(this, "Choose an organisation from the table");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void usernameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameTFActionPerformed

    private void nameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameTFActionPerformed

    private void nameTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameTFFocusGained
        // TODO add your handling code here:
        nameErr.setText("");
    }//GEN-LAST:event_nameTFFocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable FireEngineOrgTable;
    private javax.swing.JTable FireEngineTable;
    private javax.swing.JButton addOrgBtn;
    private javax.swing.JPanel alertAmbulance;
    private javax.swing.JButton alertambulanceBtn;
    private javax.swing.JLabel availErr;
    private javax.swing.JButton createEmployee;
    private javax.swing.JTable emergencyTable;
    private javax.swing.JTabbedPane enterpriseWorkArea;
    private javax.swing.JLabel header;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel manageOrgPanel;
    private javax.swing.JLabel nameErr;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTF;
    private javax.swing.JComboBox organisationCombo;
    private javax.swing.JLabel organisationLabel;
    private javax.swing.JScrollPane organisationTable;
    private javax.swing.JLabel passwordErr;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField passwordTF;
    private javax.swing.JButton processEmergencyBtn;
    private javax.swing.JLabel specialityErr;
    private javax.swing.JLabel userNameErr;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTF;
    private javax.swing.JPanel workQueue;
    // End of variables declaration//GEN-END:variables
}
