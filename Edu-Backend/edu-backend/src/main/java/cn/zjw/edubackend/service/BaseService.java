package cn.zjw.edubackend.service;

import cn.zjw.edubackend.pojo.po.BaseEntity;

import java.util.List;

public interface BaseService<T extends BaseEntity> {
    List<T> selectList(T t);
    List<T> selectListFuzzy(T t);
    T selectById(Integer id);
//    boolean updateById(T t);
    boolean updateByIdSelective(T t);
//    boolean insert(T t);
    boolean insertSelective(T t);
    boolean deleteById(Integer id);
    String importData(List<T> list, boolean updateSupport);
}
