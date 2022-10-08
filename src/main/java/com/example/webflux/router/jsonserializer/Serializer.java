package com.example.webflux.router.jsonserializer;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.writer.ObjectWriter;

import java.lang.reflect.Type;

/**
 * @author 李文
 * @create 2022-07-01 15:32
 **/
public class Serializer implements ObjectWriter<Long>
{
    @Override
    public void write(JSONWriter jsonWriter, Object o, Object o1, Type type, long l) {
        if(o==null){
            jsonWriter.writeNull();
        }else{
            String strVal = o.toString();
            jsonWriter.writeString(strVal);
        }
    }
}
