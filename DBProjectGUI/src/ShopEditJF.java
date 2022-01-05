
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Stefanos
 */
public class ShopEditJF extends javax.swing.JFrame {

    
    
    private Connection StartConn(){
        String     driverClassName = "org.postgresql.Driver" ;
        String     url = "jdbc:postgresql://localhost:5432/DBProject" ;
        String     username = "postgres";
        String     passwd = "1";
        Connection conn = null;   
        try{
            conn= DriverManager.getConnection(url, username, passwd);
        }catch (SQLException ex){
             System.out.println("Message: " + ex.getMessage());
             System.out.println("SQLState: " + ex.getSQLState());
             System.out.println("ErrorCode: " + ex.getErrorCode());
        }
        return conn;
    }
    
    
    
    private void WindowAtCenter(JFrame objFrame){
        Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int iCoordX = (objDimension.width - objFrame.getWidth()) / 2;
        int iCoordY = (objDimension.height - objFrame.getHeight()) / 2;
        objFrame.setLocation(iCoordX, iCoordY); 
    } 
      
      
    private void ACtionPerformed(java.awt.event.ActionEvent e){
        if (e.getSource() == ShopEditSubmitButton){
                submit();
        }else if (e.getSource() == ShopEditClearButton){
                clear();
        }
        
    }
    

    
    public ShopEditJF() {
        initComponents();
        WindowAtCenter(this);
    }
    
   
    
    public void inisialize(String value){
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;
        Statement stmt_MallID = null;
        Statement stmt_ContractID = null;
        Statement stmt_serviceType = null;
        Statement stmt_ActFrom = null;
        Statement stmt_ActTo = null;
        Statement stmt_Act = null;
        ResultSet rs_MallID = null;
        ResultSet rs_ContractID = null;
        ResultSet rs_serviceType = null;
        ResultSet rs_ActFrom = null;
        ResultSet rs_ActTo = null;
        ResultSet rs_Act = null;
        Pattern pattern;
        Matcher matcher;
        String [] results;
        
        
        
        
        
        int id = 0;
        String [] list_values = value.split(":");
        try{
          id = Integer.parseInt(list_values[1].substring(1,list_values[1].length()-6));  
        }catch (Exception e){
            System.out.print(e);
        }
        
        
        try{
            conn = StartConn();
            //Γεμισμα με τις τιμες απο τον πινακα Malls με το διαθεσιμα ids 
            stmt_MallID = conn.createStatement();
            rs_MallID = stmt_MallID.executeQuery("SELECT select_malll())");
            if (rs_MallID.next() == false){
                ShopEditSubmitButton.setEnabled(false);
                ShopEditClearButton.setEnabled(false);
            }else{
                do{
                    ShopEditMallIDCB.addItem(rs_MallID.getString(1));    
                }while (rs_MallID.next());
            }
            //Γεμισμα με τις τιμες απο τον πινακα contract με το διαθεσημα id
            stmt_ContractID = conn.createStatement();      
            rs_ContractID = stmt_ContractID.executeQuery("SELECT select_aggreement()");
            if (rs_ContractID.next() == false){
                ShopEditSubmitButton.setEnabled(false);
                ShopEditClearButton.setEnabled(false);
            }else{
                do{
                    if (!rs_ContractID.getString(1).equals("-1")){
                        ShopEditContIDCB.addItem(rs_ContractID.getString(1));  
                    }
                    
                }while (rs_ContractID.next());
            }
            //Γεμισμα με τις τιμες απο το constraint για τα Service Type
            stmt_serviceType = conn.createStatement();
            rs_serviceType = stmt_serviceType.executeQuery("SELECT get_store_check_services()");
            if (rs_serviceType.next() == false){
                ShopEditSubmitButton.setEnabled(false);
                ShopEditClearButton.setEnabled(false);
            }else{
                pattern = Pattern.compile("\\[(.*)\\]");
                matcher = pattern.matcher(rs_serviceType.getString(1));
                if (matcher.find()){
                   String [] split_values_at_coma = matcher.group(1).split(",");
                   for (int i=0 ; i < split_values_at_coma.length ; i++){
                       String [] split_value_at_anokato = split_values_at_coma[i].split("::");
                       String valuee = split_value_at_anokato[0].trim();
                       ShopEditServiceTypeCB.addItem(valuee.substring(1, valuee.length()-1));                     
                   }
                }              
            }
            //Εισαγωγη των τιμων του constraint Active From
            stmt_ActFrom = conn.createStatement();
            rs_ActFrom = stmt_ActFrom.executeQuery("SELECT get_store_check_active_from()");
            if (rs_ActFrom.next() == false){
                ShopEditSubmitButton.setEnabled(false);
                ShopEditClearButton.setEnabled(false);
            }else{
                pattern = Pattern.compile("\\[(.*)\\]");
                matcher = pattern.matcher(rs_ActFrom.getString(1));
                if (matcher.find()){
                   String [] split_values_at_coma = matcher.group(1).split(",");
                   for (int i=0 ; i < split_values_at_coma.length ; i++){
                       String [] split_value_at_anokato = split_values_at_coma[i].split("::");
                       String valuee = split_value_at_anokato[0].trim();
                       ShopEditActFromCB.addItem(valuee.substring(1, valuee.length()-1));                     
                   }
                }
            }
            //Εισαγωγη των τιμων του constraint Active To
            stmt_ActTo = conn.createStatement();
            rs_ActTo = stmt_ActFrom.executeQuery("SELECT get_store_check_active_to()");
            if (rs_ActTo.next() == false){
                ShopEditSubmitButton.setEnabled(false);
                ShopEditClearButton.setEnabled(false);
            }else{
                pattern = Pattern.compile("\\[(.*)\\]");
                matcher = pattern.matcher(rs_ActTo.getString(1));
                if (matcher.find()){
                   String [] split_values_at_coma = matcher.group(1).split(",");
                   for (int i=0 ; i < split_values_at_coma.length ; i++){
                       String [] split_value_at_anokato = split_values_at_coma[i].split("::");
                       String valuee = split_value_at_anokato[0].trim();
                       ShopEditActToCB.addItem(valuee.substring(1, valuee.length()-1));                     
                   }
                }
            }
            //Εισαγωγη των τιμων του constraint Active
            stmt_Act = conn.createStatement();
            rs_Act = stmt_Act.executeQuery("SELECT get_store_check_active()");
            if (rs_Act.next() == false){
                ShopEditSubmitButton.setEnabled(false);
                ShopEditClearButton.setEnabled(false);
            }else{
                pattern = Pattern.compile("\\[(.*)\\]");
                matcher = pattern.matcher(rs_Act.getString(1));
                if (matcher.find()){
                    String [] values = matcher.group(1).split(",");
                    ShopEditActCB.addItem(values[0]);
                    ShopEditActCB.addItem(values[1]);
                }
            } 
            

            prepared=conn.prepareStatement("SELECT get_store(?)");
            prepared.setInt(1, id);
            rs = prepared.executeQuery();
            if (rs.next() == false){
                System.out.print("Error");
            }else{
                do{
                    results = rs.getString(1).split(",");     
                }while (rs.next());
                ShopEditIDLabel.setText(results[0].trim());
                ShopEditFloorTF.setText(results[3].trim());
                ShopEditLocationTF.setText(results[4].trim());
                ShopEditShopNameTF.setText(results[1].trim());
                System.out.print(results[2].trim());
                if (((DefaultComboBoxModel)ShopEditMallIDCB.getModel()).getIndexOf(results[2].trim()) >= 0){
                    ShopEditMallIDCB.setSelectedItem(results[2].trim());
                }
                if (((DefaultComboBoxModel)ShopEditActCB.getModel()).getIndexOf(results[7].trim()) >= 0){
                    ShopEditActCB.setSelectedItem(results[7].trim());
                }
                if (((DefaultComboBoxModel)ShopEditActFromCB.getModel()).getIndexOf(results[5].trim()) >= 0){
                    ShopEditActFromCB.setSelectedItem(results[5].trim());
                }
                if (((DefaultComboBoxModel)ShopEditActToCB.getModel()).getIndexOf(results[6].trim()) >= 0){
                    ShopEditActToCB.setSelectedItem(results[6].trim());
                }
                if (((DefaultComboBoxModel)ShopEditContIDCB.getModel()).getIndexOf(results[8].trim()) >= 0){
                    ShopEditContIDCB.setSelectedItem(results[8].trim());
                }
                if (((DefaultComboBoxModel)ShopEditServiceTypeCB.getModel()).getIndexOf(results[9].trim()) >= 0){
                    ShopEditServiceTypeCB.setSelectedItem(results[9].trim());
                }   
            }
            stmt_MallID.close();
            stmt_ContractID.close();
            stmt_serviceType.close();
            stmt_ActFrom.close();
            stmt_ActTo.close();
            stmt_Act.close();
            prepared.close();
        }catch (SQLException ex){
            System.out.println("Message: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("ErrorCode: " + ex.getErrorCode());
        }finally{
            try{
                conn.close();

            }catch (SQLException ex){
                System.out.println("Message: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("ErrorCode: " + ex.getErrorCode());
            }
        }

    }
    
    
    private void clear(){

            ShopEditActCB.setSelectedIndex(0);
            ShopEditActFromCB.setSelectedIndex(0);
            ShopEditActToCB.setSelectedIndex(0);
            ShopEditContIDCB.setSelectedIndex(0);
            ShopEditMallIDCB.setSelectedIndex(0);
            ShopEditServiceTypeCB.setSelectedIndex(0);
            ShopEditFloorTF.setText("");
            ShopEditLocationTF.setText("");
            ShopEditShopNameTF.setText("");
    }
    
    
    
    public void submit(){
        Connection conn = null;
        PreparedStatement prepared = null;
        boolean error = false;
        boolean active;
        String active_from;
        String active_to;
        int contract_id;
        int mall_id;
        String service_type;
        String floor;
        String location;
        String shop_name;
        int shop_id = 0;
        
        
        if ((ShopEditFloorTF.getText().isBlank()) || (ShopEditLocationTF.getText().isBlank()) || (ShopEditShopNameTF.getText().isBlank())){
            javax.swing.JOptionPane.showMessageDialog(null, "Please fill all the fields!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
        }else{
            try{
                shop_id = Integer.parseInt(ShopEditIDLabel.getText().trim());
            }catch (Exception e){
                error = true;
            }
            active = Boolean.parseBoolean((String)ShopEditActCB.getSelectedItem());
            active_from = (String) ShopEditActFromCB.getSelectedItem();
            active_to = (String) ShopEditActToCB.getSelectedItem();
            contract_id = Integer.parseInt((String)ShopEditContIDCB.getSelectedItem());
            mall_id = Integer.parseInt((String)ShopEditMallIDCB.getSelectedItem());  
            service_type = (String) ShopEditServiceTypeCB.getSelectedItem();
            floor = ShopEditFloorTF.getText();
            location = ShopEditLocationTF.getText();
            shop_name = ShopEditShopNameTF.getText();
            System.out.println( shop_id + " " + active_from + " " + active_to + " " + contract_id + " " + mall_id + " " + service_type);

            try{
               conn = StartConn();
               prepared = conn.prepareStatement("SELECT edit_store(?,?,?,?,?,?,?,?,?,?)");
               prepared.setInt(1, shop_id);
               prepared.setString(2, shop_name);
               prepared.setInt(3,mall_id);
               prepared.setString(4, floor);
               prepared.setString(5, location);
               prepared.setString(6,active_from);
               prepared.setString(7,active_to);
               prepared.setBoolean(8, active);
               prepared.setInt(9,contract_id);
               prepared.setString(10, service_type);
               prepared.executeQuery();
               clear();
               javax.swing.JOptionPane.showMessageDialog(null, "The subbmit has been completed","INFO",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }catch (SQLException ex){
                   System.out.println("Message: " + ex.getMessage());
                   System.out.println("SQLState: " + ex.getSQLState());
                   System.out.println("ErrorCode: " + ex.getErrorCode());
           }finally{
               try{
                   conn.close();
                   prepared.close();
               }catch (SQLException ex){
                   System.out.println("Message: " + ex.getMessage());
                   System.out.println("SQLState: " + ex.getSQLState());
                   System.out.println("ErrorCode: " + ex.getErrorCode());
               }
           }
                
            
        }       
}
    
    
    
   @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        ShopEditClearButton = new javax.swing.JButton();
        ShopEditSubmitButton = new javax.swing.JButton();
        ShopEditMallIDCB = new javax.swing.JComboBox<>();
        ShopEditContIDCB = new javax.swing.JComboBox<>();
        ShopEditServiceTypeCB = new javax.swing.JComboBox<>();
        ShopEditShopNameTF = new javax.swing.JTextField();
        ShopEditFloorTF = new javax.swing.JTextField();
        ShopEditLocationTF = new javax.swing.JTextField();
        ShopEditActFromCB = new javax.swing.JComboBox<>();
        ShopEditActToCB = new javax.swing.JComboBox<>();
        ShopEditActCB = new javax.swing.JComboBox<>();
        ShopEditIDLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(200, 255, 240));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel1.setText("Επεξεργασια Καταστηματος");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Shop ID:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("Shop Name:");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("Location:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText("Shooping  Center ID:");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("Floor:");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setText("Active From:");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setText("Active To:");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel9.setText("Service Type:");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setText("Active:");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setText("Contract ID:");

        ShopEditClearButton.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        ShopEditClearButton.setText("Clear");
        ShopEditClearButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ShopEditClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        ShopEditSubmitButton.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        ShopEditSubmitButton.setText("Submit");
        ShopEditSubmitButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ShopEditSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        ShopEditMallIDCB.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        ShopEditMallIDCB.setMaximumRowCount(50);

        ShopEditContIDCB.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        ShopEditContIDCB.setMaximumRowCount(50);

        ShopEditServiceTypeCB.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        ShopEditServiceTypeCB.setMaximumRowCount(50);

        ShopEditShopNameTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        ShopEditFloorTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        ShopEditLocationTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        ShopEditIDLabel.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ShopEditShopNameTF, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                    .addComponent(ShopEditIDLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ShopEditMallIDCB, 0, 143, Short.MAX_VALUE)
                    .addComponent(ShopEditFloorTF, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                    .addComponent(ShopEditLocationTF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(ShopEditClearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(ShopEditSubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(ShopEditActFromCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ShopEditActToCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ShopEditActCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ShopEditContIDCB, 0, 139, Short.MAX_VALUE))
                            .addComponent(ShopEditServiceTypeCB, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(380, 380, 380)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7)
                    .addComponent(ShopEditActFromCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ShopEditIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8)
                    .addComponent(ShopEditShopNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ShopEditActToCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ShopEditFloorTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(ShopEditContIDCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(53, 53, 53)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ShopEditLocationTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(ShopEditServiceTypeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ShopEditSubmitButton)
                            .addComponent(ShopEditClearButton))
                        .addGap(23, 23, 23))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ShopEditMallIDCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel10)
                            .addComponent(ShopEditActCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(55, 55, 55)
                        .addComponent(jLabel6)
                        .addGap(56, 56, 56)
                        .addComponent(jLabel4)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents



    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ShopInsertJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ShopInsertJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ShopInsertJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShopInsertJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ShopEditJF JF = new ShopEditJF();
                JF.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ShopEditActCB;
    private javax.swing.JComboBox<String> ShopEditActFromCB;
    private javax.swing.JComboBox<String> ShopEditActToCB;
    private javax.swing.JButton ShopEditClearButton;
    private javax.swing.JComboBox<String> ShopEditContIDCB;
    private javax.swing.JTextField ShopEditFloorTF;
    private javax.swing.JLabel ShopEditIDLabel;
    private javax.swing.JTextField ShopEditLocationTF;
    private javax.swing.JComboBox<String> ShopEditMallIDCB;
    private javax.swing.JComboBox<String> ShopEditServiceTypeCB;
    private javax.swing.JTextField ShopEditShopNameTF;
    private javax.swing.JButton ShopEditSubmitButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
