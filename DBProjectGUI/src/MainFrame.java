import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.DefaultListModel;


public class MainFrame extends javax.swing.JFrame {
    
    
    
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

   
    
    
    //Μεθοδος για την εμφανιση των μαγαζιων του επιλεγμενου Mall
    public void selectMalls(){
        DefaultListModel shop_list = (DefaultListModel) ShopsList.getModel();
        Connection conn=null;
        PreparedStatement prepared = null;
        ResultSet rs = null;
        shop_list.removeAllElements();
        String selected_String =(String)MallsList.getSelectedValue();
        String [] selected_array = selected_String.split(" ");
        int selected_id = Integer.parseInt(selected_array[1]);
        try{
            conn = StartConn();
            prepared = conn.prepareStatement("SELECT Select_Mall(?)");
            prepared.setInt(1, selected_id);
            rs=prepared.executeQuery();
            /*Αρχικα ελεγχεται αμα το rs ειναι κενο
              Χρησημοποιητε do while γιατι αμα χρησημοποιηθει απλη while λογο
              το οτι πρεπει να γραφει while (rs.next()) δεν θα εμφανησει την πρωτη γραμη του rs 
            */
            if (rs.next() == false){
                System.out.print("The resultSet is Empty!");
                javax.swing.JOptionPane.showMessageDialog(null, "Ther is no shops for this Mall","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
                fillShopsList();
            }else{
                do {
                    String row = rs.getString(1);
                    System.out.println(row);
                    row = row.substring(1, row.length()-1);
                    String[] values_list = row.split(",");
                    shop_list.addElement("Κωδικός: " + values_list[0] + " Όνομα: " + values_list[1] + " Κωδικός Mall: " + values_list[2] + " Ονομα Mall: " + values_list[3]);
                }while (rs.next());
                javax.swing.JOptionPane.showMessageDialog(null, "The shops has display at tab Shops","INFO",javax.swing.JOptionPane.INFORMATION_MESSAGE);
                prepared.close();
            }   
        }catch (SQLException ex){
            System.out.println("---SQL Exception---");
            System.out.println("Message: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("ErrorCode: " + ex.getErrorCode());      
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
    
    public void refresh(javax.swing.JList lista){
        DefaultListModel listmodel = (DefaultListModel) lista.getModel();
        listmodel.removeAllElements();
        if  (lista==MallsList){
            fillMallList();
        }else if (lista == ShopsList){
            fillShopsList();
        }
        
    }
    
    private void fillShopsList(){
        DefaultListModel list_model = (DefaultListModel) ShopsList.getModel();
        Connection conn=null;
        Statement stmt;
        ResultSet rs;
        
        try{
           conn = StartConn();
           stmt = conn.createStatement();
           rs = stmt.executeQuery("SELECT Fill_shops()");
           //loop
           if (rs.next() == false){
               System.out.print("The resultSet is Empty");
                javax.swing.JOptionPane.showMessageDialog(null, "Ther is no Shops at the database","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
           }else{
               do{
                   String row = rs.getString(1);
                   row =row.substring(1, row.length()-1);
                   String[] values=row.split(",");
                   list_model.addElement("Κωδικός: " + values[0] + " Όνομα: " + values[1] + " Κωδικός Mall: " + values[2]);
               }while (rs.next());
               stmt.close();
               ShopsList.setSelectedIndex(0);
           }
        }catch (SQLException ex){
            System.out.println("---SQL Exception---");
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
   
            
    //Μεθοδος γαι το γεμισμα του MallsList        
    private void fillMallList(){//ArrayList<Mall> lista
        DefaultListModel listmodel = (DefaultListModel) MallsList.getModel();
        Connection conn = null;
        Statement stmt;
        ResultSet rs;      
        //Θα καλειτε ενα function γαι να τραβηξει ολες τις τιμες του πινακα Shopping_center και να τις εισαγει στην λιστα list_Of_Malls
        try {
            conn = StartConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT Fill_Malls_List()");
            //loop
            if (rs.next() == false){
                System.out.print("The resultSet is Empty!");
                javax.swing.JOptionPane.showMessageDialog(null, "Ther is no Malls at the database","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
            }else{
                do{
                    String row=rs.getString(1);
                    row =row.substring(1, row.length()-1);
                    String[] values=row.split(",");               
                    listmodel.addElement("Κωδικός: " + values[0] + " Οναμα: " + values[1] + " Διευθυνση: " + values[2].substring(1, values[2].length()) + " ΤK: " + values[3] + " Πολη: " + values[4].substring(0,values[4].length()-1));
                }while (rs.next());
            }
            stmt.close();
            MallsList.setSelectedIndex(0);
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
    public void  deleteValueFromMallsList(){
        DefaultListModel listmodel = (DefaultListModel) MallsList.getModel();
        Connection conn = null;
        PreparedStatement prepared = null;
        
        String selected_String =(String)MallsList.getSelectedValue();
        String [] selected_array = selected_String.split(" ");
        int selected_id = Integer.parseInt(selected_array[1]);
        try{
            conn = StartConn();
            prepared = conn.prepareStatement("SELECT Delete_Mall(?)");
            prepared.setInt(1,selected_id);
            prepared.executeQuery();
            prepared.close();
            refresh(MallsList);
            javax.swing.JOptionPane.showMessageDialog(null, "Succefull Delete","INFO",javax.swing.JOptionPane.INFORMATION_MESSAGE);
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


    public void deleteShop(){
        DefaultListModel listmodel = (DefaultListModel) ShopsList.getModel();
        Connection conn = null;
        PreparedStatement prepared = null;
        
        String selected_String =(String)ShopsList.getSelectedValue();
        String [] selected_array = selected_String.split(" ");
        int selected_id = Integer.parseInt(selected_array[1]);
        try{
            conn = StartConn();
            prepared = conn.prepareStatement("SELECT Delete_Shop(?)");
            prepared.setInt(1,selected_id);
            prepared.executeQuery();
            prepared.close();
            refresh(ShopsList);
            javax.swing.JOptionPane.showMessageDialog(null, "Succefull Delete","INFO",javax.swing.JOptionPane.INFORMATION_MESSAGE);
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
            
            

   
    private void WindowAtCenter(JFrame objFrame){
        Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int iCoordX = (objDimension.width - objFrame.getWidth()) / 2;
        int iCoordY = (objDimension.height - objFrame.getHeight()) / 2;
        objFrame.setLocation(iCoordX, iCoordY); 
    }

    //Handler για να διαχειριζεται ολα τα buttons
    public void ACtionPerformed(java.awt.event.ActionEvent e){
        //MALLS
        if (e.getSource() == InsertMallsButton){
            MallsInsertJF MallsJF = new MallsInsertJF();
            MallsJF.setVisible(true);
        }else if  (e.getSource() == DeleteMallsButton){
            deleteValueFromMallsList();
        }else if (e.getSource() == SelectMallsButton) {
            selectMalls();
        }else if (e.getSource() == RefreshMallsButton) {
            refresh(MallsList);
        }else if(e.getSource() == EditMallsButton) {
            MallsEditJF EditJF = new MallsEditJF();
            EditJF.inisialize((String)MallsList.getSelectedValue());
            EditJF.setVisible(true);
        //SHOPS
        }else if(e.getSource() == ShowAllShopsButton){
            refresh(ShopsList);
        }else if(e.getSource() == DeleteShopsButton){
            deleteShop();
        }else if(e.getSource() == InfoShopsButton){
            InfoShopJF info_shop = new InfoShopJF();
            info_shop.inisialize(ShopsList.getSelectedValue());
            info_shop.setVisible(true);
        }else if(e.getSource() == InsertShopsButton){
           ShopInsertJF aa = new ShopInsertJF();
           aa.setVisible(true);
        }else if (e.getSource() == EditShopsButton){
            ShopEditJF shopEdit = new ShopEditJF();
            shopEdit.inisialize(ShopsList.getSelectedValue());
            shopEdit.setVisible(true);
        }

    }
    
    
    public MainFrame() {
        initComponents();
        WindowAtCenter(this);
        fillMallList();
        fillShopsList();
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
        RefreshMallsButton = new javax.swing.JButton();
        EditMallsButton = new javax.swing.JButton();
        ShopTab = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ShopsList = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        InsertShopsButton = new javax.swing.JButton();
        DeleteShopsButton = new javax.swing.JButton();
        InfoShopsButton = new javax.swing.JButton();
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
        TabbedPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        TabbedPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        TabbedPane.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        TabbedPane.setName("TabMenu"); // NOI18N
        TabbedPane.setOpaque(true);

        MallsTab.setBackground(new java.awt.Color(200, 255, 240));
        MallsTab.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        MallsTab.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        MallsTab.setName("MallsTab"); // NOI18N

        MallsList.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        MallsList.setModel(new javax.swing.DefaultListModel<String>()
        );
        MallsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        MallsList.setSelectedIndex(1);
        jScrollPane1.setViewportView(MallsList);
        MallsList.getAccessibleContext().setAccessibleName("MallsList");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("List with Malls");

        InsertMallsButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        InsertMallsButton.setText("Insert");
        InsertMallsButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        InsertMallsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        DeleteMallsButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        DeleteMallsButton.setText("Delete");
        DeleteMallsButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        DeleteMallsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        SelectMallsButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        SelectMallsButton.setText("Select");
        SelectMallsButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        SelectMallsButton.setMaximumSize(new java.awt.Dimension(65, 25));
        SelectMallsButton.setMinimumSize(new java.awt.Dimension(65, 25));
        SelectMallsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        RefreshMallsButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        RefreshMallsButton.setText("Refresh");
        RefreshMallsButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        RefreshMallsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        EditMallsButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        EditMallsButton.setText("Edit");
        EditMallsButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
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
                                .addComponent(RefreshMallsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(RefreshMallsButton)
                    .addComponent(SelectMallsButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EditMallsButton))
                .addContainerGap(499, Short.MAX_VALUE))
        );

        TabbedPane.addTab("Malls ", new javax.swing.ImageIcon(getClass().getResource("/mallsicon.png")), MallsTab, "Here despley all available malls "); // NOI18N
        MallsTab.getAccessibleContext().setAccessibleName("MallsTab");

        ShopTab.setBackground(new java.awt.Color(200, 255, 240));
        ShopTab.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ShopTab.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N

        ShopsList.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        ShopsList.setModel(new javax.swing.DefaultListModel<String>());
        ShopsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ShopsList.setToolTipText("");
        ShopsList.setSelectedIndex(0);
        jScrollPane2.setViewportView(ShopsList);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("A list with shops ");

        InsertShopsButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        InsertShopsButton.setText("Insert");
        InsertShopsButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        InsertShopsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        DeleteShopsButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        DeleteShopsButton.setText("Delete");
        DeleteShopsButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        DeleteShopsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        InfoShopsButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        InfoShopsButton.setText("Info");
        InfoShopsButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        InfoShopsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        ShowAllShopsButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ShowAllShopsButton.setText("Show All");
        ShowAllShopsButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ShowAllShopsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

        EditShopsButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        EditShopsButton.setText(" Edit");
        EditShopsButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        EditShopsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACtionPerformed(evt);
            }
        });

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(DeleteShopsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(EditShopsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addComponent(InfoShopsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
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
                    .addComponent(InfoShopsButton)
                    .addComponent(ShowAllShopsButton)
                    .addComponent(EditShopsButton))
                .addContainerGap(477, Short.MAX_VALUE))
        );

        TabbedPane.addTab("Shops", new javax.swing.ImageIcon(getClass().getResource("/shopmini.png")), ShopTab, "The shops of Mall"); // NOI18N
        ShopTab.getAccessibleContext().setAccessibleName("ShopTab");

        ConstantTab.setBackground(new java.awt.Color(200, 255, 240));
        ConstantTab.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ConstantTab.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N

        javax.swing.GroupLayout ConstantTabLayout = new javax.swing.GroupLayout(ConstantTab);
        ConstantTab.setLayout(ConstantTabLayout);
        ConstantTabLayout.setHorizontalGroup(
            ConstantTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 833, Short.MAX_VALUE)
        );
        ConstantTabLayout.setVerticalGroup(
            ConstantTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 774, Short.MAX_VALUE)
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
            .addComponent(TabbedPane, javax.swing.GroupLayout.Alignment.TRAILING)
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
    private javax.swing.JButton InfoShopsButton;
    private javax.swing.JButton InsertMallsButton;
    private javax.swing.JButton InsertShopsButton;
    private javax.swing.JList MallsList;
    private javax.swing.JPanel MallsTab;
    private javax.swing.JButton RefreshMallsButton;
    private javax.swing.JButton SelectMallsButton;
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
