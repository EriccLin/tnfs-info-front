package com.tnfs.infoApplication.controller;

import com.tnfs.infoApplication.cache.AuthorizationCache;
import com.tnfs.infoApplication.model.AccountDetailObj;
import com.tnfs.infoApplication.model.AccountObj;
import com.tnfs.infoApplication.util.Menu;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainFrameController implements Initializable {
    private static Integer HEADER_HEIGHT = 40;
    private static Integer SIDEBAR_WIDTH = 80;
    @FXML private VBox sideBar;
    @FXML private StackPane contentView;
    @FXML private ScrollPane scrollContentView;
    @FXML private VBox home;

    @FXML private VBox caseCreate;
    @FXML private VBox caseCreateList;
    @FXML private Label caseCreateTypeA;
    @FXML private Label caseCreateTypeB;
    @FXML private Label caseCreateTypeC;

    @FXML private VBox caseManagement;
    @FXML private VBox caseSearch;
    @FXML private VBox caseAudit;
    @FXML private VBox evidenceManagement;
    @FXML private VBox memberManagement;
    @FXML private VBox logout;

    private List<Node> nodes;
    private Stage stage = null;
    private AccountObj accountObj = null;

    private Long lastScroll = 0L;
    private final ChangeListener<? super Number> scrollListener = (_obs, _old, _new) -> {
        lastScroll = System.currentTimeMillis();
    };

    public MainFrameController(Stage stage) {
        this.stage = stage;
        URL loc = getClass().getResource("../view/MainFrame.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(loc);
        try {
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("資訊平台");
            stage.initStyle(StageStyle.DECORATED);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.accountObj = AuthorizationCache.getInstance().getAccountObj();
        loadView(Menu.valueOf("home"));
        URL locImage = getClass().getResource("../image/ok-64.png");
        ImageView imageView = new ImageView(locImage.toString());
        Notifications notifications = Notifications.create()
                .text((accountObj == null ? "": accountObj.getName() + " ")+"登入成功")
                .graphic(imageView)
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(event -> {
                    ;
                });
        notifications.show();
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }

    public AccountObj getAccountDto() {
        return accountObj;
    }

    public void setAccountDto(AccountDetailObj accountGetDto) {
        this.accountObj = accountObj;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nodes = Arrays.asList( new Node[] {
                home,  // Menu-home
                caseCreate, // Menu-caseCreate
                caseCreateTypeA,
                caseCreateTypeB,
                caseCreateTypeC,
                caseManagement,   // Menu-caseManagement
                caseSearch, // Menu-caseSearch
                caseAudit,
                evidenceManagement,
                memberManagement,
                logout
        });
        caseCreateList.managedProperty().bind(caseCreateList.visibleProperty());
        caseCreateList.setVisible(false);
        for (Node node: nodes ) {
            node.setOnMouseClicked(event -> clickOnMenu(event));
        }
        stage.setOnCloseRequest(event -> Platform.exit());
        scrollContentView.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//        scrollContentView.setFitToHeight(true);
        scrollContentView.vvalueProperty().addListener(scrollListener);
    }
    @FXML
    private void clickOnMenu(MouseEvent event) {
        Node source = (Node) event.getSource();
        String id = source.getId();
        System.out.println("id: "+id);
        Menu menu = null;
        switch (id) {
            case "caseCreate":
                caseCreateList.setVisible(!caseCreateList.isVisible());
                break;
            case "logout":
                DialogController dialogController = DialogController.DialogBuilder.builder()
                        .title("")
                        .message("確認離開?")
                        .okActionListener(()-> {
                            stage.close();
                        })
                        .build();
                dialogController.show();
                break;
            default:
                menu = Menu.valueOf(id);
        }
        String formName = menu == null ? id : menu.name();
        for (Node node: nodes) {
            if (node.getId().equals(formName)) {
                node.getStyleClass().add("active");
            } else {
                node.getStyleClass().remove("active");
            }
        }
        if (menu != null) {
            loadView(menu);
        }
    }

    private void loadView(Menu menu) {
        contentView.getChildren().clear();
        System.out.println("../view/" + menu.getFxml());
        URL loc = getClass().getResource("../view/" + menu.getFxml());
//        System.out.println("loc:  "+loc);
        System.out.println(menu.name());
        FXMLLoader fxmlLoader = new FXMLLoader(loc);
        try {
            switch (menu.name()) {
                case "home":
                    fxmlLoader.setController(new HomeController());
                    break;
                case "caseCreateTypeA":
                    fxmlLoader.setController(new CaseCreateController(stage));
                    break;
                case "caseCreateTypeB":
                    fxmlLoader.setController(new CaseCreateController());
                    break;
                default:
                    fxmlLoader.setController(this);
            }
            Parent view = fxmlLoader.load();
            ((VBox)view).minHeightProperty().bind(stage.heightProperty().add(-HEADER_HEIGHT));
            ((VBox)view).prefWidthProperty().bind(stage.widthProperty().add(-SIDEBAR_WIDTH));
            contentView.getChildren().add(view);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
