package com.bytetcp.finalab.serve.userMoneyDetail.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bytetcp.finalab.serve.userMoneyDetail.domain.UserMoneyDetailInCourse;
import com.bytetcp.finalab.system.domain.SysUser;
import com.bytetcp.finalab.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bytetcp.finalab.serve.userMoneyDetail.mapper.UserMoneyDetailMapper;
import com.bytetcp.finalab.serve.userMoneyDetail.domain.UserMoneyDetail;
import com.bytetcp.finalab.serve.userMoneyDetail.service.IUserMoneyDetailService;
import com.bytetcp.finalab.common.support.Convert;

/**
 * 用户金额明细 服务层实现
 *
 * @author finalab
 * @date 2019-08-02
 */
@Service
public class UserMoneyDetailServiceImpl implements IUserMoneyDetailService {

    @Autowired(required = false)
    private UserMoneyDetailMapper userMoneyDetailMapper;

    @Autowired
    private ISysUserService userService;

    /**
     * 查询用户金额明细信息
     *
     * @param id 用户金额明细ID
     * @return 用户金额明细信息
     */
    @Override
    public UserMoneyDetail selectUserMoneyDetailById(Long id) {
        return userMoneyDetailMapper.selectUserMoneyDetailById(id);
    }

    /**
     * 查询用户金额明细列表
     *
     * @param userMoneyDetail 用户金额明细信息
     * @return 用户金额明细集合
     */
    @Override
    public List<UserMoneyDetail> selectUserMoneyDetailList(UserMoneyDetail userMoneyDetail) {
        return userMoneyDetailMapper.selectUserMoneyDetailList(userMoneyDetail);
    }

    /**
     * 新增用户金额明细
     *
     * @param userMoneyDetail 用户金额明细信息
     * @return 结果
     */
    @Override
    public int insertUserMoneyDetail(UserMoneyDetail userMoneyDetail) {
        return userMoneyDetailMapper.insertUserMoneyDetail(userMoneyDetail);
    }

    /**
     * 修改用户金额明细
     *
     * @param userMoneyDetail 用户金额明细信息
     * @return 结果
     */
    @Override
    public int updateUserMoneyDetail(UserMoneyDetail userMoneyDetail) {
        return userMoneyDetailMapper.updateUserMoneyDetail(userMoneyDetail);
    }

    /**
     * 删除用户金额明细对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteUserMoneyDetailByIds(String ids) {
        return userMoneyDetailMapper.deleteUserMoneyDetailByIds(Convert.toStrArray(ids));
    }

    @Override
    public List<UserMoneyDetailInCourse> profitInCourseDetail(String instanceId) {
        List<UserMoneyDetailInCourse> detail = userMoneyDetailMapper.profitInCourseDetail(instanceId);

        return detail;
    }

    @Override
    public List<UserMoneyDetailInCourse> profitInCourseDetailExt(List<UserMoneyDetailInCourse> detail, String instanceId) {
        List<UserMoneyDetailInCourse> unrealized = userMoneyDetailMapper.unrealizedProfitandLoss(instanceId);

        for (UserMoneyDetailInCourse ud : detail) {
            for (UserMoneyDetailInCourse un : unrealized) {
                if (un.getTraderId() == ud.getTraderId()) {
                    ud.setUnrealizedProfitandLoss(un.getUnrealizedProfitandLoss());
                    break;
                }
            }
        }

        return detail;
    }



    /***
     * 获取到这个实例下 所有学生的盈亏情况
     * @param instanceId
     * @return
     */
    @Override
    public Map<String, JSONArray> getRanking(String instanceId) {
        return profitRankingProcessor(profitInCourseDetail(instanceId));
    }

    @Override
    public List<UserMoneyDetail> getLastLoopDetail(UserMoneyDetail userMoneyDetail) {
        return userMoneyDetailMapper.getLastLoopDetail(userMoneyDetail);
    }

    //对 每个人的盈亏明细 分组 排序，为柱状图准备参数
    private Map<String, JSONArray> profitRankingProcessor(List<UserMoneyDetailInCourse> profitList) {
        JSONArray profitDetailArray = new JSONArray();//盈利的数组
        List<Long> traderIds = profitList.stream().map(umdic -> umdic.getTraderId()).collect(Collectors.toList());
        List<Long> ids = new ArrayList<>();
        for (UserMoneyDetailInCourse p : profitList) {
            String userName = p.getTraderName();
            BigDecimal count = p.getTotalFine()
                    .add(p.getUnrealizedProfitandLoss())
                    .add(p.getTransactionFee())
                    .add(p.getTotalPrice())
                    .setScale(2, RoundingMode.HALF_UP);
            JSONObject userDetail = new JSONObject();
            userDetail.put("traderId", p.getTraderId());
            userDetail.put("stuName", userName);
            userDetail.put("value", count);
            userDetail.put("originValue", count);
            profitDetailArray.add(userDetail);
            ids.add(p.getTraderId());
        }
        profitDetailArray.sort(Comparator.comparing(
                obj -> (new BigDecimal(((JSONObject) obj).getString("originValue")))
                ).reversed()
        );
        List<SysUser> userByIds = userService.findUserByIds(ids);
        Map<Long, String> sysUserMap = new HashMap();
        for (SysUser u: userByIds) {
            sysUserMap.put(u.getUserId(), u.getUserName());
        }
        for (Object j: profitDetailArray) {
            JSONObject entity = (JSONObject) j;
            entity.put("userName", sysUserMap.get(Long.valueOf(entity.getString("traderId"))));
        }

        String[] yAxisCortyr = new String[profitDetailArray.size()];
        for (int i = 0; i < yAxisCortyr.length; i++) {
            yAxisCortyr[i] = String.valueOf(i + 1);
        }

        JSONArray yAxisItem = new JSONArray(Collections.singletonList(yAxisCortyr));
        HashMap<String, JSONArray> resultParam = new HashMap();
        resultParam.put("plus", profitDetailArray);
        resultParam.put("yAxis", yAxisItem);
        return resultParam;
    }

}
