package com.tnfs.infoApplication.controller.common;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.istack.internal.NotNull;
import com.tnfs.infoApplication.cache.CommonCache;
import com.tnfs.infoApplication.cache.CriminalCaseCache;
import com.tnfs.infoApplication.cache.PersonCache;
import com.tnfs.infoApplication.model.CriminalCaseObj;
import com.tnfs.infoApplication.model.PersonInvolvedObj;
import com.tnfs.infoApplication.model.PersonObj;
import com.tnfs.infoApplication.task.PersonLoadingTask;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.tableview2.TableColumn2;
import org.controlsfx.control.tableview2.TableView2;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.tnfs.infoApplication.util.MyUtil.createColumn;

public class WidgetNewPersonInvolved implements Initializable {
    @FXML private JFXCheckBox chkBoxUnknown, chkBoxDead, chkBoxForeigner;
    @FXML private JFXComboBox<String> comboType;
    @FXML private JFXTextField tfCountry;
    @FXML private JFXTextField tfIdNumber;
    @FXML private JFXTextField tfReserved;
    @FXML private JFXButton btnSubmit, btnClose, btnPersonInfo;
    @FXML private Label labelPerson;
    @FXML private Pane optPane;
    @FXML private TableView2<PersonInvolvedObj> table;

    private Stage stage = null, owner = null;
    private Point2D initPos = null;
    private Node root = null;
    private boolean updateMode = false;
    private EventHandler<MouseEvent> mousePressedEventHandler = null;
    private EventHandler<MouseEvent> mouseDraggedEventHandler = null;
    private WidgetPerson widgetPerson = new WidgetPerson();

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
                // owner represent the mainframe of the whole application.
                widgetPerson.setOwner(owner);
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

    public WidgetNewPersonInvolved() {
        URL loc = CommonCache.getInstance().getPersonInvolvedFxmlURL();
        FXMLLoader fxmlLoader = new FXMLLoader(loc);
        try {
            fxmlLoader.setController(this);
            root = fxmlLoader.load();
            ((Pane)optPane.getParent()).getChildren().remove(optPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setOwner(Stage owner) {
        this.owner = owner;
    }

    private void initOptPane() {
        comboType.getItems().addAll(FXCollections.observableArrayList(PersonInvolvedObj.getTypes()));
        chkBoxUnknown.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue && chkBoxDead.isSelected()) { chkBoxDead.setSelected(false); }
        });
        chkBoxDead.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue && chkBoxUnknown.isSelected()) { chkBoxUnknown.setSelected(false); }
        });
        chkBoxForeigner.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) { tfCountry.setVisible(newValue); }
        });
        comboType.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if ("被害人".equals(newValue)) {
                    chkBoxUnknown.setVisible(true);
                    chkBoxDead.setVisible(true);
                } else {
                    chkBoxUnknown.setVisible(false);
                    chkBoxDead.setVisible(false);
                    chkBoxUnknown.setSelected(false);
                    chkBoxDead.setSelected(false);
                }
                if ("死者".equals(newValue)) {
                    chkBoxDead.setVisible(true);
                } else {
                    chkBoxDead.setVisible(false);
                    chkBoxDead.setSelected(false);
                }
            }
        });


        chkBoxUnknown.setSelected(false);
        chkBoxDead.setSelected(false);
        chkBoxForeigner.setSelected(false);
        tfCountry.setVisible(false);
        comboType.setValue("報案人");
        btnPersonInfo.setOnAction(event -> {
            widgetPerson.show();
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
        TableView.TableViewSelectionModel<PersonInvolvedObj> selectionModel = table.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn2<PersonInvolvedObj, Void> indexCol = new TableColumn2<>("#");
        indexCol.setCellFactory( col -> {
            TableCell<PersonInvolvedObj, Void> cell = new TableCell<>();
            StringBinding binding = Bindings.createStringBinding(()->{
                int ind = cell.getIndex();
                return ind >= 0 && ind < table.getItems().size() ? Integer.toString(ind+1) : "";
            }, cell.textProperty(), table.getItems());
            cell.textProperty().bind(binding);
            return cell;
        });
        indexCol.setSortable(false);
        indexCol.setPrefWidth(25);
        TableColumn2<PersonInvolvedObj, String> typeCol = createColumn("關係", 75, new PropertyValueFactory<>("type"));
        TableColumn2<PersonInvolvedObj, String> nameCol = createColumn("姓名", 100, new PropertyValueFactory<>("name"));
        TableColumn2<PersonInvolvedObj, String> idNumberCol = createColumn("身分證號", 150, new PropertyValueFactory<>("idNumber"));
        TableColumn2<PersonInvolvedObj, String> birthdateCol = createColumn("生日", 100, new PropertyValueFactory<>("mingGougDate"));
        TableColumn2<PersonInvolvedObj, String> reserveCol = createColumn("備註", 250, new PropertyValueFactory<>("reserve"));
        TableColumn2<PersonInvolvedObj, Void> optCol = new TableColumn2<>("編輯");//createColumn("功能", 200, new PropertyValueFactory<>("type"));
        optCol.setPrefWidth(130);
        optCol.setCellFactory(col -> new TableCell<PersonInvolvedObj, Void>() {
            private final HBox hb = new HBox();
            private final JFXButton btnEdit = new JFXButton(("編輯"));
            private final JFXButton btnDelete = new JFXButton("刪除");
            private int getSelectedRowIndex() { //switch to the current row
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
                    int index = getSelectedRowIndex();
                    optPane.setVisible(false);
                    PersonInvolvedObj obj = table.getItems().get(index);
                    CriminalCaseCache.getInstance().getCriminalCaseObj().getPersonInvolveds().remove(obj);
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
        table.getColumns().addAll(indexCol, typeCol, nameCol, idNumberCol, birthdateCol, reserveCol, optCol);
        table.setPrefHeight(200);
        table.setPrefWidth(1000);

    }

    public void doSubmit() {
        CriminalCaseObj criminalCaseObj = CriminalCaseCache.getInstance().getCriminalCaseObj();
        Long criminalCaseId = criminalCaseObj.getId();
        if (updateMode) {
            PersonInvolvedObj obj = table.getSelectionModel().getSelectedItem();
            updateObjByUIs(obj);
            table.refresh();
        } else {
            PersonInvolvedObj obj = new PersonInvolvedObj();
            updateObjByUIs(obj);
            obj.setCriminalCaseId(criminalCaseId);
            if (criminalCaseObj.getPersonInvolveds().contains(obj)) {
                Notifications notifications = Notifications.create()
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.BOTTOM_RIGHT)
                        .owner(owner);
                notifications.text("案件關係人("+obj.getName()+")只允許單一種身分").graphic(CommonCache.getInstance().getViewCancel()).show();
            } else {
                criminalCaseObj.getPersonInvolveds().add(obj);
                table.getItems().add(obj);
            }
        }
        updateMode = false;
    }

    public void setUIofPerson(@NotNull PersonObj target, boolean useWorker) {
        Long personId = target.getId();
        String idNumber = target.getIdNumber();
        PersonObj obj = null;
        if (personId != null) {
            obj = PersonCache.getInstance().getPersonObj(personId);
            if (obj == null && useWorker) {
                PersonLoadingTask personLoadingTask = new PersonLoadingTask();
                personLoadingTask.usePersonId(personId);
                startPersonLoadingWorker(personLoadingTask);
            }
        } else {
            obj = PersonCache.getInstance().getPersonObj(idNumber);
            if (obj == null && useWorker) {
                PersonLoadingTask personLoadingTask = new PersonLoadingTask();
                personLoadingTask.usePersonIdNumber(idNumber);
                startPersonLoadingWorker(personLoadingTask);
            }
        }
        btnPersonInfo.setText((obj != null ? "修改" : "新增") + "年籍資料");
        if (obj != null) {
            labelPerson.setText(obj.getPersonInfo());
            if (!obj.getIsActive()) {
                labelPerson.getStyleClass().add("my-alert-simple");
            }
            tfIdNumber.setText(obj.getIdNumber());
        } else {
            widgetPerson.setUIs(obj);
            tfIdNumber.setText("");
        }
    }

    public void setUIs(PersonInvolvedObj target, boolean updateMode) {
        clearUIs();
        if (target != null) {
            String idNumber = target.getIdNumber();
            tfIdNumber.setText(idNumber);
            comboType.setValue(target.getType());
            tfReserved.setText(target.getReserve());
            tfCountry.setText(target.getCountry());
            if (target.getCountry() == null || target.getCountry().length() == 0) {
                chkBoxForeigner.setSelected(false);
            }
            boolean isUnknown = !(target.getIsUnknown() == null || !target.getIsUnknown());
            boolean isActive = target.getIsActive() == null || target.getIsActive();
            chkBoxDead.setSelected(!isActive);
            chkBoxUnknown.setSelected(isUnknown);
            PersonObj obj = new PersonObj();
            obj.setId(target.getPersonId());
            obj.setIdNumber(idNumber);
            setUIofPerson(obj, true);
        }
        this.updateMode = updateMode;
    }

    public void clearUIs() {
        comboType.setValue("報案人");
        chkBoxUnknown.setSelected(false);
        chkBoxDead.setSelected(false);
        chkBoxForeigner.setSelected(false);
        tfCountry.setVisible(false);
        tfCountry.setText("");
        tfIdNumber.setText("");
        tfReserved.setText("");
        labelPerson.setText("");
        labelPerson.getStyleClass().remove("my-alert-simple");
        widgetPerson.clearUIs();
        btnSubmit.setText("新增");
    }

    public void updateObjByUIs(@NotNull PersonInvolvedObj target) {
        Long personId = widgetPerson.getPersonId();
        PersonObj personObj = PersonCache.getInstance().getPersonObj(personId);
        target.setPersonId(personId);
        target.setName(personObj.getName());
        target.setBirthdate(personObj.getBirthdate());
        target.setIsActive(personObj.getIsActive());
        target.setType(comboType.getValue().toString());
        target.setIdNumber(tfIdNumber.getText());
        target.setReserve(tfReserved.getText());
    }

//    public Node getRoot() { return root; }

    public Pane getOptPane() { return optPane; }

    public TableView2 getTable() { return table; }

//    public Pane getPersonOptPane() { return widgetPerson.getOptPane(); }

//    public JFXButton getBtnClose() { return btnClose; }

//    public JFXButton getBtnSubmit() { return btnSubmit; }

    private void startPersonLoadingWorker(PersonLoadingTask personLoadingTask) {
        Notifications notifications = Notifications.create()
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .owner(owner);
        personLoadingTask.setOnSucceeded(e-> {
            List<PersonObj> objs = new ArrayList<>();
            int status = personLoadingTask.getStatus();
            if (status >= 200 && status < 300) {
                objs = personLoadingTask.getValue();
                PersonCache.getInstance().addAll(objs);
                PersonObj obj = null;
                if (objs.size() == 1) {
                    obj = objs.get(0);
                    widgetPerson.setUIs(obj);
                    setUIofPerson(obj, false);
                }
            }
        });
        personLoadingTask.setOnFailed(e -> {
            notifications.text("連線失敗").graphic(CommonCache.getInstance().getViewCancel()).show();
        });
        Thread thread = new Thread(personLoadingTask);
        thread.setDaemon(true);
        thread.start();
    }
}
