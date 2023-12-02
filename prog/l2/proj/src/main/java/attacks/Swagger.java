package main.java.attacks;

import ru.ifmo.se.pokemon.*;

public class Swagger extends StatusMove {

    public Swagger() {
        super(Type.NORMAL, 0, 85.0);
    }

    @Override
    public void applySelfEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.ATTACK, 2);
    }

    @Override
    public void applyOppEffects(Pokemon pokemon) {
        pokemon.confuse();
    }

    @Override
    public String describe() {
        return "Swagger: confuses the target and raises its Attack by two stages.";
    }
}
