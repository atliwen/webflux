package com.example.webflux.dome;

import lombok.Data;
@Data
public class DataSourceDTO {
    //@NotBlank
    //@ApiModelProperty(value = "连接池名称", example = "db1")
    /**
     * 连接池名称
     * value = "连接池名称", example = "db1"
     */
    private String poolName;
    //@NotBlank
    //@ApiModelProperty(value = "JDBC driver", example = "com.mysql.cj.jdbc.Driver")
    /**
     *  JDBC driver
     * value = "JDBC driver", example = "com.mysql.cj.jdbc.Driver"
     */
    private String driverClassName;
    //@NotBlank
    //@ApiModelProperty(value = "JDBC url 地址", example = "jdbc:mysql://x.x.x.x:3306/x?useUnicode=true&characterEncoding=utf-8")
    /**
     * JDBC url 地址
     * value = "JDBC url 地址", example = "jdbc:mysql://x.x.x.x:3306/x?useUnicode=true&characterEncoding=utf-8"
     */
    private String url;
    //@NotBlank
    //@ApiModelProperty(value = "JDBC 用户名", example = "sa")
    /**
     *  用户名
     */
    private String username;
    //@ApiModelProperty(value = "JDBC 密码")
    /**
     *  密码
     */
    private String password;
}
