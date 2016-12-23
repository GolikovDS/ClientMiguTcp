package ru.artsok.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static ru.artsok.controller.SettingMainController.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/ru/artsok/resources/properties/panel_size.properties"));
            settingPanelSize = Double.parseDouble(properties.getProperty("vertical"));
            journalPanelIsSize = Double.parseDouble(properties.getProperty("horizontal"));
            settingPanelIsClose = Boolean.parseBoolean(properties.getProperty("verticalClose"));
            journalPanelIsClose = Boolean.parseBoolean(properties.getProperty("horizontalClose"));
            btnShowMigu = Boolean.parseBoolean(properties.getProperty("btnMigu"));
            btnShowTcp = Boolean.parseBoolean(properties.getProperty("btnTcp"));
            btnShowRs = Boolean.parseBoolean(properties.getProperty("btnRs"));
            btnShowJournal = Boolean.parseBoolean(properties.getProperty("btnJournal"));

        } catch (IOException e) {
            e.printStackTrace();
        }


        Parent root = FXMLLoader.load(getClass().getResource("../view/mainForm.fxml"));
        primaryStage.setTitle("МИЖУ");
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.setMaximized(true);
        primaryStage.setOnCloseRequest(new Controller());

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
