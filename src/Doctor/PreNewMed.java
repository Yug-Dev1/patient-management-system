
package Doctor;
import db.DBconnection;
import java.sql.*;
import javax.swing.JOptionPane;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;
import java.util.Map;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;

public class PreNewMed extends javax.swing.JFrame {
    
    private int prescriptionId;
    private NewPrescription parentFrame;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PreNewMed.class.getName());
    private DefaultTableModel parentModel;   // model from main bill table
      
    public PreNewMed(int prescriptionId, NewPrescription parentFrame) {
    this.prescriptionId = prescriptionId;
    this.parentFrame = parentFrame;
    initComponents();
    initHoverEffects1();
}
    
    private PreNewMed() {
    // prevent incorrect usage
}

    private void initHoverEffects1() {
    styleHoverButton1(jButton3);
   
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
    
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        txtDosage = new javax.swing.JTextField();
        txtMedicineName = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        txtFrequency = new javax.swing.JTextField();
        today = new javax.swing.JLabel();
        today1 = new javax.swing.JLabel();
        today2 = new javax.swing.JLabel();
        today3 = new javax.swing.JLabel();
        txtDuration = new javax.swing.JTextField();
        today4 = new javax.swing.JLabel();
        txtInstructions = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        txtDosage.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N

        txtMedicineName.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N

        jButton3.setBackground(new java.awt.Color(22, 105, 122));
        jButton3.setFont(new java.awt.Font("Kohinoor Bangla", 3, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Add");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txtFrequency.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N

        today.setBackground(new java.awt.Color(0, 0, 0));
        today.setFont(new java.awt.Font("October Compressed Devanagari", 2, 18)); // NOI18N
        today.setForeground(new java.awt.Color(72, 159, 181));
        today.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        today.setText("Name");
        today.setToolTipText("");

        today1.setBackground(new java.awt.Color(0, 0, 0));
        today1.setFont(new java.awt.Font("October Compressed Devanagari", 2, 18)); // NOI18N
        today1.setForeground(new java.awt.Color(72, 159, 181));
        today1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        today1.setText("Dosage");

        today2.setBackground(new java.awt.Color(0, 0, 0));
        today2.setFont(new java.awt.Font("October Compressed Devanagari", 2, 18)); // NOI18N
        today2.setForeground(new java.awt.Color(72, 159, 181));
        today2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        today2.setText("Frequency");

        today3.setBackground(new java.awt.Color(0, 0, 0));
        today3.setFont(new java.awt.Font("October Compressed Devanagari", 2, 18)); // NOI18N
        today3.setForeground(new java.awt.Color(72, 159, 181));
        today3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        today3.setText("Duration");

        txtDuration.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N

        today4.setBackground(new java.awt.Color(0, 0, 0));
        today4.setFont(new java.awt.Font("October Compressed Devanagari", 2, 18)); // NOI18N
        today4.setForeground(new java.awt.Color(72, 159, 181));
        today4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        today4.setText("Instruction");

        txtInstructions.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(today4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(today, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMedicineName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                            .addComponent(today1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDosage, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(today2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(40, Short.MAX_VALUE))
                    .addComponent(today3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtInstructions, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(jButton3)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(today)
                .addGap(0, 0, 0)
                .addComponent(txtMedicineName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(today1)
                .addGap(0, 0, 0)
                .addComponent(txtDosage, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(today2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(today3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(today4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txtInstructions, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
 String name = txtMedicineName.getText().trim();
    String dosage = txtDosage.getText().trim();
    String frequency = txtFrequency.getText().trim();
    String duration = txtDuration.getText().trim();
    String instructions = txtInstructions.getText().trim();

    // ---------- Validation ----------
    if (name.isEmpty() || dosage.isEmpty() || frequency.isEmpty() || duration.isEmpty()) {
        JOptionPane.showMessageDialog(this, "All fields except instructions are mandatory");
        return;
    }

    String sql = """
        INSERT INTO prescription_items
        (prescription_id, medicine_name, dosage, frequency, duration, instructions)
        VALUES (?, ?, ?, ?, ?, ?)
    """;

    try (Connection con = DBconnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, prescriptionId);
        ps.setString(2, name);
        ps.setString(3, dosage);
        ps.setString(4, frequency);
        ps.setString(5, duration);
        ps.setString(6, instructions);

        ps.executeUpdate();

        // ✅ Refresh parent table
        parentFrame.loadMedicineTable();

        // ✅ Close this window
        dispose();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Failed to add medicine");
        e.printStackTrace();
    }
       
    }//GEN-LAST:event_jButton3ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new PreNewMed().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel today;
    private javax.swing.JLabel today1;
    private javax.swing.JLabel today2;
    private javax.swing.JLabel today3;
    private javax.swing.JLabel today4;
    private javax.swing.JTextField txtDosage;
    private javax.swing.JTextField txtDuration;
    private javax.swing.JTextField txtFrequency;
    private javax.swing.JTextField txtInstructions;
    private javax.swing.JTextField txtMedicineName;
    // End of variables declaration//GEN-END:variables
}