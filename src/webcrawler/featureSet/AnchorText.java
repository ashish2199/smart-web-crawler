package webcrawler.featureSet;

import java.util.ArrayList;
import java.util.HashSet;
import webcrawler.documentMeasure.Vector;

public class AnchorText {
    static ArrayList<feature>features=new ArrayList<>();
    static Vector fv=new Vector(); 
    static HashSet<String> anchorSet=new HashSet<>();
    public static void printFeatures(){
        System.out.println(fv.toString());
    }
    public static String getGUIRepresentation(){
        return fv.getStringForGUI();
    }
    public static Vector toVector(){
        return fv;
    }
    
    public static void addFeaturesList(ArrayList<String> features){
        for (String f : features) {
            addFeature(new feature(f, 1));
        }
    }
    
    public static void addFeature(feature f){
        if(f.word.equals("")){
            return;
        }
        if(anchorSet.contains(f.word)){
            fv.incrementFrequency(f.word, f.frequency);
        }
        else{
            anchorSet.add(f.word);
            fv.addWord(f.word);
        }
    }
}
