package com.example.webflux.router;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李文
 * @create 2022-06-29 13:50
 **/
@Component
public class DefaultHttpServeHandler implements HandlerFunction<ServerResponse>
{

    private static final Logger log = LoggerFactory.getLogger(DefaultHttpServeHandler.class);

    @Override
    public Mono<ServerResponse> handle(ServerRequest requests) {
        MediaType contentType = requests.headers().asHttpHeaders().getContentType();

        //  构建执行流
        Mono<List<Map<String, Object>>> out = null;


        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();

        map.put("headers", new HashMap<>(requests.headers().asHttpHeaders().toSingleValueMap()));
        map.put("queryParams", new HashMap<>(requests.queryParams().toSingleValueMap()));

        return requests.bodyToMono(String.class)
                .doOnNext(c -> {
                    System.out.println(c);
                    map.put("body", new HashMap<>(getBody(c, contentType)));
                })
                .doOnError(c -> System.out.println(c.getMessage()))
                .doOnNext(c -> {
                    list.add(map);
                }).flatMap(c ->
                        ServerResponse.ok().body(Mono.just(list), List.class)
                ).switchIfEmpty(ServerResponse.ok().body(Mono.just(isEmpty(list,map)), List.class));


    }
    private List<Map<String, Object>> isEmpty(List<Map<String, Object>> list,Map<String, Object> map) {
        list.add(map);
        return list;
    }

    private Map getBody(String originalBody, MediaType contentType) {
        try {
            if (MediaType.APPLICATION_JSON.equals(contentType) || MediaType.APPLICATION_JSON_UTF8.equals(contentType)) {
                return JSON.parseObject(originalBody, Map.class);
            }
            if (MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(contentType)) {
                return form2Map(originalBody);
            }
        } catch (Exception e) {
            log.error("getBody 异常", e);
        }
        return new HashMap();
    }

    /**
     * 表单字符串转化成 hashMap
     *
     * @param orderinfo
     * @return
     */
    public static Map form2Map(String orderinfo) {
        String listinfo[];
        HashMap<String, String> map = new HashMap<String, String>();
        listinfo = orderinfo.split("&");
        for (String s : listinfo) {
            String list[] = s.split("=");
            if (list.length > 1) {
                map.put(list[0], list[1]);
            }
        }
        return map;
    }
}
