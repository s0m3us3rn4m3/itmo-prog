package server.objects.commands;

import common.request.FilterLessThanUsaBoxOfficeRequest;
import common.request.Request;
import common.response.Response;
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

    class Helper {
        private String res = "";

        public String exec(State state, double box) {
            res = "";
            state.getCollection().values().stream().filter(movie -> movie.getUsaBoxOffice() == null || movie.getUsaBoxOffice() < box).forEachOrdered(movie -> res += movie.toString() + '\n');
            return res;
        }
    }

    private String filterLessThanUsaBoxOffice(double box) {
        state.getLock().readLock().lock();
        try {
            return new Helper().exec(state, box);
        } finally {
            state.getLock().readLock().unlock();
        }
    }

    @Override
    public Response exec(String[] args) {
        addToHistory(args[0]);
        if (args.length < 2) {
            return error();
        }
        String boxStr = args[1];
        try {
            Double box = Double.parseDouble(boxStr);
            return new Response(filterLessThanUsaBoxOffice(box), true);
        } catch (Exception e) {
            return new Response("Введен неправильный аргумент", false);
        }
    }

    @Override
    public Response exec(Request req) {
        FilterLessThanUsaBoxOfficeRequest r = (FilterLessThanUsaBoxOfficeRequest)req;
        addToHistory(r.getCommand());
        return new Response(filterLessThanUsaBoxOffice(r.getBox()), true);
    }
}
