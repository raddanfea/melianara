package helpers;

import characters.CharacterBase;
import org.tinylog.Logger;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Function that builds filename and path.
 * Checks for bugs and tries to resolve them during loading and saving.
 * Uses JAXBHelper to marshall.
 */
public class CharSaver{

    private static String folder = "Saves/";
    private static String extension = ".xml";

    public static void save(Object o, String filename){

        String output = folder + filename + extension; /*build filename*/

        try {
            JAXBHelper.toXML(o, new FileOutputStream(output)); //throws to JAXBHelper

            Logger.trace("Character Saved to file at {}", output);
        }
        catch(JAXBException | FileNotFoundException e){
            Logger.error("Character could not be saved.\n{}",e);
        }
    }

    public static CharacterBase load(String filename){

        String input = folder + filename + extension; /*build filename*/
        CharacterBase ActiveChar = null;

        try {
            ActiveChar = JAXBHelper.fromXML(CharacterBase.class, new FileInputStream(input));

            Logger.trace("Character Loaded from file at {}", input);
        }
        catch(JAXBException | FileNotFoundException e){
            Logger.error("Character {} not loaded because it could no be found.", input);
            if(filename == null) {
                ActiveChar = new MakeRandomCharacter().MakeRandomCharacter();
                ActiveChar.setName("lastSave");
                Logger.error("Last Save is missing, therefore returning a random character.");
            }
        }
        return ActiveChar;}


}
