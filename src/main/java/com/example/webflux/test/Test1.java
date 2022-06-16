package com.example.webflux.test;

import com.alibaba.fastjson2.JSON;
import com.example.webflux.stream.FlatMap;
import com.example.webflux.stream.HttpWebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 李文
 * @create 2022-06-16 17:04
 **/
public class Test1
{

    public static void main(String[] args) {
        HttpWebClient.Data data = getData();

        Map<String, Object> map = new HashMap<>();
        map.put("user", "atliwen");

        Mono<Map> m = Mono.just(map);

        m = m.flatMap(c -> HttpWebClient.performed(data, c));

        //m = m.doOnError(c -> System.out.println("a-----------------a  " + c.getMessage()));

        FlatMap.Data flat = new FlatMap.Data();

        String a = " for (Map.Entry<String, Object> entry : data.entrySet()) {\n" +
                "            println entry.getKey()\n" +
                "            println entry.getValue()\n" +
                "        }\n" +
                "        return data";
        flat.setGroovyTest(a);

        System.out.println(FlatMap.performed(flat, map).block());


        m = m.flatMap(c -> FlatMap.performed(flat, c));
        Object o = m.block();
        System.out.println(JSON.toJSONString(o));

    }


    static HttpWebClient.Data getData() {
        HttpWebClient.Data data = new HttpWebClient.Data();
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
