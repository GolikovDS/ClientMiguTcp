package ru.artsok.entity;


import javafx.scene.control.TreeItem;
import ru.artsok.SettingMainController;

public class MiguImpl {

    private Migu migu;

    public MiguImpl() {
    }

    public MiguImpl(Migu migu) {
        this.migu = migu;
    }

    public TreeItem<String> item() {
        TreeItem<String> item = new TreeItem<>("МИЖУ зав. № " + migu.getNumber());
        item.getChildren().add(new TreeItem<>("Адрес: " + migu.getAddress()));
        item.getChildren().add(new TreeItem<>("Тип: " + migu.getType()));
        item.getChildren().add(new TreeItem<>("Примечание: " + migu.getNode()));
        return item;
    }


    public Migu getMiguByNameTreeView(String number) {
        return getMiguByNumber(Integer.parseInt(number.substring(':') + 2));
    }

    public Migu getMiguByNumber(int number) {
        return SettingMainController.miguMap.get(number);
    }

    public void removeMiguByNameTreeView(String number) {
        SettingMainController.miguMap.remove(Integer.parseInt(number.substring(':') + 2));
    }

    public void putMigu() {
        SettingMainController.miguMap.put(migu.getNumber(), migu);
    }

    public Migu getMigu() {
        return migu;
    }

    public void setMigu(Migu migu) {
        this.migu = migu;
    }
}
