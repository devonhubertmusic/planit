package planit;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Component;
import java.awt.Container;
import javax.swing.*;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;

import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
public class JavaPDF {
	public JavaPDF(){
    	Document document = new Document();
        try
        {
           Font fontSizeTitle =  FontFactory.getFont(FontFactory.TIMES, 30f);
           PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("MyPlan.pdf"));
           document.open();
           document.add(new Paragraph("Activity Plan", fontSizeTitle));
           //add image
           String logoUrl = "https://raw.githubusercontent.com/devonhubertmusic/planit/master/planit/src/main/resources/images/Planit.png";
           Image logo = Image.getInstance(new URL(logoUrl));
           logo.setAbsolutePosition(400, 50);
           logo.scaleAbsolute(160,100);
           document.add(logo);
           document.close();
           writer.close();
        } catch (Exception e)
        { 
        	e.printStackTrace();
        }
	}
	}