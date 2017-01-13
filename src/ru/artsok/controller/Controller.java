package ru.artsok.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import jssc.SerialPortException;
import ru.artsok.Main;
import ru.artsok.SerialPort.SerialPortMigu;
import ru.artsok.SerialPort.SerialPortWrapper;
import ru.artsok.entity.Migu;
import ru.artsok.entity.MiguHandleImpl;
import ru.artsok.entity.interfaces.MiguHandle;
import ru.artsok.tcp.TcpClient;
import ru.artsok.util.impl.ParserJaxbImpl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static ru.artsok.SettingMainController.*;

public class Controller implements Initializable, EventHandler<WindowEvent>, SerialPortMigu.UpdateDataCallBack {


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
    public ImageView imageCloseBtnM;
    public ImageView imageCloseBtnR;
    public ImageView imageCloseBtnT;
    public ImageView imageCloseBtnDown;
    //MIGU panel
    public Button btnAddMiguInPanel;
    public Button removeMiguFroPanel;
    public ImageView imageBtnAddMiguPanel;
    public ImageView imageBtnRemoveMiguPanel;
    public TreeView<String> treeMigu;

    //SERIAL PORT//
    public ComboBox<String> cbSeriatPortNames;
    public ComboBox<Integer> cbSeriatPortBitData;
    public ComboBox<Integer> cbSeriatPortBitInSekond;
    public ComboBox<String> cbSeriatPortChetnost;
    public ComboBox<String> cbSeriatPortStopBit;
    public TextArea serialTerminalScreen;
    ///////////////////////////////////////////////////

    public ToggleButton btnStartSerialPort;
    public CheckBox checkAvtoSerialPort;
    public ImageView imageSerialPort;
    public ImageView imageTspSetting;
    public AnchorPane leftAnchorPanel;
    public CheckBox cbAutoTcpConnect;
    public TextField tbNameTcpServer;
    public TabPane tabPanelViewMigu;
    public Label DebugLabel;


    private int btnLeftPanelIsChoice;
    private GridPane[] gridPanelsLeft;
    private static TreeItem<String> rootMigu = new TreeItem<String>("Устройства");
    private SerialPortMigu streamMigu = null;

    MiguHandle miguHandle = new MiguHandleImpl();

    AnchorPane anchorPaneViewMigu;

    /////////////////////////////////////////////////////////////
    //START
    /////////////////////////////////////////////////////////////

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ///////////////////////////////////////////////////////////////
        //
        //////////////////////////////////////////////////////////////
        gridPanelsLeft = new GridPane[]{gridPanelMigu, gridPanelRs, gridPanelTcp};

        //Делаем задержку в распределении панелий, т.к. без нее панели ставяться по умолчанию
        new Thread(() -> {
            try {
                Thread.sleep(1500);

                spGorizont.setDividerPosition(0, journalPanelIsSize);
                spTop.setDividerPosition(0, settingPanelSize);

                if (journalPanelIsClose) {
                    onClickCloseJournal(new ActionEvent());
                } else {
//                    spGorizont.setDisable(false);
                }

                if (settingPanelIsClose) {
                    onClickCloseSettingPanel(new ActionEvent());
                } else {
//                    spTop.setDisable(false);
                }

                Thread.sleep(100);
                anchorPaneViewMigu.setPrefSize(leftAnchorPanel.getWidth(), leftAnchorPanel.getHeight());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            spGorizont.setDisable(false);
            spTop.setDisable(false);
        }).start();

        /////////////////////////////////////////////////////////////////////////////////////
        //Image button*//////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////

        Image image = new Image("/ru/artsok/resources/icon/close1.png");
        imageCloseBtnM.setImage(image);
        imageCloseBtnR.setImage(image);
        imageCloseBtnT.setImage(image);
        imageCloseBtnDown.setImage(new Image("/ru/artsok/resources/icon/close2.png"));
        imageBtnAddMiguPanel.setImage(new Image("/ru/artsok/resources/icon/plus.png"));
        imageBtnRemoveMiguPanel.setImage(new Image("/ru/artsok/resources/icon/minus.png"));
        imageSerialPort.setImage(new Image("/ru/artsok/resources/icon/SerialPortSetting.png"));
        imageTspSetting.setImage(new Image("/ru/artsok/resources/icon/TCP.png"));

        //////////////////////////////////////////////////////////////////////////////////////

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

        ReadOnlyDoubleProperty readOnlyDoubleProperty = leftAnchorPanel.heightProperty();
        readOnlyDoubleProperty.addListener((observable, oldValue, newValue) -> {
            anchorPaneViewMigu.setMinSize(leftAnchorPanel.getWidth(), leftAnchorPanel.getWidth() / 1.8);
        });
        readOnlyDoubleProperty = leftAnchorPanel.widthProperty();
        readOnlyDoubleProperty.addListener((observable, oldValue, newValue) -> {
            anchorPaneViewMigu.setMinSize(leftAnchorPanel.getWidth(), leftAnchorPanel.getWidth() / 1.8);
        });


        menuRadioBtnMigu.setSelected(btnShowMigu);
        menuRadioBtnTcp.setSelected(btnShowTcp);
        menuRadioBtnRs.setSelected(btnShowRs);
        menuRadioBtnJournal.setSelected(btnShowJournal);

        new ParserJaxbImpl().getMigu();
        for (Migu migu : MiguHandle.miguMap.getMap().values()) {
            miguHandle.addMigu(rootMigu, treeMigu, migu);
            addTabMigu(migu.getNumber());
        }

        try {
            cbSeriatPortNames.setItems(FXCollections.observableArrayList(SerialPortWrapper.getPortNames()));
            cbSeriatPortBitData.setItems(FXCollections.observableArrayList(Arrays.asList(4, 5, 6, 7, 8)));
            cbSeriatPortBitInSekond.setItems(FXCollections.observableArrayList(Arrays.asList(
                    1200, 1800, 2400, 4800, 9600, 14400, 19200, 38400, 57600, 115200, 128000)));
            cbSeriatPortChetnost.setItems(FXCollections.observableArrayList(Arrays.asList("Чет", "Нечет", "Нет", "Маркер", "Пробел")));
            cbSeriatPortStopBit.setItems(FXCollections.observableArrayList(Arrays.asList("1", "1.5", "2")));


            Properties properties = new Properties();
            properties.load(new FileInputStream("src/ru/artsok/resources/properties/serial_port.properties"));
            cbSeriatPortNames.getSelectionModel().select(Integer.parseInt(properties.getProperty("port")));
            cbSeriatPortBitData.getSelectionModel().select(Integer.parseInt(properties.getProperty("dataBit")));
            cbSeriatPortBitInSekond.getSelectionModel().select(Integer.parseInt(properties.getProperty("speed")));
            cbSeriatPortChetnost.getSelectionModel().select(Integer.parseInt(properties.getProperty("parity")));
            cbSeriatPortStopBit.getSelectionModel().select(Integer.parseInt(properties.getProperty("stopBit")));

        } catch (Exception e) {
            e.printStackTrace();
        }


        ///////////////////////////////////////////////////////////////////////////
        //TIMER LIVE APP
        ///////////////////////////////////////////////////////////////////////////

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            TreeItem treeItem;

            for (int i = 0; i < MiguHandle.miguMap.getMap().size(); i++) {
                treeItem = rootMigu.getChildren().get(i);
                String str = treeItem.getValue().toString();
                Integer number = Integer.valueOf(str.substring(str.indexOf("№") + 2));
                if (MiguHandle.miguMap.getState(number).isMiguIsRespond()) {
                    ImageView imageView = new ImageView(new Image("ru/artsok/resources/icon/OK.png"));
                    imageView.setFitWidth(15);
                    imageView.setFitHeight(15);
                    treeItem.setGraphic(imageView);
                } else {
                    ImageView imageView = new ImageView(new Image("ru/artsok/resources/icon/x.png"));
                    imageView.setFitWidth(15);
                    imageView.setFitHeight(15);
                    treeItem.setGraphic(imageView);
                }
                treeMigu.refresh();
            }

        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
////////////////////////////////////////////////////////////////////////////////////



        ////////////////////////////////////////
        //TCP
        ////////////////////////////////////////

        tbNameTcpServer.setText("192.168.55.145");
        ////////////////////////////////////////
        closeLeftPanel(1);
        onChangeValueViewBtn(new ActionEvent());

    }


    /////////////////////////////////////////////////////////////
    //STOP
    /////////////////////////////////////////////////////////////
    @Override
    public void handle(WindowEvent event) {
        if (event.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
            try {
                new ParserJaxbImpl().setMigu(new Migu());
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

                properties.store(new FileOutputStream(patchProperties + "panel_size.properties"), null);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void onClickCloseSettingPanel(ActionEvent actionEvent) {
        settingPanelSize = spTop.getDividerPositions()[0];
        spTop.setMaxWidth(Double.MAX_VALUE);
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
//                spTop.setDisable(false);

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
        journalPanelIsSize = spGorizont.getDividerPositions()[0];
        journalPanelIsClose = true;
        spGorizont.setDividerPosition(0, 1);
    }

    public void onClickCloseJournalPanel(ActionEvent actionEvent) { //нижняя кнопка
        if (journalPanelIsClose) {
//            spGorizont.setDisable(false);
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
    public void onClickStartSerialPort(ActionEvent actionEvent) {
        if (btnStartSerialPort.isSelected()) {
            //Запоминаем установки/////////////////////////////////////////////
            try {
                Properties properties = new Properties();
                properties.setProperty("port", String.valueOf(cbSeriatPortNames.getSelectionModel().getSelectedIndex()));
                properties.setProperty("speed", String.valueOf(cbSeriatPortBitInSekond.getSelectionModel().getSelectedIndex()));
                properties.setProperty("stopBit", String.valueOf(cbSeriatPortStopBit.getSelectionModel().getSelectedIndex()));
                properties.setProperty("parity", String.valueOf(cbSeriatPortChetnost.getSelectionModel().getSelectedIndex()));
                properties.setProperty("dataBit", String.valueOf(cbSeriatPortBitData.getSelectionModel().getSelectedIndex()));
                properties.setProperty("autoStartPort", String.valueOf(checkAvtoSerialPort.isSelected()));

                properties.store(new FileOutputStream(patchProperties + "serial_port.properties"), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            /******************************************************************/

            checkAvtoSerialPort.setDisable(true);
            cbSeriatPortBitData.setDisable(true);
            cbSeriatPortChetnost.setDisable(true);
            cbSeriatPortStopBit.setDisable(true);
            cbSeriatPortNames.setDisable(true);
            cbSeriatPortBitInSekond.setDisable(true);

            try {
                streamMigu = SerialPortMigu.getPort(
                        cbSeriatPortNames.getSelectionModel().getSelectedItem(),
                        cbSeriatPortBitInSekond.getSelectionModel().getSelectedItem(),
                        cbSeriatPortBitData.getSelectionModel().getSelectedItem(),
                        cbSeriatPortChetnost.getSelectionModel().getSelectedItem(),
                        cbSeriatPortStopBit.getSelectionModel().getSelectedItem());
                serialTerminalScreen.appendText("Старт" + "\n");
                streamMigu.readAllRegMigu(this);
            } catch (SerialPortException e) {
                e.printStackTrace();
            }

        } else {
            checkAvtoSerialPort.setDisable(false);
            cbSeriatPortBitData.setDisable(false);
            cbSeriatPortChetnost.setDisable(false);
            cbSeriatPortStopBit.setDisable(false);
            cbSeriatPortNames.setDisable(false);
            cbSeriatPortBitInSekond.setDisable(false);
            try {
                streamMigu.closeConnection();
                serialTerminalScreen.appendText("Стоп" + "\n");
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void calling(byte[] bytes) {
        terminalInput(bytes);


    }

    @Override
    public void callIsFreshMigu() {
//        for(Migu migu: MiguHandle.miguMap.getMap().values()){
//            migu.getStates().isMiguIsRespond();
//
//            TreeItem treeItem = rootMigu.getChildren().get(1);
//            treeItem.setGraphic(new ImageView(new Image("ru/artsok/resources/icon/x.png")));
//            treeMigu.refresh();
//        }
    }

    public void terminalOutput(List<Byte> bytes) {
        StringBuilder str = new StringBuilder("<< ");
        for (byte b : bytes)
            str.append(String.format("0x%02X ", b));
        serialTerminalScreen.appendText(str + "\n");
    }

    public void terminalInput(byte[] bytes) {

        StringBuilder str = new StringBuilder(Calendar.getInstance().getTime().toString() + "\n>> ");
        for (byte b : bytes)
            str.append(String.format("0x%02X ", b));
        serialTerminalScreen.setText(str + "\n");
    }

    /**
     * ********************************************************************
     * *********************************************************************
     */
    ///////////////////////////////////////////////////////////////////////////


    /////////////////////////////////////////////////////////////////////////
    //   *****  *    *   ***
    //   *      *  *  * ** ***
    ////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////
    //**********************************************************************///
    ///////////MIGU//////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public void addMiguInPanel(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("view/addMiguDialog.fxml"));
            AnchorPane page = fxmlLoader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавить МИЖУ");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            AddMiguController miguController = fxmlLoader.getController();
            dialogStage.showAndWait();
            if (miguController.isOkClick) {
                miguHandle.addMigu(rootMigu, treeMigu, miguController.getMigu());
                addTabMigu(miguController.getMigu().getNumber());


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addTabMigu(int number){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("/ru/artsok/view/viewMigu.fxml"));
        try {
            anchorPaneViewMigu = fxmlLoader.load();
            Tab tab = new Tab("МИЖУ №" + number, anchorPaneViewMigu);
            tabPanelViewMigu.getTabs().add(tab);
            anchorPaneViewMigu.setMinSize(leftAnchorPanel.getWidth(), leftAnchorPanel.getWidth() / 1.8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeMiguInPanel(ActionEvent actionEvent) {
        TreeItem<String> item = treeMigu.getSelectionModel().getSelectedItem();
        if (item.getValue().contains("МИЖУ зав. №")) {
            TreeItem<String> parent = item.getParent();
            parent.getChildren().remove(item);
            miguHandle.removeMiguByNumberTreeView(item.getValue());
        }
    }

    /////////////////////////////////////////////////////////////////////////
    //TCP
    /////////////////////////////////////////////////////////////////////////

    public void onClickBtnStartTcpConnect(ActionEvent actionEvent) {
        new TcpClient(tbNameTcpServer.getText()).start();
    }


    //////////////////////////////////////////////////////////////////////////


}


