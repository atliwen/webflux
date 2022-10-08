package com.example.webflux.stream.mysql.mybatis;

import java.util.List;
import java.util.Map;

/**
 * @author 李文
 * @create 2022-06-22 17:19
 **/
public interface IService extends com.baomidou.mybatisplus.extension.service.IService<Object>
{
    /**
     * 通用查询
     *
     * @return
     */
    List<Map<String, Object>> select(String sql, List<Map<String, Object>> data);

    /**
     * 新增
     *
     * @param data
     * @return
     */
    int insert(String sql, List<Map<String, Object>> data);

    /**
     * 修改
     *
     * @param data
     * @return
     */
    int update(String sql, List<Map<String, Object>> data);

    /**
     * 删除
     *
     * @param data
     * @return
     */
    int delete(String sql, List<Map<String, Object>> data);

}
