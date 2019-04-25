/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import java.net.URL;
import java.util.ArrayList;
import org.jsoup.nodes.Document;
import static webcrawler.LinkQueue.getHighPriorityLink;
import static webcrawler.LinkQueue.initialiseQueue;
import static webcrawler.LinkQueue.linksInQueues;
import static webcrawler.htmlDownloader.getHtmlFromURL;
import static webcrawler.htmlParser.getTopWords;
import static webcrawler.htmlParser.parseHtmlString;
import static webcrawler.htmlParser.removeStopWords;

/**
 *
 * @author Ashish
 */

public class webCrawlerThread implements Runnable {

    @Override
    public void run(){
        siteExplorer s = new siteExplorer();
        System.out.println("Thread started");
        
        initialiseQueue();
        
        while(!linksInQueues()){
            Link l = getHighPriorityLink();
            s.exploreSite(l);
            /*
            String urlString = l.getURL().toString();
            
            System.out.println("\nLink removed from queue: "+urlString+" with score "+l.getScore()+"");
            
            
            String html = getHtmlFromURL(urlString);
            Document doc = parseHtmlString(html,urlString);
            String text = doc.text();
            System.out.println("The top ten most frequent words of this page are "+getTopWords(removeStopWords(text), 10));
            */
        }
    }
    
}
