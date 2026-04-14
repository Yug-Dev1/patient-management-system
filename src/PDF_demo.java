import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class PDF_demo
{
    public static void main(String args[]) 
    {
        try
        {
            Document doc=new Document();
            PdfWriter.getInstance(doc,new FileOutputStream("D:/Sample.pdf"));
            
            doc.open();
            
            doc.add(new Paragraph("Hello....\nI have created this using JAVA\n\n"));
            doc.add(new Paragraph("This Is You Receipt!!\n\n"));
            doc.add(new Paragraph("Thank you for contacting us...."));
            
            
            doc.close();
            
            try
            {
                //sender's email and password
                final String unm="yb192006@gmail.com";
                final String pwd="vykn ewrs rswv rfal";
                final String recp="jdgt.yhb.19@gmail.com";

                //SMTP properties
                Properties props=new Properties();
                props.put("mail.smtp.auth","true");
                props.put("mail.smtp.starttls.enable","true");
                props.put("mail.smtp.starttls.required","true");
                props.put("mail.smtp.host","smtp.gmail.com");
                props.put("mail.smtp.port","587");
                props.put("mail.smtp.ssl.protocols","TLSv1.2");

                //create session
                Session s=Session.getInstance(props,new javax.mail.Authenticator()
                {
                        protected javax.mail.PasswordAuthentication getPasswordAuthentication()
                        {
                            return new javax.mail.PasswordAuthentication(unm,pwd);
                        }
                });
                
                
                try
                {
                    Message msg=new MimeMessage(s);
                    msg.setFrom(new InternetAddress(unm));	
                    msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(recp));
                    msg.setSubject("Attachement Sending Process!!");
                    msg.setText("Dear User, \nThis is the attached File!!");

                    //send msg
                    MimeBodyPart pdf=new MimeBodyPart();
                    pdf.setText("skfksjfhksjfhkjsf");
                    pdf.attachFile(new File("D:/Sample.pdf"));

                    Multipart m=new MimeMultipart();
                    m.addBodyPart(pdf);
                    msg.setContent(m);

                    Transport.send(msg);
                    System.out.println("Attachment sent with an email");
                }
                catch(Exception x)
                {
                    System.out.println(x);
                }
            }
            catch(Exception e)
            {
                System.out.println(e);
            }

        }
        catch(Exception e1)
        {
            System.out.println(e1);
        }
    }
}