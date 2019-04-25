/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler.oldWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import java.util.logging.Level;
import webcrawler.GuiController;
import webcrawler.Webcrawler;
import static webcrawler.oldWork.BasicCrawlController.startCrawler;

/**
 * @author Ashish Padalkar
 */
class crawler_class implements Runnable{
        @Override
        public void run() {
            try {
                System.out.println("Started Crawler thread");
                startCrawler();
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(BasicCrawlController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
}

public class BasicCrawlController {
  private static final Logger logger = LoggerFactory.getLogger(BasicCrawlController.class);
    /*
     * crawlStorageFolder is a folder where intermediate crawl data is
     * stored.
     */
  public static String crawlStorageFolder = "crawled_data";

    /*
     * numberOfCrawlers shows the number of concurrent threads that should
     * be initiated for crawling.
     */
  public static int numberOfCrawlers= 1;
  public static void main(String[] args) throws Exception {
        startCrawler();
  }
  public static void startCrawler() throws Exception {
        System.out.println("Creating crawler config");
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);

      System.out.println("1");        
        config.setPolitenessDelay(GuiController.getMaxDelay());
        config.setMaxDepthOfCrawling(GuiController.getMaxDepth());
        config.setMaxPagesToFetch(GuiController.getMaxPages());
        
        config.setIncludeBinaryContentInCrawling(false);
        config.setResumableCrawling(false);
        System.out.println("2");
        //crawl controllers
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        System.out.println("3");
        System.out.println("link"+Webcrawler.getController().getLinkSeed());
        controller.addSeed(Webcrawler.getController().getLinkSeed());
        //controller.addSeed("http://www.msit.in/");
        
        //http://www.msit.in/
//System.out.println("link"+Webcrawler.getController().getLinkSeed());
        System.out.println("4");
        //controller.addSeed("http://ashish2199.github.io/");
        System.out.println("Starting crawler");
        controller.start(BasicCrawler.class, numberOfCrawlers);
    }
}
