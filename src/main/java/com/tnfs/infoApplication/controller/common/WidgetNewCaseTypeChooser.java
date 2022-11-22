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
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.tnfs.infoApplication.util.MyDataFrame;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class WidgetNewCaseTypeChooser implements Initializable {
    static private MyDataFrame case_type_df = null;
    static List<String> case_type_primary = null;
    static List<String> case_type_all = null;
    private final static int USE_COMBOS = 0;
    private final static int USE_TEXTFIELD = 1;
    private int state = 0;
    private String case_type = null;
    {
        URL loc2 = getClass().getResource("..");
        URL loc = getClass().getResource("../case_types.csv");
        System.out.println("loc:------ "+loc.toExternalForm().substring(6));
        case_type_df = new MyDataFrame(loc.toExternalForm().substring(6));
        case_type_all= case_type_df.getColumn("案類");
        case_type_primary = case_type_df.getColumnNames();
        System.out.println(case_type_primary+"#: "+case_type_primary.indexOf("#")+" 案類: "+case_type_primary.indexOf("案類"));
//        MyUtil.stringToAscii(case_type_primary.get(case_type_primary.size()-1));
//        MyUtil.stringToAscii("#");
        // theoretically, remove => '#' which indicate index of each row
        case_type_primary.remove(case_type_primary.indexOf("#")); // column-#
        case_type_primary.remove(case_type_primary.indexOf("案類")); // column-案類
    }
    @FXML HBox hbox_case_type_tf, hbox_case_type_combos;
    @FXML private JFXComboBox combo_case_type_primary, combo_case_type_secodary;
    @FXML protected JFXButton btnSubmit, btnClose, btnCombo, btnTextfield;
    @FXML private JFXTextField tf_case_type;
    @FXML Label label_tf, label_combo;
    private FlowPane pane = new FlowPane();
    private Node root = null;
    private List<String> caseTypeList = new ArrayList<>();

    public WidgetNewCaseTypeChooser() {
        URL loc = getClass().getResource("../view/widget/NewCaseTypeChooser.fxml");
        System.out.println("loc:++ "+loc);
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
        setCombos(case_type_primary.get(0));
        combo_case_type_primary.getItems().addAll(case_type_primary);
        combo_case_type_primary.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue!= null && !newValue.isEmpty()){
                    setCombos(newValue);
                }
            }
        });
        resetAutoCompleteTextField();
        label_tf.setVisible(false);
        hbox_case_type_combos.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    if (state == USE_COMBOS) {
                        hbox_case_type_combos.getStylesheets().clear();
                        hbox_case_type_tf.getStylesheets().add("selected_content");
                    } else {
                        hbox_case_type_combos.getStylesheets().add("selected_content");
                        hbox_case_type_tf.getStylesheets().clear();
                    }
                }
            }
        });
        state = USE_COMBOS;
        hbox_case_type_combos.getStyleClass().add("selected_content");
        tf_case_type.setOnMouseClicked(event->setSelectContent(USE_TEXTFIELD));
        hbox_case_type_tf.setOnMouseClicked(event->setSelectContent(USE_TEXTFIELD));
        combo_case_type_secodary.setOnMouseClicked(event->setSelectContent(USE_COMBOS));
        combo_case_type_primary.setOnMouseClicked(event->setSelectContent(USE_COMBOS));
        hbox_case_type_combos.setOnMouseClicked(event->setSelectContent(USE_COMBOS));
    }

    public void resetAutoCompleteTextField() {
        List<String> case_type_all_after_excluded = new ArrayList<>();
        case_type_all_after_excluded.addAll(case_type_all);
        //Collections.copy(case_type_all,case_type_all_after_excluded);
        case_type_all_after_excluded.removeAll(caseTypeList);
        TextFields.bindAutoCompletion(tf_case_type, FXCollections.observableArrayList(case_type_all_after_excluded));
    }

    public Node getRoot() { return root; }

    public JFXButton getBtnSubmit() { return btnSubmit; }

    public JFXButton getBtnClose() { return btnClose; }

    public void addCaseType() {
        if (state == USE_TEXTFIELD) {
            case_type = tf_case_type.getText();
            // this type already exits
            if (caseTypeList.contains(case_type)) {
                case_type = null;
            }
            tf_case_type.setText("");
            resetAutoCompleteTextField();
        } else {
            Object obj = combo_case_type_secodary.getValue();
            case_type = obj == null? null : obj.toString();
        }
        if (case_type != null && !case_type.equals("")) {
            this.caseTypeList.add(case_type);
            if (state != USE_TEXTFIELD){
                setCombos(combo_case_type_primary.getValue().toString());
            }
        }
    }

    public void setCombos(String newCaseTypePrimary) {
        System.out.println("==> "+case_type_df.getColumn(newCaseTypePrimary));
        List<Integer> integerList = case_type_df.getColumn(newCaseTypePrimary);
        System.out.println("==> "+ case_type_df.getColumn( integerList,"案類"));
        List<String> case_type_secondaries = case_type_df.getColumn(integerList,"案類");
        System.out.println("setExcludedCaseTypeList: "+caseTypeList);
        case_type_secondaries.removeAll(caseTypeList);
        System.out.println("caseTypeListCandidate: "+case_type_secondaries);
        combo_case_type_secodary.getItems().clear();
        combo_case_type_secodary.getItems().addAll(case_type_secondaries);
        combo_case_type_primary.setValue(newCaseTypePrimary);
        if (case_type_secondaries.size() > 0) {
            combo_case_type_secodary.setValue(case_type_secondaries.get(0));
        }
    }

    private void setSelectContent(int expect_state) {
        if (expect_state == state) {
            return;
        }
        state = (state == USE_COMBOS) ? USE_TEXTFIELD : USE_COMBOS;
        if (state == USE_TEXTFIELD) { // USE_COMBOS => USE_TEXTFIELD
            hbox_case_type_combos.getStyleClass().clear();
            hbox_case_type_tf.getStyleClass().add("selected_content");
            combo_case_type_primary.setValue("");
            combo_case_type_secodary.setValue("");
            combo_case_type_secodary.getItems().clear();
            label_tf.setVisible(true);
            label_combo.setVisible(false);
        } else {  // USE_TEXTFIELD => USE_COMBOS
            hbox_case_type_combos.getStyleClass().add("selected_content");
            hbox_case_type_tf.getStyleClass().clear();
            tf_case_type.clear();
            label_tf.setVisible(false);
            label_combo.setVisible(true);
        }
    }
    public List<String> getCaseTypeList() {
        return caseTypeList;
    }
    public void removeFromCaseTypeList(String caseType) {
        caseTypeList.remove(caseType);
    }
}
