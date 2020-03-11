package com.bytetcp.finalab.serve.userStock.controller;

import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.base.ResultStatus;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.course.domain.InstanceRunRecode;
import com.bytetcp.finalab.serve.course.service.ICourseService;
import com.bytetcp.finalab.serve.userStock.domain.UserStock;
import com.bytetcp.finalab.serve.userStock.service.IUserStockService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 用户持仓明细 信息操作处理
 *
 * @author finalab
 * @date 2019-05-27
 */
@Controller
@RequestMapping("/serve/userStock")
public class UserStockController extends BaseController
{
    private String prefix = "userStock";

	@Autowired
	private IUserStockService userStockService;
	@Autowired
	private ICourseService iCourseService;

	@RequiresPermissions("serve:userStock:view")
	@GetMapping()
	public String userStock()
	{
	    return prefix + "/userStock";
	}

	/**
	 * 查询用户持仓明细列表
	 */

	@PostMapping("/teacherList/{courseId}")
	@ResponseBody
	public TableDataInfo teacherList(@PathVariable("courseId") Long courseId, UserStock userStock)
	{
		List<UserStock> list = new ArrayList<>();
		InstanceRunRecode selectInstance = iCourseService.selectInstance(courseId);
		startPage();
		if (Objects.nonNull(selectInstance)) {
			String InstanceId = selectInstance.getInstanceId();
			userStock.setCourseId(courseId);
			userStock.setInstanceId(InstanceId);
			list = userStockService.selectUserStockList(userStock);
		}
		return getDataTable(list);
	}

	@PostMapping("/list/{instanceId}")
	@ResponseBody
	public TableDataInfo list(@PathVariable("instanceId") String instanceId, UserStock userStock)
	{
		startPage();
		userStock.setInstanceId(instanceId);
		userStock.setUserId(getSysUser().getUserId().intValue());
        List<UserStock> list = userStockService.selectUserStockList(userStock);
		return getDataTable(list);
	}

	@PostMapping("/getUserStore")
	@ResponseBody
	public AjaxResult getUserStore(@RequestBody UserStock userStock) {
		userStock.setUserId(getSysUser().getUserId().intValue());
		List<UserStock> list = userStockService.selectUserStockList(userStock);
		if (Objects.isNull(list) || list.size() <= 0) {
			return AjaxResult.error(ResultStatus.NO_DATA);
		}
		return AjaxResult.success(list.get(0));
	}


	/**
	 * 导出用户持仓明细列表
	 */
	@RequiresPermissions("serve:userStock:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(UserStock userStock)
    {
    	List<UserStock> list = userStockService.selectUserStockList(userStock);
        ExcelUtil<UserStock> util = new ExcelUtil<UserStock>(UserStock.class);
        return util.exportExcel(list, "userStock");
    }

	/**
	 * 新增用户持仓明细
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}

	/**
	 * 新增保存用户持仓明细
	 */
	@RequiresPermissions("serve:userStock:add")
	@Log(title = "用户持仓明细", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(UserStock userStock)
	{
		return toAjax(userStockService.insertUserStock(userStock));
	}

	/**
	 * 修改用户持仓明细
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		UserStock userStock = userStockService.selectUserStockById(id);
		mmap.put("userStock", userStock);
	    return prefix + "/edit";
	}

	/**
	 * 修改保存用户持仓明细
	 */
	@RequiresPermissions("serve:userStock:edit")
	@Log(title = "用户持仓明细", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(UserStock userStock)
	{
		return toAjax(userStockService.updateUserStock(userStock));
	}

	/**
	 * 删除用户持仓明细
	 */
	@RequiresPermissions("serve:userStock:remove")
	@Log(title = "用户持仓明细", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(userStockService.deleteUserStockByIds(ids));
	}

}
