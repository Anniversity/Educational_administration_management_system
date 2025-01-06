package cn.zjw.edubackend.service.impl;

import cn.zjw.edubackend.common.enums.ApplyStatusEnum;
import cn.zjw.edubackend.common.exception.ServiceException;
import cn.zjw.edubackend.mapper.EduApplyMapper;
import cn.zjw.edubackend.mapper.EduScheduleMapper;
import cn.zjw.edubackend.pojo.po.EduApply;
import cn.zjw.edubackend.pojo.po.EduSchedule;
import cn.zjw.edubackend.service.ApplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ApplyServiceImpl extends BaseServiceImpl<EduApply, EduApplyMapper> implements ApplyService {

    @Resource
    private EduScheduleMapper scheduleMapper;
    @Resource
    private EduApplyMapper applyMapper;

    @Override
    public boolean insertSelective(EduApply eduApply) {
        EduSchedule eduSchedule = scheduleMapper.selectByPrimaryKey(eduApply.getScheduleId());

        EduSchedule eduScheduleSelect = new EduSchedule();
        eduScheduleSelect.setSectionId(eduApply.getNewSectionId());
        eduScheduleSelect.setClassroomId(eduApply.getNewClassroomId());
        eduScheduleSelect.setYear(eduSchedule.getCourse().getYear());
        eduScheduleSelect.setSemester(eduSchedule.getCourse().getSemester());
        List<EduSchedule> eduSchedules = scheduleMapper.selectList(eduScheduleSelect);
        if(eduSchedules.size() > 0) {
            throw new ServiceException("当前节次/教室已有课程，无法申请");
        }

        eduApply.setStatus(ApplyStatusEnum.APPLYING.getCode());
        eduApply.setRawSectionId(eduSchedule.getSectionId());
        eduApply.setRawClassroomId(eduSchedule.getClassroomId());
        return super.insertSelective(eduApply);
    }

    @Override
    public boolean updateByIdSelective(EduApply eduApply) {
        if(!applyMapper.selectByPrimaryKey(eduApply.getId()).getStatus().equals(ApplyStatusEnum.APPLYING.getCode())) {
            throw new ServiceException("当前状态不能修改调课申请");
        }
        eduApply.setStatus(ApplyStatusEnum.APPLYING.getCode());
        return super.updateByIdSelective(eduApply);
    }

    @Override
    public void dealApply(Integer id, String type) {
        EduApply eduApply = new EduApply();
        eduApply.setId(id);

        eduApply = applyMapper.selectByPrimaryKey(id);
        if(eduApply == null) {
            throw new ServiceException("记录不存在");
        }
        if(!eduApply.getStatus().equals(ApplyStatusEnum.APPLYING.getCode())) {
            throw new ServiceException("当前申请已处理，无法修改");
        }

        EduApply eduApplySubmit = new EduApply();
        eduApplySubmit.setId(id);
        if(type.toUpperCase().equals(ApplyStatusEnum.AGREE.name())) {
            eduApplySubmit.setStatus(ApplyStatusEnum.AGREE.getCode());
        }else if(type.toUpperCase().equals(ApplyStatusEnum.REFUSE.name())) {
            eduApplySubmit.setStatus(ApplyStatusEnum.REFUSE.getCode());
        }
        if(applyMapper.updateByPrimaryKeySelective(eduApplySubmit) == 0) {
            throw new ServiceException("处理失败");
        }

        EduSchedule eduScheduleSubmit = new EduSchedule();
        eduScheduleSubmit.setId(eduApply.getScheduleId());
        eduScheduleSubmit.setClassroomId(eduApply.getNewClassroomId());
        eduScheduleSubmit.setSectionId(eduApply.getNewSectionId());
        scheduleMapper.updateByPrimaryKeySelective(eduScheduleSubmit);
    }
}
