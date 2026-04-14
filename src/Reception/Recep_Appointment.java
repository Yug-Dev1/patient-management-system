
package Reception;

import db.DBconnection;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class Recep_Appointment extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Recep_Appointment.class.getName());
public static Recep_Appointment INSTANCE = null;
     private int userId;
    private String userName;

public Recep_Appointment(int userId, String name) {
    INSTANCE = this;
    this.userId = userId;
    this.userName = name;

    initComponents();
    setResizable(false);
    
    lblHeaderNm.setText("Welcome " + name);
    initHoverEffects();
     initHoverEffects1();
    table();
    No_appointment();
    tablewidth();
    
      LocalDate today = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy", Locale.ENGLISH);
    Cur_date.setText(today.format(formatter));
    }

public Recep_Appointment() {
    INSTANCE = this;
        initComponents();
           initHoverEffects();
           initHoverEffects1();
           No_appointment();
           table();
           tablewidth();
           
           LocalDate today = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy", Locale.ENGLISH);
    Cur_date.setText(today.format(formatter));
    }

public void No_appointment(){
 
    int scheduled = 0;
    int completed = 0;
    int cancelled = 0;

    String sql = "SELECT status, COUNT(*) AS total \n" +
"               FROM appointments\n" +
"               WHERE DATE(appointment_time) = CURDATE() \n" +
"               GROUP BY status";

    try {
        Connection conn = DBconnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String status = rs.getString("status");
                    int count = rs.getInt("total");

                    if ("scheduled".equalsIgnoreCase(status)) {
                        scheduled = count;
                    } else if ("completed".equalsIgnoreCase(status)) {
                        completed = count;
                    } else if ("cancelled".equalsIgnoreCase(status)) {
                        cancelled = count;
                    }
                }

        // Now update labels
        lblScheduled.setText(String.valueOf(scheduled));
        lblCompleted.setText(String.valueOf(completed));
        lblCanceled.setText(String.valueOf(cancelled));

    } catch (SQLException e) {
        e.printStackTrace();
        // Optional:
        // JOptionPane.showMessageDialog(this, "Error loading appointment counts: " + e.getMessage());
    }

}
public void table() {
    DefaultTableModel model = (DefaultTableModel) tblAppointment.getModel();
    model.setRowCount(0); // clear old rows

    String sql = "SELECT "
               + "  a.appointment_id, "
               + "  a.patient_id, "
               + "  p.full_name AS patient_name, "
               + "  a.doctor_id, "
               + "  a.appointment_time, "
               + "  a.type, "
               + "  a.source, "
               + "  a.queue_no, "
               + "  a.status, "
               + "  a.created_at "
               + "FROM appointments a "
               + "JOIN patients p ON a.patient_id = p.patient_id "
               + "WHERE DATE(a.appointment_time) = CURDATE() "
               + "ORDER BY a.appointment_time ASC";

    try {
        Connection conn = DBconnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        
                while (rs.next()) {
                    Object[] row = new Object[] {
                        rs.getInt("appointment_id"),
                        rs.getInt("patient_id"),
                        rs.getString("patient_name"),
                        rs.getInt("doctor_id"),
                        rs.getTimestamp("appointment_time"),
                        rs.getString("type"),
                        rs.getString("source"),
                        rs.getObject("queue_no"),      // can be NULL
                        rs.getString("status"),
                        rs.getTimestamp("created_at")
                    };
                    model.addRow(row);
        
                }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error loading appointments: " + e.getMessage());
        e.printStackTrace();
    }
}

 private void tablewidth(){
tblAppointment.getColumnModel().getColumn(0).setPreferredWidth(50);   // appointment_id
tblAppointment.getColumnModel().getColumn(1).setPreferredWidth(50);   // patient_id
tblAppointment.getColumnModel().getColumn(2).setPreferredWidth(170);  // patient_name
tblAppointment.getColumnModel().getColumn(3).setPreferredWidth(50);   // doctor_id
tblAppointment.getColumnModel().getColumn(4).setPreferredWidth(230);  // appointment_time
tblAppointment.getColumnModel().getColumn(5).setPreferredWidth(70);  // type
tblAppointment.getColumnModel().getColumn(6).setPreferredWidth(70);  // source
tblAppointment.getColumnModel().getColumn(7).setPreferredWidth(50);   // queue_no
tblAppointment.getColumnModel().getColumn(8).setPreferredWidth(90);  // status
tblAppointment.getColumnModel().getColumn(9).setPreferredWidth(230);  // created_at
 }
 
private void initHoverEffects() {
    styleHoverButton(jButton3);
    styleHoverButton(jButton4);
    styleHoverButton(jButton5);
    styleHoverButton(jButton6);
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
private void styleHoverButton(javax.swing.JButton btn) {
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
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        lblHeaderNm = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lblCanceled = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblCompleted = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblScheduled = new javax.swing.JLabel();
        Cur_date = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAppointment = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(20, 105, 122));

        lblHeaderNm.setFont(new java.awt.Font("October Compressed Devanagari", 3, 36)); // NOI18N
        lblHeaderNm.setForeground(new java.awt.Color(255, 166, 43));
        lblHeaderNm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeaderNm.setText("Appointment List");

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
                .addGap(147, 147, 147)
                .addComponent(lblHeaderNm, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(244, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(31, Short.MAX_VALUE)
                        .addComponent(lblHeaderNm)
                        .addGap(27, 27, 27))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.setBackground(new java.awt.Color(237, 231, 227));

        jPanel3.setBackground(new java.awt.Color(237, 231, 227));

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("October Compressed Devanagari", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(20, 105, 122));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Cancelled");

        lblCanceled.setFont(new java.awt.Font("Kohinoor Devanagari", 3, 36)); // NOI18N
        lblCanceled.setForeground(new java.awt.Color(255, 166, 43));
        lblCanceled.setText("-1");

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("October Compressed Devanagari", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(20, 105, 122));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Completed");

        lblCompleted.setFont(new java.awt.Font("Kohinoor Devanagari", 3, 36)); // NOI18N
        lblCompleted.setForeground(new java.awt.Color(255, 166, 43));
        lblCompleted.setText("-1");

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("October Compressed Devanagari", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(20, 105, 122));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Scheduled");

        lblScheduled.setFont(new java.awt.Font("Kohinoor Devanagari", 3, 36)); // NOI18N
        lblScheduled.setForeground(new java.awt.Color(255, 166, 43));
        lblScheduled.setText("-1");

        Cur_date.setBackground(new java.awt.Color(0, 0, 0));
        Cur_date.setFont(new java.awt.Font("October Compressed Devanagari", 2, 18)); // NOI18N
        Cur_date.setForeground(new java.awt.Color(72, 159, 181));
        Cur_date.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Cur_date.setText("Today's");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(Cur_date, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(lblCompleted, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(lblScheduled, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCanceled, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(15, 15, 15))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Cur_date)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCompleted, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblScheduled, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCanceled, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblAppointment.setBackground(new java.awt.Color(237, 231, 227));
        tblAppointment.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        tblAppointment.setForeground(new java.awt.Color(20, 105, 122));
        tblAppointment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "App. id ", "Patient Id", "Name ", "doctor_id ", "time ", "type ", "source ", "queue_no ", "status ", "created_at "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblAppointment);

        jButton4.setBackground(new java.awt.Color(22, 105, 122));
        jButton4.setFont(new java.awt.Font("Kohinoor Bangla", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Reception/992651.png"))); // NOI18N
        jButton4.setText("  New Appointment");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(22, 105, 122));
        jButton5.setFont(new java.awt.Font("Kohinoor Bangla", 1, 18)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Cancel");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(22, 105, 122));
        jButton6.setFont(new java.awt.Font("Kohinoor Bangla", 1, 18)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Complete");
        jButton6.setToolTipText("");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(59, 59, 59))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 861, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton6)
                            .addComponent(jButton5))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                .addContainerGap())
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
        // Go back to main doctor dashboard
        RecepDashboard dash = new RecepDashboard(userId, userName);
        dash.setLocationRelativeTo(this);
        dash.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Recep_NewAppointment dash = new Recep_NewAppointment(userId, userName);
        dash.setLocationRelativeTo(this);
        dash.setVisible(true);
        this.dispose();

//new Recep_NewAppointmentSimple().setVisible(true);
//this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       int row = tblAppointment.getSelectedRow();
    if (row == -1) return;

    int modelRow = tblAppointment.convertRowIndexToModel(row);
    int appointmentId = Integer.parseInt(
            tblAppointment.getModel().getValueAt(modelRow, 0).toString()
    );

    String sql = "UPDATE appointments SET status = 'cancelled' WHERE appointment_id = ?";

    try (Connection conn = db.DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, appointmentId);
        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
        
        No_appointment();
       table();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int row = tblAppointment.getSelectedRow();
    if (row == -1) return;

    int modelRow = tblAppointment.convertRowIndexToModel(row);
    int appointmentId = Integer.parseInt(
            tblAppointment.getModel().getValueAt(modelRow, 0).toString()
    );

    String sql = "UPDATE appointments SET status = 'completed' WHERE appointment_id = ?";

    try (Connection conn = db.DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, appointmentId);
        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
        
        No_appointment();
       table();
    }//GEN-LAST:event_jButton6ActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> new Recep_Appointment().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Cur_date;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCanceled;
    private javax.swing.JLabel lblCompleted;
    private javax.swing.JLabel lblHeaderNm;
    private javax.swing.JLabel lblScheduled;
    private javax.swing.JTable tblAppointment;
    // End of variables declaration//GEN-END:variables
}
