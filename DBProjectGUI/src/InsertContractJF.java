
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.SpinnerDateModel;
import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

import java.sql.*;
import java.text.Format;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Stefanos
 */
public class InsertContractJF extends javax.swing.JFrame {

    
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
    
    
    
    private void ACtionPerformed(java.awt.event.ActionEvent e){
           if (e.getSource() == InsertConClearButton){
              clear();
           }else if(e.getSource() == InsertConSubmitButton){
               submit();
               clear();
           }

    }
    
    private void clear(){
        InsertConCodeTF.setText("");
        InsertConBillingUnitsCB.setSelectedIndex(0);      
        InsertConCompanyIDCB.setSelectedIndex(0);        
    }

    public void inisialize(){
        Connection conn = null;
        Statement stmt_units = null;
        Statement stmt_companyID = null;
        ResultSet rs_units = null;
        ResultSet rs_companyID = null;

        Pattern pattern;
        Matcher matcher;

        try{
            conn = StartConn();
            stmt_companyID = conn.createStatement();
            stmt_units = conn.createStatement();
            rs_companyID = stmt_companyID.executeQuery("SELECT get_id_company()");
            rs_units = stmt_units.executeQuery("SELECT get_contract_check_units()");
            if (rs_companyID.next() == false){
                javax.swing.JOptionPane.showMessageDialog(null, "Ther is no company, insert first company! ","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
                InsertConClearButton.setEnabled(false);
                InsertConSubmitButton.setEnabled(false);
            }else{
                do{
                    InsertConCompanyIDCB.addItem(rs_companyID.getString(1));
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
                        InsertConBillingUnitsCB.addItem(value.substring(1, value.length()-1));                     
                    }
                 }
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

    }

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

        if ((InsertConCodeTF.getText().isBlank()) || (InsertConDateActFromSpinner.getValue().toString().isBlank()) || (InsertConDateActToSpinner.getValue().toString().isBlank()) || (InsertConDateSinSpinner.getValue().toString().isBlank()) || (InsertContractInvoiceDateIssuedSpinner.getValue().toString().isBlank()) || (InsertContractInvoiceDatePaidSpinner.getValue().toString().isBlank()) || (InsertContractInvoiceTimeCreatedSpinner.getValue().toString().isBlank())) { 
            javax.swing.JOptionPane.showMessageDialog(null, "Please enter all the fields!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
        }else{
            try{
                id = Integer.parseInt(InsertConCodeTF.getText());
            }catch (Exception e){
                javax.swing.JOptionPane.showMessageDialog(null, "Please enter a number at Contract Code!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
                error =true;
            }
            if (id < 0){
                error = true;
            }
            String [] date_signed_frm = InsertConDateSinSpinner.getValue().toString().trim().split(" ");
            date_signed= String.format("%s/%s/%s",date_signed_frm[2],month(date_signed_frm[1]),date_signed_frm[5]);

            String [] date_act_from_frm = InsertConDateActFromSpinner.getValue().toString().trim().split(" "); 
            date_act_from = String.format("%s/%s/%s",date_act_from_frm[2],month(date_act_from_frm[1]),date_act_from_frm[5]);

            String [] date_act_to_frm = InsertConDateActToSpinner.getValue().toString().trim().split(" ");
            date_act_to = String.format("%s/%s/%s",date_act_to_frm[2],month(date_act_to_frm[1]),date_act_to_frm[5]);
             
            company_id = Integer.parseInt((String)InsertConCompanyIDCB.getSelectedItem());
            billing_units = (String) InsertConBillingUnitsCB.getSelectedItem();

            if ((date_signed.length() != 10) || (date_act_from.length() != 10) || (date_act_to.length()!=10)){
                error =true;
                javax.swing.JOptionPane.showMessageDialog(null, "Enter the date values with this pattern: dd/MM/yyyy","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
            }


            if (error){
                javax.swing.JOptionPane.showMessageDialog(null, "Error at insert values the submit have failed!","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
            }else{
                try{
                    conn = StartConn();
                    prepared = conn.prepareStatement("SELECT insert_aggreement(?,?,?,?,?,?)");
                    prepared.setInt(1,id);
                    prepared.setString(2,date_signed);
                    prepared.setString(3,date_act_from);
                    prepared.setString(4,date_act_to);
                    prepared.setInt(5,company_id);
                    prepared.setString(6,billing_units);
                    prepared.executeQuery();
                    prepared.close();
                    javax.swing.JOptionPane.showMessageDialog(null, "The Insert Complete!","INFO",javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }catch (SQLException ex){
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
    
    private void WindowAtCenter(JFrame objFrame){
        Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int iCoordX = (objDimension.width - objFrame.getWidth()) / 2;
        int iCoordY = (objDimension.height - objFrame.getHeight()) / 2;
        objFrame.setLocation(iCoordX, iCoordY); 
    }
    
    public InsertContractJF() {
        initComponents();
        WindowAtCenter(this);
        inisialize();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        InsertConClearButton = new javax.swing.JButton();
        InsertConSubmitButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        InsertConCompanyIDCB = new javax.swing.JComboBox<>();
        InsertConCodeTF = new javax.swing.JTextField();
        InsertConBillingUnitsCB = new javax.swing.JComboBox<>();

        InsertConDateSinSpinner = new javax.swing.JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MONTH));
        InsertConDateSinSpinner.setEditor(new javax.swing.JSpinner.DateEditor(InsertConDateSinSpinner, "dd/MM/yyyy"));
        
        InsertConDateActFromSpinner = new javax.swing.JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MONTH));
        InsertConDateActFromSpinner.setEditor(new javax.swing.JSpinner.DateEditor(InsertConDateActFromSpinner, "dd/MM/yyyy"));

        InsertConDateActToSpinner = new javax.swing.JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MONTH));
        InsertConDateActToSpinner.setEditor(new javax.swing.JSpinner.DateEditor(InsertConDateActToSpinner, "dd/MM/yyyy"));
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        InsertContractInvoiceCodeTF = new javax.swing.JTextField();
        InsertContractInvoiceCompanyCodeCB = new javax.swing.JComboBox<>();
        InsertContractInvoiceAmountTF = new javax.swing.JTextField();
        InsertContractInvoiceFeeTF = new javax.swing.JTextField();
        InsertContractInvoiceTaxTF = new javax.swing.JTextField();
        InsertContractInvoiceTotalAmountTF = new javax.swing.JTextField();
        InsertContractInvoiceDateIssuedSpinner = new javax.swing.JSpinner();
        InsertContractInvoiceTimeCreatedSpinner = new javax.swing.JSpinner();
        InsertContractInvoiceDatePaidSpinner = new javax.swing.JSpinner();
        jLabel17 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(200, 255, 240));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel1.setText("Insert Contract");

        InsertConClearButton.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        InsertConClearButton.setText("Clear");
        InsertConClearButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        InsertConClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertConClearButtonActionPerformed(evt);
            }
        });

        InsertConSubmitButton.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        InsertConSubmitButton.setText("Submit");
        InsertConSubmitButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        InsertConSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertConSubmitButtonActionPerformed(evt);
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

        InsertConCompanyIDCB.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        InsertConCodeTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        InsertConDateSinSpinner.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(315648360000L), new java.util.Date(315648360000L), new java.util.Date(315648360000L), java.util.Calendar.DAY_OF_MONTH));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel8.setText("Invoice Code:");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel9.setText("Company Code:");

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

        InsertContractInvoiceCodeTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        InsertContractInvoiceCompanyCodeCB.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        InsertContractInvoiceAmountTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        InsertContractInvoiceFeeTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        InsertContractInvoiceTaxTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        InsertContractInvoiceTotalAmountTF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        InsertContractInvoiceDateIssuedSpinner.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        InsertContractInvoiceTimeCreatedSpinner.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        InsertContractInvoiceDatePaidSpinner.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel17.setText("Invoice Values");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(336, 336, 336)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(31, 31, 31))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(InsertConCodeTF, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(InsertConDateSinSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(InsertConDateActFromSpinner)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(InsertContractInvoiceTaxTF)
                                .addComponent(InsertContractInvoiceFeeTF)
                                .addComponent(InsertContractInvoiceAmountTF)
                                .addComponent(InsertContractInvoiceCompanyCodeCB, 0, 130, Short.MAX_VALUE))
                            .addComponent(InsertContractInvoiceCodeTF, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(86, 86, 86)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(InsertContractInvoiceDateIssuedSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(InsertContractInvoiceTimeCreatedSpinner)
                            .addComponent(InsertContractInvoiceDatePaidSpinner))
                        .addGap(143, 143, 143))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(InsertConCompanyIDCB, 0, 129, Short.MAX_VALUE)
                                .addComponent(InsertConBillingUnitsCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(InsertContractInvoiceTotalAmountTF, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(InsertConDateActToSpinner, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)))
                        .addGap(139, 139, 139))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(InsertConClearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(InsertConSubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(183, 183, 183))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(53, 53, 53)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(InsertConCompanyIDCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InsertConCodeTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(89, 89, 89)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(InsertConBillingUnitsCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InsertConDateSinSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(76, 76, 76)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7)
                    .addComponent(InsertConDateActFromSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InsertConDateActToSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jLabel17)
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(InsertContractInvoiceCodeTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(InsertContractInvoiceCompanyCodeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(InsertContractInvoiceDateIssuedSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(InsertContractInvoiceAmountTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(InsertContractInvoiceTimeCreatedSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(InsertContractInvoiceFeeTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(InsertContractInvoiceDatePaidSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(InsertContractInvoiceTaxTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 67, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(InsertContractInvoiceTotalAmountTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(InsertConClearButton)
                            .addComponent(InsertConSubmitButton))))
                .addContainerGap())
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
            java.util.logging.Logger.getLogger(InsertContractJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InsertContractJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InsertContractJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InsertContractJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InsertContractJF().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> InsertConBillingUnitsCB;
    private javax.swing.JButton InsertConClearButton;
    private javax.swing.JTextField InsertConCodeTF;
    private javax.swing.JComboBox<String> InsertConCompanyIDCB;
    private javax.swing.JSpinner InsertConDateActFromSpinner;
    private javax.swing.JSpinner InsertConDateActToSpinner;
    private javax.swing.JSpinner InsertConDateSinSpinner;
    private javax.swing.JButton InsertConSubmitButton;
    private javax.swing.JTextField InsertContractInvoiceAmountTF;
    private javax.swing.JTextField InsertContractInvoiceCodeTF;
    private javax.swing.JComboBox<String> InsertContractInvoiceCompanyCodeCB;
    private javax.swing.JSpinner InsertContractInvoiceDateIssuedSpinner;
    private javax.swing.JSpinner InsertContractInvoiceDatePaidSpinner;
    private javax.swing.JTextField InsertContractInvoiceFeeTF;
    private javax.swing.JTextField InsertContractInvoiceTaxTF;
    private javax.swing.JSpinner InsertContractInvoiceTimeCreatedSpinner;
    private javax.swing.JTextField InsertContractInvoiceTotalAmountTF;
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
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
