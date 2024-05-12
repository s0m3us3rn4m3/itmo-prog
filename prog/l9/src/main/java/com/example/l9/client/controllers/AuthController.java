package com.example.l9.client.controllers;

import com.example.l9.Client;
import com.example.l9.client.Executor;
import com.example.l9.client.Language;
import com.example.l9.client.LanguageManager;
import com.example.l9.client.Session;
import com.example.l9.common.collection.Credentials;
import com.example.l9.common.request.LoginRequest;
import com.example.l9.common.request.RegisterRequest;
import com.example.l9.common.response.Response;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Arrays;

public class AuthController {
    private Runnable runMain;
    private LanguageManager language;
    private Executor client;

    public void setRunMain(Runnable r) {
        runMain = r;
    }

    public void setLanguageManager(LanguageManager c) {
        language = c;
    }

    public void setClient(Executor client) {
        this.client = client;
    }

    public void switchLanguage() {
        titleLabel.setText(language.getStringByKey("AuthTitleLabel"));
        loginField.setPromptText(language.getStringByKey("AuthLoginField"));
        passwordField.setPromptText(language.getStringByKey("AuthPasswordField"));
        loginButton.setText(language.getStringByKey("AuthLoginButton"));
        registerButton.setText(language.getStringByKey("AuthRegisterButton"));
    }

    private void showError(String key) {
        errorLabel.setText(language.getStringByKey(key));
    }

    @FXML
    public Label titleLabel;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Label errorLabel;

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
        });
    }

    private boolean validate() {
        return !loginField.getText().isEmpty() && !passwordField.getText().isEmpty();
    }

    @FXML
    void login() {
        if (!validate()) {
            showError("ErrorEmptyLoginOrPassword");
            return;
        }
        Credentials credentials = new Credentials(loginField.getText(), passwordField.getText());
        LoginRequest req = new LoginRequest(credentials);
        Response res = client.sendRequest(req);
        if (res != null && res.getSuccess()) {
            Session.setCreds(credentials);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Client.setMainScene(stage);
        } else if (res != null && res.getMessage().contains("Неправильный логин или пароль")) {
            showError("ErrorInvalidCredentials");
        } else {
            showError("ErrorInternalError");
        }
    }

    @FXML
    void register() {
        if (!validate()) {
            showError("ErrorEmptyLoginOrPassword");
            return;
        }
        Credentials credentials = new Credentials(loginField.getText(), passwordField.getText());
        RegisterRequest req = new RegisterRequest(credentials);
        Response res = client.sendRequest(req);
        if (res != null && res.getSuccess()) {
            Session.setCreds(credentials);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Client.setMainScene(stage);
        } else if (res != null && res.getMessage().contains("уже существует")) {
            showError("ErrorUserExists");
        } else {
            showError("ErrorInternalError");
        }
    }
}
