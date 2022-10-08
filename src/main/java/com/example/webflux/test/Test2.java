package com.example.webflux.test;


import com.alibaba.fastjson2.JSON;
import com.example.webflux.router.dome.HttpServeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 李文
 * @create 2022-06-30 14:52
 **/
public class Test2
{

   static Logger log = LoggerFactory.getLogger("aa");

    public static void main(String[] args)  {
        HttpServeData t=new HttpServeData();
        t.setHttpMethod("GET");

        System.out.println(JSON.toJSONString(t));


    }

}
