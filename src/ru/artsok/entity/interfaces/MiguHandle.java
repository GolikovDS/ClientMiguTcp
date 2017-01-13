package ru.artsok.entity.interfaces;


import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import ru.artsok.entity.Migu;
import ru.artsok.entity.MiguMap;

public interface MiguHandle {
    public static MiguMap miguMap = new MiguMap();
    public void view();
    public void addMigu(TreeItem<String> rootMigu,  TreeView<String> treeMigu, Migu migu);

    public void removeMiguByNumberTreeView(String number);
}
