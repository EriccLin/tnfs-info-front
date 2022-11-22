package com.tnfs.infoApplication.controller.common;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.istack.internal.NotNull;
import com.tnfs.infoApplication.cache.CommonCache;
import com.tnfs.infoApplication.cache.PersonCache;
import com.tnfs.infoApplication.model.PersonObj;
import com.tnfs.infoApplication.task.PersonSavingTask;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class WidgetPerson implements Initializable {

    @FXML private JFXComboBox comboGender;
    @FXML private JFXTextField tfName, tfIdNumber;
    @FXML private JFXCheckBox chBoxIsDead;
    @FXML private JFXButton btnSubmit, btnClose;
    @FXML private HBox hBoxBirthdate;
    @FXML private Pane optPane;

    private Stage stage = null, owner = null;
    private Point2D initPos = null;
    private Node root = null;
    private Long personId;

    private EventHandler<MouseEvent> mousePressedEventHandler = null;
    private EventHandler<MouseEvent> mouseDraggedEventHandler = null;
    private WidgetDateTime birthdate = new WidgetDateTime();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        birthdate.setTimeBlock(false);
        hBoxBirthdate.getChildren().clear();
        hBoxBirthdate.getChildren().add(birthdate.getRoot());
        comboGender.getItems().addAll("男","女","不明");
        comboGender.setValue("男");

        btnSubmit.setOnAction(event -> {
            PersonObj obj = new PersonObj();
            updateObjByUIs(obj);
            startSavingWorker(obj);
            hide();
        });
        btnClose.setOnAction(event -> {
            hide();
        });
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
                        if (deltaX * deltaX + deltaY * deltaY > 9) {
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

    public WidgetPerson() {
        URL loc = CommonCache.getInstance().getPersonFxmlURL();
        FXMLLoader fxmlLoader = new FXMLLoader(loc);
        try {
            fxmlLoader.setController(this);
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setOwner(Stage owner) {
        this.owner = owner;
    }

    public void setUIs(PersonObj personObj) {
        if (personObj != null) {
            personId = personObj.getId();
            tfName.setText(personObj.getName());
            tfIdNumber.setText(personObj.getIdNumber());
            comboGender.setValue(personObj.getGender());
            LocalDate ldBirthdate = personObj.getBirthdate();
            if (ldBirthdate != null) {
                LocalDateTime dtBirthdate = LocalDateTime.of(ldBirthdate, LocalTime.of(0,0));
                birthdate.setDateTime(dtBirthdate);
            } else {
                birthdate.setDateTime(LocalDateTime.now());
            }
            boolean isDead = personObj.getIsActive() != null && !personObj.getIsActive();
            chBoxIsDead.setSelected(isDead);
        } else {
            clearUIs();
        }
        btnSubmit.setText(personId!=null ? "修改" : "新增");
    }

    public void clearUIs() {
        tfName.setText("");
        tfIdNumber.setText("");
        comboGender.setValue("");
        birthdate.setDateTime(LocalDateTime.now());
        chBoxIsDead.setSelected(false);
    }

    public void updateObjByUIs(@NotNull PersonObj target) {
        target.setId(personId);
        target.setName(tfName.getText());
        target.setIdNumber(tfIdNumber.getText());
        target.setGender(comboGender.getValue().toString());
        LocalDateTime ldtBirthdate = birthdate.getDateTime();
        if (ldtBirthdate != null) {
            target.setBirthdate(ldtBirthdate.toLocalDate());
        } else {
            target.setBirthdate(null);
        }
        target.setIsActive(!chBoxIsDead.isSelected());
    }

    public void setPersonId(Long id) { personId = id; }

    public Long getPersonId() { return personId; }

//    public PersonObj getPersonObj() {
//        return PersonCache.getInstance().getPersonObj(personId);
//    }

//    public String getName() { return tfName.getText();}

    public Pane getOptPane() { return optPane; }

//    public JFXButton getBtnClose() { return btnClose; }

//    public JFXButton getBtnSubmit() { return btnSubmit; }

//    public void startLoadingWorkerWithPersonId(Long id) {
//        PersonLoadingTask personLoadingTask = new PersonLoadingTask();
//        personLoadingTask.usePersonId(id);
//        startLoadingWorker(personLoadingTask);
//    }

//    public void startLoadingWorkerWithPersonIdNumber(String idNumber) {
//        PersonLoadingTask personLoadingTask = new PersonLoadingTask();
//        personLoadingTask.usePersonIdNumber(idNumber);
//        startLoadingWorker(personLoadingTask);
//    }

//    private void startLoadingWorker(PersonLoadingTask personLoadingTask) {
//        Notifications notifications = Notifications.create()
//                .hideAfter(Duration.seconds(3))
//                .position(Pos.BOTTOM_RIGHT)
//                .owner(owner);
//        personLoadingTask.setOnSucceeded(e-> {
//            List<PersonObj> objs = new ArrayList<>();
//            int status = personLoadingTask.getStatus();
//            if (status >= 200 && status < 300) {
//                objs = personLoadingTask.getValue();
//                PersonCache.getInstance().addAll(objs);
//                String idNumber = tfIdNumber.getText();
//                PersonObj obj = null;
//                obj = personId == null ?
//                        PersonCache.getInstance().getPersonObj(idNumber) :
//                        PersonCache.getInstance().getPersonObj(personId);
//                if (obj != null) { // 如果這個idNumber是有效的
//                    setUIs(obj);
//                } else { // 如果這個idNumber是無效的
//                    obj = new PersonObj();
//                    obj.setIdNumber(idNumber);
//                    setUIs(obj);
//                }
//            }
//        });
//        personLoadingTask.setOnFailed(e -> {
//            notifications.text("連線失敗").graphic(imageViewCancel).show();
//        });
//        Thread thread = new Thread(personLoadingTask);
//        thread.setDaemon(true);
//        thread.start();
//    }

    public void startSavingWorker(@NotNull PersonObj target) {
        PersonSavingTask personSavingTask = new PersonSavingTask(target);
        Notifications notifications = Notifications.create()
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .owner(owner);
        personSavingTask.setOnSucceeded(e-> {
            int status = personSavingTask.getStatus();
            if ( status >= 200 && status < 300) {
                PersonObj obj = personSavingTask.getValue();
                PersonCache.getInstance().add(obj);
                setUIs(obj);
            } else {
                notifications.text(String.format("儲存Person:%s:%d失敗",target.getName(),target.getId())).graphic(CommonCache.getInstance().getViewCancel()).show();
            }
        });
        personSavingTask.setOnFailed(e -> {
            notifications.text("連線失敗").graphic(CommonCache.getInstance().getViewCancel()).show();
        });
        Thread thread = new Thread(personSavingTask);
        thread.setDaemon(true);
        thread.start();
    }
}
