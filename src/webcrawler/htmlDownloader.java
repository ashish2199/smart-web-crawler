/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author Ashish
 */
public class htmlDownloader {
    public static void main(String[] args) {
        String urLText_1 = "http://cs61b.ug/sp15/materials/lab/lab2/lab2.html";
        String urLText_2 = "https://www.reddit.com/r/brokengifs/";
        for (int i = 0; i < 20; i++) {
            getHtmlFromURL(urLText_1);
        }
        for (int i = 0; i < 20; i++) {
            getHtmlFromURL(urLText_2);
        }

    }
    
    public static String getHtmlFromURL(String url){
        URL urlObj = null;
        long startMillis = System.currentTimeMillis();
        try
        {
            
            urlObj = new URL(url);
        
        }
        catch(MalformedURLException e){
            System.out.println("The url was malformed!");
            return "";
        }
        
        
        URLConnection urlCon = null;
        BufferedReader in = null;
        String outputText = "";
        try{
            urlCon = urlObj.openConnection();
        }
        catch(IOException e){
            System.out.println("There was an error connecting to the URL");
            return "";
        }

        try{
            InputStream is = urlCon.getInputStream();
        }
        catch(IOException e){
            System.out.println("There was an error getting input stream");
            return "";
        }
        try{
            
            
            InputStream is = urlCon.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            in = new BufferedReader(isr);
            
            String line = "";
        
            while((line = in.readLine()) != null){
                outputText += line;
            }
            in.close();
        
        }
        catch(IOException e){
            System.out.println("There was an error reading HTML from the URL");
            return "";
        }
        
        long endMillis = System.currentTimeMillis();
        long duration = (endMillis-startMillis)/1000;
        
        if(outputText.equals(""))
        {
            System.out.println("Didnt fetch anything");
        }
        else
        {
            if(duration>5){
                System.out.println("Fetched data of length "+outputText.length()+" in "+duration+" seconds");
            }
        }
        
        return outputText;
    }
    
    
    /**
     * Http HEAD Method to get URL content type
     *
     * @param urlString
     * @return content type
     * @throws IOException
     */
    public static String getContentType(String urlString) throws IOException{
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("HEAD");
        if (isRedirect(connection.getResponseCode())) {
            String newUrl = connection.getHeaderField("Location"); // get redirect url from "location" header field
            return getContentType(newUrl);
        }
        String contentType = connection.getContentType();
        return contentType;
    }

    /**
     * Check status code for redirects
     * 
     * @param statusCode
     * @return true if matched redirect group
     */
    protected static boolean isRedirect(int statusCode) {
        if (statusCode != HttpURLConnection.HTTP_OK) {
            if (statusCode == HttpURLConnection.HTTP_MOVED_TEMP
                || statusCode == HttpURLConnection.HTTP_MOVED_PERM
                    || statusCode == HttpURLConnection.HTTP_SEE_OTHER) {
                return true;
            }
        }
        return false;
    }
    
}