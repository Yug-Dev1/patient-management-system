
package Doctor;
import db.DBconnection;
import java.sql.*;
import javax.swing.JOptionPane;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class PatientDetailsForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PatientDetailsForm.class.getName());

    private int patientId,userId;
    private String userName;

public PatientDetailsForm(int patientId,int userId,String userName) {
    initComponents();
    setResizable(false);
    this.patientId = patientId;
    this.userId = userId;
    this.userName = userName;
    loadPatientDetails();
    HeaderLabel();
    initHoverEffects1();
    loadAppointmentsForPatient();
}

public PatientDetailsForm() {
       initComponents();
       HeaderLabel();
       loadPatientDetails();
       initHoverEffects1();
       loadAppointmentsForPatient();
}

private void initHoverEffects1() {
    styleHoverButton1(jButton3);
    styleHoverButton1(jButton1);
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

private void loadPatientDetails() {
    String sql = "SELECT *" +
                 "FROM patients WHERE patient_id = ?";

    try (Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, patientId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            txtFullname.setText(rs.getString("full_name"));
            txtGender.setText(rs.getString("gender"));
            txtDOB.setText(String.valueOf(rs.getDate("dob")));
            txtPhone.setText(rs.getString("phone"));
            txtEmail.setText(rs.getString("email"));
            txtBloodGroup1.setText(rs.getString("address"));
            txtBloodGroup.setText(rs.getString("blood_group"));
            txtAllergy.setText(rs.getString("allergy"));
            jTextArea1.setText(rs.getString("personal_notes"));
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}
   
private void HeaderLabel() {
    String name = null;
    String sql="select full_name from patients where patient_id=?";
    
    try(Connection conn=DBconnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, patientId); 
        try (ResultSet rs = ps.executeQuery()) {
         if (rs.next()) {
                name = rs.getString("full_name");
                lblHeaderNm.setText(patientId+" - "+name);
            }
        }
    }catch (Exception e) {
        e.printStackTrace();
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        txtFullname = new javax.swing.JLabel();
        lblHeaderNm1 = new javax.swing.JLabel();
        lblHeaderNm2 = new javax.swing.JLabel();
        txtGender = new javax.swing.JLabel();
        txtDOB = new javax.swing.JLabel();
        lblHeaderNm3 = new javax.swing.JLabel();
        lblHeaderNm4 = new javax.swing.JLabel();
        txtBloodGroup = new javax.swing.JLabel();
        lblHeaderNm5 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JLabel();
        lblHeaderNm6 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JLabel();
        lblHeaderNm7 = new javax.swing.JLabel();
        txtAllergy = new javax.swing.JLabel();
        lblHeaderNm8 = new javax.swing.JLabel();
        txtBloodGroup1 = new javax.swing.JLabel();
        lblHeaderNm9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jButton3 = new javax.swing.JButton();
        lblHeaderNm = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(237, 231, 227));

        jPanel1.setBackground(new java.awt.Color(237, 231, 227));

        txtFullname.setBackground(new java.awt.Color(0, 0, 0));
        txtFullname.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        txtFullname.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtFullname.setText("Patient");

        lblHeaderNm1.setFont(new java.awt.Font("October Compressed Devanagari", 0, 18)); // NOI18N
        lblHeaderNm1.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm1.setText("Name");

        lblHeaderNm2.setFont(new java.awt.Font("October Compressed Devanagari", 0, 18)); // NOI18N
        lblHeaderNm2.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm2.setText("Gender");

        txtGender.setBackground(new java.awt.Color(0, 0, 0));
        txtGender.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        txtGender.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtGender.setText("Patient");

        txtDOB.setBackground(new java.awt.Color(0, 0, 0));
        txtDOB.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        txtDOB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtDOB.setText("Patient");

        lblHeaderNm3.setFont(new java.awt.Font("October Compressed Devanagari", 0, 18)); // NOI18N
        lblHeaderNm3.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm3.setText("DOB");

        lblHeaderNm4.setFont(new java.awt.Font("October Compressed Devanagari", 0, 18)); // NOI18N
        lblHeaderNm4.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm4.setText("Blood Grp");

        txtBloodGroup.setBackground(new java.awt.Color(0, 0, 0));
        txtBloodGroup.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        txtBloodGroup.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtBloodGroup.setText("Patient");

        lblHeaderNm5.setFont(new java.awt.Font("October Compressed Devanagari", 0, 18)); // NOI18N
        lblHeaderNm5.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm5.setText("Phone");

        txtPhone.setBackground(new java.awt.Color(0, 0, 0));
        txtPhone.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        txtPhone.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtPhone.setText("Patient");

        lblHeaderNm6.setFont(new java.awt.Font("October Compressed Devanagari", 0, 18)); // NOI18N
        lblHeaderNm6.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm6.setText("Email");

        txtEmail.setBackground(new java.awt.Color(0, 0, 0));
        txtEmail.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        txtEmail.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtEmail.setText("Patient");

        lblHeaderNm7.setFont(new java.awt.Font("October Compressed Devanagari", 0, 18)); // NOI18N
        lblHeaderNm7.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm7.setText("Allergy");

        txtAllergy.setBackground(new java.awt.Color(0, 0, 0));
        txtAllergy.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        txtAllergy.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtAllergy.setText("Patient");

        lblHeaderNm8.setFont(new java.awt.Font("October Compressed Devanagari", 0, 18)); // NOI18N
        lblHeaderNm8.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm8.setText("Address");

        txtBloodGroup1.setBackground(new java.awt.Color(0, 0, 0));
        txtBloodGroup1.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        txtBloodGroup1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtBloodGroup1.setText("Patient");

        lblHeaderNm9.setFont(new java.awt.Font("October Compressed Devanagari", 0, 18)); // NOI18N
        lblHeaderNm9.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm9.setText("Personal Notes");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Doctor/black-checkmark-png-4.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jTable1.setForeground(new java.awt.Color(20, 105, 122));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Appointment ID", "DocName", "Visits", "Status"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtBloodGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(txtBloodGroup1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblHeaderNm7, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtAllergy, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblHeaderNm9)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblHeaderNm4, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(163, 163, 163)
                                .addComponent(lblHeaderNm8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblHeaderNm2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblHeaderNm1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtGender, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblHeaderNm3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(39, 39, 39)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblHeaderNm6, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblHeaderNm5, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(412, 412, 412)
                .addComponent(jButton1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHeaderNm1)
                    .addComponent(lblHeaderNm5))
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHeaderNm2)
                    .addComponent(lblHeaderNm6))
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGender, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHeaderNm3)
                    .addComponent(lblHeaderNm7))
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAllergy))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHeaderNm4)
                    .addComponent(lblHeaderNm8))
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBloodGroup1)
                            .addComponent(txtBloodGroup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(40, 40, 40)
                        .addComponent(lblHeaderNm9))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel3.setBackground(new java.awt.Color(20, 105, 122));

        jButton3.setBackground(new java.awt.Color(22, 105, 122));
        jButton3.setFont(new java.awt.Font("Kohinoor Bangla", 3, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("< Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        lblHeaderNm.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        lblHeaderNm.setForeground(new java.awt.Color(255, 166, 43));
        lblHeaderNm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeaderNm.setText("Patient");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jButton3)
                .addGap(149, 149, 149)
                .addComponent(lblHeaderNm, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 281, Short.MAX_VALUE)
                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblHeaderNm, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // Go back to main doctor dashboard
        Doc_Patient dash = new Doc_Patient(userId,userName);
        dash.setLocationRelativeTo(this);
        dash.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    String notes = jTextArea1.getText().trim();

    String sql = "UPDATE patients SET personal_notes = ? WHERE patient_id = ?";

    try (Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, notes.isEmpty() ? null : notes);  // if empty, store NULL
        ps.setInt(2, patientId);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            JOptionPane.showMessageDialog(this, "Notes saved successfully.");
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error saving notes: " + e.getMessage());
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void loadAppointmentsForPatient() {

    String sql = 
        "SELECT a.appointment_id, a.doctor_id, u.name AS doctor_name, " +
        "a.appointment_time, a.type, a.source, a.status " +
        "FROM appointments a " +
        "LEFT JOIN user_credentials u ON a.doctor_id = u.user_id " +
        "WHERE a.patient_id = ? " +
        "ORDER BY a.appointment_time DESC";

    try (Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, patientId);

        ResultSet rs = ps.executeQuery();

        DefaultTableModel model =
                (DefaultTableModel) jTable1.getModel();

        model.setRowCount(0); // clear table

        while (rs.next()) {
            model.addRow(new Object[] {
                rs.getInt("appointment_id"),
                rs.getInt("doctor_id"),
                rs.getString("doctor_name"),
                rs.getTimestamp("appointment_time"),
                rs.getString("type"),
                rs.getString("source"),
                rs.getString("status")
            });
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error loading appointments: " + e.getMessage());
        e.printStackTrace();
    }
}
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblHeaderNm;
    private javax.swing.JLabel lblHeaderNm1;
    private javax.swing.JLabel lblHeaderNm2;
    private javax.swing.JLabel lblHeaderNm3;
    private javax.swing.JLabel lblHeaderNm4;
    private javax.swing.JLabel lblHeaderNm5;
    private javax.swing.JLabel lblHeaderNm6;
    private javax.swing.JLabel lblHeaderNm7;
    private javax.swing.JLabel lblHeaderNm8;
    private javax.swing.JLabel lblHeaderNm9;
    private javax.swing.JLabel txtAllergy;
    private javax.swing.JLabel txtBloodGroup;
    private javax.swing.JLabel txtBloodGroup1;
    private javax.swing.JLabel txtDOB;
    private javax.swing.JLabel txtEmail;
    private javax.swing.JLabel txtFullname;
    private javax.swing.JLabel txtGender;
    private javax.swing.JLabel txtPhone;
    // End of variables declaration//GEN-END:variables
}
