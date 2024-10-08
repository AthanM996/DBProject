
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import java.sql.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Stefanos
 */
public class InfoShopJF extends javax.swing.JFrame {

    
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
    
    public boolean inisialize(String value){
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;
        String rs_return;
        String[] list_values = value.split(" ");
        int id = Integer.parseInt(list_values[1]);
        boolean flag = true;
      
        try{
            conn = StartConn();
            prepared = conn.prepareStatement("SELECT info_store(?)");
            prepared.setInt(1, id);
            rs = prepared.executeQuery();
            if (rs.next() == false){
                javax.swing.JOptionPane.showMessageDialog(null, "The is an Error at data","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
                flag =false;
            }else{
                do{
                    rs_return = rs.getString(1);
                    String [] rs_return_list = rs_return.split(",");
                    if (rs_return_list.length == 11){
                        InfoShopDateSin.setText("N/A");
                        ShopInfoAct.setText(rs_return_list[7]);
                        ShopInfoActFrom.setText(rs_return_list[5]);
                        ShopInfoActTo.setText(rs_return_list[6]);
                        ShopInfoCenterID.setText(rs_return_list[2]);
                        ShopInfoConntractID.setText("N/A");
                        ShopInfoFloor.setText(rs_return_list[3]);
                        ShopInfoLocation.setText(rs_return_list[4]);
                        ShopInfoServiceType.setText(rs_return_list[9]);
                        ShopInfoShopID.setText(rs_return_list[0]);
                        ShopInfoShopName.setText(rs_return_list[1]);
                        InfoShopBillingUnits.setText("N/A");
                        InfoShopCompanyID.setText("N/A");
                        InfoShopDateActFrom.setText("N/A");
                        InfoShopDateActTo.setText("N/A");
                    }else{
                        InfoShopBillingUnits.setText(rs_return_list[13]);
                        InfoShopCompanyID.setText(rs_return_list[14]);
                        InfoShopDateActFrom.setText(rs_return_list[11]);
                        InfoShopDateActTo.setText(rs_return_list[12]); 
                        InfoShopDateSin.setText(rs_return_list[10]);
                        ShopInfoAct.setText(rs_return_list[7]);
                        ShopInfoActFrom.setText(rs_return_list[5]);
                        ShopInfoActTo.setText(rs_return_list[6]);
                        ShopInfoCenterID.setText(rs_return_list[2]);
                        ShopInfoConntractID.setText(rs_return_list[8]);
                        ShopInfoFloor.setText(rs_return_list[3]);
                        ShopInfoLocation.setText(rs_return_list[4]);
                        ShopInfoServiceType.setText(rs_return_list[9]);
                        ShopInfoShopID.setText(rs_return_list[0]);
                        ShopInfoShopName.setText(rs_return_list[1]);
                        
                    }
                }while (rs.next());
                prepared.close();
            }
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
        return flag;
    }
    
    
    public InfoShopJF() {
        initComponents();
        WindowAtCenter(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        aaa = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        InfoShopDateSin = new javax.swing.JLabel();
        InfoShopDateActFrom = new javax.swing.JLabel();
        InfoShopDateActTo = new javax.swing.JLabel();
        InfoShopCompanyID = new javax.swing.JLabel();
        InfoShopBillingUnits = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        ShopInfoShopID = new javax.swing.JLabel();
        ShopInfoShopName = new javax.swing.JLabel();
        ShopInfoCenterID = new javax.swing.JLabel();
        ShopInfoFloor = new javax.swing.JLabel();
        ShopInfoLocation = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        ShopInfoActFrom = new javax.swing.JLabel();
        ShopInfoActTo = new javax.swing.JLabel();
        ShopInfoAct = new javax.swing.JLabel();
        ShopInfoConntractID = new javax.swing.JLabel();
        ShopInfoServiceType = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jSplitPane1.setBackground(new java.awt.Color(254, 174, 174));
        jSplitPane1.setDividerLocation(350);

        aaa.setBackground(new java.awt.Color(200, 255, 240));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel1.setText("Contract Infos");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Date Singed:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("Date Active From:");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("Date Active To:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText("Company ID:");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("Billing Units:");

        InfoShopDateSin.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        InfoShopDateSin.setText("jLabel7");

        InfoShopDateActFrom.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        InfoShopDateActFrom.setText("jLabel8");

        InfoShopDateActTo.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        InfoShopDateActTo.setText("jLabel9");

        InfoShopCompanyID.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        InfoShopCompanyID.setText("jLabel10");

        InfoShopBillingUnits.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        InfoShopBillingUnits.setText("jLabel11");

        javax.swing.GroupLayout aaaLayout = new javax.swing.GroupLayout(aaa);
        aaa.setLayout(aaaLayout);
        aaaLayout.setHorizontalGroup(
            aaaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aaaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(aaaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(aaaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(aaaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(InfoShopDateSin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(InfoShopDateActFrom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(InfoShopDateActTo, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InfoShopCompanyID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(InfoShopBillingUnits, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(aaaLayout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        aaaLayout.setVerticalGroup(
            aaaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aaaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(45, 45, 45)
                .addGroup(aaaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(InfoShopDateSin))
                .addGap(56, 56, 56)
                .addGroup(aaaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(InfoShopDateActFrom))
                .addGap(69, 69, 69)
                .addGroup(aaaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(InfoShopDateActTo))
                .addGap(61, 61, 61)
                .addGroup(aaaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(InfoShopCompanyID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(aaaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(InfoShopBillingUnits))
                .addGap(56, 56, 56))
        );

        jSplitPane1.setLeftComponent(aaa);

        jPanel2.setBackground(new java.awt.Color(200, 255, 240));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel7.setText("Shop Infos");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel9.setText("Shop ID:");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setText("Shop Name:");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setText("Shoopping Center ID:");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setText("Floor:");

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setText("Location:");

        ShopInfoShopID.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        ShopInfoShopID.setText("jLabel14");

        ShopInfoShopName.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        ShopInfoShopName.setText("jLabel15");

        ShopInfoCenterID.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        ShopInfoCenterID.setText("jLabel16");

        ShopInfoFloor.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        ShopInfoFloor.setText("jLabel17");

        ShopInfoLocation.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        ShopInfoLocation.setText("jLabel18");

        jLabel19.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel19.setText("Active From:");

        jLabel20.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel20.setText("Active To:");

        jLabel21.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel21.setText("Active:");

        jLabel22.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel22.setText("Contract ID:");

        jLabel23.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel23.setText("Service Type:");

        ShopInfoActFrom.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        ShopInfoActFrom.setText("jLabel24");

        ShopInfoActTo.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        ShopInfoActTo.setText("jLabel25");

        ShopInfoAct.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        ShopInfoAct.setText("jLabel26");

        ShopInfoConntractID.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        ShopInfoConntractID.setText("jLabel27");

        ShopInfoServiceType.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        ShopInfoServiceType.setText("jLabel28");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ShopInfoCenterID, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ShopInfoShopID, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ShopInfoFloor, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ShopInfoLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(96, 96, 96))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(ShopInfoShopName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(32, 32, 32)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ShopInfoActFrom, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                            .addComponent(ShopInfoActTo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ShopInfoAct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ShopInfoConntractID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ShopInfoServiceType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(290, 290, 290)
                        .addComponent(jLabel7)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(ShopInfoShopID)
                    .addComponent(jLabel19)
                    .addComponent(ShopInfoActFrom))
                .addGap(60, 60, 60)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(ShopInfoShopName)
                    .addComponent(jLabel20)
                    .addComponent(ShopInfoActTo))
                .addGap(63, 63, 63)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(ShopInfoCenterID)
                    .addComponent(jLabel21)
                    .addComponent(ShopInfoAct))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(ShopInfoFloor)
                    .addComponent(jLabel22)
                    .addComponent(ShopInfoConntractID))
                .addGap(65, 65, 65)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(ShopInfoLocation)
                    .addComponent(jLabel23)
                    .addComponent(ShopInfoServiceType))
                .addGap(55, 55, 55))
        );

        jSplitPane1.setRightComponent(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


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
            java.util.logging.Logger.getLogger(InfoShopJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InfoShopJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InfoShopJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InfoShopJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InfoShopJF().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel InfoShopBillingUnits;
    private javax.swing.JLabel InfoShopCompanyID;
    private javax.swing.JLabel InfoShopDateActFrom;
    private javax.swing.JLabel InfoShopDateActTo;
    private javax.swing.JLabel InfoShopDateSin;
    private javax.swing.JLabel ShopInfoAct;
    private javax.swing.JLabel ShopInfoActFrom;
    private javax.swing.JLabel ShopInfoActTo;
    private javax.swing.JLabel ShopInfoCenterID;
    private javax.swing.JLabel ShopInfoConntractID;
    private javax.swing.JLabel ShopInfoFloor;
    private javax.swing.JLabel ShopInfoLocation;
    private javax.swing.JLabel ShopInfoServiceType;
    private javax.swing.JLabel ShopInfoShopID;
    private javax.swing.JLabel ShopInfoShopName;
    private javax.swing.JPanel aaa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSplitPane jSplitPane1;
    // End of variables declaration//GEN-END:variables
}
