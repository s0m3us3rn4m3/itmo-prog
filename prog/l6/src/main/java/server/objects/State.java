package server.objects;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import common.objects.Coordinates;
import common.objects.Location;
import common.objects.Movie;
import common.objects.Person;
import server.enums.Country;
import server.enums.MovieGenre;
import server.enums.MpaaRating;

public class State {
    private Scanner input;
    private Map<String, Movie> collection;
    private String fileToSave;
    private Deque<String> history;
    private Set<String> executeScriptStack;

    public State() {
        collection = new TreeMap<String, Movie>();
        history = new ArrayDeque<String>();
        executeScriptStack = new HashSet<String>();
    }

    public void setInput(Scanner input) {
        this.input = input;
    }

    public Scanner getInput() {
        return input;
    }

    public void setFileToSave(String filename) {
        this.fileToSave = filename;
    }

    public String getFileToSave() {
        return fileToSave;
    }

    public void setCollection(Map<String, Movie> collection) {
        this.collection = collection;
    }

    public Map<String, Movie> getCollection() {
        return collection;
    }

    public void setExecuteScriptStack(Set<String> stack) {
        this.executeScriptStack = stack;
    }

    public Set<String> getExecuteScriptStack() {
        return executeScriptStack;
    }

    public void setHistory(Deque<String> history) {
        this.history = history;
    }

    public Deque<String> getHistory() {
        return history;
    }

    public void parseFileToSave() throws Exception {
        try {
        SAXParser p = SAXParserFactory.newInstance().newSAXParser();
        p.parse(new BufferedInputStream(new FileInputStream(fileToSave)), new XMLHandler());
        } catch (FileNotFoundException e) {
            collection.clear();
        }
    }

    public void saveToFile() throws Exception {
        FileWriter w = new FileWriter(fileToSave);
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
    }

    /**
     * Хендлер для считывания коллекции из xml
     */
    public class XMLHandler extends DefaultHandler {
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
