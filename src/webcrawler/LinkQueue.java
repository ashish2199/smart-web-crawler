package webcrawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LinkQueue {
    public static PriorityQueue<Link> highPriorityLinks;
    public static PriorityQueue<Link> lowPriorityLinks;
    
    static linkComparator linkComparatorObject = new linkComparator();
    
    
    static void initialiseQueue(){
        highPriorityLinks=new PriorityQueue<>(linkComparatorObject);
        lowPriorityLinks=new PriorityQueue<>(linkComparatorObject);
        String seed=GuiController.getLinkSeed();
        Link l = new Link(seed, "", "");
        highPriorityLinks.add(l);
    }
    
    public static boolean linksInQueues(){
        boolean highIsEmpty = highPriorityLinks.isEmpty();
        boolean lowIsEmpty = lowPriorityLinks.isEmpty();
        if(highIsEmpty){
            highPriorityLinks.addAll(lowPriorityLinks);
            System.out.println("Queue is empty");
        }
        boolean isBothEmpty = highIsEmpty&lowIsEmpty;
        return isBothEmpty; 
    }
    
    public static void addToHighPriorityQueue(Link link){
        highPriorityLinks.add(link);
    }
    
    public static void addToLowPriorityQueue(Link link){
        lowPriorityLinks.add(link);
    }
    public static Link getHighPriorityLink(){
        Link link = (Link)highPriorityLinks.remove();
        return link;
    }
    public static Link getLowPriorityLink(){
        Link link = (Link)lowPriorityLinks.remove();
        return link;
    }
}

class linkComparator implements Comparator{

    @Override
    public int compare(Object o1, Object o2) {
        Link l1=(Link)o1;
        Link l2=(Link)o2;
        if(l1.getScore()>l2.getScore()){
            return -1;
        }
        else if(l1.getScore()<l2.getScore())
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

}