package helpers;

import characters.CharacterBase;
import characters.Skills;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * GetSkills creates an arraylist of skills based on RPGCLASS and LEVEL.
 * SkillsToString Handles regex to display skills.
 */
public class ClassSkills {
    public static ArrayList<Skills> GetSkills(CharacterBase.Rpgclass whichclass, Integer level){

        ArrayList<Skills> skills = new ArrayList<>();


        switch(whichclass.toString()) {
            case "Warrior":
                skills.add(new Skills("Warrior Skill 1", "Effect 1"));
                skills.add(new Skills("Warrior Skill 2", "Effect 2"));
                skills.add(new Skills("Warrior Skill 3", "Effect 3"));
                if(level > 5){
                    skills.add(new Skills("Warrior Skill 4", "Effect 4"));
                    skills.add(new Skills("Warrior Skill 5", "Effect 5"));
                }
                if(level > 10){
                    skills.add(new Skills("Warrior Skill 6", "Effect 6"));
                    skills.add(new Skills("Warrior Skill 7", "Effect 7"));
                }
                if(level > 15){
                    skills.add(new Skills("Warrior Skill 8", "Effect 8"));
                }
                break;
            case "Mage":
                skills.add(new Skills("Mage Skill 1", "Effect 1"));
                skills.add(new Skills("Mage Skill 2", "Effect 2"));
                skills.add(new Skills("Mage Skill 3", "Effect 3"));
                if(level > 5){
                    skills.add(new Skills("Mage Skill 4", "Effect 4"));
                    skills.add(new Skills("Mage Skill 5", "Effect 5"));
                }
                if(level > 10){
                    skills.add(new Skills("Mage Skill 6", "Effect 6"));
                    skills.add(new Skills("Mage Skill 7", "Effect 7"));
                }
                if(level > 15){
                    skills.add(new Skills("Mage Skill 8", "Effect 8"));
                }
                break;
            default:
                skills.add(new Skills("Error!", "This is an anti-crash measure."));
                Logger.error("Class skills could not be selected.");
        }
        Logger.trace("Class skills selection finished.");

        return skills;

    }

    public static String SkillsToString(List<Skills> skills){

        String skillsString = skills.toString();
        skillsString = skillsString.replace(',' , '\n');
        skillsString = skillsString.replace('[' , ' ');
        skillsString = skillsString.replace('(' , ' ');
        skillsString = skillsString.replace(')' , ' ');
        skillsString = skillsString.replace("effect=" , "\t");
        skillsString = skillsString.replace("Skills name=" , " ");
        skillsString = skillsString.replace(']' , ' ');

        return skillsString;
    }
}
