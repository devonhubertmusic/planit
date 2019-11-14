package planit;

import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;

import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Image;

public class JavaPDF {

	private ArrayList<Activity> myActivityList;
	
	public JavaPDF(){
		this.myActivityList = new ArrayList<Activity>();
	}
	public void printPdf(ArrayList<Activity> myActivityList) {
	
        Rectangle pagesize = new Rectangle(612, 792);
		Document document = new Document(pagesize); //(PageSize.A4, 20, 20, 20,20)
        try
        {
           Font fontSizeTitle =  FontFactory.getFont(FontFactory.TIMES, 30f);
           PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("MyPlan.pdf"));
           document.open();
           
           Paragraph header = new Paragraph();
           header.setFont(fontSizeTitle);
           header.setAlignment(Paragraph.ALIGN_CENTER);
          // header.setIndentationLeft(50); no lpnger need thi
           header.add("Activity Plan \n\n");
           document.add(header); //new Paragraph("Activity Plan \n\n", fontSizeTitle));
           //add image
           String logoUrl = "https://raw.githubusercontent.com/devonhubertmusic/planit/master/planit/src/main/resources/images/Planit.png";
           Image logo = Image.getInstance(new URL(logoUrl));
           logo.setAbsolutePosition(400, 50);
           logo.scaleAbsolute(160,100);
           document.add(logo);
         //  document.add("\n");
           int size = myActivityList.size();    
           for(int row = 0; row < size; row++) {
                   Activity temp = myActivityList.get(row);
        			   String tmp = temp.toString();
        			   document.add(new Paragraph(tmp));
               }
   //TO ADD : total time, type, and total cost
           //change formatting
           //maybe add tables?or list activi?
           //message box when pdf is currently open or 
           //make each pdf be named something differe
           document.close();
           writer.close();
        } catch (Exception e)
        { 
        	e.printStackTrace();
        }
	}
	}
