package Reception;
import db.DBconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class RecepBilling extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(RecepBilling.class.getName());
private int userId;
    private String userName;

       
    public RecepBilling(int userId, String name) {
        initComponents();
         this.userId = userId;
        this.userName = name;
        addPlaceHolderFields();
        initHoverEffects();
        initHoverEffects1();
        
        loadBillingTable1();  
        fetchTodayRevenue1();
        fetchTodayUnpaidBills();
        
        adjustColumnWidths();
    }
    

    private void addPlaceHolderFields(){
        addPlaceholder(txtSearch, "(eg. Gajodhar)");      
        addPlaceholder(txtFrom, "yyyy-mm-dd");    
        addPlaceholder(txtTo, "yyyy-mm-dd");    
 }
    private void addPlaceholder(javax.swing.JTextField field, String placeholderText) {
    
    field.setText(placeholderText);
    field.setForeground(new java.awt.Color(150,150,150)); // light gray

    field.addFocusListener(new java.awt.event.FocusAdapter() {

        @Override
        public void focusGained(java.awt.event.FocusEvent e) {
            if (field.getText().equals(placeholderText)) {
                field.setText("");
                field.setForeground(new java.awt.Color(0,0,0)); // black
            }
        }

        @Override
        public void focusLost(java.awt.event.FocusEvent e) {
            if (field.getText().trim().isEmpty()) {
                field.setText(placeholderText);
                field.setForeground(new java.awt.Color(150,150,150));
            }
        }
    });
}
    
    private void initHoverEffects() {
 styleHoverButton(jButton3);
    }
    private void initHoverEffects1() {
    styleHoverButton1(jButton4);
    styleHoverButton1(jButton5);
    styleHoverButton1(jButton6);
    styleHoverButton1(btnFilter);
}
    private void styleHoverButton(javax.swing.JButton btn) {
    btn.setContentAreaFilled(false);
    btn.setBorderPainted(false);
    btn.setFocusPainted(false);
    btn.setOpaque(false);
    btn.setForeground(new java.awt.Color(255,166,43));

    btn.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            btn.setContentAreaFilled(true);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            btn.setBackground( java.awt.Color.WHITE);
            btn.setForeground(new java.awt.Color(20,105,122));
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            btn.setContentAreaFilled(false);
            btn.setOpaque(false);
            btn.setBorderPainted(false);
            btn.setForeground(new java.awt.Color(255,166,43));
        }
    });
}
    private void styleHoverButton1(javax.swing.JButton btn) {
    btn.setContentAreaFilled(false);
    btn.setBorderPainted(false);
    btn.setFocusPainted(false);
    btn.setOpaque(false);
    btn.setForeground(new java.awt.Color(20,105,122));

    btn.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            btn.setContentAreaFilled(true);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            btn.setBackground(new java.awt.Color(20, 105, 122));
            btn.setForeground(java.awt.Color.WHITE);
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            btn.setContentAreaFilled(false);
            btn.setOpaque(false);
            btn.setBorderPainted(false);
            btn.setForeground(new java.awt.Color(20,105,122));
        }
    });
}
    
    private void loadBillingTable() {

    DefaultTableModel model = (DefaultTableModel) tblBilling.getModel();
    model.setRowCount(0); // clear table

    String sql = "SELECT b.bill_id, b.patient_id, p.full_name AS patient_name, " +
                 "b.appointment_id, b.doctor_id, b.total_amount, b.discount, " +
                 "b.net_amount, b.payment_mode, b.status, b.created_at " +
                 "FROM patient_billing b " +
                 "JOIN patients p ON b.patient_id = p.patient_id " +
                 "ORDER BY b.created_at DESC";

    try (Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("bill_id"),
                rs.getInt("patient_id"),
                rs.getString("patient_name"),   // NEW COLUMN
                rs.getInt("appointment_id"),
                rs.getInt("doctor_id"),
                rs.getBigDecimal("total_amount"),
                rs.getBigDecimal("discount"),
                rs.getBigDecimal("net_amount"),
                rs.getString("payment_mode"),
                rs.getString("status"),
                rs.getTimestamp("created_at")
            });
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading billing table: " + e.getMessage());
    }
}
    private void loadBillingTable1() {

    DefaultTableModel model = (DefaultTableModel) tblBilling.getModel();
    model.setRowCount(0); // clear table

    String sql = "SELECT b.bill_id, b.patient_id, p.full_name AS patient_name, " +
             "b.appointment_id, b.doctor_id, b.total_amount, b.discount, " +
             "b.net_amount, b.payment_mode, b.status, b.created_at " +
             "FROM patient_billing b " +
             "JOIN patients p ON b.patient_id = p.patient_id " +
             "WHERE DATE(b.created_at) = CURDATE() " +
             "ORDER BY b.created_at DESC";

    try (Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("bill_id"),
                rs.getInt("patient_id"),
                rs.getString("patient_name"),   // NEW COLUMN
                rs.getInt("appointment_id"),
                rs.getInt("doctor_id"),
                rs.getBigDecimal("total_amount"),
                rs.getBigDecimal("discount"),
                rs.getBigDecimal("net_amount"),
                rs.getString("payment_mode"),
                rs.getString("status"),
                rs.getTimestamp("created_at")
            });
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading billing table: " + e.getMessage());
    }
}
    private void fetchTodayRevenue() {

    String sql = "SELECT SUM(net_amount) AS totalRev " +
                 "FROM patient_billing ";

    try (Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            double total = rs.getDouble("totalRev");
            lblTotalRev.setText("₹ " + String.format("%.2f", total));
            today.setText("All");
        } else {
            lblTotalRev.setText("₹ 0.00");
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
            "Error calculating today's revenue: " + e.getMessage());
    }
}
     private void fetchTodayRevenue1() {

    String sql = "SELECT SUM(net_amount) AS totalRev " +
                 "FROM patient_billing " +
                 "WHERE DATE(created_at) = CURDATE()";

    try (Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            double total = rs.getDouble("totalRev");
            lblTotalRev.setText("₹ " + String.format("%.2f", total));
            today.setText("Today's");
        } else {
            lblTotalRev.setText("₹ 0.00");
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
            "Error calculating today's revenue: " + e.getMessage());
    }
}
     private void fetchTodayUnpaidBills() {

    String sql = "SELECT COUNT(*) AS totalUnpaid " +
                 "FROM patient_billing " +
                 "WHERE status = 'unpaid'";
                 

    try (Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            int totalUnpaid = rs.getInt("totalUnpaid");
            lblUnpaidCount.setText(String.valueOf(totalUnpaid)+" ");
        } else {
            lblUnpaidCount.setText("0");
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
            "Error calculating unpaid bills: " + e.getMessage());
    }
}
    
    private void adjustColumnWidths() {
    TableColumnModel col = tblBilling.getColumnModel();

    col.getColumn(0).setPreferredWidth(30);   // bill_id
    col.getColumn(1).setPreferredWidth(50);   // patient_id
    col.getColumn(2).setPreferredWidth(130);  // patient_name
    col.getColumn(3).setPreferredWidth(65);   // appointment_id
    col.getColumn(4).setPreferredWidth(70);   // doctor_id
    col.getColumn(5).setPreferredWidth(80);   // total_amount
    col.getColumn(6).setPreferredWidth(80);   // discount
    col.getColumn(7).setPreferredWidth(90);  // net_amount
    col.getColumn(8).setPreferredWidth(70);   // payment_mode
    col.getColumn(9).setPreferredWidth(80);   // status
    col.getColumn(10).setPreferredWidth(190); // created_at
}
    
    
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        lblHeaderNm = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblTotalRev = new javax.swing.JLabel();
        today = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        txtFrom = new javax.swing.JTextField();
        txtTo = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBilling = new javax.swing.JTable();
        total1 = new javax.swing.JLabel();
        total2 = new javax.swing.JLabel();
        total3 = new javax.swing.JLabel();
        btnFilter = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        lblUnpaidCount = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(20, 105, 122));

        lblHeaderNm.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        lblHeaderNm.setForeground(new java.awt.Color(255, 166, 43));
        lblHeaderNm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeaderNm.setText("Billing");
        lblHeaderNm.setToolTipText("");

        jButton3.setBackground(new java.awt.Color(22, 105, 122));
        jButton3.setFont(new java.awt.Font("Kohinoor Bangla", 3, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("< Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(276, 276, 276))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jButton3)
                .addGap(145, 145, 145)
                .addComponent(lblHeaderNm, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(246, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(21, Short.MAX_VALUE)
                        .addComponent(lblHeaderNm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.setBackground(new java.awt.Color(237, 231, 227));

        jPanel4.setBackground(new java.awt.Color(237, 231, 227));

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("October Compressed Devanagari", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(20, 105, 122));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Total Revenue");

        lblTotalRev.setFont(new java.awt.Font("Kohinoor Devanagari", 3, 36)); // NOI18N
        lblTotalRev.setForeground(new java.awt.Color(255, 166, 43));
        lblTotalRev.setText("-1");

        today.setBackground(new java.awt.Color(0, 0, 0));
        today.setFont(new java.awt.Font("October Compressed Devanagari", 2, 18)); // NOI18N
        today.setForeground(new java.awt.Color(72, 159, 181));
        today.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        today.setText("Today's");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(today, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalRev, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(today, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblTotalRev, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchKeyTyped(evt);
            }
        });

        txtFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFromActionPerformed(evt);
            }
        });
        txtFrom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFromKeyTyped(evt);
            }
        });

        txtTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtToActionPerformed(evt);
            }
        });
        txtTo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtToKeyTyped(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(22, 105, 122));
        jButton4.setFont(new java.awt.Font("Kohinoor Bangla", 3, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Reception/992651.png"))); // NOI18N
        jButton4.setText(" New Bill");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        tblBilling.setBackground(new java.awt.Color(237, 231, 227));
        tblBilling.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        tblBilling.setForeground(new java.awt.Color(20, 105, 122));
        tblBilling.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Patient Id", "Name", "App Id", "doctor id ", "Total", "Discount", "Net_Amount", "Payment", "status ", "created_at "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblBilling);

        total1.setBackground(new java.awt.Color(0, 0, 0));
        total1.setFont(new java.awt.Font("October Compressed Devanagari", 2, 18)); // NOI18N
        total1.setForeground(new java.awt.Color(72, 159, 181));
        total1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        total1.setText("Search");

        total2.setBackground(new java.awt.Color(0, 0, 0));
        total2.setFont(new java.awt.Font("October Compressed Devanagari", 2, 18)); // NOI18N
        total2.setForeground(new java.awt.Color(72, 159, 181));
        total2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        total2.setText("To");

        total3.setBackground(new java.awt.Color(0, 0, 0));
        total3.setFont(new java.awt.Font("October Compressed Devanagari", 2, 18)); // NOI18N
        total3.setForeground(new java.awt.Color(72, 159, 181));
        total3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        total3.setText("From");

        btnFilter.setBackground(new java.awt.Color(22, 105, 122));
        btnFilter.setFont(new java.awt.Font("Kohinoor Bangla", 3, 18)); // NOI18N
        btnFilter.setForeground(new java.awt.Color(255, 255, 255));
        btnFilter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Reception/search-icon-png-9.png"))); // NOI18N
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        jRadioButton1.setText("All");
        jRadioButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton1MouseClicked(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        jRadioButton2.setSelected(true);
        jRadioButton2.setText("Today");
        jRadioButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton2MouseClicked(evt);
            }
        });
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("October Compressed Devanagari", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(20, 105, 122));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Pending bills");

        total.setBackground(new java.awt.Color(0, 0, 0));
        total.setFont(new java.awt.Font("October Compressed Devanagari", 2, 18)); // NOI18N
        total.setForeground(new java.awt.Color(72, 159, 181));
        total.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        total.setText(" Total ");

        lblUnpaidCount.setFont(new java.awt.Font("Kohinoor Devanagari", 3, 36)); // NOI18N
        lblUnpaidCount.setForeground(new java.awt.Color(255, 166, 43));
        lblUnpaidCount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUnpaidCount.setText("-1 ");

        jButton5.setBackground(new java.awt.Color(22, 105, 122));
        jButton5.setFont(new java.awt.Font("Kohinoor Bangla", 3, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Reception/201934-200.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(22, 105, 122));
        jButton6.setFont(new java.awt.Font("Kohinoor Bangla", 0, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Mark as Paid");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(214, 214, 214)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(363, 363, 363)
                                        .addComponent(jButton5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel9))
                                    .addComponent(total, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(207, 207, 207)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblUnpaidCount, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(total1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(289, 289, 289)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(total3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(total2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtTo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnFilter))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(total1)
                    .addComponent(total2)
                    .addComponent(total3))
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(total)
                        .addGap(0, 0, 0)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addComponent(lblUnpaidCount, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        RecepDashboard dash = new RecepDashboard(userId,userName);
        dash.setLocationRelativeTo(this);
        dash.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
        
    }//GEN-LAST:event_txtSearchKeyTyped

    private void txtFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFromActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromActionPerformed

    private void txtFromKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFromKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromKeyTyped

    private void txtToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtToActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtToActionPerformed

    private void txtToKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtToKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtToKeyTyped

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       Recep_NewBill dash = new Recep_NewBill();
        dash.setLocationRelativeTo(this);
        dash.setVisible(true);
       
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        UnpaidTbl dash = new  UnpaidTbl();
        dash.setLocationRelativeTo(this);
        dash.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed

    String from = txtFrom.getText().trim();   // expected yyyy-MM-dd or empty
    String to   = txtTo.getText().trim();     // expected yyyy-MM-dd or empty

    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate fromDate = null, toDate = null;

    // ---------- Validate dates ----------
    try {
        if (!from.isEmpty()) fromDate = LocalDate.parse(from, df);
        if (!to.isEmpty())   toDate   = LocalDate.parse(to, df);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Invalid date format. Use yyyy-MM-dd (example: 2025-12-03).");
        return;
    }

    if (fromDate != null && toDate != null && fromDate.isAfter(toDate)) {
        JOptionPane.showMessageDialog(this, "'From' date cannot be after 'To' date.");
        return;
    }

    // ---------- Build SQL (date filters only) ----------
    String sql =
        "SELECT b.bill_id, b.patient_id, p.full_name AS patient_name, " +
        "       b.appointment_id, b.doctor_id, b.total_amount, b.discount, " +
        "       b.net_amount, b.payment_mode, b.status, b.created_at " +
        "FROM patient_billing b " +
        "JOIN patients p ON b.patient_id = p.patient_id " +
        "WHERE 1 = 1 ";

    if (fromDate != null) sql += " AND DATE(b.created_at) >= ? ";
    if (toDate   != null) sql += " AND DATE(b.created_at) <= ? ";

    sql += " ORDER BY b.created_at DESC LIMIT 1000"; // safe limit

    DefaultTableModel model = (DefaultTableModel) tblBilling.getModel();
    model.setRowCount(0); // clear table

    try (Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        int param = 1;
        if (fromDate != null) ps.setString(param++, fromDate.toString());
        if (toDate   != null) ps.setString(param++, toDate.toString());

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("bill_id"),
                    rs.getInt("patient_id"),
                    rs.getString("patient_name"),
                    rs.getObject("appointment_id") == null ? "" : rs.getInt("appointment_id"),
                    rs.getObject("doctor_id") == null ? "" : rs.getInt("doctor_id"),
                    rs.getBigDecimal("total_amount"),
                    rs.getBigDecimal("discount"),
                    rs.getBigDecimal("net_amount"),
                    rs.getString("payment_mode"),
                    rs.getString("status"),
                    rs.getTimestamp("created_at")
                };
                model.addRow(row);
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error filtering bills: " + e.getMessage());
    }

    // optional: re-adjust column widths after reloading
    try {
        adjustColumnWidths();
    } catch (Exception ignored) {}
    }//GEN-LAST:event_btnFilterActionPerformed

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed

    String keyword = txtSearch.getText().trim();   // 👈 fetch text from textfield

    DefaultTableModel model = (DefaultTableModel) tblBilling.getModel();
    model.setRowCount(0); // clear table before loading new results

    String sql =
        "SELECT b.bill_id, b.patient_id, p.full_name AS patient_name, " +
        "       b.appointment_id, b.doctor_id, b.total_amount, b.discount, " +
        "       b.net_amount, b.payment_mode, b.status, b.created_at " +
        "FROM patient_billing b " +
        "JOIN patients p ON b.patient_id = p.patient_id " +
        "WHERE p.full_name LIKE ? " +
        "   OR b.bill_id LIKE ? " +
        "   OR b.payment_mode LIKE ? " +
        "   OR b.status LIKE ? ";

    try (Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        String like = "%" + keyword + "%";

        ps.setString(1, like);
        ps.setString(2, like);
        ps.setString(3, like);
        ps.setString(4, like);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("bill_id"),
                    rs.getInt("patient_id"),
                    rs.getString("patient_name"),
                    rs.getInt("appointment_id"),
                    rs.getInt("doctor_id"),
                    rs.getDouble("total_amount"),
                    rs.getDouble("discount"),
                    rs.getDouble("net_amount"),
                    rs.getString("payment_mode"),
                    rs.getString("status"),
                    rs.getTimestamp("created_at")
                };
                model.addRow(row);
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    adjustColumnWidths(); 
    }//GEN-LAST:event_txtSearchKeyPressed

    private void jRadioButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton1MouseClicked
       loadBillingTable();
       fetchTodayRevenue();
    }//GEN-LAST:event_jRadioButton1MouseClicked

    private void jRadioButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton2MouseClicked
        loadBillingTable1();  
        fetchTodayRevenue1();
    }//GEN-LAST:event_jRadioButton2MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       int row = tblBilling.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Please select a bill from the table.");
        return;
    }

    // bill_id is column 0
    int billId = Integer.parseInt(tblBilling.getValueAt(row, 0).toString());

    String sql = "UPDATE patient_billing SET status = 'paid' WHERE bill_id = ?";

    try (Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, billId);

        int updated = ps.executeUpdate();

        if (updated > 0) {
            JOptionPane.showMessageDialog(this, "Bill marked as PAID!");

            // Refresh table + today's revenue + unpaid count
            loadBillingTable();      
            fetchTodayUnpaidBills();
            jRadioButton1.setSelected(true);
            jRadioButton2.setSelected(false);
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, 
            "Error updating bill status: " + e.getMessage());
    }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFilter;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblHeaderNm;
    private javax.swing.JLabel lblTotalRev;
    private javax.swing.JLabel lblUnpaidCount;
    private javax.swing.JTable tblBilling;
    private javax.swing.JLabel today;
    private javax.swing.JLabel total;
    private javax.swing.JLabel total1;
    private javax.swing.JLabel total2;
    private javax.swing.JLabel total3;
    private javax.swing.JTextField txtFrom;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTo;
    // End of variables declaration//GEN-END:variables
}
