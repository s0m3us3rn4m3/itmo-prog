package objects;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import enums.Country;
import enums.MovieGenre;
import enums.MpaaRating;

/**
 * Класс, который хранит коллекцию и выполняет команды
 */
public class Executor {
    private Map<String, Movie> collection;
    private Scanner input;
    private String file_to_save;
    private Deque<String> history;

    public Executor(Scanner input, String file_to_save) {
        this.collection = new TreeMap<String, Movie>();
        this.history = new ArrayDeque<String>();
        this.input = input;
        this.file_to_save = file_to_save;

        try {
            SAXParser p = SAXParserFactory.newInstance().newSAXParser();
            p.parse(new BufferedInputStream(new FileInputStream(file_to_save)), new XMLHandler());
        } catch (FileNotFoundException e) {
            this.collection.clear();
        } catch (Exception e) {
            System.out.printf("Не удалось загрузить файл: %s\n", e);
        }
    }

    public Executor(Scanner input, String file_to_save, Map<String, Movie> collection) {
        this.collection = collection;
        this.history = new ArrayDeque<String>();
        this.input = input;
        this.file_to_save = file_to_save;
    }

    /**
     * Считывает и выполняет команды, пока не придет команда exit или не закроется
     * поток
     * 
     * @param print_prefix нужно ли выводить '>'
     */
    public void process(boolean print_prefix) {
        if (print_prefix) {
            System.out.print("> ");
        }
        while (input.hasNextLine()) {
            String line = input.nextLine();
            int res = process_cmd(line);
            if (res != 0) {
                return;
            }
            if (print_prefix) {
                System.out.print("> ");
            }
        }
    }

    /**
     * Выполнить одну команду
     * 
     * @param line команда с аргументами
     * @return 1, если выполнилась комадна exit, иначе 0
     */
    int process_cmd(String line) {
        String[] args = line.split(" ");
        switch (args[0]) {
            case "help":
                help();
                break;
            case "info":
                info();
                break;
            case "show":
                show();
                break;
            case "insert":
                if (args.length != 2) {
                    error();
                    return 0;
                }
                insert(args[1]);
                break;
            case "update":
                if (args.length != 2) {
                    error();
                    return 0;
                }
                update(args[1]);
                break;
            case "remove_key":
                if (args.length != 2) {
                    error();
                    return 0;
                }
                remove_key(args[1]);
                break;
            case "clear":
                clear();
                break;
            case "save":
                save();
                break;
            case "execute_script":
                if (args.length != 2) {
                    error();
                    return 0;
                }
                execute_script(args[1]);
                break;
            case "exit":
                return 1;
            case "remove_greater":
                remove_greater();
                break;
            case "history":
                print_history();
                break;
            case "replace_if_lowe":
                if (args.length != 2) {
                    error();
                    return 0;
                }
                replace_if_lowe(args[1]);
                break;
            case "count_less_than_genre":
                if (args.length != 2) {
                    error();
                    return 0;
                }
                count_less_than_genre(args[1]);
                break;
            case "filter_less_than_usa_box_office":
                if (args.length != 2) {
                    error();
                    return 0;
                }
                filter_less_than_usa_box_office(args[1]);
                break;
            case "print_descending":
                print_descending();
                break;
            default:
                error();
                return 0;
        }
        history.addFirst(args[0]);
        if (history.size() > 7) {
            history.removeLast();
        }
        return 0;
    }

    void print_descending() {
        List<Movie> c = new ArrayList<Movie>(collection.values());
        Collections.sort(c, (m1, m2) -> -m1.compareTo(m2));
        for (Movie m : c) {
            System.out.println(m);
        }
    }

    void filter_less_than_usa_box_office(String box_str) {
        try {
            Double box = Double.parseDouble(box_str);
            for (Movie m : collection.values()) {
                if (m.getUsaBoxOffice() == null || m.getUsaBoxOffice() < box) {
                    System.out.println(m);
                }
            }
        } catch (Exception e) {
            System.out.println("Введен неправильный аргумент");
        }
    }

    void count_less_than_genre(String g_str) {
        try {
            MovieGenre.valueOf(g_str);
        } catch (Exception e) {
            System.out.println("Нет такого жанра");
            return;
        }
        int count = 0;
        for (Movie m : collection.values()) {
            if (m.getMovieGenre().toString().compareTo(g_str) < 0) {
                count += 1;
            }
        }
        System.out.printf("%d\n", count);
    }

    void replace_if_lowe(String key) {
        if (!collection.containsKey(key)) {
            System.out.println("Ключ не найден");
            return;
        }
        Movie new_m = Movie.read_from_scanner(input);
        Movie old_m = collection.get(key);
        if (old_m.compareTo(new_m) > 0) {
            collection.replace(key, new_m);
        }
    }

    void print_history() {
        for (String cmd : history) {
            System.out.println(cmd);
        }
    }

    void remove_greater() {
        Movie m = Movie.read_from_scanner(input);
        collection.entrySet().removeIf(entry -> entry.getValue().compareTo(m) > 0);
    }

    void execute_script(String script_path) {
        try {
            Scanner s = new Scanner(new BufferedInputStream(new FileInputStream(script_path)));
            try {
                Executor e = new Executor(s, file_to_save, collection);
                e.process(false);
                this.collection = e.collection;
            } finally {
                s.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
            return;
        }
    }

    void save() {
        try {
            FileWriter w = new FileWriter(file_to_save);
            XMLStreamWriter out = XMLOutputFactory.newFactory().createXMLStreamWriter(w);

            out.writeStartDocument();
            out.writeStartElement("root");
            for (Map.Entry<String, Movie> entry : collection.entrySet()) {
                Movie m = entry.getValue();
                out.writeStartElement("movie");
                out.writeAttribute("key", entry.getKey());
                out.writeAttribute("id", m.getId().toString());
                out.writeAttribute("name", m.getName());
                out.writeAttribute("creation_date", m.getCreationDate().toString());
                out.writeAttribute("oscars_count", m.getOscarsCount().toString());
                out.writeAttribute("usa_box_office",
                        (m.getUsaBoxOffice() == null ? "" : m.getUsaBoxOffice().toString()));
                out.writeAttribute("genre", m.getMovieGenre().toString());
                out.writeAttribute("rating", m.getMpaaRating().toString());
                Coordinates c = m.getCoordinates();
                out.writeStartElement("coordinates");
                out.writeAttribute("x", c.getX().toString());
                out.writeAttribute("y", Double.toString(c.getY()));
                out.writeEndElement();
                Person p = m.getScreenWriter();
                if (p != null) {
                    out.writeStartElement("screen_writer");
                    out.writeAttribute("name", p.getName());
                    out.writeAttribute("height", Long.toString(p.getHeight()));
                    out.writeAttribute("nationality", p.getNationality().toString());
                    Location l = p.getLocation();
                    out.writeStartElement("location");
                    out.writeAttribute("name", l.getName());
                    out.writeAttribute("x", l.getX().toString());
                    out.writeAttribute("y", l.getY().toString());
                    out.writeAttribute("z", Double.toString(l.getZ()));
                    out.writeEndElement();
                    out.writeEndElement();
                }
                out.writeEndElement();
            }
            out.writeEndElement();
            out.writeEndDocument();
            out.close();
        } catch (Exception e) {
            System.out.printf("Не удалось сохранить: %s\n", e);
        }
    }

    void clear() {
        collection.clear();
    }

    void remove_key(String key) {
        if (!collection.containsKey(key)) {
            System.out.println("Нет такого ключа");
        } else {
            collection.remove(key);
        }
    }

    void update(String key) {
        Movie movie = Movie.read_from_scanner(input);
        collection.replace(key, movie);
    }

    void insert(String key) {
        if (collection.containsKey(key)) {
            System.out.println("Такой ключ уже существует");
            return;
        }
        Movie movie = Movie.read_from_scanner(input);
        collection.put(key, movie);
    }

    void show() {
        for (Map.Entry<String, Movie> entry : collection.entrySet()) {
            Movie movie = entry.getValue();
            System.out.println(movie);
        }
    }

    void info() {
        System.out.println("Тип: TreeMap<String, Movie>\n"
                + String.format("Количество элементов: %d\n", collection.size()));
    }

    void help() {
        System.out.println("help : вывести справку по доступным командам\n"
                + "info : вывести в стандартный поток вывода информацию о коллекции\n"
                + "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n"
                + "insert key {element} : добавить новый элемент с заданным ключом\n"
                + "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n"
                + "remove_key key : удалить элемент из коллекции по его ключу\n"
                + "clear : очистить коллекцию\n"
                + "save : сохранить коллекцию в файл\n"
                + "exit : завершить программу\n"
                + "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n"
                + "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n"
                + "history : вывести последние 7 команд (без их аргументов)\n"
                + "replace_if_lowe key {element} : заменить значение по ключу, если новое значение меньше старого\n"
                + "count_less_than_genre genre : вывести количество элементов, значение поля genre которых меньше заданного\n"
                + "filter_less_than_usa_box_office usaBoxOffice : вывести элементы, значение поля usaBoxOffice которых меньше заданного\n"
                + "print_descending : вывести элементы коллекции в порядке убывания\n");
    }

    void error() {
        System.out.println("Введена некорректная команда. Для просмотра списка команд ввеедите help");
    }

    /**
     * Хендлер для считывания коллекции из xml
     */
    private class XMLHandler extends DefaultHandler {
        private String key;
        private Movie movie;
        private Person p;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {
            try {
                if (qName.equals("movie")) {
                    if (movie != null) {
                        throw new SAXException();
                    }
                    key = attributes.getValue("key");
                    if (key == null || key == "") {
                        throw new SAXException();
                    }
                    if (collection.containsKey(key)) {
                        throw new SAXException();
                    }
                    movie = new Movie();
                    movie.setId(Long.valueOf(attributes.getValue("id")));
                    movie.setName(attributes.getValue("name"));
                    movie.setCreationDate(ZonedDateTime.parse(attributes.getValue("creation_date")));
                    movie.setOscarsCount(Long.valueOf(attributes.getValue("oscars_count")));
                    if (attributes.getValue("usa_box_office") != "") {
                        movie.setUsaBoxOffice(Double.valueOf(attributes.getValue("usa_box_office")));
                    }
                    movie.setMovieGenre(MovieGenre.valueOf(attributes.getValue("genre")));
                    movie.setMpaaRating(MpaaRating.valueOf(attributes.getValue("rating")));
                    return;
                }
                if (qName.equals("coordinates")) {
                    if (movie == null) {
                        throw new SAXException();
                    }
                    Coordinates c = new Coordinates();
                    c.setX(Integer.valueOf(attributes.getValue("x")));
                    c.setY(Double.parseDouble(attributes.getValue("y")));
                    movie.setCoordinates(c);
                    return;
                }
                if (qName.equals("screen_writer")) {
                    if (movie == null || p != null) {
                        throw new SAXException();
                    }
                    p = new Person();
                    p.setName(attributes.getValue("name"));
                    p.setHeight(Long.parseLong(attributes.getValue("height")));
                    p.setNationality(Country.valueOf(attributes.getValue("nationality")));
                    return;
                }
                if (qName.equals("location")) {
                    if (p == null) {
                        throw new SAXException();
                    }
                    if (p.getLocation() != null) {
                        throw new SAXException();
                    }
                    Location l = new Location();
                    l.setName(attributes.getValue("name"));
                    l.setX(Double.valueOf(attributes.getValue("x")));
                    l.setY(Double.valueOf(attributes.getValue("y")));
                    l.setZ(Double.parseDouble(attributes.getValue("z")));
                    p.setLocation(l);
                    return;
                }
                if (qName.equals("root")) {
                    return;
                }
                throw new SAXException();
            } catch (Exception e) {
                // for (StackTraceElement s : e.getStackTrace()) {
                // System.out.println(s);
                // }
                throw new SAXException("В файле некорректные данные");
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName == "movie") {
                collection.put(key, movie);
                movie = null;
                p = null;
                key = null;
                return;
            }
            if (qName == "screen_writer") {
                movie.setScreenWriter(p);
                return;
            }
        }
    }
}
