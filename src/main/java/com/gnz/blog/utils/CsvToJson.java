package com.gnz.blog.utils;

import java.io.*;
import java.util.Arrays;
import java.util.List;
// CSV 转 json
// 使用csvToJSon对象的.ConvertToJson方法 带入csv文件路径及导出路径。
public class CsvToJson {
    private List<String> stringToList(String s, String sep) {
        if (s == null) {
            return null;
        }
        String[] parts = s.split(sep);
        return Arrays.asList(parts);
    }
    //将分割的第一行的表头list和后面的值list进行拼接
    //拼接完后以string返回
    //示例如{"name" : "kevin","sex" : "man"}
    private String stringToJson(List<String> header, List<String> lineData) throws Exception {

        if (header == null || lineData == null) {
            throw new Exception("输入不能为null。");
        }
//        else if (header.size() != lineData.size()) {
//            throw new Exception("表头个数和数据列个数不等。");
//        }
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("{ ");
        for (int i = 0; i < header.size(); i++) {
            String mString = String.format("\"%s\": \"%s\"", header.get(i), lineData.get(i));
            sBuilder.append(mString);
            if (i != header.size() - 1) {
                sBuilder.append(", ");
            }
        }
        sBuilder.append(" },");
        return sBuilder.toString();
    }
    public void ConvertToJson(InputStream filePath, OutputStream outPutPath) throws Exception {
        //创建BufferedReader 来读文件
        //创建使用BufferedWriter 来写文件
        InputStreamReader isr = new InputStreamReader(filePath,"utf-8");
        BufferedReader reader = new BufferedReader(isr);
        OutputStreamWriter osw = new OutputStreamWriter(outPutPath,"utf-8");
        BufferedWriter writer = new BufferedWriter(osw);
        try {
            String sep = ",";
            //将csv表格第一行构建成string
            String headerStr = reader.readLine();
            if (headerStr.trim().isEmpty()) {
                System.out.println("表格头不能为空");
                return;
            }
            //将String字符串通过split（","）csv是以，作分隔符
            // 进行切割输出成List
            List<String> header = stringToList(headerStr, sep);
            String line;
            int lineCnt = 1;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    System.out.println("第" + lineCnt + "行为空，已跳过");
                    continue;
                }

                List<String> lineData = stringToList(line, sep);

//                if (lineData.size() != header.size()) {
//                    String mString = String.format("第%d行数据列和表头列个数不一致\r\n%s", lineCnt, line);
//                    System.err.println(mString);
//                    break;
//                }

                String jsonStr = stringToJson(header, lineData);
                writer.write(jsonStr);
                writer.write("\r\n");
                lineCnt++;
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        //使用Java IO流操作打开/导出文件
        InputStream filePath = new FileInputStream("gnz.csv");
        OutputStream outPutPath = new FileOutputStream("gnz.json");
        CsvToJson csvToJSon = new CsvToJson();
        csvToJSon.ConvertToJson(filePath, outPutPath);
        System.out.println("转换完成");
    }
}
