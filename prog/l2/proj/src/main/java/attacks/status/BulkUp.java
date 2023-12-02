package main.java.attacks;

import ru.ifmo.se.pokemon.*;

public class BulkUp extends StatusMove {

    public BulkUp() {
        super(Type.FIGHTING, 0, 100);
    }

    @Override
    public void applySelfEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.ATTACK, 1);
        pokemon.setMod(Stat.DEFENSE, 1);
    }

    @Override
    public String describe() {
        return "Bulk Up: raises the user's Attack and Defense by one stage each";
    }
}
