package cn.zjw.edubackend.service;

import cn.zjw.edubackend.pojo.po.EduApply;

public interface ApplyService extends BaseService<EduApply> {
    void dealApply(Integer id, String type);
}
