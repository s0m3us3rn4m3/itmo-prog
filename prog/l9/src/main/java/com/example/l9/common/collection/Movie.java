package com.example.l9.common.collection;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Класс, который хранится в коллекции. Содержит информацию о фильме.
 */
public class Movie implements Comparable<Movie>, Serializable {
    /**
     * Поле не может быть null, Значение поля должно быть больше 0, Значение этого
     * поля должно быть уникальным, Значение этого поля должно генерироваться
     * автоматически
     */
    private Long id;
    /**
     * Поле не может быть null, Строка не может быть пустой
     */
    private String name;
    /**
     * Поле не может быть null
     */
    private Coordinates coordinates;
    /**
     * Поле не может быть null, Значение этого поля должно
     * генерироваться автоматически
     */
    private java.time.ZonedDateTime creationDate;
    /**
     * Значение поля должно быть больше 0, Поле не может быть null
     */
    private Long oscarsCount;
    /**
     * Поле может быть null, Значение поля должно быть больше 0
     */
    private Double usaBoxOffice;
    /**
     * Поле не может быть null
     */
    private MovieGenre genre;
    /**
     * Поле не может быть null
     */
    private MpaaRating mpaaRating;
    private Person screenwriter;

    private String owner;
    private String key;

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean validate() {
        if (id == null || id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null || !coordinates.validate()) return false;
        if (creationDate == null) return false;
        if (oscarsCount == null || oscarsCount <= 0) return false;
        if (usaBoxOffice == null || usaBoxOffice <= 0) return false;
        if (genre == null) return false;
        if (mpaaRating == null) return false;
        if (screenwriter != null) {
            return screenwriter.validate();
        }
        return true;
    }

    @Override
    public int compareTo(Movie m) {
        Double box = m.getUsaBoxOffice();
        if (this.usaBoxOffice != box) {
            if (this.usaBoxOffice != null && box != null) {
                return (this.usaBoxOffice < box ? -1 : 1);
            }
            if (this.usaBoxOffice == null) {
                return -1;
            }
            return 1;
        }
        Long ocnt = m.getOscarsCount();
        if (this.oscarsCount != ocnt) {
            if (this.oscarsCount < ocnt) {
                return -1;
            }
            return 1;
        }
        String g = m.getMovieGenre().toString();
        if (this.genre.toString() != g) {
            return this.genre.toString().compareTo(g);
        }
        return this.name.compareTo(m.getName());
    }

    public static ZonedDateTime genCreationDate() {
        return ZonedDateTime.now();
    }

    /**
     * Считывает данные для класса из потока ввода
     * 
     * @param input поток ввода
     * @return cчитанный фильм
     */
    static public Movie readFromScanner(Scanner input, boolean verbose) {
        Movie m = new Movie();
        m.creationDate = genCreationDate();

        while (m.name == null || m.name.isEmpty()) {
            if (verbose) {
                System.out.print("Введите название фильма: ");
            }
            m.name = input.nextLine();
        }

        m.coordinates = Coordinates.readFromScanner(input, verbose);

        while (m.oscarsCount == null || m.oscarsCount <= 0) {
            if (verbose) {
                System.out.print("Введите число оскаров: ");
            }
            try {
                m.oscarsCount = Long.valueOf(input.nextLine());
            } catch (NumberFormatException e) {
                m.oscarsCount = null;
            }
        }

        while (m.usaBoxOffice == null || m.usaBoxOffice <= 0) {
            if (verbose) {
                System.out.print("Введите сумму кассового сбора: ");
            }
            try {
                String s = input.nextLine();
                if (s.isEmpty()) {
                    m.usaBoxOffice = null;
                    break;
                }
                m.usaBoxOffice = Double.valueOf(s);
            } catch (NumberFormatException e) {
                m.usaBoxOffice = null;
            }
        }

        while (m.genre == null) {
            if (verbose) {
                System.out.print("Введите жанр (COMEDY / TRAGEDY / THRILLER / HORROR / SCIENCE_FICTION): ");
            }
            try {
                m.genre = MovieGenre.valueOf(input.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                m.genre = null;
            }
        }

        while (m.mpaaRating == null) {
            if (verbose) {
                System.out.print("Введите рейтинг (G / PG / R): ");
            }
            try {
                m.mpaaRating = MpaaRating.valueOf(input.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                m.mpaaRating = null;
            }
        }

        if (verbose) {
            System.out.print("Хотите ли вы указать сценариста (Y / any other key): ");
        }
        String ch = input.nextLine();
        if (ch.trim().equals("Y")) {
            m.screenwriter = Person.readFromScanner(input, verbose);
        }

        return m;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) throws IllegalArgumentException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException();
        }
        this.id = id;
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

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates c) throws IllegalArgumentException {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        this.coordinates = c;
    }

    public java.time.ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(java.time.ZonedDateTime t) throws IllegalArgumentException {
        if (t == null) {
            throw new IllegalArgumentException();
        }
        this.creationDate = t;
    }

    public Long getOscarsCount() {
        return oscarsCount;
    }

    public void setOscarsCount(Long c) throws IllegalArgumentException {
        if (c == null || c <= 0) {
            throw new IllegalArgumentException();
        }
        this.oscarsCount = c;
    }

    public Double getUsaBoxOffice() {
        return usaBoxOffice;
    }

    public void setUsaBoxOffice(Double d) throws IllegalArgumentException {
        if (d != null && d <= 0) {
            throw new IllegalArgumentException();
        }
        this.usaBoxOffice = d;
    }

    public MovieGenre getMovieGenre() {
        return genre;
    }

    public void setMovieGenre(MovieGenre g) throws IllegalArgumentException {
        if (g == null) {
            throw new IllegalArgumentException();
        }
        this.genre = g;
    }

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(MpaaRating r) throws IllegalArgumentException {
        if (r == null) {
            throw new IllegalArgumentException();
        }
        this.mpaaRating = r;
    }

    public Person getScreenWriter() {
        return screenwriter;
    }

    public void setScreenWriter(Person p) {
        this.screenwriter = p;
    }

    @Override
    public String toString() {
        String res = "";
        res += String.format("ID: %d\n", id);
        res += String.format("Название: %s\n", name);
        res += String.format("Дата создания: %s\n", creationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        res += String.format("Координаты: %s\n", coordinates);
        res += String.format("Количество оскаров: %d\n", oscarsCount);
        if (usaBoxOffice != null) {
            res += String.format("Сбор: %f\n", usaBoxOffice);
        }
        res += String.format("Жанр: %s\n", genre);
        res += String.format("Рейтинг: %s\n", mpaaRating);
        if (screenwriter != null) {
            res += String.format("Сценарист: \n---\n%s\n---", screenwriter);
        }
        return res;
    }
}
