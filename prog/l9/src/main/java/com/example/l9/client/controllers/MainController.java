package com.example.l9.client.controllers;

import com.example.l9.Client;
import com.example.l9.client.Executor;
import com.example.l9.client.Language;
import com.example.l9.client.LanguageManager;
import com.example.l9.client.Session;
import com.example.l9.common.collection.Movie;
import com.example.l9.common.collection.MovieGenre;
import com.example.l9.common.request.*;
import com.example.l9.common.response.Response;
import com.example.l9.common.response.ShowResponse;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.util.*;

public class MainController {
    private LanguageManager language;
    private Executor client;
    private InputController inputController;
    private final Map<String, Color> userToColor = new HashMap<>();
    private boolean isRefreshing = false;

    public void setInputController(InputController inputController) {
        this.inputController = inputController;
    }

    public void setLanguageManager(LanguageManager c) {
        language = c;
    }

    public void setClient(Executor client) {
        this.client = client;
    }

    public void switchLanguage() {
        helpButton.setText(language.getStringByKey("HelpCommand"));
        infoButton.setText(language.getStringByKey("InfoCommand"));
        insertButton.setText(language.getStringByKey("InsertCommand"));
        updateButton.setText(language.getStringByKey("UpdateCommand"));
        removeButton.setText(language.getStringByKey("RemoveCommand"));
        clearButton.setText(language.getStringByKey("ClearCommand"));
        executeScriptButton.setText(language.getStringByKey("ExecuteScriptCommand"));
        removeGreaterButton.setText(language.getStringByKey("RemoveGreaterCommand"));
        replaceIfLoweButton.setText(language.getStringByKey("ReplaceIfLoweCommand"));
        countLessThanGenreButton.setText(language.getStringByKey("CountLessThanGenreCommand"));
        filterLessThanUsaBoxOfficeButton.setText(language.getStringByKey("FilterLessThanUsaBoxOfficeCommand"));
        printDescendingButton.setText(language.getStringByKey("PrintDescendingCommand"));
        exitButton.setText(language.getStringByKey("ExitCommand"));
        logoutButton.setText(language.getStringByKey("LogoutCommand"));

        tableTab.setText(language.getStringByKey("MainTableTab"));
        visualTab.setText(language.getStringByKey("MainVisualTab"));

        userLabel.setText(language.getStringByKey("MainUserLabel") + Session.getCreds().getLogin());

        userColumn.setText(language.getStringByKey("MovieUser"));
        idColumn.setText(language.getStringByKey("MovieID"));
        keyColumn.setText(language.getStringByKey("MovieKey"));
        nameColumn.setText(language.getStringByKey("MovieName"));
        xColumn.setText(language.getStringByKey("MovieX"));
        yColumn.setText(language.getStringByKey("MovieY"));
        dateColumn.setText(language.getStringByKey("MovieDate"));
        oscarsColumn.setText(language.getStringByKey("MovieOscars"));
        usaBoxOfficeColumn.setText(language.getStringByKey("MovieUsaBoxOffice"));
        mpaaRatingColumn.setText(language.getStringByKey("MovieMpaaRating"));
        genreColumn.setText(language.getStringByKey("MovieGenre"));
        screenwriterNameColumn.setText(language.getStringByKey("MovieScreenwriterName"));
        screenwriterHeightColumn.setText(language.getStringByKey("MovieScreenwriterHeight"));
        screenwriterNationalityColumn.setText(language.getStringByKey("MovieScreenwriterNationality"));
        screenwriterLocationColumn.setText(language.getStringByKey("MovieScreenwriterLocation"));
        screenwriterXColumn.setText(language.getStringByKey("MovieScreenwriterX"));
        screenwriterYColumn.setText(language.getStringByKey("MovieScreenwriterY"));
        screenwriterZColumn.setText(language.getStringByKey("MovieScreenwriterZ"));
    }

    private void addLogText(String text) {
        if (text == null) {
            return;
        }
        String formatted = String.format("%s:\t%s\n", LocalDateTime.now(), text);
        logTextArea.appendText(formatted);
    }

    private void printResponse(Request req, Response res) {
        if (res == null) {
            addLogText(language.getStringByKey("ErrorInternalError"));
            return;
        }
        addLogText(req.getCommand()+": "+res.getMessage());
    }

    public void loadTable() {
        ShowRequest req = new ShowRequest();
        req.setCredentials(Session.getCreds());
        ShowResponse res = (ShowResponse) client.sendRequest(req);
        if (res == null) {
            addLogText(language.getStringByKey("ErrorInternalError"));
            return;
        }
        updateTable(res.getMovies());
        visualise();
    }

    private void updateTable(List<Movie> movies) {
        moviesTable.setItems(FXCollections.observableArrayList(movies));
    }

    private PathTransition makePathTransition(Node n, Path p) {
        PathTransition transition = new PathTransition();
        transition.setNode(n);
        transition.setDuration(Duration.seconds(1));
        transition.setPath(p);
        return transition;
    }

    public void visualise() {
        visualPane.getChildren().clear();
        Random random = new Random();

        for (Movie movie : moviesTable.getItems()) {
            String user = movie.getOwner();
            if (!userToColor.containsKey(user)) {
                int r = Math.abs(random.nextInt()) % 256;
                int g = Math.abs(random.nextInt()) % 256;
                int b = Math.abs(random.nextInt()) % 256;
                userToColor.put(user, Color.rgb(r, g, b));
            }
            Color color = userToColor.get(user);
            Rectangle rectangle = new Rectangle();
            rectangle.setFill(color);
            double k = Math.min(7, Math.max(1, movie.getOscarsCount()));
            rectangle.setHeight(10 * k);
            rectangle.setWidth(20 * k);
            double x = movie.getCoordinates().getX();
            x = Math.min(800, Math.max(0, x));
            double y = movie.getCoordinates().getY() * 10;
            rectangle.setX(x);
            rectangle.setY(y);

            Text key = new Text(movie.getKey());
            key.setX(x);
            key.setY(y + 5*k);
            key.setFill(Color.color(1-color.getRed(), 1-color.getGreen(), 1-color.getBlue()));
            visualPane.getChildren().add(rectangle);
            visualPane.getChildren().add(key);

            rectangle.setOnMouseClicked(event -> _update(movie));
            key.setOnMouseClicked(event -> _update(movie));

            Path recPath = new Path();
            recPath.getElements().add(new MoveTo(0, 0));
            recPath.getElements().add(new LineTo(x+10*k, y+5*k));

            PathTransition recTransition = makePathTransition(rectangle, recPath);
            PathTransition keyTransition = makePathTransition(key, recPath);

            recTransition.play();
            keyTransition.play();
        }
    }

    public void refresh() {
        if (isRefreshing) {
            return;
        }
        isRefreshing = true;
        new Thread(() -> {
            try {
                while (isRefreshing) {
                    Platform.runLater(this::loadTable);
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                System.out.println("stop refreshing");
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private Label userLabel;

    @FXML
    private Button helpButton;
    @FXML
    private Button infoButton;
    @FXML
    private Button insertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button executeScriptButton;
    @FXML
    private Button removeGreaterButton;
    @FXML
    private Button replaceIfLoweButton;
    @FXML
    private Button countLessThanGenreButton;
    @FXML
    private Button filterLessThanUsaBoxOfficeButton;
    @FXML
    private Button printDescendingButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button logoutButton;

    @FXML
    private Tab tableTab;
    @FXML
    private TableView<Movie> moviesTable;

    @FXML
    private TableColumn<Movie, String> userColumn;
    @FXML
    private TableColumn<Movie, Long> idColumn;
    @FXML
    private TableColumn<Movie, String> keyColumn;
    @FXML
    private TableColumn<Movie, String> nameColumn;
    @FXML
    private TableColumn<Movie, Integer> xColumn;
    @FXML
    private TableColumn<Movie, Double> yColumn;
    @FXML
    private TableColumn<Movie, String> dateColumn;
    @FXML
    private TableColumn<Movie, Long> oscarsColumn;
    @FXML
    private TableColumn<Movie, Double> usaBoxOfficeColumn;
    @FXML
    private TableColumn<Movie, String> genreColumn;
    @FXML
    private TableColumn<Movie, String> mpaaRatingColumn;
    @FXML
    private TableColumn<Movie, String> screenwriterNameColumn;
    @FXML
    private TableColumn<Movie, Long> screenwriterHeightColumn;
    @FXML
    private TableColumn<Movie, String> screenwriterNationalityColumn;
    @FXML
    private TableColumn<Movie, String> screenwriterLocationColumn;
    @FXML
    private TableColumn<Movie, Double> screenwriterXColumn;
    @FXML
    private TableColumn<Movie, Double> screenwriterYColumn;
    @FXML
    private TableColumn<Movie, Double> screenwriterZColumn;
    @FXML
    private Tab visualTab;
    @FXML
    private AnchorPane visualPane;
    @FXML
    private TextArea logTextArea;

    @FXML
    void initialize() {
        languageComboBox.setItems(
                FXCollections.observableArrayList(Arrays.stream(Language.values()).map(Language::toString).toList())
        );
        languageComboBox.setValue(Session.getLanguage().toString());
        languageComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            Language l = Language.getLanguage(newValue);
            language.switchLanguage(l);
            Session.setLanguage(l);
            switchLanguage();
            loadTable();
        });

        userColumn.setCellValueFactory(movie -> new SimpleStringProperty(movie.getValue().getOwner()));
        idColumn.setCellValueFactory(movie -> new SimpleLongProperty(movie.getValue().getId()).asObject());
        keyColumn.setCellValueFactory(movie -> new SimpleStringProperty(movie.getValue().getKey()));
        nameColumn.setCellValueFactory(movie -> new SimpleStringProperty(movie.getValue().getName()));
        xColumn.setCellValueFactory(movie -> new SimpleIntegerProperty(movie.getValue().getCoordinates().getX()).asObject());
        yColumn.setCellValueFactory(movie -> new SimpleDoubleProperty(movie.getValue().getCoordinates().getY()).asObject());
        dateColumn.setCellValueFactory(movie -> new SimpleStringProperty(
                language.getDateTime(movie.getValue().getCreationDate().toLocalDateTime()))
        );
        oscarsColumn.setCellValueFactory(movie -> new SimpleLongProperty(movie.getValue().getOscarsCount()).asObject());
        usaBoxOfficeColumn.setCellValueFactory(movie -> new SimpleDoubleProperty(movie.getValue().getUsaBoxOffice()).asObject());
        mpaaRatingColumn.setCellValueFactory(movie -> new SimpleStringProperty(movie.getValue().getMpaaRating().toString()));
        genreColumn.setCellValueFactory(movie -> new SimpleStringProperty(movie.getValue().getMovieGenre().toString()));

        screenwriterNameColumn.setCellValueFactory(movie ->
                movie.getValue().getScreenWriter() == null ? null
                        : new SimpleStringProperty(movie.getValue().getScreenWriter().getName())
        );
        screenwriterHeightColumn.setCellValueFactory(movie ->
                movie.getValue().getScreenWriter() == null ? null
                        : new SimpleLongProperty(movie.getValue().getScreenWriter().getHeight()).asObject()
        );
        screenwriterNationalityColumn.setCellValueFactory(movie ->
                movie.getValue().getScreenWriter() == null ? null
                        : new SimpleStringProperty(movie.getValue().getScreenWriter().getNationality().toString())
        );
        screenwriterLocationColumn.setCellValueFactory(movie ->
                movie.getValue().getScreenWriter() == null ? null
                        : new SimpleStringProperty(movie.getValue().getScreenWriter().getLocation().getName())
        );
        screenwriterXColumn.setCellValueFactory(movie ->
                movie.getValue().getScreenWriter() == null ? null
                        : new SimpleDoubleProperty(movie.getValue().getScreenWriter().getLocation().getX()).asObject()
        );
        screenwriterYColumn.setCellValueFactory(movie ->
                movie.getValue().getScreenWriter() == null ? null
                        : new SimpleDoubleProperty(movie.getValue().getScreenWriter().getLocation().getY()).asObject()
        );
        screenwriterZColumn.setCellValueFactory(movie ->
                movie.getValue().getScreenWriter() == null ? null
                        : new SimpleDoubleProperty(movie.getValue().getScreenWriter().getLocation().getZ()).asObject()
        );
    }

    @FXML
    public void help() {
        HelpRequest request = new HelpRequest();
        request.setCredentials(Session.getCreds());
        Response resp = client.sendRequest(request);
        if (resp == null) {
            addLogText(language.getStringByKey("ErrorInternalError"));
        } else {
            addLogText("\n"+resp.getMessage());
        }
    }

    @FXML
    public void info() {
        InfoRequest request = new InfoRequest();
        request.setCredentials(Session.getCreds());
        Response resp = client.sendRequest(request);
        if (resp == null) {
            addLogText(language.getStringByKey("ErrorInternalError"));
        } else {
            addLogText("\n"+resp.getMessage());
        }
    }

    @FXML
    public void insert() {
        inputController.clear();
        inputController.show();

        Movie m = inputController.getMovie();
        if (m == null) {
            return;
        }
        inputController.setMovie(null);
        InsertRequest req = new InsertRequest(m.getKey(), m);
        req.setCredentials(Session.getCreds());
        Response res = client.sendRequest(req);
        printResponse(req, res);
        loadTable();
    }

    @FXML
    public void update() {
        if (moviesTable.getSelectionModel().getSelectedItems().size() != 1) {
            addLogText(language.getStringByKey("SelectOneMovieForUpdate"));
            return;
        }
        Movie movie = moviesTable.getSelectionModel().getSelectedItem();
        _update(movie);
    }

    private void _update(Movie m) {
        inputController.clear();
        inputController.prepareForEdit(m);
        inputController.show();

        Movie newMovie = inputController.getMovie();
        if (newMovie == null) {
            return;
        }
        inputController.setMovie(null);
        UpdateRequest req = new UpdateRequest(m.getKey(), newMovie);
        req.setCredentials(Session.getCreds());
        Response res = client.sendRequest(req);
        printResponse(req, res);
        loadTable();
    }

    @FXML
    public void remove() {
        if (moviesTable.getSelectionModel().getSelectedItems().size() != 1) {
            addLogText(language.getStringByKey("SelectOneMovieForRemove"));
            return;
        }
        Movie movie = moviesTable.getSelectionModel().getSelectedItem();
        RemoveKeyRequest req = new RemoveKeyRequest(movie.getKey());
        req.setCredentials(Session.getCreds());
        Response res = client.sendRequest(req);
        printResponse(req, res);
        loadTable();
    }

    @FXML
    public void clear(ActionEvent actionEvent) {
        ClearRequest req = new ClearRequest();
        req.setCredentials(Session.getCreds());
        Response res = client.sendRequest(req);
        printResponse(req, res);
        loadTable();
    }

    @FXML
    public void executeScript() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(language.getStringByKey("ExecuteScriptCommand"));
        dialog.setHeaderText(null);
        dialog.setContentText(language.getStringByKey("EnterScriptPath"));
        Optional<String> scriptPath = dialog.showAndWait();
        if (scriptPath.isEmpty()) {
            return;
        }
        ExecuteScriptRequest req = new ExecuteScriptRequest(scriptPath.get());
        req.setCredentials(Session.getCreds());
        Response res = client.sendRequest(req);
        printResponse(req, res);
        loadTable();
    }

    @FXML
    public void removeGreater() {
        inputController.clear();
        inputController.show();
        Movie m = inputController.getMovie();
        if (m == null) {
            return;
        }
        inputController.setMovie(null);
        RemoveGreaterRequest req = new RemoveGreaterRequest(m);
        req.setCredentials(Session.getCreds());
        Response res = client.sendRequest(req);
        printResponse(req, res);
        loadTable();
    }

    @FXML
    public void replaceIfLowe() {
        inputController.clear();
        inputController.show();
        Movie m = inputController.getMovie();
        if (m == null) {
            return;
        }
        inputController.setMovie(null);
        ReplaceIfLoweRequest req = new ReplaceIfLoweRequest(m.getKey(), m);
        req.setCredentials(Session.getCreds());
        Response res = client.sendRequest(req);
        printResponse(req, res);
        loadTable();
    }

    @FXML
    public void countLessThanGenre() {
        ChoiceDialog<MovieGenre> dialog = new ChoiceDialog<MovieGenre>(MovieGenre.HORROR, MovieGenre.values());
        dialog.setTitle(language.getStringByKey("CountLessThanGenreCommand"));
        dialog.setHeaderText(null);
        dialog.setContentText(language.getStringByKey("ChooseGenre"));
        Optional<MovieGenre> genre = dialog.showAndWait();
        if (genre.isEmpty()) {
            return;
        }
        CountLessThanGenreRequest req = new CountLessThanGenreRequest(genre.get());
        req.setCredentials(Session.getCreds());
        Response res = client.sendRequest(req);
        printResponse(req, res);
    }

    @FXML
    public void filterLessThanUsaBoxOffice() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(language.getStringByKey("FilterLessThanUsaBoxOfficeCommand"));
        dialog.setHeaderText(null);
        dialog.setContentText(language.getStringByKey("EnterUsaBoxOffice"));
        Optional<String> amount = dialog.showAndWait();
        if (amount.isEmpty()) {
            return;
        }
        if (InputController.invalidDouble(amount.get())) {
            addLogText(language.getStringByKey("InvalidUsaBoxOffice"));
            return;
        }
        FilterLessThanUsaBoxOfficeRequest req = new FilterLessThanUsaBoxOfficeRequest(Double.parseDouble(amount.get()));
        req.setCredentials(Session.getCreds());
        Response res = client.sendRequest(req);
        printResponse(req, res);
    }

    @FXML
    public void printDescending() {
        PrintDescendingRequest req = new PrintDescendingRequest();
        req.setCredentials(Session.getCreds());
        Response res = client.sendRequest(req);
        printResponse(req, res);
    }

    @FXML
    public void exit() {
        Stage stage = (Stage) moviesTable.getScene().getWindow();
        stage.close();
        System.exit(0);
    }

    @FXML
    public void logout() {
        isRefreshing = false;
        Session.setCreds(null);
        Session.setLanguage(Language.RU);
        Stage stage = (Stage) moviesTable.getScene().getWindow();
        Client.setAuthScene(stage);
    }
}