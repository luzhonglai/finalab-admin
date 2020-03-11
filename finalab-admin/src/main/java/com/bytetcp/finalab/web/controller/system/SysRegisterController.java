package com.bytetcp.finalab.web.controller.system;


import com.bytetcp.finalab.common.Constant;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.base.ResultStatus;
import com.bytetcp.finalab.common.utils.ServletUtils;
import com.bytetcp.finalab.common.utils.StringUtils;
import com.bytetcp.finalab.framework.shiro.service.SysPasswordService;
import com.bytetcp.finalab.framework.util.ShiroUtils;
import com.bytetcp.finalab.system.domain.SysDictData;
import com.bytetcp.finalab.system.domain.SysUser;
import com.bytetcp.finalab.system.mapper.SysTeacherKeyMapper;
import com.bytetcp.finalab.system.service.ISysDictDataService;
import com.bytetcp.finalab.system.service.ISysUserService;
import com.bytetcp.finalab.system.service.impl.SysRoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static com.bytetcp.finalab.common.base.AjaxResult.error;

@Controller
@RequestMapping("/Register")
public class SysRegisterController {
    private String prefix = "system/register";

    @Autowired
    private SysPasswordService passwordService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private SysTeacherKeyMapper sysTeacherKeyMapper;

    @Autowired
    private ISysDictDataService dictDataService;

    @GetMapping("/studentPage")
    public String StudentRegister()
    {
        return prefix + "/student";
    }

    @GetMapping("/teacherPage")
    public String teacherRegister()
    {
        return prefix + "/teacher";
    }

    @PostMapping("/student")
    public String student(String loginName, String userName, String password, HttpServletRequest request, HttpServletResponse response){
        SysUser sysUser = new SysUser();
        sysUser.setPassword(password);
        sysUser.setUserName(userName);
        sysUser.setLoginName(loginName);
        sysUser.setDeptId((long)203);
        sysUser.setEmail("createStudent@finalab.com");
        sysUser.setPhonenumber("13888888888");
        sysUser.setSex("0");
        sysUser.setStatus("0");
        Long[] roleId= {(long)101};
        sysUser.setRoleIds(roleId);
        Long[] PostIds = {(long)6};
        sysUser.setPostIds(PostIds);
        sysUser.setCreateBy("finalab");
        if (StringUtils.isNotNull(sysUser.getUserId()) && SysUser.isAdmin(sysUser.getUserId()))
        {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"注册失败！\"}");
        }
        sysUser.setSalt(ShiroUtils.randomSalt());
        sysUser.setPassword(passwordService.encryptPassword(sysUser.getLoginName(), sysUser.getPassword(), sysUser.getSalt()));
        userService.insertUser(sysUser);
        SysUser user = userService.selectUserByLoginName(loginName);
        return "redirect:/login";
    }

    @PostMapping("/teacher")
    @ResponseBody
    public AjaxResult teacher(String loginName, String userName, String password,String tkey, HttpServletRequest request, HttpServletResponse response){
        List<SysDictData> sysDictDataList = dictDataService.selectDictDataByType(Constant.TEACHE_RREGIST_KEY);
        SysDictData sysDictData = sysDictDataList.get(0);
        String key = sysDictData.getDictValue();
        if (!key.equals(tkey) ){
            return AjaxResult.error(ResultStatus.FAIL);
        }
        SysUser sysUser = new SysUser();
        sysUser.setPassword(password);
        sysUser.setUserName(userName);
        sysUser.setLoginName(loginName);
        sysUser.setDeptId((long)203);
        sysUser.setEmail("default@finalab.com");
        sysUser.setPhonenumber("13788888888");
        sysUser.setSex("0");
        sysUser.setStatus("0");
        Long[] roleId= {(long)100};
        sysUser.setRoleIds(roleId);
        Long[] PostIds = {(long)5};
        sysUser.setPostIds(PostIds);
        sysUser.setCreateBy("finalab");
        if (StringUtils.isNotNull(sysUser.getUserId()) && SysUser.isAdmin(sysUser.getUserId()))
        {
            return AjaxResult.error(ResultStatus.FAIL);
        }
        sysUser.setSalt(ShiroUtils.randomSalt());
        sysUser.setPassword(passwordService.encryptPassword(sysUser.getLoginName(), sysUser.getPassword(), sysUser.getSalt()));
        userService.insertUser(sysUser);
        return AjaxResult.success();
    }
}
