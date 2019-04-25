/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Ashish Padalkar
 */
public class Webcrawler  extends Application{

    /**
     * @param args the command line arguments
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui.fxml"));
        Parent root  = fxmlLoader.load();
        gcn= (GuiController) fxmlLoader.getController();
        //Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
        
        if(null==gcn){
            System.out.println("got null for controller");
        }else{
            System.out.println("Got proper controller");
        }
        Scene scene = new Scene(root);
        
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        
        
        stage.setScene(scene);
        stage.show();
    }
    static GuiController gcn;
    public static GuiController getController(){
        return gcn;
    }
    void setController(GuiController gc){
        this.gcn=gc;
    }
    public static void main(String[] args) {
        System.out.println("Webcrawler");
        launch(args);
        // TODO code application logic here
    }
    
    
    
}
