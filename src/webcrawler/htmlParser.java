package webcrawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import static webcrawler.LinkQueue.addToLowPriorityQueue;
import webcrawler.featureSet.feature;

public class htmlParser {
    
    public static void main(String[] args) {
        String text = "Welcome to the world of Geeks \n" +
        "This portal has been created to provide well written well thought and well explained \n" +
        "solutions for selected questions If you like Geeks for Geeks and would like to contribute \n" +
        "here is your chance You can write article and mail your article to contribute at \n" +
        "geeksforgeeks org See your article appearing on the Geeks for Geeks main page and help \n" +
        "thousands of other Geeks";
        System.out.println(""+getTopWords(removeStopWords(text), 56));
    }

    public static Document parseHtmlString(String htmlText,String baseURL){
        Document doc = Jsoup.parse(htmlText, baseURL);
        Elements links = doc.select("a[href]");
        for(Element l:links){
            addToQueue(l.absUrl("href"),l.text(),"");
        }
        return doc;
    }
    
    public static Document parseHtml(String htmlText,String baseURL){
        Document doc = Jsoup.parse(htmlText, baseURL);
        return doc;
    }
    
    public static Elements getLinks(Document doc){
        Elements links = doc.select("a[href]");
        return links;
    }
    
    static HashSet<String> visitedLinks=new HashSet<>();
    
    
    public static void addToQueue(String link,String anchorText,String textAroundLink){
        if(!link.isEmpty())
        {
            if(!visitedLinks.contains(link)){
                visitedLinks.add(link);
                Link l = new Link(link, anchorText, textAroundLink);
                addToLowPriorityQueue(l);
            }
            else
            {
                //System.out.println("Skipping as already visited");
            }
        }
        else
        {
            System.out.println("Skipping as empty link");
        }
    }
    
    static String stopWordList="a\n" +
    //<editor-fold defaultstate="collapsed" desc="comment">
           "able\n" +
            "about\n" +
            "above\n" +
            "abst\n" +
            "accordance\n" +
            "according\n" +
            "accordingly\n" +
            "across\n" +
            "act\n" +
            "actually\n" +
            "added\n" +
            "adj\n" +
            "affected\n" +
            "affecting\n" +
            "affects\n" +
            "after\n" +
            "afterwards\n" +
            "again\n" +
            "against\n" +
            "ah\n" +
            "all\n" +
            "almost\n" +
            "alone\n" +
            "along\n" +
            "already\n" +
            "also\n" +
            "although\n" +
            "always\n" +
            "am\n" +
            "among\n" +
            "amongst\n" +
            "an\n" +
            "and\n" +
            "announce\n" +
            "another\n" +
            "any\n" +
            "anybody\n" +
            "anyhow\n" +
            "anymore\n" +
            "anyone\n" +
            "anything\n" +
            "anyway\n" +
            "anyways\n" +
            "anywhere\n" +
            "apparently\n" +
            "approximately\n" +
            "are\n" +
            "aren\n" +
            "arent\n" +
            "arise\n" +
            "around\n" +
            "as\n" +
            "aside\n" +
            "ask\n" +
            "asking\n" +
            "at\n" +
            "auth\n" +
            "available\n" +
            "away\n" +
            "awfully\n" +
            "b\n" +
            "back\n" +
            "be\n" +
            "became\n" +
            "because\n" +
            "become\n" +
            "becomes\n" +
            "becoming\n" +
            "been\n" +
            "before\n" +
            "beforehand\n" +
            "begin\n" +
            "beginning\n" +
            "beginnings\n" +
            "begins\n" +
            "behind\n" +
            "being\n" +
            "believe\n" +
            "below\n" +
            "beside\n" +
            "besides\n" +
            "between\n" +
            "beyond\n" +
            "biol\n" +
            "both\n" +
            "brief\n" +
            "briefly\n" +
            "but\n" +
            "by\n" +
            "c\n" +
            "ca\n" +
            "came\n" +
            "can\n" +
            "cannot\n" +
            "can't\n" +
            "cause\n" +
            "causes\n" +
            "certain\n" +
            "certainly\n" +
            "co\n" +
            "com\n" +
            "come\n" +
            "comes\n" +
            "contain\n" +
            "containing\n" +
            "contains\n" +
            "could\n" +
            "couldnt\n" +
            "d\n" +
            "date\n" +
            "did\n" +
            "didn't\n" +
            "different\n" +
            "do\n" +
            "does\n" +
            "doesn't\n" +
            "doing\n" +
            "done\n" +
            "don't\n" +
            "down\n" +
            "downwards\n" +
            "due\n" +
            "during\n" +
            "e\n" +
            "each\n" +
            "ed\n" +
            "edu\n" +
            "effect\n" +
            "eg\n" +
            "eight\n" +
            "eighty\n" +
            "either\n" +
            "else\n" +
            "elsewhere\n" +
            "end\n" +
            "ending\n" +
            "enough\n" +
            "especially\n" +
            "et\n" +
            "et-al\n" +
            "etc\n" +
            "even\n" +
            "ever\n" +
            "every\n" +
            "everybody\n" +
            "everyone\n" +
            "everything\n" +
            "everywhere\n" +
            "ex\n" +
            "except\n" +
            "f\n" +
            "far\n" +
            "few\n" +
            "ff\n" +
            "fifth\n" +
            "first\n" +
            "five\n" +
            "fix\n" +
            "followed\n" +
            "following\n" +
            "follows\n" +
            "for\n" +
            "former\n" +
            "formerly\n" +
            "forth\n" +
            "found\n" +
            "four\n" +
            "from\n" +
            "further\n" +
            "furthermore\n" +
            "g\n" +
            "gave\n" +
            "get\n" +
            "gets\n" +
            "getting\n" +
            "give\n" +
            "given\n" +
            "gives\n" +
            "giving\n" +
            "go\n" +
            "goes\n" +
            "gone\n" +
            "got\n" +
            "gotten\n" +
            "h\n" +
            "had\n" +
            "happens\n" +
            "hardly\n" +
            "has\n" +
            "hasn't\n" +
            "have\n" +
            "haven't\n" +
            "having\n" +
            "he\n" +
            "hed\n" +
            "hence\n" +
            "her\n" +
            "here\n" +
            "hereafter\n" +
            "hereby\n" +
            "herein\n" +
            "heres\n" +
            "hereupon\n" +
            "hers\n" +
            "herself\n" +
            "hes\n" +
            "hi\n" +
            "hid\n" +
            "him\n" +
            "himself\n" +
            "his\n" +
            "hither\n" +
            "home\n" +
            "how\n" +
            "howbeit\n" +
            "however\n" +
            "hundred\n" +
            "i\n" +
            "id\n" +
            "ie\n" +
            "if\n" +
            "i'll\n" +
            "im\n" +
            "immediate\n" +
            "immediately\n" +
            "importance\n" +
            "important\n" +
            "in\n" +
            "inc\n" +
            "indeed\n" +
            "index\n" +
            "information\n" +
            "instead\n" +
            "into\n" +
            "invention\n" +
            "inward\n" +
            "is\n" +
            "isn't\n" +
            "it\n" +
            "itd\n" +
            "it'll\n" +
            "its\n" +
            "itself\n" +
            "i've\n" +
            "j\n" +
            "just\n" +
            "k\n" +
            "keep	keeps\n" +
            "kept\n" +
            "kg\n" +
            "km\n" +
            "know\n" +
            "known\n" +
            "knows\n" +
            "l\n" +
            "largely\n" +
            "last\n" +
            "lately\n" +
            "later\n" +
            "latter\n" +
            "latterly\n" +
            "least\n" +
            "less\n" +
            "lest\n" +
            "let\n" +
            "lets\n" +
            "like\n" +
            "liked\n" +
            "likely\n" +
            "line\n" +
            "little\n" +
            "'ll\n" +
            "look\n" +
            "looking\n" +
            "looks\n" +
            "ltd\n" +
            "m\n" +
            "made\n" +
            "mainly\n" +
            "make\n" +
            "makes\n" +
            "many\n" +
            "may\n" +
            "maybe\n" +
            "me\n" +
            "mean\n" +
            "means\n" +
            "meantime\n" +
            "meanwhile\n" +
            "merely\n" +
            "mg\n" +
            "might\n" +
            "million\n" +
            "miss\n" +
            "ml\n" +
            "more\n" +
            "moreover\n" +
            "most\n" +
            "mostly\n" +
            "mr\n" +
            "mrs\n" +
            "much\n" +
            "mug\n" +
            "must\n" +
            "my\n" +
            "myself\n" +
            "n\n" +
            "na\n" +
            "name\n" +
            "namely\n" +
            "nay\n" +
            "nd\n" +
            "near\n" +
            "nearly\n" +
            "necessarily\n" +
            "necessary\n" +
            "need\n" +
            "needs\n" +
            "neither\n" +
            "never\n" +
            "nevertheless\n" +
            "new\n" +
            "next\n" +
            "nine\n" +
            "ninety\n" +
            "no\n" +
            "nobody\n" +
            "non\n" +
            "none\n" +
            "nonetheless\n" +
            "noone\n" +
            "nor\n" +
            "normally\n" +
            "nos\n" +
            "not\n" +
            "noted\n" +
            "nothing\n" +
            "now\n" +
            "nowhere\n" +
            "o\n" +
            "obtain\n" +
            "obtained\n" +
            "obviously\n" +
            "of\n" +
            "off\n" +
            "often\n" +
            "oh\n" +
            "ok\n" +
            "okay\n" +
            "old\n" +
            "omitted\n" +
            "on\n" +
            "once\n" +
            "one\n" +
            "ones\n" +
            "only\n" +
            "onto\n" +
            "or\n" +
            "ord\n" +
            "other\n" +
            "others\n" +
            "otherwise\n" +
            "ought\n" +
            "our\n" +
            "ours\n" +
            "ourselves\n" +
            "out\n" +
            "outside\n" +
            "over\n" +
            "overall\n" +
            "owing\n" +
            "own\n" +
            "p\n" +
            "page\n" +
            "pages\n" +
            "part\n" +
            "particular\n" +
            "particularly\n" +
            "past\n" +
            "per\n" +
            "perhaps\n" +
            "placed\n" +
            "please\n" +
            "plus\n" +
            "poorly\n" +
            "possible\n" +
            "possibly\n" +
            "potentially\n" +
            "pp\n" +
            "predominantly\n" +
            "present\n" +
            "previously\n" +
            "primarily\n" +
            "probably\n" +
            "promptly\n" +
            "proud\n" +
            "provides\n" +
            "put\n" +
            "q\n" +
            "que\n" +
            "quickly\n" +
            "quite\n" +
            "qv\n" +
            "r\n" +
            "ran\n" +
            "rather\n" +
            "rd\n" +
            "re\n" +
            "readily\n" +
            "really\n" +
            "recent\n" +
            "recently\n" +
            "ref\n" +
            "refs\n" +
            "regarding\n" +
            "regardless\n" +
            "regards\n" +
            "related\n" +
            "relatively\n" +
            "research\n" +
            "respectively\n" +
            "resulted\n" +
            "resulting\n" +
            "results\n" +
            "right\n" +
            "run\n" +
            "s\n" +
            "said\n" +
            "same\n" +
            "saw\n" +
            "say\n" +
            "saying\n" +
            "says\n" +
            "sec\n" +
            "section\n" +
            "see\n" +
            "seeing\n" +
            "seem\n" +
            "seemed\n" +
            "seeming\n" +
            "seems\n" +
            "seen\n" +
            "self\n" +
            "selves\n" +
            "sent\n" +
            "seven\n" +
            "several\n" +
            "shall\n" +
            "she\n" +
            "shed\n" +
            "she'll\n" +
            "shes\n" +
            "should\n" +
            "shouldn't\n" +
            "show\n" +
            "showed\n" +
            "shown\n" +
            "showns\n" +
            "shows\n" +
            "significant\n" +
            "significantly\n" +
            "similar\n" +
            "similarly\n" +
            "since\n" +
            "six\n" +
            "slightly\n" +
            "so\n" +
            "some\n" +
            "somebody\n" +
            "somehow\n" +
            "someone\n" +
            "somethan\n" +
            "something\n" +
            "sometime\n" +
            "sometimes\n" +
            "somewhat\n" +
            "somewhere\n" +
            "soon\n" +
            "sorry\n" +
            "specifically\n" +
            "specified\n" +
            "specify\n" +
            "specifying\n" +
            "still\n" +
            "stop\n" +
            "strongly\n" +
            "sub\n" +
            "substantially\n" +
            "successfully\n" +
            "such\n" +
            "sufficiently\n" +
            "suggest\n" +
            "sup\n" +
            "sure	t\n" +
            "take\n" +
            "taken\n" +
            "taking\n" +
            "tell\n" +
            "tends\n" +
            "th\n" +
            "than\n" +
            "thank\n" +
            "thanks\n" +
            "thanx\n" +
            "that\n" +
            "that'll\n" +
            "thats\n" +
            "that've\n" +
            "the\n" +
            "their\n" +
            "theirs\n" +
            "them\n" +
            "themselves\n" +
            "then\n" +
            "thence\n" +
            "there\n" +
            "thereafter\n" +
            "thereby\n" +
            "thered\n" +
            "therefore\n" +
            "therein\n" +
            "there'll\n" +
            "thereof\n" +
            "therere\n" +
            "theres\n" +
            "thereto\n" +
            "thereupon\n" +
            "there've\n" +
            "these\n" +
            "they\n" +
            "theyd\n" +
            "they'll\n" +
            "theyre\n" +
            "they've\n" +
            "think\n" +
            "this\n" +
            "those\n" +
            "thou\n" +
            "though\n" +
            "thoughh\n" +
            "thousand\n" +
            "throug\n" +
            "through\n" +
            "throughout\n" +
            "thru\n" +
            "thus\n" +
            "til\n" +
            "tip\n" +
            "to\n" +
            "together\n" +
            "too\n" +
            "took\n" +
            "toward\n" +
            "towards\n" +
            "tried\n" +
            "tries\n" +
            "truly\n" +
            "try\n" +
            "trying\n" +
            "ts\n" +
            "twice\n" +
            "two\n" +
            "u\n" +
            "un\n" +
            "under\n" +
            "unfortunately\n" +
            "unless\n" +
            "unlike\n" +
            "unlikely\n" +
            "until\n" +
            "unto\n" +
            "up\n" +
            "upon\n" +
            "ups\n" +
            "us\n" +
            "use\n" +
            "used\n" +
            "useful\n" +
            "usefully\n" +
            "usefulness\n" +
            "uses\n" +
            "using\n" +
            "usually\n" +
            "v\n" +
            "value\n" +
            "various\n" +
            "'ve\n" +
            "very\n" +
            "via\n" +
            "viz\n" +
            "vol\n" +
            "vols\n" +
            "vs\n" +
            "w\n" +
            "want\n" +
            "wants\n" +
            "was\n" +
            "wasnt\n" +
            "way\n" +
            "we\n" +
            "wed\n" +
            "welcome\n" +
            "we'll\n" +
            "went\n" +
            "were\n" +
            "werent\n" +
            "we've\n" +
            "what\n" +
            "whatever\n" +
            "what'll\n" +
            "whats\n" +
            "when\n" +
            "whence\n" +
            "whenever\n" +
            "where\n" +
            "whereafter\n" +
            "whereas\n" +
            "whereby\n" +
            "wherein\n" +
            "wheres\n" +
            "whereupon\n" +
            "wherever\n" +
            "whether\n" +
            "which\n" +
            "while\n" +
            "whim\n" +
            "whither\n" +
            "who\n" +
            "whod\n" +
            "whoever\n" +
            "whole\n" +
            "who'll\n" +
            "whom\n" +
            "whomever\n" +
            "whos\n" +
            "whose\n" +
            "why\n" +
            "widely\n" +
            "willing\n" +
            "wish\n" +
            "with\n" +
            "within\n" +
            "without\n" +
            "wont\n" +
            "words\n" +
            "world\n" +
            "would\n" +
            "wouldnt\n" +
            "www\n" +
            "x\n" +
            "y\n" +
            "yes\n" +
            "yet\n" +
            "you\n" +
            "youd\n" +
            "you'll\n" +
            "your\n" +
            "youre\n" +
            "yours\n" +
            "yourself\n" +
            "yourselves\n" +
            "you've\n" +
            "z\n" +
            "zero";    
//</editor-fold>
    
    
    public static ArrayList<String> removeStopWords(String text){
        ArrayList<String> words = new ArrayList<String>();
        String[] wordArray = text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        for(String w:wordArray){
            words.add(w);
        }
        String stopWordArray[] = stopWordList.split("\n");
        for (String w : stopWordArray) {
            while(words.contains(w)){
                words.remove(w);
            }
        }
        return words;
    }
    
    static ArrayList<feature> getTopWords(ArrayList<String> words,int K){
        HashMap<String,Integer> topK=new HashMap<>();
        ArrayList<String> uniqueWords=new ArrayList<>();
        for(String word : words) {
            Integer oldCount = topK.get(word);
            if(oldCount==null){uniqueWords.add(word);}
            topK.put(word, oldCount == null ? 1 : oldCount + 1);
        }
        
        String[] StringPair=new String[K];
        int[] frequencyPair=new int[K];
        for(String w:topK.keySet()){
            int frequency=topK.get(w);
            for (int i = 0; i < K; i++) {
                if(frequency>frequencyPair[i]){
                    frequencyPair[i]=frequency;
                    StringPair[i]=w;
                    break;
                }
            }
        }
        
        ArrayList<feature> topKwords = new ArrayList<>();
        for (int i = 0; i < K; i++) {
            if(StringPair[i]!=null){
                topKwords.add(new feature(StringPair[i], frequencyPair[i]));
            }
        }
        return topKwords;
    }
}
