package ru.artsok.entity;


import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import ru.artsok.entity.interfaces.MiguHandle;
import ru.artsok.util.Caster;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static ru.artsok.SettingMainController.patchImage;
import static ru.artsok.SettingMainController.patchProperties;

public class MiguHandleImpl implements MiguHandle {
    class ErrorAndEventsImage {
        private List<String> ErrOrEvent = new ArrayList<>();
        private ViewImages ErrImage = new ViewImages();

        public ErrorAndEventsImage(ViewImages errImage, List<String> errOrEvent) {
            ErrImage = errImage;
            ErrOrEvent = errOrEvent;
        }

        public void setErrImage(ViewImages errImage) {
            ErrImage = errImage;
        }

        public List<String> getErrOrEvent() {
            return ErrOrEvent;
        }

        public void setErrOrEvent(List<String> errOrEvent) {
            ErrOrEvent = errOrEvent;
        }

        public ViewImages getErrImage() {
            return ErrImage;
        }
    }

    @Override
    public void view() {
    }

    @Override
    public void addMigu(TreeItem<String> rootMigu, TreeView<String> treeMigu, Migu migu) {
        migu.getStates().setMiguIsRespond(false);
        miguMap.getMap().put(migu.getNumber(), migu);
        rootMigu.getChildren().add(migu.getTreeItem());
        treeMigu.setRoot(rootMigu);
        rootMigu.setExpanded(true);
        miguMap.getMap().put(migu.getNumber(), migu);
    }


    public Migu removeMiguByNumberTreeView(String number) {
        Migu migu = miguMap.getMap().get(Integer.valueOf(number.substring(12)));
        miguMap.getMap().remove(Integer.valueOf(number.substring(12)));
        return migu;
    }

    public void dataProcessing(byte[] bytes, Integer numberMigu) {
        Caster caster = new Caster();

        //Time
        long aLong = 1000 * caster.bytesToLong(new byte[]{bytes[32], bytes[33], bytes[34], bytes[35]});
        Date date = new Date(aLong);
        MiguState state = miguMap.getMap().get(numberMigu).getStates();
        state.setTime(date.toString());

        //Startup
        boolean[] res = new boolean[10];
        int over = 0x01;
        for (int i = 0; i < res.length; i++) {
            res[i] = ((int) ((caster.byteToLong(bytes[37]) << 8) | caster.byteToLong(bytes[36])) & over) == over;
            over <<= 1;
        }
        state.setChannelsStartup(res);

        //Pressure
        state.setPressure(getValueFloat(0x14, bytes));
        state.setFestPressureSensor(getValueFloat(0x16, bytes));
        state.setSecondPressureSensor(getValueFloat(0x18, bytes));
        //Mass
        state.setMass(getValueFloat(0x1A, bytes));
        state.setMassGOTV(getValueFloat(0x1C, bytes));
        state.setDesiredMassOfEmptyPackaging(getValueFloat(0x1E, bytes));
        state.setSetWeightGOTV(getValueFloat(0x20, bytes));
        state.setValueFestSensor(getValueFloat(0x22, bytes));
        state.setValueSecondSensor(getValueFloat(0x24, bytes));
        state.setValueThirdSensor(getValueFloat(0x26, bytes));
        state.setValueFourthSensor(getValueFloat(0x28, bytes));
        state.setInstalledWeightGOTV_1_Channel(getValueFloat(0x2A, bytes));
        state.setInstalledWeightGOTV_2_Channel(getValueFloat(0x2C, bytes));
        state.setInstalledWeightGOTV_3_Channel(getValueFloat(0x2E, bytes));
        state.setInstalledWeightGOTV_4_Channel(getValueFloat(0x30, bytes));
        state.setInstalledWeightGOTV_5_Channel(getValueFloat(0x32, bytes));
        state.setInstalledWeightGOTV_6_Channel(getValueFloat(0x34, bytes));
        state.setInstalledWeightGOTV_7_Channel(getValueFloat(0x36, bytes));
        state.setInstalledWeightGOTV_8_Channel(getValueFloat(0x38, bytes));
        state.setInstalledWeightGOTV_9_Channel(getValueFloat(0x3A, bytes));
        state.setInstalledWeightGOTV_10_Channel(getValueFloat(0x3C, bytes));
        state.setLeakGOTV(getValueFloat(0x3E, bytes));

        state.setNameDevice(new byte[]{bytes[0x88], bytes[0x89], bytes[0x8A], bytes[0x8B], bytes[0x8C], bytes[0x8D], bytes[0x8E], bytes[0x8F]});

        state.setTypeDevice(bytes[0x90], bytes[0x91]);

        ErrorAndEventsImage errOrEvent = getErrOrEvent(bytes);
        state.setErrOrEvent(errOrEvent.getErrOrEvent());
        state.setViewImages(errOrEvent.getErrImage());

    }

    private float getValueFloat(int numberStartByte, byte[] bytes) {
        numberStartByte *= 2;
        return new Caster().bytesToFloat(new byte[]{bytes[numberStartByte], bytes[numberStartByte + 1],
                bytes[numberStartByte + 2], bytes[numberStartByte + 3]});
    }


    public ErrorAndEventsImage getErrOrEvent(byte[] bytes) {
        Properties properties1 = new Properties();
        try {
            properties1.load(new FileInputStream(patchProperties + "error_and_event_from_migu.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Integer> lists = getErrOrEvent0(bytes);
        List<String> stringList = new ArrayList<>();
        stringList.addAll(lists.stream().map(integer -> String.format("E%03d \t", integer) + properties1.getProperty(integer.toString())).collect(Collectors.toList()));

        return new ErrorAndEventsImage(getErrOrEventImage(lists), stringList);
    }

    public ViewImages getErrOrEventImage(List<Integer> list) {
        ViewImages result = new ViewImages();
        if (list.contains(22) || list.contains(21)) {
            result.setCylinder(patchImage + "cylinder/cilinder_err.gif");
        } else {
            result.setCylinder(patchImage + "cylinder/cilinder.gif");
        }

        if (list.contains(20)) {
            result.setManometer(patchImage + "manometer/manometr_error.gif");
        } else {
            result.setManometer(patchImage + "manometer/manometr.gif");
        }

        if (list.contains(111)) {
            result.setEh1(patchImage + "EH/1/EH1_err.gif");
        } else if (list.contains(111)) {
            result.setEh1(patchImage + "EH/1/EH1_work.gif");
        } else {
            result.setEh1(patchImage + "EH/1/EH1.gif");
        }
        if (list.contains(112)) {
            result.setEh2(patchImage + "EH/2/EH2_err.gif");
        } else if (list.contains(112)) {
            result.setEh2(patchImage + "EH/2/EH2_work.gif");
        } else {
            result.setEh2(patchImage + "EH/2/EH2.gif");
        }

        if (list.contains(122)) {
            result.setValve(patchImage + "valve/valve_error.gif");
        } else {
            result.setValve(patchImage + "valve/valve.gif");
        }

        if (list.contains(123)) {
            result.setValveOutlet(patchImage + "valve/valve_outlet.gif");
        } else {
            result.setValveOutlet("");
        }

        if (list.contains(121)) {
            result.setMembrane(patchImage + "мembrane/мembrane_error.gif");
        } else {
            result.setMembrane(patchImage + "мembrane/мembrane.gif");
        }
        if (list.contains(124)) {
            result.setMembraneOutlet(patchImage + "мembrane/membrane_outlet.gif");
        } else {
            result.setMembraneOutlet("");
        }

        if (list.contains(135) && list.contains(137)) {
            result.setZpu(patchImage + "zpu/zpu_starup.gif");
        } else {
            result.setZpu(patchImage + "zpu/zpu.gif");
        }

        if (list.contains(11) || list.contains(12) || list.contains(13) || list.contains(14) || list.contains(15) || list.contains(24) || list.contains(23)) {
            result.setZpuElPusk(patchImage + "zpu/zpu_el_pusk/zpu_el_pusk_error.gif");
        } else {
            result.setZpuElPusk(patchImage + "zpu/zpu_el_pusk/zpu_el_pusk.gif");
        }

        if (list.contains(13) || list.contains(14) || list.contains(15) || list.contains(24) || list.contains(23)) {
            result.setZpuElTap(patchImage + "zpu/zpu_el_tap/zpu_el_tap_error.gif");
        } else {
            result.setZpuElTap(patchImage + "zpu/zpu_el_tap/zpu_el_tap.gif");
        }

        if (list.contains(17) || list.contains(18) || list.contains(19)) {
            result.setZpuTap(patchImage + "zpu/zpu_tap/zpu_tap_error.gif");
        } else {
            result.setZpuTap(patchImage + "zpu/zpu_tap/zpu_tap.gif");
        }


        if (list.contains(81) || list.contains(82) || list.contains(83) || list.contains(84) || list.contains(86) || list.contains(87) || list.contains(88)) {
            result.setXa1(patchImage + "XA/1/XA1_err.gif");
        } else if (list.contains(111)) {
            result.setXa1(patchImage + "XA/1/XA1_work.gif");
        } else {
            result.setXa1(patchImage + "XA/1/XA1.gif");
        }

        if (list.contains(91) || list.contains(92) || list.contains(93) || list.contains(94) || list.contains(96) || list.contains(97) || list.contains(98)) {
            result.setXa2(patchImage + "XA/2/XA2_err.gif");
        } else if (list.contains(111)) {
            result.setXa2(patchImage + "XA/2/XA2_work.gif");
        } else {
            result.setXa2(patchImage + "XA/2/XA2.gif");
        }

        if (list.contains(86) || list.contains(87)) {
            result.setXa1Cd(patchImage + "XA/1/cb/XA1_CD_error.gif");
        } else {
            result.setXa1Cd(patchImage + "XA/1/cb/XA1_CD.gif");
        }

        if (list.contains(83) || list.contains(84)) {
            result.setXa1k1(patchImage + "XA/1/k1/XA1_k_error.gif");
        } else {
            result.setXa1k1(patchImage + "XA/1/k1/XA1_k.gif");
        }

        if (list.contains(81)) {
            result.setXa1Pd1(patchImage + "XA/1/PD11/XA1_PD11_error.gif");
        } else {
            result.setXa1Pd1(patchImage + "XA/1/PD11/XA1_PD11.gif");
        }

        if (list.contains(82)) {
            result.setXa1Pd2(patchImage + "XA/1/PD12/XA1_PD12_error.gif");
        } else {
            result.setXa1Pd2(patchImage + "XA/1/PD12/XA1_PD12.gif");
        }

        if (list.contains(96) || list.contains(97)) {
            result.setXa2Cd(patchImage + "XA/2/cb2/XA2_CD_error.gif");
        } else {
            result.setXa2Cd(patchImage + "XA/2/cb2/XA2_CD.gif");
        }

        if (list.contains(93) || list.contains(84)) {
            result.setXa2k1(patchImage + "XA/2/k2/XA2_K1_error.gif");
        } else {
            result.setXa2k1(patchImage + "XA/2/k2/XA2_K1.gif");
        }

        if (list.contains(91)) {
            result.setXa2Pd1(patchImage + "XA/2/PD21/XA2_PD21_error.gif");
        } else {
            result.setXa2Pd1(patchImage + "XA/2/PD21/XA2_PD21.gif");
        }

        if (list.contains(92)) {
            result.setXa2Pd2(patchImage + "XA/2/PD22/XA2_PD22_error.gif");
        } else {
            result.setXa2Pd2(patchImage + "XA/2/PD22/XA2_PD22.gif");
        }

        if (list.contains(1)) {
            result.setPower(patchImage + "power/power_error.gif");
        } else {
            result.setPower(patchImage + "power/power.gif");
        }
        if (list.contains(3)) {
            result.setAkb(patchImage + "power/akb_error.gif");
        } else {
            result.setAkb(patchImage + "power/akb.gif");
        }


        return result;
    }

    private List<Integer> getErrOrEvent0(byte[] bytes) {
        List<Integer> result = new ArrayList<>();
        boolean[] flag;
        for (int i = 0; i < 32; i++) {
            flag = getFlags(bytes[i]);
            for (int j = 0; j < 8; j++) {
                if (flag[j]) {
                    result.add(i * 8 + j);
                }
            }
        }
        return result;
    }

    private boolean[] getFlags(byte b) {
        boolean[] result = new boolean[8];
        for (int i = 0; i < 8; i++) {
            result[i] = ((b >>> i) & 0x01) == 1;
        }
        return result;
    }

}
