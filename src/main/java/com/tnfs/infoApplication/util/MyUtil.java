package com.tnfs.infoApplication.util;

import com.tnfs.infoApplication.dto.AccountDetailDto;
import com.tnfs.infoApplication.model.MemberObj;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import org.controlsfx.control.tableview2.TableColumn2;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class MyUtil {
    private static SVGPath okMark;
    {
        okMark = new SVGPath();
        okMark.setContent("M16 0c-8.836 0-16 7.164-16 16s7.164 16 16 16 16-7.164 16-16-7.164-16-16-16zM13.52 23.383l-7.362-7.363 2.828-2.828 4.533 4.535 9.617-9.617 2.828 2.828-12.444 12.445z");

    }

    private static Integer YEAR_OFFSET = 1911;

    private static String BASE_URL = "http://127.0.0.1/8080";


    public static boolean checkIdNumber(String idNumber) {
        final String ALPHABETS = "ABCDEFGHJKLMNPQRSTUVXYWZIO";
        final Function<Character, Integer> firstTwoDigit = c -> ALPHABETS.indexOf(c) + 10;

        boolean isTWIdNumber = idNumber.matches("[A-Z]{1}[1-2]{1}[0-9]{8}");
        boolean isTWOldResidentIdNumber = idNumber.matches("[A-Z]{1}[A-D]{1}[0-9]{8}");
        boolean isTWNewResidentIdNumber = idNumber.matches("[A-Z]{1}[89]{1}[0-9]{8}");

        /*
        * https://school.boe.ttct.edu.tw/open-message/144503/get-file/60e5308deae6537709422be3
        * https://hackmd.io/@CynthiaChuang/Check-Resident-Certificate-Number
        * http://pisa.math.ntnu.edu.tw/files/exampaper/1chapter3/133CQ_02M.pdf
        * https://gist.github.com/yyc1217/3856443
        * */
        if (idNumber != null) { // 檢查身分證字號
            if (idNumber.length() == 10) {
                if (isTWIdNumber) {
                    int id0 = firstTwoDigit.apply(idNumber.charAt(0));
                    int sum = (id0 / 10) + (id0 % 10) * 9;
                    int factor = 8;
                    for (Character ch: idNumber.substring(1).toCharArray() ) {
                        sum += Integer.parseInt(ch+"") * factor;
                        if (factor > 1) {
                            factor--;
                        }
                    }
                    return (sum % 10) == 0;
                } else if (isTWOldResidentIdNumber) { // 檢查統一證(居留證)編號 (2031/1/1停用)
                    int id0 = firstTwoDigit.apply(idNumber.charAt(0));
                    int id1 = firstTwoDigit.apply(idNumber.charAt(1));
                    int sum = (id0 / 10) + (id0 % 10) * 9;
                    sum = sum + (id1 % 10) * 8;
                    int factor = 7;
                    for (Character ch: idNumber.substring(2).toCharArray() ) {
                        sum += Integer.parseInt(ch+"") * factor;
                        if (factor > 1) {
                            factor--;
                        }
                    }
                    return (sum % 10) == 0;
                } else if (isTWNewResidentIdNumber) { // 檢查統一證(居留證)編號 (2021/1/2實施
                    int id0 = firstTwoDigit.apply(idNumber.charAt(0));
                    int sum = (id0 / 10) + (id0 % 10) * 9;
                    int factor = 8;
                    for (Character ch: idNumber.substring(1).toCharArray() ) {
                        sum += Integer.parseInt(ch+"") * factor;
                        if (factor > 1) {
                            factor--;
                        }
                    }
                    return (sum % 10) == 0;
                }
            }
        }
        return false;
    }

    public static int[] getTimeDifference(String time) {
        int val[] = {0,0,0};
        LocalTime now = LocalTime.now();
        System.out.println("time1: "+time);
        if (time != null) {
            int len = time.length();
            if (time.matches("\\d+") && len >= 4) {
                String hou = time.substring(0,2);
                String min = time.substring(2,4);
                String sec = len > 4 ? time.substring(4,len) : "0";
                System.out.println(hou + " " + min + " " + sec);
                val[2] = Integer.parseInt(sec);
                val[1] = Integer.parseInt(min);
                val[0] = Integer.parseInt(hou);
            } else {
                time = time.replaceAll("\\\\s+", ":");
                System.out.println("time2: "+time);
                int i = 0;
                Boolean flag = true;
                for (String str : time.split(":")) {
                    if (!str.matches("[0-9]+")) {
                        time = null;
                        flag = false;
                        break;
                    }
                    val[i++] = Integer.parseInt(str);
                    if (i == 3) {
                        break;
                    }
                }
            }
            val[0] = val[0] - now.getHour();
            val[1] = val[1] - now.getMinute();
            val[2] = val[2] - now.getSecond();
            System.out.println("time-diff: "+String.format("%d/%02d/%02d", val[0],val[1],val[2]));
        }
        return val;
    }

    // 因為輸入的數值可能不符合民國的規則
    // e.g. 0/11/31 => 不存在民國0年，只有民國元年or前一年，11月也不存在31
    // 因此，統一將民國轉換為西元年，與現在日期相比較，計算與現在的(1)年、(2)月、(3)日，這3者差值
    // 再利用LocalDate內建plusYears, plusMonths, plusDays計算對應修正後符合曆法的日期
    public static int[] getMinguoDateDifference(String minguoDate) { // 民國
        int val[] = {0,0,0};
        LocalDate now = LocalDate.now();
        if (minguoDate != null) {
            int len = minguoDate.length();
            System.out.println("date1: "+minguoDate); // 民國80年7月11日 => 800711
            minguoDate = minguoDate.replaceAll("\\s+", "/");
            if (minguoDate.matches("\\d+") && len >= 6 ) { // 800711
                String day = minguoDate.substring(len-2,len);
                String mon = minguoDate.substring(len-4,len-2);
                String yea = minguoDate.substring(0,len-4);
                val[2] = Integer.parseInt(day);
                val[1] = Integer.parseInt(mon);
                val[0] = Integer.parseInt(yea);
                System.out.println("1: "+val[0]+"  "+val[1]+"  "+val[2]); // 80 07 11
            } else {
                boolean neg = "-".equals(minguoDate.substring(0,1));
                minguoDate = minguoDate
                        .replaceAll("\\:", "/")
                        .replaceAll("\\-", "/")
                        .replaceAll("\\.", "/")
                        .replaceAll("\\\\", "/")
                        .replaceAll("/+", "/");
                System.out.println("date2: "+minguoDate);
                int i = 0;
                for (String str : minguoDate.split("/")) {
                    if (str.length() > 0 && str.matches("[0-9]+")) {
                        val[i++] = Integer.parseInt(str);
                    }
                    if (i == 3) {
                        break;
                    }
                }
                if (neg) { val[0] = val[0] * -1; }
                System.out.println("1: "+val[0]+"  "+val[1]+"  "+val[2]);
            }
            // 民國-2, -1, 1, 2 => 西元 1911-1,1911,1911+1,1911+2
            val[0] = (val[0] >= 0 ? val[0] : val[0] + 1) + YEAR_OFFSET - now.getYear();
            val[1] = val[1] - now.getMonthValue();
            val[2] = val[2] - now.getDayOfMonth();
            System.out.println("date-diff: "+String.format("%d/%02d/%02d", val[0],val[1],val[2]));
        }
        return val;
    }

    // 取得與現在的(1)年、(2)月、(3)日，這3者差值後，
    // 再利用LocalDate內建plusYears, plusMonths, plusDays計算對應修正後符合曆法的西元日期
    public static LocalDate getAdjustedDate(String newMinguoDate) {
        int val[] = {0,0,0};
        val = getMinguoDateDifference(newMinguoDate); // year, month, day
        return LocalDate.now().plusYears(val[0]).plusMonths(val[1]).plusDays(val[2]);
    }

    public static boolean isValidIdNumber(String idNumber) {
        return true;
    }

    public static AccountDetailDto validateAndGetLoginAccount(String username, String password) throws IOException {

        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        Map<String, String> params = new HashMap<>();
        params.put("name", username);
        params.put("password", password);
        conn.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.writeBytes(ParameterStringBuilder.getParameterString(params));
        out.flush();
        out.close();
        conn.setRequestProperty("Content-Type", "application/json");
//        String contentType = conn.getHeaderField("Content-Type");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        return null;
    }

    public static void makeHeaderWrappable(TableColumn2 col) {
        Label label = new Label(col.getText());
        label.setStyle("-fx-padding: 8px");
        label.setWrapText(true);
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);

        StackPane stack = new StackPane();
        stack.getChildren().add(label);
        stack.prefWidthProperty().bind(col.widthProperty());
        label.prefWidthProperty().bind(stack.prefWidthProperty());
        col.setText(null);
        col.setGraphic(stack);
    }

    public static <S, T> TableColumn2<S, T>
    createColumn(String title, double width,
                 Callback<TableColumn2.CellDataFeatures<S,T>, ObservableValue<T>> value) {
        TableColumn2<S, T> col = new TableColumn2<>(title);
        col.setCellValueFactory(value);
        col.setPrefWidth(width);
        /*Make JavaFx Text wrap in TableCell
        * ref: https://stackoverflow.com/questions/61814956/javafx-text-wrap-in-tablecell
        * */
        col.setCellFactory(factory -> {
            TableCell<S,T> cell = new TableCell<S,T>() {
                Text text = new Text();
                @Override
                protected void updateItem(T item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                        return;
                    }
                    text.setWrappingWidth(getTableColumn().getWidth()-5);
                    text.setText(item.toString());
                    setGraphic(text);
                }
            };
            return cell;
        });
        makeHeaderWrappable(col);
        /*
        col.setStyle("-fx-text-alignment:center");
        col.setStyle("-fx-background-color: #0000ff");*/
        return col;
    }

    public static String convert2MingGoug(LocalDateTime dateTime) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String minguoDateTime = "";
        if ( dateTime != null) {
            String startFormatter = dateTime.format(formatter);
            int year = Integer.parseInt(startFormatter.substring(0,4)) - 1911;
            if (year <= 0) { // 民國-2, -1, 1, 2 => 西元 1911-1,1911,1911+1,1911+2 (不存在民國0年)
                year--;
            }
            int len = startFormatter.length();
            String str = startFormatter.substring(17);
            int lastInd = Integer.parseInt(str) == 0 ? len-3 : len;
            minguoDateTime = year + startFormatter.substring(4, lastInd);
        }
        return minguoDateTime;
    }

    public static String convert2MingGoug(LocalDate date) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String convert = "";
        if ( date != null) {
            String startFormatter = date.format(formatter);
            int year = Integer.parseInt(startFormatter.substring(0,4)) - 1911;
            if (year <= 0) { // 民國-2, -1, 1, 2 => 西元 1911-1,1911,1911+1,1911+2 (不存在民國0年)
                year--;
            }
            convert = year + startFormatter.substring(4);
        }
        return convert;
    }
}
