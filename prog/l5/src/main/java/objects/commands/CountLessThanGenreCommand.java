package objects.commands;

import enums.MovieGenre;
import objects.Command;
import objects.Movie;
import objects.State;

/**
 * count_less_than_genre genre : вывести количество элементов, значение поля genre которых меньше заданного
 */
public class CountLessThanGenreCommand extends Command {
    public CountLessThanGenreCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "count_less_than_genre genre : вывести количество элементов, значение поля genre которых меньше заданного";
    }

    @Override
    public int exec(String[] args) {
        addToHistory(args[0]);
        if (args.length < 2) {
            error();
            return 0;
        }
        String gStr = args[1];
        try {
            MovieGenre.valueOf(gStr);
        } catch (Exception e) {
            System.out.println("Нет такого жанра");
            return 0;
        }
        int count = 0;
        for (Movie m : state.getCollection().values()) {
            if (m.getMovieGenre().toString().compareTo(gStr) < 0) {
                count += 1;
            }
        }
        System.out.printf("%d\n", count);
        return 0;
    }
}
