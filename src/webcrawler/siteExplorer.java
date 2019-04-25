package webcrawler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import static webcrawler.Link.urlSplit;
import webcrawler.documentMeasure.Vector;
import webcrawler.featureSet.feature;
import static webcrawler.htmlDownloader.getHtmlFromURL;

public class siteExplorer {
    ArrayList<Link> linksQueue = new ArrayList<>();
    static HashSet<String> visitedLinks=new HashSet<>();
    static HashSet<String> downLoadedHtmlLinks=new HashSet<>();
    static HashSet<String> outsideLinks=new HashSet<>();
    int numTopWords;
    PriorityQueue<Link> Pqueue = new PriorityQueue<Link>(new linkComparator());
    
    static Pattern pattern = Pattern.compile("#[a-zA-Z0-9]*$");
    public static String removeLastPart(String url){
        Matcher matcher = pattern.matcher(url);
        if(matcher.find()){
            url=url.substring(0, matcher.start());
        }
        return url;
    }
    public static void main(String[] args) {
        String s =removeLastPart("http://www.msit.in/#collapse2");
        System.out.println(""+s);
    }
    int numPages=0;
    GuiController gc;
    int minutes=1;
    public void startStatistics(){
        Timer timer = new Timer();

        timer.schedule( new TimerTask() {
            public void run() {
                gc.statistics=gc.statistics+"\n"+minutes+" minutes : "+numPages+" links";
                System.out.println(" "+gc.statistics);
                minutes++;
            }
         }, 60*1000, 60*1000);
        
    }
    
    public void exploreSite(Link L){
        
        numTopWords=50;
        linksQueue.add(L);
        gc=Webcrawler.getController();
        startStatistics();
            
        while(!linksQueue.isEmpty()){
            if(numPages>GuiController.maxPages){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Crawling Finished");
                        alert.setHeaderText("Reached maximum number of pages");
                        alert.setContentText("Crawler has crawled given amount of pages");
                        alert.showAndWait();
                        gc.enableSaveBtn();
                        crawlerController.stopCrawlerThread();
                    }
                });
                return;
            }
            
            Link currentLink = linksQueue.remove(0);
            //System.out.println("link removed:"+currentLink.linkURL);
            String urlString = currentLink.getURL().toString();
            gc.setCurrentLink(urlString);
            gc.addLink(urlString);
            
            numPages++;
            String numPagesCrawled=""+numPages;
            
            Platform.runLater(() -> gc.setNumLinks(numPagesCrawled));    
            
            
            int level = currentLink.level;
            if(level>GuiController.maxDepth){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Crawling Finished");
                        alert.setHeaderText("Reached max depth");
                        alert.setContentText("Crawler has crawler given depth");
                        alert.showAndWait();
                        gc.enableSaveBtn();
                        crawlerController.stopCrawlerThread();
                    }
                });
                return;
            }
            
            
            if(numPages%10==0){
                webcrawler.featureSet.bodyText.printFeatures();
                gc.setTextFeatures(webcrawler.featureSet.bodyText.getGUIRepresentation());
                gc.setURLFeatures(webcrawler.featureSet.URLFeature.getGUIRepresentation());
                gc.setAnchorFeatures(webcrawler.featureSet.AnchorText.getGUIRepresentation());
            }
            
            if(!validExtension(urlString))
            {
               // System.out.println("Skipping as invalid extension");
                continue;
            }
            
            // if already visited link
            if(downLoadedHtmlLinks.contains(urlString))
            {   //System.out.println("Skipping as already visited");
                continue;
            }
            else{
                downLoadedHtmlLinks.add(urlString);
                //System.out.println("Not present so added "+urlString);
            }
            
            String htmlText = getHtmlFromURL(urlString);
            
            Document d = htmlParser.parseHtml(htmlText, urlString);
            
            String documentText = d.text();
            ArrayList<String> cleanedWords=htmlParser.removeStopWords(documentText);
            ArrayList<feature> featureList = htmlParser.getTopWords(cleanedWords,numTopWords);
            
            //add text features
            webcrawler.featureSet.bodyText.addFeaturesList(featureList);
            
            Vector docTextVector = new Vector(cleanedWords);
            
            Elements links = htmlParser.getLinks(d);
            
            for( Element l : links ){
                
                String url = l.absUrl("href");
                
                url=removeLastPart(url);
                
                if(!visitedLinks.contains(url)&&!url.isEmpty()){
                    
                    visitedLinks.add(url);
                    
                    // add anchor text features
                    String anchorText = l.text();
                    ArrayList<String> anchorWords = htmlParser.removeStopWords(anchorText);
                    webcrawler.featureSet.AnchorText.addFeaturesList(anchorWords);
                    
                    Vector anchorVector = new Vector(anchorWords);
                    
                    Link linkUrl = new Link(url,anchorText,documentText);
                    linkUrl.setLevel(level+1);
                    linkUrl.setTextVector(docTextVector);
                    linkUrl.setAnchorVector(anchorVector);
                    
                    String[] skipURLFeaturelist=new String[]{"www","http","https","com","in","html","html","jpg","png","gif"};
                    
                    //add URL features
                  outer:for(String w:url.split(urlSplit)) {
                            for(String skipU:skipURLFeaturelist){
                                if(skipU.toLowerCase().equals(w.toLowerCase())){
                                    continue outer;
                                }
                            }
                            webcrawler.featureSet.URLFeature.addFeature(new feature(w, 1));
                    }
                    
                    linkUrl.setScore(webcrawler.documentMeasure.similarity.computeLinkSimilarity(linkUrl));
                    
                    //add outside links
                    if(!linkUrl.host.equals(currentLink.host)){
                        if(outsideLinks.contains(linkUrl.host)){
                        }
                        else{
                            outsideLinks.add(linkUrl.host);
                            //System.out.println("New host: "+linkUrl.host);
                        }
                    }
                    Pqueue.add(linkUrl);
                }
            }
            
            while(!Pqueue.isEmpty()){
                //System.out.println("The score for  "+Pqueue.peek().linkURL+" being added is  "+Pqueue.peek().getScore());
                linksQueue.add(Pqueue.remove());
            }
        }
    }
    
    
    String skipExtensions[];
    void getExtensions(){
        skipExtensions=GuiController.getExtensionArray();
    }
    boolean validExtension(String urlString){
        if(skipExtensions==null){
            getExtensions();
        }
        boolean valid=true;
        for (String ext : skipExtensions) {
            if(urlString.endsWith(ext)){
                valid=false;
            }
        }
        return valid;
    }
}
