package webcrawler.documentMeasure;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Vector
{
    
    Map<String, Integer> wordMap = new HashMap<>();
    
    public Vector(){}
    
    public Vector(String text,String splitRegex){
        for(String w:text.split(splitRegex)) {
            incCount(w);
        }
    }
    
    public Vector(ArrayList<String> words){
        for (String word : words) {
            addWord(word);
        }
    }

    @Override
    public String toString() {
        System.out.println("sorting");
        String s="";
        s=s+"Size="+this.getSize()+"\n";
        Map<String, Integer> sortedMap = sortByComparator(wordMap);
        for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
            s=s+entry.getKey()+":"+entry.getValue()+" ";
	}
	return s+"\n";
    }
    
    public String getStringForGUI(){
        String s="";
        s=s+"Size = "+this.getSize()+"   (Showing top 100)\n";
        s=s+"---------------\n";
        Map<String, Integer> sortedMap = sortByComparator(wordMap);
        int i=0;
        for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
            s=s+entry.getKey()+" : "+entry.getValue()+"\n";
            i++;
            if(i>100){break;}
        }
        
	return s+"\n";
    }
    
    private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap) {

		// Convert Map to List
		List<Map.Entry<String, Integer>> list = 
			new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,Map.Entry<String, Integer> o2) {
                            int retValue = o1.getValue().compareTo(o2.getValue());
                            if(retValue!=0){retValue=retValue*-1;}
                            return retValue;
                            /*if(o1.getValue()<o2.getValue()){return 1;}
                            else if(o1.getValue()>o2.getValue()) {return -1;}
                            else{return 0;}*/
                        }
		});

		// Convert sorted map back to a Map
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext();) {
			Map.Entry<String, Integer> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
    
    public int getSize(){
        return wordMap.size();
    }
    public void addWord(String w){
        incCount(w);
    }
    public void incrementFrequency(String word,int NewFrequency){
        Integer oldCount = wordMap.get(word);
        wordMap.put(word, oldCount == null ? 1 : (oldCount + NewFrequency));
    }
    
    public void incCount(String word) {
        Integer oldCount = wordMap.get(word);
        wordMap.put(word, oldCount == null ? 1 : oldCount + 1);
    }

    public double getCosineSimilarityWith(Vector otherVector) {
        double innerProduct = 0;
        for(String w: this.wordMap.keySet()) {
            innerProduct += this.getCount(w) * otherVector.getCount(w);
        }
        return innerProduct / (this.getNorm() * otherVector.getNorm());
    }

    double getNorm() {
        double sum = 0;
        for (Integer count : wordMap.values()) {
            sum += count * count;
        }
        return Math.sqrt(sum);
    }
    
    public void showComponents(){
        Set<String> components = wordMap.keySet();
        for(String k : components) {
            System.out.println(""+k);
        }
    }
    
    public int getCount(String word) {
        int countLowerCase = wordMap.containsKey(word.toLowerCase()) ? wordMap.get(word.toLowerCase()) : 0;
        int countUpperCase = wordMap.containsKey(word.toUpperCase()) ? wordMap.get(word.toUpperCase()) : 0;
        int countExact = wordMap.containsKey(word) ? wordMap.get(word) : 0;
        //returns largest of the three number
        return (countLowerCase>countUpperCase?countLowerCase:countUpperCase)>countExact?(countLowerCase>countUpperCase?countLowerCase:countUpperCase):countExact;
    }
    
    

    static String urlSplit="[/.?&:;#@=]|^~`";
        
    public static void main(String[] args) {
        String doc1 = "A B C A A B C. D D E A B. D A B C B A.";
        String doc2 = "A B C R S A B C. F F G G H.";

        Vector v1 = new Vector(doc1,"[^a-zA-Z]+");
        Vector v2 = new Vector(doc2,"[^a-zA-Z]+");
        
        System.out.println("Similarity = " + v1.getCosineSimilarityWith(v2));
        
        String link1 = "http://www.msit.in/pages/31";
        String link2 = "http://www.msit.in/pages/32";
        String link3 = "http://www.msit.in/#collapse1";
        String link4 = "http://www.msit.in/#collapse2";
        
        Vector vectorl1 = new Vector(link1, urlSplit);
        Vector vectorl2 = new Vector(link2, urlSplit);
        Vector vectorl3 = new Vector(link3, urlSplit);
        Vector vectorl4 = new Vector(link4, urlSplit);
        
        double score1 = vectorl1.getCosineSimilarityWith(vectorl2);
        double score2 = vectorl3.getCosineSimilarityWith(vectorl4);
        double score3 = vectorl1.getCosineSimilarityWith(vectorl4);
        
        System.out.println("Similarity between link1 and link2="+score1);
        System.out.println("Similarity between link3 and link4="+score2);
        System.out.println("Similarity between link1 and link4="+score3);
                
        
    }

}
