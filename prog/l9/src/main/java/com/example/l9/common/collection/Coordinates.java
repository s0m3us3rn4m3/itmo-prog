package com.example.l9.common.collection;

import java.io.Serializable;
import java.util.Scanner;

/**
 * Класс для хранения координат
 */
public class Coordinates implements Serializable {
    /**
     * Поле не может быть null
     */
    private Integer x;
    /**
     * Максимальное значение поля: 37
     */
    private double y;

    /**
     * Считывает объект из потока ввода
     * 
     * @param input поток ввода
     * @return сгенерированный класс
     */
    static public Coordinates readFromScanner(Scanner input, boolean verbose) {
        Coordinates c = new Coordinates();

        while (c.x == null) {
            if (verbose) {
                System.out.print("Введите координату X (целое число): ");
            }
            try {
                c.x = Integer.valueOf(input.nextLine());
            } catch (NumberFormatException e) {
                c.x = null;
            }
        }
        c.y = -1;
        while (c.y < 0 || c.y > 37) {
            if (verbose) {
                System.out.print("Введите координату Y (вещественное число от 0 до 37): ");
            }
            try {
                c.y = Double.parseDouble(input.nextLine());
            } catch (NumberFormatException e) {
                c.y = -1;
            }
        }
        return c;
    }

    public boolean validate() {
        if (x == null) return false;
        if (y > 37) return false;
        return true;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) throws IllegalArgumentException {
        if (x == null) {
            throw new IllegalArgumentException();
        }
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) throws IllegalArgumentException {
        if (y > 37) {
            throw new IllegalArgumentException();
        }
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %f)", x, y);
    }
}
