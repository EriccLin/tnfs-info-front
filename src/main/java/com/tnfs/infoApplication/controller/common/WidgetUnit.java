/*
 * javafx setClassStyle
 * ref: https://www.itread01.com/p/664210.html
 * ref: https://www.itread01.com/content/1544828228.html
 * ref: https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html
 * */

package com.tnfs.infoApplication.controller.common;

import com.jfoenix.controls.*;
import com.sun.istack.internal.NotNull;
import com.tnfs.infoApplication.cache.CommonCache;
import com.tnfs.infoApplication.cache.UnitCache;
import com.tnfs.infoApplication.model.UnitObj;
import com.tnfs.infoApplication.task.UnitLoadingTask;
import com.tnfs.infoApplication.task.UnitSavingTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WidgetUnit implements Initializable {
    @FXML private JFXTextField tfName;
    @FXML private Label labelSuperviserName, labelSimpleName;
    @FXML private JFXButton btnCreateSubmit, btnCreateClose;
    @FXML private JFXButton btnSubmit, btnCancel;
    @FXML private VBox showPane;
    @FXML private Pane optCreatePane;
    @FXML private Pane mainPane;
    @FXML private Pane initPane;
    @FXML private Label labelWarning;

    private Long unitId = null;
    private VBox trackCreatePane = new VBox();
    private VBox vBox = new VBox();
    private Node root;
    private Popup popup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelWarning.managedProperty().bind(labelWarning.visibleProperty());
        labelWarning.setVisible(false);
        optCreatePane.managedProperty().bind(optCreatePane.visibleProperty());
        optCreatePane.setVisible(false);
        btnCreateSubmit.setOnAction(event -> {
            UnitObj target = new UnitObj();
            if (doValidation()) {
                updateObjByUIs(target);
                startUnitSavingWorker(target);
                optCreatePane.setVisible(false);
            }
        });
        btnCreateClose.setOnAction(event -> {
            optCreatePane.setVisible(false);
            UnitObj unitObj = UnitCache.getInstance().getUnitObj(unitId);
            setUIs(unitObj);
        });
        btnSubmit.setOnAction(event -> {
            if (!popup.isShowing()) {
                Bounds bd = btnSubmit.localToScene(btnSubmit.getBoundsInLocal());
                Point2D pt = new Point2D(bd.getMaxX(), bd.getMaxY());
                System.out.println("x:" + btnSubmit.getScene().getWindow().getX() + ",y:" + btnSubmit.getScene().getWindow().getY());
                popup.show(btnSubmit, btnSubmit.getScene().getWindow().getX() + pt.getX(), btnSubmit.getScene().getWindow().getY() + pt.getY());
            } else {
                popup.hide();
            }
        });
        SVGPath svgPath = new SVGPath();
        svgPath.setContent("M10 2.667c-0.369 0-0.667 0.299-0.667 0.667s0.298 0.667 0.667 0.667h1.057l-2.195 2.195c-0.261 0.261-0.261 0.682 0 0.943 0.13 0.13 0.301 0.195 0.471 0.195s0.341-0.065 0.471-0.195l2.195-2.195v1.057c0 0.368 0.298 0.667 0.667 0.667s0.667-0.299 0.667-0.667v-3.333h-3.333zM6.195 8.862l-2.195 2.195v-1.057c0-0.368-0.298-0.667-0.667-0.667s-0.667 0.299-0.667 0.667v3.333h0.664c0.005 0 2.669 0.001 2.669 0.001 0.368 0 0.667-0.299 0.667-0.667s-0.298-0.667-0.667-0.667h-1.057l2.195-2.195c0.261-0.261 0.261-0.682 0-0.943s-0.682-0.261-0.943-0.001zM4.667 8c0.368 0 0.667-0.299 0.667-0.667v-2h2c0.369 0 0.667-0.299 0.667-0.667s-0.298-0.667-0.667-0.667h-3.333l-0.001 3.333c0 0.368 0.298 0.667 0.667 0.667zM11.333 8c-0.369 0-0.667 0.299-0.667 0.667v2h-2c-0.369 0-0.667 0.299-0.667 0.667s0.298 0.667 0.667 0.667h3.333v-3.333c0-0.368-0.298-0.667-0.667-0.667z");
        btnSubmit.setGraphic(svgPath);
        btnSubmit.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        btnCancel.setOnAction(event -> popup.hide());
        popup = new Popup() {
            private Window window;
            private ChangeListener<Number> xListener = (observable, oldValue, newValue) -> {
                if (newValue != null) { setAnchorX(getAnchorX() + newValue.doubleValue() - oldValue.doubleValue()); }
            };
            private ChangeListener<Number> yListener = (observable, oldValue, newValue) -> {
                if (newValue != null) { setAnchorY(getAnchorY() + newValue.doubleValue() - oldValue.doubleValue()); }
            };
            private WeakChangeListener<Number> weakXChangeListener = new WeakChangeListener<>(xListener);
            private WeakChangeListener<Number> weakYChangeListener = new WeakChangeListener<>(yListener);
            @Override
            public void show(Node node, double x, double y) {
                if (window != null) {
                    hide();
                    window.xProperty().removeListener(weakXChangeListener);
                    window.yProperty().removeListener(weakYChangeListener);
                }
                window = node.getScene().getWindow();
                if (window != null) {
                    window.xProperty().addListener(weakXChangeListener);
                    window.yProperty().addListener(weakYChangeListener);
                    setOnShown(event -> {
                        Bounds bounds = node.getLayoutBounds();
                        setAnchorX(getAnchorX() + bounds.getMinX());
                        setAnchorY(getAnchorY() + bounds.getMinY());
                    });
                    super.show(node, x, y + node.getBoundsInLocal().getHeight());
                }
            }
        };
        popup.getContent().add(mainPane);
    }

    public Long getUnitId() { return unitId; }

    public void setUnitId(Long id) {
        unitId = id;
        if (unitId != null) {
            UnitObj unitObj = UnitCache.getInstance().getUnitObj(unitId);
            if (unitObj == null) {
                startUnitLoadingWorkerWithAncestorAndSiblingMode(unitId);
            }
            setUIs(unitObj);
        }
    }

    public WidgetUnit() {
        URL loc = CommonCache.getInstance().getUnitFxmlURL();
        FXMLLoader fxmlLoader = new FXMLLoader(loc);
        try {
            fxmlLoader.setController(this);
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setOptCreatePane() {
        Long superviserId = (Long)optCreatePane.getUserData();
        UnitObj superviser = UnitCache.getInstance().getUnitObj(superviserId);
        if (superviser != null) {
            labelSuperviserName.setText("(上級單位："+superviser.getName()+")");
        } else {
            labelSuperviserName.setText("(上級單位：空)");
        }
        tfName.setText("");
        tfName.getStyleClass().remove("my-alert");
        labelWarning.setVisible(false);
        int num = labelSuperviserName.getText().length();
        labelSuperviserName.setPrefWidth(num * 15 + 20);
    }

    public void anchorOptCreatePane(VBox base) {
        if (trackCreatePane.getChildren().contains(optCreatePane)) {
            trackCreatePane.getChildren().remove(optCreatePane);
        }
        trackCreatePane = base;
        if (!trackCreatePane.getChildren().contains(optCreatePane)) {
            trackCreatePane.getChildren().add(optCreatePane);
        }
    }

    public void setUIs(UnitObj unitObj) {
        clearUIs();
        if (unitObj == null) {
            return;
        }
        unitId = unitObj.getId();
        UnitObj cur = unitObj;
        UnitObj superviser = cur.getSuperviser();
        do {
            // if the superviser of the current UnitObj is null (i.e. NPA)
            if (superviser == null) { break; }
            List<UnitObj> supervisees = UnitCache.getInstance().getSuperviseeUnitObjs(superviser.getId());
            appendNodeOnFrontOfList(showPane, cur, supervisees);
            cur = superviser;
            superviser = cur.getSuperviser();
        } while (true);
        labelSimpleName.setText(unitObj.getSimpleName());
    }

    private void appendNodeOnFrontOfList(VBox sp, UnitObj select, List<UnitObj> list) {
        UnitObj other = new UnitObj();
        other.setName("新增單位");
        JFXComboBox<UnitObj> combo = new JFXComboBox<>();
        JFXButton btn = new JFXButton("次級單位");
        VBox showNode = new VBox();
        HBox optNode = new HBox();
        optNode.getStyleClass().add("list-pane");
        optNode.managedProperty().bind(optNode.visibleProperty());
        if (select.getName() != null) {
            combo.setStyle("-fx-pref-width:"+(select.getName().length()*15+70));
        }
        btn.setVisible(false);
        btn.setOnAction(event -> {
            startUnitLoadingWorkerWithSuperviserId(unitId);
            optCreatePane.setVisible(false);
            optCreatePane.setUserData(select.getSuperviserId());
            anchorOptCreatePane(showNode);
        });
        list.add(other);
        combo.getItems().addAll(FXCollections.observableArrayList(list));
        combo.setCellFactory(new Callback<ListView<UnitObj>, ListCell<UnitObj>>(){
            @Override
            public ListCell<UnitObj> call(ListView<UnitObj> param) {
                final ListCell<UnitObj> cell = new ListCell<UnitObj>() {
                    @Override
                    public void updateItem(UnitObj item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !empty) {
                        setText(item.getName());
                        if (item.equals(other)) {
                            setStyle("-fx-background-color: pink");
                        } else {
                            int ind = combo.getItems().indexOf(item);
                            if (ind >= 0) {
                                setText((ind+1)+". "+item.getName());
                            }
                        }
                    }
                    }
                };
                return cell;
            }
        });
        combo.setValue(select);
        optNode.getChildren().addAll(combo, btn);
        optNode.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                UnitObj obj = (UnitObj)combo.getValue();
                Long selectId = obj.getId();
                newValue = selectId != null && unitId == selectId ? newValue : false;
                btn.setManaged(newValue);
                btn.setVisible(newValue);
            }
        });
        combo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue == other) {
                    optCreatePane.setVisible(true);
                    optCreatePane.setUserData(select.getSuperviserId());
                    anchorOptCreatePane(showNode); // set location
                    setOptCreatePane(); // set data (you may need to wait thread to fetch data from db)
                    int curIdx = sp.getChildren().indexOf(showNode);
                    int sz = sp.getChildren().size();
                    if (curIdx != sz - 1) {
                        sp.getChildren().remove(curIdx+1, sz);
                    }
                } else {
                    setUIs(newValue);
                }
            }
        });
        showNode.getChildren().add(optNode);
        sp.getChildren().add(0, showNode);
    }

    public void clearUIs() {
        showPane.getChildren().clear();
        tfName.setText("");
        labelSuperviserName.setText("(上級單位：)");
    }

    public boolean doValidation() {
        boolean flag = true;
        String name = tfName.getText();
        String warningMessage = null;
        if (name == null || name.length() == 0) {
            tfName.getStyleClass().add("my-alert");
            warningMessage = "單位名稱不可為空";
        }
        Long superviserId = (Long)optCreatePane.getUserData();
        UnitObj superviser = UnitCache.getInstance().getUnitObj(superviserId);
        if (name != null && superviser != null) {
            List<UnitObj> supervisees = UnitCache.getInstance().getSuperviseeUnitObjs(superviserId);
            boolean hasExists = supervisees== null ? false : supervisees.stream().anyMatch(obj -> name.equals(obj.getName()));
            boolean hasExists2 = name.equals(superviser.getName());
            if (hasExists || hasExists2) {
                tfName.getStyleClass().add("my-alert");
                warningMessage = "別鬧了";
            }
        }
        if (warningMessage != null) {
            labelWarning.setText(warningMessage);
            labelWarning.setVisible(true);
        }
        return warningMessage == null; // true: pass validation, otherwise, fail
    }

    public void updateObjByUIs(@NotNull UnitObj target) {
        target.setId(null);
        target.setName(tfName.getText());
        Long superviserId = (Long)optCreatePane.getUserData();
        UnitObj superviser = UnitCache.getInstance().getUnitObj(superviserId);
        Integer level = superviser == null || superviser.getLevel() == null ? 0 : superviser.getLevel() + 1;
        target.setLevel(level);
        target.setSuperviser(superviser);
    }

    public Node getRoot() {return root; }

    public Node getMainPane() { return mainPane; }

    public Node getOptCreatePane() { return optCreatePane; }

    public Node getInitPane() { return initPane; }

    public void startUnitLoadingWorkerWithSuperviserId(Long id) {
        UnitLoadingTask unitLoadingTask = new UnitLoadingTask();
        unitLoadingTask.withSupervierId(id);
        startUnitLoadingWorker(unitLoadingTask);
        unitLoadingTask.setOnSucceeded(e-> {
            int status = unitLoadingTask.getStatus();
            if (status >= 200 && status < 300) {
                UnitCache.getInstance().addAll(unitLoadingTask.getValue());
                List<UnitObj> supervisees = UnitCache.getInstance().getSuperviseeUnitObjs(id);
                UnitObj obj = null;
                if (supervisees != null && supervisees.size() > 0) {
                    obj = supervisees.get(0);
                    setUIs(obj);
                } else {
                    optCreatePane.setVisible(true);
                    optCreatePane.setUserData(id);
                    setOptCreatePane();
                }
            }
        });
    }

    public void startUnitLoadingWorkerWithAncestorAndSiblingMode(Long id) {
        UnitLoadingTask unitLoadingTask = new UnitLoadingTask();
        unitLoadingTask.withAncestorAndSiblingMode(id);
//        startUnitLoadingWorker(unitLoadingTask);
//        unitLoadingTask.setOnSucceeded(e-> {
//            int status = unitLoadingTask.getStatus();
//            if (status >= 200 && status < 300) {
//                UnitCache.getInstance().addAll(unitLoadingTask.getValue());
//                UnitObj obj = UnitCache.getInstance().getUnitObj(id);
//                setUIs(obj);
//            }
//        });
        unitLoadingTask.work(
                e-> {
                    int status = unitLoadingTask.getStatus();
                    if (status >= 200 && status < 300) {
                        UnitCache.getInstance().addAll(unitLoadingTask.getValue());
                        UnitObj obj = UnitCache.getInstance().getUnitObj(id);
                        setUIs(obj);
                    }
                },
                e -> CommonCache.getInstance().getFailedNotification("連線失敗")
        );
    }

    protected synchronized void startUnitLoadingWorker(UnitLoadingTask unitLoadingTask) {
//        unitLoadingTask.work(
//                null,
//                e -> CommonCache.getInstance().getFailedNotification("連線失敗")
//        );

        Notifications notifications = Notifications.create()
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(event -> {});
        unitLoadingTask.setOnFailed(e -> {
            notifications.text("連線失敗").graphic(CommonCache.getInstance().getViewCancel()).show();
        });
        Thread thread = new Thread(unitLoadingTask);
        thread.setDaemon(true);
        thread.start();
    }

    public synchronized void startUnitSavingWorker(UnitObj target) {
        Notifications notifications = Notifications.create()
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(event -> {});
        UnitSavingTask unitSavingTask = new UnitSavingTask(target);
        unitSavingTask.stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue == Worker.State.SUCCEEDED) {
                UnitObj tmp = unitSavingTask.getValue();
                UnitCache.getInstance().add(tmp);
                unitId = tmp.getId();
                UnitObj obj = UnitCache.getInstance().getUnitObj(unitId);
                setUIs(obj);
            }
        });
        unitSavingTask.setOnFailed(e -> {
            notifications.text("連線失敗").graphic(CommonCache.getInstance().getViewCancel()).show();
        });
        Thread thread = new Thread(unitSavingTask);
        thread.setDaemon(true);
        thread.start();
    }

//    public void stopLoadingWorker() {
//        if (unitLoadingTask != null && unitLoadingTask.isRunning()) {
//            unitLoadingTask.cancel();
//        }
//    }
}
