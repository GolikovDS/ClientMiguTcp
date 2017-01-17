package ru.artsok.entity;


import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import ru.artsok.entity.interfaces.MiguHandle;

public class MiguHandleImpl implements MiguHandle {

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

//        SettingMainController.miguMap.remove(Integer.valueOf(number.substring(12)));
        return migu;
    }
}
