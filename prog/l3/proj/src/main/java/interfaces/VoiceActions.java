package interfaces;

import enums.AbilityEnum;

public interface VoiceActions {
    public void shout(String text, AbilityEnum ability);
    public void tell(String text, AbilityEnum ability);
}
