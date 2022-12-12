/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Drone;

import EmergencySystem.Emergency.Emergency;
import EmergencySystem.EmergencySystem;
import EmergencySystem.Enterprise.Enterprise;
import EmergencySystem.Enterprise.PoliceEnterprise;
import EmergencySystem.Network.Network;
import Employee.Doctor;
import Employee.EnterpriseAdmin;
import Employee.Employee;
import FireStation.FireStation;
import Hospital.Hospital;
import Hospital.Organisation.DoctorOrganization;
import Hospital.Organisation.Organisation;
import Hospital.UserAccount.UserAccount;
import Hospital.WorkQueue.Emergency911DepartmentWorkRequest;
import Hospital.WorkQueue.WorkRequest;
import com.google.gson.Gson;
import com.sl.DistancePojo;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 *  
 */
public class DroneWorkSpace extends javax.swing.JPanel {

    /**
     * Creates new form DroneWorkArea
     */
    private JPanel userProcessContainer;
    private UserAccount account;
    private EmergencySystem system;
    private Network network;
    private Emergency emergency;
    private WorkRequest request;
    private boolean view=false;
    private boolean licenPlateCaptured=false;
    public DroneWorkSpace(JPanel userProcessContainer, UserAccount account, EmergencySystem system, Network network) {
        initComponents();
        this.userProcessContainer=userProcessContainer;
        this.account=account;
        this.system=system;
        this.network=network;
       
        populateTable();
        viewButtons(view);
     
        additionalDetailsTable.setVisible(false);
       

    }
    public void viewButtons(boolean view)
    {
        viewDetailsButton.setEnabled(view);
        alertDoctorBtn.setEnabled(view);
        alertHospitalBtn.setEnabled(view);
        alertPoliceBtn.setEnabled(view);
        captureLicenceBtn.setEnabled(view);
        networkCombo.setEnabled(view);
        networkL.setEnabled(view);
        
    }
    
   
   
    public void populateNetworkCombo()
    {
        networkCombo.removeAllItems();
        networkComboFireStation.removeAllItems();
        for (Network n:system.getNetworkList()){
            if(n!=network)
            networkCombo.addItem(n);
            networkComboFireStation.addItem(n);
        } 
    }
    
    public void populateFireStationTableForTheNetwork(Network n){
    
         calculateFireStation(n);
         DefaultTableModel model = (DefaultTableModel)otherFireStations.getModel();
        
        model.setRowCount(0);
        
        for (FireStation fs:n.getFireStationList()){
            Object[] row = new Object[5];
            row[0] = fs;
            row[1] = fs.getDistanceFromEmergencyLocation();
            row[2] = fs.getEmployeeDirectory().getEmployeeList().size();
            row[3] = fs.getTimeTakenToReachTheAccidentLoc();
            
            model.addRow(row);
        }
        
    
    }
    
    public void populateHospitalTableForTheNetwork(Network n)
    {
 
        calculate(n);
         DefaultTableModel model = (DefaultTableModel)otherHospitalsTable.getModel();
        
        model.setRowCount(0);
        
        for (Hospital h:n.getHospitalList()){
            Object[] row = new Object[5];
            row[0] = h;
            row[1] = h.getDistanceFromEmergencyLocation();
            row[2] = h.getNumberOfEmptyBeds();
            row[3] = h.getSpeciality();
            row[4] = h.getTimeTakenToReachTheAccidentLoc();
            
            model.addRow(row);
        }
    }
    
    public void calculateFireStation(Network n){
    
         int count=n.getHospitalList().size();
        
        try
            {
             
            String emergencyLocation=emergency.getLocationOfEmergency().replaceAll("\\s","");
            
            StringBuilder hospitalLocation=new StringBuilder();
            for(FireStation fs:n.getFireStationList())
            {
                String temp=fs.getFireStation().replaceAll("\\s","");
                hospitalLocation.append(temp+"|");
            }
            
        //    System.err.println("the hospital location is "+hospitalLocation);
            
            URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?origins="+emergencyLocation+"&destinations="+hospitalLocation+"&key=AIzaSyDYYviZ3sF7n6ZY67LcYmE2TIiMov-_10o");
           
            //System.out.println("the url is "+url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            String line, outputString = "";
            BufferedReader reader = new BufferedReader(
            new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                outputString += line;
            }
              // System.out.println("outputString >>>> "+outputString);

           // String[] distanceStringArray = new String[5];
            try
            {
                JSONObject obj = new JSONObject(outputString);
                JSONArray rows = obj.getJSONArray("rows");
                String[] distanceStringArray=new String[count];
                String[] timeStringArray=new String[count];
                //String dist = obj.getJSONObject("rows").getJSONObject("elements").getString("distance");
       
                for(int i = 0; i<rows.length();i++)
                {
                    JSONArray e=rows.getJSONObject(i).getJSONArray("elements");

                    for(int j = 0 ; j<e.length();j++)
                    {  
                        String dist1;
                        String time1;
                        
                        String distance=e.getJSONObject(j).getJSONObject("distance").getString("text");
                        String time=e.getJSONObject(j).getJSONObject("duration").getString("text");
                       // System.err.println("the time for malden is "+time);
                       // System.err.println("the distance is"+distance);
                          timeStringArray[j]=time;
                     //     System.err.println("the array for malden is "+timeStringArray[j]);
                      //    System.err.println(timeStringArray[j]);
                        int p1 = distance.indexOf(' ');
                        if (p1 >= 0)
                          {
                           dist1 = distance.substring(0, p1);
                            distanceStringArray[j]=dist1;
                         //     System.out.println("the dist is "+distanceStringArray[j]);
                          }
                        
                         int p2 = time.indexOf(' ');
                        if (p2 >= 0)
                          {
                           time1 = time.substring(0, p2);
                            timeStringArray[j]=time1;
                           //   System.out.println("the time for malden without the string is "+timeStringArray[j]);
                          }
                        
                    }
                    
                }
                
                
                for(int i=0;i<count;i++)
                {
                    for(int j=0;j<count;j++)
                    {
                        n.getFireStationList().get(i).setDistanceFromEmergencyLocation(Float.parseFloat(distanceStringArray[i]));
                        n.getFireStationList().get(i).setTimeTakenToReachTheAccidentLoc(Float.parseFloat(timeStringArray[i]));
                       // System.err.println("the time is "+n.getHospitalEnterpriseDirectoryObject().getHospitalList().get(i).getTimeTakenToReachTheAccidentLoc());
//    System.err.println(n.getHospitalEnterpriseDirectoryObject().getHospitalList().get(i).getTimeTakenToReachTheAccidentLoc());
                        // System.out.println(network.getHospitalEnterpriseDirectoryObject().getHospitalList().get(i).getDistanceFromEmergencyLocation());
                    }
                }
                
              
             }
              
                catch(Exception e)
                {
                System.out.println("The exception occured >> "+e.getMessage());
                }

             DistancePojo capRes = new Gson().fromJson(outputString, DistancePojo.class);
              //System.out.println("capRes >> "+capRes);
             
            
        }

        catch(Exception e)
        {
            System.err.println("The exception is "+e);
        }
        
    
    
    }
    
    public void calculate(Network n)
    {
    int count=n.getHospitalList().size();
        
        try
            {
             
            String emergencyLocation=emergency.getLocationOfEmergency().replaceAll("\\s","");
            
            StringBuilder hospitalLocation=new StringBuilder();
            for(Hospital h:n.getHospitalList())
            {
                String temp=h.getHospitalLocation().replaceAll("\\s","");
                hospitalLocation.append(temp+"|");
            }
            
        //    System.err.println("the hospital location is "+hospitalLocation);
            
            URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?origins="+emergencyLocation+"&destinations="+hospitalLocation+"&key=AIzaSyDYYviZ3sF7n6ZY67LcYmE2TIiMov-_10o");
           
            //System.out.println("the url is "+url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            String line, outputString = "";
            BufferedReader reader = new BufferedReader(
            new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                outputString += line;
            }
              // System.out.println("outputString >>>> "+outputString);

           // String[] distanceStringArray = new String[5];
            try
            {
                JSONObject obj = new JSONObject(outputString);
                JSONArray rows = obj.getJSONArray("rows");
                String[] distanceStringArray=new String[count];
                String[] timeStringArray=new String[count];
                //String dist = obj.getJSONObject("rows").getJSONObject("elements").getString("distance");
       
                for(int i = 0; i<rows.length();i++)
                {
                    JSONArray e=rows.getJSONObject(i).getJSONArray("elements");

                    for(int j = 0 ; j<e.length();j++)
                    {  
                        String dist1;
                        String time1;
                        
                        String distance=e.getJSONObject(j).getJSONObject("distance").getString("text");
                        String time=e.getJSONObject(j).getJSONObject("duration").getString("text");
                       // System.err.println("the time for malden is "+time);
                       // System.err.println("the distance is"+distance);
                          timeStringArray[j]=time;
                     //     System.err.println("the array for malden is "+timeStringArray[j]);
                      //    System.err.println(timeStringArray[j]);
                        int p1 = distance.indexOf(' ');
                        if (p1 >= 0)
                          {
                           dist1 = distance.substring(0, p1);
                            distanceStringArray[j]=dist1;
                         //     System.out.println("the dist is "+distanceStringArray[j]);
                          }
                        
                         int p2 = time.indexOf(' ');
                        if (p2 >= 0)
                          {
                           time1 = time.substring(0, p2);
                            timeStringArray[j]=time1;
                           //   System.out.println("the time for malden without the string is "+timeStringArray[j]);
                          }
                        
                    }
                    
                }
                
                
                for(int i=0;i<count;i++)
                {
                    for(int j=0;j<count;j++)
                    {
                        n.getHospitalList().get(i).setDistanceFromEmergencyLocation(Float.parseFloat(distanceStringArray[i]));
                        n.getHospitalList().get(i).setTimeTakenToReachTheAccidentLoc(Float.parseFloat(timeStringArray[i]));
                       // System.err.println("the time is "+n.getHospitalEnterpriseDirectoryObject().getHospitalList().get(i).getTimeTakenToReachTheAccidentLoc());
//    System.err.println(n.getHospitalEnterpriseDirectoryObject().getHospitalList().get(i).getTimeTakenToReachTheAccidentLoc());
                        // System.out.println(network.getHospitalEnterpriseDirectoryObject().getHospitalList().get(i).getDistanceFromEmergencyLocation());
                    }
                }
                
              
             }
              
                catch(Exception e)
                {
                System.out.println("The exception occured >> "+e.getMessage());
                }

             DistancePojo capRes = new Gson().fromJson(outputString, DistancePojo.class);
              //System.out.println("capRes >> "+capRes);
             
            
        }

        catch(Exception e)
        {
            System.err.println("The exception is "+e);
        }
    }

    public void populateTable()
            
    {
         
        DefaultTableModel model = (DefaultTableModel) emergencyTable.getModel();
        
        model.setRowCount(0);
        
        for (WorkRequest workRequest:account.getWorkQueue().getWorkRequestList()){
            Object[] row = new Object[4];
            row[0]=  workRequest;
            
            row[1] = ((Emergency911DepartmentWorkRequest) workRequest).getEmergency();
            row[2] = ((Emergency911DepartmentWorkRequest) workRequest).getEmergency().getNatureOfEmergency();
            row[3]= ((Emergency911DepartmentWorkRequest) workRequest).getEmergency().getPriority();
     
            model.addRow(row);
        }
    }
    
    
    public void populateFireStation(){
    
     int row=emergencyTable.getSelectedRow();
     
        if(row>=0 && emergencyTable.getValueAt(row, 3).equals("Fire Emergency"))
        {
            JOptionPane.showMessageDialog(this,"Select an Fire emergency");
            emergency=(Emergency) emergencyTable.getValueAt(row,1);
          //  System.out.println("the emeregncy location is"+emergency.getLocationOfEmergency());

        }
        else
        {
            
        }
        int count=network.getFireStationList().size();
        
        try
            {
             
            String emergencyLocation=emergency.getLocationOfEmergency().replaceAll("\\s","");
            
            StringBuilder fireStationLocation=new StringBuilder();
            for(FireStation fs:network.getFireStationList())
            {
                String temp=fs.getFireStationLocation().replaceAll("\\s","");
                fireStationLocation.append(temp+"|");
            }
            
        //    System.err.println("the hospital location is "+hospitalLocation);
            
            URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?origins="+emergencyLocation+"&destinations="+fireStationLocation+"&key=AIzaSyDYYviZ3sF7n6ZY67LcYmE2TIiMov-_10o");
           
            //System.out.println("the url is "+url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            String line, outputString = "";
            BufferedReader reader = new BufferedReader(
            new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                outputString += line;
            }
              // System.out.println("outputString >>>> "+outputString);

           // String[] distanceStringArray = new String[5];
            try
            {
                JSONObject obj = new JSONObject(outputString);
                JSONArray rows = obj.getJSONArray("rows");
                String[] distanceStringArray=new String[count];
                String[] timeStringArray=new String[count];
                //String dist = obj.getJSONObject("rows").getJSONObject("elements").getString("distance");
       
                for(int i = 0; i<rows.length();i++)
                {
                    JSONArray e=rows.getJSONObject(i).getJSONArray("elements");

                    for(int j = 0 ; j<e.length();j++)
                    {  
                        String dist1;
                        String time1;
                        
                        String distance=e.getJSONObject(j).getJSONObject("distance").getString("text");
                        String time=e.getJSONObject(j).getJSONObject("duration").getString("text");
                        // System.err.println("the distance is"+distance);
                          
                        int p1 = distance.indexOf(' ');
                        if (p1 >= 0)
                          {
                           dist1 = distance.substring(0, p1);
                            distanceStringArray[j]=dist1;
                          }
                        
                        int p2=time.indexOf(' ');
                        if(p2 >=0)
                        {
                            time1=time.substring(0,p2);
                            timeStringArray[j]=time1;
                        }
                       // timeStringArray[j]=time;
                    }
                    
                }
                
                for(int i=0;i<count;i++)
                {
                    for(int j=0;j<count;j++)
                    {
                        network.getFireStationList().get(i).setDistanceFromEmergencyLocation(Float.parseFloat(distanceStringArray[i]));
                        network.getFireStationList().get(i).setTimeTakenToReachTheAccidentLoc(Float.parseFloat(timeStringArray[i]));
                    }
                }
                
             }
              
                catch(Exception e)
                {
                System.out.println("The exception occured >> "+e.getMessage());
                }

             DistancePojo capRes = new Gson().fromJson(outputString, DistancePojo.class);
              //System.out.println("capRes >> "+capRes);
             
            
        }

        catch(Exception e)
        {
            System.err.println("The exception is "+e);
        }

        
        DefaultTableModel model = (DefaultTableModel) FireStationTable.getModel();
        
        model.setRowCount(0);
        
        for(FireStation fs:network.getFireStationList())
        {
         Object[] row1 = new Object[3];
            row1[0] =  fs;
            row1[1] =  fs.getFireStationLocation();
         
     
            model.addRow(row1);   
        }
        
        
        DefaultTableModel model1 = (DefaultTableModel) FireStationTable_additional.getModel();
        
        model1.setRowCount(0);
        
        for(FireStation fs:network.getFireStationList())
        {
         Object[] row2 = new Object[4];
            row2[0] =  fs;
            row2[1] =  fs.getEmployeeDirectory().getEmployeeList().size();
            row2[2] =  fs.getDistanceFromEmergencyLocation();
            row2[3] =  fs.getTimeTakenToReachTheAccidentLoc();
     
            model1.addRow(row2);   
        }
        

    
    }
    
    public void populateHospitalTable()
    {
       
       
        int row=emergencyTable.getSelectedRow();
        if(row>=0)
        {
            emergency=(Emergency) emergencyTable.getValueAt(row,1);
          //  System.out.println("the emeregncy location is"+emergency.getLocationOfEmergency());

        }
        
        else
        {
            JOptionPane.showMessageDialog(this,"Select an emergency");
        }
        int count=network.getHospitalList().size();
        
        try
            {
             
            String emergencyLocation=emergency.getLocationOfEmergency().replaceAll("\\s","");
            
            StringBuilder hospitalLocation=new StringBuilder();
            for(Hospital h:network.getHospitalList())
            {
                String temp=h.getHospitalLocation().replaceAll("\\s","");
                hospitalLocation.append(temp+"|");
            }
            
        //    System.err.println("the hospital location is "+hospitalLocation);
            
            URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?origins="+emergencyLocation+"&destinations="+hospitalLocation+"&key=AIzaSyDYYviZ3sF7n6ZY67LcYmE2TIiMov-_10o");
           
            //System.out.println("the url is "+url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            String line, outputString = "";
            BufferedReader reader = new BufferedReader(
            new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                outputString += line;
            }
              // System.out.println("outputString >>>> "+outputString);

           // String[] distanceStringArray = new String[5];
            try
            {
                JSONObject obj = new JSONObject(outputString);
                JSONArray rows = obj.getJSONArray("rows");
                String[] distanceStringArray=new String[count];
                String[] timeStringArray=new String[count];
                //String dist = obj.getJSONObject("rows").getJSONObject("elements").getString("distance");
       
                for(int i = 0; i<rows.length();i++)
                {
                    JSONArray e=rows.getJSONObject(i).getJSONArray("elements");

                    for(int j = 0 ; j<e.length();j++)
                    {  
                        String dist1;
                        String time1;
                        
                        String distance=e.getJSONObject(j).getJSONObject("distance").getString("text");
                        String time=e.getJSONObject(j).getJSONObject("duration").getString("text");
                        // System.err.println("the distance is"+distance);
                          
                        int p1 = distance.indexOf(' ');
                        if (p1 >= 0)
                          {
                           dist1 = distance.substring(0, p1);
                            distanceStringArray[j]=dist1;
                          }
                        
                        int p2=time.indexOf(' ');
                        if(p2 >=0)
                        {
                            time1=time.substring(0,p2);
                            timeStringArray[j]=time1;
                        }
                       // timeStringArray[j]=time;
                    }
                    
                }
                
                for(int i=0;i<count;i++)
                {
                    for(int j=0;j<count;j++)
                    {
                        network.getHospitalList().get(i).setDistanceFromEmergencyLocation(Float.parseFloat(distanceStringArray[i]));
                        network.getHospitalList().get(i).setTimeTakenToReachTheAccidentLoc(Float.parseFloat(timeStringArray[i]));
                    }
                }
                
             }
              
                catch(Exception e)
                {
                System.out.println("The exception occured >> "+e.getMessage());
                }

             DistancePojo capRes = new Gson().fromJson(outputString, DistancePojo.class);
              //System.out.println("capRes >> "+capRes);
             
            
        }

        catch(Exception e)
        {
            System.err.println("The exception is "+e);
        }

        
        DefaultTableModel model = (DefaultTableModel) hospitalTable.getModel();
        
        model.setRowCount(0);
        
        for(Hospital h:network.getHospitalList())
        {
         Object[] row1 = new Object[3];
            row1[0] =  h;
            row1[1] =  h.getHospitalLocation();
            row1[2] =  h.getSpeciality();
         
     
            model.addRow(row1);   
        }
        
        
        DefaultTableModel model1 = (DefaultTableModel) hospitalTable_additional.getModel();
        
        model1.setRowCount(0);
        
        for(Hospital h:network.getHospitalList())
        {
         Object[] row2 = new Object[4];
            row2[0] =  h;
            row2[1] =  h.getNumberOfEmptyBeds();
            row2[2] =  h.getDistanceFromEmergencyLocation();
            row2[3] =h.getTimeTakenToReachTheAccidentLoc();
     
            model1.addRow(row2);   
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
        processBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        hospitalTable = new javax.swing.JTable();
        viewDetailsButton = new javax.swing.JButton();
        alertHospitalBtn = new javax.swing.JButton();
        networkCombo = new javax.swing.JComboBox();
        additionalDetailsTable = new javax.swing.JScrollPane();
        hospitalTable_additional = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        otherHospitalsTable = new javax.swing.JTable();
        networkL = new javax.swing.JLabel();
        alertDoctorBtn = new javax.swing.JButton();
        captureLicenceBtn = new javax.swing.JButton();
        alertPoliceBtn = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        FireStationTable = new javax.swing.JTable();
        alertFireStationBtn = new javax.swing.JButton();
        networkComboFireStation = new javax.swing.JComboBox();
        additionalDetailsTable3 = new javax.swing.JScrollPane();
        FireStationTable_additional = new javax.swing.JTable();
        networkL3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        otherFireStations = new javax.swing.JTable();
        header = new javax.swing.JLabel();

        setBackground(new java.awt.Color(102, 255, 204));

        jPanel1.setBackground(new java.awt.Color(102, 255, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        emergencyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sender", "Location of emergency", "Nature of emergency", "Priority"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(emergencyTable);
        if (emergencyTable.getColumnModel().getColumnCount() > 0) {
            emergencyTable.getColumnModel().getColumn(1).setMinWidth(250);
            emergencyTable.getColumnModel().getColumn(1).setPreferredWidth(250);
            emergencyTable.getColumnModel().getColumn(1).setMaxWidth(250);
        }

        processBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        processBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Drone/Images/Processing.png"))); // NOI18N
        processBtn.setText("Process  Work Request");
        processBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                processBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 673, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(260, 260, 260)
                .addComponent(processBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(processBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(296, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Work requests", jPanel1);

        jPanel2.setBackground(new java.awt.Color(102, 255, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        hospitalTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hospital Name", "Hospital Location", "Speciality"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(hospitalTable);
        if (hospitalTable.getColumnModel().getColumnCount() > 0) {
            hospitalTable.getColumnModel().getColumn(2).setResizable(false);
        }

        viewDetailsButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        viewDetailsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Drone/Images/moreDetails.png"))); // NOI18N
        viewDetailsButton.setText("View additional details");
        viewDetailsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewDetailsButtonActionPerformed(evt);
            }
        });

        alertHospitalBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        alertHospitalBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Drone/Images/hospital.png"))); // NOI18N
        alertHospitalBtn.setText("Alert the hospital");
        alertHospitalBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alertHospitalBtnActionPerformed(evt);
            }
        });

        networkCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                networkComboActionPerformed(evt);
            }
        });

        additionalDetailsTable.setEnabled(false);

        hospitalTable_additional.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hospital Name", "Number of beds available", "Distance from location (in kms)", "Time taken (in mins)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        hospitalTable_additional.setEnabled(false);
        additionalDetailsTable.setViewportView(hospitalTable_additional);

        otherHospitalsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hospital Name", "Distance from location", "Number of beds available", "Speciality", "Time taken (in mins)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Float.class, java.lang.Integer.class, java.lang.String.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(otherHospitalsTable);
        if (otherHospitalsTable.getColumnModel().getColumnCount() > 0) {
            otherHospitalsTable.getColumnModel().getColumn(3).setHeaderValue("Speciality");
        }

        networkL.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        networkL.setText("Network:");

        alertDoctorBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        alertDoctorBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Drone/Images/doctor.png"))); // NOI18N
        alertDoctorBtn.setText("Alert the on call doctor");
        alertDoctorBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alertDoctorBtnActionPerformed(evt);
            }
        });

        captureLicenceBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        captureLicenceBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Drone/Images/capture.png"))); // NOI18N
        captureLicenceBtn.setText("Capture license plate");
        captureLicenceBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                captureLicenceBtnActionPerformed(evt);
            }
        });

        alertPoliceBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        alertPoliceBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Drone/Images/police.png"))); // NOI18N
        alertPoliceBtn.setText("Alert the police");
        alertPoliceBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alertPoliceBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(174, 174, 174)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(alertHospitalBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(captureLicenceBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))
                        .addGap(65, 65, 65)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(alertDoctorBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(alertPoliceBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGap(28, 28, 28)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(additionalDetailsTable, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
                                .addComponent(jScrollPane3)))))
                .addContainerGap(29, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(networkL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(networkCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(221, 221, 221))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(viewDetailsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(239, 239, 239))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(viewDetailsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(additionalDetailsTable, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(alertHospitalBtn)
                    .addComponent(alertDoctorBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(alertPoliceBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(captureLicenceBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(networkCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(networkL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Find and alert hospitals", jPanel2);

        jPanel7.setBackground(new java.awt.Color(102, 255, 204));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        FireStationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "FireStation Name", "FireStation Location"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane10.setViewportView(FireStationTable);

        alertFireStationBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        alertFireStationBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Drone/Images/hospital.png"))); // NOI18N
        alertFireStationBtn.setText("Alert the FireStation");
        alertFireStationBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alertFireStationBtnActionPerformed(evt);
            }
        });

        networkComboFireStation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                networkComboFireStationActionPerformed(evt);
            }
        });

        additionalDetailsTable3.setEnabled(false);

        FireStationTable_additional.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "FireStation Name", "Number of FireEngines available", "Distance from location (in kms)", "Time taken (in mins)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        FireStationTable_additional.setEnabled(false);
        additionalDetailsTable3.setViewportView(FireStationTable_additional);

        networkL3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        networkL3.setText("Network:");

        otherFireStations.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "FireStation Name", "Distance from location", "Number of FireEngines Available", "Time taken (in mins)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Float.class, java.lang.Integer.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(otherFireStations);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(networkL3)
                .addGap(1, 1, 1)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(networkComboFireStation, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(alertFireStationBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(262, 262, 262))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(additionalDetailsTable3, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(additionalDetailsTable3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(alertFireStationBtn)
                .addGap(49, 49, 49)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(networkComboFireStation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(networkL3))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Find and alert FireStation", jPanel7);

        header.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        header.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Drone/Images/drone.png"))); // NOI18N
        header.setText("Drone work area");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 719, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(315, 315, 315)
                        .addComponent(header)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void viewDetailsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewDetailsButtonActionPerformed
        // TODO add your handling code here:

        //   viewDetailsButton.setEnabled(false);
        additionalDetailsTable.setVisible(true);
        
        populateNetworkCombo();
        
        ArrayList<Hospital>hospInSameNetwork=new ArrayList<>();
        ArrayList<Hospital>hospInDifferentNetwork=new ArrayList<>();
        StringBuilder otherHosp=new StringBuilder();
        //  hospitalTable_additional.setVisible(true);
        
//        for(Hospital h:network.getHospitalEnterpriseDirectoryObject().getHospitalList())
//        {
           if(emergency.getNatureOfEmergency().equalsIgnoreCase("Medical Emergency"))
           {
               if(emergency.getDescription().equalsIgnoreCase("Heart attack"))
               {
                    for(Network n:system.getNetworkList())
                    {
                        if(n==network)
                        {
                            for(Hospital h:network.getHospitalList())
                                {  
                                    if(h.getSpeciality().equalsIgnoreCase("Cardiothoracic") && h.getNumberOfEmptyBeds()>0)
                                        {
                                            if(emergency.getPriority()>=7)
                                            {
                                              //  System.err.println("the priority is "+emergency.getPriority());
                                                if(h.getTimeTakenToReachTheAccidentLoc()<10)
                                                {
                                                   // System.out.println("the time taken to reach the dest is less than 10"+h.getTimeTakenToReachTheAccidentLoc());
                                                    hospInSameNetwork.add(h);
                                                   // hospitalWithSpeciality.setText("The hospital "+h.getHospitalName()+" in "+network.getNetworkName()+"network takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                    JOptionPane.showMessageDialog(this, "The hospital "+h.getHospitalName()+" in "+network.getNetworkName()+"network takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                    break;
                                                }
                                                
                                                else
                                                {
                                                   // System.out.println("the time taken to reach the destination is more than 10"+h.getTimeTakenToReachTheAccidentLoc());
                                                    for(Hospital h1:n.getHospitalList())
                                                    {
                                                        if(h1.getTimeTakenToReachTheAccidentLoc()<10)
                                                        {
                                                            //System.err.println("other hospitals time which is less than 10 "+h1.getTimeTakenToReachTheAccidentLoc());
                                                             hospInSameNetwork.add(h1);
                                                           //  hospitalWithSpeciality.setText("The hospital is more than 10 minutes away,alert another hospital"+ h1.getHospitalName()+" which takes "+h1.getTimeTakenToReachTheAccidentLoc()+" minutes.");
                                                             JOptionPane.showMessageDialog(this, "The hospital is more than 10 minutes away,alert another hospital"+ h1.getHospitalName()+" which takes "+h1.getTimeTakenToReachTheAccidentLoc()+" minutes.");
                                                             break;
                                                        }
                                                        
                                                        
                                                    }
                                                    
                                                    if(hospInSameNetwork.isEmpty())
                                                    {
                                                       // System.out.println("none of the hospitals have the time less than 10");
                                                        int count=n.getHospitalList().size();
                                                        float time[] = new float[count];
                                                        for(int i=0;i<count;i++)
                                                        {
                                                        n.getHospitalList().get(i);
                                                        time[i]=n.getHospitalList().get(i).getTimeTakenToReachTheAccidentLoc();
                                                        //System.err.println("the time is "+time[i]);
                                                        }
                                                        
                                                        float smallest=time[0];
                                                            for(int i=1;i<time.length;i++)
                                                            {
                                                                if (time[i] < smallest)
                                                                smallest = time[i];
                                                            }
                                                            Hospital hosp=null;
                                                            for(Hospital h3:n.getHospitalList())
                                                            {
                                                                
                                                                if(h3.getTimeTakenToReachTheAccidentLoc()==smallest && h3.getSpeciality().equalsIgnoreCase("Cardiothoracic") && h3.getNumberOfEmptyBeds()>0)
                                                                {
                                                                   
                                                                    hosp=h3;
                                                                    break;
                                                                }
                                                                
                                                                else if(h3.getTimeTakenToReachTheAccidentLoc()==smallest)
                                                                {
                                                                    hosp=h3;
                                                                    break;
                                                                }
                                                            }
                                                            
                                                       // System.err.println("the smallest distance hospital is "+hosp.getTimeTakenToReachTheAccidentLoc());
                                                        //hospitalWithSpeciality.setText("The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName());
                                                        JOptionPane.showMessageDialog(this, "The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName());
                                                            hospInSameNetwork.add(hosp);
                                                        break;
                                                    }
                                                }
                                            }
                                            
                                            else
                                            {
                                                hospInSameNetwork.add(h);
                                               // hospitalWithSpeciality.setText("Since priority is less, assign to hospital "+h.getHospitalName());
                                                JOptionPane.showMessageDialog(this, "Since priority is less, assign to hospital "+h.getHospitalName());
                                                break;
                                            }
                                           
                                        }
                                }
                            if(!hospInSameNetwork.isEmpty())
                            {                              
                                 break;
                            }
                               
                        }
                        
                        else
                        {
                            calculate(n);
              
                            for(Hospital h:n.getHospitalList())
                                {  
                                  
                                    if(h.getSpeciality().equalsIgnoreCase("Cardiothoracic")&& h.getNumberOfEmptyBeds()>0)
                                        {
                                           if(emergency.getPriority()>=7)
                                            {
                                                ////System.err.println("the priority is "+emergency.getPriority());
                                                if(h.getTimeTakenToReachTheAccidentLoc()<15)
                                                {
                                                  //  System.out.println("the time taken to reach the dest is less than 10"+h.getTimeTakenToReachTheAccidentLoc());
                                                    hospInDifferentNetwork.add(h);
                                                    //otherNetworkHosp1.setText("The hospital "+h.getHospitalName()+" takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                  //   hospitalWithSpeciality.setText("The hospital "+h.getHospitalName()+" takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                   JOptionPane.showMessageDialog(this, "The hospital "+h.getHospitalName()+" takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                    break;
                                                }
                                                
                                                else
                                                {
                                                              
//                              
                                                        int count=network.getHospitalList().size();
                                                        float time[] = new float[count];
                                                        for(int i=0;i<count;i++)
                                                        {
                                                        network.getHospitalList().get(i);
                                                        time[i]=network.getHospitalList().get(i).getTimeTakenToReachTheAccidentLoc();
                                                            System.err.println("the time is "+time[i]);
                                                        }
                                                        
                                                        float smallest=time[0];
                                                            for(int i=1;i<time.length;i++)
                                                            {
                                                                if (time[i] < smallest)
                                                                smallest = time[i];
                                                            }
                                                            Hospital hosp=null;
                                                            for(Hospital h3:network.getHospitalList())
                                                            {
                                                                if(h3.getTimeTakenToReachTheAccidentLoc()==smallest)
                                                                {
                                                                    hosp=h3;
                                                                }
                                                            }
                                                            
                                                       // System.err.println("the smallest distance hospital is "+hosp.getTimeTakenToReachTheAccidentLoc());
                                                        //otherNetworkHosp1.setText("The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName()+" which is "+hosp.getTimeTakenToReachTheAccidentLoc()+" away.");
                                                       // hospitalWithSpeciality.setText("The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName()+" which is "+hosp.getTimeTakenToReachTheAccidentLoc()+" away.");
                                                         JOptionPane.showMessageDialog(this, "The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName()+" which is "+hosp.getTimeTakenToReachTheAccidentLoc()+" away.");
                                                            hospInDifferentNetwork.add(hosp);
                                                        break;
                                                  //  }
                                                }
                                            }
                                            
                                            else
                                            {
                                                hospInDifferentNetwork.add(h);
                                               // otherNetworkHosp1.setText("Since priority is less, assign to hospital "+h.getHospitalName()+" which is "+h.getTimeTakenToReachTheAccidentLoc()+" minutes away");
                                             //   hospitalWithSpeciality.setText("Since priority is less, assign to hospital "+h.getHospitalName()+" which is "+h.getTimeTakenToReachTheAccidentLoc()+" minutes away");
                                               JOptionPane.showMessageDialog(this, "Since priority is less, assign to hospital "+h.getHospitalName()+" which is "+h.getTimeTakenToReachTheAccidentLoc()+" minutes away");
                                                       
                                                break;
                                            }
                                           
                                    
                                    
                                }                           
                        }
                            
                            if(!hospInDifferentNetwork.isEmpty())
                            {
                                break;
                            }
                    }
               }
               
               }
               
               else if(emergency.getDescription().equalsIgnoreCase("Asthama attack"))
               {
                   for(Network n:system.getNetworkList())
                    {
                        if(n==network)
                        {
                            for(Hospital h:network.getHospitalList())
                                {  
                                    if(h.getSpeciality().equalsIgnoreCase("Allergist") && h.getNumberOfEmptyBeds()>0)
                                        {
                                            if(emergency.getPriority()>=7)
                                            {
                                              //  System.err.println("the priority is "+emergency.getPriority());
                                                if(h.getTimeTakenToReachTheAccidentLoc()<10)
                                                {
                                                   // System.out.println("the time taken to reach the dest is less than 10"+h.getTimeTakenToReachTheAccidentLoc());
                                                    hospInSameNetwork.add(h);
                                                  //  hospitalWithSpeciality.setText("The hospital "+h.getHospitalName()+" in "+network.getNetworkName()+"network takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                   JOptionPane.showMessageDialog(this, "The hospital "+h.getHospitalName()+" in "+network.getNetworkName()+"network takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                    break;
                                                }
                                                
                                                else
                                                {
                                                   // System.out.println("the time taken to reach the destination is more than 10"+h.getTimeTakenToReachTheAccidentLoc());
                                                    for(Hospital h1:n.getHospitalList())
                                                    {
                                                       // System.out.println(h1.getHospitalName());
                                                        if(h1.getTimeTakenToReachTheAccidentLoc()<10)
                                                        {
                                                            //System.err.println("other hospitals time which is less than 10 "+h1.getTimeTakenToReachTheAccidentLoc());
                                                             hospInSameNetwork.add(h1);
                                                            // hospitalWithSpeciality.setText("The hospital is more than 10 minutes away,alert another hospital"+ h1.getHospitalName()+" which takes "+h1.getTimeTakenToReachTheAccidentLoc()+" minutes.");
                                                             JOptionPane.showMessageDialog(this, "The hospital is more than 10 minutes away,alert another hospital"+ h1.getHospitalName()+" which takes "+h1.getTimeTakenToReachTheAccidentLoc()+" minutes.");
                                                             break;
                                                        }
                                                        
                                                        
                                                    }
                                                    
                                                    if(hospInSameNetwork.isEmpty())
                                                    {
                                                       // System.out.println("none of the hospitals have the time less than 10");
                                                        int count=n.getHospitalList().size();
                                                        float time[] = new float[count];
                                                        for(int i=0;i<count;i++)
                                                        {
                                                        n.getHospitalList().get(i);
                                                        time[i]=n.getHospitalList().get(i).getTimeTakenToReachTheAccidentLoc();
                                                        //System.err.println("the time is "+time[i]);
                                                        }
                                                        
                                                        float smallest=time[0];
                                                            for(int i=1;i<time.length;i++)
                                                            {
                                                                if (time[i] < smallest)
                                                                smallest = time[i];
                                                            }
                                                            Hospital hosp=null;
                                                            for(Hospital h3:n.getHospitalList())
                                                            {
                                                                
                                                                if(h3.getTimeTakenToReachTheAccidentLoc()==smallest && h3.getSpeciality().equalsIgnoreCase("Cardiothoracic") && h3.getNumberOfEmptyBeds()>0)
                                                                {
                                                                   
                                                                    hosp=h3;
                                                                    break;
                                                                }
                                                                
                                                                else if(h3.getTimeTakenToReachTheAccidentLoc()==smallest)
                                                                {
                                                                    hosp=h3;
                                                                    break;
                                                                }
                                                            }
                                                            
                                                       // System.err.println("the smallest distance hospital is "+hosp.getTimeTakenToReachTheAccidentLoc());
                                                      //  hospitalWithSpeciality.setText("The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName());
                                                          JOptionPane.showMessageDialog(this, "The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName());
                                                            hospInSameNetwork.add(hosp);
                                                        break;
                                                    }
                                                }
                                            }
                                            
                                            else
                                            {
                                                hospInSameNetwork.add(h);
                                               // hospitalWithSpeciality.setText("Since priority is less, assign to hospital "+h.getHospitalName());
                                                JOptionPane.showMessageDialog(this, "Since priority is less, assign to hospital "+h.getHospitalName());
                                                break;
                                            }
                                           
                                        }
                                }
                            if(!hospInSameNetwork.isEmpty())
                            {                              
                                 break;
                            }
                               
                        }
                        
                        else
                        {
                            calculate(n);
              
                            for(Hospital h:n.getHospitalList())
                                {  
                                  
                                    if(h.getSpeciality().equalsIgnoreCase("Allergist")&& h.getNumberOfEmptyBeds()>0)
                                        {
                                           if(emergency.getPriority()>=7)
                                            {
                                                ////System.err.println("the priority is "+emergency.getPriority());
                                                if(h.getTimeTakenToReachTheAccidentLoc()<15)
                                                {
                                                  //  System.out.println("the time taken to reach the dest is less than 10"+h.getTimeTakenToReachTheAccidentLoc());
                                                    hospInDifferentNetwork.add(h);
                                                 //   otherNetworkHosp1.setText("The hospital "+h.getHospitalName()+" takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                   //    hospitalWithSpeciality.setText("The hospital "+h.getHospitalName()+" takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                    JOptionPane.showMessageDialog(this, "The hospital "+h.getHospitalName()+" takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                    break;
                                                }
                                                
                                                else
                                                {
                                                              
//                              
                                                        int count=network.getHospitalList().size();
                                                        float time[] = new float[count];
                                                        for(int i=0;i<count;i++)
                                                        {
                                                        network.getHospitalList().get(i);
                                                        time[i]=network.getHospitalList().get(i).getTimeTakenToReachTheAccidentLoc();
                                                            System.err.println("the time is "+time[i]);
                                                        }
                                                        
                                                        float smallest=time[0];
                                                            for(int i=1;i<time.length;i++)
                                                            {
                                                                if (time[i] < smallest)
                                                                smallest = time[i];
                                                            }
                                                            Hospital hosp=null;
                                                            for(Hospital h3:network.getHospitalList())
                                                            {
                                                                if(h3.getTimeTakenToReachTheAccidentLoc()==smallest)
                                                                {
                                                                    hosp=h3;
                                                                }
                                                            }
                                                            
                                                      
                                                        // hospitalWithSpeciality.setText("The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName()+" which is "+hosp.getTimeTakenToReachTheAccidentLoc()+" away.");
                                                          JOptionPane.showMessageDialog(this, "The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName()+" which is "+hosp.getTimeTakenToReachTheAccidentLoc()+" away.");
                                                                  
                                                            hospInDifferentNetwork.add(hosp);
                                                        break;
                                                  
                                                }
                                            }
                                            
                                            else
                                            {
                                                hospInDifferentNetwork.add(h);
                                             
                                              //  hospitalWithSpeciality.setText("                     Since priority is less, assign to hospital "+h.getHospitalName()+" which is "+h.getTimeTakenToReachTheAccidentLoc()+" minutes away");
                                                JOptionPane.showMessageDialog(this, "Since priority is less, assign to hospital "+h.getHospitalName()+" which is "+h.getTimeTakenToReachTheAccidentLoc()+" minutes away");
                                                break;
                                            }
                                           
                                    
                                    
                                }                           
                        }
                            
                            if(!hospInDifferentNetwork.isEmpty())
                            {
                                break;
                            }
                    }
               }
               }
           }
           
           else if(emergency.getNatureOfEmergency().equalsIgnoreCase("Fire Emergency"))
           {
               if(emergency.getDescription().equalsIgnoreCase("Fire")){
               for(Network n:system.getNetworkList())
                    {
                        if(n==network)
                        {
                            for(Hospital h:network.getHospitalList())
                                {  
                                    if(h.getSpeciality().equalsIgnoreCase("Plastics") && h.getNumberOfEmptyBeds()>0)
                                        {
                                            if(emergency.getPriority()>=7)
                                            {
                                              //  System.err.println("the priority is "+emergency.getPriority());
                                                if(h.getTimeTakenToReachTheAccidentLoc()<10)
                                                {
                                                   // System.out.println("the time taken to reach the dest is less than 10"+h.getTimeTakenToReachTheAccidentLoc());
                                                    hospInSameNetwork.add(h);
                                                   // hospitalWithSpeciality.setText("The hospital "+h.getHospitalName()+" in "+network.getNetworkName()+"network takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                    JOptionPane.showMessageDialog(this, "The hospital "+h.getHospitalName()+" in "+network.getNetworkName()+"network takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                            
                                                    break;
                                                }
                                                
                                                else
                                                {
                                                   // System.out.println("the time taken to reach the destination is more than 10"+h.getTimeTakenToReachTheAccidentLoc());
                                                    for(Hospital h1:n.getHospitalList())
                                                    {
                                                       // System.out.println(h1.getHospitalName());
                                                        if(h1.getTimeTakenToReachTheAccidentLoc()<10)
                                                        {
                                                            //System.err.println("other hospitals time which is less than 10 "+h1.getTimeTakenToReachTheAccidentLoc());
                                                             hospInSameNetwork.add(h1);
                                                           //  hospitalWithSpeciality.setText("The hospital is more than 10 minutes away,alert another hospital"+ h1.getHospitalName()+" which takes "+h1.getTimeTakenToReachTheAccidentLoc()+" minutes.");
                                                             JOptionPane.showMessageDialog(this, "The hospital is more than 10 minutes away,alert another hospital"+ h1.getHospitalName()+" which takes "+h1.getTimeTakenToReachTheAccidentLoc()+" minutes.");
                                                                     
                                                                     
                                                             break;
                                                        }
                                                        
                                                        
                                                    }
                                                    
                                                    if(hospInSameNetwork.isEmpty())
                                                    {
                                                       // System.out.println("none of the hospitals have the time less than 10");
                                                        int count=n.getHospitalList().size();
                                                        float time[] = new float[count];
                                                        for(int i=0;i<count;i++)
                                                        {
                                                        n.getHospitalList().get(i);
                                                        time[i]=n.getHospitalList().get(i).getTimeTakenToReachTheAccidentLoc();
                                                        //System.err.println("the time is "+time[i]);
                                                        }
                                                        
                                                        float smallest=time[0];
                                                            for(int i=1;i<time.length;i++)
                                                            {
                                                                if (time[i] < smallest)
                                                                smallest = time[i];
                                                            }
                                                            Hospital hosp=null;
                                                            for(Hospital h3:n.getHospitalList())
                                                            {
                                                                
                                                                if(h3.getTimeTakenToReachTheAccidentLoc()==smallest && h3.getSpeciality().equalsIgnoreCase("Cardiothoracic") && h3.getNumberOfEmptyBeds()>0)
                                                                {
                                                                   
                                                                    hosp=h3;
                                                                    break;
                                                                }
                                                                
                                                                else if(h3.getTimeTakenToReachTheAccidentLoc()==smallest)
                                                                {
                                                                    hosp=h3;
                                                                    break;
                                                                }
                                                            }
                                                            
                                                       // System.err.println("the smallest distance hospital is "+hosp.getTimeTakenToReachTheAccidentLoc());
                                                       // hospitalWithSpeciality.setText("The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName());
                                                        JOptionPane.showMessageDialog(this, "The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName());
                                                                
                                                            hospInSameNetwork.add(hosp);
                                                        break;
                                                    }
                                                }
                                            }
                                            
                                            else
                                            {
                                                hospInSameNetwork.add(h);
                                              //  hospitalWithSpeciality.setText("Since priority is less, assign to hospital "+h.getHospitalName());
                                               JOptionPane.showMessageDialog(this, "Since priority is less, assign to hospital "+h.getHospitalName());
                                                       
                                                break;
                                            }
                                           
                                        }
                                }
                            if(!hospInSameNetwork.isEmpty())
                            {                              
                                 break;
                            }
                               
                        }
                        
                        else
                        {
                            calculate(n);
              
                            for(Hospital h:n.getHospitalList())
                                {  
                                  
                                    if(h.getSpeciality().equalsIgnoreCase("Plastics")&& h.getNumberOfEmptyBeds()>0)
                                        {
                                           if(emergency.getPriority()>=7)
                                            {
                                                ////System.err.println("the priority is "+emergency.getPriority());
                                                if(h.getTimeTakenToReachTheAccidentLoc()<15)
                                                {
                                                  //  System.out.println("the time taken to reach the dest is less than 10"+h.getTimeTakenToReachTheAccidentLoc());
                                                    hospInDifferentNetwork.add(h);
                                                   // otherNetworkHosp1.setText("The hospital "+h.getHospitalName()+" takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                   //  hospitalWithSpeciality.setText("The hospital "+h.getHospitalName()+" takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                    JOptionPane.showMessageDialog(this, "The hospital "+h.getHospitalName()+" takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                    break;
                                                }
                                                
                                                else
                                                {
                                                              
//                              
                                                        int count=network.getHospitalList().size();
                                                        float time[] = new float[count];
                                                        for(int i=0;i<count;i++)
                                                        {
                                                        network.getHospitalList().get(i);
                                                        time[i]=network.getHospitalList().get(i).getTimeTakenToReachTheAccidentLoc();
                                                            System.err.println("the time is "+time[i]);
                                                        }
                                                        
                                                        float smallest=time[0];
                                                            for(int i=1;i<time.length;i++)
                                                            {
                                                                if (time[i] < smallest)
                                                                smallest = time[i];
                                                            }
                                                            Hospital hosp=null;
                                                            for(Hospital h3:network.getHospitalList())
                                                            {
                                                                if(h3.getTimeTakenToReachTheAccidentLoc()==smallest)
                                                                {
                                                                    hosp=h3;
                                                                }
                                                            }
                                                            
                                                       // System.err.println("the smallest distance hospital is "+hosp.getTimeTakenToReachTheAccidentLoc());
                                                     //   otherNetworkHosp1.setText("The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName()+" which is "+hosp.getTimeTakenToReachTheAccidentLoc()+" away.");
                                                          // hospitalWithSpeciality.setText("The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName()+" which is "+hosp.getTimeTakenToReachTheAccidentLoc()+" away.");
                                                            JOptionPane.showMessageDialog(this, "The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName()+" which is "+hosp.getTimeTakenToReachTheAccidentLoc()+" away.");
                                                                    
                                                            hospInDifferentNetwork.add(hosp);
                                                        break;
                                                  //  }
                                                }
                                            }
                                            
                                            else
                                            {
                                                hospInDifferentNetwork.add(h);
                                              //  otherNetworkHosp1.setText("Since priority is less, assign to hospital "+h.getHospitalName()+" which is "+h.getTimeTakenToReachTheAccidentLoc()+" minutes away");
                                                //  hospitalWithSpeciality.setText("Since priority is less, assign to hospital "+h.getHospitalName()+" which is "+h.getTimeTakenToReachTheAccidentLoc()+" minutes away");
                                                JOptionPane.showMessageDialog(this, "Since priority is less, assign to hospital "+h.getHospitalName()+" which is "+h.getTimeTakenToReachTheAccidentLoc()+" minutes away");
                                                        
                                                break;
                                            }
                                           
                                    
                                    
                                }                           
                        }
                            
                            if(!hospInDifferentNetwork.isEmpty())
                            {
                                break;
                            }
                    }
               }
               }
           }
        
           else if(emergency.getNatureOfEmergency().equalsIgnoreCase("Accident Emergency"))
           {
               if(emergency.getDescription().equalsIgnoreCase("Car accident-Head Injury"))
               {
               for(Network n:system.getNetworkList())
                    {
                        if(n==network)
                        {
                            for(Hospital h:network.getHospitalList())
                                {  
                                    if(h.getSpeciality().equalsIgnoreCase("Neurology") && h.getNumberOfEmptyBeds()>0)
                                        {
                                            if(emergency.getPriority()>=7)
                                            {
                                              //  System.err.println("the priority is "+emergency.getPriority());
                                                if(h.getTimeTakenToReachTheAccidentLoc()<10)
                                                {
                                                   // System.out.println("the time taken to reach the dest is less than 10"+h.getTimeTakenToReachTheAccidentLoc());
                                                    hospInSameNetwork.add(h);
                                                   // hospitalWithSpeciality.setText("The hospital "+h.getHospitalName()+" in "+network.getNetworkName()+"network takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                    JOptionPane.showMessageDialog(this, "The hospital "+h.getHospitalName()+" in "+network.getNetworkName()+"network takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                    break;
                                                }
                                                
                                                else
                                                {
                                                   // System.out.println("the time taken to reach the destination is more than 10"+h.getTimeTakenToReachTheAccidentLoc());
                                                    for(Hospital h1:n.getHospitalList())
                                                    {
                                                       // System.out.println(h1.getHospitalName());
                                                        if(h1.getTimeTakenToReachTheAccidentLoc()<10)
                                                        {
                                                            //System.err.println("other hospitals time which is less than 10 "+h1.getTimeTakenToReachTheAccidentLoc());
                                                             hospInSameNetwork.add(h1);
                                                            // hospitalWithSpeciality.setText("The hospital is more than 10 minutes away,alert another hospital"+ h1.getHospitalName()+" which takes "+h1.getTimeTakenToReachTheAccidentLoc()+" minutes.");
                                                             JOptionPane.showMessageDialog(this, "The hospital is more than 10 minutes away,alert another hospital"+ h1.getHospitalName()+" which takes "+h1.getTimeTakenToReachTheAccidentLoc()+" minutes.");
                                                                     
                                                             break;
                                                        }
                                                        
                                                        
                                                    }
                                                    
                                                    if(hospInSameNetwork.isEmpty())
                                                    {
                                                       // System.out.println("none of the hospitals have the time less than 10");
                                                        int count=n.getHospitalList().size();
                                                        float time[] = new float[count];
                                                        for(int i=0;i<count;i++)
                                                        {
                                                        n.getHospitalList().get(i);
                                                        time[i]=n.getHospitalList().get(i).getTimeTakenToReachTheAccidentLoc();
                                                        //System.err.println("the time is "+time[i]);
                                                        }
                                                        
                                                        float smallest=time[0];
                                                            for(int i=1;i<time.length;i++)
                                                            {
                                                                if (time[i] < smallest)
                                                                smallest = time[i];
                                                            }
                                                            Hospital hosp=null;
                                                            for(Hospital h3:n.getHospitalList())
                                                            {
                                                                
                                                                if(h3.getTimeTakenToReachTheAccidentLoc()==smallest && h3.getSpeciality().equalsIgnoreCase("Cardiothoracic") && h3.getNumberOfEmptyBeds()>0)
                                                                {
                                                                   
                                                                    hosp=h3;
                                                                    break;
                                                                }
                                                                
                                                                else if(h3.getTimeTakenToReachTheAccidentLoc()==smallest)
                                                                {
                                                                    hosp=h3;
                                                                    break;
                                                                }
                                                            }
                                                            
                                                       // System.err.println("the smallest distance hospital is "+hosp.getTimeTakenToReachTheAccidentLoc());
                                                      //  hospitalWithSpeciality.setText("The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName());
                                                       JOptionPane.showMessageDialog(this, "The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName());
                                                               
                                                               
                                                            hospInSameNetwork.add(hosp);
                                                        break;
                                                    }
                                                }
                                            }
                                            
                                            else
                                            {
                                                hospInSameNetwork.add(h);
                                             //   hospitalWithSpeciality.setText("Since priority is less, assign to hospital "+h.getHospitalName());
                                               JOptionPane.showMessageDialog(this, "Since priority is less, assign to hospital "+h.getHospitalName());
                                                       
                                                break;
                                            }
                                           
                                        }
                                }
                            if(!hospInSameNetwork.isEmpty())
                            {                              
                                 break;
                            }
                               
                        }
                        
                        else
                        {
                            calculate(n);
              
                            for(Hospital h:n.getHospitalList())
                                {  
                                  
                                    if(h.getSpeciality().equalsIgnoreCase("Neurology")&& h.getNumberOfEmptyBeds()>0)
                                        {
                                           if(emergency.getPriority()>=7)
                                            {
                                                ////System.err.println("the priority is "+emergency.getPriority());
                                                if(h.getTimeTakenToReachTheAccidentLoc()<15)
                                                {
                                                  //  System.out.println("the time taken to reach the dest is less than 10"+h.getTimeTakenToReachTheAccidentLoc());
                                                    hospInDifferentNetwork.add(h);
                                                   // otherNetworkHosp1.setText("The hospital "+h.getHospitalName()+" takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                   //  hospitalWithSpeciality.setText("The hospital "+h.getHospitalName()+" takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                    JOptionPane.showMessageDialog(this, "The hospital "+h.getHospitalName()+" takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                            
                                                    break;
                                                }
                                                
                                                else
                                                {
                                                              
//                              
                                                        int count=network.getHospitalList().size();
                                                        float time[] = new float[count];
                                                        for(int i=0;i<count;i++)
                                                        {
                                                        network.getHospitalList().get(i);
                                                        time[i]=network.getHospitalList().get(i).getTimeTakenToReachTheAccidentLoc();
                                                            System.err.println("the time is "+time[i]);
                                                        }
                                                        
                                                        float smallest=time[0];
                                                            for(int i=1;i<time.length;i++)
                                                            {
                                                                if (time[i] < smallest)
                                                                smallest = time[i];
                                                            }
                                                            Hospital hosp=null;
                                                            for(Hospital h3:network.getHospitalList())
                                                            {
                                                                if(h3.getTimeTakenToReachTheAccidentLoc()==smallest)
                                                                {
                                                                    hosp=h3;
                                                                }
                                                            }
                                                            
                                                       // System.err.println("the smallest distance hospital is "+hosp.getTimeTakenToReachTheAccidentLoc());
                                                      //  otherNetworkHosp1.setText("The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName()+" which is "+hosp.getTimeTakenToReachTheAccidentLoc()+" away.");
                                                       //   hospitalWithSpeciality.setText("The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName()+" which is "+hosp.getTimeTakenToReachTheAccidentLoc()+" away.");
                                                         JOptionPane.showMessageDialog(this, "The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName()+" which is "+hosp.getTimeTakenToReachTheAccidentLoc()+" away.");
                                                                 
                                                            hospInDifferentNetwork.add(hosp);
                                                        break;
                                                  //  }
                                                }
                                            }
                                            
                                            else
                                            {
                                                hospInDifferentNetwork.add(h);
                                              //  otherNetworkHosp1.setText("Since priority is less, assign to hospital "+h.getHospitalName()+" which is "+h.getTimeTakenToReachTheAccidentLoc()+" minutes away");
                                               // hospitalWithSpeciality.setText("Since priority is less, assign to hospital "+h.getHospitalName()+" which is "+h.getTimeTakenToReachTheAccidentLoc()+" minutes away");
                                               JOptionPane.showMessageDialog(this, "Since priority is less, assign to hospital "+h.getHospitalName()+" which is "+h.getTimeTakenToReachTheAccidentLoc()+" minutes away");
                                                break;
                                            }
                                           
                                    
                                    
                                }                           
                        }
                            
                            if(!hospInDifferentNetwork.isEmpty())
                            {
                                break;
                            }
                    }
               }
               }
               
               
               else if(emergency.getDescription().equalsIgnoreCase("Car accident-Body Injury"))
               {
               for(Network n:system.getNetworkList())
                    {
                        if(n==network)
                        {
                            for(Hospital h:network.getHospitalList())
                                {  
                                    if(h.getSpeciality().equalsIgnoreCase("Orthopedic") && h.getNumberOfEmptyBeds()>0)
                                        {
                                            if(emergency.getPriority()>=7)
                                            {
                                              //  System.err.println("the priority is "+emergency.getPriority());
                                                if(h.getTimeTakenToReachTheAccidentLoc()<10)
                                                {
                                                   // System.out.println("the time taken to reach the dest is less than 10"+h.getTimeTakenToReachTheAccidentLoc());
                                                    hospInSameNetwork.add(h);
                                                   // hospitalWithSpeciality.setText("The hospital "+h.getHospitalName()+" in "+network.getNetworkName()+"network takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                    JOptionPane.showMessageDialog(this, "The hospital "+h.getHospitalName()+" in "+network.getNetworkName()+"network takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                            
                                                            
                                                    break;
                                                }
                                                
                                                else
                                                {
                                                   // System.out.println("the time taken to reach the destination is more than 10"+h.getTimeTakenToReachTheAccidentLoc());
                                                    for(Hospital h1:n.getHospitalList())
                                                    {
                                                       // System.out.println(h1.getHospitalName());
                                                        if(h1.getTimeTakenToReachTheAccidentLoc()<10)
                                                        {
                                                            //System.err.println("other hospitals time which is less than 10 "+h1.getTimeTakenToReachTheAccidentLoc());
                                                             hospInSameNetwork.add(h1);
                                                           //  hospitalWithSpeciality.setText("The hospital is more than 10 minutes away,alert another hospital"+ h1.getHospitalName()+" which takes "+h1.getTimeTakenToReachTheAccidentLoc()+" minutes.");
                                                             JOptionPane.showMessageDialog(this, "The hospital is more than 10 minutes away,alert another hospital"+ h1.getHospitalName()+" which takes "+h1.getTimeTakenToReachTheAccidentLoc()+" minutes.");
                                                             break;
                                                        }
                                                        
                                                        
                                                    }
                                                    
                                                    if(hospInSameNetwork.isEmpty())
                                                    {
                                                       // System.out.println("none of the hospitals have the time less than 10");
                                                        int count=n.getHospitalList().size();
                                                        float time[] = new float[count];
                                                        for(int i=0;i<count;i++)
                                                        {
                                                        n.getHospitalList().get(i);
                                                        time[i]=n.getHospitalList().get(i).getTimeTakenToReachTheAccidentLoc();
                                                        //System.err.println("the time is "+time[i]);
                                                        }
                                                        
                                                        float smallest=time[0];
                                                            for(int i=1;i<time.length;i++)
                                                            {
                                                                if (time[i] < smallest)
                                                                smallest = time[i];
                                                            }
                                                            Hospital hosp=null;
                                                            for(Hospital h3:n.getHospitalList())
                                                            {
                                                                
                                                                if(h3.getTimeTakenToReachTheAccidentLoc()==smallest && h3.getSpeciality().equalsIgnoreCase("Cardiothoracic") && h3.getNumberOfEmptyBeds()>0)
                                                                {
                                                                   
                                                                    hosp=h3;
                                                                    break;
                                                                }
                                                                
                                                                else if(h3.getTimeTakenToReachTheAccidentLoc()==smallest)
                                                                {
                                                                    hosp=h3;
                                                                    break;
                                                                }
                                                            }
                                                            
                                                       // System.err.println("the smallest distance hospital is "+hosp.getTimeTakenToReachTheAccidentLoc());
                                                      //  hospitalWithSpeciality.setText("The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName());
                                                        JOptionPane.showMessageDialog(this, "The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName());
                                                            hospInSameNetwork.add(hosp);
                                                        break;
                                                    }
                                                }
                                            }
                                            
                                            else
                                            {
                                                hospInSameNetwork.add(h);
                                             //   hospitalWithSpeciality.setText("Since priority is less, assign to hospital "+h.getHospitalName());
                                               JOptionPane.showMessageDialog(this, "Since priority is less, assign to hospital "+h.getHospitalName());
                                                       
                                                break;
                                            }
                                           
                                        }
                                }
                            if(!hospInSameNetwork.isEmpty())
                            {                              
                                 break;
                            }
                               
                        }
                        
                        else
                        {
                            calculate(n);
              
                            for(Hospital h:n.getHospitalList())
                                {  
                                  
                                    if(h.getSpeciality().equalsIgnoreCase("Orthopedic")&& h.getNumberOfEmptyBeds()>0)
                                        {
                                           if(emergency.getPriority()>=7)
                                            {
                                                ////System.err.println("the priority is "+emergency.getPriority());
                                                if(h.getTimeTakenToReachTheAccidentLoc()<15)
                                                {
                                                  //  System.out.println("the time taken to reach the dest is less than 10"+h.getTimeTakenToReachTheAccidentLoc());
                                                    hospInDifferentNetwork.add(h);
                                                   // otherNetworkHosp1.setText("The hospital "+h.getHospitalName()+" takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                 //   hospitalWithSpeciality.setText("The hospital "+h.getHospitalName()+" takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                   JOptionPane.showMessageDialog(this, "The hospital "+h.getHospitalName()+" takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                           
                                                    break;
                                                }
                                                
                                                else
                                                {
                                                              
//                              
                                                        int count=network.getHospitalList().size();
                                                        float time[] = new float[count];
                                                        for(int i=0;i<count;i++)
                                                        {
                                                        network.getHospitalList().get(i);
                                                        time[i]=network.getHospitalList().get(i).getTimeTakenToReachTheAccidentLoc();
                                                            System.err.println("the time is "+time[i]);
                                                        }
                                                        
                                                        float smallest=time[0];
                                                            for(int i=1;i<time.length;i++)
                                                            {
                                                                if (time[i] < smallest)
                                                                smallest = time[i];
                                                            }
                                                            Hospital hosp=null;
                                                            for(Hospital h3:network.getHospitalList())
                                                            {
                                                                if(h3.getTimeTakenToReachTheAccidentLoc()==smallest)
                                                                {
                                                                    hosp=h3;
                                                                }
                                                            }
                                                            
                                                       // System.err.println("the smallest distance hospital is "+hosp.getTimeTakenToReachTheAccidentLoc());
                                                     //   otherNetworkHosp1.setText("The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName()+" which is "+hosp.getTimeTakenToReachTheAccidentLoc()+" away.");
                                                       //   hospitalWithSpeciality.setText("The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName()+" which is "+hosp.getTimeTakenToReachTheAccidentLoc()+" away.");
                                                         JOptionPane.showMessageDialog(this, "The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName()+" which is "+hosp.getTimeTakenToReachTheAccidentLoc()+" away.");
                                                                 
                                                            hospInDifferentNetwork.add(hosp);
                                                          break;
                                                  //  }
                                                }
                                            }
                                            
                                            else
                                            {
                                                hospInDifferentNetwork.add(h);
                                              //  otherNetworkHosp1.setText("Since priority is less, assign to hospital "+h.getHospitalName()+" which is "+h.getTimeTakenToReachTheAccidentLoc()+" minutes away");
                                                 // hospitalWithSpeciality.setText("Since priority is less, assign to hospital "+h.getHospitalName()+" which is "+h.getTimeTakenToReachTheAccidentLoc()+" minutes away");
                                               JOptionPane.showMessageDialog(this, "Since priority is less, assign to hospital "+h.getHospitalName()+" which is "+h.getTimeTakenToReachTheAccidentLoc()+" minutes away");
                                                       
                                                break;
                                            }
                                           
                                    
                                    
                                }                           
                        }
                            
                            if(!hospInDifferentNetwork.isEmpty())
                            {
                                break;
                            }
                    }
               }
               }
               
               else if(emergency.getDescription().equalsIgnoreCase("Drowning"))
               {
                   
               for(Network n:system.getNetworkList())
                    {
                        if(n==network)
                        {
                            for(Hospital h:network.getHospitalList())
                                {  
                                    if(h.getSpeciality().equalsIgnoreCase("Cardiothoracic") && h.getNumberOfEmptyBeds()>0)
                                        {
                                            
                                            if(emergency.getPriority()>=7)
                                            {
                                              //  System.err.println("the priority is "+emergency.getPriority());
                                                if(h.getTimeTakenToReachTheAccidentLoc()<10)
                                                {
                                                   // System.out.println("the time taken to reach the dest is less than 10"+h.getTimeTakenToReachTheAccidentLoc());
                                                    hospInSameNetwork.add(h);
                                                  //  hospitalWithSpeciality.setText("The hospital "+h.getHospitalName()+" in "+network.getNetworkName()+"network takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                    JOptionPane.showMessageDialog(this, "The hospital "+h.getHospitalName()+" in "+network.getNetworkName()+"network takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                    break;
                                                }
                                                
                                                else
                                                {
                                                   // System.out.println("the time taken to reach the destination is more than 10"+h.getTimeTakenToReachTheAccidentLoc());
                                                    for(Hospital h1:n.getHospitalList())
                                                    {
                                                       // System.out.println(h1.getHospitalName());
                                                        if(h1.getTimeTakenToReachTheAccidentLoc()<10)
                                                        {
                                                            //System.err.println("other hospitals time which is less than 10 "+h1.getTimeTakenToReachTheAccidentLoc());
                                                             hospInSameNetwork.add(h1);
                                                           //  hospitalWithSpeciality.setText("The hospital is more than 10 minutes away,alert another hospital"+ h1.getHospitalName()+" which takes "+h1.getTimeTakenToReachTheAccidentLoc()+" minutes.");
                                                             JOptionPane.showMessageDialog(this, "The hospital is more than 10 minutes away,alert another hospital"+ h1.getHospitalName()+" which takes "+h1.getTimeTakenToReachTheAccidentLoc()+" minutes.");
                                                                     
                                                                     
                                                             break;
                                                        }
                                                        
                                                        
                                                    }
                                                    
                                                    if(hospInSameNetwork.isEmpty())
                                                    {
                                                       // System.out.println("none of the hospitals have the time less than 10");
                                                        int count=n.getHospitalList().size();
                                                        float time[] = new float[count];
                                                        for(int i=0;i<count;i++)
                                                        {
                                                        n.getHospitalList().get(i);
                                                        time[i]=n.getHospitalList().get(i).getTimeTakenToReachTheAccidentLoc();
                                                        //System.err.println("the time is "+time[i]);
                                                        }
                                                        
                                                        float smallest=time[0];
                                                            for(int i=1;i<time.length;i++)
                                                            {
                                                                if (time[i] < smallest)
                                                                smallest = time[i];
                                                            }
                                                            Hospital hosp=null;
                                                            for(Hospital h3:n.getHospitalList())
                                                            {
                                                                
                                                                if(h3.getTimeTakenToReachTheAccidentLoc()==smallest && h3.getSpeciality().equalsIgnoreCase("Cardiothoracic") && h3.getNumberOfEmptyBeds()>0)
                                                                {
                                                                   
                                                                    hosp=h3;
                                                                    break;
                                                                }
                                                                
                                                                else if(h3.getTimeTakenToReachTheAccidentLoc()==smallest)
                                                                {
                                                                    hosp=h3;
                                                                    break;
                                                                }
                                                            }
                                                            
                                                       // System.err.println("the smallest distance hospital is "+hosp.getTimeTakenToReachTheAccidentLoc());
                                                       // hospitalWithSpeciality.setText("The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName());
                                                        JOptionPane.showMessageDialog(this, "The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName());
                                                                
                                                            hospInSameNetwork.add(hosp);
                                                        break;
                                                    }
                                                }
                                            }
                                            
                                            else
                                            {
                                                hospInSameNetwork.add(h);
                                             //   hospitalWithSpeciality.setText("Since priority is less, assign to hospital "+h.getHospitalName());
                                               JOptionPane.showMessageDialog(this, "Since priority is less, assign to hospital "+h.getHospitalName());
                                                break;
                                            }
                                           
                                        }
                                }
                            if(!hospInSameNetwork.isEmpty())
                            {                              
                                 break;
                            }
                               
                        }
                        
                        else
                        {
                            calculate(n);
              
                            for(Hospital h:n.getHospitalList())
                                {  
                                  
                                    if(h.getSpeciality().equalsIgnoreCase("Cardiothoracic")&& h.getNumberOfEmptyBeds()>0)
                                        {
                                           if(emergency.getPriority()>=7)
                                            {
                                                ////System.err.println("the priority is "+emergency.getPriority());
                                                if(h.getTimeTakenToReachTheAccidentLoc()<15)
                                                {
                                                  //  System.out.println("the time taken to reach the dest is less than 10"+h.getTimeTakenToReachTheAccidentLoc());
                                                    hospInDifferentNetwork.add(h);
                                               //     otherNetworkHosp1.setText("The hospital "+h.getHospitalName()+" takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                    //  hospitalWithSpeciality.setText("The hospital "+h.getHospitalName()+" takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                    JOptionPane.showMessageDialog(this, "The hospital "+h.getHospitalName()+" takes "+h.getTimeTakenToReachTheAccidentLoc()+" minutes to reach the emergency");
                                                    break;
                                                }
                                                
                                                else
                                                {
                                                              
//                              
                                                        int count=network.getHospitalList().size();
                                                        float time[] = new float[count];
                                                        for(int i=0;i<count;i++)
                                                        {
                                                        network.getHospitalList().get(i);
                                                        time[i]=network.getHospitalList().get(i).getTimeTakenToReachTheAccidentLoc();
                                                            System.err.println("the time is "+time[i]);
                                                        }
                                                        
                                                        float smallest=time[0];
                                                            for(int i=1;i<time.length;i++)
                                                            {
                                                                if (time[i] < smallest)
                                                                smallest = time[i];
                                                            }
                                                            Hospital hosp=null;
                                                            for(Hospital h3:network.getHospitalList())
                                                            {
                                                                if(h3.getTimeTakenToReachTheAccidentLoc()==smallest)
                                                                {
                                                                    hosp=h3;
                                                                }
                                                            }
                                                            
                                                       // System.err.println("the smallest distance hospital is "+hosp.getTimeTakenToReachTheAccidentLoc());
                                                       // otherNetworkHosp1.setText("The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName()+" which is "+hosp.getTimeTakenToReachTheAccidentLoc()+" away.");
                                                        //   hospitalWithSpeciality.setText("The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName()+" which is "+hosp.getTimeTakenToReachTheAccidentLoc()+" away.");  
                                                        
                                                            JOptionPane.showMessageDialog(this, "The distance of all the hospitals is more than 10kms, the closest hospital is "+hosp.getHospitalName()+" which is "+hosp.getTimeTakenToReachTheAccidentLoc()+" away.");
                                                                    
                                                                    
                                                            hospInDifferentNetwork.add(hosp);
                                                        break;
                                                  //  }
                                                }
                                            }
                                            
                                            else
                                            {
                                                hospInDifferentNetwork.add(h);
                                              //  otherNetworkHosp1.setText("Since priority is less, assign to hospital "+h.getHospitalName()+" which is "+h.getTimeTakenToReachTheAccidentLoc()+" minutes away");
                                               //   hospitalWithSpeciality.setText("Since priority is less, assign to hospital "+h.getHospitalName()+" which is "+h.getTimeTakenToReachTheAccidentLoc()+" minutes away");
                                                JOptionPane.showMessageDialog(this, "Since priority is less, assign to hospital "+h.getHospitalName()+" which is "+h.getTimeTakenToReachTheAccidentLoc()+" minutes away");
                                                        
                                                break;
                                            }
                                           
                                    
                                    
                                }                           
                        }
                            
                            if(!hospInDifferentNetwork.isEmpty())
                            {
                                break;
                            }
                    }
               }
               }
           }
          

    }//GEN-LAST:event_viewDetailsButtonActionPerformed

   
    private void processBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_processBtnActionPerformed
        // TODO add your handling code here:
        
        
        int rowSelected=emergencyTable.getSelectedRow();
        if(rowSelected>=0)
        {
            Emergency emg=(Emergency) emergencyTable.getValueAt(rowSelected,1);
            if(emg.getNatureOfEmergency().equalsIgnoreCase("Accident Emergency"))
            {
                populateFireStation();
                populateHospitalTable();
                JOptionPane.showMessageDialog(this,"Choose the hospital and alert them");
                
                
                viewButtons(true);
                captureLicenceBtn.setEnabled(true);
                alertPoliceBtn.setEnabled(true);
            }
            
            else
            {
               populateFireStation();
                populateHospitalTable();
                JOptionPane.showMessageDialog(this,"Choose the hospital and alert them");
                viewButtons(true);
                 captureLicenceBtn.setEnabled(false);
                alertPoliceBtn.setEnabled(false);
            }
        }
        
        else
        {
            JOptionPane.showMessageDialog(this,"Choose an emergency form the table");
        }
        
    }//GEN-LAST:event_processBtnActionPerformed

    private void networkComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_networkComboActionPerformed
        // TODO add your handling code here:
        Network n=(Network) networkCombo.getSelectedItem();
        
       if(n!=null)
       {
        populateHospitalTableForTheNetwork(n);
       }
        otherHospitalsTable.removeAll();
    }//GEN-LAST:event_networkComboActionPerformed

    
    private void alertHospitalBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alertHospitalBtnActionPerformed
        // TODO add your handling code here:
       int rowSelected=emergencyTable.getSelectedRow();
       if(rowSelected>=0)
       {
        request=(WorkRequest) emergencyTable.getValueAt(rowSelected,0);
        Emergency emer=(Emergency) emergencyTable.getValueAt(rowSelected, 1);
        if (request != null&&(!emer.getEmergencyStatus().equalsIgnoreCase("Routed to Hospital")))
        {  
             int row=hospitalTable.getSelectedRow();
             int otherHospitalRow=otherHospitalsTable.getSelectedRow();
             if(row>=0&&otherHospitalRow>=0)
             {
                 JOptionPane.showMessageDialog(this, "Choose only one hospital");
                 populateHospitalTable();
                 for(Network n:system.getNetworkList())
                 {
                     if(n.getNetworkName().equalsIgnoreCase("Cambridge"))
                     {
                         populateHospitalTableForTheNetwork(n);
                     }
                 }
                 
             }
             
             else
             {
            
             if(row>=0)
             {
                request.setSender(account);
                Hospital h=(Hospital) hospitalTable.getValueAt(row, 0);
                UserAccount ua=h.getUserAccountDirectory().getUserAccountList().get(0);
                request.setReceiver(ua);
                emer.setEmergencyStatus("Routed to Hospital");
                ua.getWorkQueue().getWorkRequestList().add(request);
                JOptionPane.showMessageDialog(this,"The hospital has been alerted about the emergency");
                Date d=new Date();
                emer.setHospitalAlerted(d);
                
            }
            
            else if(otherHospitalRow>=0)
            {
                request.setSender(account);
                Hospital h=(Hospital) otherHospitalsTable.getValueAt(otherHospitalRow, 0);
                UserAccount ua=h.getUserAccountDirectory().getUserAccountList().get(0);
                request.setReceiver(ua);
                emer.setEmergencyStatus("Routed to Hospital");
                ua.getWorkQueue().getWorkRequestList().add(request);
                JOptionPane.showMessageDialog(this,"The hospital has been alerted about the emergency");
                Date d=new Date();
                emer.setHospitalAlerted(d);
            }
          
            
            else
            {
                JOptionPane.showMessageDialog(this, "Choose a hospital from the table");
            }
             
             }
                
           
          
        }
        
        else if(emer.getEmergencyStatus().equalsIgnoreCase("Routed to Hospital"))
        {
            JOptionPane.showMessageDialog(this, "A hospital has already been alerted about this emergency");
        }
       }
       
       else
       {
           JOptionPane.showMessageDialog(this,"Choose an emergency from the emergency table");
       }
                  
    }//GEN-LAST:event_alertHospitalBtnActionPerformed

    private void alertDoctorBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alertDoctorBtnActionPerformed
        // TODO add your handling code here:
        int rowSelectedFromHospitalsInNetwork=hospitalTable.getSelectedRow();
        int rowSelectedFromHospitalsInOtherNetwork=otherHospitalsTable.getSelectedRow();
        int requestRow=emergencyTable.getSelectedRow();
        Emergency emg=null;
       if(requestRow>=0)
       {
        request=(WorkRequest) emergencyTable.getValueAt(requestRow,0);
        emg=(Emergency) emergencyTable.getValueAt(requestRow, 1);
       }
       
       else
       {
           JOptionPane.showMessageDialog(this,"Choose the emergency from the emergency table");
       }
       
       if(rowSelectedFromHospitalsInNetwork>=0&&rowSelectedFromHospitalsInOtherNetwork>=0)
       {
           JOptionPane.showMessageDialog(this, "Choose only one hospital");
           populateHospitalTable();
                 for(Network n:system.getNetworkList())
                 {
                     if(n.getNetworkName().equalsIgnoreCase("Cambridge"))
                     {
                         populateHospitalTableForTheNetwork(n);
                     }
                 }
           
       }
       
       else
       {
        if((rowSelectedFromHospitalsInNetwork>=0)&&(!emg.getEmergencyStatus().equalsIgnoreCase("Alerted doctor")))
        {
            Hospital h=(Hospital) hospitalTable.getValueAt(rowSelectedFromHospitalsInNetwork, 0);
           
            for(Organisation org:h.getOrganizationDirectory().getOrganisationList())
            {
                if(org instanceof DoctorOrganization)
                {
                    for(Employee emp:org.getEmployeeDirectory().getEmployeeList())
                    {
                        
                        if(((Doctor)emp).isDoctorsAvailablityStatus()==true)
                        {
                            if(((Doctor)emp).getDoctorsSpeciality().equalsIgnoreCase(emergency.getDescription()))
                            {
                                for(UserAccount ua:org.getUserAccountDirectory().getUserAccountList())
                                {
                                    if(ua.getEmployee()==(Doctor)emp)
                                    {
                                      
                                        request.setSender(account);
                                        request.setReceiver(ua);
                                        emergency.setEmergencyStatus("Alerted doctor");
                                        ua.getWorkQueue().getWorkRequestList().add(request);
                                        ((Doctor)emp).setDoctorsAvailablityStatus(false);
                                        Date d=new Date();
                                        emg.setAlertOnCallDoctor(d);
                                        
                                        JOptionPane.showMessageDialog(this,"The doctor "+ ((Doctor)emp).getName() +" from hospital "+h.getHospitalName()+ " has been alerted about the emergency");
                                        break;
                                    }
                                   
                                }
                                
                            }
                            
                          
                        }
                        if(emergency.getEmergencyStatus().equalsIgnoreCase("Alerted doctor"))
                        {
                            break;
                        }
                    }
                    
                   if(!emergency.getEmergencyStatus().equalsIgnoreCase("Alerted doctor"))
                      {
                           for(Employee emp:org.getEmployeeDirectory().getEmployeeList())
                             {
                                if(((Doctor)emp).isDoctorsAvailablityStatus())
                                    {
                                         for(UserAccount ua:org.getUserAccountDirectory().getUserAccountList())
                                             {
                                                if(ua.getEmployee()==(Doctor)emp)
                                                     {
                                                        request.setSender(account);
                                                        request.setReceiver(ua);
                                                        emergency.setEmergencyStatus("Alerted doctor");
                                                        ua.getWorkQueue().getWorkRequestList().add(request);
                                                        ((Doctor)emp).setDoctorsAvailablityStatus(false);
                                                        JOptionPane.showMessageDialog(this,"The doctor"+ ((Doctor)emp).getName() +" from hospital "+h.getHospitalName()+ " has been alerted about the emergency");
                                                         Date d=new Date();
                                       
                                                         emg.setAlertOnCallDoctor(d);
                                                         break;
                                                        
                                                     }
                                   
                                             }
                                    }
                                if(emergency.getEmergencyStatus().equalsIgnoreCase("Alerted doctor"))
                                     {
                                        break;
                                     }
                             }
                     }
                   
                   if(!emergency.getEmergencyStatus().equalsIgnoreCase("Alerted doctor"))
                   {
                     
                     
                     for(Network n:system.getNetworkList())
                     {
                         for(Hospital hospital:n.getHospitalList())
                            {
                                for(Organisation organisation:hospital.getOrganizationDirectory().getOrganisationList())
                                {
                                    if(organisation instanceof DoctorOrganization)
                                    {
                                        for(Employee e:organisation.getEmployeeDirectory().getEmployeeList())
                                        {
                                            if(((Doctor)e).isDoctorsAvailablityStatus()&&((Doctor)e).getDoctorsSpeciality().equalsIgnoreCase(emergency.getDescription()))
                                            {
                                                for(UserAccount user:organisation.getUserAccountDirectory().getUserAccountList())
                                                  {
                                                    if(user.getEmployee()==(Doctor)e)
                                                     {
                                                        request.setSender(account);
                                                        request.setReceiver(user);
                                                        emergency.setEmergencyStatus("Alerted doctor");
                                                        user.getWorkQueue().getWorkRequestList().add(request);
                                                        ((Doctor)e).setDoctorsAvailablityStatus(false);
                                                        Date d=new Date();
                                                        emg.setAlertOnCallDoctor(d);
                                                        JOptionPane.showMessageDialog(this,"There are no doctors available in hospital, so the doctor " + ((Doctor)e).getName()+ " from " +hospital.getHospitalName()+" has been alerted about the emergency");
                                                        break;
                                                     }
                                   
                                                  }
                                            }
                                            if(emergency.getEmergencyStatus().equalsIgnoreCase("Alerted doctor"))
                                                {
                                                    break;
                                                    
                                                }
                                            
                                        }
                                    }
                                    
                                
                                         if(emergency.getEmergencyStatus().equalsIgnoreCase("Alerted doctor"))
                                                {
                                                    break;
                                                    
                                                }
                                  }  
                                
                                if(emergency.getEmergencyStatus().equalsIgnoreCase("Alerted doctor"))
                                                {
                                                    break;
                                                    
                                                }
                            }
                         
                                        if(emergency.getEmergencyStatus().equalsIgnoreCase("Alerted doctor"))
                                                {
                                                    break;
                                                    
                                                }
                     }
                   }  
               }
                if(emergency.getEmergencyStatus().equalsIgnoreCase("Alerted doctor"))
                  {
                      break;
                  }
           }
        }
        
        else if((rowSelectedFromHospitalsInOtherNetwork>=0)&&(!emg.getEmergencyStatus().equalsIgnoreCase("Alerted doctor")))
        {
            Hospital h=(Hospital) otherHospitalsTable.getValueAt(rowSelectedFromHospitalsInOtherNetwork, 0);
           
            for(Organisation org:h.getOrganizationDirectory().getOrganisationList())
            {
                if(org instanceof DoctorOrganization)
                {
                    for(Employee emp:org.getEmployeeDirectory().getEmployeeList())
                    {
                        if(((Doctor)emp).isDoctorsAvailablityStatus())
                        {
                            if(((Doctor)emp).getDoctorsSpeciality().equalsIgnoreCase(emergency.getDescription()))
                            {
                                for(UserAccount ua:org.getUserAccountDirectory().getUserAccountList())
                                {
                                    if(ua.getEmployee()==(Doctor)emp)
                                    {
                                        request.setSender(account);
                                        request.setReceiver(ua);
                                        emergency.setEmergencyStatus("Alerted doctor");
                                        ua.getWorkQueue().getWorkRequestList().add(request);
                                        ((Doctor)emp).setDoctorsAvailablityStatus(false);
                                        Date d=new Date();
                                        emg.setAlertOnCallDoctor(d);
                                        JOptionPane.showMessageDialog(this,"The doctor"+ ((Doctor)emp).getName() +" from hospital "+h.getHospitalName()+ " has been alerted about the emergency");
                                        
                                        break;
                                    }
                                   
                                }
                                
                            }
                            
                          
                        }
                        if(emergency.getEmergencyStatus().equalsIgnoreCase("Alerted doctor"))
                        {
                            break;
                        }
                    }
                    
                   if(!emergency.getEmergencyStatus().equalsIgnoreCase("Alerted doctor"))
                      {
                           for(Employee emp:org.getEmployeeDirectory().getEmployeeList())
                             {
                                if(((Doctor)emp).isDoctorsAvailablityStatus())
                                    {
                                         for(UserAccount user:org.getUserAccountDirectory().getUserAccountList())
                                             {
                                                if(user.getEmployee()==(Doctor)emp)
                                                     {
                                                        request.setSender(account);
                                                        request.setReceiver(user);
                                                        emergency.setEmergencyStatus("Alerted doctor");
                                                        user.getWorkQueue().getWorkRequestList().add(request);
                                                        ((Doctor)emp).setDoctorsAvailablityStatus(false);
                                                        Date d=new Date();
                                                        emg.setAlertOnCallDoctor(d);
                                                        JOptionPane.showMessageDialog(this,"The doctor"+ ((Doctor)emp).getName() +" from hospital "+h.getHospitalName()+ " has been alerted about the emergency");
                                                        break;
                                                     }
                                   
                                             }
                                    }
                                if(emergency.getEmergencyStatus().equalsIgnoreCase("Alerted doctor"))
                                     {
                                        break;
                                     }
                             }
                     }
                   
                   if(!emergency.getEmergencyStatus().equalsIgnoreCase("Alerted doctor"))
                   {
                    
                     
                     for(Network n:system.getNetworkList())
                     {
                         for(Hospital hospital:n.getHospitalList())
                            {
                                for(Organisation organisation:hospital.getOrganizationDirectory().getOrganisationList())
                                {
                                    if(organisation instanceof DoctorOrganization)
                                    {
                                        for(Employee e:organisation.getEmployeeDirectory().getEmployeeList())
                                        {
                                            if(((Doctor)e).isDoctorsAvailablityStatus()&&((Doctor)e).getDoctorsSpeciality().equalsIgnoreCase(emergency.getDescription()))
                                            {
                                                for(UserAccount ua:org.getUserAccountDirectory().getUserAccountList())
                                                  {
                                                    if(ua.getEmployee()==(Doctor)e)
                                                     {
                                                        request.setSender(account);
                                                        request.setReceiver(ua);
                                                        emergency.setEmergencyStatus("Alerted doctor");
                                                        ua.getWorkQueue().getWorkRequestList().add(request);
                                                        ((Doctor)e).setDoctorsAvailablityStatus(false);
                                                        Date d=new Date();
                                                        emg.setAlertOnCallDoctor(d);
                                                        JOptionPane.showMessageDialog(this, "There are no doctors available in hospital, so the doctor " + ((Doctor)e).getName()+ " from " +hospital.getHospitalName()+" has been alerted about the emergency" ); 
                                                        break;
                                                     }
                                   
                                                  }
                                            }
                                            if(emergency.getEmergencyStatus().equalsIgnoreCase("Alerted doctor"))
                                                {
                                                    break;
                                                    
                                                }
                                        }
                                    }
                                    if(emergency.getEmergencyStatus().equalsIgnoreCase("Alerted doctor"))
                                                {
                                                    break;
                                                    
                                                }
                                    
                                }
                                if(emergency.getEmergencyStatus().equalsIgnoreCase("Alerted doctor"))
                                                {
                                                    break;
                                                    
                                                }
                            }
                         if(emergency.getEmergencyStatus().equalsIgnoreCase("Alerted doctor"))
                                                {
                                                    break;
                                                    
                                                }
                     }
                     
                     } 
                   if(emergency.getEmergencyStatus().equalsIgnoreCase("Alerted doctor"))
                                                {
                                                    break;
                                                    
                                                }
               }
           }
        }
        
        else
        {
            if(emg.getEmergencyStatus().equalsIgnoreCase("Alerted doctor"))
            {
            JOptionPane.showMessageDialog(this,"The doctor has already been allocated to this emergency");
            }
            
            else
            {
            JOptionPane.showMessageDialog(this,"Choose the hospital from the table");
            }
            
        }
        
       }
    }//GEN-LAST:event_alertDoctorBtnActionPerformed

    private void captureLicenceBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_captureLicenceBtnActionPerformed
        // TODO add your handling code here:
       
        
        if(licenPlateCaptured==false)
        {
        Random rand = new Random(); 
        int index=rand.nextInt(5);
        emergency.setLicensePlateURL(system.getLicensePlateDir().getLicensePlateList().get(index).getLicensePlateNumber());
        JOptionPane.showMessageDialog(this, "License plate added");
        licenPlateCaptured=true;
        }
        else
        {
            JOptionPane.showMessageDialog(this, "The license plate is already captured");
                    
        }
        
     
    }//GEN-LAST:event_captureLicenceBtnActionPerformed

    private void alertPoliceBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alertPoliceBtnActionPerformed
        // TODO add your handling code here:
        
        int row=emergencyTable.getSelectedRow();
        if(row>=0)
        {
            WorkRequest workRequest=(WorkRequest) emergencyTable.getValueAt(row,0);
            Emergency eme=(Emergency) emergencyTable.getValueAt(row, 1);
            for(Enterprise ent:network.getEntDirObj().getEnterpriseList())
            {
                if((ent instanceof PoliceEnterprise )&&(!eme.getEmergencyStatus().equalsIgnoreCase("Alerted the police")))
                {
                    
                    workRequest.setSender(account);
                    workRequest.setReceiver(ent.getUserAccountDirectory().getUserAccountList().get(0));
                    ent.getUserAccountDirectory().getUserAccountList().get(0).getWorkQueue().getWorkRequestList().add(workRequest);
                    EnterpriseAdmin eadmin=(EnterpriseAdmin) ent.getEmployeeDirectory().getEmployeeList().get(0);
                    eadmin.setAvailable(false);
                    
                    eme.setEmergencyStatus("Alerted the police");
                    JOptionPane.showMessageDialog(this,"The "+ent.getName() +" has been alerted about the emergency");
                    Date d=new Date();
                    eme.setPoliceAlerted(d);
                    break;
                }
                
                else
                {
                    if(eme.getEmergencyStatus().equalsIgnoreCase("Alerted the police"))
                    {
                        
                        JOptionPane.showMessageDialog(this, "The police has already been alerted");
                        break;
                    }
                }
            }
        }
        
        else
        {
            JOptionPane.showMessageDialog(this,"Choose an emergency from the table");
        }
    }//GEN-LAST:event_alertPoliceBtnActionPerformed

    private void alertFireStationBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alertFireStationBtnActionPerformed
        // TODO add your handling code here:
        
          int rowSelected=emergencyTable.getSelectedRow();
       if(rowSelected>=0)
       {
        request=(WorkRequest) emergencyTable.getValueAt(rowSelected,0);
        Emergency emer=(Emergency) emergencyTable.getValueAt(rowSelected, 1);
        if (request != null&&(!emer.getEmergencyStatus().equalsIgnoreCase("Routed to FireStation")))
        {  
             int row=FireStationTable.getSelectedRow();
             int otherfirestationrow=otherFireStations.getSelectedRow();
             if(row>=0&&otherfirestationrow>=0)
             {
                 JOptionPane.showMessageDialog(this, "Choose only one FireStation");
                 populateHospitalTable();
                 for(Network n:system.getNetworkList())
                 {
                     if(n.getNetworkName().equalsIgnoreCase("Cambridge"))
                     {
                         populateFireStationTableForTheNetwork(n);
                     }
                 }
                 
             }
             
             else
             {
            
             if(row>=0)
             {
                request.setSender(account);
                FireStation fs=(FireStation) FireStationTable.getValueAt(row, 0);
                UserAccount ua=fs.getUserAccountDirectory().getUserAccountList().get(0);
                request.setReceiver(ua);
                emer.setEmergencyStatus("Routed to FireStation");
                ua.getWorkQueue().getWorkRequestList().add(request);
                JOptionPane.showMessageDialog(this,"The FireStation has been alerted about the emergency");
                Date d=new Date();
                emer.setFireStationAlerted(d);
                
            }
            
            else if(otherfirestationrow>=0)
            {
                request.setSender(account);
                FireStation h=(FireStation) otherFireStations.getValueAt(otherfirestationrow, 0);
                UserAccount ua=h.getUserAccountDirectory().getUserAccountList().get(0);
                request.setReceiver(ua);
                emer.setEmergencyStatus("Routed to FireStation");
                ua.getWorkQueue().getWorkRequestList().add(request);
                JOptionPane.showMessageDialog(this,"The FireStation has been alerted about the emergency");
                Date d=new Date();
                emer.setFireStationAlerted(d);
            }
          
            
            else
            {
                JOptionPane.showMessageDialog(this, "Choose a FireStation from the table");
            }
             
             }
                
           
          
        }
        
        else if(emer.getEmergencyStatus().equalsIgnoreCase("Routed to FireStation"))
        {
            JOptionPane.showMessageDialog(this, "A FireStation has already been alerted about this emergency");
        }
       }
       
       else
       {
           JOptionPane.showMessageDialog(this,"Choose an emergency from the emergency table");
       }
        
        
    }//GEN-LAST:event_alertFireStationBtnActionPerformed

    private void networkComboFireStationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_networkComboFireStationActionPerformed
        // TODO add your handling code here:'
        Network n=(Network) networkCombo.getSelectedItem();

        if(n!=null)
        {
            populateHospitalTableForTheNetwork(n);
        }
        otherHospitalsTable.removeAll();
    }//GEN-LAST:event_networkComboFireStationActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable FireStationTable;
    private javax.swing.JTable FireStationTable_additional;
    private javax.swing.JScrollPane additionalDetailsTable;
    private javax.swing.JScrollPane additionalDetailsTable3;
    private javax.swing.JButton alertDoctorBtn;
    private javax.swing.JButton alertFireStationBtn;
    private javax.swing.JButton alertHospitalBtn;
    private javax.swing.JButton alertPoliceBtn;
    private javax.swing.JButton captureLicenceBtn;
    private javax.swing.JTable emergencyTable;
    private javax.swing.JLabel header;
    private javax.swing.JTable hospitalTable;
    private javax.swing.JTable hospitalTable_additional;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox networkCombo;
    private javax.swing.JComboBox networkComboFireStation;
    private javax.swing.JLabel networkL;
    private javax.swing.JLabel networkL3;
    private javax.swing.JTable otherFireStations;
    private javax.swing.JTable otherHospitalsTable;
    private javax.swing.JButton processBtn;
    private javax.swing.JButton viewDetailsButton;
    // End of variables declaration//GEN-END:variables
}
