package ru.artsok;


import ru.artsok.entity.Migu;

import java.util.HashMap;
import java.util.Map;

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


    public static Map<Integer, Migu> miguMap = new HashMap<>();
    public static String patchProperties = "src/ru/artsok/resources/properties/panel_size.properties";




    static {



    }

}
