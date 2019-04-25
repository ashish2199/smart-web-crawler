package webcrawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import webcrawler.documentMeasure.Vector;

public class Link {
    
    URL linkURL;
    private double score=0;
    String anchor;
    String text;
    String host;
    Vector urlVector;
    Vector textVector;
    Vector anchorVector;
    int level;
    static String urlSplit="[/.?&:;#@=]|^~`";
    
    public Link(String url,String anchor,String text){
        try
        {
            linkURL = new URL(url);
            host=linkURL.getHost();
            urlVector=new Vector(url,urlSplit);
        }
        catch(MalformedURLException ex)
        {
             System.out.println("Exception from Inside Link URL class url is malformed  for the link"+url);
             Logger.getLogger(Link.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.anchor = anchor;
        this.text = text;
    }

    public Vector getUrlVector() {
        return urlVector;
    }

    public Vector getTextVector() {
        return textVector;
    }

    public Vector getAnchorVector() {
        return anchorVector;
    }
    
    public void setAnchorVector(Vector v){
        this.anchorVector=v;
    }
    public void setTextVector(Vector v){
        this.textVector=v;
    }
    
    public void setLevel(int level){
        this.level=level;
    }
    
    public double documentDistanceOfLink(Link l){
        double score = this.urlVector.getCosineSimilarityWith(l.urlVector);
        return score;
    }
    
    public static boolean containsIgnoreCase(String src, String what) {
        final int length = what.length();
        if (length == 0)
            return true; // Empty string is contained

        final char firstLo = Character.toLowerCase(what.charAt(0));
        final char firstUp = Character.toUpperCase(what.charAt(0));

        for (int i = src.length() - length; i >= 0; i--) {
            // Quick check before calling the more expensive regionMatches() method:
            final char ch = src.charAt(i);
            if (ch != firstLo && ch != firstUp)
                continue;

            if (src.regionMatches(true, i, what, 0, length))
                return true;
        }

        return false;
    }
    
    public double getScore(){
        return score;
    }
    public void setScore(double score){
        this.score=score;
    }
    
    public URL getURL(){
        return linkURL;
    }
    
    public static void main(String[] args){
        String url1 = "https://lucene.apache.org/core/3_4_0/api/core/org/apache/lucene/search/Similarity.html";
        String url2 = "https://www.mytatasky.com/Home/RedirectReceiver?pgrurl=https%3A%2F%2Fwww.mytatasky.com%2FHome%2FRedirectReceiver&SubscriberID=1067379147";
        String url3 = "https://www.google.co.in/search?q=document+distance+java&oq=document+distance+java&aqs=chrome..69i57.6871j0j7&sourceid=chrome&ie=UTF-8";
        String url4 = "https://www.andrew.cmu.edu/course/15-121/labs/HW-4%20Document%20Distance/lab.html";
        String url5 = "https://github.com/compSci91/TwitterGuard/blob/073c077f97b99c5145c43c75359e16f04ea44901/src/main/java/Comparators/Distance.java";
        String url6 = "http://www.dreamincode.net/forums/index.php?app=core&module=search&section=search&do=quick_search&search_app=core&fromsearch=1";
        for(String s:url1.split("[/.?&:;#@]|^~`")){
            System.out.println(""+s);
        }
    }
}
