package Reception;

// ===================== DB =====================
import db.DBconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

// ===================== PDF (iText 5.x) =====================
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

// ===================== FILE IO =====================
import java.io.File;
import java.io.FileOutputStream;

// ===================== EMAIL =====================
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

// ===================== SWING =====================
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

// ===================== CONCURRENCY =====================
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// ===================== LOGGING =====================
import java.util.logging.Logger;


public class Recep_NewBill extends javax.swing.JFrame {
    //pool create-thread-for 5 threads 
    public final ExecutorService emailExecutor = Executors.newFixedThreadPool(5);
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Recep_NewBill.class.getName());
    private int billId = 0;
   
    public Recep_NewBill() {
        initComponents();
        addPlaceHolderFields();
        initHoverEffects1();
        initPaymentCommands();
        initStatusCommands();
        lockUI();
    }
    
    // ===================== PDF HELPER METHODS =====================

// Generic table cell (used for normal rows)
private PdfPCell cell(String text) {
    PdfPCell c = new PdfPCell(
        new Phrase(text, new Font(Font.FontFamily.HELVETICA, 9))
    );
    c.setPadding(5);
    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
    return c;
}

// Table header cell (for column headings)
private void addHeader(PdfPTable table, String text) {
    PdfPCell c = new PdfPCell(
        new Phrase(text, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD))
    );
    c.setBackgroundColor(BaseColor.LIGHT_GRAY);
    c.setPadding(6);
    c.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(c);
}

// Meta info cell (Bill ID, Status etc.)
private PdfPCell metaCell(String text, Font font) {
    PdfPCell c = new PdfPCell(new Phrase(text, font));
    c.setBorder(Rectangle.NO_BORDER);
    c.setPadding(4);
    return c;
}

// Totals section cell (right-aligned, bold)
private PdfPCell totalCell(String text) {
    PdfPCell c = new PdfPCell(
        new Phrase(text, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD))
    );
    c.setPadding(6);
    c.setHorizontalAlignment(Element.ALIGN_RIGHT);
    return c;
}

// Optional helper for currency formatting
private String money(String value) {
    return "₹" + value;
}

    private void initStatusCommands() {
    jRadioButton1.setActionCommand("paid");
    jRadioButton2.setActionCommand("unpaid");
}
    private void initPaymentCommands() {
    jRadioButton3.setActionCommand("cash");
    jRadioButton4.setActionCommand("upi");
    jRadioButton5.setActionCommand("card");
    jRadioButton6.setActionCommand("other");
}
    
    private void addPlaceHolderFields(){
     addPlaceholder(txtAppointmentId, "Search Appointment ID");  
     addPlaceholder(txtDiscount, "Enter Discount (%)"); 
     addPlaceholder(txtEmail, "@gmail.com"); 
     
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
    
 
    private void initHoverEffects1() {
    styleHoverButton1(jButton4);
    styleHoverButton1(jButton5);
    styleHoverButton1(jButton6);
    styleHoverButton1(jButton7);
    styleHoverButton1(btnFilter);
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
    
    private void lockUI() {
    jButton4.setEnabled(false);
    tblItems.setEnabled(false);
    txtDiscount.setEnabled(false);
    jButton5.setEnabled(false);
}
    private void unlockUI(){
         jButton4.setEnabled(true);
    tblItems.setEnabled(true);
    txtDiscount.setEnabled(true);
    jButton5.setEnabled(true);
     }
    
   private void recalcTotals() {

    String sql = "SELECT SUM(total) FROM invoice WHERE bill_id = ?";

    try (Connection con = DBconnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, billId);
        ResultSet rs = ps.executeQuery();

        double total = 0;
        if (rs.next()) {
            total = rs.getDouble(1);
        }

        double discountPercent = getDiscountPercentage();
        double discountAmount = (total * discountPercent) / 100;
        double net = total - discountAmount;

        lblTotal.setText(String.format("%.2f", total));
        lblDiscount.setText(String.format("%.2f", discountAmount));
        lblNet.setText(String.format("%.2f", net));

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
    }
}
   private double getDiscountPercentage() {
    String text = txtDiscount.getText().trim();

    if (text.isEmpty() || text.equals("Enter Discount")) {
        return 0;
    }

    try {
        double percent = Double.parseDouble(text);

        // clamp between 0 and 100
        if (percent < 0) percent = 0;
        if (percent > 100) percent = 100;

        return percent;
    } catch (NumberFormatException e) {
        return 0;
    }
}
    
private Integer selectedAppointmentId = null;
private Integer selectedPatientId     = null;
private Integer selectedDoctorId      = null;

    private void searchAppointment() {

    String text = txtAppointmentId.getText().trim();

    if (text.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter Appointment ID.");
        return;
    }

    int appId;
    try {
        appId = Integer.parseInt(text);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Invalid Appointment ID.");
        return;
    }

    String sql =
        "SELECT a.appointment_id, a.patient_id, a.doctor_id, " +
        "       p.full_name, p.dob, p.phone, p.blood_group, " +
        "       d.name AS doctor_name " +
        "FROM appointments a " +
        "JOIN patients p ON a.patient_id = p.patient_id " +
        "LEFT JOIN user_credentials d ON a.doctor_id = d.user_id " +
        "WHERE a.appointment_id = ?";

    try (Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, appId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                // save IDs for later use when creating bill
                selectedAppointmentId = rs.getInt("appointment_id");
                selectedPatientId     = rs.getInt("patient_id");
                selectedDoctorId      = rs.getInt("doctor_id");   // may be null

                // set labels
                lblName.setText(rs.getString("full_name"));
                lblDOB.setText(rs.getString("dob"));              // or format
                lblPhone.setText(rs.getString("phone"));
                lblBloodGrp.setText(rs.getString("blood_group"));

                String docName = rs.getString("doctor_name");
                lblDoctor.setText(docName != null ? docName : "—");

            } else {
                JOptionPane.showMessageDialog(this,
                    "No appointment found for ID: " + appId);
               
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
            "Error while searching appointment: " + e.getMessage());
    }
}
    
    public void refreshItemsTable() {

    DefaultTableModel model = (DefaultTableModel) tblItems.getModel();
    model.setRowCount(0);

    String sql = """
        SELECT item_name, item_type, qty, price
        FROM invoice
        WHERE bill_id = ?
    """;
    recalcTotals();//refreshing the totalsss
    try (Connection con = DBconnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, billId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("item_name"),
                rs.getString("item_type"),
                rs.getInt("qty"),
                rs.getDouble("price")
            });
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
    
    private void createBill() {

    if (selectedPatientId == null || selectedAppointmentId == null) {
        JOptionPane.showMessageDialog(this, "Search appointment first");
        return;
    }

    String sql = """
        INSERT INTO patient_billing
        (patient_id, appointment_id, doctor_id, status)
        VALUES (?, ?, ?, 'unpaid')
    """;

    try (Connection con = DBconnection.getConnection();
         PreparedStatement ps = con.prepareStatement(
             sql, Statement.RETURN_GENERATED_KEYS)) {

        ps.setInt(1, selectedPatientId);
        ps.setInt(2, selectedAppointmentId);
        ps.setInt(3, selectedDoctorId);

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            billId = rs.getInt(1);
        }

        unlockUI();
        JOptionPane.showMessageDialog(this, "Bill created");

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
    
    private String generateInvoicePDF(int billId) {

    String baseDir = System.getProperty("user.home") + "/Documents/ProjectPdf";
    new File(baseDir).mkdirs();

    String filePath = baseDir + "/bill_" + billId + ".pdf";

    try {
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(filePath));
        doc.open();

        // ===================== FONTS =====================
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.WHITE);
        Font sectionFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 10);
        Font boldFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);

        // ===================== HEADER BANNER =====================
        PdfPTable header = new PdfPTable(1);
        header.setWidthPercentage(100);
        PdfPCell hCell = new PdfPCell(new Phrase("STACK OVERFLOW URGENT CARE", headerFont));
        hCell.setBackgroundColor(new BaseColor(20,105,122));
        hCell.setPadding(12);
        hCell.setBorder(Rectangle.NO_BORDER);
        hCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(hCell);
        doc.add(header);

        doc.add(Chunk.NEWLINE);

        // ===================== DOCTOR & PATIENT INFO =====================
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);
        infoTable.setWidths(new float[]{1,1});

        PdfPCell doctorCell = new PdfPCell();
        doctorCell.addElement(new Phrase("Doctor Information", sectionFont));
        doctorCell.addElement(new Phrase("Name: " + lblDoctor.getText(), normalFont));
        doctorCell.setPadding(8);
        doctorCell.setBorder(Rectangle.BOX);

        PdfPCell patientCell = new PdfPCell();
        patientCell.addElement(new Phrase("Patient Information", sectionFont));
        patientCell.addElement(new Phrase("Name: " + lblName.getText(), normalFont));
        patientCell.addElement(new Phrase("Phone: " + lblPhone.getText(), normalFont));
        patientCell.addElement(new Phrase("Blood Group: " + lblBloodGrp.getText(), normalFont));
        patientCell.setPadding(8);
        patientCell.setBorder(Rectangle.BOX);

        infoTable.addCell(doctorCell);
        infoTable.addCell(patientCell);
        doc.add(infoTable);

        doc.add(Chunk.NEWLINE);

        // ===================== BILL META =====================
        PdfPTable meta = new PdfPTable(2);
        meta.setWidthPercentage(100);
        meta.addCell(metaCell("Bill ID: " + billId, boldFont));
        meta.addCell(metaCell("Status: " + buttonGroup1.getSelection().getActionCommand().toUpperCase(), boldFont));
        doc.add(meta);

        doc.add(Chunk.NEWLINE);

        // ===================== ITEMS TABLE =====================
        PdfPTable itemTable = new PdfPTable(5);
        itemTable.setWidthPercentage(100);
        itemTable.setWidths(new float[]{3,2,1,2,2});

        addHeader(itemTable, "Item");
        addHeader(itemTable, "Type");
        addHeader(itemTable, "Qty");
        addHeader(itemTable, "Price");
        addHeader(itemTable, "Total");

        String sql = "SELECT item_name, item_type, qty, price, total FROM invoice WHERE bill_id = ?";
        try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, billId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                itemTable.addCell(cell(rs.getString("item_name")));
                itemTable.addCell(cell(rs.getString("item_type")));
                itemTable.addCell(cell(rs.getInt("qty")+""));
                itemTable.addCell(cell("₹"+rs.getDouble("price")));
                itemTable.addCell(cell("₹"+rs.getDouble("total")));
            }
        }

        doc.add(itemTable);
        doc.add(Chunk.NEWLINE);

        // ===================== TOTALS =====================
        PdfPTable totals = new PdfPTable(2);
        totals.setWidthPercentage(40);
        totals.setHorizontalAlignment(Element.ALIGN_RIGHT);

        totals.addCell(totalCell("Total"));
        totals.addCell(totalCell("₹" + lblTotal.getText()));

        totals.addCell(totalCell("Discount"));
        totals.addCell(totalCell("₹" + lblDiscount.getText()));

        totals.addCell(totalCell("Net Amount"));
        totals.addCell(totalCell("₹" + lblNet.getText()));

        doc.add(totals);

        doc.add(Chunk.NEWLINE);

        // ===================== FOOTER =====================
        PdfPTable footer = new PdfPTable(1);
        footer.setWidthPercentage(100);
        PdfPCell fCell = new PdfPCell(new Phrase(
            "Stack Overflow Urgent Care | Contact: +91-XXXXXXXXXX",
            normalFont
        ));
        fCell.setBackgroundColor(new BaseColor(20,105,122));
        fCell.setPadding(10);
        fCell.setBorder(Rectangle.NO_BORDER);
        fCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        fCell.setPhrase(new Phrase(fCell.getPhrase().getContent(), new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.WHITE)));
        footer.addCell(fCell);
        doc.add(footer);

        doc.close();
        return filePath;

    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
    private void mail(String email) {

    final String unm = "yb192006@gmail.com";
    final String pwd = "evdp oqsg elql pohz";

    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    Session session = Session.getInstance(props, new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(unm, pwd);
        }
    });
    emailExecutor.submit(()->{
    try {
        
        
        System.out.println("Thread Started for email sending "+Thread.currentThread().getName());
        // 1️⃣ Generate PDF
        String pdfPath = generateInvoicePDF(billId);

        // 2️⃣ Create email
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(unm));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        msg.setSubject("Your Invoice – StackOverflow Clinic");

        // 3️⃣ Text part
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(
            "Hello,\n\nPlease find your invoice attached.\n\n" +
            "Thank you for choosing StackOverflow Clinic."
        );

        // 4️⃣ PDF attachment
        MimeBodyPart pdfPart = new MimeBodyPart();
        pdfPart.attachFile(pdfPath);

        // 5️⃣ Combine
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(textPart);
        multipart.addBodyPart(pdfPart);

        msg.setContent(multipart);

        // 6️⃣ Send
        Transport.send(msg);
        //Thread.sleep(3000);

        System.out.println("Invoice email sent successfully.");

    } catch (Exception e) {
        e.printStackTrace();
    }
    finally
    {
         emailExecutor.shutdown();
    }
    });
} 

private void sendMailIfProvided() {
    String email = txtEmail.getText().trim();

    if (email.isEmpty() || email.equals("@gmail.com")) return;

    if (!isValidEmail(email)) {
        JOptionPane.showMessageDialog(this, "Invalid email address");
        return;
    }

    try {
        mail(email);
    } catch (Exception e) {
        // IMPORTANT: Do not stop bill flow
        JOptionPane.showMessageDialog(this,
            "Bill saved, but email could not be sent.");
    }
}
     private boolean isValidEmail(String email) {
    return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
}
 
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        lblHeaderNm = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtAppointmentId = new javax.swing.JTextField();
        btnFilter = new javax.swing.JButton();
        lblHeaderNm1 = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblHeaderNm5 = new javax.swing.JLabel();
        lblPhone = new javax.swing.JLabel();
        lblHeaderNm4 = new javax.swing.JLabel();
        lblBloodGrp = new javax.swing.JLabel();
        lblHeaderNm3 = new javax.swing.JLabel();
        lblDOB = new javax.swing.JLabel();
        lblHeaderNm6 = new javax.swing.JLabel();
        lblDoctor = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        lblHeaderNm7 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblItems = new javax.swing.JTable();
        txtDiscount = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        lblHeaderNm8 = new javax.swing.JLabel();
        lblHeaderNm9 = new javax.swing.JLabel();
        lblHeaderNm10 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblDiscount = new javax.swing.JLabel();
        lblNet = new javax.swing.JLabel();
        lblHeaderNm14 = new javax.swing.JLabel();
        lblHeaderNm15 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jButton5 = new javax.swing.JButton();
        lblHeaderNm16 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(20, 105, 122));

        lblHeaderNm.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        lblHeaderNm.setForeground(new java.awt.Color(255, 166, 43));
        lblHeaderNm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeaderNm.setText("New Bill");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(243, 243, 243)
                .addComponent(lblHeaderNm, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 290, Short.MAX_VALUE)
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

        jPanel2.setBackground(new java.awt.Color(237, 231, 227));

        txtAppointmentId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAppointmentIdActionPerformed(evt);
            }
        });

        btnFilter.setBackground(new java.awt.Color(22, 105, 122));
        btnFilter.setFont(new java.awt.Font("Kohinoor Bangla", 3, 18)); // NOI18N
        btnFilter.setForeground(new java.awt.Color(255, 255, 255));
        btnFilter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Reception/search-icon-png-9.png"))); // NOI18N
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });

        lblHeaderNm1.setFont(new java.awt.Font("October Compressed Devanagari", 0, 18)); // NOI18N
        lblHeaderNm1.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm1.setText("Name");

        lblName.setBackground(new java.awt.Color(0, 0, 0));
        lblName.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        lblName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblName.setText("-");

        lblHeaderNm5.setFont(new java.awt.Font("October Compressed Devanagari", 0, 18)); // NOI18N
        lblHeaderNm5.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm5.setText("Phone");

        lblPhone.setBackground(new java.awt.Color(0, 0, 0));
        lblPhone.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        lblPhone.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPhone.setText("-");

        lblHeaderNm4.setFont(new java.awt.Font("October Compressed Devanagari", 0, 18)); // NOI18N
        lblHeaderNm4.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm4.setText("Blood Grp");

        lblBloodGrp.setBackground(new java.awt.Color(0, 0, 0));
        lblBloodGrp.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        lblBloodGrp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBloodGrp.setText("-");

        lblHeaderNm3.setFont(new java.awt.Font("October Compressed Devanagari", 0, 18)); // NOI18N
        lblHeaderNm3.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm3.setText("DOB");

        lblDOB.setBackground(new java.awt.Color(0, 0, 0));
        lblDOB.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        lblDOB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDOB.setText("-");

        lblHeaderNm6.setFont(new java.awt.Font("October Compressed Devanagari", 0, 18)); // NOI18N
        lblHeaderNm6.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm6.setText("Doctor");

        lblDoctor.setBackground(new java.awt.Color(0, 0, 0));
        lblDoctor.setFont(new java.awt.Font("October Compressed Devanagari", 0, 36)); // NOI18N
        lblDoctor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDoctor.setText("-");

        jButton7.setBackground(new java.awt.Color(22, 105, 122));
        jButton7.setFont(new java.awt.Font("Kohinoor Bangla", 2, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Create Bill");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblHeaderNm4, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(lblHeaderNm1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblHeaderNm3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(txtAppointmentId, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addComponent(btnFilter))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                            .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(1, 1, 1))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(lblHeaderNm5, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(127, 127, 127)))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblHeaderNm6)
                                        .addComponent(lblDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(lblPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(26, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblBloodGrp, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton7)
                        .addGap(39, 39, 39))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAppointmentId, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHeaderNm1)
                    .addComponent(lblHeaderNm3))
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHeaderNm5)
                    .addComponent(lblHeaderNm6))
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHeaderNm4)
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblBloodGrp)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton7)
                        .addGap(32, 32, 32))))
        );

        jPanel4.setBackground(new java.awt.Color(237, 231, 227));

        lblHeaderNm7.setFont(new java.awt.Font("October Compressed Devanagari", 2, 24)); // NOI18N
        lblHeaderNm7.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm7.setText("Billing Item");

        jButton4.setBackground(new java.awt.Color(22, 105, 122));
        jButton4.setFont(new java.awt.Font("Kohinoor Bangla", 2, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Reception/992651.png"))); // NOI18N
        jButton4.setText("New Item");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        tblItems.setFont(new java.awt.Font("Monospaced", 0, 10)); // NOI18N
        tblItems.setForeground(new java.awt.Color(20, 105, 122));
        tblItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Item Name", "Type", "Qty", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblItems);

        txtDiscount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiscountActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(22, 105, 122));
        jButton6.setFont(new java.awt.Font("Kohinoor Bangla", 2, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Reception/1345874.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(lblHeaderNm7, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton6)))))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHeaderNm7)
                    .addComponent(jButton6))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(237, 231, 227));

        lblHeaderNm8.setFont(new java.awt.Font("October Compressed Devanagari", 0, 18)); // NOI18N
        lblHeaderNm8.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm8.setText("Total");

        lblHeaderNm9.setFont(new java.awt.Font("October Compressed Devanagari", 0, 18)); // NOI18N
        lblHeaderNm9.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm9.setText("Discount");

        lblHeaderNm10.setFont(new java.awt.Font("October Compressed Devanagari", 1, 18)); // NOI18N
        lblHeaderNm10.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm10.setText("Net Amount");

        lblTotal.setFont(new java.awt.Font("October Compressed Devanagari", 0, 18)); // NOI18N
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTotal.setText("Amt1");

        lblDiscount.setFont(new java.awt.Font("October Compressed Devanagari", 0, 18)); // NOI18N
        lblDiscount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDiscount.setText("Amt2");

        lblNet.setFont(new java.awt.Font("October Compressed Devanagari", 1, 18)); // NOI18N
        lblNet.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNet.setText("Amt3");

        lblHeaderNm14.setFont(new java.awt.Font("October Compressed Devanagari", 1, 18)); // NOI18N
        lblHeaderNm14.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm14.setText("Status");

        lblHeaderNm15.setFont(new java.awt.Font("October Compressed Devanagari", 1, 18)); // NOI18N
        lblHeaderNm15.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm15.setText("Enter Email");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        jRadioButton1.setText("Paid");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        jRadioButton2.setSelected(true);
        jRadioButton2.setText("Unpaid");

        buttonGroup2.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        jRadioButton3.setText("Cash");

        buttonGroup2.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        jRadioButton4.setText("UPI");

        buttonGroup2.add(jRadioButton5);
        jRadioButton5.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        jRadioButton5.setText("Card");

        buttonGroup2.add(jRadioButton6);
        jRadioButton6.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        jRadioButton6.setSelected(true);
        jRadioButton6.setText("Other");

        jButton5.setBackground(new java.awt.Color(22, 105, 122));
        jButton5.setFont(new java.awt.Font("Kohinoor Bangla", 2, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Doctor/black-checkmark-png-4.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        lblHeaderNm16.setFont(new java.awt.Font("October Compressed Devanagari", 1, 18)); // NOI18N
        lblHeaderNm16.setForeground(new java.awt.Color(20, 105, 122));
        lblHeaderNm16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHeaderNm16.setText("Payment Mode");

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHeaderNm9, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHeaderNm10, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHeaderNm8, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNet, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton1)
                    .addComponent(lblHeaderNm14, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton2))
                .addGap(84, 84, 84)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jRadioButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(128, 128, 128))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lblHeaderNm16, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblHeaderNm15, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(169, 169, 169))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jRadioButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5)
                        .addGap(19, 19, 19))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblHeaderNm14)
                        .addComponent(lblHeaderNm16)
                        .addComponent(lblHeaderNm15))
                    .addComponent(lblHeaderNm8))
                .addGap(0, 0, 0)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblHeaderNm9)
                            .addComponent(lblDiscount)
                            .addComponent(jRadioButton1)
                            .addComponent(jRadioButton3)
                            .addComponent(jRadioButton5)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jRadioButton2)
                                    .addComponent(jRadioButton4)
                                    .addComponent(jRadioButton6))
                                .addGap(19, 19, 19))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblNet, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblHeaderNm10))
                                .addContainerGap())))
                    .addComponent(jButton5)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        searchAppointment();
    }//GEN-LAST:event_btnFilterActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
   RecepBillNewItem itemFrame =
        new RecepBillNewItem(billId, this);

    itemFrame.setLocationRelativeTo(this);
    itemFrame.setVisible(true);

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       
    String status = buttonGroup1.getSelection().getActionCommand();
    String payment = buttonGroup2.getSelection().getActionCommand();

    String sql = """
        UPDATE patient_billing
        SET total_amount = ?, discount = ?, net_amount = ?,
            status = ?, payment_mode = ?
        WHERE bill_id = ?
    """;

    try (Connection con = DBconnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setDouble(1, Double.parseDouble(lblTotal.getText()));
        ps.setDouble(2, getDiscountPercentage());
        ps.setDouble(3, Double.parseDouble(lblNet.getText()));
        ps.setString(4, status);
        ps.setString(5, payment);
        ps.setInt(6, billId);

        ps.executeUpdate();
        sendMailIfProvided();
        JOptionPane.showMessageDialog(this, "Bill finalized");
        this.dispose();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }      
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
       createBill();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtDiscountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiscountActionPerformed
       recalcTotals();
    }//GEN-LAST:event_txtDiscountActionPerformed

    private void txtAppointmentIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAppointmentIdActionPerformed
         searchAppointment();
    }//GEN-LAST:event_txtAppointmentIdActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Recep_NewBill().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFilter;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBloodGrp;
    private javax.swing.JLabel lblDOB;
    private javax.swing.JLabel lblDiscount;
    private javax.swing.JLabel lblDoctor;
    private javax.swing.JLabel lblHeaderNm;
    private javax.swing.JLabel lblHeaderNm1;
    private javax.swing.JLabel lblHeaderNm10;
    private javax.swing.JLabel lblHeaderNm14;
    private javax.swing.JLabel lblHeaderNm15;
    private javax.swing.JLabel lblHeaderNm16;
    private javax.swing.JLabel lblHeaderNm3;
    private javax.swing.JLabel lblHeaderNm4;
    private javax.swing.JLabel lblHeaderNm5;
    private javax.swing.JLabel lblHeaderNm6;
    private javax.swing.JLabel lblHeaderNm7;
    private javax.swing.JLabel lblHeaderNm8;
    private javax.swing.JLabel lblHeaderNm9;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNet;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblItems;
    private javax.swing.JTextField txtAppointmentId;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtEmail;
    // End of variables declaration//GEN-END:variables
}
