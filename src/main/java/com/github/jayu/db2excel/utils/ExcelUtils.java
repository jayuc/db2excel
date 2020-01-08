package com.github.jayu.db2excel.utils;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by Jay on 2020/1/8 10:09
 */

public class ExcelUtils {

    /**
     * 导出 excel
     * @param list    数据
     * @param outputStream
     * @param isXlsx  是否是 .xlsx 格式 ， 默认 .xls
     */
    public static void export(List<Object> list, OutputStream outputStream, boolean isXlsx){
        ExcelWriter excelWriter = ExcelUtil.getWriter(isXlsx);
        excelWriter.write(list, true);
        excelWriter.flush(outputStream, true);
        excelWriter.close();
    }

    /**
     * 导出 excel
     * @param list
     * @param filePath 文件名
     */
    public static void export(List<Object> list, String filePath){
        try {
            OutputStream outputStream = new FileOutputStream(new File(filePath));
            export(list, outputStream, isXlsx(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static boolean isXlsx(String filePath){
        if(filePath.endsWith(".xlsx")){
            return true;
        }
        return false;
    }

}
