package cn.zjw.edubackend.controller;

import cn.zjw.edubackend.common.AjaxResult;
import cn.zjw.edubackend.common.ValidateGroup;
import cn.zjw.edubackend.common.enums.ServiceEnum;
import cn.zjw.edubackend.pojo.po.BaseEntity;
import cn.zjw.edubackend.service.BaseService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseController<T extends BaseEntity, K extends BaseService<T>> {

    @Autowired
    private K _service;

    @RequestMapping("/list")
//    @PreAuthorize("@permissionService.hasPermission(this.getName(), T(cn.zjw.edubackend.common.enums.OperateEnum).LIST)" )
    public AjaxResult list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            T t,
            HttpServletRequest request
    ) {
        PageHelper.startPage(page, pageSize);
        return AjaxResult.success(_service.selectListFuzzy(t));
    }

    @PutMapping("/{id}")
//    @PreAuthorize("@permissionService.hasPermission(this.getName(), T(cn.zjw.edubackend.common.enums.OperateEnum).UPDATE)" )
    public AjaxResult updateById(
            @PathVariable Integer id,
            @Validated(ValidateGroup.update.class) @RequestBody T t,
            HttpServletRequest request
    ) {
        t.setId(id);
        if (_service.updateByIdSelective(t))
            return AjaxResult.success();
        return AjaxResult.error();
    }

    @PostMapping
//    @PreAuthorize("@permissionService.hasPermission(this.getName(), T(cn.zjw.edubackend.common.enums.OperateEnum).INSERT)" )
    public AjaxResult insert(
            @Validated(ValidateGroup.insert.class) @RequestBody T t,
            HttpServletRequest request
    ) {
        if (_service.insertSelective(t))
            return AjaxResult.success();
        return AjaxResult.error();
    }

    @GetMapping("/{id}")
//    @PreAuthorize("@permissionService.hasPermission(this.getName(), T(cn.zjw.edubackend.common.enums.OperateEnum).ONE)" )
    public AjaxResult selectById(
            @PathVariable Integer id,
            HttpServletRequest request
    ) {
        return AjaxResult.success(_service.selectById(id));
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("@permissionService.hasPermission(this.getName(), T(cn.zjw.edubackend.common.enums.OperateEnum).DELETE)" )
    public AjaxResult deleteById(
            @PathVariable Integer id,
            HttpServletRequest request
    ) {
        return AjaxResult.success(_service.deleteById(id));
    }

    /*@PostMapping("/importData")
    public AjaxResult importData(MultipartFile multipartFile) throws Exception {
        File tempFile = FileUtil.createTempFile();
        multipartFile.transferTo(tempFile);
        ExcelReader excelReader = ExcelUtil.getReader(tempFile);
        List<List<Object>> rows = excelReader.read();

        if(rows.size() <= 1) {
            throw new ServiceException("导入失败，请确保至少有一行数据，且含有表头");
        }

        return AjaxResult.success("上传成功");
    }

    public abstract void importDataInner();*/

    public abstract ServiceEnum getName();

}
