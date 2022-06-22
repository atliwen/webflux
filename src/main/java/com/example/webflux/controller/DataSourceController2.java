//package com.example.webflux.controller;
//
//import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
//import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
//import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
//import org.springframework.beans.BeanUtils;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//import java.util.Set;
//
//@RestController
//@RequestMapping("/datasources")
//public class DataSourceController2
//{
//
//    @Resource
//    private DataSource dataSource;
//    @Resource
//    private DefaultDataSourceCreator dataSourceCreator;
//
//    @GetMapping("list")
//    public Set<String> list() {
//        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
//        return ds.getDataSources().keySet();
//    }
//
//    @PostMapping("add")
//    public Set<String> add(@Validated @RequestBody DataSourceProperty dto) {
//        DataSourceProperty dataSourceProperty = new DataSourceProperty();
//        dataSourceProperty.getPoolName();
//        BeanUtils.copyProperties(dto, dataSourceProperty);
//        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
//        DataSource dataSource = dataSourceCreator.createDataSource(dataSourceProperty);
//        ds.addDataSource(dto.getPoolName(), dataSource);
//        return ds.getDataSources().keySet();
//    }
//
//    @DeleteMapping("remove")
//    public void remove(String name) {
//        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
//        ds.removeDataSource(name);
//    }
//}
