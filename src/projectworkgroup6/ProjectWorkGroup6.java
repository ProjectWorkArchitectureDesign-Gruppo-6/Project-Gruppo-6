/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package projectworkgroup6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import projectworkgroup6.Controller.MainController;

/**
 * 
 * @author andreadm
 */
public class ProjectWorkGroup6 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/projectworkgroup6/Interfacce/Main_layout.fxml"));
        Parent root = loader.load();

        
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        passScene(scene, loader);
    }

    private void passScene(Scene scene, FXMLLoader loader) {
        MainController mainController = loader.getController();
        mainController.passScene(scene);
    }



    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}