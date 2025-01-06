package cn.zjw.edubackend.mapper;

import cn.zjw.edubackend.pojo.po.EduArrangeRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EduArrangeRecordMapper extends BaseMapper<EduArrangeRecord> {
    int updateByPrimaryKeyWithBLOBs(EduArrangeRecord row);
}