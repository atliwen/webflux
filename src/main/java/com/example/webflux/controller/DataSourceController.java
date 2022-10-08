package com.example.webflux.controller;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.creator.DruidDataSourceCreator;
import com.baomidou.dynamic.datasource.creator.HikariDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.example.webflux.dome.DataSourceDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.Set;
@RestController
@RequestMapping("/datasources")
@Api(tags = "添加删除数据源")
public class DataSourceController {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private DefaultDataSourceCreator dataSourceCreator;
    @Autowired
    private DruidDataSourceCreator druidDataSourceCreator;
    @Autowired
    private HikariDataSourceCreator hikariDataSourceCreator;

    @GetMapping
    @ApiOperation("获取当前所有数据源")
    public Set<String> now() {

        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        return ds.getDataSources().keySet();
    }

    //通用数据源会根据maven中配置的连接池根据顺序依次选择。
    //默认的顺序为druid>hikaricp>beecp>dbcp>spring basic
    @PostMapping("/add")
    @ApiOperation("通用添加数据源（推荐）")
    public Set<String> add(@Validated @RequestBody DataSourceDTO dto) {
        DataSourceProperty dataSourceProperty = new DataSourceProperty();
        BeanUtils.copyProperties(dto, dataSourceProperty);
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        DataSource dataSource = dataSourceCreator.createDataSource(dataSourceProperty);
        ds.addDataSource(dto.getPoolName(), dataSource);
        return ds.getDataSources().keySet();


    }

    @PostMapping("/addDruid")
    @ApiOperation("基础Druid数据源")
    public Set<String> addDruid(@Validated @RequestBody DataSourceDTO dto) {
        DataSourceProperty dataSourceProperty = new DataSourceProperty();
        BeanUtils.copyProperties(dto, dataSourceProperty);
        dataSourceProperty.setLazy(true);
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        DataSource dataSource = druidDataSourceCreator.createDataSource(dataSourceProperty);
        ds.addDataSource(dto.getPoolName(), dataSource);
        return ds.getDataSources().keySet();
    }

    @PostMapping("/addHikariCP")
    @ApiOperation("基础HikariCP数据源")
    public Set<String> addHikariCP(@Validated @RequestBody DataSourceDTO dto) {
        DataSourceProperty dataSourceProperty = new DataSourceProperty();
        BeanUtils.copyProperties(dto, dataSourceProperty);
        dataSourceProperty.setLazy(true);//3.4.0版本以下如果有此属性，需手动设置，不然会空指针。
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        DataSource dataSource = hikariDataSourceCreator.createDataSource(dataSourceProperty);
        ds.addDataSource(dto.getPoolName(), dataSource);
        return ds.getDataSources().keySet();
    }

    @DeleteMapping
    @ApiOperation("删除数据源")
    public String remove(String name) {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        ds.removeDataSource(name);

        return "删除成功";


    }
}
