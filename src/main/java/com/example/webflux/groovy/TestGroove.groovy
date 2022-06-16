package com.example.webflux.groovy

import com.alibaba.fastjson.JSONObject

/**
 * ${DESCRIPTION}
 * @author 李文
 * @create 2022-06-16 14:46
 * */
class TestGroove {

    static String str = "{\"msg\":\"ua   atliwen  AA  AA{\\\"range\\\":null,\\\"id\\\":null,\\\"createBy\\\":\\\"aa\\\",\\\"createTime\\\":\\\"null\\\",\\\"updateBy\\\":\\\"null\\\",\\\"updateTime\\\":\\\"null\\\",\\\"delFlag\\\":null,\\\"uid\\\":null,\\\"name\\\":\\\"null\\\"}\",\"code\":200}";

    static void main(String[] args) {
        def mapObj = JSONObject.parseObject(str, Map.class);


        Map<String, Object> out = new HashMap<>()
        for (Map.Entry<String, Object> entry : mapObj.entrySet()) {
            println entry.getKey()
            println entry.getValue()
        }
//        return mapObj

    }

}
