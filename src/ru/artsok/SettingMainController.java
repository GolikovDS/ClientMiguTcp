package ru.artsok;


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


    public static String patchResource ="resources/";//for production

//    public static String patchResource ="src/ru/artsok/resources/"; //for debug in IDE
    public static String patchPropertiesPanelSize = patchResource + "properties/panel_size.properties";
    public static String patchPropertiesSerialPort = patchResource + "properties/serial_port.properties";
    public static String patchPropertiesErrorAndEvent = patchResource + "properties/error_and_event_from_migu.properties";

    public final static String patchImage = "ru/artsok/resources/images/view/";



}
