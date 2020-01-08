package com.github.jayu.db2excel;

import com.github.jayu.db2excel.utils.DbUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jay on 2020/1/8 10:04
 */

public class App {

    public static void main(String[] args) {

//        final String path = "G:/log/a/f.xls";
//        List<Object> list = testData();
//
//        ExcelUtils.export(list, path);

        final String sql = "select menu_code from t_sys_menu";
        DbUtils.query(sql);

    }

    static List<Object> testData(){
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> row = new HashMap<>();
        row.put("姓名", "余杰");
        row.put("年龄", 23);
        row.put("密码", "123456");
        list.add(row);
        return list;
    }

}
