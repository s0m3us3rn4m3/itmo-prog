package main.java.attacks;

import ru.ifmo.se.pokemon.*;

public class TakeDown extends PhysicalMove {

    public TakeDown() {
        super(Type.NORMAL, 90.0, 85.0);
    }

    @Override
    public void applySelfDamage(Pokemon pokemon, double damage) {
        pokemon.setMod(Stat.HP, (int) Math.round(damage*0.25));
    }

    @Override
    public String describe() {
        return "Take Down: user gets 1/4 of the damage";
    }
}
