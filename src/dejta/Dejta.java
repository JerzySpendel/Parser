/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dejta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author jerzy
 */
public class Dejta {

        public static String kRaJe[]={"Argentina","Australia","Bosnia and Herzegovina","Brazil",
    "Bulgaria","Canada","China","Croatia","France","Germany","Greece","Hungary","India", "Indonesia",
    "Iran","Italy","Latvia","Lithuania","Mexico","Montenegro","New Zealand","Norway","Poland",
    "Portugal","Republic of Macedonia","Romania","Russia","Serbia","Slovenia","South African Republic",
    "Spain","Sweden","Turkey","Ukraine","United Kingdom","Uruguay","USA"
            };
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParserConfigurationException, SAXException, FileNotFoundException, IOException, InterruptedException{ 
PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File("RESULTS"))));
for(String kraj : Dejta.kRaJe){
ZipInputStream zis = new ZipInputStream(new FileInputStream(new File("baza.zip")));   
    ZipEntry e;
    while(true){
        try{
        FileInputStream fis = Parser.OmijanieBlokady(zis);
        if(fis != null){
        Parser.Dane(fis, kraj);
        Parser.plik.delete();
        }else{
            break;
        }
        } catch(Exception ex){
            break;
            
        }
    }
    Parser.pokazDane(kraj, writer);
    Parser.zerujDane();
    System.out.println(kraj);
    }
    }
    
}
