package characters;

import lombok.Data;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Skills {

    private String name;
    private String effect;

    public Skills() {}

    public Skills(String name, String effect) {
        this.name = name;
        this.effect = effect;
    }
}