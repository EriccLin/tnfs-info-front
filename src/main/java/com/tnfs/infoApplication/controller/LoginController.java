package com.tnfs.infoApplication.controller;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import com.tnfs.infoApplication.cache.AuthorizationCache;
import com.tnfs.infoApplication.mapper.IAccountDetailObjMapper;
import com.tnfs.infoApplication.model.AccountObj;
import com.tnfs.infoApplication.task.LoginValidationTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private Button btnClose;
    @FXML private JFXTextField tfUsername, tfPassword;
    @FXML private JFXPasswordField tfHiddenPassword;
    @FXML private JFXCheckBox chboxShowPasswd;
    @FXML private JFXButton btnLogin;
    private String message = null;
    private static Stage stage = new Stage();
    LoginValidationTask loginValidationTask = null;

    public static void loadView(Stage stage) {

        try {
//            file:/C:/Users/c8062/Desktop/infoClient/target/classes/com/tnfs/infoClient/controller
//            System.out.println(LoginController.class.getResource("."));
            Parent root = FXMLLoader.load(LoginController.class.getResource("../view/Login.fxml"));
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LoginController() {
//        file:/C:/Users/c8062/Desktop/infoClient/target/classes/com/tnfs/infoClient/controller
//        System.out.println(LoginController.class.getResource("."));
        URL loc = getClass().getResource("../view/Login.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(loc);
        try {
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("帳號登入");
            stage.initStyle(StageStyle.UNDECORATED);
//            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    if (btnLogin.isFocused()) {
                        startLoginWorker();
                    } else if (btnClose.isFocused()) {
                        stopLoginWorker();
                        close();
                    }
                }
            }
        });
    }

    public void showAndWait() {
        stage.showAndWait();
    }

    public void close() {
        stage.hide();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chboxShowPasswd.setSelected(false);
        tfHiddenPassword.textProperty().bindBidirectional(tfPassword.textProperty());
        tfHiddenPassword.visibleProperty().bind(tfPassword.visibleProperty().not());
        tfPassword.setVisible(false);
        chboxShowPasswd.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                tfPassword.setVisible(newValue);
            }
        });
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("輸入欄位不可空白");
        tfUsername.getValidators().add(validator);
        tfUsername.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    tfUsername.validate();
                }
            }
        });
        tfUsername.setText("account1");
        tfPassword.setText("123456");
        btnClose.setOnAction(event -> {
            stopLoginWorker();
            close();
        });
        btnLogin.setOnAction(event -> {
            startLoginWorker();
        });
    }

    public void startLoginWorker() {
        // this username exists or not?
        loginValidationTask = new LoginValidationTask(tfUsername.getText(), tfPassword.getText());
        Notifications notifications = Notifications.create()
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(event -> {});
        loginValidationTask.setOnSucceeded(e -> {
            AccountObj accountObj = loginValidationTask.getValue();
            // account1, 123456
            if (accountObj == null){
                URL loc = getClass().getResource("../image/cancel-64.png");
                ImageView imageView = new ImageView(loc.toString());
                notifications.text("請重新確認帳號&密碼").graphic(imageView).show();
                reset();
            } else {
                AuthorizationCache.getInstance().setAccountObj(accountObj);
                close();
            }
        });
        loginValidationTask.setOnFailed(e->{
            URL loc = getClass().getResource("../image/cancel-64.png");
            ImageView imageView = new ImageView(loc.toString());
            notifications.text("連線失敗").graphic(imageView).show();
            reset();
        });
        new Thread(loginValidationTask).start();
    }

    public void stopLoginWorker() {
        if (loginValidationTask != null && loginValidationTask.isRunning()) {
            loginValidationTask.cancel();
        }
    }

    public void reset() {
//        tfUsername.clear();
        tfPassword.clear();
        chboxShowPasswd.setSelected(false);
    }
}
