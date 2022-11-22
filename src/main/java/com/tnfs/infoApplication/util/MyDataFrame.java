/*
* read/remove: BOM:
* ref: https://mkyong.com/java/java-how-to-add-and-remove-bom-from-utf-8-file/
* utf-8 bytes to string
* ref: https://stackoverflow.com/questions/8512121/utf-8-byte-to-string
* Zero Width Space U+200B
* ref: https://www.fileformat.info/info/unicode/char/200b/index.htm
* remove Zero Width Space U+200B/ BOM U+FEFF
* ref: https://stackoverflow.com/questions/6198986/how-can-i-replace-non-printable-unicode-characters-in-java/6199346#6199346
* ref: https://stackoverflow.com/questions/42960282/how-to-remove-u200b-zero-length-whitespace-unicode-character-from-string-in-j
* ref: https://stackoverflow.com/questions/6784799/what-is-this-char-65279
* */
package com.tnfs.infoApplication.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MyDataFrame {
    private List<Object[]> table = null;
    private Map<String, Integer> name2ind = null;
    private List<String> column_datatypes = null;
    private List<String> column_titles = null;
    private String filepath = null;

    public MyDataFrame (String filepath) {
        load(filepath);
    }

    private static String strList2line(List<String> list) {
        String line = "";
        int sz = list.size();
        for (int i = 0; i < sz; ++i) {
            String str = list.get(i);
            line += str + (i == (sz-1) ? "\n" : ", ");
//            if (i != (sz-1) )
//                ret += ", ";
//            else // (i == sz-1)
//                ret += "\n";
        }
        return line;
    }

    private static String strArr2line(String[] strArr) {
        String line = "";
        int sz = strArr.length;
        for (int i = 0; i < sz; ++i) {
            String str = strArr[i];
            line += str + (i == (sz-1) ? "\n" : ", ");
        }
        return line;
    }

    private String rowdata2line(Object[] rowdata) {
        return rowdata2line(rowdata, false);
    }

    private String rowdata2line(Object[] rowdata, Boolean treatBoolAsInt) {
        String line = "";
        int sz = rowdata.length;
        for (int i = 0; i < sz; ++i) {
            Object obj = rowdata[i];
            switch (column_datatypes.get(i)) {
                case "boolean":
                    line += treatBoolAsInt ? ((boolean)obj ? 1 : 0 ) : (boolean)obj;
                    break;
                case "int":
                    line += (int)obj;
                    break;
                case "float":
                    line += (float)obj;
                    break;
                case "double":
                    line += (double)obj;
                    break;
                case "date":    // yyyy-MM-dd
                    line += (LocalDate)obj;
                    break;
                case "datetime":// yyyy-MM-dd HH:mm:ss
                    line += (LocalDateTime)obj;
                    break;
                default: // treat it as string
                    line += (String)obj;
            }
            line += (i == (sz-1) ? "\n" : ", ");
        }
        return line;
    }

    public void writeBack() {
        writeBack(filepath);
    }

    public void writeBack(String filepath) {
        OutputStreamWriter osw = null;
        BufferedWriter writer = null;
        String line = null;
        if (column_titles == null || column_datatypes == null || table == null) {
            throw new RuntimeException("empty data!");
        }
        try {
            osw = new OutputStreamWriter(new FileOutputStream(filepath), "utf-8");
            writer = new BufferedWriter(osw);
            writer.write('\ufeff');
            line = strList2line(column_titles);
            writer.write(line);
            line = strList2line(column_datatypes);
            writer.write(line);
            Iterator<Object[]> itor = table.iterator();
            while (itor.hasNext()) {
                Object[] rowdata = itor.next();
                line = rowdata2line(rowdata, true);
                writer.write(line);
            }
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex);
//            new MessageBox(ex);
        } finally {
            try {
                if (osw != null) {
                    osw.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                System.out.println(ex);
//                new MessageBox(ex);
            }
            System.out.println("writing done!");
        }
    }

    public void load(String filepath) {
        InputStreamReader isr = null;
        BufferedReader reader = null;
        String line = null;
        int count = 0;
        int num_column = 0;
        try {
            isr = new InputStreamReader(new FileInputStream(filepath), "utf-8");
            reader = new BufferedReader(isr);

            if ( (line = reader.readLine()) != null) {
                if (hasBOM(line)) { // detect BOM (ef bb bf)
                    byte[] lineByte = line.getBytes();
                    byte[] newLineByte = new byte[lineByte.length-3];
                    ByteBuffer bb = ByteBuffer.wrap(lineByte);
                    bb.get(newLineByte, 0,3); // fetch the first 3 byte
                    bb.get(newLineByte, 0,newLineByte.length); // fetch the remaining bytes
                    line = new String(newLineByte, StandardCharsets.UTF_8); //convert back to utf-8
                }
            }

            do {
                line = line + " ";
                String items[] = line.split(",");
//                System.out.println(line+" sz:"+num_column);
                if (count>1){
                    Object[] rowdata = new Object[num_column];
                    for (int i = 0; i < num_column; ++i){
                        String value = items[i];
//                        value = items[i].replaceAll("[\\s+|　]",""); // remove all space (half/full)
                        value = value==null ? "" : items[i]
                                .replace("[　]", "").trim();
//                                .replaceAll("[　|\\n|\\t|\\r|\\p{Cf}]"," ").trim();
//                                '\u200B' & '\uFEFF' -> '\\p{Cf}' (Zero width space) characters
//                                in addition, '\uFEFF' is usually byte-order mark (BOM)
                        rowdata[i] = parse(value, column_datatypes.get(i));
                    }
                    table.add(rowdata);
                }
                else if (count == 0) {
                    column_titles = new ArrayList<>();
                    num_column = items.length;
                    name2ind = new TreeMap<>();
                    for (int i = 0; i < num_column; ++i) {
                        String column_title = items[i].trim().toLowerCase(Locale.ROOT);
                        name2ind.put(column_title, i);
                        column_titles.add(column_title);
                    }
                    table = new ArrayList<>();
                } else { // if (count ==1) {
                    column_datatypes = new ArrayList<>();
                    for (int i = 0; i < num_column; ++i) {
                        column_datatypes.add(items[i].trim().toLowerCase(Locale.ROOT));
                    }
                }
                ++count;
            } while ( (line = reader.readLine()) != null);
        } catch (IOException ex) {
            System.out.println(ex);
//            new MessageBox(ex);
        } finally {
            try {
                if (isr != null) {
                    isr.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    public boolean hasBOM(String line) {
        byte[] bom = new byte[3];
        byte[] lineByte = (line+"").getBytes();
        ByteBuffer bb = ByteBuffer.wrap(lineByte);
        bb.get(bom, 0, 3);
        // BOM encoded as ef bb bf
        String content = String.format("%02x%02x%02x", bom[0],bom[1],bom[2]);
        return "efbbbf".equalsIgnoreCase(content);
    }

    private static Object parse(String value, String type) {
        switch (type) {
            case "boolean":
                return value.length() == 0 ? false : Integer.parseInt(value)>0;
            case "int":
                return value.length() == 0 ? 0 : Integer.parseInt(value);
            case "float":
                return value.length() == 0 ? 0.f : Float.parseFloat(value);
            case "double":
                return value.length() == 0 ? 0. : Double.parseDouble(value);
            case "date":    // yyyy-MM-dd
                return value.length() == 0 ? null : LocalDate.parse(value);
            case "datetime":// yyyy-MM-dd HH:mm:ss
                return value.length() == 0 ? null : LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            default: // treat it as string
                return value;
        }
    }

    public List<String> getColumnNames() { return new ArrayList<>(name2ind.keySet()); }

    public <T> List<T> getColumn(String name) {
        List<T> ret = new ArrayList<>();
        Integer col_index = name2ind.get(name);
        if (col_index == null) {
            throw new RuntimeException("illegal column name(" + name + ")");
        }
        table.forEach(row->ret.add((T)row[col_index]) );
        return ret;
    }

    public <T, S> List<T> getColumn(List<Integer> list, String name) {
        List<T> ret = new ArrayList<>();
        Integer col_index = name2ind.get(name);
        if (col_index == null) {
            throw new RuntimeException("illegal column name(" + name + ")");
        }
        if (list == null) {
            throw new RuntimeException("illegal input list");
        }
        int sz = Math.min(table.size(), list.size());
        for (int i = 0; i < sz; ++i) {
            if (list.get(i) != 0) {
                ret.add((T) table.get(i)[col_index]);
            }
            //ret.add((T) table.get(i)[col_index]);
        }
        return ret;
    }

    public <T> T get(int row, String name) {
        Integer col_index = name2ind.get(name);
        if ( col_index == null) {
            throw new RuntimeException("invalid column name(" + name + ")");
        }
        if ( row >= table.size() || row < 0 ) {
            throw new RuntimeException("invalid row index(" + row + ")");
        }
        return (T)table.get(row)[col_index];
    }

    public <T> void set(int row, String name, T value) {
        Integer col_index = name2ind.get(name);
        if ( col_index == null) {
            throw new RuntimeException("invalid column name(" + name + ")");
        }
        if ( row >= table.size() || row < 0 ) {
            throw new RuntimeException("invalid row index(" + row + ")");
        }
        table.get(row)[col_index] = value;
    }

    public void set(int row, String name, String value) {
        Integer col_index = name2ind.get(name);
        if ( col_index == null) {
            throw new RuntimeException("invalid column name(" + name + ")");
        }
        if ( row >= table.size() || row < 0 ) {
            throw new RuntimeException("invalid row index(" + row + ")");
        }
        table.get(row)[col_index] = parse(value, column_datatypes.get(col_index));
    }

    @Override
    public String toString() {
        if (column_titles == null) {
            return null;
        }
        String ret = "";
        ret += strList2line(column_titles);
        ret += strList2line(column_datatypes);
        Iterator<Object[]> itor = table.iterator();
        while (itor.hasNext()) {
            Object[] rowdata = itor.next();
            ret += rowdata2line(rowdata);
        }
        return ret;
    }

    public static void test() {
        String configFile = System.getProperty("user.dir") + "/data.csv";
        System.out.println("data-file-path: "+configFile);
        MyDataFrame mdf = new MyDataFrame(configFile);
        mdf.writeBack(System.getProperty("user.dir") + "/data2.csv");
        System.out.println(mdf);
        System.out.println(mdf.getColumn("人"));
        System.out.println(mdf.getColumn(mdf.getColumn("重大刑案"),"案類"));
    }
}
