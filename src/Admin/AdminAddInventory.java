
package Admin;
import Reception.*;
import db.DBconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AdminAddInventory extends javax.swing.JFrame {
    private Admin_Inventory parent;   // Reference to main screen
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AdminAddInventory.class.getName());

    private Admin_Inventory parentReference;
    public AdminAddInventory(Admin_Inventory parent) {
        initComponents();
        
        this.parent = parent;
        
        initHoverEffects1();
        addPlaceholderFn();
        autoFetchItemId();
        setLocationRelativeTo(null);
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
addPlaceholder(txtName,        "Enter item name");
addPlaceholder(txtCategory,    "Enter category");
addPlaceholder(txtQty,         "Enter quantity");
addPlaceholder(txtLowStock,    "Enter low stock limit");
addPlaceholder(txtExpiry,      "yyyy-MM-dd");
addPlaceholder(txtCostPrice,   "Enter cost price");
addPlaceholder(txtSellingPrice,   "Enter selling price");
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
    
    private void autoFetchItemId() {

    String sql = "SELECT IFNULL(MAX(item_id), 0) + 1 AS next_id FROM inventory";

    try (Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            int nextId = rs.getInt("next_id");
            txtItemId.setText(String.valueOf(nextId));
            txtItemId.setEditable(false);   // make sure user cannot change it
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error fetching next Item ID: " + e.getMessage());
    }
} 
    
     @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtItemId = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtCategory = new javax.swing.JTextField();
        txtQty = new javax.swing.JTextField();
        txtLowStock = new javax.swing.JTextField();
        txtExpiry = new javax.swing.JTextField();
        txtCostPrice = new javax.swing.JTextField();
        txtSellingPrice = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(237, 231, 227));

        txtItemId.setEditable(false);

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

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
        jButton1.setText("  Add Item");
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
                            .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtItemId, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSellingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCostPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLowStock, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtItemId, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSellingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCostPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLowStock, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
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
            parent.loadInventoryTable();
            parent.adjustInventoryColumnWidths();
        }
        this.dispose();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    String name       = txtName.getText().trim();
    String category   = txtCategory.getText().trim();
    String qtyText    = txtQty.getText().trim();
    String lowStock   = txtLowStock.getText().trim();
    String expiry     = txtExpiry.getText().trim();
    String costPrice  = txtCostPrice.getText().trim();
    String sellPrice  = txtSellingPrice.getText().trim();

    // -----------------------------
    // 2️⃣ Placeholder Safety Check
    // -----------------------------
    if (name.equals("Enter item name")) name = "";
    if (category.equals("Enter category")) category = "";
    if (qtyText.equals("Enter quantity")) qtyText = "";
    if (lowStock.equals("Enter low stock limit")) lowStock = "";
    if (expiry.equals("yyyy-MM-dd")) expiry = "";
    if (costPrice.equals("Enter cost price")) costPrice = "";
    if (sellPrice.equals("Enter selling price")) sellPrice = "";

    // -----------------------------
    // 3️⃣ Required Field Validation
    // -----------------------------
    if (name.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Item name is required!");
        txtName.requestFocus();
        return;
    }

    if (category.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Item category is required!");
        txtCategory.requestFocus();
        return;
    }

    if (qtyText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Quantity is required!");
        txtQty.requestFocus();
        return;
    }

    if (lowStock.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Low stock alert value is required!");
        txtLowStock.requestFocus();
        return;
    }

    if (expiry.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Expiry date is required!");
        txtExpiry.requestFocus();
        return;
    }

    // -----------------------------
    // 4️⃣ Convert To Correct Types
    // -----------------------------
    int qty = Integer.parseInt(qtyText);
    int lowStockAlert = Integer.parseInt(lowStock);
    java.sql.Date expiryDate = java.sql.Date.valueOf(expiry);   // yyyy-MM-dd

    double costP = costPrice.isEmpty() ? 0.0 : Double.parseDouble(costPrice);
    double sellP = sellPrice.isEmpty() ? 0.0 : Double.parseDouble(sellPrice);

    // -----------------------------
    // 5️⃣ SQL Insert Query
    // -----------------------------
    String sql = "INSERT INTO inventory " +
                 "(name, category, qty, low_stock_alert, expiry_date, cost_price, selling_price) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, name);
        ps.setString(2, category);
        ps.setInt(3, qty);
        ps.setInt(4, lowStockAlert);
        ps.setDate(5, expiryDate);
        ps.setDouble(6, costP);
        ps.setDouble(7, sellP);

        int rows = ps.executeUpdate();
        if (rows > 0) {
            JOptionPane.showMessageDialog(this, "Item added successfully!");
            

//            // refresh the table in parent page
//            if (parentReference != null) {
//                parentReference.loadInventoryTable();
//            }
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add item.");
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtCategory;
    private javax.swing.JTextField txtCostPrice;
    private javax.swing.JTextField txtExpiry;
    private javax.swing.JTextField txtItemId;
    private javax.swing.JTextField txtLowStock;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtSellingPrice;
    // End of variables declaration//GEN-END:variables
}
