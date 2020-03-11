package com.bytetcp.finalab.serve.courseUserNews.controller;

import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.courseUserNews.domain.CourseUserNews;
import com.bytetcp.finalab.serve.courseUserNews.service.ICourseUserNewsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课件用户新闻 信息操作处理
 *
 * @author finalab
 * @date 2019-05-25
 */
@Controller
@RequestMapping("/serve/courseUserNews")
public class CourseUserNewsController extends BaseController {
    private String prefix = "courseUserNews";

    @Autowired
    private ICourseUserNewsService courseUserNewsService;

    @RequiresPermissions("serve:courseUserNews:view")
    @GetMapping()
    public String courseUserNews() {
        return prefix + "/courseUserNews";
    }

    /**
     * 查询课件用户新闻列表
     */
    @RequiresPermissions("serve:courseUserNews:list")
    @PostMapping("/list/{courseId}")
    @ResponseBody
    public TableDataInfo list(@PathVariable("courseId") long courseId, CourseUserNews courseUserNews) {
        courseUserNews.setCourseId(courseId);
        startPage();
        List<CourseUserNews> list = courseUserNewsService.selectCourseUserNewsList(courseUserNews);
        return getDataTable(list);
    }


    /**
     * 导出课件用户新闻列表
     */
    @RequiresPermissions("serve:courseUserNews:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CourseUserNews courseUserNews) {
        List<CourseUserNews> list = courseUserNewsService.selectCourseUserNewsList(courseUserNews);
        ExcelUtil<CourseUserNews> util = new ExcelUtil<CourseUserNews>(CourseUserNews.class);
        return util.exportExcel(list, "courseUserNews");
    }

    /**
     * 新增课件用户新闻
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存课件用户新闻
     */
    @RequiresPermissions("serve:courseUserNews:add")
    @Log(title = "课件用户新闻", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CourseUserNews courseUserNews) {
        return toAjax(courseUserNewsService.insertCourseUserNews(courseUserNews));
    }

    /**
     * 修改课件用户新闻
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        CourseUserNews courseUserNews = courseUserNewsService.selectCourseUserNewsById(id);
        mmap.put("courseUserNews", courseUserNews);
        return prefix + "/edit";
    }

    /**
     * 修改保存课件用户新闻
     */
    @RequiresPermissions("serve:courseUserNews:edit")
    @Log(title = "课件用户新闻", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CourseUserNews courseUserNews) {
        return toAjax(courseUserNewsService.updateCourseUserNews(courseUserNews));
    }

    /**
     * 删除课件用户新闻
     */
    @RequiresPermissions("serve:courseUserNews:remove")
    @Log(title = "课件用户新闻", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(courseUserNewsService.deleteCourseUserNewsByIds(ids));
    }

}
