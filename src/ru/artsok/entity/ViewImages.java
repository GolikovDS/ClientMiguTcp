package ru.artsok.entity;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "image")
@XmlType(propOrder = {"cylinder", "manometer", "eh1", "eh2", "membrane", "valve", "membraneOutlet", "valveOutlet", "zpu",
        "zpuElPusk", "zpuElTap", "zpuTap", "xa1", "xa2", "xa1Cd", "xa1k1", "xa1Pd1", "xa1Pd2", "xa2Cd", "xa2Pd2",
        "xa2Pd1", "xa2k1", "akb", "power"})
public class ViewImages {

    private String cylinder;
    private String manometer;
    private String eh1;
    private String eh2;
    private String valve;
    private String membrane;
    private String valveOutlet;
    private String membraneOutlet;
    private String zpu;
    private String zpuElPusk;
    private String zpuElTap;
    private String zpuTap;
    private String xa1;
    private String xa2;

    private String xa1Cd;
    private String xa1k1;
    private String xa1Pd1;
    private String xa1Pd2;
    private String xa2Cd;
    private String xa2k1;
    private String xa2Pd1;
    private String xa2Pd2;

    private String power;
    private String akb;


    public String getAkb() {
        return akb;
    }

    @XmlElement
    public void setAkb(String akb) {
        this.akb = akb;
    }

    public String getPower() {
        return power;
    }

    @XmlElement
    public void setPower(String power) {
        this.power = power;
    }

    public String getXa2k1() {
        return xa2k1;
    }

    @XmlElement
    public void setXa2k1(String xa2k1) {
        this.xa2k1 = xa2k1;
    }

    public String getXa2Pd1() {
        return xa2Pd1;
    }

    @XmlElement
    public void setXa2Pd1(String xa2Pd1) {
        this.xa2Pd1 = xa2Pd1;
    }

    public String getXa2Pd2() {
        return xa2Pd2;
    }

    @XmlElement
    public void setXa2Pd2(String xa2Pd2) {
        this.xa2Pd2 = xa2Pd2;
    }

    public String getXa2Cd() {
        return xa2Cd;
    }

    @XmlElement
    public void setXa2Cd(String xa2Cd) {
        this.xa2Cd = xa2Cd;
    }

    public String getXa1Cd() {
        return xa1Cd;
    }

    @XmlElement
    public void setXa1Cd(String xa1Cd) {
        this.xa1Cd = xa1Cd;
    }

    public String getXa1k1() {
        return xa1k1;
    }

    @XmlElement
    public void setXa1k1(String xa1k1) {
        this.xa1k1 = xa1k1;
    }

    public String getXa1Pd1() {
        return xa1Pd1;
    }

    @XmlElement
    public void setXa1Pd1(String xa1Pd1) {
        this.xa1Pd1 = xa1Pd1;
    }

    public String getXa1Pd2() {
        return xa1Pd2;
    }

    @XmlElement
    public void setXa1Pd2(String xa1Pd2) {
        this.xa1Pd2 = xa1Pd2;
    }


    public String getXa1() {
        return xa1;
    }

    @XmlElement
    public void setXa1(String xa1) {
        this.xa1 = xa1;
    }

    public String getXa2() {
        return xa2;
    }

    @XmlElement
    public void setXa2(String xa2) {
        this.xa2 = xa2;
    }

    public String getZpuElPusk() {
        return zpuElPusk;
    }

    @XmlElement
    public void setZpuElPusk(String zpuElPusk) {
        this.zpuElPusk = zpuElPusk;
    }

    public String getZpuElTap() {
        return zpuElTap;
    }

    @XmlElement
    public void setZpuElTap(String zpuElTap) {
        this.zpuElTap = zpuElTap;
    }

    public String getZpuTap() {
        return zpuTap;
    }

    @XmlElement
    public void setZpuTap(String zpuTap) {
        this.zpuTap = zpuTap;
    }

    public String getZpu() {
        return zpu;
    }

    @XmlElement
    public void setZpu(String zpu) {
        this.zpu = zpu;
    }

    public String getMembraneOutlet() {
        return membraneOutlet;
    }

    @XmlElement
    public void setMembraneOutlet(String membraneOutlet) {
        this.membraneOutlet = membraneOutlet;
    }

    public String getValveOutlet() {
        return valveOutlet;
    }

    @XmlElement
    public void setValveOutlet(String valveOutlet) {
        this.valveOutlet = valveOutlet;
    }

    public String getMembrane() {
        return membrane;
    }

    @XmlElement
    public void setMembrane(String membrane) {
        this.membrane = membrane;
    }

    public String getValve() {
        return valve;
    }

    @XmlElement
    public void setValve(String valve) {
        this.valve = valve;
    }

    public String getEh1() {
        return eh1;
    }

    @XmlElement
    public void setEh1(String eh1) {
        this.eh1 = eh1;
    }

    public String getEh2() {
        return eh2;
    }

    @XmlElement
    public void setEh2(String eh2) {
        this.eh2 = eh2;
    }

    public String getCylinder() {
        return cylinder;
    }

    @XmlElement
    public void setCylinder(String cylinder) {
        this.cylinder = cylinder;
    }

    public String getManometer() {
        return manometer;
    }

    @XmlElement
    public void setManometer(String manometer) {
        this.manometer = manometer;
    }
}
