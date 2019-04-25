package webcrawler.featureSet;

import java.util.ArrayList;
import java.util.HashSet;
import webcrawler.documentMeasure.Vector;

public class bodyText {
    
    static Vector fv=new Vector(); 
    static HashSet<String> textSet=new HashSet<>();
    
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
        if(textSet.contains(f.word)){
            fv.incrementFrequency(f.word, f.frequency);
        }
        else{
            textSet.add(f.word);
            fv.addWord(f.word);
        }
    }
}
