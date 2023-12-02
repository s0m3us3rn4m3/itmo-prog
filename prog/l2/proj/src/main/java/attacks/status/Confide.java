package main.java.attacks;

import ru.ifmo.se.pokemon.*;

public class Confide extends StatusMove {

    public Confide() {
        super(Type.NORMAL, 0, 100);
    }

    @Override
    public void applyOppEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.ATTACK, -1);
    }

    @Override
    public String describe() {
        return "Confide: lowers the target's Special Attack by one stage.";
    }
}
