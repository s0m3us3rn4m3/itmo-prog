package objects.commands;

import objects.Command;
import objects.Movie;
import objects.State;

/**
 * filter_less_than_usa_box_office usaBoxOffice : вывести элементы, значение поля usaBoxOffice которых меньше заданного
 */
public class FilterLessThanUsaBoxOffice extends Command {
    public FilterLessThanUsaBoxOffice(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "filter_less_than_usa_box_office usaBoxOffice : вывести элементы, значение поля usaBoxOffice которых меньше заданного";
    }

    @Override
    public int exec(String[] args) {
        addToHistory(args[0]);
        if (args.length < 2) {
            error();
            return 0;
        }
        String boxStr = args[1];
        try {
            Double box = Double.parseDouble(boxStr);
            for (Movie m : state.getCollection().values()) {
                if (m.getUsaBoxOffice() == null || m.getUsaBoxOffice() < box) {
                    System.out.println(m);
                }
            }
        } catch (Exception e) {
            System.out.println("Введен неправильный аргумент");
        }
        return 0;
    }
}
