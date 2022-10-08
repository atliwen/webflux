package com.example.webflux.dome;

import com.example.webflux.stream.Sos;
import lombok.Data;

/**
 * @author 李文
 * @create 2022-10-08 11:22
 **/
@Data
public class NextSosList
{
    private Object data;
    private Sos sos;

    public NextSosList() {
    }

    public NextSosList(Object data, Sos sos) {
        this.data = data;
        this.sos = sos;
    }
}
