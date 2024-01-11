package objects;

import enums.AbilityEnum;
import interfaces.SubjectActions;

import java.util.Objects;

public class Subject implements SubjectActions {
    private String subject;
    
    public Subject(String subject) {
        this.subject = subject;
    }
    
    public String getSubject() {
        return this.subject;
    }

    @Override
    public void approach(String to, AbilityEnum ability) {
        System.out.printf("%s %sприближаться %s\n", this, ability, to);
    }

    @Override
    public String toString() {
        return this.subject;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.subject);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        return this.subject.equals(((Subject)obj).getSubject());
    }
}
