package ru.artsok;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.artsok.controller.Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static ru.artsok.SettingMainController.*;

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


        Parent root = FXMLLoader.load(getClass().getResource("view/mainForm.fxml"));
        primaryStage.setTitle("МИЖУ");
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.setMaximized(true);
        primaryStage.setMinHeight(400.0);
        primaryStage.setMinWidth(500.0);
        primaryStage.setOnCloseRequest(new Controller());
        primaryStage.show();

    }

    public static void showAddMiguDialog() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("view/addMiguDialog.fxml"));
            AnchorPane page = (AnchorPane) fxmlLoader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавить МИЖУ");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
