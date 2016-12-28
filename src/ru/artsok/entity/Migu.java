package ru.artsok.entity;


import javafx.scene.control.TreeItem;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "migu")
@XmlType(propOrder = {"address", "number", "type", "node"})
public class Migu {

    private int address;
    private int number;
    private String type;
    private String node;

    TreeItem<String> rootMigu = new TreeItem<String>("Устройства");

    public Migu() {

    }


    public Migu(int address, int number, String type, String node) {
        this.address = address;
        this.node = node;
        this.number = number;
        this.type = type;
    }


    public String getNode() {
        return node;
    }

    @XmlElement
    public void setNode(String node) {
        this.node = node;
    }

    public int getNumber() {
        return number;
    }

    @XmlElement
    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    @XmlElement
    public void setType(String type) {
        this.type = type;
    }

    public int getAddress() {
        return address;
    }

    @XmlElement
    public void setAddress(int address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "МИЖУ зав. № " + number;
    }
}
