package com.example.webflux.controller;

import com.example.webflux.stream.mysql.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 李文
 * @create 2022-06-22 14:03
 **/
@RestController
@RequestMapping("/t")
public class Test
{
    @Autowired
    private Mapper m;

    @GetMapping
    public String test1() {
        Map<String, Object> map = new HashMap<>();
        map.put("id",111);
        //List list = m.select("select * from test2  where id = #{map.id}", map);
        //return JSON.toJSONString(list);
        return null;
    }

}
