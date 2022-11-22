package com.tnfs.infoApplication.controller.common;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.tnfs.infoApplication.cache.CommonCache;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import org.controlsfx.control.PopOver;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WidgetNewInvestigation implements Initializable {

    @FXML private JFXButton btnSubmit, btnClose;
    @FXML private VBox paneTimeLocality;
    @FXML private JFXComboBox comboType;
    @FXML private HBox hBoxDateTime, hBoxDateTimeEnd;
    @FXML private FlowPane paneMember;
    private WidgetDateTime begin = new WidgetDateTime();
    private WidgetDateTime end = new WidgetDateTime();
    private WidgetNewMemberChooser widgetNewMemberChooser = new WidgetNewMemberChooser();
    private PopOver popoverWidgetNewMemberChooser = new PopOver();
    private JFXButton btnAddNewMember = new JFXButton();
    private Node root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hBoxDateTime.getChildren().clear();
        begin.setTimeBlockVisible(false);
        hBoxDateTime.getChildren().add(begin.getRoot());
        hBoxDateTimeEnd.getChildren().clear();
        end.setTimeBlockVisible(false);
        hBoxDateTimeEnd.getChildren().add(end.getRoot());
        comboType.getItems().addAll("勘察","複勘","相驗","解剖","證物處理","其他");
        SVGPath iconAddNewMember = new SVGPath();
        iconAddNewMember.setContent("M16 6h-6v-6h-4v6h-6v4h6v6h4v-6h6z");
        btnAddNewMember.setGraphic(iconAddNewMember);

        popoverWidgetNewMemberChooser.setContentNode(widgetNewMemberChooser.getRoot());
        popoverWidgetNewMemberChooser.setDetachable(false);
        popoverWidgetNewMemberChooser.setArrowLocation(PopOver.ArrowLocation.BOTTOM_LEFT);
        popoverWidgetNewMemberChooser.setAutoFix(true);
        btnAddNewMember.setOnMouseClicked(event -> popoverWidgetNewMemberChooser.show(btnAddNewMember));
        widgetNewMemberChooser.getBtnClose().setOnMouseClicked(event -> popoverWidgetNewMemberChooser.hide());

        paneMember.getChildren().add(btnAddNewMember);
        widgetNewMemberChooser.getBtnSubmit().setOnMouseClicked(event -> {
            widgetNewMemberChooser.addMember();
            setPaneList();
        });
    }

    public WidgetNewInvestigation() {
        URL loc = CommonCache.getInstance().getInvestigationFxmlURL();
        FXMLLoader fxmlLoader = new FXMLLoader(loc);
        try {
            fxmlLoader.setController(this);
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPaneList() {
        ObservableList<Node> observableList = paneMember.getChildren();
        observableList.clear();
        for (String name : widgetNewMemberChooser.getMemberList()) {
            JFXButton btnDelete = new JFXButton(name);
            SVGPath svgpath = new SVGPath();
            svgpath.setStyle("-fx-fill: red;");
            svgpath.setContent("M0 6h16v4h-16z");
            btnDelete.setGraphic(svgpath);
            btnDelete.setOnAction(event -> {
                widgetNewMemberChooser.remove(name);
                observableList.remove(btnDelete);
            });
            observableList.add(btnDelete);
        }
        observableList.add(btnAddNewMember);
    }
    public Node getRoot() { return root; }
    public JFXButton getBtnClose() {return btnClose;}
}
