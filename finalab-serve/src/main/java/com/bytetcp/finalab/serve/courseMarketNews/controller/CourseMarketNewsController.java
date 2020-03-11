package com.bytetcp.finalab.serve.courseMarketNews.controller;

import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.courseMarketNews.domain.CourseMarketNews;
import com.bytetcp.finalab.serve.courseMarketNews.service.ICourseMarketNewsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课件市场新闻 信息操作处理
 *
 * @author finalab
 * @date 2019-05-25
 */
@Controller
@RequestMapping("/serve/courseMarketNews")
public class CourseMarketNewsController extends BaseController
{
    private String prefix = "courseMarketNews";

	@Autowired
	private ICourseMarketNewsService courseMarketNewsService;

	@RequiresPermissions("serve:courseMarketNews:view")
	@GetMapping()
	public String courseMarketNews()
	{
	    return prefix + "/courseMarketNews";
	}

	/**
	 * 查询课件市场新闻列表
	 */
	@RequiresPermissions("serve:courseMarketNews:list")
	@PostMapping("/list/{courseId}")
	@ResponseBody
	public TableDataInfo list(@PathVariable("courseId") long courseId, CourseMarketNews courseMarketNews)
	{
		courseMarketNews.setCourseId(courseId);
		startPage();
        List<CourseMarketNews> list = courseMarketNewsService.selectCourseMarketNewsList(courseMarketNews);
		return getDataTable(list);
	}


	/**
	 * 导出课件市场新闻列表
	 */
	@RequiresPermissions("serve:courseMarketNews:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CourseMarketNews courseMarketNews)
    {
    	List<CourseMarketNews> list = courseMarketNewsService.selectCourseMarketNewsList(courseMarketNews);
        ExcelUtil<CourseMarketNews> util = new ExcelUtil<CourseMarketNews>(CourseMarketNews.class);
        return util.exportExcel(list, "courseMarketNews");
    }

	/**
	 * 新增课件市场新闻
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}

	/**
	 * 新增保存课件市场新闻
	 */
	@RequiresPermissions("serve:courseMarketNews:add")
	@Log(title = "课件市场新闻", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(CourseMarketNews courseMarketNews)
	{
		return toAjax(courseMarketNewsService.insertCourseMarketNews(courseMarketNews));
	}

	/**
	 * 修改课件市场新闻
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		CourseMarketNews courseMarketNews = courseMarketNewsService.selectCourseMarketNewsById(id);
		mmap.put("courseMarketNews", courseMarketNews);
	    return prefix + "/edit";
	}

	/**
	 * 修改保存课件市场新闻
	 */
	@RequiresPermissions("serve:courseMarketNews:edit")
	@Log(title = "课件市场新闻", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(CourseMarketNews courseMarketNews)
	{
		return toAjax(courseMarketNewsService.updateCourseMarketNews(courseMarketNews));
	}

	/**
	 * 删除课件市场新闻
	 */
	@RequiresPermissions("serve:courseMarketNews:remove")
	@Log(title = "课件市场新闻", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(courseMarketNewsService.deleteCourseMarketNewsByIds(ids));
	}

}
