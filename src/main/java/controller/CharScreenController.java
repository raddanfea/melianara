package controller;

import characters.CharacterBase;
import helpers.MakeRandomCharacter;
import helpers.CharSaver;
import helpers.CharacterSaveLogic;
import helpers.ClassSkills;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharScreenController {

    private CharacterBase ActiveChar;
    private String loadedCharName;

    @FXML    private TextField nameTxf;
    @FXML    private TextField ageTxf;
    @FXML    private TextField levelTxf;

    @FXML    private TextArea itemsTxf;

    @FXML    private Label skillsTxf;
    @FXML    private Label profLabel;

    @FXML    private Label Str;
    @FXML    private Label Dex;
    @FXML    private Label Con;
    @FXML    private Label Int;
    @FXML    private Label Wis;
    @FXML    private Label Char;

    @FXML    private ChoiceBox<Enum> genderChoice;
    @FXML    private ChoiceBox<Enum> classChoice;
    @FXML    private ChoiceBox<Enum> raceChoice;

    @FXML private Button randomButton;
    @FXML private Button loadButton;
    @FXML private Button backButton;

    @FXML private CheckBox checkBox1;
    @FXML private CheckBox checkBox2;
    @FXML private CheckBox checkBox3;
    @FXML private CheckBox checkBox4;
    @FXML private CheckBox checkBox5;
    @FXML private CheckBox checkBox6;
    @FXML private CheckBox checkBox7;
    @FXML private CheckBox checkBox8;
    @FXML private CheckBox checkBox9;
    @FXML private CheckBox checkBox10;
    @FXML private CheckBox checkBox11;

    /**
     * When initializing, builds elements of ChoiceBoxes and creates listener to update the page when changed.
     * Attempts loading icons from resources.
     */
    public void initialize() {

        raceChoice.getItems().addAll(CharacterBase.Race.values());
        classChoice.getItems().addAll(CharacterBase.Rpgclass.values());
        genderChoice.getItems().addAll(CharacterBase.Gender.values());

        raceChoice.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) -> {
            ActiveChar.setRace(CharacterBase.Race.valueOf(raceChoice.getItems().get((Integer) number2).toString()));
            refresh();
        });
        genderChoice.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) -> {
            ActiveChar.setGender(CharacterBase.Gender.valueOf(genderChoice.getItems().get((Integer) number2).toString()));
            refresh();
        });
        classChoice.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) -> {
            ActiveChar.setRpgclass(CharacterBase.Rpgclass.valueOf(classChoice.getItems().get((Integer) number2).toString()));
            refresh();
        });


        try{
            loadButton.setGraphic(new ImageView( new Image(getClass().getResource("/img/diskette.png").toExternalForm())));
            randomButton.setGraphic(new ImageView( new Image(getClass().getResource("/img/dice.png").toExternalForm())));
            backButton.setGraphic(new ImageView( new Image(getClass().getResource("/img/arrow.png").toExternalForm())));
            randomButton.setText("");
            loadButton.setText("");
            backButton.setText("");
            String hideShape = "-fx-background-color: rgba(0,0,0,0);";
            randomButton.setStyle(hideShape);
            loadButton.setStyle(hideShape);
            backButton.setStyle(hideShape);
        }catch (Exception e){
            Logger.error("Icons could not be loaded. {}", e);
        }
    }


    /**
     * Catches savefile name from the loading screen then loads it.
     * @param loadCharName Name to load.
     */
    public void initdata(String loadCharName) {

        this.loadedCharName = loadCharName;
        ActiveChar = CharSaver.load(loadedCharName);

        refresh();
    }

    /**
     * Generates random character then displays it.
     */
    public void randomCharGenSet(ActionEvent actionEvent) {

        ActiveChar = new MakeRandomCharacter().MakeRandomCharacter();
        CharSaver.save(ActiveChar,"lastSave");
        refresh();
    }

    /**
     * Refreshes actively shown data.
     * Skills are built with ClassSkills helper.
     * Items are a string regex.
     * Proficiency (profLabel) is one-way basic calculation, not saved in file.
     * Stats stream to writes saved values to labels.
     * CheckBox stream sets checkboxes to saved values.
     */
    public void refresh(){

        nameTxf.setText(ActiveChar.getName());
        levelTxf.setText(ActiveChar.getLevel().toString());
        ageTxf.setText(ActiveChar.getAge().toString());
        genderChoice.setValue(ActiveChar.getGender());
        classChoice.setValue(ActiveChar.getRpgclass());
        raceChoice.setValue(ActiveChar.getRace());
        skillsTxf.setText(ClassSkills.SkillsToString(ClassSkills.GetSkills(ActiveChar.getRpgclass(),ActiveChar.getLevel())));
        itemsTxf.setText(String.join(",", ActiveChar.getItems()).replace(",",",\n"));
        profLabel.setText("+" + (2 + ActiveChar.getLevel() / 3));

        List<Label> statsList = List.of(Str,Dex,Con,Int,Wis,Char);
        statsList.stream().forEach((StatsTemp) -> StatsTemp.setText(ActiveChar.getStats().get(statsList.indexOf(StatsTemp)).getValue().toString()));

        List<CheckBox> checkBoxList = List.of(checkBox1,checkBox2,checkBox3,checkBox4,checkBox5,checkBox6,checkBox7,checkBox8,checkBox9,checkBox10,checkBox11);
        checkBoxList.parallelStream().forEach((AbilityTemp) -> AbilityTemp.setSelected(ActiveChar.getAbilities().get(checkBoxList.indexOf(AbilityTemp))));

        Logger.trace("Page refreshed.");

    }

    /**
     * Manual save button calls save.
     * @param event Button press event
     * @throws IOException Button press exception
     */
    public void saveButton(ActionEvent event)throws IOException {
        refreshToFile();
        Logger.trace("Save was manual.");
    }

    /**
     * Saves data to file and calls refresh to update displayed data.
     */
    public void refreshToFile(){

        Logger.trace("Saving to file...");

        CharacterSaveLogic.SaveLogic(
                nameTxf.getText(),
                levelTxf.getText(),
                ageTxf.getText(),
                genderChoice.getValue(),
                raceChoice.getValue(),
                classChoice.getValue(),
                itemsTxf.getText(),
                ActiveChar.getStats(),
                ActiveChar.getAbilities());

        ActiveChar = CharSaver.load("lastSave");
        refresh();
    }

    /**
     * Saves data to memory and calls refresh to update displayed data.
     */
    public void refreshTemp(){

        Logger.trace("Saving to memory.");

        try {
            ActiveChar.setName(nameTxf.getText());
            ActiveChar.setLevel(Integer.parseInt(levelTxf.getText()));
            ActiveChar.setAge(Integer.parseInt(ageTxf.getText()));
            ActiveChar.setGender(CharacterBase.Gender.valueOf(genderChoice.getValue().toString()));
            ActiveChar.setRace(CharacterBase.Race.valueOf(raceChoice.getValue().toString()));
            ActiveChar.setRpgclass(CharacterBase.Rpgclass.valueOf(classChoice.getValue().toString()));
            ActiveChar.setSkills(ActiveChar.getSkillsList());
            ActiveChar.setItems(Arrays.asList(itemsTxf.getText().replace("\n", "").split(",")));
            ActiveChar.setStats(ActiveChar.getStats());
            ActiveChar.setAbilities(ActiveChar.getAbilities());
        }catch(Exception e){Logger.error("Error while saving to memory. Invalid inputs? {}",e);}
        refresh();
    }

    /**
     * @param event Detects checkbox changes and saves them to memory.
     */
    @FXML
    private void handleCheckBoxAction(ActionEvent event) {
        CheckBox operatorPressed = ((CheckBox) event.getSource());
        List<CheckBox> checkBoxList = List.of(checkBox1,checkBox2,checkBox3,checkBox4,checkBox5,checkBox6,checkBox7,checkBox8,checkBox9,checkBox10,checkBox11);
        ArrayList<Boolean> CharTemp = ActiveChar.getAbilities();
        CharTemp.set(checkBoxList.indexOf(operatorPressed),operatorPressed.selectedProperty().getValue());
        ActiveChar.setAbilities(CharTemp);
    }


    /**
     * Button to return to the load screen.
     * @param event Button event
     * @throws IOException Button exception
     */
    public void goBack(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoadScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        root.getStylesheets().add("/css/stylesheet.css");
        stage.show();
    }

}


