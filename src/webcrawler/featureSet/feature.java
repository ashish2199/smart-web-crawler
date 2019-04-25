package webcrawler.featureSet;
public class feature {
    int frequency;
    String word;
    public feature(String w,int f){
        w=w.toLowerCase();
        word=w;
        frequency=f;
    }
    
    @Override
    public String toString() {
        String correspondingValue="";
        for (int i = 0; i < frequency; i++) {
            correspondingValue+=word+" ";
        }
        return correspondingValue;
    }
    
    public double getWeight(){
        return 1+Math.log(frequency);
    }
    
    
    
}
