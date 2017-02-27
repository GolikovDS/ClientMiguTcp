package ru.artsok;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ru.artsok.controller.Controller;

import java.io.FileInputStream;
import java.util.Properties;

import static ru.artsok.SettingMainController.*;

//import static ru.artsok.SettingMainController.*;

//import java.io.FileInputStream;

public class Main extends Application {

    private static Controller controller = new Controller();
    public static boolean appIsLive;

    @Override
    public void start(Stage primaryStage) throws Exception {
        appIsLive = true;
        Alert alert = new Alert(Alert.AlertType.WARNING);
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(patchPropertiesPanelSize));
            settingPanelSize = Double.parseDouble(properties.getProperty("vertical"));
            journalPanelIsSize = Double.parseDouble(properties.getProperty("horizontal"));
            settingPanelIsClose = Boolean.parseBoolean(properties.getProperty("verticalClose"));
            journalPanelIsClose = Boolean.parseBoolean(properties.getProperty("horizontalClose"));
            btnShowMigu = Boolean.parseBoolean(properties.getProperty("btnMigu"));
            btnShowTcp = Boolean.parseBoolean(properties.getProperty("btnTcp"));
            btnShowRs = Boolean.parseBoolean(properties.getProperty("btnRs"));
            btnShowJournal = Boolean.parseBoolean(properties.getProperty("btnJournal"));


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Parent root = FXMLLoader.load(getClass().getResource("view/mainForm.fxml"));
        primaryStage.setTitle("МИЖУ");
        primaryStage.setScene(new Scene(root, 1200, 600));

        primaryStage.setMaximized(true);
        primaryStage.setMinHeight(400.0);
        primaryStage.setMinWidth(500.0);
        primaryStage.setOnCloseRequest(controller);
        primaryStage.show();

    }

    @Override
    public void stop() throws Exception {
        appIsLive = false;
        System.out.println("Stop App");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
