package com.example.webflux.stream;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * 动态转换
 *
 * @author 李文
 * @create 2022-06-16 15:22
 **/
public class FlatMap
{

    public static class Data
    {
        String groovyTest;

        public String getGroovyTest() {
            return groovyTest;
        }

        public Data setGroovyTest(String groovyTest) {
            this.groovyTest = groovyTest;
            return this;
        }
    }

    /**
     * 具体执行方法
     *
     * @param data    配置数据
     * @param dataMap 上个方法执行结果
     * @return 相应的数据
     */
    public static Mono<HashMap> performed(Object data, Map<String, Object> dataMap) {
        Binding binding = new Binding();
        dataMap.forEach(binding::setVariable);
        binding.setVariable("data", dataMap);
        GroovyShell groovyShell = new GroovyShell(binding);
        Data newData = (Data) data;
        HashMap output = (HashMap) groovyShell.evaluate(newData.getGroovyTest());
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
