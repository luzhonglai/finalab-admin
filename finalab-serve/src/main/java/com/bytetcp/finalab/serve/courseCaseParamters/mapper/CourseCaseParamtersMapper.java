package com.bytetcp.finalab.serve.courseCaseParamters.mapper;

import com.bytetcp.finalab.serve.courseCaseParamters.domain.CourseCaseParamters;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程案例参数 数据层
 *
 * @author finalab
 * @date 2019-04-02
 */
public interface CourseCaseParamtersMapper {
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
     * 删除课程案例参数
     *
     * @param id 课程案例参数ID
     * @return 结果
     */
    public int deleteCourseCaseParamtersById(Long id);

    /**
     * 批量删除课程案例参数
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCourseCaseParamtersByIds(String[] ids);

    /**
     * 将案例参数拷贝到课程参数表
     *
     * @param caseId
     */
    public void copyCaseParamsByCaseId(@Param("caseId") Long caseId, @Param("courseId") Long courseId);

    /**
     * 将标的拷贝到课程标的表
     *
     * @param caseId
     */
    public void copyTradingTargetByCaseId(@Param("caseId") Long caseId, @Param("courseId") Long courseId);

    /**
     * 将标的参数拷贝到课程标的参数表
     *
     * @param caseId
     */
    public void copyTargetParamByCaseId(@Param("caseId") Long caseId, @Param("courseId") Long courseId);

    /**
     * 将交易约束拷贝到课程交易约束表
     *
     * @param caseId
     */
    public void copyTradingConstraintByCaseId(@Param("caseId") Long caseId, @Param("courseId") Long courseId);

    /**
     * 将清算表拷贝到课程清算表
     *
     * @param caseId
     */
    public void copyLiquidationByCaseId(@Param("caseId") Long caseId, @Param("courseId") Long courseId);

    /**
     * 将价格走势表拷贝到价格走势表
     *
     * @param caseId
     */
    public void copyPriceMoveByCaseId(@Param("caseId") Long caseId, @Param("courseId") Long courseId);

    /**
     * 根据课件id，caseID、参数id，更新
     *
     * @param CourseCaseParamters
     * @return 结果
     */
    public int EditCourseCaseParamters(CourseCaseParamters courseCaseParamters);


    /**
     * 根据caseID删除全部数据
     *
     * @param caseId
     */
    public int deleteCourseCaseParamtersByCaseId(Long caseId);
    public int deleteCourseUserNewsByCaseId(@Param("caseId") Long caseId);
    public int deleteCourseTradingConstraintByCaseId(@Param("caseId") Long caseId);
    public int deleteCourseTargetParamByCaseId(@Param("caseId") Long caseId);

    public int deleteCourseMarketNewsByCaseId(@Param("caseId") Long caseId);

    public int deleteCourseLiquidationByCaseId(@Param("caseId") Long caseId);

    public int deleteCoursePriceMoveByCaseId(@Param("caseId") Long caseId);

    /**
     * 确定是否多组数据
     *
     * @param caseId
     */
    public Integer selectTargetParamByGroupNumRandom(@Param("caseId") Long caseId);

    Integer selectMaxGroupNumInPriceMove(@Param("caseId") Long caseId);


    public Integer selectLiquidationByGroupNumRandom(@Param("caseId") Long caseId);


    public void copyTargetParamByCaseIdAndGroupNum(@Param("caseId") Long caseId, @Param("courseId") Long courseId, @Param("group_num") int group_num);

    public void copyLiquidationByCaseIdAndGroupNum(@Param("caseId") Long caseId, @Param("courseId") Long courseId, @Param("group_num") int group_num);

    public void copyPriceMoveByCaseIdAndGroupNum(@Param("caseId") Long caseId, @Param("courseId") Long courseId, @Param("group_num") int group_num);
    public void copyUserNewsByCaseId(@Param("caseId") long caseId, @Param("courseId") Long courseId);
    public void copyMarketNewsByCaseId(@Param("caseId") long caseId, @Param("courseId") Long courseId);


    Integer selectMarketNewsByGroupNumRandom(Long caseId);

    void copyMarketNewsByCaseIdAndGroupNum(@Param("caseId")Long caseId, @Param("courseId")Long courseId,  @Param("group_num")int group_num);

    void copyUpdateVarByCaseId(@Param("caseId")Long caseId,@Param("courseId") Long courseId);
}