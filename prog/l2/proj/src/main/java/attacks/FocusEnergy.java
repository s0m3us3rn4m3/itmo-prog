package main.java.attacks;

import ru.ifmo.se.pokemon.*;

public class FocusEnergy extends StatusMove {

    public FocusEnergy() {
        super(Type.NORMAL, 0, 100);
    }

    @Override
    public void applySelfEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.SPECIAL_ATTACK, 1);
    }

    @Override
    public String describe() {
        return "FocusEnergy: Increases critical hit ratio.";
    }
}
