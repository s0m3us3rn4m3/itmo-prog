package main.java.pokemons;

import main.java.attacks.BulkUp;
import main.java.attacks.Rest;
import main.java.attacks.Swagger;

import ru.ifmo.se.pokemon.*;

public class Tyrogue extends Pokemon {
    public Tyrogue(String name, int lvl) {
        super(name, lvl);
        this.setStats(35, 35, 35, 35, 35, 35);
        this.setType(Type.FIGHTING);
        this.setMove(new Swagger(), new Rest(), new BulkUp());
    }
}
