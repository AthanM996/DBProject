import java.sql.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;

public class InserInvoice extends javax.swing.JFrame {

  

    private Connection StartConn(){
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
    
    
    //Για την διαχειρηση των Events 
    private void ACtionPerformed(java.awt.event.ActionEvent e){
           if (e.getSource() ==  InsertVoiceClearButton){
              clear();
           }else if(e.getSource() == InsertVoiceSubmitButton){
               submit();
               clear();
           }

    }

    //Καθαρισμα των componets 
    private void clear(){
        InsertVoiceContractCodeCB.setSelectedIndex(0);      
        InsertVoiceCompanyCodeCB.setSelectedIndex(0); 
        InsertVoiceCodeTF.setText("");
        InsertVoiceAmountTF.setText("");
        InsertVoiceFeeTF.setText("");
        InsertVoiceTaxTF.setText("");
        InsertVoiceTotalAmountTF.setText("");
    }

    public void inisialize(){

        Connection conn = null;
        Statement contract_stmt = null;
        Statement company_stmt = null;
        ResultSet contract_rs, company_rs = null;

        try{
            conn = StartConn();
            contract_stmt = conn.createStatement();
            company_stmt = conn.createStatement();
            contract_rs = contract_stmt.executeQuery("SELECT get_aggreement_id()");
            company_rs = company_stmt.executeQuery("SELECT get_id_company()");

            if (contract_rs.next() == false){
                javax.swing.JOptionPane.showMessageDialog(null, "Insert Contract First!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
                InsertVoiceSubmitButton.setEnabled(false);
                InsertVoiceClearButton.setEnabled(false);

            }else{
                do{
                    String value = contract_rs.getString(1);
                    InsertVoiceContractCodeCB.addItem(value);
                }while (contract_rs.next());
                InsertVoiceContractCodeCB.setSelectedIndex(0);
            }

            if (company_rs.next() == false){
                javax.swing.JOptionPane.showMessageDialog(null, "Insert Contract First!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
                InsertVoiceSubmitButton.setEnabled(false);
                InsertVoiceClearButton.setEnabled(false);

            }else{
                do{
                    String value = company_rs.getString(1);
                    System.out.println(value);
                    InsertVoiceCompanyCodeCB.addItem(value);
                }while (company_rs.next());
                InsertVoiceCompanyCodeCB.setSelectedIndex(0);
            }
            contract_stmt.close();
            company_stmt.close();
        }catch (SQLException ex){
            //Γιθα την περιπτωση που υπαρχεδι ειδη τιμη
            switch (ex.getSQLState()){
                case "23505":
                    javax.swing.JOptionPane.showMessageDialog(null, "This code is already exists, give another code","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
                    clear();
                    break;
            }
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

        //Για την επιλογη του μηνα
    public String month(String value){
        String ret = "aaa";

        switch (value){
            case "Jan":
                ret = "01";
                break;
            case "Feb":
                ret = "02";
                break;
            case "Mar":
                ret = "03";
                break;
            case "Apr":
                ret = "04";
                break;
            case "May":
                ret = "05";
                break;
            case "Jun":
                ret = "06";
                break;
            case "Jul":
                ret = "07";
                break;
            case "Aug":
                ret = "08";
                break;
            case "Sep":
                ret = "09";
                break;
            case "Oct":
                ret = "10";
                break;
            case "Nov":
                ret = "11";
                break;
            case "Dec":
                ret = "12";
                break;
        }
        return ret;
        
    }

    private void submit(){

        
        Connection conn = null;
        PreparedStatement prepared = null;
        int company_id;
        int contract_id;
        int invoice_id = -1;
        double amount = -0.1;
        double fee =  -0.1;
        double tax = -0.1;
        double total = -0.1;
        String date_issued = null;
        String time_created = null;
        String date_paid = null;
        boolean error = false;

        if ((InsertVoiceAmountTF.getText().isBlank()) || (InsertVoiceCodeTF.getText().isBlank()) || (InsertVoiceFeeTF.getText().isBlank()) || (InsertVoiceTaxTF.getText().isBlank()) || (InsertVoiceTotalAmountTF.getText().isBlank()) || (InsertVoiceDateCreatedSpinner.getValue().toString().isBlank()) || (InsertVoiceDateIssuedSpinner.getValue().toString().isBlank()) || (InsertVoiceDatePaidSpinner.getValue().toString().isBlank())){
            error = true;
        } 
        company_id = Integer.parseInt((String)InsertVoiceCompanyCodeCB.getSelectedItem());
        contract_id = Integer.parseInt((String)InsertVoiceContractCodeCB.getSelectedItem());
        try{
            invoice_id = Integer.parseInt(InsertVoiceCodeTF.getText().trim());
        }catch (Exception e){
            javax.swing.JOptionPane.showMessageDialog(null, "Please enter a number at Code!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
                error =true;
        }
        
        try{
            amount = Double.parseDouble(InsertVoiceAmountTF.getText().trim());
        }catch (Exception e){
            javax.swing.JOptionPane.showMessageDialog(null, "Please enter a number at Amount!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
            error =true;
        }

        try{
            fee = Double.parseDouble(InsertVoiceFeeTF.getText().trim());
        }catch (Exception e){
            javax.swing.JOptionPane.showMessageDialog(null, "Please enter a number at Fee!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
            error =true;
        }
        
        try{
            tax = Double.parseDouble(InsertVoiceTaxTF.getText().trim());
        }catch (Exception e){
            javax.swing.JOptionPane.showMessageDialog(null, "Please enter a number at Tax!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
            error =true;
        }

        try{
            total = Double.parseDouble(InsertVoiceTotalAmountTF.getText().trim());
        }catch (Exception e){
            javax.swing.JOptionPane.showMessageDialog(null, "Please enter a number at Total Amount!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
            error =true;
        }

        String [] date_signed_frm = InsertVoiceDateCreatedSpinner.getValue().toString().trim().split(" ");
        time_created= String.format("%s/%s/%s",date_signed_frm[2],month(date_signed_frm[1]),date_signed_frm[5]);

        String [] date_act_from_frm = InsertVoiceDateIssuedSpinner.getValue().toString().trim().split(" "); 
        date_issued = String.format("%s/%s/%s",date_act_from_frm[2],month(date_act_from_frm[1]),date_act_from_frm[5]);

        String [] date_act_to_frm = InsertVoiceDatePaidSpinner.getValue().toString().trim().split(" ");
        date_paid = String.format("%s/%s/%s",date_act_to_frm[2],month(date_act_to_frm[1]),date_act_to_frm[5]);

        if ( (time_created.length() != 10) || (date_issued.length() != 10) || (date_paid.length() != 10)){
            error =true;
            javax.swing.JOptionPane.showMessageDialog(null, "Enter the date values with this pattern: dd/MM/yyyy","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
        }
        
        if (error){
            javax.swing.JOptionPane.showMessageDialog(null, "Error at insert values the submit have failed!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
        }else{
            try{
                conn = StartConn();
                //Αποστολη των τιμω για το Invoice 
                prepared = conn.prepareStatement("SELECT insert_bill(?,?,?,?,?,?,?,?,?,?)");
                prepared.setInt(1,invoice_id);
                prepared.setInt(2,contract_id);
                prepared.setInt(3,company_id);
                prepared.setDouble(4, amount);
                prepared.setDouble(5,fee);
                prepared.setDouble(6,tax);
                prepared.setDouble(7,total);
                prepared.setString(8,time_created);
                prepared.setString(9,date_issued);
                prepared.setString(10,date_paid);
                prepared.executeQuery();
                prepared.close();
                javax.swing.JOptionPane.showMessageDialog(null, "The Insert Complete!","INFO",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }catch (SQLException ex){
                //Γιθα την περιπτωση που υπαρχεδι ειδη τιμη
                switch (ex.getSQLState()){
                    case "23505":
                        javax.swing.JOptionPane.showMessageDialog(null, "This code is already exists, give another code","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
                        clear();
                        break;
                }
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
    }

    //Μεθοδος για την τοποθεσια του frame στο κεντρο
    private void WindowAtCenter(JFrame objFrame){
        Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int iCoordX = (objDimension.width - objFrame.getWidth()) / 2;
        int iCoordY = (objDimension.height - objFrame.getHeight()) / 2;
        objFrame.setLocation(iCoordX, iCoordY); 
    }




    public InserInvoice() {
        initComponents();
        WindowAtCenter(this);
        inisialize();
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
        InsertVoiceContractCodeCB = new javax.swing.JComboBox<>();
        InsertVoiceCompanyCodeCB = new javax.swing.JComboBox<>();
        InsertVoiceCodeTF = new javax.swing.JTextField();
        InsertVoiceAmountTF = new javax.swing.JTextField();
        InsertVoiceFeeTF = new javax.swing.JTextField();
        InsertVoiceTaxTF = new javax.swing.JTextField();
        InsertVoiceTotalAmountTF = new javax.swing.JTextField();
    
        InsertVoiceClearButton = new javax.swing.JButton();
        InsertVoiceSubmitButton = new javax.swing.JButton();


        InsertVoiceDateIssuedSpinner = new javax.swing.JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MONTH));
        InsertVoiceDateIssuedSpinner.setEditor(new javax.swing.JSpinner.DateEditor(InsertVoiceDateIssuedSpinner, "dd/MM/yyyy"));
        
        InsertVoiceDateCreatedSpinner = new javax.swing.JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MONTH));
        InsertVoiceDateCreatedSpinner.setEditor(new javax.swing.JSpinner.DateEditor(InsertVoiceDateCreatedSpinner, "dd/MM/yyyy"));

        InsertVoiceDatePaidSpinner = new javax.swing.JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MONTH));
        InsertVoiceDatePaidSpinner.setEditor(new javax.swing.JSpinner.DateEditor(InsertVoiceDatePaidSpinner, "dd/MM/yyyy"));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(200, 255, 240));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Insert Invoice");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel2.setText("Code:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel3.setText("Company Code:");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel4.setText("Contract Code:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel5.setText("Amount:");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel6.setText("Fee:");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel7.setText("Tax:");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel8.setText("Total Amount:");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel9.setText("Date Issued:");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel10.setText("Date Created:");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel11.setText("Date Paid:");

        InsertVoiceContractCodeCB.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        InsertVoiceCompanyCodeCB.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        InsertVoiceCodeTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        InsertVoiceAmountTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        InsertVoiceAmountTF.setToolTipText("");

        InsertVoiceFeeTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        InsertVoiceFeeTF.setToolTipText("");

        InsertVoiceTaxTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        InsertVoiceTotalAmountTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        InsertVoiceDateIssuedSpinner.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        InsertVoiceDateCreatedSpinner.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        InsertVoiceDatePaidSpinner.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        InsertVoiceClearButton.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        InsertVoiceClearButton.setText("Clear");
        InsertVoiceClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        InsertVoiceSubmitButton.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        InsertVoiceSubmitButton.setText("Submit");
        InsertVoiceSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(51, 51, 51)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(InsertVoiceAmountTF, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(InsertVoiceFeeTF, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 7, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(26, 26, 26))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(InsertVoiceContractCodeCB, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(InsertVoiceCompanyCodeCB, 0, 135, Short.MAX_VALUE)
                                .addComponent(InsertVoiceCodeTF)))))
                .addGap(113, 113, 113)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(InsertVoiceClearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(InsertVoiceSubmitButton))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(InsertVoiceDateCreatedSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                        .addComponent(InsertVoiceDateIssuedSpinner)
                        .addComponent(InsertVoiceTotalAmountTF)
                        .addComponent(InsertVoiceTaxTF)
                        .addComponent(InsertVoiceDatePaidSpinner)))
                .addContainerGap(61, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(298, 298, 298)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7)
                    .addComponent(InsertVoiceCodeTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InsertVoiceTaxTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(InsertVoiceCompanyCodeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(InsertVoiceTotalAmountTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(InsertVoiceContractCodeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InsertVoiceDateIssuedSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel4))
                .addGap(100, 100, 100)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(InsertVoiceAmountTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InsertVoiceDateCreatedSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(103, 103, 103)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel11)
                    .addComponent(InsertVoiceFeeTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InsertVoiceDatePaidSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(InsertVoiceClearButton)
                    .addComponent(InsertVoiceSubmitButton))
                .addGap(21, 21, 21))
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
            java.util.logging.Logger.getLogger(InserInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InserInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InserInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InserInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InserInvoice().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField InsertVoiceAmountTF;
    private javax.swing.JButton InsertVoiceClearButton;
    private javax.swing.JTextField InsertVoiceCodeTF;
    private javax.swing.JComboBox<String> InsertVoiceCompanyCodeCB;
    private javax.swing.JComboBox<String> InsertVoiceContractCodeCB;
    private javax.swing.JSpinner InsertVoiceDateCreatedSpinner;
    private javax.swing.JSpinner InsertVoiceDateIssuedSpinner;
    private javax.swing.JSpinner InsertVoiceDatePaidSpinner;
    private javax.swing.JTextField InsertVoiceFeeTF;
    private javax.swing.JButton InsertVoiceSubmitButton;
    private javax.swing.JTextField InsertVoiceTaxTF;
    private javax.swing.JTextField InsertVoiceTotalAmountTF;
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
