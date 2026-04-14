
package Reception;
import db.DBconnection;
import java.sql.*;
import javax.swing.JOptionPane;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;
import java.util.Map;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;

public class RecepBillNewItem extends javax.swing.JFrame {
    
    private int billId;
    private Recep_NewBill parentFrame;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(RecepBillNewItem.class.getName());
    private DefaultTableModel parentModel;   // model from main bill table
    
    private final Map<String, ItemModel> itemCache = new HashMap<>();
    private static class ItemModel {
        int itemId;
        String name;
        double sellingPrice;
        java.sql.Date expiryDate;
        int availableQty;
    }
    
    public RecepBillNewItem(int billId, Recep_NewBill parentFrame) {
    this.billId = billId;
    this.parentFrame = parentFrame;
    initComponents();
    initTypeRadioCommands();
    initHoverEffects1();
    
    cmbItem.addActionListener(e -> {

    String selectedName = (String) cmbItem.getSelectedItem();
    if (selectedName == null) return;

    ItemModel item = itemCache.get(selectedName);
    if (item == null) return;

    // Price is ALWAYS auto-fetched
    txtPrice.setText(String.valueOf(item.sellingPrice));

    // Handle expiry based on category
    if (buttonGroup1.getSelection() == null) {
        txtExpiry.setText("");
        return;
    }

    String category = buttonGroup1.getSelection().getActionCommand();

    if ("medicine".equals(category)) {
        txtExpiry.setText(
            item.expiryDate != null ? item.expiryDate.toString() : ""
        );
    } else {
        // Consultation / Procedure → no expiry
        txtExpiry.setText("");
    }
});
}
    
    private RecepBillNewItem() {
    // prevent incorrect usage
}

    private void initTypeRadioCommands() {
    jRadioButton1.setActionCommand("consultation");
    jRadioButton2.setActionCommand("procedure");
    jRadioButton3.setActionCommand("medicine");
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
    
    private void loadItemsByCategory(String category) {

    cmbItem.removeAllItems();
    itemCache.clear();

    new javax.swing.SwingWorker<Void, ItemModel>() {

        @Override
        protected Void doInBackground() throws Exception {

            String sql = """
                SELECT item_id, name, selling_price, expiry_date, qty
                FROM inventory
                WHERE category = ?
                ORDER BY name
            """;

            try (Connection con = DBconnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, category);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        ItemModel item = new ItemModel();
                        item.itemId = rs.getInt("item_id");
                        item.name = rs.getString("name");
                        item.sellingPrice = rs.getDouble("selling_price");
                        item.expiryDate = rs.getDate("expiry_date");
                        item.availableQty = rs.getInt("qty");
                        publish(item);
                    }
                }
            }
            return null;
        }

        @Override
        protected void process(java.util.List<ItemModel> items) {
            for (ItemModel item : items) {
                cmbItem.addItem(item.name);      // visible
                itemCache.put(item.name, item); // hidden data
            }
        }

    }.execute();
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        txtQty = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        cmbItem = new javax.swing.JComboBox<>();
        txtExpiry = new javax.swing.JTextField();
        today = new javax.swing.JLabel();
        today1 = new javax.swing.JLabel();
        today2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        txtQty.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N

        txtPrice.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N

        jButton3.setBackground(new java.awt.Color(22, 105, 122));
        jButton3.setFont(new java.awt.Font("Kohinoor Bangla", 3, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Doctor/black-checkmark-png-4.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        jRadioButton1.setText("consultation");
        jRadioButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton1MouseClicked(evt);
            }
        });
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        jRadioButton1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jRadioButton1PropertyChange(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        jRadioButton2.setText("procedure");
        jRadioButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton2MouseClicked(evt);
            }
        });
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        jRadioButton3.setText("medicine");
        jRadioButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton3MouseClicked(evt);
            }
        });
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        txtExpiry.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N

        today.setBackground(new java.awt.Color(0, 0, 0));
        today.setFont(new java.awt.Font("October Compressed Devanagari", 2, 18)); // NOI18N
        today.setForeground(new java.awt.Color(72, 159, 181));
        today.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        today.setText("Price");
        today.setToolTipText("");

        today1.setBackground(new java.awt.Color(0, 0, 0));
        today1.setFont(new java.awt.Font("October Compressed Devanagari", 2, 18)); // NOI18N
        today1.setForeground(new java.awt.Color(72, 159, 181));
        today1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        today1.setText("Quantity");

        today2.setBackground(new java.awt.Color(0, 0, 0));
        today2.setFont(new java.awt.Font("October Compressed Devanagari", 2, 18)); // NOI18N
        today2.setForeground(new java.awt.Color(72, 159, 181));
        today2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        today2.setText("Expiry ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtQty, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                            .addComponent(today1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtExpiry)
                            .addComponent(today2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(today, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jRadioButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton3))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(71, 71, 71)
                                .addComponent(jRadioButton2))
                            .addComponent(txtPrice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                            .addComponent(cmbItem, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3))
                        .addContainerGap(40, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addGap(18, 18, 18)
                .addComponent(cmbItem, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(today)
                .addGap(0, 0, 0)
                .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(today1)
                .addGap(0, 0, 0)
                .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(today2)
                .addGap(0, 0, 0)
                .addComponent(txtExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
 if (billId <= 0) {
        JOptionPane.showMessageDialog(this, "Create bill first");
        return;
    }

    if (buttonGroup1.getSelection() == null) {
        JOptionPane.showMessageDialog(this, "Select item type");
        return;
    }

    String category = buttonGroup1.getSelection().getActionCommand();
    String itemName = (String) cmbItem.getSelectedItem();

    if (itemName == null) {
        JOptionPane.showMessageDialog(this, "Select an item");
        return;
    }

    ItemModel item = itemCache.get(itemName);
    if (item == null) {
        JOptionPane.showMessageDialog(this, "Invalid item selection");
        return;
    }

    int qty;
if ("consultation".equals(category)) {
    qty = 1;
} else {
    try {
        qty = Integer.parseInt(txtQty.getText().trim());
        if (qty <= 0) throw new NumberFormatException();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Quantity must be a positive number");
        return;
    }
}

    double price;
    try {
        price = Double.parseDouble(txtPrice.getText().trim());
        if (price < 0) throw new NumberFormatException();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Invalid price");
        return;
    }

    if ("medicine".equals(category)) {
        if (qty > item.availableQty) {
            JOptionPane.showMessageDialog(
                this,
                "Insufficient stock.\nAvailable: " + item.availableQty
            );
            return;
        }
    }

    double total = qty * price;

    String insertInvoiceSQL = """
        INSERT INTO invoice
        (bill_id, item_type, item_name, qty, price, total)
        VALUES (?, ?, ?, ?, ?, ?)
    """;

    String deductStockSQL = """
        UPDATE inventory
        SET qty = qty - ?
        WHERE item_id = ? AND qty >= ?
    """;

    try (Connection con = DBconnection.getConnection()) {

        // 🔐 START TRANSACTION
        con.setAutoCommit(false);

        // 1️⃣ Insert invoice item
        try (PreparedStatement ps = con.prepareStatement(insertInvoiceSQL)) {
            ps.setInt(1, billId);
            ps.setString(2, category);
            ps.setString(3, item.name);
            ps.setInt(4, qty);
            ps.setDouble(5, price);
            ps.setDouble(6, total);
            ps.executeUpdate();
        }

        // 2️⃣ Deduct stock ONLY for medicine
        if ("medicine".equals(category)) {
            try (PreparedStatement ps = con.prepareStatement(deductStockSQL)) {
                ps.setInt(1, qty);
                ps.setInt(2, item.itemId);
                ps.setInt(3, qty);

                int updated = ps.executeUpdate();
                if (updated == 0) {
                    con.rollback();
                    JOptionPane.showMessageDialog(this, "Stock update failed");
                    return;
                }
            }
        }

        // ✅ COMMIT
        con.commit();

        parentFrame.refreshItemsTable();
        dispose();

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Transaction failed: " + e.getMessage());
    }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
      txtQty.setVisible(false);
   txtExpiry.setVisible(false);
   txtPrice.setEditable(false);
   today1.setVisible(false);
   today2.setVisible(false);
   loadItemsByCategory("consultation");
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
      txtQty.setVisible(true);
    txtExpiry.setVisible(false);
  txtPrice.setEditable(false);
  today2.setVisible(false);
  today1.setVisible(true);
  loadItemsByCategory("procedure");
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton1MouseClicked

    }//GEN-LAST:event_jRadioButton1MouseClicked

    private void jRadioButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton3MouseClicked
 
    }//GEN-LAST:event_jRadioButton3MouseClicked

    private void jRadioButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton2MouseClicked
  
    }//GEN-LAST:event_jRadioButton2MouseClicked

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
      txtQty.setVisible(true);
   txtExpiry.setVisible(true);
   txtExpiry.setEditable(false);
   txtPrice.setEditable(false);
   today2.setVisible(true);
  today1.setVisible(true);
   loadItemsByCategory("medicine");
   
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jRadioButton1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jRadioButton1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1PropertyChange

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new RecepBillNewItem().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmbItem;
    private javax.swing.JButton jButton3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JLabel today;
    private javax.swing.JLabel today1;
    private javax.swing.JLabel today2;
    private javax.swing.JTextField txtExpiry;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtQty;
    // End of variables declaration//GEN-END:variables
}