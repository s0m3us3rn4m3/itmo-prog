package main.java.attacks;

import ru.ifmo.se.pokemon.*;

public class Rest extends StatusMove {

    public Rest() {
        super(Type.PSYCHIC, 0, 100);
    }

    @Override
    public void applySelfEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.HP, -10);
        new Effect().turns(2).sleep(pokemon);
    }

    @Override
    public String describe() {
        return "Rest: sleep 2 turns, HP is 100";
    }
}
