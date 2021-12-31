
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Stefanos
 */
public class ShopInsertJF extends javax.swing.JFrame {

    
    
    public Connection StartConn(){
        String     driverClassName = "org.postgresql.Driver" ;
        String     url = "jdbc:postgresql://localhost:5432/DBLabs" ;
        String     username = "postgres";
        String     passwd = "147896325!";
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
      
      
    public void ACtionPerformed(java.awt.event.ActionEvent e){
        if (e.getSource() == ShopInsertSubmitButton){
            
        }else if (e.getSource() == ShopInsertClearButton){
            
        }
        
    }
    

    
    public ShopInsertJF() {
        initComponents();
        WindowAtCenter(this);
        if (inisialize()){
            this.dispose();
        }
    }
    
   
    
    public boolean inisialize(){
        String addressnum=null;
        String value="CHECK (((active_from)::text = ANY ((ARRAY['Monday'::character varying, 'Tuesday'::character varying, 'Wednesday'::character varying, 'Thursday'::character varying, 'Friday'::character varying, 'Saturday'::character varying, 'Sunday'::character varying])::text[])))";
        Pattern pattern = Pattern.compile("\\[(.*)\\]");
        Matcher matcher = pattern.matcher(value);
        if (matcher.find()){
          addressnum = matcher.group(1);
        }
        System.out.println(addressnum);
        
        
        
        
        
        
        boolean flag = false;
        Connection conn = null;
        Statement stmt_MallID = null;
        Statement stmt_ContractID = null;
        Statement stmt_serviceType = null;
        ResultSet rs_MallID = null;
        ResultSet rs_ContractID = null;
        ResultSet rs_serviceType = null;
        
        try{
            conn = StartConn();
            stmt_MallID = conn.createStatement();
            rs_MallID = stmt_MallID.executeQuery("SELECT Select_Mall_id()");
            if (rs_MallID.next() == false){
                flag = true;
                ShopInsertSubmitButton.setEnabled(false);
                ShopInsertClearButton.setEnabled(false);
            }else{
                do{
                    ShopInsertMallIDCB.addItem(rs_MallID.getString(1));    
                }while (rs_MallID.next());
            }
            stmt_ContractID = conn.createStatement();      
            rs_ContractID = stmt_ContractID.executeQuery("SELECT Select_ContractID()");
            if (rs_ContractID.next() == false){
                flag = true;
                ShopInsertSubmitButton.setEnabled(false);
                ShopInsertClearButton.setEnabled(false);
            }else{
                do{
                    ShopInsertContIDCB.addItem(rs_ContractID.getString(1));    
                }while (rs_ContractID.next());
            }
            stmt_serviceType = conn.createStatement();
            rs_serviceType = stmt_serviceType.executeQuery("SELECT Select_ServiceType()");
            if (rs_serviceType.next() == false){
                flag = true;
                ShopInsertSubmitButton.setEnabled(false);
                ShopInsertClearButton.setEnabled(false);
            }else{
                do{
                    ShopInsertServiceTypeCB.addItem(rs_serviceType.getString(1));    
                }while (rs_serviceType.next());
            }
            if (flag){
                javax.swing.JOptionPane.showMessageDialog(null, "Ther is no values at the database please insert values first!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
            }
            rs_serviceType.close();
            rs_ContractID.close();
            rs_MallID.close();
        }catch (SQLException ex){
            System.out.println("SQL Exception");
            while (ex != null){
                System.out.println("Message: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("ErrorCode: " + ex.getErrorCode());
                ex.getNextException();
            }
        }finally{
            try{
                conn.close();
            }catch (SQLException ex){
                System.out.println(ex);
            }
        }
        return flag;
 
    }
    
    
    
    
    public void submit(){
        Connection conn = null;
        PreparedStatement prepared = null;
        
        
        
        
        
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
        ShopInsertClearButton = new javax.swing.JButton();
        ShopInsertSubmitButton = new javax.swing.JButton();
        ShopInsertMallIDCB = new javax.swing.JComboBox<>();
        ShopInsertContIDCB = new javax.swing.JComboBox<>();
        ShopInsertServiceTypeCB = new javax.swing.JComboBox<>();
        ShopInsertShopIDTF = new javax.swing.JTextField();
        ShopInsertShopNameTF = new javax.swing.JTextField();
        ShopInsertFloorTF = new javax.swing.JTextField();
        ShopInsertLocationTF = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(200, 255, 240));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel1.setText("Εισαγωγή νέου καταστήματος");

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

        ShopInsertClearButton.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        ShopInsertClearButton.setText("Clear");
        ShopInsertClearButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        ShopInsertSubmitButton.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        ShopInsertSubmitButton.setText("Submit");
        ShopInsertSubmitButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        ShopInsertMallIDCB.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        ShopInsertMallIDCB.setMaximumRowCount(50);

        ShopInsertContIDCB.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        ShopInsertContIDCB.setMaximumRowCount(50);

        ShopInsertServiceTypeCB.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        ShopInsertServiceTypeCB.setMaximumRowCount(50);

        ShopInsertShopIDTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        ShopInsertShopNameTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        ShopInsertShopNameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShopInsertShopNameTFActionPerformed(evt);
            }
        });

        ShopInsertFloorTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        ShopInsertLocationTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" }));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Yes", "No" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 92, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(ShopInsertShopIDTF)
                    .addComponent(ShopInsertShopNameTF, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ShopInsertMallIDCB, javax.swing.GroupLayout.Alignment.LEADING, 0, 143, Short.MAX_VALUE)
                    .addComponent(ShopInsertFloorTF, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ShopInsertLocationTF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(ShopInsertClearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(ShopInsertSubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ShopInsertContIDCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ShopInsertServiceTypeCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(87, 87, 87))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(280, 280, 280)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(ShopInsertShopIDTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8)
                    .addComponent(ShopInsertShopNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ShopInsertMallIDCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(65, 65, 65))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(62, 62, 62)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(ShopInsertFloorTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ShopInsertContIDCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(59, 59, 59)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(ShopInsertLocationTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ShopInsertServiceTypeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ShopInsertSubmitButton)
                    .addComponent(ShopInsertClearButton))
                .addGap(23, 23, 23))
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

    private void ShopInsertShopNameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShopInsertShopNameTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ShopInsertShopNameTFActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

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
                ShopInsertJF JF = new ShopInsertJF();
                JF.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ShopInsertClearButton;
    private javax.swing.JComboBox<String> ShopInsertContIDCB;
    private javax.swing.JTextField ShopInsertFloorTF;
    private javax.swing.JTextField ShopInsertLocationTF;
    private javax.swing.JComboBox<String> ShopInsertMallIDCB;
    private javax.swing.JComboBox<String> ShopInsertServiceTypeCB;
    private javax.swing.JTextField ShopInsertShopIDTF;
    private javax.swing.JTextField ShopInsertShopNameTF;
    private javax.swing.JButton ShopInsertSubmitButton;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
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
