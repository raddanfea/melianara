package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MyApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Main.class.getResource("/fxml/LoadScreen.fxml"));
        stage.setTitle("RPG Character Editor");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        root.getStylesheets().add("/css/stylesheet.css");
        stage.show();
    }
}