package objects;

import java.util.*;

import exceptions.ObjectException;
import interfaces.PeopleGroupInterface;


public class PeopleGroup implements PeopleGroupInterface {
    private Human[] people;

    public PeopleGroup(Human[] people) throws ObjectException {
        class NotEnoughPeopleException extends RuntimeException {
            NotEnoughPeopleException() {
                super("Группа должна состоять хотя бы из 2 человек");
            }
        }
        if (people.length < 2) {
            throw new NotEnoughPeopleException();
        }
        this.people = people;
    }

    public String getPeople() {
        List<String> names = new ArrayList<String>();
        for (Human h : people) {
            names.add(h.getName());
        }
        return String.join(", ", names);
    }

    @Override
    public void run(String where, boolean printNames) {
        String to_str = printNames ? getPeople() : toString();
        System.out.printf("%s побежали %s\n", to_str, where);
    }

    @Override
    public void watch(String where, boolean printNames) {
        String to_str = printNames ? getPeople() : toString();
        System.out.printf("%s смотреть %s\n", to_str, where);
    }

    @Override
    public String toString() {
        return "Мы";
    }
}
