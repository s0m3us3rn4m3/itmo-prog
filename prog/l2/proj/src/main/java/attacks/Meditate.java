package main.java.attacks;

import ru.ifmo.se.pokemon.*;

public class Meditate extends PhysicalMove {

    public Meditate() {
        super(Type.PSYCHIC, 0, 100);
    }

    @Override
    public void applySelfEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.ATTACK, 1);
    }

    @Override
    public String describe() {
        return "Meditate: raises the user's Attack by one stage.";
    }
}
