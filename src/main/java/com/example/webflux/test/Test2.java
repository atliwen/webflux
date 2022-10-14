package com.example.webflux.test;

import com.alibaba.fastjson.JSON;
import com.example.webflux.router.dome.HttpServeData;

/**
 * @author 李文
 * @create 2022-10-14 14:03
 **/
public class Test2
{

   static String json= "{\"urlPath\":\"dsdsds\",\"httpMethod\":\"GET\",\"contentType\":\"x-www-form-urlencoded\",\"queryParam\":{\"111\":\"2\"},\"bodyMap\":{\"11\":\"2\",\"1111\":\"3\"},\"headers\":{\"1111\":\"3\"}}";


    public static void main(String[] args) {

        HttpServeData d= new HttpServeData();
        d=JSON.parseObject(json,HttpServeData.class);
        System.out.println(JSON.toJSONString(d));
    }
}
