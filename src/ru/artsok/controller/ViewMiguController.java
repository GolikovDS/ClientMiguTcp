package ru.artsok.controller;


import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewMiguController implements Initializable {


    public Label labelPress;
    public AnchorPane anchorPanelMainView;
    public HBox hboxXA1;
    public HBox hboxXA2;
    public HBox hboxEH1;
    public HBox hboxEH2;
    public Label labelMass;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        labelPress.setEffect(dropShadow);
        labelPress.setTextFill(Color.web("0x3b596d"));
        labelMass.setEffect(dropShadow);
        labelMass.setTextFill(Color.web("0x3b596d"));

        ReadOnlyDoubleProperty readOnlyDoubleProperty = anchorPanelMainView.widthProperty();
        readOnlyDoubleProperty.addListener((observable, oldValue, newValue) -> {

            double posX = (double) newValue / 1.65;

            AnchorPane.setTopAnchor(labelPress, (posX * 0.08 - posX / 250));
            AnchorPane.setLeftAnchor(labelPress, (posX * 0.5 + posX / 250));
            labelPress.setFont(Font.font(null, FontWeight.BOLD, posX * 0.030));
            labelPress.setText("Давление \n" + String.valueOf(newValue) + " МПа");

            AnchorPane.setTopAnchor(labelMass, (posX * 0.08 - posX / 250));
            AnchorPane.setLeftAnchor(labelMass, (posX * 0.95 + posX / 250));
            labelMass.setFont(Font.font(null, FontWeight.BOLD, posX * 0.030));
            labelMass.setText("Масса \n" + String.valueOf(newValue) + " кг");


        });

//        hboxXA1.setStyle("-fx-background-image: url('/ru/artsok/resources/images/main_view/XA/XA1_sleep.gif')");


    }
}
