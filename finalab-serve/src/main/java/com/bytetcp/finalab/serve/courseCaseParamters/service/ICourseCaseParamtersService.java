package com.bytetcp.finalab.serve.courseCaseParamters.service;

import com.bytetcp.finalab.serve.courseCaseParamters.domain.CourseCaseParamters;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程案例参数 服务层
 *
 * @author finalab
 * @date 2019-04-02
 */
public interface ICourseCaseParamtersService {
    /**
     * 查询课程案例参数信息
     *
     * @param id 课程案例参数ID
     * @return 课程案例参数信息
     */
    public CourseCaseParamters selectCourseCaseParamtersById(Long id);

    /**
     * 查询课程案例参数列表
     *
     * @param courseCaseParamters 课程案例参数信息
     * @return 课程案例参数集合
     */
    public List<CourseCaseParamters> selectCourseCaseParamtersList(CourseCaseParamters courseCaseParamters);



    /**
     * 新增课程案例参数
     *
     * @param courseCaseParamters 课程案例参数信息
     * @return 结果
     */
    public int insertCourseCaseParamters(CourseCaseParamters courseCaseParamters);

    /**
     * 修改课程案例参数
     *
     * @param courseCaseParamters 课程案例参数信息
     * @return 结果
     */
    public int updateCourseCaseParamters(CourseCaseParamters courseCaseParamters);

    /**
     * 删除课程案例参数信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCourseCaseParamtersByIds(String ids);

    public int deleteCourseCaseParamtersById(long id);
    /**
     * 将案例参数拷贝到课程参数表
     *
     * @param caseId
     * @param courseId
     */
    public void copyCaseParamsByCaseId(Long caseId, Long courseId);

    /**
     * 将标的拷贝到课程标的表
     *
     * @param caseId
     * @param courseId
     */
    public void copyTradingTargetByCaseId(Long caseId, Long courseId);

    /**
     * 将标的参数拷贝到课程标的参数的表
     *
     * @param caseId
     * @param courseId
     */
    public void copyTargetParamByCaseId(Long caseId, Long courseId);

    /**
     * 将交易约束拷贝到课程交易约束的表
     *
     * @param caseId
     * @param courseId
     */
    public void copyTradingConstraintByCaseId(Long caseId, Long courseId);

    /**
     * 将清算表拷贝到课程清算表的表
     *
     * @param caseId
     * @param courseId
     */
    public void copyLiquidationByCaseId(Long caseId, Long courseId);

    /**
     * 将价格走势表拷贝到课程价格走势的表
     *
     * @param caseId
     * @param courseId
     */
    public void copyPriceMoveByCaseId(Long caseId, Long courseId);

    /**
     *
     * @param courseCaseParamters
     * @return
     */

    public  int EditCourseCaseParamters(CourseCaseParamters courseCaseParamters);

    /**
     *
     * @param caseId
     * @return
     */
    public int deleteCourseCaseParamtersByCaseId(Long caseId);

    /**
     *
     * @param caseId
     * @return Integer
     */
    public Integer selectTargetParamByGroupNumRandom(Long caseId);

    public Integer selectLiquidationByGroupNumRandom(Long caseId);

    public void copyTargetParamByCaseIdAndGroupNum(Long caseId,Long courseId, int group_num);

    public void copyLiquidationByCaseIdAndGroupNum(Long caseId,Long courseId, int group_num);
    public void copyPriceMoveByCaseIdAndGroupNum(Long caseId,Long courseId, int group_num);

    public void copyUserNewsByCaseId(@Param("caseId") long caseId, @Param("courseId") Long courseId);

    public void copyMarketNewsByCaseId(@Param("caseId") long caseId, @Param("courseId") Long courseId);

    Integer selectMarketNewsByGroupNumRandom(Long caseId);

    void copyMarketNewsByCaseIdAndGroupNum(Long caseId, Long courseId, int randomNum);

    Integer selectMaxGroupNumInPriceMove(Long caseId);

    void copyUpdateVarByCaseId(Long caseId, Long courseId);
}
