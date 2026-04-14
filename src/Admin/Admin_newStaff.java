package Admin;
import db.DBconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class Admin_newStaff extends javax.swing.JFrame {
      private Admin_staffManage parent;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Admin_newStaff.class.getName());

    private Admin_staffManage parentReference;
    public Admin_newStaff(Admin_staffManage parent) {
        initComponents();
         this.parent = parent;
           setLocationRelativeTo(null);
           initHoverEffects1();
           addPlaceholderFn();
    }
     private void initHoverEffects1() {
    styleHoverButton1(jButton1);
     styleHoverButton1(jButton8);
    
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
   
    private void addPlaceholderFn(){
addPlaceholder(txtName,        "Enter name");
addPlaceholder(txtPhone,    "Enter phone");
addPlaceholder(txtEmail,         "Enter Email");
addPlaceholder(txtRole,    "Enter Role");
addPlaceholder(txtPassword,    "Enter Password");

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
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtName = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtRole = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(237, 231, 227));

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Reception/cross-23.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(22, 105, 122));
        jButton1.setFont(new java.awt.Font("Kohinoor Bangla", 3, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Reception/992651.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRole, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRole, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if (parent != null) {
            parent.loadtbl();
            parent.adjustColumnWidths();
        }
        this.dispose();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     String name  = txtName.getText().trim();
    String phone = txtPhone.getText().trim();
    String email = txtEmail.getText().trim();
    String role  = txtRole.getText().trim().toLowerCase();
    String pass  = txtPassword.getText().trim();

    // Remove placeholder values
    if (name.equals("Enter name")) name = "";
    if (phone.equals("Enter phone")) phone = "";
    if (email.equals("Enter Email")) email = "";
    if (pass.equals("Enter Password")) pass = "";
    if (role.equals("Enter Role")) role = "";

    // ---------------- VALIDATIONS ----------------

    if (name.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Name is required");
        txtName.requestFocus();
        return;
    }

    // 🚫 Username must NOT contain spaces
    if (name.contains(" ")) {
        JOptionPane.showMessageDialog(this, "Username must not contain spaces");
        txtName.requestFocus();
        return;
    }

    if (phone.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Phone is required");
        txtPhone.requestFocus();
        return;
    }

    if (email.isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
        JOptionPane.showMessageDialog(this, "Enter valid email");
        txtEmail.requestFocus();
        return;
    }

    if (!role.equals("doctor") && !role.equals("receptionist") && !role.equals("admin")) {
        JOptionPane.showMessageDialog(this, "Role must be doctor / receptionist / admin");
        txtRole.requestFocus();
        return;
    }

    if (pass.isEmpty() || pass.length() < 6) {
        JOptionPane.showMessageDialog(this, "Password must be at least 6 characters");
        txtPassword.requestFocus();
        return;
    }

    // ---------------- DB INSERT ----------------

    String sql = """
        INSERT INTO user_credentials
        (name, phone, email, password_hash, role)
        VALUES (?, ?, ?, ?, ?)
    """;

    try (Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, name);
        ps.setString(2, phone);
        ps.setString(3, email);
        ps.setString(4, pass);
        ps.setString(5, role);

        ps.executeUpdate();

        JOptionPane.showMessageDialog(this, "Staff added successfully");

        if (parent != null) {
            parent.loadtbl();
            parent.adjustColumnWidths();
        }

        this.dispose();

    } catch (java.sql.SQLIntegrityConstraintViolationException ex) {
        JOptionPane.showMessageDialog(this, "Phone or Email already exists");
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtRole;
    // End of variables declaration//GEN-END:variables
}
