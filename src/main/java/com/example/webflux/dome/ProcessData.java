package com.example.webflux.dome;

import lombok.Data;

/**
 * @author 李文
 * @create 2022-06-30 15:11
 **/
@Data
public class ProcessData
{

    private String name;

    private Object data;

    private ProcessData next;

    public ProcessData() {
    }

    public ProcessData(String name, Object data, ProcessData next) {
        this.name = name;
        this.data = data;
        this.next = next;
    }
}
