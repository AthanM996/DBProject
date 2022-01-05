
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import java.sql.*;

public class MallsInsertJF extends javax.swing.JFrame {

    
    
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
        if (e.getSource() == newMallClearButton){
            clear();
        }else if  (e.getSource() == newMallSubmitButton){
            submit();
            clear();
        }    
    }

    //Μεθοδο για την Εισαγωγή των στοιχειων στην βαση
    public void submit(){
        MainFrame mf = new MainFrame();
        Connection conn = null;
        PreparedStatement prepared;
        ResultSet rs;
        String mallName,mallAddress = null;
        int mallCode = 0;
        boolean error = false;
        int tk=-1;
        
        int addressNum;
        //Ελεγχος για αμα εχουν συμπληρωθει ολα τα πεδια 
        if ((newMallAdressNumTF.getText().isBlank()) || (newMallAdressTF.getText().isBlank()) || (newMallNameTF.getText().isBlank()) ||  (newMallCodeTF.getText().isBlank()) || (newMallTKTF.getText().isBlank()) || (newMallTownTF.getText().isBlank())){
           javax.swing.JOptionPane.showMessageDialog(null, "Please fill all the fields!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
        //Προετοιμασια του address για να περασει στην βαση
        }else{
            //Αρχηκοποιηση Μεταβλητων ωστε να περασουν στην βαση
            mallName = newMallNameTF.getText();
            mallAddress =  newMallAdressTF.getText() + " " ;
            try{
                mallCode = Integer.parseInt(newMallCodeTF.getText());
                if (mallCode<0){
                    javax.swing.JOptionPane.showMessageDialog(null, "Please enter a number greater than 0","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
                    error=true;
                }
            }catch (Exception e){
                System.out.println("Not interger value");
                javax.swing.JOptionPane.showMessageDialog(null, "Please enter a number!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE); 
                error = true;
            }
            try{
                addressNum = Integer.parseInt(newMallAdressNumTF.getText());
                if (addressNum<0){
                    javax.swing.JOptionPane.showMessageDialog(null, "Please enter a number greater than 0","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
                    error=true;
                }else{
                   // mallAddress = mallAddress.concat(Integer.toString(addressNum));
                   mallAddress = mallAddress + Integer.toString(addressNum);
                }
            }catch (Exception e){
                System.out.println("Not interger value");
                javax.swing.JOptionPane.showMessageDialog(null, "Please enter Address Number!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
                error = true;
            }
            try{
                 tk = Integer.parseInt(newMallTKTF.getText().trim());
                 if (newMallTKTF.getText().trim().length()!=5){
                     javax.swing.JOptionPane.showMessageDialog(null, "Please enter a postal code with the correct format!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
                     error=true;
                 }else{
                     mallAddress = mallAddress + ", " + tk + ", " + newMallTownTF.getText().trim();
                 }
            }catch (Exception e){
                javax.swing.JOptionPane.showMessageDialog(null, "Please enter a number!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
                error = true;
            }
            
            
            
            //Ελεγχος για αμα πηγε κατι στραβα
            if (error){
                System.out.println("Error");
                javax.swing.JOptionPane.showMessageDialog(null, "Error at insert values, the submit has failed!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
            }else{
                //Δημιουργια συνδεσης
                try{
                    conn = StartConn();
                    prepared = conn.prepareStatement("SELECT Submit_New_Mall(?,?,?)");
                    prepared.setInt(1,mallCode);
                    prepared.setString(2, mallName);
                    prepared.setString(3, mallAddress);
                    prepared.executeQuery();
                    javax.swing.JOptionPane.showMessageDialog(null, "Values have been inserted correctly.","INFO",javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    prepared.close();
                }catch (SQLException ex){
                    System.out.println("SQL Exception");
                    switch (ex.getSQLState()){
                        case "23505":
                            javax.swing.JOptionPane.showMessageDialog(null, "This code already exists, give another code","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
                            clear();
//                            MallsInsertJF MallsJF = new MallsInsertJF();
//                            MallsJF.setVisible(true);
//                            kill(this);
                            break;
                    }
                        System.out.println("Message: " + ex.getMessage());
                        System.out.println("SQLState: " + ex.getSQLState());
                        System.out.println("ErrorCode: " + ex.getErrorCode());
                    
                }finally{
                    try{
                        conn.close();
                    }catch (SQLException ex){
                        System.out.println(ex);
                    }
                }
            }
           
            
        }
    }

    //Μεθοδος για την εκαθαριση των TextFields στο MallsInsertJF(JFrame)  
    public void clear(){
        newMallAdressNumTF.setText(""); 
        newMallAdressTF.setText("");      
        newMallNameTF.setText("");   
        newMallCodeTF.setText("");
        newMallTownTF.setText("");
        newMallTKTF.setText("");
    }

        
    
    
    public void kill(JFrame frame){
        frame.dispose();
    }

    
    public void WindowAtCenter(JFrame objFrame){
        Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int iCoordX = (objDimension.width - objFrame.getWidth()) / 2;
        int iCoordY = (objDimension.height - objFrame.getHeight()) / 2;
        objFrame.setLocation(iCoordX, iCoordY); 
    }
    
    
    public MallsInsertJF() {
        initComponents();
        WindowAtCenter(this);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        newMallNameTF = new javax.swing.JTextField();
        newMallAdressTF = new javax.swing.JTextField();
        newMallAdressNumTF = new javax.swing.JTextField();
        newMallSubmitButton = new javax.swing.JButton();
        newMallClearButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        newMallCodeTF = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        newMallTKTF = new javax.swing.JTextField();
        newMallTownTF = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(200, 255, 240));
        jPanel1.setMinimumSize(new java.awt.Dimension(100, 100));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel1.setText("Εισαγωγή Εμπορικού Κέντρου");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Όνομα");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Διεύθυνση");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Αριθμος");

        newMallNameTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        newMallNameTF.setToolTipText("");

        newMallAdressTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        newMallAdressNumTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        newMallSubmitButton.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        newMallSubmitButton.setText("Submit");
        newMallSubmitButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        newMallSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        newMallClearButton.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        newMallClearButton.setText("Clear");
        newMallClearButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        newMallClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText("Κωδικός");

        newMallCodeTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("TK: ");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setText("Πολη: ");

        newMallTKTF.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        newMallTownTF.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(59, 59, 59)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(newMallAdressTF, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                            .addComponent(newMallAdressNumTF)
                            .addComponent(newMallNameTF)
                            .addComponent(newMallCodeTF)
                            .addComponent(newMallTKTF)
                            .addComponent(newMallTownTF))))
                .addContainerGap(225, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(newMallClearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(newMallSubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(newMallNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(newMallCodeTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(newMallAdressTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(newMallAdressNumTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(newMallTKTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(newMallTownTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newMallClearButton)
                    .addComponent(newMallSubmitButton))
                .addGap(32, 32, 32))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
   
    public static void main(String args[]) {
  
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MallsInsertJF().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField newMallAdressNumTF;
    private javax.swing.JTextField newMallAdressTF;
    private javax.swing.JButton newMallClearButton;
    private javax.swing.JTextField newMallCodeTF;
    private javax.swing.JTextField newMallNameTF;
    private javax.swing.JButton newMallSubmitButton;
    private javax.swing.JTextField newMallTKTF;
    private javax.swing.JTextField newMallTownTF;
    // End of variables declaration//GEN-END:variables
}
