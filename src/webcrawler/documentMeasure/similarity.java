package webcrawler.documentMeasure;

import webcrawler.Link;

public class similarity{
    
    static Vector getAnchorTextVector(){
        Vector v=webcrawler.featureSet.AnchorText.toVector();
        return v;
    }
    static Vector getBodyTextVector(){
        Vector v=webcrawler.featureSet.bodyText.toVector();
        return v;
    }
    static Vector getURLTextVector(){
        Vector v = webcrawler.featureSet.URLFeature.toVector();
        return v;
    }
    public static double getAnchorTextSimilarity(Vector v){
        double score =0;
        Vector AT = getAnchorTextVector();
        score = AT.getCosineSimilarityWith(v);
        return score;
    }
    public static double getBodyTextSimilarity(Vector v){
        double score =0;
        Vector BT = getBodyTextVector();
        score = BT.getCosineSimilarityWith(v);
        return score;
    }
    public static double getURLTextSimilarity(Vector v){
        double score =0;
        Vector UT = getURLTextVector();
        score = UT.getCosineSimilarityWith(v);
        return score;
    }
    
    public static double computeLinkSimilarity(Link l){
        double score=0;
        double URLscore=getURLTextSimilarity(l.getUrlVector());
        double ANCHORscore=getBodyTextSimilarity(l.getAnchorVector());
        double TEXTscore=getBodyTextSimilarity(l.getTextVector());
        score =0.2*URLscore+0.3*ANCHORscore+0.5*TEXTscore;
        return score;
    }
}
