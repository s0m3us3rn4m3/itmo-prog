package main.java.pokemons;

import main.java.attacks.BulkUp;
import main.java.attacks.Meditate;
import main.java.attacks.Rest;
import main.java.attacks.Swagger;

import ru.ifmo.se.pokemon.*;

public class Hitmonlee extends Pokemon {
    public Hitmonlee(String name, int lvl) {
        super(name, lvl);
        this.setStats(50, 120, 53, 35, 110, 87);
        this.setType(Type.FIGHTING);
        this.setMove(new Swagger(), new Rest(), new BulkUp(), new Meditate());
    }
}
