package cn.zjw.edubackend.service;

import cn.zjw.edubackend.pojo.po.EduAdmin;

public interface AdminService extends BaseService<EduAdmin> {
    boolean resetPassword(Integer id);
}
