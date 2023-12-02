package main.java.pokemons;

import main.java.attacks.Confide;
import main.java.attacks.FireBlast;

import ru.ifmo.se.pokemon.*;

public class Slakoth extends Pokemon {
    public Slakoth(String name, int lvl) {
        super(name, lvl);
        this.setStats(60, 60, 60, 35, 35, 35);
        this.setType(Type.NORMAL);
        this.setMove(new FireBlast(), new Confide());
    }
}
