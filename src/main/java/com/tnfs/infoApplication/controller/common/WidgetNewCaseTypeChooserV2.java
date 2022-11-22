/*
 * javafx setClassStyle
 * ref: https://www.itread01.com/p/664210.html
 * ref: https://www.itread01.com/content/1544828228.html
 * ref: https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html
 * */

package com.tnfs.infoApplication.controller.common;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.tnfs.infoApplication.util.MyDataFrame;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.AutoCompletionBinding;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class WidgetNewCaseTypeChooserV2 implements Initializable {
    static private MyDataFrame case_type_df = null;
    static List<String> case_type_primary = null;
    static List<String> case_type_all = null;
    private String case_type = null;
    {
        URL loc = getClass().getResource("../case_types.csv");
        System.out.println("loc:------ "+loc.toExternalForm().substring(6));
        case_type_df = new MyDataFrame(loc.toExternalForm().substring(6));
        case_type_all= case_type_df.getColumn("案類");
        case_type_primary = case_type_df.getColumnNames();
        System.out.println(case_type_primary+"#: "+case_type_primary.indexOf("#")+" 案類: "+case_type_primary.indexOf("案類"));
//        MyUtil.stringToAscii(case_type_primary.get(case_type_primary.size()-1));
//        MyUtil.stringToAscii("#");
        // theoretically, remove => '#' which indicate index of each row
        case_type_primary.remove(case_type_primary.indexOf("#")); // column-#
        case_type_primary.remove(case_type_primary.indexOf("案類")); // column-案類
    }

    @FXML protected JFXButton btnCreate;
    @FXML private FlowPane pane;
    @FXML protected JFXButton btnSubmit, btnClose;
    @FXML private JFXTextField tfCaseTypePrimary, tfCaseTypeSecond;
    @FXML private HBox caseTypeChooser;
    AutoCompletionBinding<String> autoBindPrimary, autoBindSecond;
    ImageView imageViewCancel, imageViewOk;
    private Node root = null;
    private List<String> caseTypeList = new ArrayList<>();
    private Point2D chooserPanePos;

    public WidgetNewCaseTypeChooserV2() {
        URL loc = getClass().getResource("../view/widget/NewCaseTypeChooserV2.fxml");
        System.out.println("loc:++ "+loc);
        FXMLLoader fxmlLoader = new FXMLLoader(loc);
        try {
            fxmlLoader.setController(this);
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        caseTypeChooser.managedProperty().bindBidirectional(caseTypeChooser.visibleProperty());
        URL locImage1 = getClass().getResource("../image/cancel-64.png");
        imageViewCancel = new ImageView(locImage1.toString());
        URL locImage2 = getClass().getResource("../image/ok-64.png");
        imageViewOk = new ImageView(locImage2.toString());
        // List Selected CaseTypes
        initListPane();
        // Choose
        initChooser();
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

    public void updatePaneList() {
        ObservableList<Node> observableList = pane.getChildren();
        observableList.clear();
        if (caseTypeList != null && caseTypeList.size() > 0) {
            for (String castType : caseTypeList) {
                JFXButton btnDelete = new JFXButton(castType);
                //btnDelete.getStyleClass().add("wd-60;");
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

    public void initChooser() {
        setAutoCompleteTFPrimary();
        setAutoCompleteTFSecondary();
        tfCaseTypePrimary.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    tfCaseTypeSecond.setText("");
                    setAutoCompleteTFSecondary();
                }
            }
        });
        caseTypeChooser.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            chooserPanePos = new Point2D(event.getX(), event.getY());
        });
        caseTypeChooser.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            if (chooserPanePos != null) {
                caseTypeChooser.setTranslateX(caseTypeChooser.getTranslateX() + event.getX() - chooserPanePos.getX());
                caseTypeChooser.setTranslateY(caseTypeChooser.getTranslateY() + event.getY() - chooserPanePos.getY());
            }
        });
    }

    public void setAutoCompleteTFPrimary() {
        autoBindPrimary = setAutoCompleteTF(tfCaseTypePrimary, case_type_primary);
    }

    public void setAutoCompleteTFSecondary() {
        String primary = tfCaseTypePrimary.getText();
        boolean flag = true;
        if (!primary.equals("")) {
            for (String suggPrimary: case_type_primary) {
                if (suggPrimary.equals(primary)) {
                    List<Integer> integerList = case_type_df.getColumn(primary);
                    List<String> caseTypeSecond = case_type_df.getColumn(integerList,"案類");
                    autoBindSecond = setAutoCompleteTF(tfCaseTypeSecond, caseTypeSecond);
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            autoBindSecond = setAutoCompleteTF(tfCaseTypeSecond, case_type_all);
        }
        autoBindSecond.getAutoCompletionPopup().getSuggestions().removeAll(caseTypeList);
    }

    public AutoCompletionBinding<String> setAutoCompleteTF(JFXTextField textField, List<String> possibleSuggestions) {
        Callback<AutoCompletionBinding.ISuggestionRequest, Collection<String>> callback = new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<String>>() {
            private final Object possibleSuggestionsLock = new Object();
            /**
             * Add the given new possible suggestions to this  SuggestionProvider
             * @param newPossible
             */
            public void setPossibleSuggestions(Collection<String> newPossible){
                synchronized (possibleSuggestionsLock) {
                    possibleSuggestions.clear();
                    possibleSuggestions.addAll(newPossible);
                }
            }
            @Override
            public Collection<String> call(AutoCompletionBinding.ISuggestionRequest request) {
                List<String> ret = new ArrayList<>();
                synchronized (possibleSuggestionsLock) {
                    for (String possibleSuggestion : possibleSuggestions) {
                        if(isMatch(possibleSuggestion, request)){
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
                String userTextLower = request.getUserText().toLowerCase();
                String suggestionStr = suggestion.toLowerCase();
                return suggestionStr.contains(userTextLower) && !suggestionStr.equals(userTextLower);
            }
        };
        AutoCompletionBinding<String> autoBind = new AutoCompletionTextFieldBinding<String>(textField, callback);
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

    public void addCaseType() {
        case_type = tfCaseTypeSecond.getText();
        // this type already exits
        Notifications notifications = Notifications.create()
                .graphic(imageViewCancel)
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(event -> {
                    ;
                });
        boolean hasExists = caseTypeList.contains(case_type);
        boolean isEmpty = case_type.trim().length() == 0;
        boolean exceedUpperNum = caseTypeList.size() == 4;
        if (hasExists) {
            notifications = notifications.text(case_type+"-已存在");
        } else if (isEmpty) {
            notifications = notifications.text("無效輸入：次要類別不可為空");
        } else if (exceedUpperNum) {
            notifications = notifications.text("禁止新增過多類別");
        } else {
            caseTypeList.add(case_type);
            autoBindSecond.getAutoCompletionPopup().getSuggestions().remove(case_type);
        }
        tfCaseTypePrimary.setText("");
        tfCaseTypeSecond.setText("");
        if (hasExists || isEmpty || exceedUpperNum) {
            notifications.show();
        }
        updatePaneList();
    }

    public void setCaseTypeList(List<String> list) {
        caseTypeList = list;
    }

    public List<String> getCaseTypeList() {
        return caseTypeList;
    }

    public void removeFromCaseTypeList(String caseType) {
        caseTypeList.remove(caseType);
    }

    public Node getRoot() { return root; }

    public FlowPane getCaseTypeListPane() { return pane; }

    public HBox getCaseTypeChooserPane() { return caseTypeChooser; }

    public JFXButton getBtnSubmit() { return btnSubmit; }

    public JFXButton getBtnClose() { return btnClose; }

    public JFXButton getBtnCreate() { return btnCreate; }

//    public void resetAutoCompleteTextField() {
//        List<String> case_type_all_after_excluded = new ArrayList<>();
//        case_type_all_after_excluded.addAll(case_type_all);
//        //Collections.copy(case_type_all,case_type_all_after_excluded);
//        case_type_all_after_excluded.removeAll(caseTypeList);
//
////        AutoCompletionBinding<String> autoBind = TextFields.bindAutoCompletion(tf_case_type, FXCollections.observableArrayList(case_type_all_after_excluded));
//        Callback<AutoCompletionBinding.ISuggestionRequest, Collection<String>> callback = new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<String>>() {
//            private final List<String> possibleSuggestions = case_type_all_after_excluded;
//            private final Object possibleSuggestionsLock = new Object();
//            /**
//             * Add the given new possible suggestions to this  SuggestionProvider
//             * @param newPossible
//             */
//            public void setPossibleSuggestions(Collection<String> newPossible){
//                synchronized (possibleSuggestionsLock) {
//                    possibleSuggestions.clear();
//                    possibleSuggestions.addAll(newPossible);
//                }
//            }
//            @Override
//            public Collection<String> call(AutoCompletionBinding.ISuggestionRequest request) {
//                List<String> suggestions = new ArrayList<>();
//                synchronized (possibleSuggestionsLock) {
//                    for (String possibleSuggestion : possibleSuggestions) {
//                        if(isMatch(possibleSuggestion, request)){
//                            suggestions.add(possibleSuggestion);
//                        }
//                    }
//                }
//                return suggestions;
//            }
//            /**
//             * Check the given possible suggestion is a match (is a valid suggestion)
//             * @param suggestion
//             * @param request
//             * @return
//             */
//            protected boolean isMatch(String suggestion, AutoCompletionBinding.ISuggestionRequest request) {
//                String userTextLower = request.getUserText().toLowerCase();
//                String suggestionStr = suggestion.toLowerCase();
//                return suggestionStr.contains(userTextLower) && !suggestionStr.equals(userTextLower);
//            }
//        };
//        AutoCompletionBinding<String> autoBind = new AutoCompletionTextFieldBinding<String>(tfCaseTypePrimary, callback);
////        AutoCompletionBinding<String> autoBind = new AutoCompletionTextFieldBinding<String>(tf_case_type, new SuggestionProvider<String>() {
////            {
////                addPossibleSuggestions(FXCollections.observableArrayList(case_type_all_after_excluded));
////            }
////            @Override
////            protected Comparator<String> getComparator() {
////                return null;
////            }
////
////            @Override
////            protected boolean isMatch(String suggestion, AutoCompletionBinding.ISuggestionRequest request) {
////                System.out.println(request.getUserText().equals(" ") );
////                return request.getUserText().equals(" ") || isMatch(suggestion, request);
//////                String userTextLower = request.getUserText().toLowerCase();
//////                String suggestionStr = suggestion.toString().toLowerCase();
//////                int sz = userTextLower.length();
//////                if (sz < 1) {
//////                    return true;
//////                } else {
//////                    for (int i = 0; i < sz - 1; ++i) {
//////                        String sub = userTextLower.substring(i,i+1);
//////                        if (suggestionStr.contains(sub)) {
//////                            return true;
//////                        }
//////                    }
//////                }
//////                return suggestionStr.contains(userTextLower)
//////                        && !suggestionStr.equals(userTextLower);
////            }
////        });
//        autoBind.getCompletionTarget().setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                if (event.getButton().equals(MouseButton.PRIMARY)) {
//                    autoBind.setUserInput(""); // set the newText to trigger "addListener" of userInput
//                    autoBind.getAutoCompletionPopup().show(autoBind.getCompletionTarget());
//                }
//            }
//        });
//    }
}
