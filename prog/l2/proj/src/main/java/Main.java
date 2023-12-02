package main.java;

import main.java.pokemons.Hitmonlee;
import main.java.pokemons.Slaking;
import main.java.pokemons.Slakoth;
import main.java.pokemons.Tyrogue;
import main.java.pokemons.Vigoroth;
import main.java.pokemons.Wishiwashi;

import ru.ifmo.se.pokemon.*;

public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();
        Hitmonlee p1 = new Hitmonlee("p1", 1);
        Slaking p2 = new Slaking("p2", 1);
        Slakoth p3 = new Slakoth("p3", 1);
        Tyrogue p4 = new Tyrogue("p4", 1);
        Vigoroth p5 = new Vigoroth("p5", 1);
        Wishiwashi p6 = new Wishiwashi("p6", 1);
        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p4);
        b.addFoe(p3);
        b.addFoe(p5);
        b.addFoe(p6);
        b.go();
    }    
}
