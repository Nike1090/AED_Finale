/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Police;

import EmergencySystem.Emergency.Emergency;
import EmergencySystem.EmergencySystem;
import EmergencySystem.Enterprise.Enterprise;
import EmergencySystem.Network.Network;
import Hospital.UserAccount.UserAccount;
import Hospital.WorkQueue.Emergency911DepartmentWorkRequest;
import Hospital.WorkQueue.WorkRequest;
import Person.Student;
/*import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;*/
import java.io.File;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Komal
 */
public class PoliceAdminWorkArea extends javax.swing.JPanel {

    /**
     * Creates new form PoliceAdminWorkArea
     */
    JPanel userProcessContainer;
    UserAccount account;
    EmergencySystem system;
    Network network;
    Enterprise enterprise;
    private boolean manageFields=false;
    public PoliceAdminWorkArea(JPanel userProcessContainer, UserAccount account, EmergencySystem system, Network network, Enterprise enterprise) {
        initComponents();
        this.userProcessContainer=userProcessContainer;
        this.account=account;
        this.system=system;
        this.network=network;
        this.enterprise=enterprise;
        populateEmergencyTable();
        manageFields(manageFields);
        alertFamBtn.setVisible(false);
       
    }
    public void manageFields(boolean display)
    {
        nameLabel.setVisible(display);
        nameTF.setVisible(display);
        ageLabel.setVisible(display);
        ageTF.setVisible(display);
        addressLabel.setVisible(display);
        addressTF.setVisible(display);
        driverLicenseNumberLabel.setVisible(display);
        driversLicenseNumberTF.setVisible(display);
        carOwnedLabel.setVisible(display);
        carOwnedTF.setVisible(display);
        licensePlaeNumberLabel.setVisible(display);
        licensePlateTF.setVisible(display);
        phoneNumberLabel.setVisible(display);
        phoneNumberTF.setVisible(display);
        emergencyContactNumberLabel.setVisible(display);
        emergencyContactNumberTF.setVisible(display);
        photoLabel.setVisible(display);
        nameTF.setEditable(false);
        ageTF.setEditable(false);
        addressTF.setEditable(false);
        driversLicenseNumberTF.setEditable(false);
        carOwnedTF.setEditable(false);
        licensePlateTF.setEditable(false);
        phoneNumberTF.setEditable(false);
        emergencyContactNumberTF.setEditable(false);
        
        
        
    }
    public void populateEmergencyTable()
    {
        DefaultTableModel model = (DefaultTableModel) emergencyTable.getModel();
        
        model.setRowCount(0);
        
        for (WorkRequest workRequest:account.getWorkQueue().getWorkRequestList()){
            Object[] row = new Object[4];
            row[0]= ((Emergency911DepartmentWorkRequest) workRequest);
            row[1]= ((Emergency911DepartmentWorkRequest) workRequest).getEmergency().getCallersPhoneNumber();
            row[2]= ((Emergency911DepartmentWorkRequest) workRequest).getEmergency();
            row[3]= ((Emergency911DepartmentWorkRequest) workRequest).getEmergency().getDescription();
            
            model.addRow(row);
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        emergencyTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        retrieveLicensePlateBtn = new javax.swing.JButton();
        licensePlateLabel = new javax.swing.JLabel();
        licensePlateNumberTF = new javax.swing.JTextField();
        retrieveDataBtn = new javax.swing.JButton();
        photoLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        ageLabel = new javax.swing.JLabel();
        addressLabel = new javax.swing.JLabel();
        driverLicenseNumberLabel = new javax.swing.JLabel();
        carOwnedLabel = new javax.swing.JLabel();
        licensePlaeNumberLabel = new javax.swing.JLabel();
        phoneNumberLabel = new javax.swing.JLabel();
        nameTF = new javax.swing.JTextField();
        ageTF = new javax.swing.JTextField();
        addressTF = new javax.swing.JTextField();
        licensePlateTF = new javax.swing.JTextField();
        phoneNumberTF = new javax.swing.JTextField();
        driversLicenseNumberTF = new javax.swing.JTextField();
        carOwnedTF = new javax.swing.JTextField();
        emergencyContactNumberTF = new javax.swing.JTextField();
        emergencyContactNumberLabel = new javax.swing.JLabel();
        alertFamBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(102, 255, 204));

        jPanel1.setBackground(new java.awt.Color(102, 255, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        emergencyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sender", "Phone number", "Location of emergency", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(emergencyTable);
        if (emergencyTable.getColumnModel().getColumnCount() > 0) {
            emergencyTable.getColumnModel().getColumn(2).setMinWidth(250);
            emergencyTable.getColumnModel().getColumn(2).setPreferredWidth(250);
            emergencyTable.getColumnModel().getColumn(2).setMaxWidth(250);
        }

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Accident List");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 613, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(292, 292, 292)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(381, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Work Queue", jPanel1);

        jPanel2.setBackground(new java.awt.Color(102, 255, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        retrieveLicensePlateBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        retrieveLicensePlateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Police/Images/qr.png"))); // NOI18N
        retrieveLicensePlateBtn.setText("Retrieve the license plate");
        retrieveLicensePlateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retrieveLicensePlateBtnActionPerformed(evt);
            }
        });

        licensePlateLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        licensePlateLabel.setText("Retrieve person data from license plate:");

        retrieveDataBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        retrieveDataBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Police/Images/cloud down.png"))); // NOI18N
        retrieveDataBtn.setText("Retrieve data");
        retrieveDataBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retrieveDataBtnActionPerformed(evt);
            }
        });

        photoLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        nameLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        nameLabel.setText("Name:");

        ageLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ageLabel.setText("Age:");

        addressLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        addressLabel.setText("Address:");

        driverLicenseNumberLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        driverLicenseNumberLabel.setText("Drivers license number:");

        carOwnedLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        carOwnedLabel.setText("Car owned:");

        licensePlaeNumberLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        licensePlaeNumberLabel.setText("License plate number:");

        phoneNumberLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        phoneNumberLabel.setText("Phone number:");

        addressTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressTFActionPerformed(evt);
            }
        });

        emergencyContactNumberLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        emergencyContactNumberLabel.setText("Emergency contact number:");

        alertFamBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        alertFamBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Police/Images/alert.png"))); // NOI18N
        alertFamBtn.setText("Alert the family about the incidence");
        alertFamBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alertFamBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(licensePlateLabel)
                        .addGap(38, 38, 38)
                        .addComponent(licensePlateNumberTF, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(163, 163, 163))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(photoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameLabel)
                            .addComponent(ageLabel)
                            .addComponent(addressLabel)
                            .addComponent(driverLicenseNumberLabel)
                            .addComponent(carOwnedLabel)
                            .addComponent(phoneNumberLabel)
                            .addComponent(licensePlaeNumberLabel)
                            .addComponent(emergencyContactNumberLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nameTF)
                            .addComponent(ageTF)
                            .addComponent(addressTF)
                            .addComponent(driversLicenseNumberTF)
                            .addComponent(carOwnedTF)
                            .addComponent(licensePlateTF)
                            .addComponent(phoneNumberTF)
                            .addComponent(emergencyContactNumberTF, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(retrieveDataBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(219, 219, 219))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(retrieveLicensePlateBtn)
                        .addGap(223, 223, 223))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(alertFamBtn)
                        .addGap(189, 189, 189))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(retrieveLicensePlateBtn)
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(licensePlateLabel)
                    .addComponent(licensePlateNumberTF, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(retrieveDataBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameLabel)
                            .addComponent(nameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ageTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ageLabel))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(addressLabel))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(addressTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(driversLicenseNumberTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(driverLicenseNumberLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(carOwnedLabel)
                            .addComponent(carOwnedTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(licensePlaeNumberLabel)
                            .addComponent(licensePlateTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(phoneNumberLabel)
                            .addComponent(phoneNumberTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emergencyContactNumberTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(emergencyContactNumberLabel)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(photoLabel)))
                .addGap(18, 18, 18)
                .addComponent(alertFamBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Search person", jPanel2);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Police/Images/admin.png"))); // NOI18N
        jLabel1.setText("Police admin work area");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(295, 295, 295)
                .addComponent(jLabel1)
                .addContainerGap(271, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void retrieveDataBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retrieveDataBtnActionPerformed
        // TODO add your handling code here:
       
       
        String licensePlateNumber=licensePlateNumberTF.getText();
        boolean status=false;
        for(Student p:system.getPersonDirectory().getPersonList())
        {
            if(p.getLicensePlateNumber().equalsIgnoreCase(licensePlateNumber))
            {
                status=true;
                manageFields(true);
                nameTF.setText(p.getName());
                ageTF.setText(Integer.toString(p.getAge()));
                addressTF.setText(p.getAddress());
                driversLicenseNumberTF.setText(Integer.toString(p.getDriversLicenseNumber()));
                carOwnedTF.setText(p.getCarOwned());
                licensePlateTF.setText(p.getLicensePlateNumber());
                phoneNumberTF.setText(p.getPhoneNumber());
                emergencyContactNumberTF.setText(p.getEmergencyContactNumber());
               alertFamBtn.setVisible(true);
                 ImageIcon imgThisImg =null;
                java.net.URL imgURL = getClass().getResource(p.getPhoto());
                if (imgURL != null) 
                {
                imgThisImg=new ImageIcon(imgURL);
                } 
              
                photoLabel.setIcon(imgThisImg);  
            }    
           
        }
        
        if(status==false)
        {
            JOptionPane.showMessageDialog(this,"This car has not been registered, cannot locate in database!");
        }
    }//GEN-LAST:event_retrieveDataBtnActionPerformed

    private void addressTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addressTFActionPerformed

    private void alertFamBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alertFamBtnActionPerformed
        // TODO add your handling code here:
        int row=emergencyTable.getSelectedRow();
        if(row>=0)
        {
            Emergency eme=(Emergency) emergencyTable.getValueAt(row,2);
            eme.setEmergencyStatus("Family alerted");
            Date d=new Date();
            eme.setAlertedThePatientEmergencyContact(d);
            eme.setTotatTimeTakenByPoliceToAlertEmergencyContact((eme.getAlertedThePatientEmergencyContact().getTime()-eme.getPoliceAlerted().getTime())/1000%60);
            System.err.println("the timw when police is alerted and family is alerted"+eme.getTotatTimeTakenByPoliceToAlertEmergencyContact());
            JOptionPane.showMessageDialog(this,"The patients family has been informed about the accident");
        }
        
        else{
        JOptionPane.showMessageDialog(this,"Choose a emergency from the table");
        }
    }//GEN-LAST:event_alertFamBtnActionPerformed

    private void retrieveLicensePlateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retrieveLicensePlateBtnActionPerformed
        // TODO add your handling code here:
        int row=emergencyTable.getSelectedRow();
        Emergency emergency=null;
        if(row>=0)
        {
            emergency=(Emergency) emergencyTable.getValueAt(row, 2);
            String imageUrl=emergency.getLicensePlateURL();
            //   imageUrl.replace("\\", "\\\\");
                StringBuffer sb = new StringBuffer();

                // imageUrl=sb.append("\"").append(imageUrl).append("\"");

                String finalPath="";
                java.net.URL imgURL = getClass().getResource(imageUrl);
                if (imgURL != null) {

                    File file=new File(imgURL.toString());
                    finalPath=file.toString().replaceAll("\\\\", "\\\\\\\\");
                        finalPath=finalPath.substring(7,finalPath.length());
                        //System.out.println(finalPath);

                    } else {
                        System.err.println("Couldn't find file: " + imageUrl);

                    }

                  /*  opencv_core.IplImage image= cvLoadImage(finalPath);

                    final CanvasFrame canvas =new CanvasFrame("License plate");
                    canvas.showImage(image);
                    canvas.setCanvasSize(500, 300);*/
                    //canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
                }

                else
                {
                    JOptionPane.showMessageDialog(this,"Choose an emeregncy from the table");
                }
    }//GEN-LAST:event_retrieveLicensePlateBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addressLabel;
    private javax.swing.JTextField addressTF;
    private javax.swing.JLabel ageLabel;
    private javax.swing.JTextField ageTF;
    private javax.swing.JButton alertFamBtn;
    private javax.swing.JLabel carOwnedLabel;
    private javax.swing.JTextField carOwnedTF;
    private javax.swing.JLabel driverLicenseNumberLabel;
    private javax.swing.JTextField driversLicenseNumberTF;
    private javax.swing.JLabel emergencyContactNumberLabel;
    private javax.swing.JTextField emergencyContactNumberTF;
    private javax.swing.JTable emergencyTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel licensePlaeNumberLabel;
    private javax.swing.JLabel licensePlateLabel;
    private javax.swing.JTextField licensePlateNumberTF;
    private javax.swing.JTextField licensePlateTF;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTF;
    private javax.swing.JLabel phoneNumberLabel;
    private javax.swing.JTextField phoneNumberTF;
    private javax.swing.JLabel photoLabel;
    private javax.swing.JButton retrieveDataBtn;
    private javax.swing.JButton retrieveLicensePlateBtn;
    // End of variables declaration//GEN-END:variables
}