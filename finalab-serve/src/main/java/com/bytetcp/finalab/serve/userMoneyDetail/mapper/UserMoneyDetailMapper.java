package com.bytetcp.finalab.serve.userMoneyDetail.mapper;

import com.bytetcp.finalab.serve.userMoneyDetail.domain.UserMoneyDetail;
import com.bytetcp.finalab.serve.userMoneyDetail.domain.UserMoneyDetailInCourse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 用户金额明细 数据层
 *
 * @author finalab
 * @date 2019-08-02
 */
public interface UserMoneyDetailMapper
{
	/**
     * 查询用户金额明细信息
     *
     * @param id 用户金额明细ID
     * @return 用户金额明细信息
     */
	public UserMoneyDetail selectUserMoneyDetailById(Long id);

	/**
     * 查询用户金额明细列表
     *
     * @param userMoneyDetail 用户金额明细信息
     * @return 用户金额明细集合
     */
	public List<UserMoneyDetail> selectUserMoneyDetailList(UserMoneyDetail userMoneyDetail);

	/**
     * 新增用户金额明细
     *
     * @param userMoneyDetail 用户金额明细信息
     * @return 结果
     */
	public int insertUserMoneyDetail(UserMoneyDetail userMoneyDetail);

	/**
     * 修改用户金额明细
     *
     * @param userMoneyDetail 用户金额明细信息
     * @return 结果
     */
	public int updateUserMoneyDetail(UserMoneyDetail userMoneyDetail);

	/**
     * 删除用户金额明细
     *
     * @param id 用户金额明细ID
     * @return 结果
     */
	public int deleteUserMoneyDetailById(Long id);

	/**
     * 批量删除用户金额明细
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteUserMoneyDetailByIds(String[] ids);

	//查询学生盈亏 交易费 罚款等信息
    List<UserMoneyDetailInCourse> profitInCourseDetail(String instanceId);
	//查询学生未实现盈利
    List<UserMoneyDetailInCourse> unrealizedProfitandLoss(String instanceId);

    List<UserMoneyDetail> getLastLoopDetail(UserMoneyDetail userMoneyDetail);

}