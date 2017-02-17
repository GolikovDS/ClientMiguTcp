package ru.artsok;


import java.io.File;
import java.net.URISyntaxException;

public class SettingMainController {

    public static boolean settingPanelIsClose;
    public static boolean journalPanelIsClose;
    public static double settingPanelSize;
    public static double journalPanelIsSize;
    public static double settingPanelSizeInListener;
    public static double journalPanelIsSizeInListener;
    public static boolean btnShowMigu;
    public static boolean btnShowRs;
    public static boolean btnShowTcp;
    public static boolean btnShowJournal;


    //    public final static String patchProperties = "src/ru/artsok/resources/properties/";//for debug
    public final static String patchResource = "src/ru/artsok/resources/";
    public static String patchPropertiesPanelSize = patchResource + "properties/panel_size.properties";
    public static String patchPropertiesSerialPort = patchResource + "properties/serial_port.properties";
    public static String patchPropertiesErrorAndEvent = patchResource + "properties/error_and_event_from_migu.properties";//for build ru/artsok/resources/properties/serial_port.properties

    public final static String patchImage = "ru/artsok/resources/images/view/";
    public static File panelSizeProperty;
    public static File serialPortProperty;
    public static File errorAndEventProperty;




    static {

        try {
            panelSizeProperty = new File(Main.class.getResource("resources/properties/panel_size.properties").toURI());
            serialPortProperty = new File(Main.class.getResource("resources/properties/serial_port.properties").toURI());
            errorAndEventProperty = new File(Main.class.getResource("resources/properties/error_and_event_from_migu.properties").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
