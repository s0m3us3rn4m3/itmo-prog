package main.java.pokemons;

import main.java.attacks.Growl;
import main.java.attacks.Rest;
import main.java.attacks.TakeDown;
import main.java.attacks.WaterPulse;

import ru.ifmo.se.pokemon.*;

public class Wishiwashi extends Pokemon {
    public Wishiwashi(String name, int lvl) {
        super(name, lvl);
        this.setStats(45, 20, 20, 25, 25, 40);
        this.setType(Type.WATER);
        this.setMove(new Growl(), new Rest(), new TakeDown(), new WaterPulse());
    }
}
