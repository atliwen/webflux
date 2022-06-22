package com.example.webflux.test;

import com.alibaba.fastjson.JSON;
import com.example.webflux.stream.convert.ConvertData;
import com.example.webflux.stream.convert.FlatMap;
import com.example.webflux.stream.mysql.MysqlData;
import com.example.webflux.stream.webclient.HttpWebClient;
import com.example.webflux.stream.webclient.WebClientData;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李文
 * @create 2022-06-16 17:04
 **/
public class Test1
{


    static String a = " for (Map.Entry<String, Object> entry : data.entrySet()) {\n" +
            "            println entry.getKey()\n" +
            "            println entry.getValue()\n" +
            "    }\n" +
            " // println ${user}  \n" +
            "return  data ";

    public static void main(String[] args) {

        MysqlData dat1a = new MysqlData();


        WebClientData data = getData();

        Map<String, Object> map = new HashMap<>();
        map.put("user", "atliwen");

        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map);


        Mono<List<Map<String, Object>>> m = Mono.just(list);
        HttpWebClient httpWebClient = new HttpWebClient();
        m = m.flatMap(c -> httpWebClient.performed(data, c));


        ConvertData flat = new ConvertData();

        flat.setGroovyTest("      Map map1 = new HashMap();\n" +
                "        map1.put(\"out\",code);\n" +
                "        return map1; ");

        m = m.flatMap(c -> new FlatMap().performed(flat, c));
        Object o = m.block();
        System.out.println(JSON.toJSONString(o));

    }


    static WebClientData getData() {
        WebClientData data = new WebClientData();
        data.setUrlPath("http://10.10.12.114:8099/test3/tt");

        data.setContentType("application/json");

        // 设置 Headers
        Map<String, String> headers = new HashMap<>();
        headers.put("ua", "ua");
        data.setHeaders(headers);

        data.setHttpMethod("GET");

        // 设置 Body
        Map<String, String> map = new HashMap<>();
        map.put("createBy", "aa");
        data.setBodyMap(map);

        // 设置 QueryParam
        Map<String, String> map2 = new HashMap<>();
        map2.put("aa", "AA");
        map2.put("pageNum", "${user}");
        map2.put("pageSize", "AA");
        data.setQueryParam(map2);
        return data;
    }

}
