package com.example.l9;

import java.io.IOException;

import com.example.l9.client.Executor;
import com.example.l9.client.Language;
import com.example.l9.client.controllers.AuthController;
import com.example.l9.client.LanguageManager;
import com.example.l9.client.controllers.InputController;
import com.example.l9.client.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Client extends Application {
    static Executor client;
    static LanguageManager language;

    public static void main(String[] args) {
        int port;
        try {
            if (args.length < 1) {
                throw new IllegalArgumentException();
            }
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {
            System.out.println("usage: ./client {port}");
            return;
        }

        try {
            client = new Executor(port);
            language = new LanguageManager(Language.RU);
            launch();
        } catch (Exception e) {
            System.out.printf("error: %s\n", e);
            e.printStackTrace();
        }
    }

    @Override
     public void start(Stage stage) {
         setAuthScene(stage);
         stage.show();
    }

    public static void setAuthScene(Stage stage) {
        try {
            FXMLLoader authLoader = new FXMLLoader(Client.class.getResource("auth.fxml"));
            Parent authScene = authLoader.load();

            AuthController authController = authLoader.getController();
            authController.setLanguageManager(language);
            authController.switchLanguage();
            authController.setClient(client);

            stage.setScene(new Scene(authScene));
            stage.setTitle("lab8");
            stage.setResizable(false);
        } catch (IOException e) {
            System.out.printf("failed load auth scene: %s\n", e);
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void setMainScene(Stage stage) {
        try {
            FXMLLoader mainLoader = new FXMLLoader(Client.class.getResource("main.fxml"));
            Parent mainScene = mainLoader.load();

            MainController mainController = mainLoader.getController();
            mainController.setLanguageManager(language);
            mainController.setClient(client);
            mainController.setInputController(getInputController());
            mainController.switchLanguage();
            mainController.refresh();

            stage.setScene(new Scene(mainScene));
            stage.setResizable(false);
            stage.setTitle("lab8");
        } catch (IOException e) {
            System.out.printf("failed to load main scene: %s\n", e);
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static InputController getInputController() {
        try {
            FXMLLoader inputLoader = new FXMLLoader(Client.class.getResource("input.fxml"));
            Parent inputScene = inputLoader.load();

            Stage inputStage = new Stage();
            inputStage.setScene(new Scene(inputScene));
            inputStage.setResizable(false);
            inputStage.setTitle("lab8");
            inputStage.initModality(Modality.APPLICATION_MODAL);

            InputController inputController = inputLoader.getController();
            inputController.setLanguageManager(language);
            inputController.setStage(inputStage);
            return inputController;
        } catch (IOException e) {
            System.out.printf("failed to load input scene: %s\n", e);
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

}