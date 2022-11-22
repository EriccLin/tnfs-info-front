package com.tnfs.infoApplication.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

public class DialogController {

    @FXML private Label title;
    @FXML private Label message;
    @FXML private HBox buttons;
    @FXML private JFXButton btnOk, btnClose;
    private Stage stage = new Stage();

    public void show() {
        stage.show();
    }
    public void close() {
        stage.close();
    }

    public static class DialogBuilder {
        private String title = "";
        private String message = "";

        private ActionListener okActionListener;

        private DialogBuilder() {
        }
        public DialogBuilder title(String title) {
            this.title = title;
            return this;
        }
        public DialogBuilder message(String message) {
            this.message = message;
            return this;
        }
        public DialogBuilder okActionListener(ActionListener actionListener) {
            this.okActionListener = actionListener;
            return this;
        }
        public DialogController build() {
            URL loc = getClass().getResource("../view/Dialog.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(loc);
            DialogController dialogController = new DialogController();
            try {
                fxmlLoader.setController(dialogController);
                Parent root = fxmlLoader.load();
                dialogController.stage.setScene(new Scene(root));
                dialogController.stage.initStyle(StageStyle.UNDECORATED);
                dialogController.title.setText(this.title);
                dialogController.message.setText(this.message);

                if (okActionListener != null) {
                    dialogController.btnOk.setOnAction(event -> {
                        okActionListener.doAction();
                        dialogController.stage.close();
                    });
                } else {
                    dialogController.btnOk.setVisible(false);
                    dialogController.btnClose.setText("關閉");
                }
                dialogController.btnClose.setOnAction(event -> {
                    dialogController.stage.close();
                });
                return dialogController;
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
        public static DialogBuilder builder() {
            return new DialogBuilder();
        }
    }

    public static interface ActionListener {
        void doAction();
    }
}
