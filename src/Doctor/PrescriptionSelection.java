
package Doctor;
import db.DBconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PrescriptionSelection extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PrescriptionSelection.class.getName());
   private int docId;
   private String docNm;

public PrescriptionSelection(int docId,String docNm) {

    this.docId = docId;
    this.docNm = docNm;
    initComponents();         
   lblHeaderNm.setText(docNm+"'s Prescription Editor");
    setLocationRelativeTo(null);
    setResizable(false);

    addPlaceHolderFields();
    initHoverEffects1();
    loadPatientsTable();

    tblPatients.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (evt.getClickCount() == 2) {
                openSelectedPatient();
            }
        }
    });
}
    

    private void addPlaceHolderFields(){
        addPlaceholder(txtSearch, "Search (Name/Phone)");     
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
    
private void initHoverEffects1() {
    styleHoverButton1(jButton3);
}
private void styleHoverButton1(javax.swing.JButton btn) {
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
    
private void loadPatientsTable() {
    String sql = "SELECT patient_id, full_name, gender, dob, phone, email, " +
                 "updated_at, created_at " +
                 "FROM patients ORDER BY created_at DESC";
    adjustColumnWidths();
    try (Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        DefaultTableModel model = (DefaultTableModel) tblPatients.getModel();
        model.setRowCount(0);  // clear table

        while (rs.next()) {
            model.addRow(new Object[]{
                    rs.getInt("patient_id"),
                    rs.getString("full_name"),
                    rs.getString("gender"),
                    rs.getDate("dob"),
                    rs.getString("phone"),
                    rs.getString("email")
            });
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
            "Error loading patients: " + e.getMessage());
    }
}
private void adjustColumnWidths() {
    var columnModel = tblPatients.getColumnModel();

    int[] widths = {50, 165, 90, 150, 150, 250};

    for (int i = 0; i < widths.length; i++) {
        columnModel.getColumn(i).setPreferredWidth(widths[i]);
    }

    // Prevent user from resizing too badly (optional)
    tblPatients.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
}

private void openSelectedPatient() {

    int row = tblPatients.getSelectedRow();
    if (row == -1) return;

    int modelRow = tblPatients.convertRowIndexToModel(row);
    int patientId = (int) tblPatients.getModel().getValueAt(modelRow, 0);

    PrescriptionOldView detail =
            new PrescriptionOldView(patientId, docId);

    detail.setLocationRelativeTo(this);
    detail.setVisible(true);
    this.dispose();
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        lblHeaderNm = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPatients = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(20, 105, 122));

        lblHeaderNm.setFont(new java.awt.Font("October Compressed Devanagari", 3, 36)); // NOI18N
        lblHeaderNm.setForeground(new java.awt.Color(255, 166, 43));
        lblHeaderNm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeaderNm.setText("Prescription");
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
                .addContainerGap(641, Short.MAX_VALUE)
                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(276, 276, 276))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jButton3)
                .addGap(43, 43, 43)
                .addComponent(lblHeaderNm, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                    .addComponent(lblHeaderNm))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.setBackground(new java.awt.Color(237, 231, 227));

        tblPatients.setBackground(new java.awt.Color(237, 231, 227));
        tblPatients.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        tblPatients.setForeground(new java.awt.Color(20, 105, 122));
        tblPatients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Patient ID ", "Full Name", "Gender", "DOB", "Phone", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblPatients);

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 2, 13)); // NOI18N
        jLabel1.setText("(Double click any row to view old prescription)");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtSearch, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 847, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(17, Short.MAX_VALUE))
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
private String getDoctorNameById(int doctorId) {

    String doctorName = null;

    String sql = """
        SELECT full_name
        FROM users
        WHERE user_id = ? AND role = 'doctor'
    """;

    try (Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, doctorId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                doctorName = rs.getString("full_name");
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
                "Error fetching doctor name: " + e.getMessage());
    }

    return doctorName;
}
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        DoctorDashboard dash = new DoctorDashboard(docId,docNm);
        dash.setLocationRelativeTo(this);
        dash.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed

        String keyword = txtSearch.getText().trim();

        String sql = "SELECT patient_id, full_name, gender, dob, phone, email, " +
        "updated_at, created_at " +
        "FROM patients " +
        "WHERE full_name LIKE ? OR phone LIKE ? " +
        "ORDER BY created_at DESC";

        try (Connection conn = DBconnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            String likeValue = "%" + keyword + "%";
            ps.setString(1, likeValue);
            ps.setString(2, likeValue);

            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) tblPatients.getModel();
            model.setRowCount(0); // clear table

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("patient_id"),
                    rs.getString("full_name"),
                    rs.getString("gender"),
                    rs.getDate("dob"),
                    rs.getString("phone"),
                    rs.getString("email")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
        String keyword = txtSearch.getText().trim();

        String sql = "SELECT patient_id, full_name, gender, dob, phone, email, " +
        "updated_at, created_at " +
        "FROM patients " +
        "WHERE full_name LIKE ? OR phone LIKE ? " +
        "ORDER BY created_at DESC";

        try (Connection conn = DBconnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            String likeValue = "%" + keyword + "%";
            ps.setString(1, likeValue);
            ps.setString(2, likeValue);

            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) tblPatients.getModel();
            model.setRowCount(0); // clear table

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("patient_id"),
                    rs.getString("full_name"),
                    rs.getString("gender"),
                    rs.getDate("dob"),
                    rs.getString("phone"),
                    rs.getString("email")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_txtSearchKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblHeaderNm;
    private javax.swing.JTable tblPatients;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
