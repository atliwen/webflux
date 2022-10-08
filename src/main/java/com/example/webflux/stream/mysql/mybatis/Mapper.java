package com.example.webflux.stream.mysql.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @author 李文
 * @create 2022-06-22 13:53
 **/
@org.apache.ibatis.annotations.Mapper
public interface Mapper<Object> extends BaseMapper<Object>
{

    /**
     * 通用查询
     *
     * @return
     */
    @Select("${sql}")
    List<Map<String, Object>> select(String sql, List<Map<String, Object>> data);

    /**
     * 新增
     *
     * @param data
     * @return
     */
    @Insert("${sql}")
    int insert(String sql, List<Map<String, Object>> data);

    /**
     * 修改
     *
     * @param data
     * @return
     */
    @Update("${sql}")
    int update(String sql, List<Map<String, Object>> data);

    /**
     * 删除
     *
     * @param data
     * @return
     */
    @Delete("${sql}")
    int delete(String sql, List<Map<String, Object>> data);

}
