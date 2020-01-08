package com.github.jayu.db2excel.utils;

import cn.hutool.db.DbUtil;
import cn.hutool.db.sql.SqlExecutor;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * Created by Jay on 2020/1/8 11:26
 */

public class DbUtils {

    private static final DataSource dataSource;

    static {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:oracle:thin:@192.168.10.63:1521:orcl");
        druidDataSource.setUsername("itms5_gg");
        druidDataSource.setPassword("itms5");
        druidDataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        dataSource = druidDataSource;
    }

    public static List query(String sql, Object... params){
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            ResultSet rs = SqlExecutor.callQuery(conn, sql, params);
            //创建一个JSONArray对象
            JSONArray jsonArray = new JSONArray();
            //获得ResultSetMeataData对象
//            System.out.println(rs.getCl);
            ResultSetMetaData rsmd = rs.getMetaData();
            System.out.println(rsmd);
            while (rs.next()) {
                //定义json对象
                JSONObject obj = new JSONObject();
                //判断数据类型&获取value
                getType(rs, rsmd, obj);
                //将对象添加到JSONArray中
                jsonArray.add(obj);
            }
            System.out.println(jsonArray);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn != null){
                DbUtil.close(conn);
            }
        }
        return null;
    }

    private static void getType(ResultSet rs, ResultSetMetaData rsmd,
                         JSONObject obj) throws SQLException {
        int total_rows = rsmd.getColumnCount();
        for (int i = 0; i < total_rows; i++) {
            String columnName = rsmd.getColumnLabel(i + 1);
            if (obj.containsKey(columnName)) {
                columnName += "1";
            }
            try {
                switch (rsmd.getColumnType(i + 1)) {
                    case java.sql.Types.ARRAY:
                        obj.put(columnName, rs.getArray(columnName));
                        break;
                    case java.sql.Types.BIGINT:
                        obj.put(columnName, rs.getInt(columnName));
                        break;
                    case java.sql.Types.BOOLEAN:
                        obj.put(columnName, rs.getBoolean(columnName));
                        break;
                    case java.sql.Types.BLOB:
                        obj.put(columnName, rs.getBlob(columnName));
                        break;
                    case java.sql.Types.DOUBLE:
                        obj.put(columnName, rs.getDouble(columnName));
                        break;
                    case java.sql.Types.FLOAT:
                        obj.put(columnName, rs.getFloat(columnName));
                        break;
                    case java.sql.Types.INTEGER:
                        obj.put(columnName, rs.getInt(columnName));
                        break;
                    case java.sql.Types.NVARCHAR:
                        obj.put(columnName, rs.getNString(columnName));
                        break;
                    case java.sql.Types.VARCHAR:
                        obj.put(columnName, rs.getString(columnName));
                        break;
                    case java.sql.Types.TINYINT:
                        obj.put(columnName, rs.getInt(columnName));
                        break;
                    case java.sql.Types.SMALLINT:
                        obj.put(columnName, rs.getInt(columnName));
                        break;
                    case java.sql.Types.DATE:
                        obj.put(columnName, rs.getDate(columnName));
                        break;
                    case java.sql.Types.TIMESTAMP:
                        obj.put(columnName, rs.getTimestamp(columnName));
                        break;
                    default:
                        obj.put(columnName, rs.getObject(columnName));
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
