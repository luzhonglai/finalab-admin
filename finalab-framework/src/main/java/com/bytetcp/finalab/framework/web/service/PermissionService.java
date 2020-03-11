package com.bytetcp.finalab.framework.web.service;

import java.util.Set;
import java.util.stream.Collectors;

import com.bytetcp.finalab.system.domain.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

/**
 * RuoYi首创 js调用 thymeleaf 实现按钮权限可见性
 *
 * @author ruoyi
 */
@Service("permission")
public class PermissionService {
    public String hasPermi(String permission) {
        return isPermittedOperator(permission) ? "" : "hidden";
    }

    private boolean isPermittedOperator(String permission) {
        return SecurityUtils.getSubject().isPermitted(permission);
    }

    /**
     * 可以结合菜单按钮的权限功能,实现配置化
     *
     * @return
     */
    @Deprecated
    public boolean isTeacher() {
        Subject subject = SecurityUtils.getSubject();
        SysUser sysUser = (SysUser) subject.getPrincipal();
        Set<String> roleKeys = sysUser.getRoles().stream().map(u -> u.getRoleKey()).collect(Collectors.toSet());
        boolean isTeacher = roleKeys.contains("teacher") || roleKeys.contains("admin");
        return isTeacher;
    }

    /**
     * 可以结合菜单按钮的权限功能,实现配置化
     *
     * @return
     */
    @Deprecated
    public boolean isStudent() {
            Subject subject = SecurityUtils.getSubject();
        SysUser sysUser = (SysUser) subject.getPrincipal();
        Set<String> roleKeys = sysUser.getRoles().stream().map(u -> u.getRoleKey()).collect(Collectors.toSet());
        boolean isStudent = roleKeys.contains("student") || roleKeys.contains("admin");
        return isStudent;
    }
}
