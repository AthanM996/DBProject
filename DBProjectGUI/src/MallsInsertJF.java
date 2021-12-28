
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import java.sql.*;

public class MallsInsertJF extends javax.swing.JFrame {



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
        
        int addressNum;
        //Ελεγχος για αμα εχουν συμπληρωθει ολα τα πεδια 
        if ((newMallAdressNumTF.getText().isBlank()) || (newMallAdressTF.getText().isBlank()) || (newMallNameTF.getText().isBlank()) ||  (newMallCodeTF.getText().isBlank())){
           javax.swing.JOptionPane.showMessageDialog(null, "Please enter all the fields!");
        //Προετοιμασια του address για να περασει στην βαση
        }else{
            //Αρχηκοποιηση Μεταβλητων ωστε να περασουν στην βαση
            mallName = newMallNameTF.getText();
            mallAddress =  newMallAdressTF.getText() + " " ;
            try{
                mallCode = Integer.parseInt(newMallCodeTF.getText());
                if (mallCode<0){
                    javax.swing.JOptionPane.showMessageDialog(null, "Please enter a number greater than 0");
                    error=true;
                }
            }catch (Exception e){
                System.out.println("Not interger value");
                javax.swing.JOptionPane.showMessageDialog(null, "Please enter a number!"); 
                error = true;
            }
            try{
                addressNum = Integer.parseInt(newMallAdressNumTF.getText());
                if (addressNum<0){
                    javax.swing.JOptionPane.showMessageDialog(null, "Please enter a number greater than 0");
                    error=true;
                }else{
                   // mallAddress = mallAddress.concat(Integer.toString(addressNum));
                   mallAddress = mallAddress + Integer.toString(addressNum);
                }
            }catch (Exception e){
                System.out.println("Not interger value");
                javax.swing.JOptionPane.showMessageDialog(null, "Please enter Address Number!");
                error = true;
            }
            
            
            //Ελεγχος για αμα πηγε κατι στραβα
            if (error){
                System.out.println("Error");
                javax.swing.JOptionPane.showMessageDialog(null, "Error at insert values the submit have failed!");
            }else{
                //Δημιουργια συνδεσης
                conn = mf.startConn();
                try{
                    prepared = conn.prepareStatement("SELECT Submit_New_Mall(?,?,?)");
                    prepared.setInt(1,mallCode);
                    prepared.setString(2, mallName);
                    prepared.setString(3, mallAddress);
                    prepared.executeQuery();
                    javax.swing.JOptionPane.showMessageDialog(null, "Values has insert Correct");
                    prepared.close();
                }catch (SQLException ex){
                    System.out.println("SQL Exception");
                    switch (ex.getSQLState()){
                        case "23505":
                            javax.swing.JOptionPane.showMessageDialog(null, "This code is already exists, give another code");
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
                            .addComponent(jLabel5))
                        .addGap(59, 59, 59)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(newMallAdressTF, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                            .addComponent(newMallAdressNumTF)
                            .addComponent(newMallNameTF)
                            .addComponent(newMallCodeTF))))
                .addContainerGap(225, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(newMallClearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(newMallSubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(newMallCodeTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newMallAdressTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(newMallAdressNumTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField newMallAdressNumTF;
    private javax.swing.JTextField newMallAdressTF;
    private javax.swing.JButton newMallClearButton;
    private javax.swing.JTextField newMallCodeTF;
    private javax.swing.JTextField newMallNameTF;
    private javax.swing.JButton newMallSubmitButton;
    // End of variables declaration//GEN-END:variables
}
