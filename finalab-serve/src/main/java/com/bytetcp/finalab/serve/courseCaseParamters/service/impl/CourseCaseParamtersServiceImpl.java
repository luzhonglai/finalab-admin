package com.bytetcp.finalab.serve.courseCaseParamters.service.impl;

import com.bytetcp.finalab.common.support.Convert;
import com.bytetcp.finalab.serve.courseCaseParamters.domain.CourseCaseParamters;
import com.bytetcp.finalab.serve.courseCaseParamters.mapper.CourseCaseParamtersMapper;
import com.bytetcp.finalab.serve.courseCaseParamters.service.ICourseCaseParamtersService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程案例参数 服务层实现
 *
 * @author finalab
 * @date 2019-04-02
 */
@Service
public class CourseCaseParamtersServiceImpl implements ICourseCaseParamtersService {

    @Autowired
    private CourseCaseParamtersMapper courseCaseParamtersMapper;

    /**
     * 查询课程案例参数信息
     *
     * @param id 课程案例参数ID
     * @return 课程案例参数信息
     */
    @Override
    public CourseCaseParamters selectCourseCaseParamtersById(Long id) {
        return courseCaseParamtersMapper.selectCourseCaseParamtersById(id);
    }

    /**
     * 查询课程案例参数列表
     *
     * @param courseCaseParamters 课程案例参数信息
     * @return 课程案例参数集合
     */
    @Override
    public List<CourseCaseParamters> selectCourseCaseParamtersList(CourseCaseParamters courseCaseParamters) {
        return courseCaseParamtersMapper.selectCourseCaseParamtersList(courseCaseParamters);
    }

    /**
     * 新增课程案例参数
     *
     * @param courseCaseParamters 课程案例参数信息
     * @return 结果
     */
    @Override
    public int insertCourseCaseParamters(CourseCaseParamters courseCaseParamters) {
        return courseCaseParamtersMapper.insertCourseCaseParamters(courseCaseParamters);
    }

    /**
     * 修改课程案例参数
     *
     * @param courseCaseParamters 课程案例参数信息
     * @return 结果
     */
    @Override
    public int updateCourseCaseParamters(CourseCaseParamters courseCaseParamters) {
        return courseCaseParamtersMapper.updateCourseCaseParamters(courseCaseParamters);
    }

    /**
     * 删除课程案例参数对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCourseCaseParamtersByIds(String ids) {
        return courseCaseParamtersMapper.deleteCourseCaseParamtersByIds(Convert.toStrArray(ids));
    }

    @Override
    public int deleteCourseCaseParamtersById(long id) {
        return courseCaseParamtersMapper.deleteCourseCaseParamtersById(id);
    }

    @Override
    public void copyCaseParamsByCaseId(Long caseId, Long courseId) {
        courseCaseParamtersMapper.copyCaseParamsByCaseId(caseId, courseId);
    }

    @Override
    public void copyTradingTargetByCaseId(Long caseId, Long courseId) {
        courseCaseParamtersMapper.copyTradingTargetByCaseId(caseId, courseId);
    }

    @Override
    public void copyTargetParamByCaseId(Long caseId, Long courseId) {
        courseCaseParamtersMapper.copyTargetParamByCaseId(caseId, courseId);
    }

    @Override
    public void copyTradingConstraintByCaseId(Long caseId, Long courseId) {
        courseCaseParamtersMapper.copyTradingConstraintByCaseId(caseId, courseId);
    }

    @Override
    public void copyLiquidationByCaseId(Long caseId, Long courseId) {
        courseCaseParamtersMapper.copyLiquidationByCaseId(caseId, courseId);
    }

    @Override
    public void copyPriceMoveByCaseId(Long caseId, Long courseId) {
        courseCaseParamtersMapper.copyPriceMoveByCaseId(caseId, courseId);
    }

    @Override
    public int EditCourseCaseParamters(CourseCaseParamters courseCaseParamters) {
        return courseCaseParamtersMapper.EditCourseCaseParamters(courseCaseParamters);
    }

    //删除全部该案例的数据
    @Override
    public int deleteCourseCaseParamtersByCaseId(Long caseId) {
        return courseCaseParamtersMapper.deleteCourseCaseParamtersByCaseId(caseId);
    }

    @Override
    public Integer selectTargetParamByGroupNumRandom(Long caseId) {
        return courseCaseParamtersMapper.selectTargetParamByGroupNumRandom(caseId);
    }

    @Override
    public void copyTargetParamByCaseIdAndGroupNum(Long caseId, Long courseId, int group_num) {
        courseCaseParamtersMapper.copyTargetParamByCaseIdAndGroupNum(caseId, courseId, group_num);
    }

    @Override
    public void copyLiquidationByCaseIdAndGroupNum(Long caseId, Long courseId, int group_num) {
        courseCaseParamtersMapper.copyLiquidationByCaseIdAndGroupNum(caseId, courseId, group_num);
    }

    @Override
    public void copyPriceMoveByCaseIdAndGroupNum(Long caseId, Long courseId, int group_num) {
        courseCaseParamtersMapper.copyPriceMoveByCaseIdAndGroupNum(caseId, courseId, group_num);
    }

    @Override
    public Integer selectLiquidationByGroupNumRandom(Long caseId) {
        return courseCaseParamtersMapper.selectLiquidationByGroupNumRandom(caseId);
    }

    @Override
    public void copyUserNewsByCaseId(@Param("caseId") long caseId, @Param("courseId") Long courseId) {
        courseCaseParamtersMapper.copyUserNewsByCaseId(caseId, courseId);
    }

    @Override
    public void copyMarketNewsByCaseId(@Param("caseId") long caseId, @Param("courseId") Long courseId) {
        courseCaseParamtersMapper.copyMarketNewsByCaseId(caseId, courseId);
    }

    @Override
    public Integer selectMarketNewsByGroupNumRandom(Long caseId) {
        return courseCaseParamtersMapper.selectMarketNewsByGroupNumRandom(caseId);
    }

    @Override
    public void copyMarketNewsByCaseIdAndGroupNum(Long caseId, Long courseId, int group_num) {
        courseCaseParamtersMapper.copyMarketNewsByCaseIdAndGroupNum(caseId, courseId, group_num);
    }

    @Override
    public Integer selectMaxGroupNumInPriceMove(Long caseId) {
        return courseCaseParamtersMapper.selectMaxGroupNumInPriceMove(caseId);
    }

    @Override
    public void copyUpdateVarByCaseId(Long caseId, Long courseId) {
        courseCaseParamtersMapper.copyUpdateVarByCaseId(caseId, courseId);
    }
}
