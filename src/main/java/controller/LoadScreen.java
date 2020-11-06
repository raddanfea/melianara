package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class LoadScreen {

    @FXML
    private ChoiceBox<String> loadBox;

    @FXML
    private ImageView nemeiah;
    @FXML
    private ImageView luran;

    /**
     * Checks if Saves folder exists, if missing attempts to create it.
     * Reads files from Saves folder.
     * If save files are missing, defaults on lastSave.
     * Attempts to load a pretty image to display.
     */
    public void initialize() {

        if(!Files.isDirectory(Path.of("Saves"))){
            try{
                Files.createDirectory(Path.of("Saves"));
            }
            catch(Exception e){
                Logger.error("Saves folder missing and program failed to generate one.{}",e);
            }
        }

        try{
            File[] files = new File("Saves").listFiles();

            for (File file : files) {
                if (file.isFile()) {
                    loadBox.getItems().add(
                            file.getName().substring(
                                    0, file.getName().lastIndexOf('.')));
                }
            }
        } catch(Exception e){
            Logger.error("Files could not be read.{}",e);
        }

        if(loadBox.getItems().contains("lastSave")){   loadBox.setValue("lastSave");
        }

        try{
            nemeiah.setImage(new Image(getClass().getResource("/img/nemeiah.png").toExternalForm()));
        } catch(Exception e){
            Logger.error("Couldn't load image. {}",e);}
    }

    /**
     * Button moves scene to Character Editor, loads css, gives filename to load. Exceptions are handled in the loader.
     * @param actionEvent Button event
     * @throws IOException Button exception
     */
    public void MakeAndShowRandomChar(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CharScreen.fxml"));
        Parent root = fxmlLoader.load();
        fxmlLoader.<CharScreenController>getController().initdata(loadBox.getValue());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        root.getStylesheets().add("/css/stylesheet.css");
        stage.show();
        Logger.trace("Loading Character: {}", this.loadBox.getValue());
    }
}