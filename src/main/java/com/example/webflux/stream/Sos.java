package com.example.webflux.stream;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * @author 李文
 * @create 2022-06-22 17:04
 **/
public interface Sos
{
    Mono<List<Map<String, Object>>> performed(Object data, List<Map<String, Object>> dataMap);
}

