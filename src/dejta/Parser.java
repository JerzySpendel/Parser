/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dejta;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author jerzy
 */
public class Parser {
    private static int tanki[] = {0,0,0,0};
    private static int heli[] = {0,0,0,0};
    private static int gun[] = {0,0,0,0};
    
    private static int land[] = {0,0,0,0};
    private static int manu[] = {0,0,0,0};
    private static int constr[] = {0,0,0,0};
    
    private static int takiSamMilitary = 0;
    private static int takiSamEko = 0;
    
    public static File plik;

    public static void Dane(InputStream f, String kraj) throws ParserConfigurationException, SAXException, IOException{
       
        
        DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
        
            DocumentBuilder builder = DBF.newDocumentBuilder();
            
            Document dokument = builder.parse(f);
            NodeList residenceL = dokument.getElementsByTagName("residence");
            Element residence = (Element) residenceL.item(0);
            
            NodeList elementN = residence.getChildNodes();
            Element country = (Element)elementN.item(1);

            NodeList countryL = country.getChildNodes();
            Element KRAJ = (Element)countryL.item(0);
            
            if(KRAJ.getTextContent().equals(new String(kraj))){

            Element skills = (Element)dokument.getElementsByTagName("skills").item(0);
            
            Element military = (Element)skills.getChildNodes().item(0);
            Element ekonomia = (Element) skills.getChildNodes().item(1);
            
            NodeList skille = military.getChildNodes();
            NodeList ekonomie = ekonomia.getChildNodes();
            //Pobranie wartości skilli poszczególnych broni und eko
            NodeList weapon =  skille.item(0).getChildNodes();
            NodeList helicopter =  skille.item(1).getChildNodes();
            NodeList tank =  skille.item(2).getChildNodes();
            
            NodeList manu =  ekonomie.item(0).getChildNodes();
            NodeList land =  ekonomie.item(1).getChildNodes();
            NodeList constr =  ekonomie.item(2).getChildNodes();

            NodeList najlepszyEKO = ktoryWiekszyEkonomia(manu,land,constr);

            NodeList najlepszy = ktoryWiekszyMilitary(weapon,helicopter,tank);

            }
            
            
            
            
    }
    
    private static NodeList ktoryWiekszyMilitary(NodeList a, NodeList b, NodeList c){
        double iA = Double.valueOf(a.item(1).getTextContent());
        double iB = Double.valueOf(b.item(1).getTextContent());
        double iC = Double.valueOf(c.item(1).getTextContent());
        
        if((iA > iB && iA > iC) || (iB > iA && iB > iC) || (iC > iA && iC > iB)){
        if(Math.max(iA, iB) == iA && Math.max(iA, iC) == iA){
        Parser.gun[0]++;
                    if(iA >= 4.0){
                Parser.gun[1]++;
            }
            else if(iA >= 3.0){
                Parser.gun[2]++;
               
            }
            else if(iA >= 2.0){
                Parser.gun[3]++;
            }
            return a;
        
        }
        if(Math.max(iA, iB) == iB && Math.max(iB, iC) == iB){
            Parser.heli[0]++;
            
                        if(iB >= 4.0){
                Parser.heli[1]++;
            }
            else if(iB >= 3.0){
                Parser.heli[2]++;
               
            }
            else if(iB >= 2.0){
                Parser.heli[3]++;
            }
            return b;
        }
        if(Math.max(iA, iC) == iC && Math.max(iB, iC) == iC){
            Parser.tanki[0]++;
            
                                    if(iC >= 4.0){
                Parser.tanki[1]++;
            }
            else if(iC >= 3.0){
                Parser.tanki[2]++;               
            }
            else if(iC >= 2.0){
                Parser.tanki[3]++;
            }
            return c;
        }
        else {
            return null;
        }
        } else{
            Parser.takiSamMilitary++;
            return null;
        }
        
 
    }
    private static NodeList ktoryWiekszyEkonomia(NodeList a, NodeList b, NodeList c){
        double iA = Double.valueOf(a.item(1).getTextContent());
        double iB = Double.valueOf(b.item(1).getTextContent());
        double iC = Double.valueOf(c.item(1).getTextContent());
        
        if((iA > iB && iA > iC) || (iB > iA && iB > iC) || (iC > iA && iC > iB)){
        if(Math.max(iA, iB) == iA && Math.max(iA, iC) == iA){
            Parser.manu[0]++;
            
            if(iA >= 3.5){
                Parser.manu[1]++;
            }
            else if(iA >= 2.5){
                Parser.manu[2]++;
               
            }
            else if(iA >= 1.5){
                Parser.manu[3]++;
            }
            
        return a;
        }
        if(Math.max(iA, iB) == iB && Math.max(iB, iC) == iB){
            Parser.land[0]++;
                        
            
            if(iB >= 3.5){
                Parser.land[1]++;
            }
            else if(iB >= 2.5){
                Parser.land[2]++;
               
            }
            else if(iB >= 1.5){
                Parser.land[3]++;
            }
            return b;
        }
        if(Math.max(iA, iC) == iC && Math.max(iB, iC) == iC){
            Parser.constr[0]++;
                        if(iC >= 3.5){
                Parser.constr[1]++;
            }
            else if(iC >= 2.5){
                Parser.constr[2]++;               
            }
            else if(iC >= 1.5){
                Parser.constr[3]++;
            }
            return c;
        }
        else {
            return null;
        }
        } else{
            Parser.takiSamEko++;
            
            return null;
        }
       
    }
    
    public static void pokazDane(String kraj, PrintWriter writer){
        for(int i=0;i<3;i++){
            writer.println("");
        }
        writer.println("                           Dane o obywatelach kraju ");
        writer.println("                                    "+kraj);
        writer.println("Mamy: "+Parser.gun[0]+" gunów w SUMIE.");
        writer.println("            ,powyżej 4.0 ->"+Parser.gun[1]);
        writer.println("            ,powyżej 3.0->"+Parser.gun[2]);
        writer.println("            ,powyżej 2.0"+Parser.gun[3]);
        
        
        
        writer.println("Mamy: "+Parser.heli[0]+" heli w SUMIE");
        writer.println("             ,powyżej 4.0 ->"+Parser.heli[1]);
        writer.println("             ,powyżej 3.0"+Parser.heli[2]);
        writer.println("             ,powyżej 2.0"+Parser.heli[3]);
        
        
        writer.println("Mamy: "+Parser.tanki[0]+" tanków w SUMIE");
        writer.println("            ,powyżej 4.0 ->"+Parser.tanki[1]);
        writer.println("            ,powyżej 3.0->"+Parser.tanki[2]);
        writer.println("            ,powyżej 2.0"+Parser.tanki[3]);
        
        
        writer.println("Mamy: "+Parser.land[0]+" landów w SUMIE");
        writer.println("            ,powyżej 3.5 ->"+Parser.land[1]);
        writer.println("            ,powyżej 2.5->"+Parser.land[2]);
        writer.println("            ,powyżej 1.5"+Parser.land[3]);
        writer.println("Mamy: "+Parser.manu[0]+" manu w SUMIE");
        writer.println("            ,powyżej 3.5 ->"+Parser.manu[1]);
        writer.println("            ,powyżej 2.5->"+Parser.manu[2]);
        writer.println("            ,powyżej 1.5"+Parser.manu[3]);
        writer.println("Mamy: "+Parser.constr[0]+" constr w SUMIE");
        writer.println("            ,powyżej 3.5 ->"+Parser.constr[1]);
        writer.println("            ,powyżej 2.5->"+Parser.constr[2]);
        writer.println("            ,powyżej 1.5"+Parser.constr[3]);
        
        writer.println(Parser.takiSamEko+" obywateli nie specjalizowało się ekonomicznie");
        writer.println(Parser.takiSamMilitary+" obywateli nie specjalizowało się militarnie");
        
        writer.flush();
    }
    public static void zerujDane(){
        Parser.constr = new int[]{0,0,0,0};
        Parser.manu = new int[]{0,0,0,0};
        Parser.land = new int[]{0,0,0,0};
        
        Parser.gun = new int[]{0,0,0,0};
        Parser.heli = new int[]{0,0,0,0};
        Parser.tanki = new int[]{0,0,0,0};
        
        Parser.takiSamEko = 0;
        Parser.takiSamMilitary = 0;
    }
    
    public static FileInputStream OmijanieBlokady(ZipInputStream zis){
        BufferedInputStream bis = new BufferedInputStream(zis);
        BufferedReader czytacz = new BufferedReader(new InputStreamReader(bis));
        try {
            ZipEntry e = zis.getNextEntry();
            
            System.out.println(e.getName());
             Parser.plik = new File(e.getName());
            BufferedWriter pisacz = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(plik)));
            String s;
            while((s = czytacz.readLine())!=null){
                pisacz.append(s);
            }
            pisacz.flush();
            return new FileInputStream(plik);
            
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        
    }

}
