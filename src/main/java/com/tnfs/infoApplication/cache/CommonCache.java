package com.tnfs.infoApplication.cache;

import com.sun.istack.internal.NotNull;
import com.tnfs.infoApplication.model.UnitObj;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import lombok.Getter;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static com.tnfs.infoApplication.model.UnitObj.UNITOBJ_COMPARATOR;

@Getter
public class CommonCache {

    private static CommonCache instance;

    private String _BASE_ = "..";
    private String _ICON_OK_ = "/image/ok-64.png";
    private String _ICON_CANCEL_ = "/image/ok-64.png";

    private ImageView imageViewOk = new ImageView(getClass().getResource(_BASE_+_ICON_OK_).toString());
    private ImageView imageViewCancel = new ImageView(getClass().getResource(_BASE_+_ICON_CANCEL_).toString());

    public URL getCaseTypeChooserURL() {
        return CommonCache.class.getResource(_BASE_+"/view/widget/NewCaseTypeChooserV2.fxml");
    }

    public URL getCaseBasicFxmlURL() {
        return CommonCache.class.getResource(_BASE_+"/view/F111CaseBasic.fxml");
    }

    public URL getDateTimeFxmlURL() {
        return CommonCache.class.getResource(_BASE_+"/view/widget/NewDateTime.fxml");
    }

    public URL getInChargeFxmlURL() {
        return CommonCache.class.getResource(_BASE_+"/view/widget/NewInCharge.fxml");
    }

    public URL getPersonInvolvedFxmlURL() {
        return CommonCache.class.getResource(_BASE_+"/view/widget/NewCasePersonInvolvedV2.fxml");
    }

    public URL getTimeLocalityFxmlURL() {
        return CommonCache.class.getResource(_BASE_+"/view/widget/NewTimeLocalityV3.fxml");
    }

    public URL getInvestigationFxmlURL() {
        return CommonCache.class.getResource(_BASE_+"/view/widget/NewInvestigation.fxml");
    }

    public URL getPersonFxmlURL() {
        return CommonCache.class.getResource(_BASE_+"/view/widget/NewPerson.fxml");
    }

    public URL getMemberFxmlURL() {
        return CommonCache.class.getResource(_BASE_+"/view/widget/NewMember.fxml");
    }

    public URL getMemberChooserFxmlURL() {
        return CommonCache.class.getResource(_BASE_+"/view/widget/NewMemberChooser.fxml");
    }


    public URL getUnitFxmlURL() {
        return CommonCache.class.getResource(_BASE_+"/view/widget/NewUnit.fxml");
    }
    public ImageView getViewOk() { return imageViewOk; }

    public ImageView getViewCancel() { return imageViewCancel; }

    public Notifications getFailedNotification(String message) {
        Notifications notifications = Notifications.create()
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(event -> {});
        notifications.text(message).graphic(CommonCache.getInstance().getViewCancel()).show();
        return notifications;
    }

    public Notifications getSuccessNotification(String message) {
        Notifications notifications = Notifications.create()
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(event -> {});
        notifications.text(message).graphic(CommonCache.getInstance().getViewOk()).show();
        return notifications;
    }

    private CommonCache() {}

    public static CommonCache getInstance() {
//        System.out.println("###: "+CommonCache.class.getResource(".."));
        if (instance == null) {
            synchronized (CommonCache.class) {
                if (instance == null) {
                    instance = new CommonCache();
                }
            }
        }
        return instance;
    }

}
