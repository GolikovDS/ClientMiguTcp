package ru.artsok.controller;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import ru.artsok.entity.Migu;
import ru.artsok.entity.interfaces.MiguHandle;

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
    public HBox boxNoResponseSerialPort;
    public Label labelTime;
    public Label Ch1;
    public Label Ch2;
    public Label Ch3;
    public Label Ch4;
    public Label Ch5;
    public Label Ch6;
    public Label Ch7;
    public Label Ch8;
    public Label Ch9;
    public Label Ch10;
    public Label labelPressureSensor1;
    public Label labelPressureSensor2;
    public Label labelMassSensor1;
    public Label labelMassSensor2;
    public Label labelMassSensor3;
    public Label labelMassSensor4;
    public Label labelMassGotv;
    public HBox hboxZpu;
    public HBox hboxZpu_tap;
    public HBox hboxZpu_el_tap;
    public HBox hboxZpu_el_pusk;
    public HBox hboxZpu_startup;
    public HBox hboxMembrana_outlet;
    public HBox hboxMembrana;
    public HBox hboxCilinder;
    public HBox hboxManometr;
    public HBox hboxValve;
    public HBox hboxValveOutlet;
    public HBox hboxXA1_CD;
    public HBox hboxXA1_k;
    public HBox hboxXA1_PD11;
    public HBox hboxXA1_PD12;
    public HBox hboxXA2_CD;
    public HBox hboxXA2_k;
    public HBox hboxXA2_PD21;
    public HBox hboxXA2_PD22;
    public HBox hboxPower;
    public HBox hboxAkb;
    public Label labelLeak;


    private int noResponseOldTime;
    private int id;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Label[] chanel = new Label[]{Ch1, Ch2, Ch3, Ch4, Ch5, Ch6, Ch7, Ch8, Ch9, Ch10};


        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        labelPress.setEffect(dropShadow);
        labelPress.setTextFill(Color.web("0x3b596d"));
        labelMass.setEffect(dropShadow);
        labelMass.setTextFill(Color.web("0x3b596d"));
        labelLeak.setTextFill(Color.web("0xffef0f"));
        ReadOnlyDoubleProperty readOnlyDoubleProperty = anchorPanelMainView.widthProperty();
        readOnlyDoubleProperty.addListener((observable, oldValue, newValue) -> {

            double posX = (double) newValue / 1.65;

            AnchorPane.setTopAnchor(labelPress, (posX * 0.08 - posX / 250));
            AnchorPane.setLeftAnchor(labelPress, (posX * 0.5 + posX / 250));
            labelPress.setFont(Font.font(null, FontWeight.BOLD, posX * 0.030));

            AnchorPane.setTopAnchor(labelMass, (posX * 0.08 - posX / 250));
            AnchorPane.setLeftAnchor(labelMass, (posX * 0.95 + posX / 250));
            labelMass.setFont(Font.font(null, FontWeight.BOLD, posX * 0.030));

            AnchorPane.setTopAnchor(labelLeak, (posX * 0.25 - posX / 250));
            AnchorPane.setLeftAnchor(labelLeak, (posX * 0.95 + posX / 250));
            labelLeak.setFont(Font.font(null, FontWeight.BOLD, posX * 0.020));

            AnchorPane.setTopAnchor(labelTime, (posX * (-0.85) - posX / 250));
            AnchorPane.setLeftAnchor(labelTime, (posX * 1.42 + posX / 250));
            labelTime.setFont(Font.font(null, FontWeight.LIGHT, posX * 0.015));

            for (int i = 0; i < 10; i++) {
                AnchorPane.setTopAnchor(chanel[i], (posX * (-0.15 + i * 0.072) - posX / 250));
                AnchorPane.setLeftAnchor(chanel[i], (posX * 0.08 + posX / 250));
                chanel[i].setFont(Font.font(null, FontWeight.LIGHT, posX * 0.02));
            }

            AnchorPane.setTopAnchor(labelMassSensor1, (posX * (8.5 * 0.072) - posX / 250));
            AnchorPane.setLeftAnchor(labelMassSensor1, (posX * 0.44 + posX / 250));
            labelMassSensor1.setFont(Font.font(null, FontWeight.LIGHT, posX * 0.015));


            AnchorPane.setTopAnchor(labelMassSensor2, (posX * (8.5 * 0.072) - posX / 250));
            AnchorPane.setLeftAnchor(labelMassSensor2, (posX * 0.55 + posX / 250));
            labelMassSensor2.setFont(Font.font(null, FontWeight.LIGHT, posX * 0.015));

            AnchorPane.setTopAnchor(labelMassSensor3, (posX * (8.5 * 0.072) - posX / 250));
            AnchorPane.setLeftAnchor(labelMassSensor3, (posX * 1.05 + posX / 250));
            labelMassSensor3.setFont(Font.font(null, FontWeight.LIGHT, posX * 0.015));

            AnchorPane.setTopAnchor(labelMassSensor4, (posX * (8.5 * 0.072) - posX / 250));
            AnchorPane.setLeftAnchor(labelMassSensor4, (posX * 1.15 + posX / 250));
            labelMassSensor4.setFont(Font.font(null, FontWeight.LIGHT, posX * 0.015));


            AnchorPane.setTopAnchor(labelPressureSensor1, (posX * (-0.55) - posX / 250));
            AnchorPane.setLeftAnchor(labelPressureSensor1, (posX * 0.87 + posX / 250));
            labelPressureSensor1.setFont(Font.font(null, FontWeight.LIGHT, posX * 0.015));

            AnchorPane.setTopAnchor(labelPressureSensor2, (posX * (-0.52) - posX / 250));
            AnchorPane.setLeftAnchor(labelPressureSensor2, (posX * 1.0 + posX / 250));
            labelPressureSensor2.setFont(Font.font(null, FontWeight.LIGHT, posX * 0.015));

            AnchorPane.setTopAnchor(labelMassGotv, (posX * (-0.7) - posX / 250));
            AnchorPane.setLeftAnchor(labelMassGotv, (posX * 1.4 + posX / 250));
            labelMassGotv.setFont(Font.font(null, FontWeight.LIGHT, posX * 0.015));


        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            for (Migu migu : MiguHandle.miguMap.getMap().values()) {

                if (migu.getStates().isSelect()) {
                    labelPress.setText(String.format("Давление \n%.2f МПа", migu.getStates().getPressure()));
                    labelMass.setText(String.format("Масса \n%.0f кг", migu.getStates().getMassGOTV()));
                    labelLeak.setText(String.format("Утечка: %.0f кг", migu.getStates().getLeakGOTV()));
                    labelTime.setText(migu.getStates().getTime() + "\nМИЖУ " + migu.getStates().getTypeDevice() + "\nНаименование: " + migu.getStates().getNameDevice());
                    labelPressureSensor1.setText(String.format("%.2f МПа", migu.getStates().getFestPressureSensor()));
                    labelPressureSensor2.setText(String.format("%.2f МПа", migu.getStates().getSecondPressureSensor()));


                    chanel[0].setText(String.format("%.0f", migu.getStates().getInstalledWeightGOTV_1_Channel()));
                    chanel[1].setText(String.format("%.0f", migu.getStates().getInstalledWeightGOTV_2_Channel()));
                    chanel[2].setText(String.format("%.0f", migu.getStates().getInstalledWeightGOTV_3_Channel()));
                    chanel[3].setText(String.format("%.0f", migu.getStates().getInstalledWeightGOTV_4_Channel()));
                    chanel[4].setText(String.format("%.0f", migu.getStates().getInstalledWeightGOTV_5_Channel()));
                    chanel[5].setText(String.format("%.0f", migu.getStates().getInstalledWeightGOTV_6_Channel()));
                    chanel[6].setText(String.format("%.0f", migu.getStates().getInstalledWeightGOTV_7_Channel()));
                    chanel[7].setText(String.format("%.0f", migu.getStates().getInstalledWeightGOTV_8_Channel()));
                    chanel[8].setText(String.format("%.0f", migu.getStates().getInstalledWeightGOTV_9_Channel()));
                    chanel[9].setText(String.format("%.0f", migu.getStates().getInstalledWeightGOTV_10_Channel()));

                    labelMassSensor1.setText(String.format("%.0f кг", migu.getStates().getValueFestSensor()));
                    labelMassSensor2.setText(String.format("%.0f кг", migu.getStates().getValueSecondSensor()));
                    labelMassSensor3.setText(String.format("%.0f кг", migu.getStates().getValueThirdSensor()));
                    labelMassSensor4.setText(String.format("%.0f кг", migu.getStates().getValueFourthSensor()));

                    labelMassGotv.setText(String.format("Масса ГОТВ %.0f кг \nЗаданная масса резервуара %.0f кг\nЗаданная масса ГОТВ %.0f кг\nУтечка %.0f кг",
                            migu.getStates().getMass(), migu.getStates().getDesiredMassOfEmptyPackaging(), migu.getStates().getSetWeightGOTV(), migu.getStates().getLeakGOTV()));

                    if (migu.getStates().isMiguIsRespond()) {
                        migu.getAnchorPaneViewMigu().getChildren().get(7).setOpacity(0.0);
                        noResponseOldTime = 0;

                    } else {
                        noResponseOldTime++;
                        if (noResponseOldTime > 5) {
                            migu.getAnchorPaneViewMigu().getChildren().get(7).setOpacity(1.0);
                            noResponseOldTime = 5;
                        }
                    }


                    hboxCilinder.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getCylinder() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");

                    hboxManometr.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getManometer() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");


                    hboxEH1.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getEh1() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");

                    hboxEH2.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getEh2() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");

                    hboxValve.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getValve() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");

                    hboxMembrana.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getMembrane() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");

                    hboxValveOutlet.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getValveOutlet() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");

                    hboxMembrana_outlet.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getMembraneOutlet() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");

                    hboxZpu.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getZpu() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");

                    hboxZpu_el_pusk.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getZpuElPusk() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");

                    hboxZpu_el_tap.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getZpuElTap() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");

                    hboxZpu_tap.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getZpuTap() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");

                    hboxXA1.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getXa1() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");

                    hboxXA2.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getXa2() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");

                    hboxXA1_CD.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getXa1Cd() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");

                    hboxXA1_k.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getXa1k1() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");

                    hboxXA1_PD11.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getXa1Pd1() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");

                    hboxXA1_PD12.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getXa1Pd2() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");

                    hboxXA2_CD.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getXa2Cd() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");

                    hboxXA2_k.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getXa2k1() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");

                    hboxXA2_PD21.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getXa2Pd1() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");

                    hboxXA2_PD22.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getXa2Pd2() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");

                    hboxPower.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getPower() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");

                    hboxAkb.setStyle("-fx-background-image: url('" + migu.getStates().getViewImages().getAkb() + "');\n" +
                            "    -fx-background-repeat: stretch;\n" +
                            "    -fx-background-size: 100% 100%;\n" +
                            "    -fx-background-position: center center;");
                }
            }
        }

        ));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
