import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.DefaultListModel;


public class MainFrame extends javax.swing.JFrame {
    
    //Αρχικοποιηση Μεταβλητων
    int selected_Index_Value_Of_Malls_Frame = -1; //Μεταβλητη για την ευρεση του επιλεγμενου Mall 
   // ArrayList<Mall> list_Of_Malls = new ArrayList<Mall>(); //Λιστα με τα Malls της βασης 
    
    /*Δημιουργια λιστας για το γεμισμα της MallsList και χρηση αυτη της λιστας στο Panel Shops για 
      ευρεση αμα εχει επιλεχτει καποιο Mall (αυτο γινεται με της χρηση της μεταβλητης selectedIndexValueOfMallsFrame η οποια 
      αμα εχει -1 δεν εχει επιλεχτει κατι)
    */
    public void fillMallList(){//ArrayList<Mall> lista
        DefaultListModel listmodel = (DefaultListModel) MallsList.getModel();
        Connection conn = startConn();
        Statement stmt;
        ResultSet rs;
        ResultSetMetaData rsmd;      
        //Θα καλειτε ενα function γαι να τραβηξει ολες τις τιμες του πινακα Shopping_center και να τις εισαγει στην λιστα list_Of_Malls
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT Fill_Malls_List()");
            //loop         
            while (rs.next()){
                String row=rs.getString(1);
                row =row.substring(1, row.length()-1);
                String[] values=row.split(",");
                listmodel.addElement("Κωδικός: " + values[0] + " Οναμα: " + values[1] + " Διευθυνση: " + values[2]);
               // lista.add(new Mall(id,address,name));
            }
            stmt.close();
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
    }    
    
            
    /*Μεθοδος για την διαγραφη του επιλεγμενου στοιχειου απο την βαση, 
    απο την λιστα MallsList */      
    public void  deleteValueFromMallsList(int index){
        Connection conn = startConn();
        
        DefaultListModel listModel = (DefaultListModel) MallsList.getModel();
        listModel.remove(index);
        
    }       
            
            
    public Connection startConn(){
        String     driverClassName = "org.postgresql.Driver" ;
        String     url = "jdbc:postgresql://localhost:5432/DBLabs" ;
        Connection dbConnection = null;
        String     username = "postgres";
        String     passwd = "147896325!";
        try{
            Class.forName(driverClassName);
        }catch (Exception e){
            System.out.println(e);
        }
        try{
            dbConnection = DriverManager.getConnection(url, username, passwd);
        }catch (SQLException ex){
            System.out.println("SQL Exception");
            while (ex != null){
                System.out.println("Message: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("ErrorCode: " + ex.getErrorCode());
                ex.getNextException();
            }
        }
        return dbConnection;
    }
   
    public void WindowAtCenter(JFrame objFrame){
        Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int iCoordX = (objDimension.width - objFrame.getWidth()) / 2;
        int iCoordY = (objDimension.height - objFrame.getHeight()) / 2;
        objFrame.setLocation(iCoordX, iCoordY); 
    }

    //Handler για να διαχειριζεται ολα τα buttons
    public void ACtionPerformed(java.awt.event.ActionEvent e){
        if (e.getSource() == InsertMallsButton){
            MallsInsertJF MallsJF = new MallsInsertJF();
            MallsJF.setVisible(true);
        }else if  (e.getSource() == DeleteMallsButton){
            int index_of_item=MallsList.getSelectedIndex();
            deleteValueFromMallsList(index_of_item);
        }else if (e.getSource() == SelectMallsButton) {
            selected_Index_Value_Of_Malls_Frame = MallsList.getSelectedIndex();
        }else if (e.getSource() == SearchMallsButton) {

        }else if(e.getSource() == EditMallsButton) {
            
        }    
    }
    
    
    public MainFrame() {
        initComponents();
        WindowAtCenter(this);
        fillMallList();
    }

    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TabbedPane = new javax.swing.JTabbedPane();
        MallsTab = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        MallsList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        InsertMallsButton = new javax.swing.JButton();
        DeleteMallsButton = new javax.swing.JButton();
        SelectMallsButton = new javax.swing.JButton();
        SearchMallsButton = new javax.swing.JButton();
        EditMallsButton = new javax.swing.JButton();
        ShopTab = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ShopsList = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        InsertShopsButton = new javax.swing.JButton();
        DeleteShopsButton = new javax.swing.JButton();
        SearchShopsButton = new javax.swing.JButton();
        SelectShopsButton = new javax.swing.JButton();
        ShowAllShopsButton = new javax.swing.JButton();
        EditShopsButton = new javax.swing.JButton();
        ConstantTab = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("DBProject");
        setBackground(new java.awt.Color(4, 2, 2));
        setMaximumSize(new java.awt.Dimension(900, 900));
        setMinimumSize(new java.awt.Dimension(700, 700));
        setName("MainFrame"); // NOI18N
        setPreferredSize(new java.awt.Dimension(850, 850));

        TabbedPane.setBackground(new java.awt.Color(244, 211, 211));
        TabbedPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        TabbedPane.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        TabbedPane.setName("TabMenu"); // NOI18N
        TabbedPane.setOpaque(true);

        MallsTab.setBackground(new java.awt.Color(200, 255, 240));
        MallsTab.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        MallsTab.setName("MallsTab"); // NOI18N

        MallsList.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        MallsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        MallsList.setSelectedIndex(0);
        MallsList.setModel(new javax.swing.DefaultListModel<String>());
        jScrollPane1.setViewportView(MallsList);
        MallsList.getAccessibleContext().setAccessibleName("MallsList");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("List with Malls");

        InsertMallsButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        InsertMallsButton.setText("Insert");
        InsertMallsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        DeleteMallsButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        DeleteMallsButton.setText("Delete");
        DeleteMallsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        SelectMallsButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        SelectMallsButton.setText("Select");
        SelectMallsButton.setMaximumSize(new java.awt.Dimension(65, 25));
        SelectMallsButton.setMinimumSize(new java.awt.Dimension(65, 25));
        SelectMallsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        SearchMallsButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        SearchMallsButton.setText("Search");
        SearchMallsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        EditMallsButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        EditMallsButton.setText("Edit");
        EditMallsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MallsTabLayout = new javax.swing.GroupLayout(MallsTab);
        MallsTab.setLayout(MallsTabLayout);
        MallsTabLayout.setHorizontalGroup(
            MallsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MallsTabLayout.createSequentialGroup()
                .addGroup(MallsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MallsTabLayout.createSequentialGroup()
                        .addGap(381, 381, 381)
                        .addComponent(jLabel1))
                    .addGroup(MallsTabLayout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addGroup(MallsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(MallsTabLayout.createSequentialGroup()
                                .addComponent(InsertMallsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(SelectMallsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(DeleteMallsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(EditMallsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(SearchMallsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(148, Short.MAX_VALUE))
        );
        MallsTabLayout.setVerticalGroup(
            MallsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MallsTabLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(MallsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(InsertMallsButton)
                    .addComponent(DeleteMallsButton)
                    .addComponent(SearchMallsButton)
                    .addComponent(SelectMallsButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EditMallsButton))
                .addContainerGap(499, Short.MAX_VALUE))
        );

        TabbedPane.addTab("Malls ", new javax.swing.ImageIcon(getClass().getResource("/mallsicon.png")), MallsTab, "Here despley all available malls "); // NOI18N
        MallsTab.getAccessibleContext().setAccessibleName("MallsTab");

        ShopTab.setBackground(new java.awt.Color(200, 255, 240));
        ShopTab.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N

        ShopsList.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        ShopsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ShopsList.setToolTipText("");
        ShopsList.setSelectedIndex(0);
        jScrollPane2.setViewportView(ShopsList);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("A list with shops ");

        InsertShopsButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        InsertShopsButton.setText("Insert");
        InsertShopsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               // InsertShopsButtonActionPerformed(evt);
            }
        });

        DeleteShopsButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        DeleteShopsButton.setText("Delete");

        SearchShopsButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        SearchShopsButton.setText("Search");
        SearchShopsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               // SearchShopsButtonActionPerformed(evt);
            }
        });

        SelectShopsButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        SelectShopsButton.setText("Select");

        ShowAllShopsButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ShowAllShopsButton.setText("Show All");

        EditShopsButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        EditShopsButton.setText(" Edit");

        javax.swing.GroupLayout ShopTabLayout = new javax.swing.GroupLayout(ShopTab);
        ShopTab.setLayout(ShopTabLayout);
        ShopTabLayout.setHorizontalGroup(
            ShopTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ShopTabLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(343, 343, 343))
            .addGroup(ShopTabLayout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addGroup(ShopTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 672, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ShopTabLayout.createSequentialGroup()
                        .addComponent(InsertShopsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(DeleteShopsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(EditShopsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(SearchShopsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(SelectShopsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(ShowAllShopsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        ShopTabLayout.setVerticalGroup(
            ShopTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShopTabLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addGap(26, 26, 26)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(ShopTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(InsertShopsButton)
                    .addComponent(DeleteShopsButton)
                    .addComponent(SelectShopsButton)
                    .addComponent(ShowAllShopsButton)
                    .addComponent(EditShopsButton)
                    .addComponent(SearchShopsButton))
                .addContainerGap(477, Short.MAX_VALUE))
        );

        TabbedPane.addTab("Shops", new javax.swing.ImageIcon(getClass().getResource("/shopmini.png")), ShopTab, "The shops of Mall"); // NOI18N
        ShopTab.getAccessibleContext().setAccessibleName("ShopTab");

        ConstantTab.setBackground(new java.awt.Color(200, 255, 240));
        ConstantTab.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N

        javax.swing.GroupLayout ConstantTabLayout = new javax.swing.GroupLayout(ConstantTab);
        ConstantTab.setLayout(ConstantTabLayout);
        ConstantTabLayout.setHorizontalGroup(
            ConstantTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 833, Short.MAX_VALUE)
        );
        ConstantTabLayout.setVerticalGroup(
            ConstantTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 811, Short.MAX_VALUE)
        );

        TabbedPane.addTab("Contracts", new javax.swing.ImageIcon(getClass().getResource("/conmono.png")), ConstantTab, "The contract for  specific shop of the mall"); // NOI18N
        ConstantTab.getAccessibleContext().setAccessibleName("ConstractTab");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabbedPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        TabbedPane.getAccessibleContext().setAccessibleName("MainTab");

        getAccessibleContext().setAccessibleName("MainFrame");

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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ConstantTab;
    private javax.swing.JButton DeleteMallsButton;
    private javax.swing.JButton DeleteShopsButton;
    private javax.swing.JButton EditMallsButton;
    private javax.swing.JButton EditShopsButton;
    private javax.swing.JButton InsertMallsButton;
    private javax.swing.JButton InsertShopsButton;
    private javax.swing.JList MallsList;
    private javax.swing.JPanel MallsTab;
    private javax.swing.JButton SearchMallsButton;
    private javax.swing.JButton SearchShopsButton;
    private javax.swing.JButton SelectMallsButton;
    private javax.swing.JButton SelectShopsButton;
    private javax.swing.JPanel ShopTab;
    private javax.swing.JList<String> ShopsList;
    private javax.swing.JButton ShowAllShopsButton;
    private javax.swing.JTabbedPane TabbedPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
