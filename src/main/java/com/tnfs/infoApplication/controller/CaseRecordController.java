package com.tnfs.infoApplication.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.tnfs.infoApplication.model.CriminalCaseObj;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CaseRecordController implements Initializable {

    @FXML private JFXTextArea taDescription;
    @FXML private JFXButton btnNewEvidence, btnSubmit, btnCancel, btnMax, btnMin;
    @FXML private VBox contentView;
    @FXML private HBox contentViewBody;
    private Node root;
    private CriminalCaseObj criminalCaseObj = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        contentViewBody.managedProperty().bind(contentViewBody.visibleProperty());
        contentViewBody.setVisible(false);

        btnMin.setOnMouseClicked(event -> {
            contentViewBody.setVisible(false);
        });
        btnMax.setOnMouseClicked(event -> {
            contentViewBody.setVisible(true);
        });
    }

    public CaseRecordController() {
        URL loc = getClass().getResource("../view/CaseRecord.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(loc);
        try {
            fxmlLoader.setController(this);
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public VBox getContentView() {return contentView; }
    public JFXButton getBtnSubmit() { return btnSubmit; }
    public JFXButton getBtnCancel() { return btnCancel; }
    public JFXButton getBtnMin() { return btnMin; }
    public JFXButton getBtnMax() { return btnMax; }
    public Node getRoot() { return root; }
}
