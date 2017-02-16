package ru.artsok.entity;


import ru.artsok.SerialPort.SerialPortMigu;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "state")
@XmlType(propOrder = {"miguIsRespond", "time", "channelsStartup", "pressure", "mass", "select", "festPressureSensor",
        "secondPressureSensor", "desiredMassOfEmptyPackaging",
        "installedWeightGOTV_1_Channel", "installedWeightGOTV_2_Channel", "installedWeightGOTV_3_Channel",
        "installedWeightGOTV_4_Channel", "installedWeightGOTV_5_Channel", "installedWeightGOTV_6_Channel",
        "installedWeightGOTV_7_Channel", "installedWeightGOTV_8_Channel", "installedWeightGOTV_9_Channel",
        "installedWeightGOTV_10_Channel", "leakGOTV", "massGOTV", "nameDevice", "setWeightGOTV",
        "typeDevice", "valueFestSensor", "valueFourthSensor", "valueSecondSensor", "valueThirdSensor", "errOrEvent", "viewImages"})

public class MiguState implements SerialPortMigu.UpdateDataCallBack {
    private boolean miguIsRespond;
    private boolean isSelect;
    private String time;
    private boolean[] channelsStartup = new boolean[10];
//    Map<String, Float> value = new HashMap<>();

    private float pressure;
    private float festPressureSensor;
    private float secondPressureSensor;
    private float mass;
    private float massGOTV;
    private float desiredMassOfEmptyPackaging;
    private float setWeightGOTV;
    private float valueFestSensor;
    private float valueSecondSensor;
    private float valueThirdSensor;
    private float valueFourthSensor;
    private float installedWeightGOTV_1_Channel;
    private float installedWeightGOTV_2_Channel;
    private float installedWeightGOTV_3_Channel;
    private float installedWeightGOTV_4_Channel;
    private float installedWeightGOTV_5_Channel;
    private float installedWeightGOTV_6_Channel;
    private float installedWeightGOTV_7_Channel;
    private float installedWeightGOTV_8_Channel;
    private float installedWeightGOTV_9_Channel;
    private float installedWeightGOTV_10_Channel;
    private float leakGOTV;

    private String nameDevice;
    private String typeDevice;

    private List<String> ErrOrEvent = new ArrayList<>();
    private ViewImages viewImages = new ViewImages();

    public ViewImages getViewImages() {
        return viewImages;
    }

    @XmlElement
    public void setViewImages(ViewImages viewImages) {
        this.viewImages = viewImages;
    }

    public List<String> getErrOrEvent() {
        return ErrOrEvent;
    }

    @XmlElement
    public void setErrOrEvent(List<String> errOrEvent) {
        ErrOrEvent = errOrEvent;
    }

    @XmlElement
    public void setTypeDevice(String typeDevice) {
        this.typeDevice = typeDevice;
    }


    public void setTypeDevice(byte maxPressure, byte maxMass) {
        StringBuilder builder = new StringBuilder();

        if (maxPressure == 0)
            builder.append("2,2/");
        else
            builder.append("3,3/");

        switch (maxMass) {
            case 0:
                builder.append("3");
                break;
            case 1:
                builder.append("5");
                break;
            case 2:
                builder.append("10");
                break;
            case 3:
                builder.append("16");
                break;
            case 4:
                builder.append("25");
                break;
            case 5:
                builder.append("28");
                break;
        }
        typeDevice = builder.toString();
    }

    public String getTypeDevice() {
        return typeDevice;
    }


    public MiguState() {
//        value.put("pressure", 0.0f);
//        value.put("festPressureSensor", 0.0f);
//        value.put("secondPressureSensor", 0.0f);
//        value.put("mass", 0.0f);
//        value.put("massGOTV", 0.0f);
//        value.put("desiredMassOfEmptyPackaging", 0.0f);
//        value.put("setWeightGOTV", 0.0f);
//        value.put("valueFestSensor", 0.0f);
//        value.put("valueSecondSensor", 0.0f);
//        value.put("pressure", 0.0f);
//        value.put("pressure", 0.0f);
//        value.put("pressure", 0.0f);
//        value.put("pressure", 0.0f);
//        value.put("pressure", 0.0f);
//        value.put("pressure", 0.0f);
//        value.put("pressure", 0.0f);
//        value.put("pressure", 0.0f);
//        value.put("pressure", 0.0f);
//        value.put("pressure", 0.0f);
//        value.put("pressure", 0.0f);
//        value.put("pressure", 0.0f);
//        value.put("pressure", 0.0f);
//        value.put("pressure", 0.0f);

    }


    public float getDesiredMassOfEmptyPackaging() {
        return desiredMassOfEmptyPackaging;
    }

    @XmlElement
    public void setDesiredMassOfEmptyPackaging(float desiredMassOfEmptyPackaging) {
        this.desiredMassOfEmptyPackaging = desiredMassOfEmptyPackaging;
    }

    public float getInstalledWeightGOTV_10_Channel() {
        return installedWeightGOTV_10_Channel;
    }

    @XmlElement
    public void setInstalledWeightGOTV_10_Channel(float installedWeightGOTV_10_Channel) {
        this.installedWeightGOTV_10_Channel = installedWeightGOTV_10_Channel;
    }

    public float getInstalledWeightGOTV_1_Channel() {
        return installedWeightGOTV_1_Channel;
    }

    @XmlElement
    public void setInstalledWeightGOTV_1_Channel(float installedWeightGOTV_1_Channel) {
        this.installedWeightGOTV_1_Channel = installedWeightGOTV_1_Channel;
    }

    public float getInstalledWeightGOTV_2_Channel() {
        return installedWeightGOTV_2_Channel;
    }

    @XmlElement
    public void setInstalledWeightGOTV_2_Channel(float installedWeightGOTV_2_Channel) {
        this.installedWeightGOTV_2_Channel = installedWeightGOTV_2_Channel;
    }

    public float getInstalledWeightGOTV_3_Channel() {
        return installedWeightGOTV_3_Channel;
    }

    @XmlElement
    public void setInstalledWeightGOTV_3_Channel(float installedWeightGOTV_3_Channel) {
        this.installedWeightGOTV_3_Channel = installedWeightGOTV_3_Channel;
    }

    public float getInstalledWeightGOTV_4_Channel() {
        return installedWeightGOTV_4_Channel;
    }

    @XmlElement
    public void setInstalledWeightGOTV_4_Channel(float installedWeightGOTV_4_Channel) {
        this.installedWeightGOTV_4_Channel = installedWeightGOTV_4_Channel;
    }

    public float getInstalledWeightGOTV_5_Channel() {
        return installedWeightGOTV_5_Channel;
    }

    @XmlElement
    public void setInstalledWeightGOTV_5_Channel(float installedWeightGOTV_5_Channel) {
        this.installedWeightGOTV_5_Channel = installedWeightGOTV_5_Channel;
    }

    public float getInstalledWeightGOTV_6_Channel() {
        return installedWeightGOTV_6_Channel;
    }

    @XmlElement
    public void setInstalledWeightGOTV_6_Channel(float installedWeightGOTV_6_Channel) {
        this.installedWeightGOTV_6_Channel = installedWeightGOTV_6_Channel;
    }

    public float getInstalledWeightGOTV_7_Channel() {
        return installedWeightGOTV_7_Channel;
    }

    @XmlElement
    public void setInstalledWeightGOTV_7_Channel(float installedWeightGOTV_7_Channel) {
        this.installedWeightGOTV_7_Channel = installedWeightGOTV_7_Channel;
    }

    public float getInstalledWeightGOTV_8_Channel() {
        return installedWeightGOTV_8_Channel;
    }

    @XmlElement
    public void setInstalledWeightGOTV_8_Channel(float installedWeightGOTV_8_Channel) {
        this.installedWeightGOTV_8_Channel = installedWeightGOTV_8_Channel;
    }

    public float getInstalledWeightGOTV_9_Channel() {
        return installedWeightGOTV_9_Channel;
    }

    @XmlElement
    public void setInstalledWeightGOTV_9_Channel(float installedWeightGOTV_9_Channel) {
        this.installedWeightGOTV_9_Channel = installedWeightGOTV_9_Channel;
    }

    public float getLeakGOTV() {
        return leakGOTV;
    }

    @XmlElement
    public void setLeakGOTV(float leakGOTV) {
        this.leakGOTV = leakGOTV;
    }

    public float getMassGOTV() {
        return massGOTV;
    }

    @XmlElement
    public void setMassGOTV(float massGOTV) {
        this.massGOTV = massGOTV;
    }

    public String getNameDevice() {
        return nameDevice;
    }

    @XmlElement
    public void setNameDevice(String nameDevice) {
        this.nameDevice = nameDevice;
    }

    public void setNameDevice(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            if (bytes[i] != 0x00) {
                try {
                    sb.append(new String(new byte[]{bytes[i]}, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                break;
            }
        }
        this.nameDevice = sb.toString();
    }

    public float getSetWeightGOTV() {
        return setWeightGOTV;
    }

    @XmlElement
    public void setSetWeightGOTV(float setWeightGOTV) {
        this.setWeightGOTV = setWeightGOTV;
    }

    public float getValueFestSensor() {
        return valueFestSensor;
    }

    @XmlElement
    public void setValueFestSensor(float valueFestSensor) {
        this.valueFestSensor = valueFestSensor;
    }

    public float getValueFourthSensor() {
        return valueFourthSensor;
    }

    @XmlElement
    public void setValueFourthSensor(float valueFourthSensor) {
        this.valueFourthSensor = valueFourthSensor;
    }

    public float getValueSecondSensor() {
        return valueSecondSensor;
    }

    @XmlElement
    public void setValueSecondSensor(float valueSecondSensor) {
        this.valueSecondSensor = valueSecondSensor;
    }

    public float getValueThirdSensor() {
        return valueThirdSensor;
    }

    @XmlElement
    public void setValueThirdSensor(float valueThirdSensor) {
        this.valueThirdSensor = valueThirdSensor;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public float getFestPressureSensor() {
        return festPressureSensor;
    }

    @XmlElement
    public void setFestPressureSensor(float festPressureSensor) {
        this.festPressureSensor = festPressureSensor;
    }

    public float getSecondPressureSensor() {
        return secondPressureSensor;
    }

    @XmlElement
    public void setSecondPressureSensor(float secondPressureSensor) {
        this.secondPressureSensor = secondPressureSensor;
    }

    @XmlElement
    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public String getTime() {
        return time;
    }

    @XmlElement
    public void setTime(String time) {
        this.time = time;
    }

    public MiguState(boolean miguIsRespond) {
        this.miguIsRespond = miguIsRespond;
    }

    public boolean isMiguIsRespond() {
        return miguIsRespond;
    }

    public boolean[] getChannelsStartup() {
        return channelsStartup;
    }

    public float getPressure() {
        return pressure;
    }

    @XmlElement
    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    @XmlElement
    public void setChannelsStartup(boolean[] channelsStartup) {
        this.channelsStartup = channelsStartup;
    }

    @XmlElement
    public void setMiguIsRespond(boolean miguIsRespond) {
        this.miguIsRespond = miguIsRespond;
    }

    public float getMass() {
        return mass;
    }

    @XmlElement
    public void setMass(float mass) {
        this.mass = mass;
    }

    @Override
    public void calling(byte[] bytes, Integer numberMigu) {
        time = String.valueOf(bytes[11] << 8 | bytes[0]);
    }

    @Override
    public void callIsFreshMigu() {

    }
}
