package com.tnfs.infoApplication.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.tnfs.infoApplication.cache.CaseTypeCache;
import com.tnfs.infoApplication.cache.CommonCache;
import com.tnfs.infoApplication.cache.CriminalCaseCache;
import com.tnfs.infoApplication.cache.UnitCache;
import com.tnfs.infoApplication.controller.common.WidgetNewCaseTypeChooserV3;
import com.tnfs.infoApplication.controller.common.WidgetNewPersonInvolved;
import com.tnfs.infoApplication.controller.common.WidgetNewTimeLocality;
import com.tnfs.infoApplication.model.*;
import com.tnfs.infoApplication.task.CriminalCaseSavingTask;
import com.tnfs.infoApplication.task.UnitLoadingTask;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.HiddenSidesPane;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.AutoCompletionBinding;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static com.tnfs.infoApplication.model.MemberObj.MEMBER_RANKS;
import static com.tnfs.infoApplication.model.UnitObj.UNITOBJ_COMPARATOR;

public class CaseBasicController implements Initializable {

    @FXML private JFXButton btnNewTimeLocality, btnNewInvolved, btnInCharge;
    @FXML private VBox paneTimeLocality;
    @FXML private HBox contentView;
    @FXML private HBox paneCaseTypeParent;
    @FXML private VBox paneAddTimeLocality;
    @FXML private VBox paneAddPersonInvolved;
    @FXML private HiddenSidesPane inChargePane;
    @FXML private JFXButton btnSubmit, btnCancel, btnNewCaseType;
    @FXML private JFXTextField tfCaseName, tfInChargeUnit, tfCaseCode, tfInCharger;
    @FXML private JFXComboBox comboMRank;
    @FXML private StackPane stackPane;

    private CriminalCaseSavingTask criminalCaseSavingTask = null;

    private CriminalCaseObj criminalCaseObj = null;
    private Node root = null;
    private Stage stage = null;

    WidgetNewCaseTypeChooserV3 widgetNewCaseTypeChooser = new WidgetNewCaseTypeChooserV3();
    WidgetNewTimeLocality widgetNewTimeLocality = new WidgetNewTimeLocality();
    WidgetNewPersonInvolved widgetNewPersonInvolved = new WidgetNewPersonInvolved();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboMRank.getItems().addAll(FXCollections.observableArrayList(MEMBER_RANKS));
        startUnitLoadingWorkerWithAncestorAndSiblingMode(1059L);

        // Case Types
        paneCaseTypeParent.getChildren().remove(1); // remove caseType demo
        paneCaseTypeParent.getChildren().add(widgetNewCaseTypeChooser.getListPane());

        widgetNewCaseTypeChooser.getBtnCreate().setOnAction(event -> {
            widgetNewCaseTypeChooser.getBtnCreate().setVisible(false);
            widgetNewCaseTypeChooser.setUIs();
            widgetNewCaseTypeChooser.show();
        });

        // Time-Location
        paneAddTimeLocality.getChildren().clear();
        paneAddTimeLocality.getChildren().add(widgetNewTimeLocality.getTable());

        btnNewTimeLocality.visibleProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue != null) {
                    btnNewTimeLocality.setVisible(!btnNewTimeLocality.isVisible());
                }
            }
        });
        btnNewTimeLocality.setOnMouseClicked(event -> {
            widgetNewTimeLocality.show();
            widgetNewPersonInvolved.setUIs(null, false);
        });

        // Person-Involved
        paneAddPersonInvolved.getChildren().clear();
        paneAddPersonInvolved.getChildren().add(widgetNewPersonInvolved.getTable());

        btnNewInvolved.visibleProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue != null) {
                    btnNewInvolved.setVisible(!btnNewInvolved.isVisible());
                }
            }
        });
        btnNewInvolved.setOnMouseClicked(event -> {
            widgetNewPersonInvolved.setUIs(null, false);
            widgetNewPersonInvolved.show();
        });

        tfInChargeUnit.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null &&  newValue.length() == 1) {
                    startUnitLoadingWorkerWithPartOfName(newValue);
                }
            }
        });

        // Prepare Background(StackPane)
//        Pane chooserPane = widgetNewCaseTypeChooser.getOptPane();
//        Pane timeLocalityOptPane = widgetNewTimeLocality.getOptPane();
//        Pane personInvolvedOptPane = widgetNewPersonInvolved.getOptPane();
//        Pane personOptPane = widgetNewPersonInvolved.getPersonOptPane();
//        stackPane.getChildren().addAll(chooserPane, timeLocalityOptPane, personInvolvedOptPane, personOptPane);

//        timeLocalityOptPane.setVisible(false);
//        personInvolvedOptPane.setVisible(false);
//        personOptPane.setVisible(false);

//        personInvolvedOptPane.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                Bounds bounds = paneAddPersonInvolved.localToScene(stackPane.getLayoutBounds());
//                Bounds bounds1 = personInvolvedOptPane.localToScene(stackPane.getLayoutBounds());
//                double deltaY = bounds.getMinY() - bounds1.getMinY() - 550;//personInvolvedOptPane.getHeight();
////                    System.out.println("1#: minX: "+bounds.getMinX() + ", minY: "+bounds.getMinY() + ", maxX: "+bounds.getMaxX() + ", maxY: "+bounds.getMaxY() );
////                    System.out.println("2#: minX: "+bounds1.getMinX() + ", minY: "+bounds1.getMinY() +  ", maxX: "+bounds1.getMaxX() + ", maxY: "+bounds1.getMaxY() );
////                    System.out.println("deltaY: "+deltaY);
//                personInvolvedOptPane.setTranslateY(personInvolvedOptPane.getTranslateY() + deltaY);
//            }
//        });

        contentView.managedProperty().bind(contentView.visibleProperty());
        contentView.setVisible(true);
        btnSubmit.setOnAction(event -> doSubmit());
    }

    public CaseBasicController(Stage stage) {
        setStage(stage);
        URL loc = CommonCache.getInstance().getCaseBasicFxmlURL();
        System.out.println(loc);
        FXMLLoader fxmlLoader = new FXMLLoader(loc);
        try {
            fxmlLoader.setController(this);
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CaseTypeCache caseTypeCache = CaseTypeCache.getInstance();
        System.out.println(caseTypeCache.getCaseTypes());
    }

    public CaseBasicController() {
        this(null);
    }

    public void setCriminalCaseObj(CriminalCaseObj obj) {
        this.criminalCaseObj = obj;
    }

    public void setStage(Stage stage) {
        if (stage != null) {
            this.stage = stage;
            widgetNewCaseTypeChooser.setOwner(stage);
            widgetNewTimeLocality.setOwner(stage);
            widgetNewPersonInvolved.setOwner(stage);
        }
    }

    public AutoCompletionBinding<MemberObj> setAutoCompleteTFWriter(JFXTextField textField, List<MemberObj> possibleSuggestions) {
        Callback<AutoCompletionBinding.ISuggestionRequest, Collection<MemberObj>> callback = new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<MemberObj>>() {
            private final Object possibleSuggestionsLock = new Object();
            @Override
            public Collection<MemberObj> call(AutoCompletionBinding.ISuggestionRequest request) {
                List<MemberObj> ret = new ArrayList<>();
                synchronized (possibleSuggestionsLock) {
                    for (MemberObj possibleSuggestion : possibleSuggestions) {
                        if(isMatch(possibleSuggestion.getNameWithMrankAndUnit(), request)){
                            ret.add(possibleSuggestion);
                        }
                    }
                }
                return ret;
            }
            /**
             * Check the given possible suggestion is a match (is a valid suggestion)
             * @param suggestion
             * @param request
             * @return
             */
            protected boolean isMatch(String suggestion, AutoCompletionBinding.ISuggestionRequest request) {
                String userTextUpper = request.getUserText().toUpperCase();
                String suggestionStr = suggestion.toUpperCase();
                return suggestionStr.contains(userTextUpper) && !suggestionStr.equals(userTextUpper);
            }
        };
        AutoCompletionBinding<MemberObj> autoBind = new AutoCompletionTextFieldBinding<MemberObj>(textField, callback);
        autoBind.getCompletionTarget().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    autoBind.setUserInput(""); // set the newText to trigger "addListener" of userInput
                    autoBind.getAutoCompletionPopup().show(autoBind.getCompletionTarget());
                }
            }
        });
        return autoBind;
    }

    public AutoCompletionBinding<UnitObj> setAutoCompleteTFUnit(JFXTextField textField, List<UnitObj> possibleSuggestions) {
        Callback<AutoCompletionBinding.ISuggestionRequest, Collection<UnitObj>> callback = new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<UnitObj>>() {
            private final Object possibleSuggestionsLock = new Object();
            /**
             * Add the given new possible suggestions to this  SuggestionProvider
             * @param newPossible
             */
            public void setPossibleSuggestions(Collection<UnitObj> newPossible){
                synchronized (possibleSuggestionsLock) {
                    possibleSuggestions.clear();
                    possibleSuggestions.addAll(newPossible);
                }
            }
            @Override
            public Collection<UnitObj> call(AutoCompletionBinding.ISuggestionRequest request) {
                Set<UnitObj> ret = new TreeSet<>(UNITOBJ_COMPARATOR);
                synchronized (possibleSuggestionsLock) {
                    for (UnitObj possibleSuggestion : possibleSuggestions) {
                        if(isMatch(possibleSuggestion.getName(), request)){
                            ret.add(possibleSuggestion);
                        }
                    }
                }
                return ret;
            }
            /**
             * Check the given possible suggestion is a match (is a valid suggestion)
             * @param suggestion
             * @param request
             * @return
             */
            protected boolean isMatch(String suggestion, AutoCompletionBinding.ISuggestionRequest request) {
                String userTextLower = request.getUserText().toUpperCase();
                String suggestionStr = suggestion.toUpperCase();
                return suggestionStr.contains(userTextLower) && !suggestionStr.equals(userTextLower);
            }
        };
        AutoCompletionBinding<UnitObj> autoBind = new AutoCompletionTextFieldBinding<UnitObj>(textField, callback);
        autoBind.getCompletionTarget().setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                autoBind.setUserInput(""); // set the newText to trigger "addListener" of userInput
                autoBind.getAutoCompletionPopup().show(autoBind.getCompletionTarget());
            }
        });
        return autoBind;
    }

    public void setUIofInCharge(boolean useWorker) {
        Set<InChargeObj> inCharges = criminalCaseObj.getInCharges();
        if (inCharges != null && inCharges.size() > 0) {
            Optional<InChargeObj> optionalInChargeObj = inCharges.stream().filter(obj -> obj != null && "主辦".equals(obj.getType())).findFirst();
            if (optionalInChargeObj.isPresent()) {
                InChargeObj inChargeObj = optionalInChargeObj.get();
                Long unitId = inChargeObj.getUnitId();
                if (inChargeObj.getInChargers() != null && inChargeObj.getInChargers().size() > 0) {
                    InChargerObj inCharger = inChargeObj.getInChargers().get(0);
                    tfInCharger.setText(inCharger.getName());
                    comboMRank.setValue(inCharger.getMrank());
                } else {
                    tfInCharger.setText("");
                    comboMRank.setValue("");
                }
                UnitObj unit = UnitCache.getInstance().getUnitObj(unitId);
                if (unit != null) {
                    tfInChargeUnit.setText(unit.getNameWithSuperviser());
                } else if (useWorker) {
                    startUnitLoadingWorkerWithId(unitId);
                } else {
                    tfInChargeUnit.setText("");
                }
            }
        }
    }

    public void setUIs() {
        assert criminalCaseObj != null;
        tfCaseName.setText(criminalCaseObj.getName());
        setUIofInCharge(true);
        Set<CaseTypeObj> caseTypes = criminalCaseObj.getCaseTypes();
        if (caseTypes != null && caseTypes.size() > 0 && widgetNewCaseTypeChooser != null) {
            widgetNewCaseTypeChooser.setCaseTypeList(caseTypes.stream().collect(Collectors.toList()));
        } else {
            widgetNewCaseTypeChooser.clear();
        }
        widgetNewCaseTypeChooser.updateListPane();

        List<TimeLocalityObj> timeLocalities = criminalCaseObj.getTimeLocalities();
        if (timeLocalities != null && timeLocalities.size() > 0 && widgetNewTimeLocality != null) {
            widgetNewTimeLocality.getTable().setItems(FXCollections.observableArrayList(timeLocalities));
        }
        List<PersonInvolvedObj> personInvolveds = criminalCaseObj.getPersonInvolveds();
        if (personInvolveds != null && personInvolveds.size() > 0 && widgetNewPersonInvolved != null) {
            widgetNewPersonInvolved.getTable().setItems(FXCollections.observableArrayList(personInvolveds));
        }
    }

    public void clearUIs() {
        tfCaseName.setText("");
        tfInCharger.setText("");
        tfInChargeUnit.setText("");
        comboMRank.setValue("");
        if (widgetNewCaseTypeChooser != null) {
            widgetNewCaseTypeChooser.getCaseTypeList().clear();
            widgetNewCaseTypeChooser.updateListPane();
        }
    }

    public void doSubmit() {
        startLoadingWorker();
        setUIs();
    }

    public void startLoadingWorker() {
        criminalCaseSavingTask = new CriminalCaseSavingTask(criminalCaseObj);
        Notifications notifications = Notifications.create()
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(event -> {});
        criminalCaseSavingTask.setOnSucceeded(e->{
            int status = criminalCaseSavingTask.getStatus();
            if ( status >= 200 && status < 300) {
                CriminalCaseCache caseCache = CriminalCaseCache.getInstance();
                caseCache.setCriminalCaseObj(criminalCaseSavingTask.getValue());
                criminalCaseObj = caseCache.getCriminalCaseObj();
                setUIs();
            } else {
                notifications.text(String.format("儲存失敗{案名：%d}",criminalCaseObj.getName()))
                        .graphic(CommonCache.getInstance().getViewCancel()).show();
            }
        });
        criminalCaseSavingTask.setOnFailed(e -> {
            notifications.text("連線失敗").graphic(CommonCache.getInstance().getViewCancel()).show();
        });
        Thread thread = new Thread(criminalCaseSavingTask);
        thread.start();
    }

    public void stopLoadingWorker() {
        if (criminalCaseSavingTask != null && criminalCaseSavingTask.isRunning()) {
            criminalCaseSavingTask.cancel();
        }
    }

    public void startUnitLoadingWorkerWithId(Long id) {
        UnitLoadingTask unitLoadingTask = new UnitLoadingTask();
        unitLoadingTask.withUnitId(id);
        Notifications notifications = Notifications.create()
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(event -> {});
        unitLoadingTask.setOnFailed(e -> {
            notifications.text("連線失敗").graphic(CommonCache.getInstance().getViewCancel()).show();
        });
        unitLoadingTask.setOnSucceeded(e-> {
            int status = unitLoadingTask.getStatus();
            if (status >= 200 && status < 300) {
                UnitCache.getInstance().addAll(unitLoadingTask.getValue());
                setUIofInCharge(false);
            }
        });
        Thread thread = new Thread(unitLoadingTask);
        thread.setDaemon(true);
        thread.start();
    }

    public void startUnitLoadingWorkerWithPartOfName(String name) {
        UnitLoadingTask unitLoadingTask = new UnitLoadingTask();
        unitLoadingTask.withPartOfName(name);
        Notifications notifications = Notifications.create()
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(event -> {});
        unitLoadingTask.setOnFailed(e -> {
            notifications.text("連線失敗").graphic(CommonCache.getInstance().getViewCancel()).show();
        });
        unitLoadingTask.setOnSucceeded(e-> {
            int status = unitLoadingTask.getStatus();
            if (status >= 200 && status < 300) {
                UnitCache.getInstance().addAll(unitLoadingTask.getValue());
                setAutoCompleteTFUnit(tfInChargeUnit, UnitCache.getInstance().getUnitObjs());
            }
        });
        Thread thread = new Thread(unitLoadingTask);
        thread.setDaemon(true);
        thread.start();
    }

    public void startUnitLoadingWorkerWithAncestorAndSiblingMode(Long id) {
        UnitLoadingTask unitLoadingTask = new UnitLoadingTask();
        unitLoadingTask.withAncestorAndSiblingMode(id);
        Notifications notifications = Notifications.create()
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(event -> {});
        unitLoadingTask.setOnFailed(e -> {
            notifications.text("連線失敗").graphic(CommonCache.getInstance().getViewCancel()).show();
        });
        unitLoadingTask.setOnSucceeded(e-> {
            int status = unitLoadingTask.getStatus();
            if (status >= 200 && status < 300) {
                UnitCache.getInstance().addAll(unitLoadingTask.getValue());
                setAutoCompleteTFUnit(tfInChargeUnit, UnitCache.getInstance().getUnitObjs());
            }
        });
        Thread thread = new Thread(unitLoadingTask);
        thread.setDaemon(true);
        thread.start();
    }

    public HBox getContentView() {return contentView; }
    public JFXButton getBtnSubmit() { return btnSubmit; }
    public JFXButton getBtnCancel() { return btnCancel; }
    public Node getRoot() { return root; }
}
