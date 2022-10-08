package com.example.webflux.router;

import com.example.webflux.dome.ProcessData;
import com.example.webflux.router.dome.HttpServeData;
import com.example.webflux.stream.Sos;
import com.example.webflux.stream.convert.ConvertData;
import com.example.webflux.stream.mysql.MysqlData;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import java.util.HashMap;
import java.util.Map;

// 注册路由
@Configuration
public class RouterFunctionConfiguration
{
    DefaultHttpServeHandler httpServeHandler;
    final
    Map<String, Sos> sosServiceMap;

    public RouterFunctionConfiguration(DefaultHttpServeHandler httpServeHandler, ConfigurableApplicationContext context) {
        this.httpServeHandler = httpServeHandler;
        this.sosServiceMap = context.getBeansOfType(Sos.class);
    }

    @SuppressWarnings("rawtypes")
    @Bean
    public RouterFunction routerFunction(ConfigurableApplicationContext app) {

        RouterFunction r = RouterFunctions.route(
                RequestPredicates.GET("/test")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                httpServeHandler);

        ProcessData dat = getData();
        HttpServeData serveData = (HttpServeData) dat.getData();
        HttpServeHandler handler = new HttpServeHandler(dat, sosServiceMap);

        r = r.andRoute(RequestPredicates.GET(serveData.getUrlPath())
                .and(RequestPredicates.accept(serveData.getContentTypeMediaType())), handler);

        return r;

    }

    public ProcessData getData() {
        // 创建API 接口
        ProcessData data = new ProcessData();
        data.setName("API");
        HttpServeData serveData = new HttpServeData();
        serveData.setContentType("x-www-form-urlencoded");
        serveData.setUrlPath("t1");
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("name", "用户名");
        serveData.setQueryParam(queryParams);
        serveData.setBodyMap(queryParams);
        data.setData(serveData);


        ConvertData flat = new ConvertData();
        flat.setGroovyTest("      Map map1 = new HashMap();\n" +
                "        map1.put(\"out\",\"ocode\");\n" +
                "        return map1; ");
        ProcessData groovyData = new ProcessData("flatMapService", flat, null);
        data.setNext(groovyData);


        //  构建第三步   查询数据库
        MysqlData d1 = new MysqlData();
        d1.setDatasourceName("master");
        // 0 ：Select ， 1 update , 2 delete  3 insert
        d1.setExecuteType(0);
        // 0 Mybatis XML  1 拼接SQL
        d1.setSqlType(0);
        d1.setSql(" SELECT * FROM `test1` ");
        ProcessData mysqlData = new ProcessData("mysqlService", d1, null);
        groovyData.setNext(mysqlData);
        return data;
    }
}
