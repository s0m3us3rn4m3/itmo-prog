package prog.t2.tmp;

import java.util.LinkedList;
import java.util.Queue;

/* compiled from: Battle.java */
/* loaded from: Pokemon.jar:ru/ifmo/se/pokemon/Team.class */
class Team {
    private Queue<Pokemon> team = new LinkedList();
    private Pokemon pokemon;
    private String name;

    public Team(String str) {
        this.name = str;
    }

    public void add(Pokemon pokemon) {
        this.team.add(pokemon);
    }

    public Pokemon next() {
        if (this.pokemon == null || !this.pokemon.isAlive()) {
            this.pokemon = this.team.poll();
            this.pokemon.restore();
            System.out.print(this.pokemon + " " + Messages.get("from") + " " + this.name);
            System.out.println(" " + Messages.get("enter"));
        }
        return this.pokemon;
    }

    public boolean hasNext() {
        return !this.team.isEmpty() || this.pokemon.isAlive();
    }

    public Pokemon poke() {
        return this.pokemon;
    }

    public String getName() {
        return this.name;
    }
}