package com.bytetcp.finalab.serve.courseMessage.controller;

import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.course.service.ICourseService;
import com.bytetcp.finalab.serve.courseMessage.domain.CourseMessage;
import com.bytetcp.finalab.serve.courseMessage.service.ICourseMessageService;
import com.bytetcp.finalab.serve.courseStudent.domain.CourseStudent;
import com.bytetcp.finalab.serve.courseStudent.service.ICourseStudentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 老师学生广播信息操作处理
 *
 * @author finalab
 * @date 2020-05-28
 */
@Controller
@RequestMapping("/serve/courseMessage")
public class CourseMessageController extends BaseController {
    @Autowired
    private ICourseMessageService courseMessageService;

    @Autowired
    private ICourseStudentService courseStudentService;
    /**
     * 发送消息给指定学生
     */
    @RequiresPermissions("serve:courseMessage:sendInformation")
    @PostMapping("/sendInformationToStudent")
    @ResponseBody
    public AjaxResult sendInformationToStudent(CourseMessage courseMessage) {
        return toAjax(courseMessageService.insertCourseMessage(courseMessage));
    }

    /**
     * 发送消息给所有学生
     */
    @RequiresPermissions("serve:courseMessage:sendInformationToAllStudent")
    @PostMapping("/sendInformationToAllStudent")
    @ResponseBody
    public AjaxResult sendInformationToAllStudent(CourseMessage courseMessage) {
        CourseMessage msg = new CourseMessage();
        msg.setCourseId(courseMessage.getCourseId());
        msg.setInformation(courseMessage.getInformation());
        msg.setInstanceId(courseMessage.getInstanceId());
        List<CourseStudent> list = courseStudentService.selectCourseStudentListByCourseId(courseMessage.getCourseId());
        int count = 0;
        for (CourseStudent std:list) {
            msg.setStudentId(std.getStudentId());
            int num = courseMessageService.insertCourseMessage(msg);
            count += num;
        }
        return toAjax(count);
    }

    /**
     * 学生检查是否有未读消息
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CourseMessage courseMessage) {
        startPage();
        List<CourseMessage> messageList = courseMessageService.selectCourseMessage(courseMessage);
        return getDataTable(messageList);
    }

    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult edit(CourseMessage courseMessage) {
        return toAjax(courseMessageService.updateCourseMessage(courseMessage));
    }

    /**
     * 删除 学生消息广播
     */
    @RequiresPermissions("serve:courseMessage:remove")
    @Log(title = " 学生课件消息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(CourseMessage courseMessage) {
        return toAjax(courseMessageService.deleteCourseMessage(courseMessage));
    }

    /**
     * 删除 学生所有消息广播
     */
    @RequiresPermissions("serve:courseMessage:removeAll")
    @Log(title = " 学生课件消息", businessType = BusinessType.DELETE)
    @PostMapping("/removeAll")
    @ResponseBody
    public AjaxResult removeAll(CourseMessage courseMessage) {
        return toAjax(courseMessageService.deleteAllCourseMessage(courseMessage));
    }

}
