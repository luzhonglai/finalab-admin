package com.bytetcp.finalab.serve.courseStudent.controller;

import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.enums.JoinEnum;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.course.service.ICourseService;
import com.bytetcp.finalab.serve.course.vo.CourseVo;
import com.bytetcp.finalab.serve.courseStudent.domain.CourseStudent;
import com.bytetcp.finalab.serve.courseStudent.service.ICourseStudentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *  学生参与课件 信息操作处理
 *
 * @author finalab
 * @date 2019-03-15
 */
@Controller
@RequestMapping("/serve/courseStudent")
public class CourseStudentController extends BaseController
{
    private String prefix = "courseStudent";

	@Autowired
	private ICourseStudentService courseStudentService;

	@Autowired
	private ICourseService courseService;

	@RequiresPermissions("serve:courseStudent:view")
	@GetMapping()
	public String courseStudent()
	{
	    return prefix + "/courseStudent";
	}

	/**
	 * 查询 学生参与课件列表
	 */
	@RequiresPermissions("serve:courseStudent:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(CourseStudent courseStudent)
	{
		startPage();
        List<CourseStudent> list = courseStudentService.selectCourseStudentList(courseStudent);
		return getDataTable(list);
	}

	/**
	 * 查询 学生参与课件列表
	 */
	@RequiresPermissions("serve:courseStudent:list")
	@PostMapping("/list/{courseId}")
	@ResponseBody
	public TableDataInfo listByCourseId(@PathVariable("courseId") Long courseId)
	{
		startPage();
        List<CourseStudent> list = courseStudentService.selectCourseStudentListByCourseId(courseId);
		return getDataTable(list);
	}

	/**
	 * 学生报名课程
	 * @param courseId
	 * @return
	 */
	@PostMapping("/signUp")
	@ResponseBody
	public AjaxResult signUp(Long courseId) {
		Long studentId = getSysUser().getUserId();
		CourseVo courseVo = courseService.selectCourseById(courseId);
		boolean notAllowIn = courseVo.getAllowIn() == JoinEnum.NOT_ALLOW.getCode();
		CourseStudent findCourseStudent = courseStudentService.selectCourseStudentByStudentIdAndCourseId(studentId, courseId);
		// 判断是否允许新名单进入
		if (Objects.isNull(findCourseStudent) && notAllowIn) {
			return AjaxResult.error(JoinEnum.NOT_ALLOW.getDesc());
		}
		CourseStudent courseStudent = new CourseStudent();
		courseStudent.setStudentId(studentId);
		courseStudent.setSignUp(1);
		courseStudent.setOnLine(1);
		courseStudent.setCourseId(courseId);
		courseStudent.setAddTime(new Date());
		courseStudentService.signUp(courseStudent);
		return AjaxResult.success();
	}

	@PostMapping("/exitCourse")
	@ResponseBody
	public AjaxResult exitCourse(Long courseId) {
		Long studentId = getSysUser().getUserId();
		CourseStudent courseStudent = new CourseStudent();
		courseStudent.setStudentId(studentId);
		courseStudent.setOnLine(0);
		courseStudent.setSignUp(0);
		courseStudent.setLeaveTime(new Date());
		courseStudent.setCourseId(courseId);
		courseStudentService.updateCourseStudentByStudIdAndCourseId(courseStudent);
		String membersProportion = courseStudentService.membersProportion(courseId);
		return AjaxResult.success().put("membersProportion", membersProportion);
	}

	@PostMapping("/membersProportion")
	@ResponseBody
	public AjaxResult membersProportion(Long courseId) {

		String membersProportion = courseStudentService.membersProportion(courseId);
		return AjaxResult.success().put("membersProportion", membersProportion);
	}


	/**
	 * 导出 学生参与课件列表
	 */
	@RequiresPermissions("serve:courseStudent:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CourseStudent courseStudent)
    {
    	List<CourseStudent> list = courseStudentService.selectCourseStudentList(courseStudent);
        ExcelUtil<CourseStudent> util = new ExcelUtil<CourseStudent>(CourseStudent.class);
        return util.exportExcel(list, "courseStudent");
    }

	/**
	 * 新增 学生参与课件
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}

	/**
	 * 新增保存 学生参与课件
	 */
	@RequiresPermissions("serve:courseStudent:add")
	@Log(title = " 学生参与课件", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(CourseStudent courseStudent)
	{
		return toAjax(courseStudentService.insertCourseStudent(courseStudent));
	}

	/**
	 * 修改 学生参与课件
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		CourseStudent courseStudent = courseStudentService.selectCourseStudentById(id);
		mmap.put("courseStudent", courseStudent);
	    return prefix + "/edit";
	}

	/**
	 * 修改保存 学生参与课件
	 */
	@RequiresPermissions("serve:courseStudent:edit")
	@Log(title = " 学生参与课件", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(CourseStudent courseStudent)
	{
		return toAjax(courseStudentService.updateCourseStudent(courseStudent));
	}

	/**
	 * 删除 学生参与课件
	 */
	@RequiresPermissions("serve:courseStudent:remove")
	@Log(title = " 学生参与课件", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(courseStudentService.deleteCourseStudentByIds(ids));
	}

}
