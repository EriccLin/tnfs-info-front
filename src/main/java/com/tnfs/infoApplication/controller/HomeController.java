package com.tnfs.infoApplication.controller;

import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    private VBox contentView;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public VBox getContentView() { return contentView; }
}
