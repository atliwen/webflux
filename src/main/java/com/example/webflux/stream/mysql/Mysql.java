package com.example.webflux.stream.mysql;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.example.webflux.stream.Sos;
import com.example.webflux.stream.mysql.mybatis.Mapper;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyShell;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.*;

/**
 * @author 李文
 * @create 2022-06-22 14:19
 **/
@Component
public class Mysql implements Sos
{
    final Mapper mapper;

    public Mysql(Mapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 具体执行方法
     *
     * @param data    配置数据
     * @param dataMap 上个方法执行结果
     * @return 相应的数据
     */
    public Mono<List<Map<String, Object>>> performed(Object data, List<Map<String, Object>> dataMap) {

        MysqlData newData = (MysqlData) data;
        DynamicDataSourceContextHolder.push(newData.getDatasourceName());//数据源名称

        String sql = newData.getSql();
        if (newData.getSqlType() == 1) {
            sql = executeGroovy(newData, dataMap);
        }
        if (newData.getExecuteType() == 0) {
            return Mono.just(mapper.select(sql, dataMap));
        }

        List<Map<String, Object>> outputs = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        if (newData.getExecuteType() == 1) {
            map.put("rows", mapper.update(sql, dataMap));
            outputs.add(map);
        }
        if (newData.getExecuteType() == 2) {
            map.put("rows", mapper.delete(sql, dataMap));
            outputs.add(map);
        }
        if (newData.getExecuteType() == 3) {
            map.put("rows", mapper.insert(sql, dataMap));
            outputs.add(map);
        }

        DynamicDataSourceContextHolder.push("master");//数据源名称
        return Mono.just(outputs);
    }

    static GroovyClassLoader loader = new GroovyClassLoader();

    public  String executeGroovy(MysqlData data, List<Map<String, Object>> dataMap) {
        GroovyCodeSource gcs = new GroovyCodeSource(data.getSql(), UUID.randomUUID() + ".groovy", "/groovy/shell");
        loader.parseClass(gcs, true);
        // 应为入参多线程的问题，每次都得创建新的，只能优化到 GroovyClassLoader自己 缓存了。
        GroovyShell newShell = new GroovyShell(loader);
        newShell.setVariable("data", dataMap);
        String sql = (String) newShell.evaluate(data.getSql());
        return sql;
    }
}
