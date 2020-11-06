package characters;

import helpers.ClassSkills;
import helpers.MakeRandomCharacter;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CharacterBaseTest {

    CharacterBase ActiveChar = new CharacterBase();

    @Test
    void testCharacterName() {
        ActiveChar.setName("name");
        assertEquals(ActiveChar.getName(),"name");
    }
    @Test
    void testCharacterLevel() {
        ActiveChar.setLevel(1);
        assertEquals(ActiveChar.getLevel(),1);
    }
    @Test
    void testCharacterAge() {
        ActiveChar.setAge(1);
        assertEquals(ActiveChar.getAge(),1);
    }
    @Test
    void testCharacterGender() {
        ActiveChar.setGender(CharacterBase.Gender.valueOf("Female"));
        assertEquals(ActiveChar.getGender().toString(),"Female");
    }
    @Test
    void testCharacterRace() {
        ActiveChar.setRace(CharacterBase.Race.valueOf("Human"));
        assertEquals(ActiveChar.getRace().toString(),"Human");
    }
    @Test
    void testCharacterRpgclass() {
        ActiveChar.setRpgclass(CharacterBase.Rpgclass.valueOf("Warrior"));
        assertEquals(ActiveChar.getRpgclass().toString(),"Warrior");
    }
    @Test
    void testCharacterSkills() {
        ActiveChar.setSkills(ClassSkills.GetSkills(CharacterBase.Rpgclass.valueOf("Warrior"), 1));
        assertEquals(ActiveChar.getSkillsList(),ClassSkills.GetSkills(CharacterBase.Rpgclass.valueOf("Warrior"), 1));
    }
    @Test
    void testCharacterItems() {
        ActiveChar.setItems(List.of("List"));
        assertEquals(ActiveChar.getItems().get(0), "List");
    }
    @Test
    void testCharacterAbilties() {
        ActiveChar.setAbilities(new ArrayList<>(List.of(false)));
        assertEquals(ActiveChar.getAbilities().get(0),false);
    }

    @Test
    void testTypeOfRandom() {
        assertEquals(ActiveChar.getClass(),new MakeRandomCharacter().MakeRandomCharacter().getClass());
    }

}