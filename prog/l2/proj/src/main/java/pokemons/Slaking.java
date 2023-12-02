package main.java.pokemons;

import main.java.attacks.HammerArm;

import ru.ifmo.se.pokemon.*;

public class Slaking extends Vigoroth {
    public Slaking(String name, int lvl) {
        super(name, lvl);
        this.addMove(new HammerArm());
    }
}
