/*
 * javafx setClassStyle
 * ref: https://www.itread01.com/p/664210.html
 * ref: https://www.itread01.com/content/1544828228.html
 * ref: https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html
 * */

package com.tnfs.infoApplication.controller.common;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.tnfs.infoApplication.cache.CommonCache;
import com.tnfs.infoApplication.util.MyDataFrame;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class WidgetNewMemberChooser implements Initializable {
    static private MyDataFrame df = null;
    static List<String> primary = null;
    static List<String> memberAll = null;
    private String name = null;
    {
        URL loc = getClass().getResource("../../csi_member.csv");
        System.out.println("loc:------ "+loc.toExternalForm().substring(6));
        df = new MyDataFrame(loc.toExternalForm().substring(6));
        memberAll= df.getColumn("名稱");
        primary = df.getColumnNames();
        System.out.println(primary+"#: "+primary.indexOf("#")+" 名稱: "+primary.indexOf("名稱"));
//        MyUtil.stringToAscii(case_type_primary.get(case_type_primary.size()-1));
//        MyUtil.stringToAscii("#");
        // theoretically, remove => '#' which indicate index of each row
        primary.remove(primary.indexOf("#")); // column-#
        primary.remove(primary.indexOf("名稱")); // column-案類
    }
    @FXML private JFXComboBox comboMRank;
    @FXML private JFXButton btnSubmit, btnClose, btnUnitExpand, btnUnitSqueeze;
    @FXML private JFXTextField tfName, tfUnitName, tfSuperviserUnitName;
    @FXML private HBox paneUnit;
    private WidgetNewMember widgetNewMember = new WidgetNewMember();
    private Node root = null;
    private List<String> selectedMemberList = new ArrayList<>();

    public WidgetNewMemberChooser() {
        URL loc = CommonCache.getInstance().getMemberChooserFxmlURL();
//        System.out.println("loc:++ "+loc);
        FXMLLoader fxmlLoader = new FXMLLoader(loc);
        try {
            fxmlLoader.setController(this);
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboMRank.getItems().addAll("主任","技正","專員","股長","警務正","警務員","巡官","小隊長","偵查佐","巡佐","警員");
        resetAutoCompleteTextField();
        paneUnit.managedProperty().bind(paneUnit.visibleProperty());
        btnUnitSqueeze.managedProperty().bind(btnUnitSqueeze.visibleProperty());
        btnUnitExpand.managedProperty().bind(btnUnitExpand.visibleProperty());
        btnUnitExpand.setOnMouseClicked(event -> {
            paneUnit.setVisible(true);
            btnUnitSqueeze.setVisible(true);
            btnUnitExpand.setVisible(false);
        });
        btnUnitSqueeze.setOnMouseClicked(event -> {
            paneUnit.setVisible(false);
            btnUnitSqueeze.setVisible(false);
            btnUnitExpand.setVisible(true);
        });
    }

    public void resetAutoCompleteTextField() {
        List<String> memberExcludeSelected = new ArrayList<>();
        memberExcludeSelected.addAll(memberAll);
        memberExcludeSelected.removeAll(selectedMemberList);
        TextFields.bindAutoCompletion(tfName, FXCollections.observableArrayList(memberExcludeSelected));
    }

    public Node getRoot() { return root; }
    public JFXButton getBtnSubmit() { return btnSubmit; }
    public JFXButton getBtnClose() { return btnClose; }
    public JFXButton getBtnUnitExpand() { return btnUnitExpand; }
    public JFXButton getBtnUnitSqueeze() { return btnUnitSqueeze; }

    public void addMember() {
        String name = tfName.getText();
        String mrank = comboMRank.getItems().toString();
        String unitName = tfUnitName.getText();
        String superviserUnitName = tfSuperviserUnitName.getText();
        if (selectedMemberList.contains(name)) {
            name = null;
        }
        tfName.setText("");
        resetAutoCompleteTextField();
        if (name != null && !name.equals("")) {
            selectedMemberList.add(name);
        }
        resetAutoCompleteTextField();
        System.out.println("list: "+selectedMemberList);
    }
    public List<String> getMemberList() {
        return selectedMemberList;
    }
    public void remove(String name) { selectedMemberList.remove(name);}
}
