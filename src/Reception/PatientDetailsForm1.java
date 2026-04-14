
package Reception;
import db.DBconnection;
import java.sql.*;
import javax.swing.JOptionPane;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class PatientDetailsForm1 extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PatientDetailsForm1.class.getName());
    
private int patientId;
    
   public PatientDetailsForm1(int patientId) {
    initComponents();
    setResizable(false);
    this.patientId = patientId;

    loadPatientDetails();
    HeaderLabel();
    loadAppointmentsForPatient();
}

public PatientDetailsForm1() {
       initComponents();
       HeaderLabel();
       loadPatientDetails();
       loadAppointmentsForPatient();
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
            txtCreated.setText(rs.getString("created_at"));
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
                rs.getString("doctor_name"),
                rs.getTimestamp("appointment_time"),
                rs.getString("status")
            });
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error loading appointments: " + e.getMessage());
        e.printStackTrace();
    }
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        lblHeaderNm = new javax.swing.JLabel();
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
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblHeaderNm9 = new javax.swing.JLabel();
        txtCreated = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(20, 105, 122));

        lblHeaderNm.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        lblHeaderNm.setForeground(new java.awt.Color(255, 166, 43));
        lblHeaderNm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeaderNm.setText("Patient");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(243, 243, 243)
                .addComponent(lblHeaderNm, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblHeaderNm, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(12, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))))
        );

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

        jTable1.setBackground(new java.awt.Color(237, 231, 227));
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
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable1);

        lblHeaderNm9.setFont(new java.awt.Font("October Compressed Devanagari", 0, 18)); // NOI18N
        lblHeaderNm9.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm9.setText("Created At");

        txtCreated.setBackground(new java.awt.Color(0, 0, 0));
        txtCreated.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        txtCreated.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtCreated.setText("Patient");

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
                                    .addComponent(txtAllergy, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                    .addComponent(lblHeaderNm9, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCreated, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBloodGroup1)
                    .addComponent(txtBloodGroup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHeaderNm9)
                .addGap(0, 0, 0)
                .addComponent(txtCreated, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(217, 217, 217))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 471, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(() -> new PatientDetailsForm1().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
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
    private javax.swing.JLabel txtCreated;
    private javax.swing.JLabel txtDOB;
    private javax.swing.JLabel txtEmail;
    private javax.swing.JLabel txtFullname;
    private javax.swing.JLabel txtGender;
    private javax.swing.JLabel txtPhone;
    // End of variables declaration//GEN-END:variables
}
