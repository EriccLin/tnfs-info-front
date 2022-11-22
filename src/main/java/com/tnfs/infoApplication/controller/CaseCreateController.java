package com.tnfs.infoApplication.controller;

import com.jfoenix.controls.JFXTabPane;
import com.tnfs.infoApplication.cache.CriminalCaseCache;
import com.tnfs.infoApplication.model.CriminalCaseObj;
import com.tnfs.infoApplication.task.CriminalCaseLoadingTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.ResourceBundle;

public class CaseCreateController implements Initializable {

    @FXML private VBox contentView;
    @FXML private Tab tabCaseBasic, tabCaseInvestigation, tabCaseRecord;
    @FXML private JFXTabPane tabPane;
    private Stage stage = null;
    private CriminalCaseLoadingTask criminalCaseLoadingTask = null;

    CaseBasicController caseBasicController = new CaseBasicController();
    CaseInvestigationController caseInvestigationController = new CaseInvestigationController();
    CaseRecordController caseRecordController = new CaseRecordController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Node nodeCaseBasic = caseBasicController.getRoot();
        nodeCaseBasic.managedProperty().bind(nodeCaseBasic.visibleProperty());
        nodeCaseBasic.setVisible(true);

        Node nodeCaseInvestigation = caseInvestigationController.getRoot();
        nodeCaseInvestigation.managedProperty().bind(nodeCaseInvestigation.visibleProperty());
        nodeCaseInvestigation.setVisible(true);

        Node nodeCaseRecord = caseRecordController.getRoot();
        nodeCaseRecord.managedProperty().bind(nodeCaseRecord.visibleProperty());
        nodeCaseRecord.setVisible(true);

        tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue != null && newValue != oldValue) {
                    Integer index = newValue.intValue();
                    Integer sz = getContentView().getChildren().size();
                    if (sz > 2) {
                        getContentView().getChildren().remove(sz - 1);
                    }
                    switch (index) {
                        case 1:
                            getContentView().getChildren().add(nodeCaseInvestigation);
                            break;
                        case 2:
                            getContentView().getChildren().add(nodeCaseRecord);
                            break;
                        case 0:
                        default:
                            getContentView().getChildren().add(nodeCaseBasic);
                    }
                }
            }
        });
        getContentView().getChildren().add(nodeCaseBasic);
    }

    public CaseCreateController(Stage stage) {
        setStage(stage);
        startLoadingWorker();
    }

    public CaseCreateController() {
        this(null);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        caseBasicController.setStage(stage);
    }

    public void startLoadingWorker() {
        criminalCaseLoadingTask = new CriminalCaseLoadingTask(1L);
        Notifications notifications = Notifications.create()
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(event -> {});
        criminalCaseLoadingTask.setOnSucceeded(e->{
            int status = criminalCaseLoadingTask.getStatus();
            if ( status >= 200 && status < 300) {
                CriminalCaseCache caseCache = CriminalCaseCache.getInstance();
                CriminalCaseCache.getInstance().setCriminalCaseObj(criminalCaseLoadingTask.getValue());
                caseBasicController.setCriminalCaseObj(caseCache.getCriminalCaseObj());
                caseBasicController.setUIs();
            } else {
                CriminalCaseCache.getInstance().setCriminalCaseObj(new CriminalCaseObj());
                URL loc = getClass().getResource("../image/cancel-64.png");
                ImageView imageView = new ImageView(loc.toString());
                notifications.text(String.format("載入失敗{id=%d}",1L)).graphic(imageView).show();
            }
        });
        criminalCaseLoadingTask.setOnFailed(e -> {
            URL loc = getClass().getResource("../image/cancel-64.png");
            ImageView imageView = new ImageView(loc.toString());
            notifications.text("連線失敗").graphic(imageView).show();
        });
        Thread thread = new Thread(criminalCaseLoadingTask);
        thread.start();
    }

    public void stopLoadingWorker() {
        if (criminalCaseLoadingTask != null && criminalCaseLoadingTask.isRunning()) {
            criminalCaseLoadingTask.cancel();
            CriminalCaseCache.getInstance().setCriminalCaseObj(new CriminalCaseObj());
        }
    }

    public VBox getContentView() {return contentView; }
}
