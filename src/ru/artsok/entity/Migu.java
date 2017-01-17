package ru.artsok.entity;


import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ru.artsok.Main;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.IOException;

@XmlRootElement(name = "migu")
@XmlType(propOrder = {"address", "number", "type", "node", "states"})
public class Migu {

    private int address;
    private int number;
    private String type;
    private String node;
    private Tab tab;
    private TreeItem<String> treeItem;
    private AnchorPane anchorPaneViewMigu;
    private MiguState states = new MiguState();


    public Migu() {
    }

    public Migu(int address, int number, String type, String node) {
        this.address = address;
        this.node = node;
        this.number = number;
        this.type = type;
        tab = getTabMigu(number);
        treeItem = getTreeItemMigu(address, number, type, node);
    }

    public Migu setViewItem(){
        return new Migu(address, getNumber(), type, node);
    }

    private Tab getTabMigu(int number) {
        Tab tab = null;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("/ru/artsok/view/viewMigu.fxml"));
        try {
            anchorPaneViewMigu = fxmlLoader.load();
            tab = new Tab("МИЖУ №" + number, anchorPaneViewMigu);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }

    public TreeItem<String> getTreeItemMigu(int address, int number, String type, String node) {
        ImageView imageView = new ImageView(new Image("ru/artsok/resources/icon/OK.png"));
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        TreeItem<String> item = new TreeItem<>("МИЖУ зав. № " + number, imageView);
        item.getChildren().add(new TreeItem<>("Адрес: " +address));
        item.getChildren().add(new TreeItem<>("Тип: " + type));
        item.getChildren().add(new TreeItem<>("Примечание: " + node));
        return item;
    }

    public void setAnchorPaneSize(double width, double height) {
        anchorPaneViewMigu.setMinSize(width, height);
    }

    public AnchorPane getAnchorPaneViewMigu() {
        return anchorPaneViewMigu;
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

    public MiguState getStates() {
        return states;
    }

    @XmlElement
    public void setStates(MiguState states) {
        this.states = states;
    }

    @Override
    public String toString() {
        return "МИЖУ зав. № " + number;
    }

    public Tab getTab() {
        return tab;
    }

    public TreeItem<String> getTreeItem() {
        return treeItem;
    }
}
