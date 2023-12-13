package objects;

import enums.MediaEnum;

import java.util.Objects;

public abstract class BaseMedia {    
    private MediaEnum type;
    private String name;

    public BaseMedia(String name, MediaEnum type) {
        this.type = type;
        this.name = name;
    }

    public MediaEnum getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return String.format("%s \"%s\"", this.type, this.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        return this.name.equals(((BaseMedia)obj).getName()) && this.type.equals(((BaseMedia)obj).getType());
    }
}
