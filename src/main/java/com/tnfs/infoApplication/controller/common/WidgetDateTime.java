package com.tnfs.infoApplication.controller.common;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import com.tnfs.infoApplication.cache.CommonCache;
import com.tnfs.infoApplication.util.MyUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;
import org.controlsfx.control.PopOver;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.MinguoChronology;
import java.util.ResourceBundle;

public class WidgetDateTime implements Initializable {
    static Integer YEAR_OFFSET = 1911;
    LocalDate date = LocalDate.now();
    LocalTime time = LocalTime.now();

    PopOver popOver;
    DatePicker dpDate;
    @FXML private JFXCheckBox checkBoxTime;
    @FXML private JFXTextField tfTime;
    @FXML private JFXTextField tfDate;
    @FXML private JFXButton btnDate;
    @FXML private HBox hBoxDate;
    @FXML private HBox hBoxTime;
    @FXML private HBox hBoxDateTime;
    private Node root;

    private boolean isValid = true;
    private boolean isRangeStart = true;

    final static EventHandler<KeyEvent> keyTypeEvent = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (!event.getCharacter().matches("\\d+|\\\\|\\/|\\-|\\:|\\s+")) {
                event.consume();
            }
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkBoxTime.setText("");
        btnDate.setText("");
        // time
        tfTime.addEventFilter(KeyEvent.KEY_TYPED, keyTypeEvent);
        tfTime.setText(String.format("%02d:%02d", time.getHour(), time.getMinute()));
        tfTime.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue != null && newValue == false) {
                    String stime = tfTime.getText();
                    int val[] = {0,0,0};
                    val = MyUtil.getTimeDifference(stime);
                    time = LocalTime.now().plusHours(val[0]).plusMinutes(val[1]).plusSeconds(val[2]);
                    String strTime = String.format("%02d:%02d", time.getHour(), time.getMinute());
                    if (time.getSecond() == 0) {
                        tfTime.setText(strTime);
                    } else {
                        tfTime.setText(String.format("%s:%02d", strTime, time.getSecond()));
                    }
                }
            }
        });
        // date
        dpDate = new DatePicker(date);
        tfDate.addEventFilter(KeyEvent.KEY_TYPED, keyTypeEvent);
        tfDate.setText(String.format("%d/%02d/%02d", date.getYear()-YEAR_OFFSET, date.getMonthValue(), date.getDayOfMonth()));
        dpDate.setChronology(MinguoChronology.INSTANCE);
        DatePickerSkin datePickerSkin = new DatePickerSkin(dpDate);
        popOver = new PopOver(datePickerSkin.getPopupContent());
        popOver.setDetachable(false);
        popOver.setArrowSize(0);
        popOver.setArrowLocation(PopOver.ArrowLocation.TOP_RIGHT);
        popOver.setOnCloseRequest(event -> {
            popOver.hide();
        });
        btnDate.setOnAction(event -> {
            if (popOver.isShowing()) {
                popOver.hide();
            } else {
                popOver.show(btnDate);
            }
        });
        dpDate.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                if (newValue != null) {
                    String minguoDate = MyUtil.convert2MingGoug(newValue);
                    tfDate.setText(minguoDate);
                }
            }
        });
        tfDate.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue != null && newValue == false) {
                    String newDate = tfDate.getText();
                    dpDate.setValue(MyUtil.getAdjustedDate(newDate));
                    LocalDate localDate = dpDate.getValue();
                    String minguoDate = MyUtil.convert2MingGoug(localDate);
                    tfDate.setText(minguoDate);
                }
            }
        });
        checkBoxTime.setSelected(true);
        checkBoxTime.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                boolean flag = (newValue != null && newValue);
                tfTime.setDisable(!flag);
            }
        });
    }

    public WidgetDateTime() {
        URL loc = CommonCache.getInstance().getDateTimeFxmlURL();
        //System.out.println("loc:++ "+loc);
        FXMLLoader fxmlLoader = new FXMLLoader(loc);
        try {
            fxmlLoader.setController(this);
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTimeBlock(boolean enableTimeBlock) {
        hBoxTime.setManaged(enableTimeBlock);
        hBoxTime.setVisible(enableTimeBlock);
        checkBoxTime.setManaged(enableTimeBlock);
        checkBoxTime.setVisible(enableTimeBlock);
    }

    public void setTimeBlockVisible(boolean visible) {
        checkBoxTime.setManaged(visible);
        checkBoxTime.setVisible(visible);
    }

    public HBox getDateTimeHBox() {
        return hBoxDateTime;
    }

    public Node getRoot() { return root; }

    public HBox gethBoxDate() { return hBoxDate; }

    public LocalDateTime getDateTime() {
        if (isValid) {
            boolean isSelected = checkBoxTime.isSelected();
            String tfStr = tfTime.getText();
            LocalTime localTime = isSelected ?
                    LocalTime.parse(tfStr) : isRangeStart ?
                    LocalTime.of(0,0,0) :
                    LocalTime.of(23,59,59);
            LocalDateTime ret = LocalDateTime.of(dpDate.getValue(), localTime);
            System.out.println(ret);
            return ret;
        } else {
            return null;
        }
    }

    public void setDateTime(LocalDateTime dateTime) {
        if (dateTime != null) {
            isValid = true;
            dpDate.setValue(dateTime.toLocalDate());
            String timeStr = String.format("%02d:%02d:%02d", dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond());
            tfTime.setText(timeStr);
        } else {
            isValid = false;
        }
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public void setRangeStart(boolean start) {
        isRangeStart = start;
    }
}
