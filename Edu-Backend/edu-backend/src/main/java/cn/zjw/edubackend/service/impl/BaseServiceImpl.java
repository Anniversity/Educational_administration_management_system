package cn.zjw.edubackend.service.impl;

import cn.zjw.edubackend.common.exception.ServiceException;
import cn.zjw.edubackend.mapper.BaseMapper;
import cn.zjw.edubackend.pojo.po.BaseEntity;
import cn.zjw.edubackend.pojo.po.EduStudent;
import cn.zjw.edubackend.service.BaseService;
import cn.zjw.edubackend.service.StudentService;
import cn.zjw.edubackend.util.bean.BeanValidators;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.List;

public class BaseServiceImpl<T extends BaseEntity, K extends BaseMapper<T>> implements BaseService<T> {
    @Autowired
    private K _mapper;

    @Autowired
    private Validator validator;

    @Override
    public List<T> selectList(T t) {
        return _mapper.selectList(t);
    }

    @Override
    public List<T> selectListFuzzy(T t) {
        return _mapper.selectListFuzzy(t);
    }

    @Override
    public T selectById(Integer id) {
        return _mapper.selectByPrimaryKey(id);
    }

    /*@Override
    public boolean updateById(T t) {
        return _mapper.updateByPrimaryKey(t) > 0;
    }*/

    @Override
    public boolean updateByIdSelective(T t) {
        return _mapper.updateByPrimaryKeySelective(t) > 0;
    }

    /*@Override
    public boolean insert(T t) {
        return _mapper.insert(t) > 0;
    }*/

    @Override
    public boolean insertSelective(T t) {
        return _mapper.insertSelective(t) > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        return _mapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public String importData(List<T> list, boolean updateSupport) {
        if (list == null || list.size() == 0)
        {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (T user : list)
        {
            try
            {
                BeanValidators.validateWithException(validator, user);
                _mapper.insertSelective(user);
                successNum++;
                successMsg.append("<br/>" + successNum + "、 " + " 导入成功");
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、 " + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

}
