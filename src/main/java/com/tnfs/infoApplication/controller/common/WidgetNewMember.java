package com.tnfs.infoApplication.controller.common;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.tnfs.infoApplication.cache.CommonCache;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WidgetNewMember implements Initializable {
    @FXML private JFXComboBox comboMRank;
    @FXML private JFXTextField tfName;
    @FXML private JFXButton btnSubmit, btnClose, btnMoveUp, btnMoveDown;
    @FXML private FlowPane paneUnit;
    private WidgetUnit wnu = new WidgetUnit();
    private Node root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TextFields.bindAutoCompletion(tfName, FXCollections.observableArrayList("麻豆分局", "善化分局", "第一分局"));
        paneUnit.getChildren().clear();
//        paneUnit.getChildren().add(wnu.getRoot());
    }

    public WidgetNewMember() {
        URL loc = CommonCache.getInstance().getMemberFxmlURL();
        FXMLLoader fxmlLoader = new FXMLLoader(loc);
        try {
            fxmlLoader.setController(this);
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getName() { return tfName.getText();}
    public Node getRoot() { return root; }
    public JFXButton getBtnClose() { return btnClose; }
    public JFXButton getBtnSubmit() { return btnSubmit; }

}
