package com.bytecp.finalab.test;

import com.alibaba.fastjson.JSON;
import com.bytetcp.finalab.FinalabApplication;
import com.bytetcp.finalab.serve.courseTargetParam.domain.CourseTargetParam;
import com.bytetcp.finalab.serve.courseTargetParam.mapper.CourseTargetParamMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FinalabApplication.class)
public class CourseParamsTest {

    @Autowired
    private CourseTargetParamMapper courseTargetParamMapper;

    @Test
    public void testCourseParams() {
        CourseTargetParam courseTargetParam = new CourseTargetParam();
        courseTargetParam.setCourseId(46L);
        courseTargetParam.setTargetName("SHJZ001");
        List<CourseTargetParam> courseTargetParams = courseTargetParamMapper.selectCourseTargetParamList(courseTargetParam);


        System.err.println(JSON.toJSONString(courseTargetParams));
    }
}
