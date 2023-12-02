package main.java.attacks;

import ru.ifmo.se.pokemon.*;

public class Growl extends StatusMove {

    public Growl() {
        super(Type.NORMAL, 0, 100);
    }

    @Override
    public void applyOppEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.ATTACK, -1);
    }

    @Override
    public String describe() {
        return "Growl: target's ATTACK is lowering by 1 stage";
    }

}
