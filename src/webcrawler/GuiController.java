/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Ashish Padalkar
 */

public class GuiController implements Initializable {

    @FXML
    private TextField tf_max_depth;

    @FXML
    private TextField tf_max_delay;

    @FXML
    private TextField tf_max_pages;

    @FXML
    private TextField tf_extensions;

    @FXML
    private TextField tf_current_link;
    @FXML
    private Button btn_start_crawling;
    @FXML
    private TextArea ta_links;
    @FXML
    private TextArea ta_text_features;
    @FXML
    private TextArea ta_url_features;
    @FXML
    private TextArea ta_anchor_features;
    
    @FXML
    private TextField tf_link_seed;

    @FXML
    private Label lbl_title;
    
    @FXML
    private Label lbl_numLinks;
    
    @FXML
    private Button btn_showStatistics;
    
    @FXML
    private Button btn_saveLinks;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initialise_tfs();
        initialise_btns();
        ta_links.setEditable(false);
        btn_saveLinks.setDisable(true);
        setNumLinks(""+0);
    }
    
    String statistics="";
    String defaultExtenstions="pdf,rar,zip,jpg,gif,png,exe,doc,xls,mp3,mp4";
    public void initialise_tfs(){
        tf_current_link.setEditable(false);
        tf_extensions.setText(defaultExtenstions);
        tf_max_delay.setText("50");
        tf_max_depth.setText("200");
        tf_max_pages.setText("2000");
    }
    public void initialise_btns(){
        btn_start_crawling.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    fetchExtensions();
                    fetchLinkSeed();
                    fetchMaxDelay();
                    fetchMaxDepth();
                    fetchMaxPages();
                    
                    if(btn_start_crawling.getText().equals("Stop")){
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Dialog");
                        alert.setHeaderText("Are you sure you want to stop crawling?");
                        alert.setContentText("This will result in abandoning of all unvisited links");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK){
                            crawlerController.stopCrawlerThread();    // ... user chose OK
                            btn_start_crawling.setDisable(true);
                            btn_saveLinks.setDisable(false);
                        }else {
                            // ... user chose CANCEL or closed the dialog
                        }
                    }
                    
                    if(btn_start_crawling.getText().equals("Start")){
                        if(!linkSeed.equals("")){
                            crawlerController.startCrawlerThread();
                            btn_start_crawling.setText("Stop");
                        }
                        else{
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("Warning Dialog");
                            alert.setHeaderText("Please input the Seed Site");
                            alert.setContentText("Please enter the site you want to start crawling with");
                            alert.showAndWait();
                        }
                    }
                    
                    
                }catch (Exception ex) {
                    Logger.getLogger(GuiController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btn_saveLinks.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileWriter fw=null;
                try {
                    Stage savedStage = new Stage();
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Save file");
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT (*.txt)", "*.txt");
                    fileChooser.getExtensionFilters().add(extFilter);
                    String filenamewithExtension ="links.txt";
                    fileChooser.setInitialFileName(filenamewithExtension);
                    File savedFile = fileChooser.showSaveDialog(savedStage);
                    fw = new FileWriter(savedFile);
                    BufferedWriter bw= new BufferedWriter(fw);
                    String links=ta_links.getText();
                    links=links.replaceAll("\n", System.lineSeparator());
                    bw.write(links);
                    bw.close();
                }
                catch (IOException ex) {
                    Logger.getLogger(GuiController.class.getName()).log(Level.SEVERE, null, ex);
                }
                finally {
                    try {
                        fw.close();
                    } catch (IOException ex) {
                        Logger.getLogger(GuiController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        btn_showStatistics.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Statistics");
                alert.setHeaderText("Number of Links Crawled");
                alert.setContentText(statistics);

                alert.showAndWait();
            }
        });
    }
    
    
    public void enableButton(){
        btn_start_crawling.setDisable(false);
    }
    
    public void setCurrentLink(String lnk){
        tf_current_link.setText(lnk);
    }
    TextArea getTextArea(){
        return this.ta_links;
    }
    String guiLink ="";
    HashSet<String> vlinks = new HashSet<>();
    class rAddLink implements Runnable{
        @Override
        public void run() {
            if(vlinks.add(guiLink)){
                ta_links.appendText(guiLink+"\n");
            }else{
                //System.out.println("link already present"+guiLink);
            }
        }
    }
    rAddLink rl = new rAddLink();
    
    public void addLink(String link){
        //System.out.println("Visiting link : "+link);
        guiLink=link;
        Platform.runLater(rl);
    }
    static String linkSeed;
    public void fetchLinkSeed(){
        linkSeed=tf_link_seed.getText();
    }
    public static String getLinkSeed(){
        System.out.println("Link Seed:"+linkSeed);
        return linkSeed;
    }
    static String extensions_str;
    public static String skipExtensions[];
    public void fetchExtensions(){
        extensions_str=tf_extensions.getText();
        String s[]=defaultExtenstions.split(",");
        skipExtensions=new String[s.length];
        int i=0;
        for(String ext:s){
            if(ext.length()==3){
                skipExtensions[i]=ext;
                i++;
            }
            else{
                skipExtensions[i]="";
                i++;
            }
        }
    }
    public static String getExtensions(){
        return extensions_str;
    }
    public static String[] getExtensionArray(){
        return skipExtensions;
    }
    static int maxDelay;
    public void fetchMaxDelay(){
        String s = tf_max_delay.getText();
        System.out.println("s="+s);
        if(null==s||s.equals("")){
            maxDelay=200;
        }
        else maxDelay=Integer.parseInt(s);
    }
    
    public static int getMaxDelay(){
        return maxDelay;
    }
    
    static int maxDepth;
    public void fetchMaxDepth(){
        String s = tf_max_depth.getText();
        System.out.println("s="+s);
        if(null==s||s.equals("")){
            maxDepth=200;
        }
        else maxDepth=Integer.parseInt(s);
    }
    
    public static int getMaxDepth(){
        return maxDepth;
    }

    static int maxPages;
    public void fetchMaxPages(){
        String s = tf_max_pages.getText();
        System.out.println("s="+s);
        if(null==s||s.equals("")){
            maxPages=2000;
        }
        else maxPages=Integer.parseInt(s);
    }
    
    public static int getMaxPages(){
        return maxPages;
    }
    
    String guiText ="";
    class rAddText implements Runnable{
        @Override
        public void run() {
            ta_text_features.setText(guiText+"\n");
        }
    }
    rAddText rt = new rAddText();
    
    public void setTextFeatures(String features){
        guiText=features;
        Platform.runLater(rt);
    }
    
    String guiAnchor ="";
    class rAddAnchor implements Runnable{
        @Override
        public void run() {
            ta_anchor_features.setText(guiAnchor+"\n");
        }
    }
    rAddAnchor ra = new rAddAnchor();
    
    public void setAnchorFeatures(String features){
        guiAnchor=features;
        Platform.runLater(ra);
    }
    
    String guiURL ="";
    class rAddURL implements Runnable{
        @Override
        public void run() {
            ta_url_features.setText(guiURL+"\n");
        }
    }
    rAddURL ru = new rAddURL();
    
    public void setURLFeatures(String features){
        guiURL=features;
        Platform.runLater(ru);
    }
    
    public void setNumLinks(String numLinks){
        lbl_numLinks.setText(numLinks);
    }
    
    public void enableSaveBtn(){
        btn_saveLinks.setDisable(false);
    }
    
}
