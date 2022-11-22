package com.tnfs.infoApplication.controller.common;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.istack.internal.NotNull;
import com.tnfs.infoApplication.cache.CommonCache;
import com.tnfs.infoApplication.cache.CriminalCaseCache;
import com.tnfs.infoApplication.model.CriminalCaseObj;
import com.tnfs.infoApplication.model.InChargeObj;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.tableview2.TableColumn2;
import org.controlsfx.control.tableview2.TableView2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.tnfs.infoApplication.util.MyUtil.createColumn;

public class WidgetNewInCharge implements Initializable {
    @FXML private JFXComboBox comboType;
    @FXML private JFXTextField tfReserved;
    @FXML private JFXButton btnSubmit, btnClose, btnInChargerInfo;
    @FXML private JFXCheckBox chkBoxNotification;
    @FXML private HBox unitBlock;
    @FXML private VBox optPane;
    @FXML private TableView2<InChargeObj> table;

    private WidgetUnit widgetUnit = new WidgetUnit();
    private Node root;
    private Point2D optPanePos;
    private boolean updateMode = false;
    private Long criminalCaseId = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        initOptPane();
    }

    private void initOptPane() {
        unitBlock.getChildren().clear();
        unitBlock.getChildren().add(widgetUnit.getMainPane());
        widgetUnit.setUnitId(1016L);
        optPane.visibleProperty().bindBidirectional(optPane.managedProperty());
        optPane.setVisible(true);
        optPane.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            optPanePos = new Point2D(event.getX(), event.getY());
        });
        optPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            if (optPanePos != null) {
                optPane.setTranslateX(optPane.getTranslateX() + event.getX() - optPanePos.getX());
                optPane.setTranslateY(optPane.getTranslateY() + event.getY() - optPanePos.getY());
            }
        });
        comboType.getItems().addAll("主辦","協辦");
        comboType.setValue("主辦");
    }

    private void initTable() {
        TableView.TableViewSelectionModel<InChargeObj> selectionModel = table.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn2<InChargeObj, Void> indexCol = new TableColumn2<>("#");
        indexCol.setCellFactory( col -> {
            TableCell<InChargeObj, Void> cell = new TableCell<>();
            StringBinding binding = Bindings.createStringBinding(()->{
                int ind = cell.getIndex();
                return ind >= 0 && ind < table.getItems().size() ? Integer.toString(ind+1) : "";
            }, cell.textProperty(), table.getItems());
            cell.textProperty().bind(binding);
            return cell;
        });
        indexCol.setSortable(false);
        indexCol.setPrefWidth(25);
        TableColumn2<InChargeObj, String> nameCol = createColumn("姓名", 80, new PropertyValueFactory<>("name"));
        TableColumn2<InChargeObj, String> mrankCol = createColumn("職稱", 80, new PropertyValueFactory<>("mrank"));
        TableColumn2<InChargeObj, String> dispatchDateCol = createColumn("派案日期", 140, new PropertyValueFactory<>("mingGougDispatchDate"));
        TableColumn2<InChargeObj, String> reserveCol = createColumn("備註", 160, new PropertyValueFactory<>("reserve"));
        TableColumn2<InChargeObj, Void> optCol = new TableColumn2<>("編輯");
        optCol.setPrefWidth(130);
        optCol.setCellFactory(col -> new TableCell<InChargeObj, Void>() {
            private final HBox hb = new HBox(10);
            private final JFXButton btnEdit = new JFXButton(("編輯"));
            private final JFXButton btnDelete = new JFXButton("刪除");
            private int getSelectedRowIndex() { //switch to the current row
                int index = getIndex();
                table.getSelectionModel().select(index);
                return index;
            }
            {
                btnEdit.setStyle("-fx-background-color: pass");
                btnEdit.setOnAction(event -> {
                    int index = getSelectedRowIndex();
                    optPane.setVisible(true);
                    updateMode = true;
                    setUIs(table.getItems().get(index));
                });
                btnDelete.setStyle("-fx-background-color: warning");
                btnDelete.setOnAction(event -> {
                    int index = getSelectedRowIndex();
                    optPane.setVisible(false);
                    InChargeObj obj = table.getItems().get(index);
                    CriminalCaseCache.getInstance().getCriminalCaseObj().getInCharges().remove(obj);
                    table.getItems().remove(obj);
                });
                hb.getChildren().addAll(btnEdit, btnDelete);
            }
            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                int index = getIndex();
                int last = table.getItems().size() - 1;
                if (index > last || empty) {
                    setGraphic(null);
                } else {
                    setGraphic(hb);
                }
                table.refresh();
            }
        });

        table.getColumns().clear();
        table.getColumns().addAll(indexCol, nameCol, mrankCol, dispatchDateCol, reserveCol, optCol);
        table.setPrefHeight(200);
        table.setPrefWidth(600);

    }

    public WidgetNewInCharge() {
        URL loc = CommonCache.getInstance().getInChargeFxmlURL();
        FXMLLoader fxmlLoader = new FXMLLoader(loc);
        try {
            fxmlLoader.setController(this);
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doSubmit() {
        CriminalCaseObj criminalCaseObj = CriminalCaseCache.getInstance().getCriminalCaseObj();
        if (updateMode) {
            InChargeObj obj = table.getSelectionModel().getSelectedItem();
            updateObjByUIs(obj);
            table.refresh();
        } else {
            InChargeObj obj = new InChargeObj();
            updateObjByUIs(obj);
            obj.setCriminalCaseId(criminalCaseId);
            criminalCaseObj.getInCharges().add(obj);
//            List<InChargeObj> list = criminalCaseObj.getInCharges().stream().collect(Collectors.toList());
//            table.getItems().clear();
//            table.getItems().addAll(FXCollections.observableArrayList(list));
            table.getItems().add(obj);
        }
        btnSubmit.setText("新增");
        updateMode = false;
    }

    public void setUIs(@NotNull InChargeObj target) {
        CriminalCaseObj criminalCaseObj = CriminalCaseCache.getInstance().getCriminalCaseObj();
        criminalCaseId = criminalCaseObj.getId();
        assert criminalCaseId != null;
        if (target != null) {
            Long unitId = target.getUnitId();
            widgetUnit.setUnitId(unitId);
            comboType.setValue(target.getType());
            tfReserved.setText(target.getReserve());
            chkBoxNotification.setSelected(target.getNotification());
        } else {
            clearUIs();
        }
        btnSubmit.setText( updateMode ? "修改" : "新增");
    }

    public void clearUIs() {
        comboType.setValue("主辦");
        chkBoxNotification.setSelected(false);
        widgetUnit.setUnitId(1016L);
        tfReserved.setText("");
    }

    public void updateObjByUIs(@NotNull InChargeObj target) {
        target.setUnitId(widgetUnit.getUnitId());
        target.setReserve(tfReserved.getText());
        target.setNotification(chkBoxNotification.isSelected());
    }

    public void setUpdateMode(boolean update) {
        updateMode = update;
    }

    public Node getRoot() { return root; }
////    public void setOptPaneVisible(boolean visible) {
////        optPane.setVisible(visible);
////    }
//    public VBox getOptPane() { return optPane; }
//    public VBox getPersonOptPane() { return widgetPerson.getOptPane(); }
//    public TableView2 getTable() { return table; }
//    public JFXButton getBtnClose() { return btnClose; }
//    public JFXButton getBtnSubmit() { return btnSubmit; }
}
