package com.bytetcp.finalab.serve.courseDerivedVar.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.serve.courseDerivedVar.domain.CourseDerivedVar;
import com.bytetcp.finalab.serve.courseDerivedVar.service.ICourseDerivedVarService;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;

/**
 * 课件-衍生变量 信息操作处理
 *
 * @author finalab
 * @date 2019-09-24
 */
@Controller
@RequestMapping("/serve/courseDerivedVar")
public class CourseDerivedVarController extends BaseController {
    private String prefix = "courseDerivedVar";

    @Autowired
    private ICourseDerivedVarService courseDerivedVarService;

    @RequiresPermissions("serve:courseDerivedVar:view")
    @GetMapping()
    public String courseDerivedVar() {
        return prefix + "/courseDerivedVar";
    }

    /**
     * 查询课件-衍生变量列表
     */
    @RequiresPermissions("serve:courseDerivedVar:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CourseDerivedVar courseDerivedVar) {
        startPage();
        List<CourseDerivedVar> list = courseDerivedVarService.selectCourseDerivedVarList(courseDerivedVar);
        return getDataTable(list);
    }

    /**
     * 课件编辑-衍生变量列表
     */
    @PostMapping("/listForCourse/{courseId}")
    @ResponseBody
    public TableDataInfo listForCourse(@PathVariable("courseId") Long courseId) {
        startPage();
        CourseDerivedVar courseDerivedVar = new CourseDerivedVar();
        courseDerivedVar.setCourseId(courseId);
        List<CourseDerivedVar> list = courseDerivedVarService.selectCourseDerivedVarList(courseDerivedVar);
        return getDataTable(list);
    }

    /**
     * 导出课件-衍生变量列表
     */
    @RequiresPermissions("serve:courseDerivedVar:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CourseDerivedVar courseDerivedVar) {
        List<CourseDerivedVar> list = courseDerivedVarService.selectCourseDerivedVarList(courseDerivedVar);
        ExcelUtil<CourseDerivedVar> util = new ExcelUtil<CourseDerivedVar>(CourseDerivedVar.class);
        return util.exportExcel(list, "courseDerivedVar");
    }

    /**
     * 新增课件-衍生变量
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存课件-衍生变量
     */
    @RequiresPermissions("serve:courseDerivedVar:add")
    @Log(title = "课件-衍生变量", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CourseDerivedVar courseDerivedVar) {
        return toAjax(courseDerivedVarService.insertCourseDerivedVar(courseDerivedVar));
    }

    /**
     * 修改课件-衍生变量
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        CourseDerivedVar courseDerivedVar = courseDerivedVarService.selectCourseDerivedVarById(id);
        mmap.put("courseDerivedVar", courseDerivedVar);
        return prefix + "/edit";
    }

    /**
     * 修改保存课件-衍生变量
     */
    @Log(title = "课件-衍生变量", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CourseDerivedVar courseDerivedVar) {
        return toAjax(courseDerivedVarService.updateCourseDerivedVar(courseDerivedVar));
    }

    /**
     * 删除课件-衍生变量
     */
    @RequiresPermissions("serve:courseDerivedVar:remove")
    @Log(title = "课件-衍生变量", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(courseDerivedVarService.deleteCourseDerivedVarByIds(ids));
    }

}
