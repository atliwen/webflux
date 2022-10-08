package com.example.webflux.stream.convert;

import com.example.webflux.stream.Sos;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyShell;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.*;

/**
 * 动态转换
 *
 * @author 李文
 * @create 2022-06-16 15:22
 **/
@Component
public class FlatMapService implements Sos
{
    public Mono<List<Map<String, Object>>> performed(Object data, List<Map<String, Object>> dataMap) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Map<String, Object> map : dataMap) {
            list.add(performed(data, map).block());
        }
        return Mono.just(list);
    }


    GroovyClassLoader loader = new GroovyClassLoader();

    /**
     * 具体执行方法
     *
     * @param data    配置数据
     * @param dataMap 上个方法执行结果
     * @return 相应的数据
     */
    public Mono<HashMap> performed(Object data, Map<String, Object> dataMap) {
        ConvertData newData = (ConvertData) data;
        GroovyCodeSource gcs = new GroovyCodeSource(newData.getGroovyTest(), UUID.randomUUID() + ".groovy", "/groovy/shell");
        loader.parseClass(gcs, true);
        // 每次都需求创建新的，只能优化到 GroovyClassLoader自己 缓存了。
        GroovyShell newShell = new GroovyShell(loader);
        dataMap.forEach(newShell::setVariable);
        newShell.setVariable("data", dataMap);
        HashMap output = (HashMap) newShell.evaluate(newData.getGroovyTest());
        return Mono.just(output);
    }


    public static void main(String[] args) {

        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("a", "a");
        dataMap.put("b", "b");
        Binding binding = new Binding();

        binding.setVariable("mapObj", dataMap);
        GroovyShell groovyShell = new GroovyShell(binding);
        String a = " for (Map.Entry<String, Object> entry : mapObj.entrySet()) {\n" +
                "            println entry.getKey()\n" +
                "            println entry.getValue()\n" +
                "        }\n" +
                "        return mapObj";
        Object o = groovyShell.evaluate(a);
        System.out.println(o.getClass());
        System.out.println(o);
    }

}
