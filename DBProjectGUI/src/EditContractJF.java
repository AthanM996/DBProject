


import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.SpinnerDateModel;
import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

import java.sql.*;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerDateModel;



public class EditContractJF extends javax.swing.JFrame {


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
           if (e.getSource() == EditConClearButton){
              clear();
           }else if(e.getSource() == EditConSubmitButton){
               submit();
               clear();
           }

    }
    
    //Καθαρισμα των componets 
    private void clear(){
        
        EditConBillingUnitsCB.setSelectedIndex(0);      
        EditConCompanyIDCB.setSelectedIndex(0); 
        EditConInvoiceAmountTF.setText("");
        EditConInvoiceCodeLabel.setText("");
        EditConInvoiceFeeTF.setText("");
        EditConInvoiceTaxTF.setText("");
        EditConInvoiceTotalAmountTF.setText("");  
        EditConDateSin.setText("dd/mm/yyyy");
        EditConDateActFrom.setText("dd/mm/yyyy");
        EditConDateActTo.setText("dd/mm/yyyy");
        EditConDateIssued.setText("dd/mm/yyyy");
        EditConTimeCreated.setText("dd/mm/yyyy");
        EditConDatePaid.setText("dd/mm/yyyy");
    }

    //Αρχικοποιηση οσως componets χρειαζεται
    public boolean  inisialize(String str){
        Connection conn = null;
        Statement stmt_units = null;
        Statement stmt_companyID = null;
        PreparedStatement prepared = null;
        ResultSet rs_pre = null;
        ResultSet rs_units = null;
        ResultSet rs_companyID = null;
        boolean tr = true; 

        Pattern pattern;
        Matcher matcher;

        try{
            conn = StartConn();
            stmt_companyID = conn.createStatement();
            stmt_units = conn.createStatement();
            rs_companyID = stmt_companyID.executeQuery("SELECT get_id_company()");
            rs_units = stmt_units.executeQuery("SELECT get_aggreement_check_units()");
            if (rs_companyID.next() == false){
                javax.swing.JOptionPane.showMessageDialog(null, "Ther is no company, insert first company! ","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
                EditConClearButton.setEnabled(false);
                EditConSubmitButton.setEnabled(false);
            }else{
                do{
                    EditConCompanyIDCB.addItem(rs_companyID.getString(1));
                }while (rs_companyID.next());
            }
            while (rs_units.next()){
                pattern = Pattern.compile("\\[(.*)\\]");
                matcher = pattern.matcher(rs_units.getString(1));
                if (matcher.find()){
                    String [] split_values_at_coma = matcher.group(1).split(",");
                    for (int i=0 ; i < split_values_at_coma.length ; i++){
                        String [] split_value_at_anokato = split_values_at_coma[i].split("::");
                        String value = split_value_at_anokato[0].trim();
                        EditConBillingUnitsCB.addItem(value.substring(1, value.length()-1));                     
                    }
                 }
            }
            //Ευρεση του id που εχει επιλεχτει
            String [] list_values= str.split(":");
            int id = -1;
            try{
                id = Integer.parseInt(list_values[1].substring(1, list_values[1].length()-12));
            }catch (Exception e){
                System.out.println("The parse has failed!");
            }
            
            
            //Γεμισμα ολων τον componets μες τις τιμες που εχει η επιλεγμενη εγγραφη
            prepared = conn.prepareStatement("SELECT get_con_voice_edit(?)");
            prepared.setInt(1,id);
            rs_pre = prepared.executeQuery();
            if (rs_pre.next() == false){
                javax.swing.JOptionPane.showMessageDialog(null, "Ther is no invoice for this contract! Insert INVOICE First!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
                tr = false;
            }else{
                do {
                    String [] split_values = rs_pre.getString(1).split(",");
                    //Αρχικοποιηση των τιμων για το Contract
                    EditConCodeLabel.setText(Integer.toString(id));
                    EditConDateSin.setText(split_values[0]);
                    EditConDateActFrom.setText(split_values[1]);
                    EditConDateActTo.setText(split_values[2]);
                    if (((DefaultComboBoxModel)EditConCompanyIDCB.getModel()).getIndexOf(split_values[3]) >= 0){
                        EditConCompanyIDCB.setSelectedItem(split_values[3].trim());
                    }
                    if (((DefaultComboBoxModel)EditConBillingUnitsCB.getModel()).getIndexOf(split_values[4]) >= 0){
                        EditConBillingUnitsCB.setSelectedItem(split_values[4]);
                    }

                    //Αρχικοποιηση τιμως για το invoice 
                    EditConInvoiceCodeLabel.setText(split_values[5]);                 
                    EditConInvoiceAmountTF.setText(split_values[6]);
                    EditConInvoiceFeeTF.setText(split_values[7]);
                    EditConInvoiceTaxTF.setText(split_values[8]);
                    EditConInvoiceTotalAmountTF.setText(split_values[9]);
                    EditConDateIssued.setText(split_values[10]);
                    EditConTimeCreated.setText(split_values[11]);
                    EditConDatePaid.setText(split_values[12]);


                }while (rs_pre.next());
            }





            stmt_units.close();
            stmt_companyID.close();
        }catch (SQLException ex){
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
        return tr;
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
    
    //Αποστολη
    private void submit(){

        Connection conn = null;
        PreparedStatement prepared = null;
        PreparedStatement prepared_invoice = null;
        int id =-1;

        String date_signed;
        String date_act_from;
        String date_act_to;
        int company_id;
        String billing_units;
        boolean error = false;

        int invoice_id = -1;
        double amount = -0.1;
        double fee =  -0.1;
        double tax = -0.1;
        double total = -0.1;
        String date_issued = null;
        String time_created = null;
        String date_paid = null;

        if (((EditConDateActFrom.getText().isBlank()) || (EditConDateActTo.getText().isBlank()) || (EditConDateSin.getText().isBlank()) || (EditConDateIssued.getText().isBlank()) || (EditConDatePaid.getText().isBlank()) || (EditConTimeCreated.getText().isBlank()))) { 
            javax.swing.JOptionPane.showMessageDialog(null, "Please enter all the fields!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
        }else{
            //Ελεγχος του Contract ID
            try{
                id = Integer.parseInt(EditConCodeLabel.getText());
            }catch (Exception e){
                javax.swing.JOptionPane.showMessageDialog(null, "Please enter a number at Contract Code!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
                error =true;
            }
            if (id < 0){
                error = true;
            }
            //Ελεγχος του Invoice ID 
            try{
                invoice_id = Integer.parseInt(EditConInvoiceCodeLabel.getText());
            }catch (Exception e){
                javax.swing.JOptionPane.showMessageDialog(null, "Please enter a number at Contract Code!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
                error =true;
            }
            if (invoice_id < 0 ){
                error = true;
            }

            //Αποθηκευση των τιμων των Spinner σε μεταβλητες
            
            date_signed = EditConDateSin.getText().trim();
            date_act_from =EditConDateActFrom.getText().trim();
            date_act_to = EditConDateActTo.getText().trim();
            date_issued =EditConDateIssued.getText().trim();
            time_created = EditConTimeCreated.getText().trim();
            date_paid = EditConDatePaid.getText().trim();

            company_id = Integer.parseInt((String)EditConCompanyIDCB.getSelectedItem());
            billing_units = (String) EditConBillingUnitsCB.getSelectedItem();
            //Ελεγχος για αμα εχει δωθει το σωστα format στα spinner dd/mm/yyyy
            if ((date_signed.length() != 10) || (date_act_from.length() != 10) || (date_act_to.length()!=10) || (date_issued.length() != 10) || (time_created.length() != 10) || (date_paid.length() != 10)){
                error =true;
                javax.swing.JOptionPane.showMessageDialog(null, "Enter the date values with this pattern: dd/MM/yyyy","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
            }
            try{
                amount = Double.parseDouble(EditConInvoiceAmountTF.getText().trim());
            }catch (Exception e){
                javax.swing.JOptionPane.showMessageDialog(null, "Enter a number!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
                error =true;
            }
            try{
                fee =  Double.parseDouble(EditConInvoiceFeeTF.getText().trim());
            }catch (Exception e){
                javax.swing.JOptionPane.showMessageDialog(null, "Enter a number!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
                error =true;
            }
            try{
                tax = Double.parseDouble(EditConInvoiceTaxTF.getText().trim());
            }catch (Exception e){
                javax.swing.JOptionPane.showMessageDialog(null, "Enter a number!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
                error =true;
            }
            try{
                total = Double.parseDouble(EditConInvoiceTotalAmountTF.getText().trim());
            }catch (Exception e){
                javax.swing.JOptionPane.showMessageDialog(null, "Enter a number!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
                error =true;
            }
            
           

            //Ελεγχος για να δει αμα εχει παει ακτι στραβα
            if (error){
                javax.swing.JOptionPane.showMessageDialog(null, "Error at insert values the submit have failed!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
            //Αμα δεν...
            }else{
                try{
                    //Αποστολη των τιμως για το Contract 
                    conn = StartConn();
                    prepared = conn.prepareStatement("SELECT edit_aggreement(?,?,?,?,?,?)");
                    prepared.setInt(1,id);
                    prepared.setString(2,date_signed);
                    prepared.setString(3,date_act_from);
                    prepared.setString(4,date_act_to);
                    prepared.setInt(5,company_id);
                    prepared.setString(6,billing_units);
                    prepared.executeQuery();
                    prepared.close();

                    //Αποστολη των τιμω για το Invoice 
                    prepared_invoice = conn.prepareStatement("SELECT edit_bill(?,?,?,?,?,?,?,?,?,?)");
                    prepared_invoice.setInt(1,invoice_id);
                    prepared_invoice.setInt(2,id);
                    prepared_invoice.setInt(3,company_id);
                    prepared_invoice.setDouble(4, amount);
                    prepared_invoice.setDouble(5,fee);
                    prepared_invoice.setDouble(6,tax);
                    prepared_invoice.setDouble(7,total);
                    prepared_invoice.setString(8,time_created);
                    prepared_invoice.setString(9,date_issued);
                    prepared_invoice.setString(10,date_paid);
                    prepared_invoice.executeQuery();
                    prepared_invoice.close();
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
    }
    
    //Μεθοδος για την τοποθεσια του frame στο κεντρο
    private void WindowAtCenter(JFrame objFrame){
        Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int iCoordX = (objDimension.width - objFrame.getWidth()) / 2;
        int iCoordY = (objDimension.height - objFrame.getHeight()) / 2;
        objFrame.setLocation(iCoordX, iCoordY); 
    }
   
    public EditContractJF() {
        initComponents();
        WindowAtCenter(this);
        
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        EditConClearButton = new javax.swing.JButton();
        EditConSubmitButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        EditConCompanyIDCB = new javax.swing.JComboBox<>();
        EditConBillingUnitsCB = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        EditConInvoiceAmountTF = new javax.swing.JTextField();
        EditConInvoiceFeeTF = new javax.swing.JTextField();
        EditConInvoiceTaxTF = new javax.swing.JTextField();
        EditConInvoiceTotalAmountTF = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        EditConCodeLabel = new javax.swing.JLabel();
        EditConInvoiceCodeLabel = new javax.swing.JLabel();
        EditConDateActFrom = new javax.swing.JTextField();
        EditConDateSin = new javax.swing.JTextField();
        EditConDateIssued = new javax.swing.JTextField();
        EditConDateActTo = new javax.swing.JTextField();
        EditConTimeCreated = new javax.swing.JTextField();
        EditConDatePaid = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(200, 255, 240));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel1.setText("Edit Contract");

        EditConClearButton.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        EditConClearButton.setText("Clear");
        EditConClearButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        EditConClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        EditConSubmitButton.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        EditConSubmitButton.setText("Submit");
        EditConSubmitButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        EditConSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel2.setText("Contract Code:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel3.setText("Date Singed:");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel4.setText("Date Active From:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel5.setText("Company ID:");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel6.setText("Billing Units:");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel7.setText("Date Active To:");

        EditConCompanyIDCB.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel8.setText("Invoice Code:");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel10.setText("Amount:");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel11.setText("Fee:");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel12.setText("Tax:");

        jLabel13.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel13.setText("Total Amount:");

        jLabel14.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel14.setText("Date Issued:");

        jLabel15.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel15.setText("Time Created:");

        jLabel16.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel16.setText("Date Paid:");

        EditConInvoiceAmountTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        EditConInvoiceFeeTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        EditConInvoiceTaxTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        EditConInvoiceTotalAmountTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel17.setText("Edit Invoice");

        EditConCodeLabel.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        EditConInvoiceCodeLabel.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        EditConDateActFrom.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        EditConDateActFrom.setText("dd/mm/yyyy");

        EditConDateSin.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        EditConDateSin.setText("dd/mm/yyyy");

        EditConDateIssued.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        EditConDateIssued.setText("dd/mm/yyyy");

        EditConDateActTo.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        EditConDateActTo.setText("dd/mm/yyyy");

        EditConTimeCreated.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        EditConTimeCreated.setText("dd/mm/yyyy");

        EditConDatePaid.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        EditConDatePaid.setText("dd/mm/yyyy");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(EditConInvoiceTaxTF, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(EditConInvoiceFeeTF)
                            .addComponent(EditConInvoiceAmountTF)
                            .addComponent(EditConInvoiceCodeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(EditConCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(EditConDateSin)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(EditConDateActFrom)
                                .addGap(1, 1, 1)))))
                .addGap(86, 86, 86)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(23, 23, 23))
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EditConDatePaid, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EditConTimeCreated, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(EditConCompanyIDCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(EditConBillingUnitsCB, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(EditConInvoiceTotalAmountTF, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(EditConDateIssued, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(EditConDateActTo, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(4, 4, 4))))
                .addGap(230, 230, 230))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(293, 293, 293)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(295, 295, 295)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(EditConClearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(EditConSubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(191, 191, 191))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(53, 53, 53)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(EditConCodeLabel))
                        .addGap(93, 93, 93)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(EditConDateSin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(76, 76, 76)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addComponent(EditConDateActFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addComponent(jLabel17)
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel8)
                                            .addComponent(EditConInvoiceCodeLabel))
                                        .addGap(136, 136, 136)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel15)
                                            .addComponent(EditConInvoiceFeeTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel11))
                                        .addGap(56, 56, 56)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel16)
                                            .addComponent(EditConInvoiceTaxTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel12)
                                            .addComponent(EditConDatePaid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(80, 80, 80)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel14)
                                            .addComponent(jLabel10)
                                            .addComponent(EditConInvoiceAmountTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(EditConSubmitButton)
                                    .addComponent(EditConClearButton))
                                .addGap(46, 46, 46))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(EditConCompanyIDCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89)
                        .addComponent(EditConBillingUnitsCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(EditConDateActTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(100, 100, 100)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(152, 152, 152)
                                .addComponent(EditConTimeCreated, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(EditConInvoiceTotalAmountTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(EditConDateIssued, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents




    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditContractJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditContractJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditContractJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditContractJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditContractJF().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> EditConBillingUnitsCB;
    private javax.swing.JButton EditConClearButton;
    private javax.swing.JLabel EditConCodeLabel;
    private javax.swing.JComboBox<String> EditConCompanyIDCB;
    private javax.swing.JTextField EditConDateActFrom;
    private javax.swing.JTextField EditConDateActTo;
    private javax.swing.JTextField EditConDateIssued;
    private javax.swing.JTextField EditConDatePaid;
    private javax.swing.JTextField EditConDateSin;
    private javax.swing.JTextField EditConInvoiceAmountTF;
    private javax.swing.JLabel EditConInvoiceCodeLabel;
    private javax.swing.JTextField EditConInvoiceFeeTF;
    private javax.swing.JTextField EditConInvoiceTaxTF;
    private javax.swing.JTextField EditConInvoiceTotalAmountTF;
    private javax.swing.JButton EditConSubmitButton;
    private javax.swing.JTextField EditConTimeCreated;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
