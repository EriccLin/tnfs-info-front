/*
 * javafx setClassStyle
 * ref: https://www.itread01.com/p/664210.html
 * ref: https://www.itread01.com/content/1544828228.html
 * ref: https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html
 * */

package com.tnfs.infoApplication.controller.common;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WidgetNewCaseType implements Initializable {
    @FXML protected JFXButton btnSubmit;
    @FXML private HBox pane;
    private WidgetNewCaseTypeChooserV2 chooserController = new WidgetNewCaseTypeChooserV2();
    private Node root = null;

    public WidgetNewCaseType() {
        URL loc = getClass().getResource("../view/widget/NewCaseType.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(loc);
        try {
            fxmlLoader.setController(this);
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void doSubmit() {
        chooserController.addCaseType();
        setPaneList();
    }

    public Node getRoot() { return root; }

    public Node getChooserRoot() { return chooserController.getRoot(); }

    public List<String> getSelectedCaseTypes() {
        return chooserController.getCaseTypeList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSubmit.setText("");
        SVGPath iconPlus = new SVGPath();
//        iconPlus.setContent("M0 6h16v4h-16z");
        iconPlus.setContent("M16 6h-6v-6h-4v6h-6v4h6v6h4v-6h6z");
        iconPlus.setScaleY(0.8);
        iconPlus.setStyle("-fx-fill: green;");
        btnSubmit.setGraphic(iconPlus);
        btnSubmit.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//        btnSubmit.setOnMouseClicked(event -> {
//            if (stageChooser.isShowing()) {
//                stageChooser.hide();
//            } else {
//                stageChooser.show();
//            }
//        });
//        chooserController.getBtnSubmit().setOnMouseClicked(event->doSubmit());
    }
    public JFXButton getBtnSubmit() { return btnSubmit; }
    public JFXButton getChooserBtnSubmit() { return chooserController.getBtnSubmit(); }
    public JFXButton getChooserBtnClose() { return chooserController.getBtnClose(); }
    public void setPaneList() {
        ObservableList<Node> observableList = pane.getChildren();
        observableList.clear();
        for (String castType : chooserController.getCaseTypeList()) {
            JFXButton btnDelete = new JFXButton(castType);
            //btnDelete.getStyleClass().add("wd-60;");
            SVGPath svgpath = new SVGPath();
            svgpath.setStyle("-fx-fill: red;");
            svgpath.setContent("M0 6h16v4h-16z");
            btnDelete.setGraphic(svgpath);
            btnDelete.setOnAction(event -> {
                chooserController.removeFromCaseTypeList(castType);
                observableList.remove(btnDelete);
            });
            observableList.add(btnDelete);
        }
        observableList.add(btnSubmit);
    }
}
