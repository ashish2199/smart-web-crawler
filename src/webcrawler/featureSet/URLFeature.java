/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler.featureSet;

import java.util.ArrayList;
import java.util.HashSet;
import webcrawler.documentMeasure.Vector;
import static webcrawler.featureSet.AnchorText.anchorSet;

/**
 *
 * @author Ashish
 */
public class URLFeature {
    
    static ArrayList<feature>features=new ArrayList<>();
    static Vector fv=new Vector(); 
    static HashSet<String> URLSet=new HashSet<>();
    public static void printFeatures(){
        System.out.println(""+fv.toString());
    }
    public static String getGUIRepresentation(){
        return fv.getStringForGUI();
    }
    public static Vector toVector(){
        return fv;
    }
    public static void addFeaturesList(ArrayList<feature> features){
        for (feature f : features) {
            addFeature(f);
        }
    }
    
    public static void addFeature(feature f){
        if(f.word.equals("")){
            return;
        }
        if(URLSet.contains(f.word)){
            fv.incrementFrequency(f.word, f.frequency);
        }
        else{
            URLSet.add(f.word);
            fv.addWord(f.word);
        }
    }
}
