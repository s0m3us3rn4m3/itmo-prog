package enums;

public enum MediaEnum {
    NEWSPAPER("Газета");

    private String media;

    MediaEnum(String media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return this.media;
    }

}
