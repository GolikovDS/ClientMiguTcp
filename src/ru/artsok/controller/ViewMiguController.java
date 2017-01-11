package ru.artsok.controller;


import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewMiguController implements Initializable {


    public Label labelPress;
    public AnchorPane anchorPanelMainView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ReadOnlyDoubleProperty readOnlyDoubleProperty = anchorPanelMainView.widthProperty();
        readOnlyDoubleProperty.addListener((observable, oldValue, newValue) -> {

            double posX = (double) newValue / 1.65;

            AnchorPane.setTopAnchor(labelPress, (posX * 0.51 - posX / 250));
            AnchorPane.setLeftAnchor(labelPress, (posX * 0.65 + posX / 250));
            labelPress.setFont(new Font(posX*0.025));
            labelPress.setText(String.valueOf(newValue));
//            anchorPaneViewMigu.setTopAnchor(labelPress, leftAnchorPanel.getHeight()*0.3);
        });


    }
}
