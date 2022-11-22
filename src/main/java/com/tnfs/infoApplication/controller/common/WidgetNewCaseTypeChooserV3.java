/*
 * javafx setClassStyle
 * ref: https://www.itread01.com/p/664210.html
 * ref: https://www.itread01.com/content/1544828228.html
 * ref: https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html
 * */

package com.tnfs.infoApplication.controller.common;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.tnfs.infoApplication.cache.CaseTypeCache;
import com.tnfs.infoApplication.cache.CommonCache;
import com.tnfs.infoApplication.model.CaseTypeObj;
import com.tnfs.infoApplication.task.CaseTypesLoadingTask;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.*;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.AutoCompletionBinding;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class WidgetNewCaseTypeChooserV3 implements Initializable {
    private List<CaseTypeObj> caseTypeList = new ArrayList<>();

    @FXML protected JFXButton btnCreate;
    @FXML private FlowPane pane;
    @FXML protected JFXButton btnSubmit, btnClose;
    @FXML private JFXTextField tfCaseTypePrimary, tfCaseTypeSecond;
    @FXML private Pane optPane;

    private Stage stage = null, owner = null;
    private Point2D initPos = null;
    private EventHandler<MouseEvent> mousePressedEventHandler = null;
    private EventHandler<MouseEvent> mouseDraggedEventHandler = null;
    private AutoCompletionBinding<CaseTypeObj> autoBindSecond;
    private Node root = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // List Selected CaseTypes
        initListPane();
        // Choose
        initChooser();
    }

    public void show() {
        if (stage == null) {
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            if (owner != null) {
                stage.initOwner(owner);
            }
//            System.out.println("optPane(W): "+optPane.getPrefWidth());
//            System.out.println("optPane(H): "+optPane.getPrefHeight());

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

    public WidgetNewCaseTypeChooserV3() {
        URL loc = CommonCache.getInstance().getCaseTypeChooserURL();
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

    public void initListPane() {
        btnCreate.setText("");
        SVGPath iconPlus = new SVGPath();
        iconPlus.setContent("M16 6h-6v-6h-4v6h-6v4h6v6h4v-6h6z");
        iconPlus.setScaleY(0.8);
        iconPlus.setStyle("-fx-fill: info;");
        btnCreate.setGraphic(iconPlus);
        btnCreate.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    public void updateListPane() {
        ObservableList<Node> observableList = pane.getChildren();
        observableList.clear();
        if (caseTypeList != null && caseTypeList.size() > 0) {
            for (CaseTypeObj castType : caseTypeList) {
                JFXButton btnDelete = new JFXButton(castType.getName());
                SVGPath svgpath = new SVGPath();
                svgpath.setStyle("-fx-fill: red;");
                svgpath.setContent("M0 6h16v4h-16z");
                btnDelete.setGraphic(svgpath);
                btnDelete.setOnAction(event -> {
                    caseTypeList.remove(castType);
                    observableList.remove(btnDelete);
                });
                observableList.add(btnDelete);
            }
        }
        observableList.add(btnCreate);
    }

    public void setUIs() {
        tfCaseTypePrimary.setText("");
        tfCaseTypeSecond.setText("");
        setAutoCompleteTFPrimary();
        setAutoCompleteTFSecondary();
        setUIofAutoCompleteTFSecondary(true);
    }

    public void initChooser() {
        // Binding Catalog Info to AutoComplete Tools
        ChangeListener<String> textListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null && tfCaseTypeSecond.getText().length() > 0) {
                    tfCaseTypeSecond.setText("");
                }
            }
        };
        WeakChangeListener<String> weakTextListener = new WeakChangeListener<>(textListener);
        tfCaseTypePrimary.textProperty().addListener(weakTextListener);
        btnCreate.setOnAction(event -> {
            btnCreate.setVisible(false);
            setUIs();
            show();
        });
        btnSubmit.setOnAction(event -> {
            addCaseType();
            hide();
            btnCreate.setVisible(true);
        });
        btnClose.setOnAction(event -> {
            hide();
            btnCreate.setVisible(true);
        });
    }

    public void setUIofAutoCompleteTFSecondary(boolean useWorker) {
        if (CaseTypeCache.getInstance().getSize() == 0 && useWorker) {
            startLoadingWorker();
        }
    }

    public void setAutoCompleteTFPrimary() {
        AutoCompletionBinding<String> autoBind = new AutoCompletionTextFieldBinding<String>(tfCaseTypePrimary, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<String>>() {
            private final Object possibleSuggestionsLock = new Object();
            @Override
            public Collection<String> call(AutoCompletionBinding.ISuggestionRequest request) {
                List<String> ret = new ArrayList<>();
                synchronized (possibleSuggestionsLock) {
                    for (String typeName : CaseTypeObj.getTypes()) {
                        if(isMatch(typeName, request)){
                            ret.add(typeName);
                        }
                    }
                }
                return ret;
            }
            protected boolean isMatch(String suggestion, AutoCompletionBinding.ISuggestionRequest request) {
                String userTextLower = request.getUserText().toLowerCase();
                String suggestionStr = suggestion.toLowerCase();
                return suggestionStr.contains(userTextLower) && !suggestionStr.equals(userTextLower);
            }
        });
        autoBind.getCompletionTarget().setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                autoBind.setUserInput(""); // set the newText to trigger "addListener" of userInput
                autoBind.getAutoCompletionPopup().show(autoBind.getCompletionTarget());
            }
        });
    }

    public void setAutoCompleteTFSecondary() {
        Callback<AutoCompletionBinding.ISuggestionRequest, Collection<CaseTypeObj>> callback = new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<CaseTypeObj>>() {
            private final Object possibleSuggestionsLock = new Object();
            @Override
            public Collection<CaseTypeObj> call(AutoCompletionBinding.ISuggestionRequest request) {
                List<CaseTypeObj> ret = new ArrayList<>();
                String primarySelected = tfCaseTypePrimary.getText();
                int typeId = primarySelected.equals("") ? -1 : CaseTypeObj.getTypes().indexOf(primarySelected);
                List<CaseTypeObj> list = CaseTypeCache.getInstance().getCaseTypes();
                synchronized (possibleSuggestionsLock) {
                    for (CaseTypeObj possibleSuggestion : list) {
                        // (1) belongs to none or selected-primary-type && (2) not-selected before && (3) match caseTypeName
                        if( (typeId == -1 || possibleSuggestion.isType(typeId)) && !caseTypeList.contains(possibleSuggestion) && isMatch(possibleSuggestion.getName(), request)){
                            ret.add(possibleSuggestion);
                        }
                    }
                }
                return ret;
            }
            protected boolean isMatch(String suggestion, AutoCompletionBinding.ISuggestionRequest request) {
                String userTextLower = request.getUserText().toLowerCase();
                String suggestionStr = suggestion.toLowerCase();
                return suggestionStr.contains(userTextLower) && !suggestionStr.equals(userTextLower);
            }
        };
        autoBindSecond = new AutoCompletionTextFieldBinding<CaseTypeObj>(tfCaseTypeSecond, callback);
        autoBindSecond.getCompletionTarget().setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                autoBindSecond.setUserInput(""); // set the newText to trigger "addListener" of userInput
                autoBindSecond.getAutoCompletionPopup().show(autoBindSecond.getCompletionTarget());
            }
        });
    }

    public void addCaseType() {
        String secondarySelected = tfCaseTypeSecond.getText();
        Notifications notifications = Notifications.create()
                .graphic(CommonCache.getInstance().getViewCancel())
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .owner(owner);
        boolean showNotification = true;
        if (caseTypeList.stream().anyMatch(obj -> secondarySelected.equals(obj.getName()))) { // has existed
            notifications = notifications.text(secondarySelected+"-已選取");
        } else if (secondarySelected.trim().length() == 0) { // is empty
            notifications = notifications.text("無效輸入：次要類別不可為空");
        } else if (caseTypeList.size() == 4) { // exceed the max elements required
            notifications = notifications.text("禁止新增過多類別");
        } else {
            CaseTypeObj objSelected = CaseTypeCache.getInstance().getCaseTypeObj(secondarySelected);
            caseTypeList.add(objSelected);
            showNotification = false;
        }
        tfCaseTypePrimary.setText("");
        tfCaseTypeSecond.setText("");
        if (showNotification) {
            notifications.show();
        }
        updateListPane();
    }

    public List<CaseTypeObj> getCaseTypeList() {
        return caseTypeList;
    }

    public void setCaseTypeList(List<CaseTypeObj> list) {
        caseTypeList = list;
    }

    public void clear() {
        caseTypeList.clear();
    }

    public FlowPane getListPane() { return pane; }

    public Pane getOptPane() { return optPane; }

    public JFXButton getBtnSubmit() { return btnSubmit; }

    public JFXButton getBtnClose() { return btnClose; }

    public JFXButton getBtnCreate() { return btnCreate; }

    public void startLoadingWorker() {
        Notifications notifications = Notifications.create()
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(event -> {})
                .owner(owner);
        CaseTypesLoadingTask caseTypesLoadingTask = new CaseTypesLoadingTask(null);
        caseTypesLoadingTask.setOnSucceeded(e-> {
            int status = caseTypesLoadingTask.getStatus();
            if (status >= 200 && status < 300) {
                List<CaseTypeObj> objs = caseTypesLoadingTask.getValue();
                CaseTypeCache.getInstance().addAll(objs);
                setUIofAutoCompleteTFSecondary(false);
            }
            notifications.text(String.format("載入案類代號(共%d類)",CaseTypeCache.getInstance().getSize())).graphic(CommonCache.getInstance().getViewOk()).show();
        });
        caseTypesLoadingTask.setOnFailed(e -> {
            notifications.text("連線失敗").graphic(CommonCache.getInstance().getViewCancel()).show();
        });
        Thread thread = new Thread(caseTypesLoadingTask);
        thread.setDaemon(true);
        thread.start();
    }
}
