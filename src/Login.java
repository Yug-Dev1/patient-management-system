import Admin.AdminDashboard;
import Reception.RecepDashboard;
import Doctor.DoctorDashboard;
import java.sql.*;
import db.DBconnection;
import javax.swing.JOptionPane;
import com.formdev.flatlaf.FlatLightLaf;


public class Login extends javax.swing.JFrame {
    
public static final String URL="jdbc:mysql://localhost:3306/stack_overflow_urgent_care";
public static final String USER="root";
public static final String PASSWORD="root";


    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Login.class.getName());

    public Login() {
        initComponents();
        sql_code();
       
        setLocationRelativeTo(null);

        setResizable(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setBorderPainted(false);
        jButton1.setFocusPainted(false);
        jButton1.setOpaque(false);
        jButton1.setForeground(new java.awt.Color(20, 105, 122));

      
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                jButton1.setContentAreaFilled(true);
                jButton1.setOpaque(true);
                jButton1.setBorderPainted(false);
                jButton1.setBackground(new java.awt.Color(20, 105, 122));
                jButton1.setForeground(java.awt.Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                jButton1.setContentAreaFilled(false);
                jButton1.setOpaque(false);
                jButton1.setBorderPainted(false);
                jButton1.setForeground(new java.awt.Color(20, 105, 122));
            }
        });
    }
    void sql_code()
{
    try
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);  
            String sql="create database if not exists stack_overflow_urgent_care";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute(sql);
            System.out.print("DB create successss");
            
            String table1="CREATE TABLE user_credentials (\n" +
"    user_id        INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n" +
"    name           VARCHAR(120) NOT NULL,\n" +
"    phone          VARCHAR(15) UNIQUE,\n" +
"    email          VARCHAR(150) UNIQUE,\n" +
"    password_hash  TEXT NOT NULL,\n" +
"    role           ENUM('doctor','receptionist','admin') NOT NULL,\n" +
"    status         ENUM('active','disabled') NOT NULL DEFAULT 'active',\n" +
"    created_at     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP\n" +
") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
            ps.execute(table1);
            
            String table2="CREATE TABLE patients (\n" +
"    patient_id      INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n" +
"    full_name       VARCHAR(120) NOT NULL,\n" +
"    gender          ENUM('M','F','O') DEFAULT NULL,\n" +
"    dob             DATE DEFAULT NULL,\n" +
"    phone           VARCHAR(15),\n" +
"    email           VARCHAR(150),\n" +
"    address         TEXT,\n" +
"    blood_group     VARCHAR(5),\n" +
"    allergy         VARCHAR(150),\n" +
"    personal_notes  TEXT,\n" +
"    created_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
"    updated_at      DATETIME NULL ON UPDATE CURRENT_TIMESTAMP,\n" +
"    INDEX idx_patients_phone (phone),\n" +
"    INDEX idx_patients_name (full_name)\n" +
") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
            ps.execute(table2);
            
            String table3="CREATE TABLE appointments (\n" +
"    appointment_id    INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n" +
"    patient_id        INT UNSIGNED NOT NULL,\n" +
"    doctor_id         INT UNSIGNED DEFAULT NULL,\n" +
"    appointment_time  DATETIME NOT NULL,\n" +
"    type              ENUM('direct','slot') NOT NULL DEFAULT 'direct',\n" +
"    source            ENUM('walk-in','referral','ads','online','other') NOT NULL DEFAULT 'other',\n" +
"    queue_no          INT UNSIGNED,\n" +
"    status            ENUM('scheduled','completed','cancelled') NOT NULL DEFAULT 'scheduled',\n" +
"    created_at        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
"    INDEX idx_app_patient (patient_id),\n" +
"    INDEX idx_app_doctor (doctor_id),\n" +
"    INDEX idx_app_time (appointment_time),\n" +
"    CONSTRAINT fk_app_patient\n" +
"        FOREIGN KEY (patient_id) REFERENCES patients(patient_id)\n" +
"        ON DELETE CASCADE,\n" +
"    CONSTRAINT fk_app_doctor\n" +
"        FOREIGN KEY (doctor_id) REFERENCES user_credentials(user_id)\n" +
"        ON DELETE SET NULL\n" +
") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
            ps.execute(table3);
            
             String table4="CREATE TABLE patient_billing (\n" +
"    bill_id        INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n" +
"    patient_id     INT UNSIGNED NOT NULL,\n" +
"    appointment_id INT UNSIGNED DEFAULT NULL,\n" +
"    doctor_id      INT UNSIGNED DEFAULT NULL,\n" +
"    total_amount   DECIMAL(10,2) NOT NULL DEFAULT 0,\n" +
"    discount       DECIMAL(10,2) NOT NULL DEFAULT 0,\n" +
"    net_amount     DECIMAL(10,2) NOT NULL DEFAULT 0,\n" +
"    payment_mode   ENUM('cash','upi','card','other') NOT NULL,\n" +
"    status         ENUM('paid','unpaid') NOT NULL DEFAULT 'paid',\n" +
"    created_at     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
"    INDEX idx_bill_date (created_at),\n" +
"    INDEX idx_bill_patient (patient_id),\n" +
"    CONSTRAINT fk_bill_patient\n" +
"        FOREIGN KEY (patient_id) REFERENCES patients(patient_id)\n" +
"        ON DELETE CASCADE,\n" +
"    CONSTRAINT fk_bill_appointment\n" +
"        FOREIGN KEY (appointment_id) REFERENCES appointments(appointment_id)\n" +
"        ON DELETE SET NULL,\n" +
"    CONSTRAINT fk_bill_doctor\n" +
"        FOREIGN KEY (doctor_id) REFERENCES user_credentials(user_id)\n" +
"        ON DELETE SET NULL\n" +
") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
            ps.execute(table4);
            
             String table5="CREATE TABLE invoice (\n" +
"    invoice_item_id  INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n" +
"    bill_id          INT UNSIGNED NOT NULL,\n" +
"    item_type        ENUM('consultation','procedure','medicine','lab','other') NOT NULL,\n" +
"    item_name        VARCHAR(255) NOT NULL,\n" +
"    qty              INT UNSIGNED NOT NULL DEFAULT 1,\n" +
"    price            DECIMAL(10,2) NOT NULL DEFAULT 0,\n" +
"    total            DECIMAL(10,2) NOT NULL DEFAULT 0,\n" +
"    INDEX idx_invoice_bill (bill_id),\n" +
"    CONSTRAINT fk_invoice_bill\n" +
"        FOREIGN KEY (bill_id) REFERENCES patient_billing(bill_id)\n" +
"        ON DELETE CASCADE\n" +
") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
            ps.execute(table5);
            
             String table6="CREATE TABLE inventory (\n" +
"    item_id          INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n" +
"    name             VARCHAR(200) NOT NULL,\n" +
"    category         ENUM('consultation','procedure','medicine','lab','other') NOT NULL,\n" +
"    qty              INT NOT NULL DEFAULT 0,\n" +
"    low_stock_alert  INT NOT NULL DEFAULT 5,\n" +
"    expiry_date      DATE DEFAULT NULL,\n" +
"    cost_price       DECIMAL(10,2) DEFAULT 0,\n" +
"    selling_price    DECIMAL(10,2) DEFAULT 0,\n" +
"    created_at       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
"    updated_at       DATETIME NULL ON UPDATE CURRENT_TIMESTAMP,\n" +
"    INDEX idx_inventory_name (name)\n" +
") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
            ps.execute(table6);
            
             String table7="CREATE TABLE prescriptions (\n" +
"    prescription_id  INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n" +
"    patient_id       INT UNSIGNED NOT NULL,\n" +
"    doctor_id        INT UNSIGNED NOT NULL,\n" +
"    appointment_id   INT UNSIGNED DEFAULT NULL,\n" +
"    notes            TEXT,\n" +
"    created_at       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
"    INDEX idx_rx_patient (patient_id),\n" +
"    INDEX idx_rx_doctor (doctor_id),\n" +
"    CONSTRAINT fk_rx_patient\n" +
"        FOREIGN KEY (patient_id) REFERENCES patients(patient_id)\n" +
"        ON DELETE CASCADE,\n" +
"    CONSTRAINT fk_rx_doctor\n" +
"        FOREIGN KEY (doctor_id) REFERENCES user_credentials(user_id)\n" +
"        ON DELETE CASCADE,\n" +
"    CONSTRAINT fk_rx_appointment\n" +
"        FOREIGN KEY (appointment_id) REFERENCES appointments(appointment_id)\n" +
"        ON DELETE SET NULL\n" +
") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
            ps.execute(table7);
            
             String table8="CREATE TABLE prescription_items (\n" +
"    prescription_item_id  INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n" +
"    prescription_id       INT UNSIGNED NOT NULL,\n" +
"    medicine_name         VARCHAR(255) NOT NULL,\n" +
"    dosage                VARCHAR(100),\n" +
"    frequency             VARCHAR(100),\n" +
"    duration              VARCHAR(50),\n" +
"    instructions          TEXT,\n" +
"    created_at            DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
"    INDEX idx_pi_rx (prescription_id),\n" +
"    CONSTRAINT fk_pi_rx\n" +
"        FOREIGN KEY (prescription_id) REFERENCES prescriptions(prescription_id)\n" +
"        ON DELETE CASCADE\n" +
") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
            ps.execute(table8);
            
            String table9="CREATE TABLE prescription_templates (\n" +
"    template_id    INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n" +
"    doctor_id      INT UNSIGNED NOT NULL,\n" +
"    template_name  VARCHAR(120) NOT NULL,\n" +
"    created_at     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
"    INDEX idx_pt_doctor (doctor_id),\n" +
"    CONSTRAINT fk_pt_doctor\n" +
"        FOREIGN KEY (doctor_id) REFERENCES user_credentials(user_id)\n" +
"        ON DELETE CASCADE\n" +
") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
            ps.execute(table9);
            
            String table10="CREATE TABLE template_items (\n" +
"    id             INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n" +
"    template_id    INT UNSIGNED NOT NULL,\n" +
"    medicine_name  VARCHAR(255) NOT NULL,\n" +
"    dosage         VARCHAR(100),\n" +
"    frequency      VARCHAR(100),\n" +
"    duration       VARCHAR(50),\n" +
"    instructions   TEXT,\n" +
"    INDEX idx_ti_template (template_id),\n" +
"    CONSTRAINT fk_ti_template\n" +
"        FOREIGN KEY (template_id) REFERENCES prescription_templates(template_id)\n" +
"        ON DELETE CASCADE\n" +
") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
            ps.execute(table10);
            
            String table11="CREATE TABLE advertisement_analysis (\n" +
"    ad_id        INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n" +
"    source       VARCHAR(100),\n" +
"    spend_amount DECIMAL(10,2) DEFAULT 0,\n" +
"    leads        INT UNSIGNED DEFAULT 0,\n" +
"    conversions  INT UNSIGNED DEFAULT 0,\n" +
"    created_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP\n" +
") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
            ps.execute(table11);
            
            String table12="CREATE TABLE clinic_expense_tracker (\n" +
"    expense_id    INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n" +
"    category      VARCHAR(100),\n" +
"    description   TEXT,\n" +
"    amount        DECIMAL(10,2) NOT NULL DEFAULT 0,\n" +
"    payment_mode  ENUM('cash','upi','card','bank') NOT NULL,\n" +
"    expense_date  DATE NOT NULL,\n" +
"    created_at    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
"    INDEX idx_exp_date (expense_date)\n" +
") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
            ps.execute(table12);
            
                
            ps.close();
            conn.close();
     }catch(Exception e){
         System.out.print("Error"+e);
     }
}
    void login(){
          String username = jTextField1.getText().trim();
    String password = new String(jPasswordField1.getPassword());  // this is fine

    // Quick empty check
    if (username.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter both username and password.");
        return;
    }

    String sql = "SELECT user_id, name, role, password_hash, status " +
                 "FROM user_credentials " +
                 "WHERE name = ?";

        try {
             Connection conn = DBconnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    // No such user
                    JOptionPane.showMessageDialog(this, "Username or password is incorrect.");
                } else {
                    String dbPassword = rs.getString("password_hash");
                    String role = rs.getString("role");
                    String status = rs.getString("status");

                    // Check account status
                    if ("disabled".equalsIgnoreCase(status)) {
                        JOptionPane.showMessageDialog(this, "Your account is disabled. Please contact admin.");
                        return;
                    }

                    // ⚠️ For now: plain-text comparison (later replace with hashing)
                    if (password.equals(dbPassword)) {
    int userId = rs.getInt("user_id");
    String name = rs.getString("name");

    JOptionPane.showMessageDialog(this, "Welcome " + role + ", you are logged in!");

    if ("doctor".equalsIgnoreCase(role)) {
    DoctorDashboard docDash = new DoctorDashboard(userId, name);
    docDash.setLocationRelativeTo(null);
    docDash.setVisible(true);
} else if ("receptionist".equalsIgnoreCase(role)) {
  RecepDashboard recDash = new RecepDashboard(userId, name);
    recDash.setLocationRelativeTo(null);
    recDash.setVisible(true);
} else if ("admin".equalsIgnoreCase(role)) {
    AdminDashboard adDash = new AdminDashboard(userId, name);
    adDash.setLocationRelativeTo(null);
    adDash.setVisible(true);
}

    this.dispose();
                    }else {
                        JOptionPane.showMessageDialog(this, "Username or password is incorrect.");
                    }
                }
            }
        } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error while logging in: " + e.getMessage());
    } finally {
        // Clear fields
        jTextField1.setText("");
        jPasswordField1.setText("");
    }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(900, 560));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Login_Stock.jpg"))); // NOI18N

        jPanel3.setBackground(new java.awt.Color(130, 192, 204));

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("October Compressed Devanagari", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(20, 105, 122));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Username");

        jTextField1.setBorder(null);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("October Compressed Devanagari", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(20, 105, 122));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Password");
        jLabel5.setToolTipText("");

        jPasswordField1.setBorder(null);
        jPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField1ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(22, 105, 122));
        jButton1.setFont(new java.awt.Font("Kohinoor Bangla", 3, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5)
                            .addComponent(jTextField1)
                            .addComponent(jLabel4)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(169, 169, 169)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jLabel4)
                .addGap(0, 0, 0)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel5)
                .addGap(0, 0, 0)
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(filler1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setBackground(new java.awt.Color(20, 105, 122));

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("October Compressed Devanagari", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 166, 43));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Stack Overflow Urgent Care");

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("October Compressed Devanagari", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 166, 43));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("-By JAVA KUMAR");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(276, 276, 276))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(263, 263, 263)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(272, 272, 272)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addComponent(jLabel3)
                .addGap(12, 12, 12)
                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
     jPasswordField1.requestFocusInWindow();      
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        login();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField1ActionPerformed
        login();
    }//GEN-LAST:event_jPasswordField1ActionPerformed

    public static void main(String args[]) {
         try {
        FlatLightLaf.setup();   // 👈 Activates FlatLaf
    } catch (Exception ex) {
        ex.printStackTrace();
    }

        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
