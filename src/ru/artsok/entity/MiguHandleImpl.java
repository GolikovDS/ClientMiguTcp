package ru.artsok.entity;


import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.artsok.entity.interfaces.MiguHandle;

public class MiguHandleImpl implements MiguHandle {

    @Override
    public void view() {
    }

    @Override
    public void addMigu(TreeItem<String> rootMigu, TreeView<String> treeMigu, Migu migu) {
        migu.getStates().setMiguIsRespond(false);
        if (migu != null) {
            ImageView imageView = new ImageView(new Image("ru/artsok/resources/icon/OK.png"));
            imageView.setFitHeight(15);
            imageView.setFitWidth(15);
            TreeItem<String> item = new TreeItem<>("МИЖУ зав. № " + migu.getNumber(), imageView);
            item.getChildren().add(new TreeItem<>("Адрес: " + migu.getAddress()));
            item.getChildren().add(new TreeItem<>("Тип: " + migu.getType()));
            item.getChildren().add(new TreeItem<>("Примечание: " + migu.getNode()));
            rootMigu.getChildren().add(item);
            treeMigu.setRoot(rootMigu);
            rootMigu.setExpanded(true);
            miguMap.getMap().put(migu.getNumber(), migu);
//            SettingMainController.miguMap.put(migu.getNumber(), migu);
        }
    }

    public void removeMiguByNumberTreeView(String number) {
        miguMap.getMap().remove(Integer.valueOf(number.substring(12)));
//        SettingMainController.miguMap.remove(Integer.valueOf(number.substring(12)));
    }
}
