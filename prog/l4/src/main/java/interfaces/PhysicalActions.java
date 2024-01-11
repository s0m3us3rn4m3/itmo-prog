package interfaces;

import enums.AbilityEnum;

public interface PhysicalActions {
    public void run(String where, AbilityEnum ability);
    public void loose(String subject, AbilityEnum ability);
    public void watch(String subject, AbilityEnum ability);
    public void see(String subject, AbilityEnum ability);
}
