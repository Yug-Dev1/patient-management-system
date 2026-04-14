package Doctor;
import db.DBconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableCellRenderer;

public class PrescriptionOldView extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PrescriptionOldView.class.getName());
    private int ID;
    private int Doc_ID;

  
    
    public PrescriptionOldView(int ID,int Doc_ID) {
          initComponents();
        this.ID = ID;
        this.Doc_ID = Doc_ID;
       initHoverEffects1();
       loadPrescriptionTable();
       txtNotes.setEditable(false);
       tblMedicine.setEnabled(false);
        
         tblPrescription.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) { 
          int row = tblPrescription.getSelectedRow();
            if (row != -1) {
                int prescriptionId = (int) tblPrescription.getValueAt(row, 0);
                loadMedicinesAndNotes(prescriptionId);
            }
        }
    }
});
         adjustTableColumnWidths(tblPrescription);
    }
    
    private void adjustTableColumnWidths(JTable table) {

    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    for (int col = 0; col < table.getColumnCount(); col++) {

        int maxWidth = 50; // minimum width

        // ---------- Header width ----------
        TableColumn column = table.getColumnModel().getColumn(col);
        TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
        Component headerComp = headerRenderer.getTableCellRendererComponent(
                table, column.getHeaderValue(), false, false, 0, col
        );
        maxWidth = Math.max(maxWidth, headerComp.getPreferredSize().width);

        // ---------- Cell width ----------
        for (int row = 0; row < table.getRowCount(); row++) {
            TableCellRenderer cellRenderer = table.getCellRenderer(row, col);
            Component cellComp = table.prepareRenderer(cellRenderer, row, col);
            maxWidth = Math.max(maxWidth, cellComp.getPreferredSize().width);
        }

        column.setPreferredWidth(maxWidth + 10); // padding
    }
}
private void initHoverEffects1() {
    styleHoverButton1(jButton3);styleHoverButton1(jButton2);
    
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

public void loadPrescriptionTable() {

    DefaultTableModel model = (DefaultTableModel) tblPrescription.getModel();
    model.setRowCount(0); // clear table

    String sql = """
        SELECT prescription_id, patient_id, doctor_id, appointment_id, notes
        FROM prescriptions
        WHERE patient_id = ?
        ORDER BY created_at DESC
    """;

    try (Connection con = DBconnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, ID);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("prescription_id"),
                rs.getInt("patient_id"),
                rs.getInt("doctor_id"),
                rs.getInt("appointment_id"),
                rs.getString("notes")
            });
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Failed to load prescriptions");
        logger.severe(e.getMessage());
    }
}
private void loadMedicinesAndNotes(int prescriptionId) {

    // ---------- Clear previous data ----------
    DefaultTableModel medModel = (DefaultTableModel) tblMedicine.getModel();
    medModel.setRowCount(0);
    txtNotes.setText("");

    // ---------- Load medicines ----------
    String medSql = """
        SELECT medicine_name, dosage, frequency, duration, instructions
        FROM prescription_items
        WHERE prescription_id = ?
    """;

    try (Connection con = DBconnection.getConnection();
         PreparedStatement ps = con.prepareStatement(medSql)) {

        ps.setInt(1, prescriptionId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            medModel.addRow(new Object[]{
                rs.getString("medicine_name"),
                rs.getString("dosage"),
                rs.getString("frequency"),
                rs.getString("duration"),
                rs.getString("instructions")
            });
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Failed to load medicines");
        logger.severe(e.getMessage());
    }

    // ---------- Load notes ----------
    String noteSql = "SELECT notes FROM prescriptions WHERE prescription_id = ?";

    try (Connection con = DBconnection.getConnection();
         PreparedStatement ps = con.prepareStatement(noteSql)) {

        ps.setInt(1, prescriptionId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            txtNotes.setText(rs.getString("notes"));
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Failed to load notes");
        logger.severe(e.getMessage());
    }
}

private Integer fetchValidAppointmentId(int patientId, int doctorId) {

    String sql = """
        SELECT appointment_id
        FROM appointments
        WHERE patient_id = ?
          AND doctor_id = ?
          AND status IN ('scheduled', 'completed')
        ORDER BY appointment_time DESC
        LIMIT 1
    """;

    try (Connection con = DBconnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, patientId);
        ps.setInt(2, doctorId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("appointment_id");
        }

    } catch (Exception e) {
        logger.severe("Error fetching appointment: " + e.getMessage());
    }

    return null; // no valid appointment found
}

private String getDoctorNameById(int doctorId) {

    String doctorName = null;

    String sql = """
        SELECT name
        FROM user_credentials
        WHERE user_id = ? AND role = 'doctor'
    """;

    try (Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, doctorId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                doctorName = rs.getString("name");
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
                "Error fetching doctor name: " + e.getMessage());
    }

    return doctorName;
}

private Integer createDraftPrescription(int patientId, int doctorId) {

    Integer appointmentId = fetchValidAppointmentId(patientId, doctorId);
    if (appointmentId == null) return null;

    String sql = """
        INSERT INTO prescriptions (patient_id, doctor_id, appointment_id, finalized)
        VALUES (?, ?, ?, 0)
    """;

    try (Connection con = DBconnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

        ps.setInt(1, patientId);
        ps.setInt(2, doctorId);
        ps.setInt(3, appointmentId);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1); // ✅ prescription_id
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Failed to create prescription");
        logger.severe(e.getMessage());
    }

    return null;
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
        tblPrescription = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMedicine = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtNotes = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        lblHeaderNm1 = new javax.swing.JLabel();
        lblHeaderNm2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(20, 105, 122));

        lblHeaderNm.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        lblHeaderNm.setForeground(new java.awt.Color(255, 166, 43));
        lblHeaderNm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeaderNm.setText("Old Records");
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
                .addGap(158, 158, 158)
                .addComponent(lblHeaderNm, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        tblPrescription.setBackground(new java.awt.Color(237, 231, 227));
        tblPrescription.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        tblPrescription.setForeground(new java.awt.Color(20, 105, 122));
        tblPrescription.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Prescription ID", "Patient ID ", "Doctor ID", "Appointment ID", "Notes"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblPrescription);

        tblMedicine.setBackground(new java.awt.Color(237, 231, 227));
        tblMedicine.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        tblMedicine.setForeground(new java.awt.Color(20, 105, 122));
        tblMedicine.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Name", "Dosage", "Frequency", "Duration", "Instruction"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblMedicine);

        txtNotes.setBackground(new java.awt.Color(237, 231, 227));
        txtNotes.setColumns(20);
        txtNotes.setRows(5);
        jScrollPane3.setViewportView(txtNotes);

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 2, 13)); // NOI18N
        jLabel1.setText("(Double click any row to view records)");

        lblHeaderNm1.setFont(new java.awt.Font("October Compressed Devanagari", 2, 18)); // NOI18N
        lblHeaderNm1.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblHeaderNm1.setText("Notes ");
        lblHeaderNm1.setToolTipText("");

        lblHeaderNm2.setFont(new java.awt.Font("October Compressed Devanagari", 2, 18)); // NOI18N
        lblHeaderNm2.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblHeaderNm2.setText("Medicine ");
        lblHeaderNm2.setToolTipText("");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Reception/992651.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 847, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblHeaderNm2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(30, 30, 30)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblHeaderNm1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton2))
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHeaderNm1)
                    .addComponent(lblHeaderNm2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
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
  
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
String doctorName = getDoctorNameById(Doc_ID);

        PrescriptionSelection dash = new PrescriptionSelection(Doc_ID,doctorName);
        dash.setLocationRelativeTo(this);
        dash.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    Integer prescriptionId = createDraftPrescription(ID, Doc_ID);

if (prescriptionId != null) {
    NewPrescription dash =
        new NewPrescription(ID, Doc_ID, prescriptionId, this);
    dash.setLocationRelativeTo(this);
    dash.setVisible(true);
} else {
    JOptionPane.showMessageDialog(
        this,
        "No scheduled or completed appointment found for this patient.",
        "Cannot Create Prescription",
        JOptionPane.WARNING_MESSAGE
    );
}
    }//GEN-LAST:event_jButton2ActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler2;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblHeaderNm;
    private javax.swing.JLabel lblHeaderNm1;
    private javax.swing.JLabel lblHeaderNm2;
    private javax.swing.JTable tblMedicine;
    private javax.swing.JTable tblPrescription;
    private javax.swing.JTextArea txtNotes;
    // End of variables declaration//GEN-END:variables
}
