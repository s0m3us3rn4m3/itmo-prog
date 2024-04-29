package common.objects;

import java.io.Serializable;
import java.util.Scanner;

public class Credentials implements Serializable {
    private String login;
    private String password;

    static public Credentials readFromScanner(Scanner input, boolean verbose) {
        Credentials c = new Credentials();
        while (c.login == null || c.login.isEmpty()) {
            if (verbose) {
                System.out.println("Введите логин: ");
            }
            c.login = input.nextLine();
        }
        while (c.password == null || c.password.isEmpty()) {
            if (verbose) {
                System.out.println("Введите пароль: ");
            }
            c.password = input.nextLine();
        }
        return c;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
