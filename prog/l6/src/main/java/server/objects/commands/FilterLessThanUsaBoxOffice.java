package server.objects.commands;

import common.request.FilterLessThanUsaBoxOfficeRequest;
import common.request.Request;
import server.objects.Command;
import server.objects.State;

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

    String res = "";

    private String filterLessThanUsaBoxOffice(double box) {
        res = "";
        state.getCollection().values().stream().filter(movie -> movie.getUsaBoxOffice() == null || movie.getUsaBoxOffice() < box).forEachOrdered(movie -> res += movie.toString() + '\n');
        return res;
    }

    @Override
    public String exec(String[] args) {
        addToHistory(args[0]);
        if (args.length < 2) {
            return error();
        }
        String boxStr = args[1];
        try {
            Double box = Double.parseDouble(boxStr);
            return filterLessThanUsaBoxOffice(box);
        } catch (Exception e) {
            return "Введен неправильный аргумент";
        }
    }

    @Override
    public String exec(Request req) {
        FilterLessThanUsaBoxOfficeRequest r = (FilterLessThanUsaBoxOfficeRequest)req;
        addToHistory(r.getCommand());
        return filterLessThanUsaBoxOffice(r.getBox());
    }
}
