
package Reception;
import db.DBconnection;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

public class Recep_ClinicalExpense extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Recep_ClinicalExpense.class.getName());

    
    public Recep_ClinicalExpense() {
        initComponents();
        addPlaceHolderFields();
        initHoverEffects1();
    }
    private void initHoverEffects1() {
    styleHoverButton1(jButton5);
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
    
     private void addPlaceHolderFields(){
     addPlaceholder(jTextField1, "Category");  
     addPlaceholderArea(jTextArea1, "Enter Description...."); 
     addPlaceholder(jTextField4, "Price"); 
     addPlaceholder(jTextField2, "Payement Mode");
     addPlaceholder(jTextField3, "yyyy-mm-dd");
     
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
    private void addPlaceholderArea(javax.swing.JTextArea area, String placeholderText) {

    area.setText(placeholderText);
    area.setForeground(new java.awt.Color(150, 150, 150)); // light gray

    area.addFocusListener(new java.awt.event.FocusAdapter() {

        @Override
        public void focusGained(java.awt.event.FocusEvent e) {
            if (area.getText().equals(placeholderText)) {
                area.setText("");
                area.setForeground(new java.awt.Color(0, 0, 0)); // black
            }
        }

        @Override
        public void focusLost(java.awt.event.FocusEvent e) {
            if (area.getText().trim().isEmpty()) {
                area.setText(placeholderText);
                area.setForeground(new java.awt.Color(150, 150, 150));
            }
        }
    });
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        lblHeaderNm = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField3 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(20, 105, 122));

        lblHeaderNm.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        lblHeaderNm.setForeground(new java.awt.Color(255, 166, 43));
        lblHeaderNm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeaderNm.setText("Add Expense");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(lblHeaderNm, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblHeaderNm, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jButton5.setBackground(new java.awt.Color(22, 105, 122));
        jButton5.setFont(new java.awt.Font("Kohinoor Bangla", 2, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Doctor/black-checkmark-png-4.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton5)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
String category = jTextField1.getText().trim();
String description = jTextArea1.getText().trim();
String amountStr = jTextField4.getText().trim();
String paymentMode = jTextField2.getText().trim().toLowerCase();
String dateStr = jTextField3.getText().trim();

if (category.isEmpty() || amountStr.isEmpty() || paymentMode.isEmpty() || dateStr.isEmpty()) {
    JOptionPane.showMessageDialog(this, "All required fields must be filled.");
    return;
}

double amount;
try {
    amount = Double.parseDouble(amountStr);
    if (amount <= 0) throw new NumberFormatException();
} catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(this, "Invalid amount.");
    return;
}

if (!paymentMode.equals("cash") &&
    !paymentMode.equals("upi") &&
    !paymentMode.equals("card") &&
    !paymentMode.equals("bank")) {

    JOptionPane.showMessageDialog(this, "Payment mode must be: cash, upi, card, or bank.");
    return;
}
LocalDate expenseDate;
DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

try {
    expenseDate = LocalDate.parse(dateStr, df);
} catch (Exception e) {
    JOptionPane.showMessageDialog(
        this,
        "Invalid date format. Use yyyy-MM-dd (example: 2025-12-03)"
    );
    return;
}

String sql = """
    INSERT INTO clinic_expense_tracker
    (category, description, amount, payment_mode, expense_date)
    VALUES (?, ?, ?, ?, ?)""";

try (Connection con = DBconnection.getConnection();
     PreparedStatement ps = con.prepareStatement(sql)) {

    ps.setString(1, category);
    ps.setString(2, description);
    ps.setDouble(3, amount);
    ps.setString(4, paymentMode);
    ps.setDate(5, java.sql.Date.valueOf(expenseDate));

    ps.executeUpdate();

    JOptionPane.showMessageDialog(this, "Expense added successfully.");

    this.dispose();

} catch (SQLException e) {
    JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
}
    }//GEN-LAST:event_jButton5ActionPerformed

   
    public static void main(String args[]) {
         java.awt.EventQueue.invokeLater(() -> new Recep_ClinicalExpense().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler2;
    private javax.swing.JButton jButton5;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JLabel lblHeaderNm;
    // End of variables declaration//GEN-END:variables
}
