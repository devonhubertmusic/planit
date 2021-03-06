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

/**
* JavaPDF handles the saving of a generated plan
* as a pdf.
*/
public class JavaPDF extends JComponent implements Accessible {

  private ArrayList<Activity> myActivityList;
  private Activity myActivity;
  
  public JavaPDF(){
    this.myActivityList = new ArrayList<Activity>();
    this.myActivity = new Activity();
  }
  public void printPdf(ArrayList<Activity> myActivityList) {
	  
	   boolean isOpened = false;
       JFileChooser chooser = new JFileChooser();
       chooser.setCurrentDirectory(new java.io.File("."));
       chooser.setDialogTitle("Save Backup");
       chooser.setApproveButtonText("Save");
       chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
       chooser.setAcceptAllFileFilterUsed(false);

       if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
       
        Rectangle pagesize = new Rectangle(612, 792);
        Document document = new Document(pagesize);
        try
        {

           Font fontSizeTitle =  FontFactory.getFont(FontFactory.HELVETICA, 30f);
           PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File(chooser.getSelectedFile(), "MyPlan.pdf")));
           document.open();

           Paragraph header = new Paragraph();
           header.setFont(fontSizeTitle);
           header.setAlignment(Paragraph.ALIGN_CENTER);
           header.add("Activity Plan \n\n\n");
           document.add(header); 
           String logoUrl = "https://raw.githubusercontent.com/devonhubertmusic/planit/master/planit/src/main/resources/images/Planit.png";
           Image logo = Image.getInstance(new URL(logoUrl));
           logo.setAbsolutePosition(420, 25);
           logo.scaleAbsolute(160,125);
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

        double totalTime = 0.0;
        double totalCost = 0.0;

 
        //fills the table with information of each activity
           for(int row = 0; row < size; row++) {
                   Activity temp = myActivityList.get(row);
                 String tmp = temp.toString();
                 table.addCell(tmp);
                  double tempTime = myActivityList.get(row).getActualTime();
                  totalTime += tempTime;
                  String tmpTime = Double.valueOf(tempTime).toString();
                  table.addCell(tmpTime + " minutes");
                  double tempCost = myActivityList.get(row).getMaxCost();
                  totalCost += tempCost;
                  String tmpCost = Double.valueOf(tempCost).toString();
                  table.addCell("$" + tmpCost);
               }

            document.add(table);

            String tmpTotalTime = Double.valueOf(totalTime).toString();
            String tmpTotalCost = Double.valueOf(totalCost).toString();

            Paragraph totals = new Paragraph();
            totals.setAlignment(Paragraph.ALIGN_CENTER);
            totals.add("\n");
            totals.add("Total Time: " + tmpTotalTime + " minutes\n");
            totals.add("Total Cost: $" + tmpTotalCost);  
            document.add(totals);

           document.close();
           writer.close();
           
        } catch (Exception e)
        { 
     	   isOpened = true;
     	   //this means the pdf is opened by another app, it wont be able to be saved
        	   JOptionPane.showMessageDialog(null, "MyPlan.pdf is currently opened by another application. \n" 
        			   +"Please close the PDF and try again.");
        }
        
        if(isOpened = false) {
            //this means there wasn't an error and the PDF successfully saved
            JOptionPane.showMessageDialog(null, "PDF Saved.");
        }
  }
  }
  }
