package com.example.webflux.stream.mysql.mybatis;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 李文
 * @create 2022-06-22 17:20
 **/
@Service
public class ServiceImpl
        extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<Mapper<Object>, Object>
        implements IService
{

    private final Mapper m;

    public ServiceImpl(Mapper m) {
        this.m = m;
    }

    @Override
    public List<Map<String, Object>> select(String sql, List<Map<String, Object>> data) {
        return m.select(sql,data);
    }

    @Override
    public int insert(String sql, List<Map<String, Object>> data) {
        return m.insert(sql,data);
    }

    @Override
    public int update(String sql, List<Map<String, Object>> data) {
        return m.update(sql,data);
    }

    @Override
    public int delete(String sql, List<Map<String, Object>> data) {
        return m.delete(sql,data);
    }
}
