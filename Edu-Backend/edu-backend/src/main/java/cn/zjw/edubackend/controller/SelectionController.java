package cn.zjw.edubackend.controller;

import cn.zjw.edubackend.common.AjaxResult;
import cn.zjw.edubackend.common.enums.ServiceEnum;
import cn.zjw.edubackend.common.enums.UserTypeEnum;
import cn.zjw.edubackend.pojo.po.EduSelection;
import cn.zjw.edubackend.security.LoginUser;
import cn.zjw.edubackend.service.SelectionService;
import cn.zjw.edubackend.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/selection")
public class SelectionController extends BaseController<EduSelection, SelectionService> {

    @Autowired
    private SelectionService selectionService;

    @GetMapping("/allSelection/{courseId}")
    @PreAuthorize("hasAnyRole('teacher', 'admin')")
    public AjaxResult getAllSelection(@PathVariable Integer courseId) {
        EduSelection selection = new EduSelection();
        selection.setCourseId(courseId);
        return AjaxResult.success(selectionService.selectListFuzzy(selection));
    }

    @GetMapping("/select/{id}")
    @PreAuthorize("hasAnyRole('student')")
    public AjaxResult select(@PathVariable Integer id) {
        selectionService.selectCourse(id, SecurityUtils.getUserId());
        return AjaxResult.success("选修成功");
    }

    @GetMapping("/cancel/{id}")
    @PreAuthorize("hasAnyRole('student')")
    public AjaxResult cancel(@PathVariable Integer id) {
        if (!selectionService.cancelSelectCourse(id, SecurityUtils.getUserId()))
            return AjaxResult.error("退选失败");
        return AjaxResult.success("退选成功");
    }

    @PutMapping("/grade/{id}/{score}")
    @PreAuthorize("hasAnyRole('teacher', 'admin')")
    public AjaxResult enterGrade(
            @PathVariable Integer id,
            @Digits(integer = 3, fraction = 2, message = "成绩最多为3位整数，2位小数")
            @Size(max = 100, message = "成绩只能在0 - 100之间")
            @PathVariable BigDecimal score) {
        EduSelection selection = new EduSelection();
        selection.setId(id);
        selection.setScore(score);
        if (!selectionService.updateByIdSelective(selection)) {
            return AjaxResult.success("提交失败");
        }
        return AjaxResult.success("提交成功");
    }

    @Override
    public AjaxResult list(Integer page, Integer pageSize, EduSelection eduSelection, HttpServletRequest request) {
        if (eduSelection == null)
            eduSelection = new EduSelection();
        // 判断当前的用户 设置id
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (loginUser.getUserType().equals(UserTypeEnum.STUDENT.getCode())) {
            eduSelection.setStudentId(loginUser.getUser().getId());
        }
        return super.list(page, pageSize, eduSelection, request);
    }

    @Override
    public ServiceEnum getName() {
        return ServiceEnum.SELECTION;
    }
}
