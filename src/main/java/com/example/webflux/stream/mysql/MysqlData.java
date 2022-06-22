package com.example.webflux.stream.mysql;

/**
 * @author 李文
 * @create 2022-06-22 16:26
 **/
@lombok.Data
public class MysqlData
{
    private String datasourceName;

    /**
     * 执行的SQL语句
     */
    private String sql;

    /**
     * 类型  脚本语言自己拼接SQL  与  mybatis XML
     * 0 XML 1 拼接SQL
     */
    private Integer sqlType;

    /**
     * 执行SQL的类型
     * 0 ：Select ， 1 update , 2 delete
     */
    private Integer executeType;


}
