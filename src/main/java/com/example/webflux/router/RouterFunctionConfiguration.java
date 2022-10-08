package com.example.webflux.router;

import com.example.webflux.dome.ProcessData;
import com.example.webflux.router.dome.HttpServeData;
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

    public RouterFunctionConfiguration(DefaultHttpServeHandler httpServeHandler) {
        this.httpServeHandler = httpServeHandler;
    }

    @SuppressWarnings("rawtypes")
    @Bean
    public RouterFunction routerFunction(ConfigurableApplicationContext app) {

        RouterFunction r = RouterFunctions.route(
                RequestPredicates.GET("/test")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                httpServeHandler);

        ProcessData dat = getData();
        HttpServeData serveData = (HttpServeData) dat.getNext();
        HttpServeHandler handler = new HttpServeHandler(serveData);

        r = r.andRoute(RequestPredicates.GET(serveData.getUrlPath())
                .and(RequestPredicates.accept(serveData.getContentTypeMediaType())), handler);

        return r;

    }

    public ProcessData getData() {
        ProcessData data = new ProcessData();
        data.setName("API");
        HttpServeData serveData = new HttpServeData();
        serveData.setContentType("x-www-form-urlencoded");
        serveData.setUrlPath("t1");
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("name", "用户名");
        serveData.setQueryParam(queryParams);
        serveData.setBodyMap(queryParams);

        data.setNext(serveData);
        return data;
    }
}
