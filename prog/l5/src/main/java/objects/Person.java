package objects;

import java.util.Scanner;

import enums.Country;

/**
 * Класс для хранения человека
 */
public class Person {
    /**
     * Поле не может быть null, Строка не может быть пустой
     */
    private String name;
    /**
     * Значение поля должно быть больше 0
     */
    private long height;
    /**
     * Поле не может быть null
     */
    private Country nationality;
    /**
     * Поле не может быть null
     */
    private Location location;

    /**
     * Считывает объект из потока ввода
     * 
     * @param input поток ввода
     * @return сгенерированный класс
     */
    static public Person read_from_scanner(Scanner input) {
        Person p = new Person();

        while (p.name == null || p.name.isEmpty()) {
            System.out.print("Введите имя: ");
            p.name = input.nextLine();
        }

        p.height = 0;
        while (p.height <= 0) {
            try {
                System.out.print("Введите рост: ");
                p.height = Long.parseLong(input.nextLine());
            } catch (NumberFormatException e) {
                p.height = 0;
            }
        }

        while (p.nationality == null) {
            try {
                System.out.print("Введите страну (UNITED_KINGDOM / SPAIN / INDIA / VATICAN): ");
                p.nationality = Country.valueOf(input.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                p.nationality = null;
            }
        }

        p.location = Location.read_from_scanner(input);

        return p;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long h) throws IllegalArgumentException {
        if (h <= 0) {
            throw new IllegalArgumentException();
        }
        this.height = h;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country c) throws IllegalArgumentException {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        this.nationality = c;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location l) throws IllegalArgumentException {
        if (l == null) {
            throw new IllegalArgumentException();
        }
        this.location = l;
    }

    @Override
    public String toString() {
        return String.format("Имя: %s\nРост: %s\nСтрана: %s\nМестоположение: %s", name, height, nationality, location);
    }
}