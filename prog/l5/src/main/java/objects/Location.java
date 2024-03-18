package objects;

import java.util.Scanner;

/**
 * Класс для хранения местоположени
 */
public class Location {
    /**
     * Поле не может быть null
     */
    private Double x;
    /**
     * Поле не может быть null
     */
    private Double y;
    private double z;
    /**
     * Поле не может быть null
     */
    private String name;

    /**
     * Считывает объект из потока ввода
     * 
     * @param input поток ввода
     * @return сгенерированный класс
     */
    static public Location readFromScanner(Scanner input) {
        Location l = new Location();

        while (l.x == null) {
            try {
                System.out.print("Введите координату X: ");
                l.x = Double.valueOf(input.nextLine());
            } catch (NumberFormatException e) {
                l.x = null;
            }
        }

        while (l.y == null) {
            try {
                System.out.print("Введите координату Y: ");
                l.y = Double.valueOf(input.nextLine());
            } catch (NumberFormatException e) {
                l.y = null;
            }
        }

        boolean err = true;
        while (err) {
            try {
                System.out.print("Введите координату Z: ");
                l.z = Double.parseDouble(input.nextLine());
                err = false;
            } catch (NumberFormatException e) {
                err = true;
            }
        }

        while (l.name == null) {
            try {
                System.out.print("Введите название: ");
                l.name = input.nextLine();
            } catch (NumberFormatException e) {
                l.name = null;
            }
        }

        return l;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) throws IllegalArgumentException {
        if (x == null) {
            throw new IllegalArgumentException();
        }
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) throws IllegalArgumentException {
        if (y == null) {
            throw new IllegalArgumentException();
        }
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s (%f, %f, %f)", name, x, y, z);
    }
}