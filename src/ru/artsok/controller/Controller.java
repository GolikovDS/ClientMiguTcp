package ru.artsok.controller;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.WindowEvent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import static ru.artsok.controller.SettingMainController.*;

public class Controller implements Initializable, EventHandler<WindowEvent> {


    public SplitPane spTop;
    public AnchorPane settingPanelMigu;
    public SplitPane spGorizont;
    public AnchorPane panelJournal;
    public AnchorPane panelAll;
    public GridPane menuJournal;
    public Button btnMiguPanel;
    public Button btnRsPanel;
    public Button btnTcpPanel;
    public RadioMenuItem menuRadioBtnMigu;
    public RadioMenuItem menuRadioBtnRs;
    public RadioMenuItem menuRadioBtnTcp;
    public RadioMenuItem menuRadioBtnJournal;
    public Button btnJournalPanel;
    public GridPane gridPanelMigu;
    public GridPane gridPanelRs;
    public GridPane gridPanelTcp;


    private int btnLeftPanelIsChoice;
    private GridPane[] gridPanelsLeft;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ///////////////////////////////////////////////////////////////
        //
        //////////////////////////////////////////////////////////////
        gridPanelsLeft = new GridPane[]{gridPanelMigu, gridPanelRs, gridPanelTcp};


        new Thread(() -> {

            try {
                Thread.sleep(500);
                spGorizont.setDividerPosition(0, journalPanelIsSize);
                spTop.setDividerPosition(0, settingPanelSize);

                if (journalPanelIsClose) {
                    onClickCloseJournal(new ActionEvent());
                } else {
                    spGorizont.setDisable(false);
                }

                if (settingPanelIsClose) {
                    onClickCloseSettingPanel(new ActionEvent());
                } else {
                    spTop.setDisable(false);
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        DoubleProperty doubleProperty = spGorizont.getDividers().get(0).positionProperty();
        doubleProperty.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (!panelJournal.isDisable())
                journalPanelIsSizeInListener = (double) newValue;
        });

        doubleProperty = spTop.getDividers().get(0).positionProperty();
        doubleProperty.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (!settingPanelIsClose)
                settingPanelSizeInListener = (double) newValue;
        });

        menuRadioBtnMigu.setSelected(btnShowMigu);
        menuRadioBtnTcp.setSelected(btnShowTcp);
        menuRadioBtnRs.setSelected(btnShowRs);
        menuRadioBtnJournal.setSelected(btnShowJournal);

        closeLeftPanel(1);
        onChangeValueViewBtn(new ActionEvent());

    }

    public void onClickCloseSettingPanel(ActionEvent actionEvent) {
        settingPanelSize = spTop.getDividerPositions()[0];
        spTop.setDisable(true);
        spTop.setDividerPosition(0, 0);
        settingPanelIsClose = true;
        allBtnLeftPanelStyleClear();
    }

    ///////////////////////////////////////////////////////////
    //3 BUTTONS
    ///////////////////////////////////////////////////////////
    public void onClickBtnOpenMiguSetting(ActionEvent actionEvent) {
        closeLeftPanel(1);
    }

    public void onClickBtnOpenRSSetting(ActionEvent actionEvent) {
        closeLeftPanel(2);
    }

    public void onClickBtnOpenTcpSetting(ActionEvent actionEvent) {
        closeLeftPanel(3);
    }


    private void closeLeftPanel(int numBtn) {


        allBtnLeftPanelStyleClear();
        FXMLLoader loader = new FXMLLoader();

        switch (numBtn) {
            case 1:
                setGridPanelSetting(gridPanelMigu);
                btnMiguPanel.setStyle("-fx-background-color: rgb(200, 200, 200);");
                break;
            case 2:
                setGridPanelSetting(gridPanelRs);
                btnRsPanel.setStyle("-fx-background-color: rgb(200, 200, 200);");
                break;
            case 3:
                setGridPanelSetting(gridPanelTcp);
                btnTcpPanel.setStyle("-fx-background-color: rgb(200, 200, 200);");
                break;
            default:
                break;
        }

        if (btnLeftPanelIsChoice == numBtn) {
            if (settingPanelIsClose) {
                if (settingPanelSize > 0.003) {
                    spTop.setDividerPosition(0, settingPanelSize);
                } else {
                    spTop.setDividerPosition(0, 0.2);
                }
                spTop.setDisable(false);
                settingPanelIsClose = false;
            } else {

                onClickCloseSettingPanel(new ActionEvent());
            }
        } else {
            btnLeftPanelIsChoice = numBtn;
        }
    }


    private void setGridPanelSetting(GridPane obj) {
        for (GridPane gp : gridPanelsLeft) {
            gp.setVisible(false);
            gp.setDisable(true);
        }
        obj.setVisible(true);
        obj.setDisable(false);

    }


///////////////////////////////////////////////////////////////////////////////////////


    public void onClickCloseJournal(ActionEvent actionEvent) { //кнопка панели
        spGorizont.setDisable(true);
        journalPanelIsSize = spGorizont.getDividerPositions()[0];
        journalPanelIsClose = true;
        spGorizont.setDividerPosition(0, 1);
    }

    public void onClickCloseJournalPanel(ActionEvent actionEvent) { //нижняя кнопка
        if (journalPanelIsClose) {
            spGorizont.setDisable(false);
            journalPanelIsClose = false;
            if (journalPanelIsSize > 0.003) {
                spGorizont.setDividerPosition(0, journalPanelIsSize);
            } else {
                spGorizont.setDividerPosition(0, 0.2);
            }

        } else {
            onClickCloseJournal(new ActionEvent());
        }
    }

    /////////////////////////////////////////////////////////////////////////
    //Color button on focus mouse :hover
    /////////////////////////////////////////////////////////////////////////
    public void onHoverButtonMenuMigu(Event event) {
        if (btnLeftPanelIsChoice == 1)
            btnMiguPanel.setStyle("-fx-background-color: rgb(200, 200, 200);");
        else
            btnMiguPanel.setStyle("-fx-background-color: rgb(215, 215, 215);");
    }

    public void onHoverButtonRs(Event event) {
        if (btnLeftPanelIsChoice == 2)
            btnRsPanel.setStyle("-fx-background-color: rgb(200, 200, 200);");
        else
            btnRsPanel.setStyle("-fx-background-color: rgb(215, 215, 215);");
    }

    public void onHoverButtonTcp(Event event) {
        if (btnLeftPanelIsChoice == 3)
            btnTcpPanel.setStyle("-fx-background-color: rgb(200, 200, 200);");
        else
            btnTcpPanel.setStyle("-fx-background-color: rgb(215, 215, 215);");
    }

    public void onExitButtonMenuMigu(Event event) {
        if (btnLeftPanelIsChoice == 1)
            btnMiguPanel.setStyle("-fx-background-color: rgb(200, 200, 200);");
        else
            btnMiguPanel.setStyle("-fx-background-color: rgb(225, 225, 225);");
    }

    public void onExitButtonMenuRs(Event event) {
        if (btnLeftPanelIsChoice == 2)
            btnRsPanel.setStyle("-fx-background-color: rgb(200, 200, 200);");
        else
            btnRsPanel.setStyle("-fx-background-color: rgb(225, 225, 225);");
    }

    public void onExitButtonMenuTcp(Event event) {
        if (btnLeftPanelIsChoice == 3)
            btnTcpPanel.setStyle("-fx-background-color: rgb(200, 200, 200);");
        else
            btnTcpPanel.setStyle("-fx-background-color: rgb(225, 225, 225);");
    }

    private void allBtnLeftPanelStyleClear() {
        btnMiguPanel.setStyle("-fx-background-color: rgb(225, 225, 225);");
        btnRsPanel.setStyle("-fx-background-color: rgb(225, 225, 225);");
        btnTcpPanel.setStyle("-fx-background-color: rgb(225, 225, 225);");
    }

    @Override
    public void handle(WindowEvent event) {
        if (event.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
            try {
                Properties properties = new Properties();
                if (journalPanelIsClose) {
                    properties.setProperty("horizontal", String.valueOf(journalPanelIsSize));
                } else {
                    properties.setProperty("horizontal", String.valueOf(journalPanelIsSizeInListener));
                }
                if (settingPanelIsClose) {
                    properties.setProperty("vertical", String.valueOf(settingPanelSize));
                } else {
                    properties.setProperty("vertical", String.valueOf(settingPanelSizeInListener));
                }
                properties.setProperty("horizontalClose", String.valueOf(journalPanelIsClose));
                properties.setProperty("verticalClose", String.valueOf(settingPanelIsClose));

                properties.setProperty("btnMigu", String.valueOf(btnShowMigu));
                properties.setProperty("btnRs", String.valueOf(btnShowRs));
                properties.setProperty("btnTcp", String.valueOf(btnShowTcp));
                properties.setProperty("btnJournal", String.valueOf(btnShowJournal));

                properties.store(new FileOutputStream(patchProperties), null);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public void onChangeValueViewBtn(ActionEvent actionEvent) {
        btnShowMigu = menuRadioBtnMigu.isSelected();
        btnMiguPanel.setDisable(!btnShowMigu);
        btnMiguPanel.setVisible(btnShowMigu);

        btnShowRs = menuRadioBtnRs.isSelected();
        btnRsPanel.setDisable(!btnShowRs);
        btnRsPanel.setVisible(btnShowRs);

        btnShowTcp = menuRadioBtnTcp.isSelected();
        btnTcpPanel.setDisable(!btnShowTcp);
        btnTcpPanel.setVisible(btnShowTcp);

        btnShowJournal = menuRadioBtnJournal.isSelected();
        btnJournalPanel.setDisable(!btnShowJournal);
        btnJournalPanel.setVisible(btnShowJournal);
    }


    ///////////////////////////////////////////////////////////////////////////
    //**********************************************************************///
    ///////////RS-485//////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    /*************************************************************************/
    ///////////////////////////////////////////////////////////////////////////
    public void onClickConnect(ActionEvent actionEvent) {

    }

    /////////////////////////////////////////////////////////////////////////
    //   *****  *    *   ***
    //   *      *  *  * ** ***
    ////////////////////////////////////////////////////////////////////////



}
