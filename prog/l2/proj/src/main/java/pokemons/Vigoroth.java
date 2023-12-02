package main.java.pokemons;

import main.java.attacks.Confide;
import main.java.attacks.FireBlast;
import main.java.attacks.FocusEnergy;

import ru.ifmo.se.pokemon.*;

public class Vigoroth extends Slakoth {
    public Vigoroth(String name, int lvl) {
        super(name, lvl);
        this.addMove(new FocusEnergy());
    }
}
