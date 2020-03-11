package com.bytetcp.finalab.serve.coursePriceMove.controller;

import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.coursePriceMove.domain.CoursePriceMove;
import com.bytetcp.finalab.serve.coursePriceMove.service.ICoursePriceMoveService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 价格走势 信息操作处理
 *
 * @author finalab
 * @date 2019-05-03
 */
@Controller
@RequestMapping("/serve/coursePriceMove")
public class CoursePriceMoveController extends BaseController
{
    private String prefix = "coursePriceMove";

	@Autowired
	private ICoursePriceMoveService coursePriceMoveService;

	@RequiresPermissions("serve:coursePriceMove:view")
	@GetMapping()
	public String coursePriceMove()
	{
	    return prefix + "/coursePriceMove";
	}

	/**
	 * 查询价格走势列表
	 */
	@RequiresPermissions("serve:coursePriceMove:list")
	@PostMapping("/list/{courseId}")
	@ResponseBody
	public TableDataInfo list(CoursePriceMove coursePriceMove,@PathVariable("courseId") long courseId)
	{
		startPage();
		coursePriceMove.setCourseId(courseId);
        List<CoursePriceMove> list = coursePriceMoveService.selectCoursePriceMoveList(coursePriceMove);
		return getDataTable(list);
	}


	/**
	 * 导出价格走势列表
	 */
	@RequiresPermissions("serve:coursePriceMove:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CoursePriceMove coursePriceMove)
    {
    	List<CoursePriceMove> list = coursePriceMoveService.selectCoursePriceMoveList(coursePriceMove);
        ExcelUtil<CoursePriceMove> util = new ExcelUtil<CoursePriceMove>(CoursePriceMove.class);
        return util.exportExcel(list, "coursePriceMove");
    }

	/**
	 * 新增价格走势
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}

	/**
	 * 新增保存价格走势
	 */
	@RequiresPermissions("serve:coursePriceMove:add")
	@Log(title = "价格走势", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(CoursePriceMove coursePriceMove)
	{
		return toAjax(coursePriceMoveService.insertCoursePriceMove(coursePriceMove));
	}

	/**
	 * 修改价格走势
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		CoursePriceMove coursePriceMove = coursePriceMoveService.selectCoursePriceMoveById(id);
		mmap.put("coursePriceMove", coursePriceMove);
	    return prefix + "/edit";
	}

	/**
	 * 修改保存价格走势
	 */
	@RequiresPermissions("serve:coursePriceMove:edit")
	@Log(title = "价格走势", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(CoursePriceMove coursePriceMove)
	{
		return toAjax(coursePriceMoveService.updateCoursePriceMove(coursePriceMove));
	}

	/**
	 * 删除价格走势
	 */
	@RequiresPermissions("serve:coursePriceMove:remove")
	@Log(title = "价格走势", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(coursePriceMoveService.deleteCoursePriceMoveByIds(ids));
	}

}
