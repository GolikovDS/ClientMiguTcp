package ru.artsok.controller;


import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.artsok.entity.Migu;

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
    public boolean isOkClick = false;

    public Migu getMigu() {
        return migu;
    }

    public void setMigu(Migu migu) {
        this.migu = migu;
    }

    private Migu migu;


    public void okDialogBtnClick() {
        try {
            setMigu(new Migu(Integer.parseInt(addressInRS485.getText()),
                    Integer.parseInt(number.getText()),
                    type.getSelectionModel().getSelectedItem().toString(),
                    node.getText()));
            Stage stage = (Stage) node.getScene().getWindow();
            isOkClick = true;
            stage.close();
        } catch (NumberFormatException e) {
            errorLabel.setVisible(true);
        }
    }

    public void cancelDialogBtnClick() {
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
