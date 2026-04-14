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

public class NewPrescription extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(NewPrescription.class.getName());
private int ID;
private int Doc_ID;
private int prescriptionId;
private boolean isFinalized = false;
private PrescriptionOldView parentView;
   
    public NewPrescription(int ID,int Doc_ID,int prescriptionId, PrescriptionOldView parentView) {
      initComponents();
        this.ID = ID;
        this.Doc_ID = Doc_ID;
        this.prescriptionId = prescriptionId;
        initHoverEffects1();
        loadPatientInfo();
        System.out.println("Prescription ID = " + prescriptionId);
        tblMedicine.getColumnModel().getColumn(0).setMinWidth(0);
tblMedicine.getColumnModel().getColumn(0).setMaxWidth(0);
tblMedicine.getColumnModel().getColumn(0).setWidth(0);
        
        tblMedicine.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {

            int row = tblMedicine.getSelectedRow();
            if (row == -1) return;

            int itemId = (int) tblMedicine.getValueAt(row, 0);
            deleteMedicineItem(itemId);

            ((DefaultTableModel) tblMedicine.getModel()).removeRow(row);
        }
    }
});
         
      addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosing(java.awt.event.WindowEvent e) {
        if (!isFinalized) {
            deleteDraftPrescription();
            System.out.println("No Changes are saved");
        }
    }
});
    }
    
    private void initHoverEffects1() {
    styleHoverButton1(jButton6);
     styleHoverButton1(jButton7);
    
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

    private void deleteDraftPrescription() {
    try (Connection con = DBconnection.getConnection();
         PreparedStatement ps =
             con.prepareStatement(
                 "DELETE FROM prescriptions WHERE prescription_id = ?"
             )) {

        ps.setInt(1, prescriptionId);
        ps.executeUpdate();

    } catch (Exception e) {
        logger.warning("Failed to delete draft prescription: " + e.getMessage());
    }
}
    
    
    public void loadMedicineTable() {

    DefaultTableModel model = (DefaultTableModel) tblMedicine.getModel();
    model.setRowCount(0); // clear table

    String sql = """
        SELECT prescription_item_id,
               medicine_name,
               dosage,
               frequency,
               duration,
               instructions
        FROM prescription_items
        WHERE prescription_id = ?
        ORDER BY prescription_item_id DESC
    """;

    try (Connection con = DBconnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, prescriptionId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("prescription_item_id"), // 🔑 ID column
                rs.getString("medicine_name"),
                rs.getString("dosage"),
                rs.getString("frequency"),
                rs.getString("duration"),
                rs.getString("instructions")
            });
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to load medicines");
    }
}
      
    private void loadPatientInfo() {

    String sql = """
        SELECT full_name, dob, blood_group, allergy
        FROM patients
        WHERE patient_id = ?
    """;

    try (Connection con = DBconnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, ID);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            txtFullname.setText(rs.getString("full_name"));
            txtDOB.setText(rs.getString("dob"));
            txtBloodGroup.setText(rs.getString("blood_group"));
            txtAllergy.setText(rs.getString("allergy"));
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to load patient info");
    }
}
    
    private void deleteMedicineItem(int itemId) {

    String sql = "DELETE FROM prescription_items WHERE prescription_item_id = ?";

    try (Connection con = DBconnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, itemId);
        ps.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to delete medicine");
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        lblHeaderNm1 = new javax.swing.JLabel();
        txtFullname = new javax.swing.JLabel();
        lblHeaderNm3 = new javax.swing.JLabel();
        txtDOB = new javax.swing.JLabel();
        lblHeaderNm4 = new javax.swing.JLabel();
        txtBloodGroup = new javax.swing.JLabel();
        lblHeaderNm7 = new javax.swing.JLabel();
        txtAllergy = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMedicine = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(20, 105, 122));
        jPanel2.setForeground(new java.awt.Color(20, 105, 122));
        jPanel2.setToolTipText("");

        lblHeaderNm1.setFont(new java.awt.Font("October Compressed Devanagari", 0, 18)); // NOI18N
        lblHeaderNm1.setForeground(new java.awt.Color(72, 159, 181));
        lblHeaderNm1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm1.setText("Name");

        txtFullname.setBackground(new java.awt.Color(237, 231, 227));
        txtFullname.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        txtFullname.setForeground(new java.awt.Color(237, 231, 227));
        txtFullname.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtFullname.setText("Patient");

        lblHeaderNm3.setFont(new java.awt.Font("October Compressed Devanagari", 0, 18)); // NOI18N
        lblHeaderNm3.setForeground(new java.awt.Color(72, 159, 181));
        lblHeaderNm3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm3.setText("DOB");

        txtDOB.setBackground(new java.awt.Color(0, 0, 0));
        txtDOB.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        txtDOB.setForeground(new java.awt.Color(237, 231, 227));
        txtDOB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtDOB.setText("Patient");

        lblHeaderNm4.setFont(new java.awt.Font("October Compressed Devanagari", 0, 18)); // NOI18N
        lblHeaderNm4.setForeground(new java.awt.Color(72, 159, 181));
        lblHeaderNm4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm4.setText("Blood Grp");

        txtBloodGroup.setBackground(new java.awt.Color(0, 0, 0));
        txtBloodGroup.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        txtBloodGroup.setForeground(new java.awt.Color(237, 231, 227));
        txtBloodGroup.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtBloodGroup.setText("Patient");

        lblHeaderNm7.setFont(new java.awt.Font("October Compressed Devanagari", 0, 18)); // NOI18N
        lblHeaderNm7.setForeground(new java.awt.Color(72, 159, 181));
        lblHeaderNm7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm7.setText("Allergy");

        txtAllergy.setBackground(new java.awt.Color(0, 0, 0));
        txtAllergy.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        txtAllergy.setForeground(new java.awt.Color(237, 231, 227));
        txtAllergy.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtAllergy.setText("Patient");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(276, 276, 276))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblHeaderNm1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(184, 184, 184)
                        .addComponent(lblHeaderNm4, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblHeaderNm3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(58, 58, 58)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAllergy, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBloodGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblHeaderNm7, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHeaderNm1)
                    .addComponent(lblHeaderNm4))
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBloodGroup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHeaderNm3)
                    .addComponent(lblHeaderNm7))
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAllergy))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.setBackground(new java.awt.Color(237, 231, 227));

        tblMedicine.setBackground(new java.awt.Color(237, 231, 227));
        tblMedicine.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        tblMedicine.setForeground(new java.awt.Color(20, 105, 122));
        tblMedicine.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Dosage", "Frequency", "Duration", "Instruction"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblMedicine);

        jButton6.setBackground(new java.awt.Color(22, 105, 122));
        jButton6.setFont(new java.awt.Font("Kohinoor Bangla", 2, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Reception/992651.png"))); // NOI18N
        jButton6.setText("New Item");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 2, 13)); // NOI18N
        jLabel1.setText("(Double click any row to delete)");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton7.setBackground(new java.awt.Color(22, 105, 122));
        jButton7.setFont(new java.awt.Font("Kohinoor Bangla", 3, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Doctor/black-checkmark-png-4.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton7)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jButton6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jLabel1))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
    PreNewMed itemFrame = new PreNewMed(prescriptionId, this);
    itemFrame.setLocationRelativeTo(this);
    itemFrame.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

    String notes = jTextArea1.getText().trim();

    if (notes.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Notes cannot be empty");
        return;
    }

    String sql = """
        UPDATE prescriptions
        SET notes = ?, finalized = 1
        WHERE prescription_id = ?
    """;

    try (Connection con = DBconnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, notes);
        ps.setInt(2, prescriptionId);
        ps.executeUpdate();

        isFinalized = true;
        if (parentView != null) {
    parentView.loadPrescriptionTable();
}
        JOptionPane.showMessageDialog(this, "Prescription saved");
        dispose();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Failed to finalize prescription");
    }       
    }//GEN-LAST:event_jButton7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler2;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblHeaderNm1;
    private javax.swing.JLabel lblHeaderNm3;
    private javax.swing.JLabel lblHeaderNm4;
    private javax.swing.JLabel lblHeaderNm7;
    private javax.swing.JTable tblMedicine;
    private javax.swing.JLabel txtAllergy;
    private javax.swing.JLabel txtBloodGroup;
    private javax.swing.JLabel txtDOB;
    private javax.swing.JLabel txtFullname;
    // End of variables declaration//GEN-END:variables
}
