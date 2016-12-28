package ru.artsok.controller;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.artsok.entity.Migu;
import ru.artsok.entity.MiguImpl;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class AddMiguController implements Initializable {

    public TextField addressInRS485;
    public TextField number;
    public TextField node;
    public Label errorLabel;
    public ComboBox type;
    public static boolean isOkClick = false;


    public void okDialogBtnClick(ActionEvent actionEvent) {
        try {
            MiguImpl migu = new MiguImpl(
                    new Migu(Integer.parseInt(addressInRS485.getText()),
                            Integer.parseInt(number.getText()),
                            type.getSelectionModel().getSelectedItem().toString(),
                            node.getText()));
            migu.putMigu();
            Stage stage = (Stage) node.getScene().getWindow();
            Controller.miguBuffer = migu.getMigu();
            isOkClick = true;
            stage.close();
        } catch (NumberFormatException e) {
            isOkClick = false;
            errorLabel.setVisible(true);
        }
    }

    public void cancelDialogBtnClick(ActionEvent actionEvent) {
        isOkClick = false;
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        type.setItems(FXCollections.observableArrayList(new ArrayList<String>(Arrays.asList("25/2,2", "20/2,2", "5/3,0"))));
        type.getSelectionModel().select(1);
    }
}
