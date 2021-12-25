
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
        int addressNum;
        String mallName = newMallNameTF.getText();
        String mallAddress = (newMallTownTF.getText() + " " + newMallAdressTF.getText() + " " );
        //Προετοιμασια του address για να περασει στην βαση
        try{
            addressNum = Integer.parseInt(newMallAdressNumTF.getText());
            mallAddress = mallAddress.concat(Integer.toString(addressNum));
        }catch (Exception e){
            System.out.println("Not interger value");
            javax.swing.JOptionPane.showMessageDialog(null, "Please enter Address Number!");
        }
        //Δημιουργια συνδεσης
        Connection conn = mf.startConn();
        // Περασμα στην βαση
        //ΧΡΕΙΑΖΕΤΑΙ ΣΥΜΠΛΗΡΩΣΗ


    }

    //Μεθοδος για την εκαθαριση των TextFields στο MallsInsertJF(JFrame)  
    public void clear(){
        newMallAdressNumTF.setText(""); 
        newMallAdressTF.setText("");      
        newMallNameTF.setText("");   
        newMallTownTF.setText("");
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
        newMallTownTF = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(200, 255, 240));

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
        newMallSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        newMallClearButton.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        newMallClearButton.setText("Clear");
        newMallClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText("Πόλη");

        newMallTownTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

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
                            .addComponent(newMallTownTF))))
                .addContainerGap(225, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(newMallClearButton)
                .addGap(66, 66, 66)
                .addComponent(newMallSubmitButton)
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
                    .addComponent(newMallTownTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
    private javax.swing.JTextField newMallNameTF;
    private javax.swing.JButton newMallSubmitButton;
    private javax.swing.JTextField newMallTownTF;
    // End of variables declaration//GEN-END:variables
}
