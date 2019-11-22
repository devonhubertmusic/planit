package planit;

import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import javax.swing.JFileChooser;
import java.awt.Component;
import java.awt.Container;
import java.io.File;
import javax.accessibility.*;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.Element;
import com.itextpdf.text.BaseColor;

public class JavaPDF extends JComponent implements Accessible {

  private ArrayList<Activity> myActivityList;
  private Activity myActivity;
  
  public JavaPDF(){
    this.myActivityList = new ArrayList<Activity>();
    this.myActivity = new Activity();
  }
  public void printPdf(ArrayList<Activity> myActivityList) {
    
        JFileChooser chooser = new JFileChooser();
       chooser.setCurrentDirectory(new java.io.File("."));
       chooser.setDialogTitle("Save Backup");
       chooser.setApproveButtonText("Save");
       chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
       chooser.setAcceptAllFileFilterUsed(false);

       if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
       
        Rectangle pagesize = new Rectangle(612, 792);
    Document document = new Document(pagesize); //(PageSize.A4, 20, 20, 20,20)
        try
        {
           JOptionPane.showMessageDialog(null, "PDF Saved.");
           Font fontSizeTitle =  FontFactory.getFont(FontFactory.TIMES, 30f);
           PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File(chooser.getSelectedFile(), "MyPlan.pdf")));//"MyPlan.pdf"));
           document.open();

           Paragraph header = new Paragraph();
           header.setFont(fontSizeTitle);
           header.setAlignment(Paragraph.ALIGN_CENTER);
           header.add("Activity Plan \n\n");
           document.add(header); 
           String logoUrl = "https://raw.githubusercontent.com/devonhubertmusic/planit/master/planit/src/main/resources/images/Planit.png";
           Image logo = Image.getInstance(new URL(logoUrl));
           logo.setAbsolutePosition(400, 50);
           logo.scaleAbsolute(160,100);
           document.add(logo);
           int size = myActivityList.size(); 


        // a table with three columns
        PdfPTable table = new PdfPTable(3);
        // the cell object
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Activity"));
        cell.setRowspan(2);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Activity Time"));
        cell.setRowspan(2);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Activity Cost"));
        cell.setRowspan(2);
        table.addCell(cell);

 

           for(int row = 0; row < size; row++) {
                   Activity temp = myActivityList.get(row);
                 String tmp = temp.toString();
                 table.addCell(tmp);
                  double tempTime = myActivityList.get(row).getActualTime();
                  String tmpTime = Double.valueOf(tempTime).toString();
                  table.addCell(tmpTime + " minutes");
                  double tempCost = myActivityList.get(row).getMaxCost();
                  String tmpCost = Double.valueOf(tempCost).toString();
                  table.addCell("$" + tmpCost);
               }

            document.add(table);
   //TO ADD : total time, type, and total cost
           //change formatting
           //maybe add tables?or list activi?
           //message box when pdf is currently open or 
           //make each pdf be named something differe
           //also pdf saved message should only pop up ONLY when the pdf is saved 

           document.close();
           writer.close();
        } catch (Exception e)
        { 
          e.printStackTrace();
        }
  }
  }
  }
