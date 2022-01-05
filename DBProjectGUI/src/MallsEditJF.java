
import java.sql.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Stefanos
 */
public class MallsEditJF extends javax.swing.JFrame {
            

    
    
    public Connection StartConn(){
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
    
    //Handler για να διαχειριζεται ολα τα buttons
    public void ACtionPerformed(java.awt.event.ActionEvent e){
            if (e.getSource() == ClearEditMall){
                clear();
            }else if (e.getSource() == SubmitEditMall){
                submit();
                clear();
            }
    }
    
    public void submit(){
        Connection conn=null;
        PreparedStatement prepared = null;
        String name=null;
        String address=null;
        int id=-1;
        boolean flag=false;
        
        try{
         id = Integer.parseInt(TitleLabelEditMalls.getText().trim().substring(27, TitleLabelEditMalls.getText().length()).trim());
        }catch (Exception e){
            System.out.println("Not a integer");
        }
        name = MallNameEditMallsTF.getText().trim();
        try{
            address = AddressEditMallTF.getText().trim() + " " + Integer.parseInt(AddressNumEditMallTF.getText().trim()) + ", " + Integer.parseInt(TKEditMallTF.getText().trim()) + ", " + TownEditMallTF.getText();
        }catch (Exception e){
            System.out.println("Not a integer");
            javax.swing.JOptionPane.showMessageDialog(null, "Give an integer!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
            flag=true;
        }
        if ((name == null) || (address == null)){
            flag=true;
        }else if ((MallNameEditMallsTF.getText().isBlank()) || (AddressEditMallTF.getText().isBlank()) || (AddressNumEditMallTF.getText().isBlank()) || (TKEditMallTF.getText().isBlank()) || (TownEditMallTF.getText().isBlank())){
            flag=true;
        }
        
        if (flag){
            javax.swing.JOptionPane.showMessageDialog(null, "Invalid values!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
        }else{
            try{
                conn = StartConn();
                prepared = conn.prepareStatement("SELECT edit_mall(?,?,?)");
                prepared.setInt(1,id);
                prepared.setString(2, name);
                prepared.setString(3, address);
                prepared.executeQuery();
                prepared.close();
                javax.swing.JOptionPane.showMessageDialog(null, "The values have been updated!","INFO",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }catch (SQLException ex){
                System.out.println("---SQL Exception---");
                System.out.println("Message: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("ErrorCode: " + ex.getErrorCode()); 
                javax.swing.JOptionPane.showMessageDialog(null, "Something went wrong!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
            }finally{
                try{
                    conn.close();
                }catch (SQLException ex){
                    System.out.println("---SQL Exception---");
                    System.out.println("Message: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("ErrorCode: " + ex.getErrorCode());  
                }
            }
        }    
    
    
    }
    
    
    //Εκχωρει μεσα στα componets της ειδη εχκχρημενες τιμες του Mall
    public void inisialize(String value){
        String [] selected_list = value.split(":");
        String addressnum="";
        String tk=null;
        TitleLabelEditMalls.setText("This is the Mall with code " + selected_list[1].trim().substring(0,selected_list[1].trim().length()-6));
        MallNameEditMallsTF.setText(selected_list[2].trim().substring(0,selected_list[2].trim().length()-10));
        Pattern pattern = Pattern.compile("([1-9][0-9]{0,2})");
        Matcher matcher = pattern.matcher(selected_list[3].trim());
        if (matcher.find()){
           addressnum = matcher.group(1);
        }
        AddressNumEditMallTF.setText(addressnum);
        AddressEditMallTF.setText(selected_list[3].trim().substring(0,selected_list[3].trim().length()-addressnum.length()-4));
        Pattern pattern2 = Pattern.compile("([1-9][0-9]{0,4})");
        Matcher matcher2 = pattern2.matcher(selected_list[4].trim());
        if (matcher2.find()){
           tk = matcher2.group(1);
        }
        TKEditMallTF.setText(tk);
        TownEditMallTF.setText(selected_list[5].trim());
//        
    }
    
    //Εκαθαριση των TextFields 
    public void clear(){
        MallNameEditMallsTF.setText("");
        AddressEditMallTF.setText("");
        AddressNumEditMallTF.setText("");
    }
    
    
    public void WindowAtCenter(JFrame objFrame){
        Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int iCoordX = (objDimension.width - objFrame.getWidth()) / 2;
        int iCoordY = (objDimension.height - objFrame.getHeight()) / 2;
        objFrame.setLocation(iCoordX, iCoordY); 
    }
    
    
    
    
    public MallsEditJF() {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        TitleLabelEditMalls = new javax.swing.JLabel();
        MallNameEditMall = new javax.swing.JLabel();
        AddressEditMall = new javax.swing.JLabel();
        MallNameEditMallsTF = new javax.swing.JTextField();
        AddressEditMallTF = new javax.swing.JTextField();
        SubmitEditMall = new javax.swing.JButton();
        ClearEditMall = new javax.swing.JButton();
        AddressNumEditMall = new javax.swing.JLabel();
        AddressNumEditMallTF = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TKEditMallTF = new javax.swing.JTextField();
        TownEditMallTF = new javax.swing.JTextField();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(200, 255, 240));

        TitleLabelEditMalls.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N

        MallNameEditMall.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        MallNameEditMall.setText("Mall Name:");

        AddressEditMall.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        AddressEditMall.setText("Address:");

        MallNameEditMallsTF.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        AddressEditMallTF.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        SubmitEditMall.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        SubmitEditMall.setText("Submit");
        SubmitEditMall.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        SubmitEditMall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        ClearEditMall.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ClearEditMall.setText("Clear");
        ClearEditMall.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ClearEditMall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        AddressNumEditMall.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        AddressNumEditMall.setText("Address Number:");

        AddressNumEditMallTF.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("TK: ");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Town: ");

        TKEditMallTF.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        TownEditMallTF.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 234, Short.MAX_VALUE)
                .addComponent(TitleLabelEditMalls, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(142, 142, 142))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ClearEditMall, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(SubmitEditMall, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AddressNumEditMall)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TKEditMallTF)
                            .addComponent(TownEditMallTF, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                            .addComponent(AddressNumEditMallTF)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(AddressEditMall, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                                .addGap(22, 22, 22))
                            .addComponent(MallNameEditMall, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(AddressEditMallTF, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                            .addComponent(MallNameEditMallsTF))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TitleLabelEditMalls, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MallNameEditMall)
                    .addComponent(MallNameEditMallsTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddressEditMallTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddressEditMall))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddressNumEditMall)
                    .addComponent(AddressNumEditMallTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(TKEditMallTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(TownEditMallTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SubmitEditMall)
                    .addComponent(ClearEditMall))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(MallsEditJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MallsEditJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MallsEditJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MallsEditJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MallsEditJF().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AddressEditMall;
    private javax.swing.JTextField AddressEditMallTF;
    private javax.swing.JLabel AddressNumEditMall;
    private javax.swing.JTextField AddressNumEditMallTF;
    private javax.swing.JButton ClearEditMall;
    private javax.swing.JLabel MallNameEditMall;
    private javax.swing.JTextField MallNameEditMallsTF;
    private javax.swing.JButton SubmitEditMall;
    private javax.swing.JTextField TKEditMallTF;
    private javax.swing.JLabel TitleLabelEditMalls;
    private javax.swing.JTextField TownEditMallTF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
