package enums;

public enum AbilityEnum {
    DO(""),
    DONT("не "),
    CAN("мог "),
    CANNOT("не мог "),
    WOULDNT("не стал бы ");

    private String ability;

    private AbilityEnum(String ability) {
        this.ability = ability;
    }

    public String getAbility() {
        return ability;
    }

    @Override
    public String toString() {
        return ability;
    }
}
