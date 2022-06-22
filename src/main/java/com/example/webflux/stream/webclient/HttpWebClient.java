package com.example.webflux.stream.webclient;

import com.example.webflux.stream.Sos;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李文
 * @create 2022-06-15 11:16
 **/
@Component
public class HttpWebClient implements Sos
{

    WebClient client = WebClient.create();

    public Mono<List<Map<String, Object>>> performed(Object data, List<Map<String, Object>> dataMap) {

        List<Map<String, Object>> list = new ArrayList<>();
        for (Map<String, Object> map : dataMap) {
            list.add(performed(data, map).block());
        }
        return Mono.just(list);
    }

    /**
     * 具体执行方法
     *
     * @param data    配置数据
     * @param dataMap 上个方法执行结果
     * @return 相应的数据
     */
    public Mono<HashMap> performed(Object data, Map<String, Object> dataMap) {
        Mono<HashMap> output;
        WebClientData newData = (WebClientData) data;
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(newData.getUrlPath()).queryParams(newData.getQueryParam(dataMap)).build();
        WebClient.RequestBodySpec spec = client
                .method(newData.getHttpMethod())
                .uri(uriComponents.toUriString())
                .headers(c -> newData.getHeaders(dataMap, c))
                .contentType(newData.getContentType());
        if (newData.getBodyMap() != null) {
            output = spec.bodyValue(WebClientData.getMapValueObject(newData.getBodyMap(), dataMap)).retrieve().bodyToMono(HashMap.class);
        } else {
            output = spec.retrieve().bodyToMono(HashMap.class);
        }
        return output;
    }


}
