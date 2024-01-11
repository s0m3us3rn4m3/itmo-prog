package objects;

import interfaces.MentalActions;
import interfaces.PhysicalActions;
import interfaces.VoiceActions;

import enums.AbilityEnum;
import exceptions.ObjectException;

import java.util.Objects;

public class Human implements VoiceActions, MentalActions, PhysicalActions {
    private String name;
    private int age;

    static public class InvalidAgeException extends RuntimeException {
        InvalidAgeException() {
            super("Возраст должен быть от 0 до 100");
        }
    }

    public Human(String name, int age) throws InvalidAgeException {
        if (!(0 <= age && age <= 100)) {
            throw new InvalidAgeException();
        }
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    @Override
    public void shout(String text, AbilityEnum ability) {
        System.out.printf("%s %sзакричать %s\n", this, ability, text);
    }

    @Override
    public void tell(String text, AbilityEnum ability) {
        System.out.printf("%s %sсказать %s\n", this, ability, text);
    }

    @Override
    public void remember(String thing, AbilityEnum ability) {
        System.out.printf("%s %sзапомнить %s\n", this, ability, thing);
    }

    @Override
    public void feel(String thing, AbilityEnum ability) {
        System.out.printf("%s %sпочувствовать %s\n", this, ability, thing);
    }

    @Override
    public void loose(String subject, AbilityEnum ability) {
        System.out.printf("%s %sпотерять %s\n", this, ability, subject);
    }

    @Override
    public void run(String where, AbilityEnum ability) {
        System.out.printf("%s %sпобежать %s\n", this, ability, where);
    }

    @Override
    public void watch(String subject, AbilityEnum ability) {
        System.out.printf("%s %sсмотреть %s\n", this, ability, subject);
    }

    @Override
    public void see(String subject, AbilityEnum ability) {
        System.out.printf("%s %sувидеть %s\n", this, ability, subject);
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        return this.name.equals(((Human)obj).getName());
    }
}
