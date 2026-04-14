
package Reception;
import db.DBconnection;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;


public class Recep_NewAppointment extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Recep_NewAppointment.class.getName());
    private int selectedPatientId = -1;
    private static class ComboItem {
    private final int id;
    private final String label;
    public ComboItem(int id, String label) { this.id = id; this.label = label; }
    public int getId() { return id; }
    @Override public String toString() { return label; } // JComboBox will show label only
    }
    private int userId;
    private String userName;
    
    public Recep_NewAppointment(int userId, String name) {
    
    initComponents();
    this.userId = userId;
    this.userName = name;
    setLocationRelativeTo(null);
    initHoverEffects();initHoverEffects1();
    addPlaceHolderFields();
    loadDoctorsIntoCombo();
    txtPatientName.setEditable(false);
    txtPatientPhone.setEditable(false);
      txtPatientDob.setEditable(false);
        txtPatientID.setEditable(false);
  }
    
    private void addPlaceHolderFields(){
        addPlaceholder(txtSearchPhone, "Enter Existing Number");
        addPlaceholder(txtPatientID, "PatientID displayed here");
        addPlaceholder(txtPatientName, "Name");
        addPlaceholder(txtPatientDob, "D.O.B");
        addPlaceholder(txtPatientPhone, "Phone");
        addPlaceholder(txtAppDate, "yyyy-MM-dd");  
        addPlaceholder(txtAppTime, "HH:mm");        
 
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
    styleHoverButton1(btnAddNew);
    styleHoverButton1(btnSearch);
    styleHoverButton1(jButton4);
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
    
    private boolean canScheduleAppointment(
        int doctorId,
        String appointmentType,   // "slot" or "direct"
        LocalDate appointmentDate) {

    int limit = appointmentType.equalsIgnoreCase("slot") ? 10 : 15;

    String sql = """
        SELECT COUNT(*) 
        FROM appointments
        WHERE doctor_id = ?
          AND type = ?
          AND DATE(appointment_time) = ?
    """;

    try (Connection con = DBconnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, doctorId);
        ps.setString(2, appointmentType);
        ps.setDate(3, java.sql.Date.valueOf(appointmentDate));

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int count = rs.getInt(1);
            return count < limit;   // ✅ allowed or not
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error checking appointment limit.");
        e.printStackTrace();
    }

    return false;
}
    
    private boolean checkAppointmentLimit(int doctorId) {
    String appointmentType;
    LocalDate appointmentDate;

    if (jRadioButton1.isSelected()) {
        appointmentType = "direct";
        appointmentDate = LocalDate.parse(txtAppDate.getText());
    } else {
        appointmentType = "slot";
      String dateText = txtAppDate.getText();
if (dateText.equals("yyyy-MM-dd")) {
    JOptionPane.showMessageDialog(this, "Enter appointment date.");
    return false;
}
appointmentDate = LocalDate.parse(dateText);
    }

    if (!canScheduleAppointment(doctorId, appointmentType, appointmentDate)) {
        int max = appointmentType.equals("slot") ? 10 : 15;
        JOptionPane.showMessageDialog(
            this,
            "Cannot schedule more than " + max + " " + appointmentType + " appointments for this doctor on this date."
        );
        return false;
    }
    return true;
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        typeGroup = new javax.swing.ButtonGroup();
        sourceGroup = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jButton3 = new javax.swing.JButton();
        lblHeaderNm2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnAddNew = new javax.swing.JButton();
        txtSearchPhone = new javax.swing.JTextField();
        txtPatientName = new javax.swing.JTextField();
        txtPatientDob = new javax.swing.JTextField();
        txtPatientPhone = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtPatientID = new javax.swing.JTextField();
        txtAppTime = new javax.swing.JTextField();
        comboDoctor = new javax.swing.JComboBox<>();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        txtAppDate = new javax.swing.JTextField();
        lblHeaderNm = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(20, 105, 122));

        jButton3.setBackground(new java.awt.Color(22, 105, 122));
        jButton3.setFont(new java.awt.Font("Kohinoor Bangla", 3, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("< Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        lblHeaderNm2.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        lblHeaderNm2.setForeground(new java.awt.Color(255, 166, 43));
        lblHeaderNm2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeaderNm2.setText("New Appointment");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jButton3)
                .addGap(151, 151, 151)
                .addComponent(lblHeaderNm2, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblHeaderNm2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))))
        );

        jPanel1.setBackground(new java.awt.Color(237, 231, 227));

        btnAddNew.setFont(new java.awt.Font("Kohinoor Bangla", 0, 12)); // NOI18N
        btnAddNew.setText("Add New Patient");
        btnAddNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewActionPerformed(evt);
            }
        });

        txtPatientDob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPatientDobActionPerformed(evt);
            }
        });

        txtPatientPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPatientPhoneActionPerformed(evt);
            }
        });

        btnSearch.setFont(new java.awt.Font("Kohinoor Bangla", 0, 12)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel1.setText("|");

        jPanel3.setBackground(new java.awt.Color(237, 231, 227));

        txtPatientID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPatientIDActionPerformed(evt);
            }
        });

        txtAppTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAppTimeActionPerformed(evt);
            }
        });

        comboDoctor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        typeGroup.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Kohinoor Bangla", 0, 16)); // NOI18N
        jRadioButton1.setText("Direct");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        typeGroup.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Kohinoor Bangla", 0, 16)); // NOI18N
        jRadioButton2.setSelected(true);
        jRadioButton2.setText(" Slot");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        sourceGroup.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Kohinoor Bangla", 0, 16)); // NOI18N
        jRadioButton3.setText("Walk-in");

        sourceGroup.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("Kohinoor Bangla", 0, 16)); // NOI18N
        jRadioButton4.setText("Referral");

        sourceGroup.add(jRadioButton5);
        jRadioButton5.setFont(new java.awt.Font("Kohinoor Bangla", 0, 16)); // NOI18N
        jRadioButton5.setText("Ads");

        sourceGroup.add(jRadioButton6);
        jRadioButton6.setFont(new java.awt.Font("Kohinoor Bangla", 0, 16)); // NOI18N
        jRadioButton6.setText("Other");

        sourceGroup.add(jRadioButton7);
        jRadioButton7.setFont(new java.awt.Font("Kohinoor Bangla", 0, 16)); // NOI18N
        jRadioButton7.setText("Online");

        lblHeaderNm.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        lblHeaderNm.setForeground(new java.awt.Color(22, 105, 122));
        lblHeaderNm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeaderNm.setText("Appointment type");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHeaderNm)
                    .addComponent(txtAppDate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPatientID, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton3)
                            .addComponent(jRadioButton7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jRadioButton4)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton5))
                            .addComponent(jRadioButton6)))
                    .addComponent(txtAppTime, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jRadioButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton2)))
                .addGap(0, 195, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(comboDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtPatientID, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblHeaderNm)
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addGap(12, 12, 12)
                .addComponent(txtAppTime, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAppDate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4)
                    .addComponent(jRadioButton5))
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton6)
                    .addComponent(jRadioButton7))
                .addContainerGap())
        );

        jButton4.setBackground(new java.awt.Color(22, 105, 122));
        jButton4.setFont(new java.awt.Font("Kohinoor Bangla", 3, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Schedule Appointment");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPatientDob, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPatientName, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPatientPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSearchPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSearch)
                        .addGap(55, 55, 55)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btnAddNew)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 945, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddNew, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtPatientName, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtPatientDob, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtPatientPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(102, 102, 102))))
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
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // Go back to main doctor dashboard
        Recep_Appointment dash = new Recep_Appointment(userId, userName);
        dash.setLocationRelativeTo(this);
        dash.setVisible(true);
       if (Recep_Appointment.INSTANCE != null) {
    Recep_Appointment.INSTANCE.table();
    Recep_Appointment.INSTANCE.No_appointment();
    Recep_Appointment.INSTANCE.setVisible(true); // optional: bring main frame back
    } else {
    Recep_Appointment ra = new Recep_Appointment();
    ra.setLocationRelativeTo(null);
    ra.setVisible(true);
}
        this.dispose();
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnAddNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewActionPerformed
 AddNew_Patient dash = new AddNew_Patient();
        dash.setLocationRelativeTo(this);
        dash.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnAddNewActionPerformed

    private void searchPatientByPhone() {
    String phone = txtSearchPhone.getText().trim();
    
    // Check for placeholder text
    if (phone.isEmpty() || phone.equals("Enter Existing Number")) {
        JOptionPane.showMessageDialog(this, "Enter phone number first!");
        return;
    }

    String sql = "SELECT patient_id, full_name, dob, phone FROM patients WHERE phone = ?";

    try (Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        if (conn == null || conn.isClosed()) {
            JOptionPane.showMessageDialog(this, "Database connection is not available. Try again.");
            return;
        }
        
        ps.setString(1, phone);
        
        // Execute query and process results within the try block
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                selectedPatientId = rs.getInt("patient_id");
                
                txtPatientName.setText(rs.getString("full_name"));
                txtPatientDob.setText(rs.getDate("dob") != null ? rs.getDate("dob").toString() : "");
                txtPatientPhone.setText(rs.getString("phone"));

                txtPatientName.setEditable(false);
                txtPatientDob.setEditable(false);
                txtPatientPhone.setEditable(false);
                
                // Fix: Use the actual ID value, not the string "selectedPatientId"
                txtPatientID.setText(String.valueOf(selectedPatientId));
                txtPatientID.setEditable(false);
            
            } else {
                JOptionPane.showMessageDialog(this, "No patient found. Click 'Add New Patient'");
            }
        }

    } catch (SQLException e) {
        logger.log(java.util.logging.Level.SEVERE, "Database error during patient search", e);
        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
    } catch (Exception e) {
        logger.log(java.util.logging.Level.SEVERE, "Unexpected error during patient search", e);
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}
    
    private void loadDoctorsIntoCombo() {
    // Ensure the combo uses a raw DefaultComboBoxModel so we can store ComboItem objects
    comboDoctor.setModel(new javax.swing.DefaultComboBoxModel()); // raw model (no generics)

    String sql = "SELECT user_id, name FROM user_credentials WHERE role = 'doctor' AND status <> 'disabled' ORDER BY name";
    try (Connection conn = db.DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        // use raw DefaultComboBoxModel to avoid generics mismatch
        javax.swing.DefaultComboBoxModel model = (javax.swing.DefaultComboBoxModel) comboDoctor.getModel();

        // clear any existing elements just in case
        model.removeAllElements();

        while (rs.next()) {
            // add ComboItem instances (model accepts Object because it's raw)
            model.addElement(new ComboItem(rs.getInt("user_id"), rs.getString("name")));
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading doctors: " + ex.getMessage());
    }
}
    
    private int getSelectedDoctorId() {
    Object sel = comboDoctor.getSelectedItem();
    if (sel instanceof ComboItem) {
        return ((ComboItem) sel).getId();
    }
    return -1;
}
    
    private String getSelectedType() {
    if (jRadioButton1.isSelected()) return "direct";
    if (jRadioButton2.isSelected()) return "slot";
    return null;
}
    private String getSelectedSource() {
    if (jRadioButton3.isSelected()) return "walk-in";
    if (jRadioButton4.isSelected()) return "referral";
    if (jRadioButton5.isSelected()) return "ads";
    if (jRadioButton6.isSelected()) return "online";
    if (jRadioButton7.isSelected()) return "other";
    return null;
}
    
  private Timestamp getAppointmentDateTime() {
    try {
        if (jRadioButton1.isSelected()) { // Direct
            return Timestamp.valueOf(
                LocalDate.parse(txtAppDate.getText()).atTime(9, 0)
            );
        }

        String date = txtAppDate.getText().trim();
        String time = txtAppTime.getText().trim();

        if (date.equals("yyyy-MM-dd") || time.equals("HH:mm")) {
            JOptionPane.showMessageDialog(this, "Enter valid date and time.");
            return null;
        }

        return Timestamp.valueOf(date + " " + time + ":00");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Invalid date or time format.");
        return null;
    }
}  
    private void txtPatientDobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPatientDobActionPerformed
           
    }//GEN-LAST:event_txtPatientDobActionPerformed

    private void txtPatientPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPatientPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPatientPhoneActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
       searchPatientByPhone();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtPatientIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPatientIDActionPerformed
      
    }//GEN-LAST:event_txtPatientIDActionPerformed

    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

    int doctorId = getSelectedDoctorId();
    int patientId = selectedPatientId;

    if (patientId <= 0) {
        JOptionPane.showMessageDialog(this, "Select a patient first.");
        return;
    }

    if (doctorId <= 0) {
        JOptionPane.showMessageDialog(this, "Select a doctor first.");
        return;
    }

    if (getSelectedSource() == null) {
        JOptionPane.showMessageDialog(this, "Select appointment source.");
        return;
    }

    if (!checkAppointmentLimit(doctorId)) return;

    Timestamp appTime = getAppointmentDateTime();
    if (appTime == null) return;

    String sql = """
    INSERT INTO appointments
    (patient_id, doctor_id, appointment_time, type, source, queue_no, status)
    VALUES (?, ?, ?, ?, ?, ?, ?)
""";

    try (Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, patientId);
        ps.setInt(2, doctorId);
        ps.setTimestamp(3, appTime);
        ps.setString(4, getSelectedType());
        ps.setString(5, getSelectedSource());
        ps.setInt(6, 1); // queue always 1
        ps.setString(7, "scheduled");

        ps.executeUpdate();
        JOptionPane.showMessageDialog(this, "Appointment Scheduled!");

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to schedule appointment.");
    }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
    txtAppDate.setText("yyyy-MM-dd");
    txtAppDate.setForeground(new java.awt.Color(150,150,150));
    txtAppTime.setText("HH:mm");
    txtAppTime.setForeground(new java.awt.Color(150,150,150));
    txtAppDate.setEditable(true);
    txtAppTime.setEditable(true);
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
    txtAppDate.setText(java.time.LocalDate.now().toString());
    txtAppDate.setEditable(false);
    txtAppTime.setText(java.time.LocalTime.now()
        .withSecond(0)
        .withNano(0)
        .toString());
    txtAppTime.setEditable(false);
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void txtAppTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAppTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAppTimeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddNew;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> comboDoctor;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblHeaderNm;
    private javax.swing.JLabel lblHeaderNm2;
    private javax.swing.ButtonGroup sourceGroup;
    private javax.swing.JTextField txtAppDate;
    private javax.swing.JTextField txtAppTime;
    private javax.swing.JTextField txtPatientDob;
    private javax.swing.JTextField txtPatientID;
    private javax.swing.JTextField txtPatientName;
    private javax.swing.JTextField txtPatientPhone;
    private javax.swing.JTextField txtSearchPhone;
    private javax.swing.ButtonGroup typeGroup;
    // End of variables declaration//GEN-END:variables
}
