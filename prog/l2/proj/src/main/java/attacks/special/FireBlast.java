package main.java.attacks;

import ru.ifmo.se.pokemon.*;

public class FireBlast extends SpecialMove {

    public FireBlast() {
        super(Type.FIRE, 0, 100);
    }

    @Override
    public void applyOppEffects(Pokemon pokemon) {
        if (Math.random() < 0.1) {
            Effect.burn(pokemon);
        }
    }

    @Override
    public String describe() {
        return "FireBlast: has a 10% chance of burning the target.";
    }
}
