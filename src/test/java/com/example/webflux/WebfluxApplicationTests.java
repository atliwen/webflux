package com.example.webflux;

import com.alibaba.fastjson.JSON;
import com.example.webflux.stream.convert.ConvertData;
import com.example.webflux.stream.convert.FlatMapService;
import com.example.webflux.stream.mysql.MysqlService;
import com.example.webflux.stream.mysql.MysqlData;
import com.example.webflux.stream.mysql.mybatis.Mapper;
import com.example.webflux.stream.mysql.mybatis.ServiceImpl;
import com.example.webflux.stream.webclient.HttpWebClientService;
import com.example.webflux.stream.webclient.WebClientData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class WebfluxApplicationTests
{


    @Autowired
    Mapper mp;

    @Autowired
    ServiceImpl im;

    @Test
    void contextLoads() {

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> m = new HashMap<>();
        m.put("uid", 10);
        list.add(m);

        Object o = mp.select("select * from test3  where id = #{data[0].uid} ", list);
        System.out.println(JSON.toJSONString(o));

        int i = mp.delete("delete from test3 where id = #{data[0].uid} ", list);

        System.out.println(i);
    }

    @Autowired
    MysqlService mysqlService;

    @Test
    void test1() {


        //初始化参数  模拟获取请求全部数据

        Map<String, Object> map = new HashMap<>();
        map.put("user", "atliwen");
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map);

        //创建链路对象
        Mono<List<Map<String, Object>>> m = Mono.just(list);

        //构建 第一步  发送请求

        WebClientData data = getData();
        HttpWebClientService httpWebClientService = new HttpWebClientService();
        m = m.flatMap(c -> httpWebClientService.performed(data, c));


        // 构建第二步 转换数据
        ConvertData flat = new ConvertData();
        flat.setGroovyTest("     " +
                "        Map map1 = new HashMap() \n" +
                "        map1.put(\"out\",code) \n" +
                "        return map1  ");

        m = m.flatMap(c -> new FlatMapService().performed(flat, c));

        //m = m.doOnNext(c -> {
        //    for (Map<String, Object> ma : c) {
        //        for (Map.Entry<String, Object> entry : ma.entrySet()) {
        //            System.out.println("  " + entry.getKey() + "  " + entry.getValue());
        //        }
        //    }
        //});

        //  构建第三步   插入数据库
        MysqlData d1 = new MysqlData();
        d1.setDatasourceName("master");
        // 0 ：Select ， 1 update , 2 delete  3 insert
        d1.setExecuteType(3);
        // 0 Mybatis XML  1 拼接SQL
        d1.setSqlType(0);
        d1.setSql(" INSERT INTO test3 (uid) VALUES (#{data[0].out}) ");

        m = m.flatMap(c -> mysqlService.performed(d1, c));

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
