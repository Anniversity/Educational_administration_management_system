package cn.zjw.edubackend.mapper;

import java.util.List;

public interface BaseMapper<T> {
    int deleteByPrimaryKey(Integer id);

    int insert(T row);

    int insertSelective(T row);

    T selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(T row);

    int updateByPrimaryKey(T row);

    List<T> selectList(T record);

    List<T> selectListFuzzy(T record);
}
