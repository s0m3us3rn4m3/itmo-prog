package main.java.attacks;

import ru.ifmo.se.pokemon.*;

public class HammerArm extends PhysicalMove {

    public HammerArm() {
        super(Type.FIGHTING, 100.0, 90.0);
    }

    @Override
    public void applySelfEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.SPEED, -1);
    }

    @Override
    public String describe() {
        return "Hammer Arm: deals damage but lowers the user's Speed by one stage after attacking.";
    }
}
