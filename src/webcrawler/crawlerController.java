package webcrawler;
public class crawlerController {
    public static void main(String[] args) {
        
    }
    static webCrawlerThread wbc = new webCrawlerThread();
    static Thread t; 
    public static void startCrawlerThread(){
        t = new Thread(wbc);
        t.setName("Crawler Thread");
        t.start();
    }
    public static void stopCrawlerThread() {
        
        t.stop();
        System.out.println("Stopped Thread");
    }
    
}
 