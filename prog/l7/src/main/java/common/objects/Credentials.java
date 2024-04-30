package common.objects;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Credentials implements Serializable {
    private String login;
    private String password;

    static private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-224");
        byte[] messageDigest = md.digest(password.getBytes());
        return String.format("%032x", new BigInteger(1, messageDigest));
    }

    static public Credentials readFromScanner(Scanner input, boolean verbose) {
        Credentials c = new Credentials();
        while (c.login == null || c.login.isEmpty()) {
            if (verbose) {
                System.out.println("Введите логин: ");
            }
            c.login = input.nextLine();
        }
        String password = null;
        while (password == null || password.isEmpty()) {
            if (verbose) {
                System.out.println("Введите пароль: ");
            }
            password = input.nextLine();
        }
        try {
            c.password = hashPassword(password);
        } catch (NoSuchAlgorithmException e) {
            c.password = password;
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
