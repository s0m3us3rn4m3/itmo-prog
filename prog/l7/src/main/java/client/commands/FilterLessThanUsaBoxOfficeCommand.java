package client.commands;

import common.request.Request;
import common.request.FilterLessThanUsaBoxOfficeRequest;

public class FilterLessThanUsaBoxOfficeCommand implements Command {
    @Override
    public Request makeRequest(String[] args) {
        if (args.length < 2) {
            System.out.println("usage: filter_less_than_usa_box_office {box}");
            return null;
        }
        double box;
        try {
            box = Double.parseDouble(args[1]);
        } catch (Exception e) {
            System.out.println("invalid box");
            return null;
        }
        return new FilterLessThanUsaBoxOfficeRequest(box);
    }
}
