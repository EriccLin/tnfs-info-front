package com.tnfs.infoApplication.util;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Window;

public class SimplePopupWindow extends Popup {
    private Window window = null;
    private Parent rootNode = null;
    private Point2D initPos = null;
    private Point2D anchor = null;
    private ChangeListener<Number> xListener = (observable, oldValue, newValue) -> {
        if (newValue != null) { setAnchorX(getAnchorX() + newValue.doubleValue() - oldValue.doubleValue()); }
    };
    private ChangeListener<Number> yListener = (observable, oldValue, newValue) -> {
        if (newValue != null) { setAnchorY(getAnchorY() + newValue.doubleValue() - oldValue.doubleValue()); }
    };
    private WeakChangeListener<Number> weakXChangeListener = new WeakChangeListener<>(xListener);
    private WeakChangeListener<Number> weakYChangeListener = new WeakChangeListener<>(yListener);
    private EventHandler<MouseEvent> mousePressEvent = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event) {
            Bounds bd = rootNode.localToScene(rootNode.getLayoutBounds());
            if (bd.contains(event.getX(), event.getY())) {
                System.out.println(String.format("<%.2f,%.2f>", event.getX(), event.getY()));
                initPos = new Point2D(event.getSceneX(), event.getSceneY());
                anchor = new Point2D(getAnchorX(), getAnchorY());
            }
        }
    };
    private EventHandler<MouseEvent> mouseDraggedEvent = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event) {
            if (initPos == null) {
                return;
            }
            Parent rootNode = getScene().getRoot();
            double deltaX = event.getSceneX() - initPos.getX();


            System.out.println(String.format("<%.2f,%.2f> ==> <%.2f,%.2f>", event.getX(), event.getY(), event.getX() - initPos.getX(), event.getY() - initPos.getY()));
//            Scene scene = getScene();
//            Window window = scene.getWindow();
            double x = window.getX();
            double y = window.getY();

//            System.out.println(String.format("%.2f => (%.2f,%.2f) : %.2f", deltaX, getAnchorX() , getAnchorX(), initPos.getX() -event.getX() + getScene().getX() - window.getScene().getX() ));
//            Point2D pt = window.getScene().localToScene(event.getX(), event.getY());
//                Point2D pt = rootNode.localToParent(event.getX(), event.getY());
//                System.out.println(deltaX + ":" + bounds);
//            System.out.println(deltaX + ":" + pt.getX() + " " + pt.getY());
//                double deltaY = event.getY() - initPos.getY();
            getScene().getWindow().setX(anchor.getX() + deltaX);
//                setAnchorY(anchor.getY() + deltaY);
        }
    };
    private EventHandler<MouseEvent> mouseReleasedEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (anchor != null && initPos != null) {
                anchor = new Point2D(anchor.getX() + event.getX() - initPos.getX(), anchor.getY() + event.getY() - initPos.getY());
                initPos = null;
            }
        }
    };
    @Override
    public void show(Node node, double x, double y) {
        System.out.println("check: " + (getScene() == node.getScene()));
        rootNode = getScene().getRoot();
        if (window != null) {
            hide();
            rootNode.removeEventHandler(MouseEvent.MOUSE_PRESSED, mousePressEvent);
            rootNode.getScene().removeEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDraggedEvent);
            rootNode.getScene().removeEventHandler(MouseEvent.MOUSE_RELEASED, mouseReleasedEvent);
//            window.xProperty().removeListener(weakXChangeListener);
//            window.yProperty().removeListener(weakYChangeListener);
        }
        window = node.getScene().getWindow();
        if (window != null) {
            rootNode.addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressEvent);
            rootNode.getScene().addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDraggedEvent);
            rootNode.getScene().addEventHandler(MouseEvent.MOUSE_RELEASED, mouseReleasedEvent);
//            window.xProperty().addListener(weakXChangeListener);
//            window.yProperty().addListener(weakYChangeListener);
            setOnShown(event -> {
                Bounds bounds = node.getLayoutBounds();
                setAnchorX(getAnchorX() + bounds.getMinX());
                setAnchorY(getAnchorY() + bounds.getMinY());
            });
            super.show(node, x, y);
        }
    }
};

//        popup.getContent().add(widgetNewCaseTypeChooser.getOptPane());
//        btnNewCaseType.setOnAction(event -> {
//            widgetNewCaseTypeChooser.setUIs();
//            if (!popup.isShowing()) {
//                Bounds bd = btnNewCaseType.localToScene(btnNewCaseType.getBoundsInLocal());
//                Point2D pt = new Point2D(bd.getMaxX(), bd.getMaxY());
//                Window window = btnNewCaseType.getScene().getWindow();
//                popup.show(window, window.getX() + pt.getX(), window.getY() + pt.getY());
//            }
//        });