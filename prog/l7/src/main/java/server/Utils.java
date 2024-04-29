package server;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.TreeMap;

import common.objects.Coordinates;
import common.objects.Location;
import common.objects.Movie;
import common.objects.Person;
import server.enums.Country;
import server.enums.MovieGenre;
import server.enums.MpaaRating;

public class Utils {
    public static boolean isOwner(Connection conn, String username, String key) {
        try {
            PreparedStatement s = conn.prepareStatement(
                "SELECT username FROM movies WHERE username=? AND key=?"
            );
            s.setString(1, username);
            s.setString(2, key);
            ResultSet res = s.executeQuery();
            return res.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-224");
        byte[] messageDigest = md.digest(password.getBytes());
        return String.format("%032x", new BigInteger(1, messageDigest));
    }

    public static boolean userExists(Connection conn, String username) {
        try {
            PreparedStatement s = conn.prepareStatement("SELECT username FROM users WHERE username=?");
            s.setString(1, username);
            ResultSet res = s.executeQuery();
            return res.next();
        } catch (Exception e) {
            return false;
        }
    }

    public static Integer countUserMovies(Connection conn, String username) {
        try {
            PreparedStatement s = conn.prepareStatement("SELECT COUNT(id) FROM movies WHERE username=?");
            s.setString(1, username);
            ResultSet res = s.executeQuery();
            if (!res.next()) {
                return null;
            }
            return res.getInt(1);
        } catch (SQLException e) {
            return null;
        }
    }

    public static boolean deleteAllMoviesByUsername(Connection conn, String username) {
        try {
            PreparedStatement s;
            s = conn.prepareStatement(
                "SELECT key FROM movies WHERE username=?"
            );
            s.setString(1, username);
            ResultSet res = s.executeQuery();
            while (res.next()) {
                if (!deleteMovieFromDBByKey(conn, res.getString(1))) {
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public static boolean deleteMovieFromDBByID(Connection conn, String username, long id) {
        try {
            PreparedStatement s;
            s = conn.prepareStatement(
                "DELETE FROM screenwriters WHERE id IN "+
                "(SELECT screenwriter_id FROM screenwriter_movie WHERE movie_id IN "+
                "(SELECT id FROM movies WHERE id=? AND username=?))"
            );
            s.setLong(1, id);
            s.setString(2, username);
            s.executeUpdate();

            s = conn.prepareStatement(
                "DELETE FROM movies WHERE id=? AND username=?"
            );
            s.setLong(1, id);
            s.setString(2, username);
            s.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean deleteMovieFromDBByKey(Connection conn, String key) {
        try {
            PreparedStatement s;
            s = conn.prepareStatement(
                "DELETE FROM screenwriters WHERE id IN "+
                "(SELECT screenwriter_id FROM screenwriter_movie WHERE movie_id IN "+
                "(SELECT id FROM movies WHERE key=?))"
            );
            s.setString(1, key);
            s.executeUpdate();

            s = conn.prepareStatement(
                "DELETE FROM movies WHERE key=?"
            );
            s.setString(1, key);
            s.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private static int insertCoordsToDB(Connection conn, Coordinates c) throws SQLException {
        PreparedStatement s = conn.prepareStatement(
            "INSERT INTO coordinates (x, y) VALUES (?, ?) RETURNING id;"  
        );
        s.setInt(1, c.getX());
        s.setDouble(2, c.getY());
        ResultSet res = s.executeQuery();
        if (!res.next()) {
            throw new SQLException();
        }
        return res.getInt(1);
    }

    private static int insertLocationToDB(Connection conn, Location l) throws SQLException {
        PreparedStatement s = conn.prepareStatement(
            "INSERT INTO locations (x, y, z) VALUES (?, ?, ?) RETURNING id;"
        );
        s.setDouble(1, l.getX());
        s.setDouble(2, l.getY());
        s.setDouble(3, l.getZ());
        ResultSet res = s.executeQuery();
        if (!res.next()) {
            throw new SQLException();
        }
        return res.getInt(1);
    }

    private static int insertScreenwriterToDB(Connection conn, Person p) throws SQLException {
        int locationId = insertLocationToDB(conn, p.getLocation());

        PreparedStatement s = conn.prepareStatement(
            "INSERT INTO screenwriters (name, height, nationality, location_id) "+
            "VALUES (?, ?, ?, ?) RETURNING id;"
        );
        s.setString(1, p.getName());
        s.setLong(2, p.getHeight());
        s.setString(3, p.getNationality().toString());
        s.setInt(4, locationId);
        ResultSet res = s.executeQuery();
        if (!res.next()) {
            throw new SQLException();
        }
        return res.getInt(1);
    }

    public static Long insertMovieToDB(Connection conn, String username, String key, Movie m) {
        try {
            PreparedStatement s;
            int coordsId = insertCoordsToDB(conn, m.getCoordinates());

            s = conn.prepareStatement(
                "INSERT INTO movies (username, key, name, coords_id, creation_date,  "+
                "oscars_count, usa_box_office, genre, mpaa_rating) "+
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;"
            );
            s.setString(1, username);
            s.setString(2, key);
            s.setString(3, m.getName());
            s.setInt(4, coordsId);
            s.setTimestamp(5, Timestamp.from(m.getCreationDate().toInstant()));
            s.setLong(6, m.getOscarsCount());
            s.setDouble(7, m.getUsaBoxOffice());
            s.setString(8, m.getMovieGenre().toString());
            s.setString(9, m.getMpaaRating().toString());
 
            ResultSet res = s.executeQuery();
            if (!res.next()) return null;
            long movieId = res.getLong(1);

            if (m.getScreenWriter() != null) {
                int screenwriterId = insertScreenwriterToDB(conn, m.getScreenWriter());
                
                s = conn.prepareStatement(
                    "INSERT INTO screenwriter_movie (screenwriter_id, movie_id) VALUES (?, ?)"
                );
                s.setInt(1, screenwriterId);
                s.setLong(2, movieId);
                s.executeUpdate();
            }
            return movieId;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Coordinates getUnverifiedCoordsByID(Connection conn, int id) {
        try {
            PreparedStatement s = conn.prepareStatement("SELECT x, y FROM coordinates WHERE id=?");
            s.setInt(1, id);
            ResultSet res = s.executeQuery();
            if (!res.next()) {
                return null;
            }
            Coordinates c = new Coordinates();
            c.setX(res.getInt(1));
            c.setY(res.getDouble(2));
            return c;
        } catch (SQLException e) {
            return null;
        }
    }

    private static Location getUnverifiedLocationByID(Connection conn, int id) {
        try {
            PreparedStatement s = conn.prepareStatement("SELECT x, y, z, name FROM locations WHERE id=?");
            s.setInt(1, id);
            ResultSet res = s.executeQuery();
            if (!res.next()) {
                return null;
            }
            Location l = new Location();
            l.setX(res.getDouble(1));
            l.setY(res.getDouble(2));
            l.setZ(res.getDouble(3));
            l.setName(res.getString(4));
            return l;
        } catch (SQLException e) {
            return null;
        }
    }

    private static Person getUnverifiedScreenwriterByMovieID(Connection conn, long movieID) {
        try {
            PreparedStatement s = conn.prepareStatement(
                "SELECT name, height, nationality, location_id FROM screenwriters "+
                "WHERE id IN (SELECT screenwriter_id FROM screenwriter_movie WHERE movie_id=?);"
            );
            s.setLong(1, movieID);
            ResultSet res = s.executeQuery();
            if (!res.next()) {
                return null;
            }
            Person p = new Person();
            p.setName(res.getString(1));
            p.setHeight(res.getLong(2));
            p.setNationality(Country.valueOf(res.getString(3)));
            p.setLocation(getUnverifiedLocationByID(conn, res.getInt(4)));
            return p;
        } catch (SQLException e) {
            return null;
        }
    }

    public static Map<String, Movie> getMoviesFromDB(Connection conn) throws Exception {
        PreparedStatement s;
        s = conn.prepareStatement(
            "SELECT key, name, coords_id, creation_date, oscars_count, usa_box_office, genre, mpaa_rating, id FROM movies;"
        );
        ResultSet res = s.executeQuery();
        TreeMap<String, Movie> movies = new TreeMap<String, Movie>();
        while (res.next()) {
            Movie m = new Movie();
            String key = res.getString(1);
            if (key == null) {
                return null;
            }
            m.setName(res.getString(2));
            m.setCoordinates(getUnverifiedCoordsByID(conn, res.getInt(3)));
            m.setCreationDate(ZonedDateTime.ofInstant(res.getTimestamp(4).toInstant(), ZoneOffset.UTC));
            m.setOscarsCount(res.getLong(5));
            m.setUsaBoxOffice(res.getDouble(6));
            m.setMovieGenre(MovieGenre.valueOf(res.getString(7)));
            m.setMpaaRating(MpaaRating.valueOf(res.getString(8)));
            m.setId(res.getLong(9));
            Person screenwriter = getUnverifiedScreenwriterByMovieID(conn, m.getId());
            if (screenwriter != null) {
                m.setScreenWriter(null);
            }
            if (!m.validate()) {
                return null;
            }
            movies.put(key, m);
        }
        return movies;
    }
}
