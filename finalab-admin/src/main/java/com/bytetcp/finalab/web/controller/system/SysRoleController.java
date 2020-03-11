package com.bytetcp.finalab.web.controller.system;

import java.util.List;

import com.bytetcp.finalab.common.Constant;
import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.constant.UserConstants;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.StringUtils;
import com.bytetcp.finalab.common.utils.UUIDUtil;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.util.ShiroUtils;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.system.domain.SysDictData;
import com.bytetcp.finalab.system.domain.SysRole;
import com.bytetcp.finalab.system.domain.SysTkey;
import com.bytetcp.finalab.system.mapper.SysTeacherKeyMapper;
import com.bytetcp.finalab.system.service.ISysDictDataService;
import com.bytetcp.finalab.system.service.ISysDictTypeService;
import com.bytetcp.finalab.system.service.ISysRoleService;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 角色信息
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {
    private String prefix = "system/role";

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysDictTypeService dictTypeService;

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private SysTeacherKeyMapper sysTeacherKeyMapper;

    @RequiresPermissions("system:role:view")
    @GetMapping()
    public String role() {
        return prefix + "/role";
    }

    @GetMapping("/showtKey")
    public String tkey(ModelMap mmap) {
        SysTkey sysTkey = new SysTkey();
        List<SysDictData> sysDictDataList = dictDataService.selectDictDataByType(Constant.TEACHE_RREGIST_KEY);
        SysDictData sysDictData = sysDictDataList.get(0);
        sysTkey.setTkey(sysDictData.getDictValue());
        mmap.put("sysTkey", sysTkey);
        return prefix + "/tKey";
    }

    @PostMapping("/tKey")
    @ResponseBody
    public AjaxResult tkey(String  tkey) {
        List<SysDictData> sysDictDataList = dictDataService.selectDictDataByType(Constant.TEACHE_RREGIST_KEY);
        SysDictData sysDictData = sysDictDataList.get(0);
        String random = UUIDUtil.getRandomString(6);
        sysDictData.setDictValue(random);
        dictDataService.updateDictData(sysDictData);
        return AjaxResult.success(random);
    }

    @PostMapping("/checkTkey")
    @ResponseBody
    public String checkTkey(@RequestParam("tkey") String  tkey) {
        List<SysDictData> sysDictDataList = dictDataService.selectDictDataByType(Constant.TEACHE_RREGIST_KEY);
        SysDictData sysDictData = sysDictDataList.get(0);
        if(sysDictData.getDictValue().equals(tkey)){
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    @RequiresPermissions("system:role:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysRole role) {
        startPage();
        List<SysRole> list = roleService.selectRoleList(role);
        return getDataTable(list);
    }

    @Log(title = "角色管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:role:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysRole role) {
        List<SysRole> list = roleService.selectRoleList(role);
        ExcelUtil<SysRole> util = new ExcelUtil<SysRole>(SysRole.class);
        return util.exportExcel(list, "角色数据");
    }

    /**
     * 新增角色
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存角色
     */
    @RequiresPermissions("system:role:add")
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(SysRole role) {
        role.setCreateBy(ShiroUtils.getLoginName());
        ShiroUtils.clearCachedAuthorizationInfo();
        return toAjax(roleService.insertRole(role));

    }

    /**
     * 修改角色
     */
    @GetMapping("/edit/{roleId}")
    public String edit(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleService.selectRoleById(roleId));
        return prefix + "/edit";
    }

    /**
     * 修改保存角色
     */
    @RequiresPermissions("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(SysRole role) {
        role.setUpdateBy(ShiroUtils.getLoginName());
        ShiroUtils.clearCachedAuthorizationInfo();
        return toAjax(roleService.updateRole(role));
    }

    /**
     * 新增数据权限
     */
    @GetMapping("/rule/{roleId}")
    public String rule(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleService.selectRoleById(roleId));
        return prefix + "/rule";
    }

    /**
     * 修改保存数据权限
     */
    @RequiresPermissions("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("/rule")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult ruleSave(SysRole role) {
        role.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(roleService.updateRule(role));
    }

    @RequiresPermissions("system:role:remove")
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(roleService.deleteRoleByIds(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 校验角色名称
     */
    @PostMapping("/checkRoleNameUnique")
    @ResponseBody
    public String checkRoleNameUnique(SysRole role) {
        return roleService.checkRoleNameUnique(role);
    }

    /**
     * 校验角色权限
     */
    @PostMapping("/checkRoleKeyUnique")
    @ResponseBody
    public String checkRoleKeyUnique(SysRole role) {
        return roleService.checkRoleKeyUnique(role);
    }

    /**
     * 选择菜单树
     */
    @GetMapping("/selectMenuTree")
    public String selectMenuTree() {
        return prefix + "/tree";
    }

    /**
     * 角色状态修改
     */
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:role:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(SysRole role) {
        return toAjax(roleService.changeStatus(role));
    }
}