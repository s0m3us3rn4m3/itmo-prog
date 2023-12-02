package main.java.attacks;

import ru.ifmo.se.pokemon.*;

public class WaterPulse extends SpecialMove {

    public WaterPulse() {
        super(Type.WATER, 60.0, 100.0);
    }

    @Override
    public void applyOppEffects(Pokemon pokemon) {
        if (Math.random() < 0.2) {
            pokemon.confuse();
        }
    }

    @Override
    public String describe() {
        return "Water Pulse: 20% chance of confusing the target";
    }
}
