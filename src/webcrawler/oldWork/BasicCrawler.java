package webcrawler.oldWork;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.http.Header;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import webcrawler.GuiController;
import webcrawler.Webcrawler;

/**
 * @author Ashish Padalkar
 */
public class BasicCrawler extends WebCrawler {

  private static final Pattern IMAGE_EXTENSIONS = Pattern.compile(".*\\.(bmp|gif|jpg|png|pdf|doc|rar|zip|mp3|mp4|flv)$");
  private static String domainToCrawlUnder;
  private static String keyword;
  private static Boolean crawlUnderSubDomainOnly;
  
  /**
   * This function is called just before starting the crawl by this crawler
   * instance. It can be used for setting up the data structures or
   * initializations needed by this crawler instance.
   */
  public void onStart() {
      System.out.println("onStart method of crawler");
      gcn=Webcrawler.getController();
      //keyword=GuiController.getKeyword();
      domainToCrawlUnder=GuiController.getLinkSeed();
      //crawlUnderSubDomainOnly=GuiController.crawlUnderSubDomainOnly();
      System.out.println("Starting with: "+domainToCrawlUnder);
  }
  GuiController gcn;
  /**
   * You should implement this function to specify whether the given url
   * should be crawled or not (based on your crawling logic).
   * @param url
   *            the url which we are interested to know whether it should be
   *            included in the crawl or not.
   * @param page
   *           Page context from which this URL was scraped
   * @return if the url should be included in the crawl it returns true,
   *         otherwise false is returned.
   */
  @Override
  public boolean shouldVisit(Page referringPage, WebURL url) {
    String href = url.getURL().toLowerCase();
      System.out.println("Checking : "+href);
    // Ignore the url if it has an extension that matches our defined set of image extensions.
        if (IMAGE_EXTENSIONS.matcher(href).matches()) {
            System.out.println("Invalid extension");
            return false;
        }
        
        if(crawlUnderSubDomainOnly){
            System.out.println("Checking for domain");
            System.out.println("Refferer:"+referringPage.getWebURL().getDomain());
            System.out.println("url:"+url.getDomain());
            if(!referringPage.getWebURL().getDomain().equals(url.getDomain())){
                System.out.print(" : Didnt match domain");
                return false;
            }
            else{
                System.out.println("Inside correct domain");
            }
        }
        System.out.println("Returning true for visit");
        return true;
    }

  /**
   * This function is called when a page is fetched and ready to be processed
   * by your program.
   */
  @Override
  public void visit(Page page) {
    int docid = page.getWebURL().getDocid();
    String url = page.getWebURL().getURL();
    String domain = page.getWebURL().getDomain();
    String path = page.getWebURL().getPath();
    String subDomain = page.getWebURL().getSubDomain();
    String parentUrl = page.getWebURL().getParentUrl();
    String anchor = page.getWebURL().getAnchor();
    
    System.out.println("\nvisiting "+url);
    
    
    /*
    logger.debug("Docid: {}", docid);
    logger.info("URL: {}", url);
    logger.debug("Domain: '{}'", domain);
    logger.debug("Sub-domain: '{}'", subDomain);
    logger.debug("Path: '{}'", path);
    logger.debug("Parent page: {}", parentUrl);
    logger.debug("Anchor text: {}", anchor);
    */
            
    if (page.getParseData() instanceof HtmlParseData) {
      System.out.println("Parsing page");
      HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
      String text = htmlParseData.getText();
      String html = htmlParseData.getHtml();
      Set<WebURL> links = htmlParseData.getOutgoingUrls();
      if(text.contains(keyword)){
          //gcn.show("\n"+page.getWebURL().getURL());
          //System.out.println("\n\n"+text+"\n\n");
      }
      else{
          System.out.println("Page didnt contain keyword");
      }
      /*
      System.out.println("text: \n"+text);
      System.out.println("html: \n"+html);
      */
        /*
      logger.debug("Text length: {}", text.length());
      logger.debug("Html length: {}", html.length());
      logger.debug("Number of outgoing links: {}", links.size());
      */
    }
    else
    {
        
        System.out.println("Didnt match htmlParse instance");
    
    }
    Header[] responseHeaders = page.getFetchResponseHeaders();
    if (responseHeaders != null) {
      //logger.debug("Response headers:");
      for (Header header : responseHeaders) {
        //logger.debug("\t{}: {}", header.getName(), header.getValue());
      }
    }

    //logger.debug("=============");
  }
  
  /**
   * This function is called just before the termination of the current
   * crawler instance. It can be used for persisting in-memory data or other
   * finalization tasks.
   */
  public void onBeforeExit() {
      System.out.println("\n\nStopped Crawling\n\n");
      //gcn.show("\n\nStopped Crawling");
      gcn.enableButton();
  }

}