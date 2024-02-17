package main.java;

import java.util.Scanner;

import objects.Executor;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("usage: prog {file_to_save}");
            return;
        }
        Scanner input = new Scanner(System.in);
        Executor e = new Executor(input, args[0]);
        try {
            e.process(true);
        } finally {
            input.close();
        }
    }
}
