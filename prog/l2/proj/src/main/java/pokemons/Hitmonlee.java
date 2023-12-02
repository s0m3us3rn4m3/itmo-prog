package main.java.pokemons;

import main.java.attacks.BulkUp;
import main.java.attacks.Meditate;
import main.java.attacks.Rest;
import main.java.attacks.Swagger;

import ru.ifmo.se.pokemon.*;

public class Hitmonlee extends Tyrogue {
    public Hitmonlee(String name, int lvl) {
        super(name, lvl);
        this.addMove(new Meditate());
    }
}
