package com.tnfs.infoApplication.controller.common;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.istack.internal.NotNull;
import com.tnfs.infoApplication.cache.CommonCache;
import com.tnfs.infoApplication.cache.CriminalCaseCache;
import com.tnfs.infoApplication.model.CriminalCaseObj;
import com.tnfs.infoApplication.model.TimeLocalityObj;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.tableview2.TableColumn2;
import org.controlsfx.control.tableview2.TableView2;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static com.tnfs.infoApplication.util.MyUtil.createColumn;

public class WidgetNewTimeLocality implements Initializable {
    @FXML private JFXComboBox comboType;
    @FXML private JFXTextField location, explanation;
    @FXML private JFXButton btnSubmit;
    @FXML private JFXButton btnClose;
    @FXML private JFXCheckBox checkBoxTimeRange;
    @FXML private HBox hBoxDateTime, hBoxDateTimeEnd;
    @FXML private Label labelDateTimeEnd;
    @FXML private Pane optPane;
    @FXML private TableView2<TimeLocalityObj> table;

    private Stage stage = null, owner = null;
    private Point2D initPos = null;
    private Node root = null;
    private boolean updateMode = false;
    private EventHandler<MouseEvent> mousePressedEventHandler = null;
    private EventHandler<MouseEvent> mouseDraggedEventHandler = null;

    private WidgetDateTime begin = new WidgetDateTime();
    private WidgetDateTime end = new WidgetDateTime();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // List Data in the Table
        initTable();
        // Edit/Create Data
        initOptPane();
    }

    public void show() {
        if (stage == null) {
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            if (owner != null) {
                stage.initOwner(owner);
            }

            mousePressedEventHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    initPos = new Point2D(event.getX(), event.getY());
                }
            };

            mouseDraggedEventHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (initPos != null) {
                        double deltaX = event.getX() - initPos.getX();
                        double deltaY = event.getY() - initPos.getY();
                        if (deltaX * deltaX + deltaY * deltaY > 4) {
                            stage.setX(stage.getX() + deltaX);
                            stage.setY(stage.getY() + deltaY);
                        }
                    } else {
                        initPos = new Point2D(event.getX(), event.getY());
                    }
                }
            };

            stage.setWidth(optPane.getPrefWidth());
            stage.setHeight(optPane.getPrefHeight());
            optPane.setLayoutX(0);
            optPane.setLayoutY(0);
            stage.setScene(new Scene(optPane));
        }
        stage.getScene().setOnMousePressed(mousePressedEventHandler);
        stage.getScene().setOnMouseDragged(mouseDraggedEventHandler);
        stage.show();
    }

    public void hide() {
        if (stage != null) {
            if (mousePressedEventHandler != null) {
                stage.getScene().removeEventHandler(MouseEvent.MOUSE_PRESSED, mousePressedEventHandler);
            }
            if (mouseDraggedEventHandler != null) {
                stage.getScene().removeEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDraggedEventHandler);
            }
            stage.hide();
        }
    }

    public WidgetNewTimeLocality() {
        URL loc = CommonCache.getInstance().getTimeLocalityFxmlURL();
        FXMLLoader fxmlLoader = new FXMLLoader(loc);
        try {
            fxmlLoader.setController(this);
            root = fxmlLoader.load();
            ((Pane)optPane.getParent()).getChildren().remove(optPane);
//            root.setStyle("-fx-effect: dropshadow(three-pass-box, darkgray, 10, 0, 0, 0);");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setOwner(Stage owner) {
        this.owner = owner;
    }

    private void initOptPane() {
        begin.setRangeStart(true);
        end.setRangeStart(false);
        hBoxDateTime.getChildren().clear();
        hBoxDateTime.getChildren().add(begin.getRoot());
        hBoxDateTimeEnd.getChildren().clear();
        hBoxDateTimeEnd.getChildren().add(end.getRoot());
        checkBoxTimeRange.setSelected(false);
        hBoxDateTimeEnd.setDisable(true);
        labelDateTimeEnd.setDisable(true);
        comboType.getItems().addAll("報案","犯案","其他");
        checkBoxTimeRange.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                boolean flag = (newValue != null && newValue);
                hBoxDateTimeEnd.setDisable(!flag);
                labelDateTimeEnd.setDisable(!flag);
            }
        });
        btnSubmit.setOnMouseClicked(event -> {
            doSubmit();
            hide();
        });
        btnClose.setOnMouseClicked(event -> {
            hide();
        });
    }

    private void initTable() {
        TableView2.TableViewSelectionModel<TimeLocalityObj> selectionModel = table.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn2<TimeLocalityObj, Void> indexCol = new TableColumn2<>("#");
        indexCol.setCellFactory( col -> {
            TableCell<TimeLocalityObj, Void> cell = new TableCell<>();
            StringBinding binding = Bindings.createStringBinding(()->{
                int ind = cell.getIndex();
                return ind >= 0 && ind < table.getItems().size() ? Integer.toString(ind+1) : "";
            }, cell.indexProperty(), table.getItems());
            cell.textProperty().bind(binding);
            return cell;
        });
        indexCol.setSortable(false);
        indexCol.setPrefWidth(25);
        TableColumn2<TimeLocalityObj, String> typeCol = createColumn("類型", 75, new PropertyValueFactory<>("type"));
        TableColumn2<TimeLocalityObj, String> rangeCol = createColumn("時間(範圍)", 200, new PropertyValueFactory<>("dateTimeRange"));
        TableColumn2<TimeLocalityObj, String> locCol = createColumn("地點", 320, new PropertyValueFactory<>("location"));
        TableColumn2<TimeLocalityObj, String> reserveCol = createColumn("備註", 250, new PropertyValueFactory<>("reserve"));
        TableColumn2<TimeLocalityObj, Void> optCol = new TableColumn2<>("編輯");//createColumn("功能", 200, new PropertyValueFactory<>("type"));
        optCol.setPrefWidth(130);
        optCol.setCellFactory(col -> new TableCell<TimeLocalityObj, Void>() {
            private final HBox hb = new HBox();
            private final JFXButton btnEdit = new JFXButton("編輯");
            private final JFXButton btnDelete = new JFXButton("刪除");
            private int getSelectedRowIndex() {//switch to the current row
                int index = getIndex();
                table.getSelectionModel().select(index);
                return index;
            }
            {
                hb.setSpacing(10);
                btnEdit.setStyle("-fx-background-color: pass");
                btnEdit.setOnAction(event -> {
                    int index = getSelectedRowIndex();
                    optPane.setVisible(true);
                    setUIs(table.getItems().get(index), true);
                    show();
                });
                btnDelete.setStyle("-fx-background-color: warning");
                btnDelete.setOnAction(event -> {
                    optPane.setVisible(false);
                    int index = getSelectedRowIndex();
                    TimeLocalityObj obj = table.getItems().get(index);
                    CriminalCaseCache.getInstance().getCriminalCaseObj().getTimeLocalities().remove(obj);
                    table.getItems().remove(index);
                });
                hb.getChildren().addAll(btnEdit,btnDelete);
            }
            @Override
            public void updateItem(Void item, boolean empty){
                super.updateItem(item, empty);
                int index = getIndex();
                int last = table.getItems().size() - 1;
                if(index > last || empty)
                    setGraphic(null);
                else
                    setGraphic(hb);
            }
        });

        table.getColumns().clear();
        table.getColumns().addAll(indexCol, typeCol, rangeCol, locCol, reserveCol, optCol);
        table.setPrefWidth(1000);
        table.setPrefHeight(200);
    }

    public void doSubmit() {
        CriminalCaseObj criminalCaseObj = CriminalCaseCache.getInstance().getCriminalCaseObj();
        Long criminalCaseId = criminalCaseObj.getId();
        assert criminalCaseId != null;
        if (updateMode) {
            TimeLocalityObj obj = table.getSelectionModel().getSelectedItem();
            updateObjByUIs(obj);
            table.refresh();
        } else {
            TimeLocalityObj obj = new TimeLocalityObj();
            updateObjByUIs(obj);
            obj.setCriminalCaseId(criminalCaseId);
            criminalCaseObj.getTimeLocalities().add(obj);
            table.getItems().add(obj);
        }
        btnSubmit.setText("新增");
        updateMode = false;
    }

    public void setUIs(TimeLocalityObj target, boolean updateMode) {
        if (target != null) {
            comboType.setValue(target.getType());
            begin.setDateTime(target.getStime());
            LocalDateTime endDateTime = target.getEtime();
            checkBoxTimeRange.setSelected(endDateTime != null);
            end.setDateTime(endDateTime);
            location.setText(target.getLocation());
            explanation.setText(target.getReserve());
        } else {
            clearUIs();
        }
        this.updateMode = updateMode;
        btnSubmit.setText( updateMode ? "修改" : "新增");
    }

    public void clearUIs() {
        comboType.setValue("");
        begin.setDateTime(LocalDateTime.now());
        checkBoxTimeRange.setSelected(false);
        end.setDateTime(LocalDateTime.now());
        location.setText(null);
        explanation.setText(null);
    }

    public void updateObjByUIs(@NotNull TimeLocalityObj target) {
        target.setType(comboType.getValue().toString());
        target.setStime(begin.getDateTime());
        end.setValid(checkBoxTimeRange.isSelected());
        LocalDateTime endDT = end.getDateTime();
        target.setEtime(endDT);
        target.setLocation(location.getText());
        target.setReserve(explanation.getText());
    }

//    public Node getRoot() { return root; }

    public Pane getOptPane() { return optPane; }

    public TableView2 getTable() {return table; }

//    public JFXButton getBtnClose() { return btnClose; }

//    public JFXButton getBtnSubmit() { return btnSubmit; }
}
