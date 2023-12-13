package objects;

import interfaces.MediaActions;

import enums.AbilityEnum;
import enums.MediaEnum;

public class Newspaper extends BaseMedia implements MediaActions {
    
    public Newspaper(String name) {
        super(name, MediaEnum.NEWSPAPER);
    }

    @Override
    public void publish(String news, AbilityEnum ability) {
        System.out.printf("%s %sнапечатала %s\n", this, ability, news);
    }

}
