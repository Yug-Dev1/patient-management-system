package Admin;
import db.DBconnection;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.JFrame;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;



public class Admin_adsAnalysis extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Admin_adsAnalysis.class.getName());
private JFreeChart pieChart;
private JFreeChart barChart;

private ChartPanel pieChartPanel;
private ChartPanel barChartPanel;
    public Admin_adsAnalysis() {
        initComponents();
         setLocationRelativeTo(null);
         styleHoverButton(jButton3);
         
         jComboBox1.addActionListener(e -> onDateFilterChange());
        // Create datasets
        DefaultPieDataset typeDataset = createTypeDataset(null);
        DefaultCategoryDataset sourceDataset = createSourceDataset(null);

        // Create charts
        pieChart = createTypePieChart(typeDataset);
    barChart = createSourceBarChart(sourceDataset);

    pieChartPanel = new ChartPanel(pieChart);
    barChartPanel = new ChartPanel(barChart);

    pieChartPanel.setPreferredSize(new Dimension(435, 431));
    barChartPanel.setPreferredSize(new Dimension(455, 375));

    // Attach to existing Swing panels
    piePanel.setLayout(new java.awt.BorderLayout());
    barPanel.setLayout(new java.awt.BorderLayout());

    piePanel.add(pieChartPanel, java.awt.BorderLayout.CENTER);
    barPanel.add(barChartPanel, java.awt.BorderLayout.CENTER);

    piePanel.revalidate();
    barPanel.revalidate();
    
    colouring();
    }
    
  private void onDateFilterChange() {

    String selected = jComboBox1.getSelectedItem().toString();
    Integer days = null;

    switch (selected) {
        case "7 days" -> days = 7;
        case "30 days" -> days = 30;
        case "All time" -> days = null;
    }

    DefaultPieDataset newPieDataset = createTypeDataset(days);
    DefaultCategoryDataset newBarDataset = createSourceDataset(days);

    PiePlot piePlot = (PiePlot) pieChart.getPlot();
    piePlot.setDataset(newPieDataset);

    org.jfree.chart.plot.CategoryPlot barPlot =
            (org.jfree.chart.plot.CategoryPlot) barChart.getPlot();
    barPlot.setDataset(newBarDataset);

    pieChartPanel.repaint();
    barChartPanel.repaint();
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
  private void colouring() {

    Color bg = new Color(237, 231, 227);

    // ===== Swing panel background =====
    piePanel.setBackground(bg);
    barPanel.setBackground(bg);

    pieChartPanel.setBackground(bg);
    barChartPanel.setBackground(bg);

    // ===== Chart background =====
    pieChart.setBackgroundPaint(bg);
    barChart.setBackgroundPaint(bg);

    // ===== Pie plot =====
    PiePlot piePlot = (PiePlot) pieChart.getPlot();
    piePlot.setBackgroundPaint(bg);
    piePlot.setOutlineVisible(false);

    // ===== Bar plot =====
    org.jfree.chart.plot.CategoryPlot barPlot =
            (org.jfree.chart.plot.CategoryPlot) barChart.getPlot();

    barPlot.setBackgroundPaint(bg);
    barPlot.setOutlineVisible(false);
    barPlot.setRangeGridlinesVisible(false);
}
  private JFreeChart createSourceBarChart(DefaultCategoryDataset dataset) {

    return ChartFactory.createBarChart(
        "Appointment Source Analysis",
        "Source",
        "Number of Appointments",
        dataset
    );
}
   private JFreeChart createTypePieChart(DefaultPieDataset dataset) {

    return ChartFactory.createPieChart(
        "Appointment Type Distribution",
        dataset,
        true,
        false,
        false
    );
}
   private DefaultPieDataset createTypeDataset(Integer days) {

    DefaultPieDataset dataset = new DefaultPieDataset();

   
    String sql = """
        SELECT type, COUNT(*) AS total
        FROM appointments
    """;

    if (days != null) {
        sql += " WHERE appointment_time >= DATE_SUB(CURDATE(), INTERVAL ? DAY)";
    }

    sql += " GROUP BY type";

    try (
        Connection con = DBconnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);)
    {   
        if (days != null) {
    ps.setInt(1, days);
}

        ResultSet rs = ps.executeQuery();
   
        while (rs.next()) {
            dataset.setValue(
                rs.getString("type"),
                rs.getInt("total")
            );
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return dataset;
}
   private DefaultCategoryDataset createSourceDataset(Integer days) {

    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    String sql = """
        SELECT source, COUNT(*) AS total
        FROM appointments
       
    """;
     if (days != null) {
        sql += " WHERE appointment_time >= DATE_SUB(CURDATE(), INTERVAL ? DAY)";
    }

    sql += """
    GROUP BY source
    ORDER BY total DESC
""";
    try (
        Connection con = DBconnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);)
      {  
          if (days != null) {
    ps.setInt(1, days);
}
          ResultSet rs = ps.executeQuery();
     
        while (rs.next()) {
            dataset.addValue(
                rs.getInt("total"),   // value
                "Appointments",       // row key (series)
                rs.getString("source")// column key
            );
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return dataset;
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        lblHeaderNm = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        barPanel = new javax.swing.JPanel();
        piePanel = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(20, 105, 122));

        lblHeaderNm.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        lblHeaderNm.setForeground(new java.awt.Color(255, 166, 43));
        lblHeaderNm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeaderNm.setText("Analysis");
        lblHeaderNm.setToolTipText("");

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
                .addGap(145, 145, 145)
                .addComponent(lblHeaderNm, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(257, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(21, Short.MAX_VALUE)
                        .addComponent(lblHeaderNm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.setBackground(new java.awt.Color(237, 231, 227));

        barPanel.setBackground(new java.awt.Color(237, 231, 227));

        javax.swing.GroupLayout barPanelLayout = new javax.swing.GroupLayout(barPanel);
        barPanel.setLayout(barPanelLayout);
        barPanelLayout.setHorizontalGroup(
            barPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 455, Short.MAX_VALUE)
        );
        barPanelLayout.setVerticalGroup(
            barPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 375, Short.MAX_VALUE)
        );

        piePanel.setBackground(new java.awt.Color(237, 231, 227));

        javax.swing.GroupLayout piePanelLayout = new javax.swing.GroupLayout(piePanel);
        piePanel.setLayout(piePanelLayout);
        piePanelLayout.setHorizontalGroup(
            piePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        piePanelLayout.setVerticalGroup(
            piePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jComboBox1.setFont(new java.awt.Font("Kannada MN", 2, 18)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All time", "Yesterday", "7 days", "30 days" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(barPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(piePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(piePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 18, Short.MAX_VALUE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(barPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        AdminDashboard dash = new AdminDashboard();
        dash.setLocationRelativeTo(this);
        dash.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(() -> new Admin_adsAnalysis().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel barPanel;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblHeaderNm;
    private javax.swing.JPanel piePanel;
    // End of variables declaration//GEN-END:variables
}
