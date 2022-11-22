package com.tnfs.infoApplication.controller;

import com.jfoenix.controls.JFXButton;
import com.tnfs.infoApplication.controller.common.WidgetNewInvestigation;
import com.tnfs.infoApplication.controller.common.WidgetNewMember;
import com.tnfs.infoApplication.model.CriminalCaseObj;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CaseInvestigationController implements Initializable {

    @FXML private JFXButton btnNewInvestigation, btnNewMember, btnSubmit, btnCancel, btnMax, btnMin;
    @FXML private VBox paneTimeLocality;
    @FXML private VBox contentView;
    @FXML private HBox contentViewBody;
    PopOver popupWidgetNewInvestigation = new PopOver();
    PopOver popupWidgetNewMember = new PopOver();
    WidgetNewMember widgetNewMember = new WidgetNewMember();
    WidgetNewInvestigation widgetNewInvestigation = new WidgetNewInvestigation();
    private Node root;
    private CriminalCaseObj criminalCaseObj = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        popupWidgetNewMember.setContentNode(widgetNewMember.getRoot());
        popupWidgetNewMember.setArrowLocation(PopOver.ArrowLocation.BOTTOM_LEFT);
        popupWidgetNewMember.setDetachable(false);
        popupWidgetNewMember.setAutoFix(true);
        btnNewMember.setOnMouseClicked(event -> popupWidgetNewMember.show(btnNewMember));
        widgetNewMember.getBtnClose().setOnMouseClicked(event -> popupWidgetNewMember.hide());

        popupWidgetNewInvestigation.setContentNode(widgetNewInvestigation.getRoot());
        popupWidgetNewInvestigation.setArrowLocation(PopOver.ArrowLocation.BOTTOM_LEFT);
        popupWidgetNewInvestigation.setDetachable(false);
        popupWidgetNewInvestigation.setAutoFix(true);
        btnNewInvestigation.setOnMouseClicked(event -> popupWidgetNewInvestigation.show(btnNewInvestigation));
        widgetNewInvestigation.getBtnClose().setOnMouseClicked(event -> popupWidgetNewInvestigation.hide() );

        contentViewBody.managedProperty().bind(contentViewBody.visibleProperty());
        contentViewBody.setVisible(false);

        btnMin.setOnMouseClicked(event -> {
            contentViewBody.setVisible(false);
        });
        btnMax.setOnMouseClicked(event -> {
            contentViewBody.setVisible(true);
        });
    }

    public CaseInvestigationController() {
        URL loc = getClass().getResource("../view/CaseInvestigation.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(loc);
        try {
            fxmlLoader.setController(this);
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Node getRoot() { return root; }
    public VBox getContentView() {return contentView; }
}
