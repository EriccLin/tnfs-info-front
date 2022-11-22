package com.tnfs.infoApplication;

import com.tnfs.infoApplication.controller.LoginController;
import com.tnfs.infoApplication.controller.MainFrameController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginController loginController = new LoginController();
        loginController.showAndWait();
        MainFrameController mainFrameController = new MainFrameController(primaryStage);
        mainFrameController.show();

//        WidgetUnit widgetUnit = new WidgetUnit();
//        widgetUnit.setUnitId(1016L);
//        VBox vBox = new VBox();
//        vBox.getChildren().add(widgetUnit.getInitPane());
//        Scene scene = new Scene(vBox);
//        primaryStage.setScene(scene);
//        primaryStage.show();

    }
    public static void main(String[] args) {
//        String date = "/-12/22/11";
//
//        for (String str : date.split("/")) {
////                    if (!str.matches("[0-9]+")) {
////                        date = null;
////                        break;
////                    }
//            if (str.length()>0 && str.matches("(\\-)?[0-9]+")) {
//                System.out.println(Integer.parseInt(str));
//            }
//        }
////        System.out.println(MyUtil.checkIdNumber("S123812317"));
////        System.out.println(MyUtil.checkIdNumber("A123456789"));
////        System.out.println(MyUtil.checkIdNumber("X120356539"));
////        System.out.println(MyUtil.checkIdNumber("F126984838"));
        launch(args);
    }
}