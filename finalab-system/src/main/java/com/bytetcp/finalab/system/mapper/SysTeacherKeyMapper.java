package com.bytetcp.finalab.system.mapper;

import com.bytetcp.finalab.system.domain.SysRole;
import com.bytetcp.finalab.system.domain.SysTkey;

import java.util.List;

/**
 * 角色表 数据层
 * 
 * @author ruoyi
 */
public interface SysTeacherKeyMapper
{
    /**
     * 
     * 查询教师注册码
     */
    public String selectTkey();

    /**
     * 修改教师注册码
     * 
     * @param role 角色信息
     * @return 结果
     */
    public int updateTkey(SysTkey sysTkey);

}
