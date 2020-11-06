package characters;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlEnum;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Stats {

    private String name;
    private Integer value;

    public Stats() {}

    public Stats(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
    public String getName() {
        return name;
    }

    @XmlEnum
    public static enum  Stattypes {
        Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma;
    }
}
