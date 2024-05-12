package com.example.l9.client.controllers;

import com.example.l9.client.LanguageManager;
import com.example.l9.client.Session;
import com.example.l9.common.collection.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

public class InputController {
    private Stage stage;
    private Movie movie;
    private LanguageManager language;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setLanguageManager(LanguageManager language) {
        this.language = language;
    }

    public void switchLanguage() {
        keyLabel.setText(language.getStringByKey("MovieKey"));
        nameLabel.setText(language.getStringByKey("MovieName"));
        xLabel.setText(language.getStringByKey("MovieX"));
        yLabel.setText(language.getStringByKey("MovieY"));
        oscarsLabel.setText(language.getStringByKey("MovieOscars"));
        usaBoxOfficeLabel.setText(language.getStringByKey("MovieUsaBoxOffice"));
        mpaaRatingLabel.setText(language.getStringByKey("MovieMpaaRating"));
        genreLabel.setText(language.getStringByKey("MovieGenre"));
        screenwriterNameLabel.setText(language.getStringByKey("MovieScreenwriterName"));
        screenwriterHeightLabel.setText(language.getStringByKey("MovieScreenwriterHeight"));
        screenwriterNationalityLabel.setText(language.getStringByKey("MovieScreenwriterNationality"));
        screenwriterLocationLabel.setText(language.getStringByKey("MovieScreenwriterLocation"));
        screenwriterXLabel.setText(language.getStringByKey("MovieScreenwriterX"));
        screenwriterYLabel.setText(language.getStringByKey("MovieScreenwriterY"));
        screenwriterZLabel.setText(language.getStringByKey("MovieScreenwriterZ"));

        addScreenwriterLabel.setText(language.getStringByKey("AddScreenwriterCheckBox"));
    }

    private void showError(String s) {
        errorLabel.setText(language.getStringByKey(s));
    }

    public void clear() {
        okButton.disableProperty().setValue(false);
        keyTextField.clear();
        nameTextField.clear();
        xTextField.clear();
        yTextField.clear();
        oscarsTextField.clear();
        usaBoxOfficeTextField.clear();
        genreChoiceBox.valueProperty().setValue(null);
        mpaaRatingChoiceBox.valueProperty().setValue(null);
        addScreenwriterCheckBox.selectedProperty().setValue(false);
        screenwriterNameTextField.clear();
        screenwriterHeightTextField.clear();
        screenwriterNationalityChoiceBox.valueProperty().setValue(null);
        screenwriterLocationTextField.clear();
        screenwriterXTextField.clear();
        screenwriterYTextField.clear();
        screenwriterZTextField.clear();
        errorLabel.setText("");
    }

    public void prepareForEdit(Movie m) {
        if (!m.getOwner().equals(Session.getCreds().getLogin())) {
            okButton.disableProperty().setValue(true);
        }
        keyTextField.setText(m.getKey());
        nameTextField.setText(m.getName());
        xTextField.setText(m.getCoordinates().getX().toString());
        yTextField.setText(Double.valueOf(m.getCoordinates().getY()).toString());
        oscarsTextField.setText(m.getOscarsCount().toString());
        usaBoxOfficeTextField.setText(m.getUsaBoxOffice().toString());
        genreChoiceBox.valueProperty().setValue(m.getMovieGenre().toString());
        mpaaRatingChoiceBox.valueProperty().setValue(m.getMpaaRating().toString());
        addScreenwriterCheckBox.selectedProperty().setValue(m.getScreenWriter() != null);
        if (m.getScreenWriter() != null) {
            Person p = m.getScreenWriter();
            screenwriterNameTextField.setText(p.getName());
            screenwriterHeightTextField.setText(Long.valueOf(p.getHeight()).toString());
            screenwriterNationalityChoiceBox.valueProperty().setValue(p.getNationality().toString());
            screenwriterLocationTextField.setText(p.getLocation().getName());
            screenwriterXTextField.setText(p.getLocation().getX().toString());
            screenwriterYTextField.setText(p.getLocation().getY().toString());
            screenwriterZTextField.setText(Double.valueOf(p.getLocation().getZ()).toString());
        }
    }

    public void show() {
        if (!stage.isShowing()) {
            switchLanguage();
            stage.showAndWait();
        }
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @FXML
    private Label keyLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label xLabel;
    @FXML
    private Label yLabel;
    @FXML
    private Label oscarsLabel;
    @FXML
    private Label usaBoxOfficeLabel;
    @FXML
    private Label genreLabel;
    @FXML
    private Label mpaaRatingLabel;
    @FXML
    private Label addScreenwriterLabel;
    @FXML
    private Label screenwriterNameLabel;
    @FXML
    private Label screenwriterHeightLabel;
    @FXML
    private Label screenwriterNationalityLabel;
    @FXML
    private Label screenwriterLocationLabel;
    @FXML
    private Label screenwriterXLabel;
    @FXML
    private Label screenwriterYLabel;
    @FXML
    private Label screenwriterZLabel;

    @FXML
    private TextField keyTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField xTextField;
    @FXML
    private TextField yTextField;
    @FXML
    private TextField oscarsTextField;
    @FXML
    private TextField usaBoxOfficeTextField;
    @FXML
    private ChoiceBox<String> genreChoiceBox;
    @FXML
    private ChoiceBox<String> mpaaRatingChoiceBox;

    @FXML
    private CheckBox addScreenwriterCheckBox;

    @FXML
    private TextField screenwriterNameTextField;
    @FXML
    private TextField screenwriterHeightTextField;
    @FXML
    private ChoiceBox<String> screenwriterNationalityChoiceBox;
    @FXML
    private TextField screenwriterLocationTextField;
    @FXML
    private TextField screenwriterXTextField;
    @FXML
    private TextField screenwriterYTextField;
    @FXML
    private TextField screenwriterZTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    private boolean isValidIntOrEmpty(String s) {
        if (s.isEmpty() || s.equals("-")) {
            return true;
        }

        if (!s.matches("-?\\d+")) {
            return false;
        }

        BigInteger b = new BigInteger(s);
        BigInteger maxInt = BigInteger.valueOf(Integer.MAX_VALUE);
        BigInteger minInt = BigInteger.valueOf(Integer.MIN_VALUE);
        return 0 <= b.compareTo(minInt) && b.compareTo(maxInt) <= 0;
    }

    private boolean isValidLongOrEmpty(String s) {
        if (s.isEmpty() || s.equals("-")) {
            return true;
        }

        if (!s.matches("-?\\d+")) {
            return false;
        }

        BigInteger b = new BigInteger(s);
        BigInteger maxInt = BigInteger.valueOf(Long.MAX_VALUE);
        BigInteger minInt = BigInteger.valueOf(Long.MIN_VALUE);
        return 0 <= b.compareTo(minInt) && b.compareTo(maxInt) <= 0;
    }

    private boolean isValidDoubleOrEmpty(String s) {
        if (s.isEmpty() || s.equals("-")) {
            return true;
        }
        if (!s.matches("-?\\d+\\.?\\d*")) {
            return false;
        }
        if (s.endsWith(".")) {
            return true;
        }
        BigDecimal d = new BigDecimal(s);
        BigDecimal maxDouble = BigDecimal.valueOf(Double.MAX_VALUE);
        BigDecimal minDouble = BigDecimal.valueOf(Double.MIN_VALUE);
        return 0 <= d.abs().compareTo(minDouble) && d.abs().compareTo(maxDouble) <= 0
                || d.compareTo(BigDecimal.ZERO) == 0;
    }

    public static boolean invalidDouble(String s) {
        try {
            Double.parseDouble(s);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    @FXML
    void initialize() {
        genreChoiceBox.setItems(FXCollections.observableArrayList(
                Arrays.stream(MovieGenre.values()).map(Enum::toString).toList()
        ));
        mpaaRatingChoiceBox.setItems(FXCollections.observableArrayList(
                Arrays.stream(MpaaRating.values()).map(Enum::toString).toList()
        ));
        screenwriterNationalityChoiceBox.setItems(FXCollections.observableArrayList(
                Arrays.stream(Country.values()).map(Enum::toString).toList()
        ));

        Arrays.asList(screenwriterNameTextField, screenwriterHeightTextField, screenwriterNationalityChoiceBox,
                        screenwriterLocationTextField, screenwriterXTextField, screenwriterYTextField, screenwriterZTextField)
                .forEach(field -> field.disableProperty().bind(addScreenwriterCheckBox.selectedProperty().not())
        );

        xTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!isValidIntOrEmpty(newValue)) {
                xTextField.setText(oldValue);
            }
        });

        Arrays.asList(oscarsTextField, screenwriterHeightTextField).forEach(
                field -> field.textProperty().addListener((observableValue, oldValue, newValue) -> {
                    if (!isValidLongOrEmpty(newValue)) {
                        field.setText(oldValue);
                    }
                    if (newValue.contains("-")) {
                        field.setText(oldValue);
                    }
                })
        );

        usaBoxOfficeTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!isValidDoubleOrEmpty(newValue)) {
                usaBoxOfficeTextField.setText(oldValue);
            }
            if (newValue.contains("-")) {
                usaBoxOfficeTextField.setText(oldValue);
            }
        });

        Arrays.asList(yTextField, screenwriterXTextField, screenwriterYTextField, screenwriterZTextField)
                .forEach(field -> field.textProperty().addListener((observableValue, oldValue, newValue) -> {
                    if (!isValidDoubleOrEmpty(newValue)) {
                        field.setText(oldValue);
                    }
                }));
    }

    @FXML
    public void ok() {
        if (keyTextField.getText().isEmpty()) {
            showError("EmptyKey");
            return;
        }
        if (nameTextField.getText().isEmpty()) {
            showError("EmptyName");
            return;
        }
        if (xTextField.getText().isEmpty()) {
            showError("InvalidX");
            return;
        }
        try {
            double y = Double.parseDouble(yTextField.getText());
            if (y < 0 || y > 37) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showError("InvalidY");
            return;
        }
        if (oscarsTextField.getText().isEmpty()) {
            showError("InvalidOscars");
            return;
        }
        if (invalidDouble(usaBoxOfficeTextField.getText())) {
            showError("InvalidUsaBoxOffice");
            return;
        }
        if (genreChoiceBox.getValue() == null) {
            showError("InvalidGenre");
            return;
        }
        if (mpaaRatingChoiceBox.getValue() == null) {
            showError("InvalidMpaa");
            return;
        }

        if (addScreenwriterCheckBox.isSelected()) {
            if (screenwriterNameTextField.getText().isEmpty()) {
                showError("EmptyScreenwriterName");
                return;
            }
            if (screenwriterHeightTextField.getText().isEmpty()) {
                showError("InvalidScreenwriterHeight");
                return;
            }
            if (screenwriterNationalityChoiceBox.getValue() == null) {
                showError("InvalidScreenwriterNationality");
                return;
            }
            if (screenwriterLocationTextField.getText().isEmpty()) {
                showError("InvalidScreenwriterLocation");
                return;
            }
            if (invalidDouble(screenwriterXTextField.getText())) {
                showError("InvalidScreenwriterX");
                return;
            }
            if (invalidDouble(screenwriterYTextField.getText())) {
                showError("InvalidScreenwriterY");
                return;
            }
            if (invalidDouble(screenwriterZTextField.getText())) {
                showError("InvalidScreenwriterZ");
                return;
            }
        }

        Movie m = new Movie();
        m.setKey(keyTextField.getText());
        m.setName(nameTextField.getText());
        Coordinates c = new Coordinates();
        c.setX(Integer.parseInt(xTextField.getText()));
        c.setY(Double.parseDouble(yTextField.getText()));
        m.setCoordinates(c);
        m.setOscarsCount(Long.parseLong(oscarsTextField.getText()));
        m.setUsaBoxOffice(Double.parseDouble(usaBoxOfficeTextField.getText()));
        m.setMovieGenre(MovieGenre.valueOf(genreChoiceBox.getValue()));
        m.setMpaaRating(MpaaRating.valueOf(mpaaRatingChoiceBox.getValue()));
        if (addScreenwriterCheckBox.isSelected()) {
            Person p = new Person();
            p.setName(screenwriterNameTextField.getText());
            p.setHeight(Long.parseLong(screenwriterHeightTextField.getText()));
            p.setNationality(Country.valueOf(screenwriterNationalityChoiceBox.getValue()));
            Location l = new Location();
            l.setName(screenwriterLocationTextField.getText());
            l.setX(Double.parseDouble(screenwriterXTextField.getText()));
            l.setY(Double.parseDouble(screenwriterYTextField.getText()));
            l.setZ(Double.parseDouble(screenwriterZTextField.getText()));
            p.setLocation(l);
            m.setScreenWriter(p);
        }

        movie = m;
        stage.close();
    }

    @FXML
    public void cancel() {
        stage.close();
    }
}
