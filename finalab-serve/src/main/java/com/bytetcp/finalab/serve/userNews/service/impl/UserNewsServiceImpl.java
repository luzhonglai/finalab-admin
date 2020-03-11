package com.bytetcp.finalab.serve.userNews.service.impl;

import com.bytetcp.finalab.common.annotation.Excel;
import com.bytetcp.finalab.common.support.Convert;
import com.bytetcp.finalab.common.utils.StringUtils;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.serve.userNews.domain.UserNews;
import com.bytetcp.finalab.serve.userNews.mapper.UserNewsMapper;
import com.bytetcp.finalab.serve.userNews.service.IUserNewsService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * user_news用户新闻 服务层实现
 *
 * @author finalab
 * @date 2019-05-25
 */
@Service
public class UserNewsServiceImpl implements IUserNewsService {
    private static Logger logger = LoggerFactory.getLogger(UserNewsServiceImpl.class);

    @Autowired
    private UserNewsMapper userNewsMapper;

    /**
     * 查询user_news用户新闻信息
     *
     * @param id user_news用户新闻ID
     * @return user_news用户新闻信息
     */
    @Override
    public UserNews selectUserNewsById(Long id) {
        return userNewsMapper.selectUserNewsById(id);
    }

    /**
     * 查询user_news用户新闻列表
     *
     * @param userNews user_news用户新闻信息
     * @return user_news用户新闻集合
     */
    @Override
    public List<UserNews> selectUserNewsList(UserNews userNews) {
        return userNewsMapper.selectUserNewsList(userNews);
    }

    /**
     * 新增user_news用户新闻
     *
     * @param userNews user_news用户新闻信息
     * @return 结果
     */
    @Override
    public int insertUserNews(UserNews userNews) {
        return userNewsMapper.insertUserNews(userNews);
    }

    /**
     * 修改user_news用户新闻
     *
     * @param userNews user_news用户新闻信息
     * @return 结果
     */
    @Override
    public int updateUserNews(UserNews userNews) {
        return userNewsMapper.updateUserNews(userNews);
    }

    /**
     * 删除user_news用户新闻对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteUserNewsByIds(String ids) {
        return userNewsMapper.deleteUserNewsByIds(Convert.toStrArray(ids));
    }

    @Override
    public boolean handlExcelSheet(Sheet UserNewsSheet, Long caseId) {
        if (Objects.isNull(UserNewsSheet)) {
            return false;
        }
        int skipRows = 1;//跳过sheet的前两行数据(一般是跳过表头)
        int rownums = UserNewsSheet.getPhysicalNumberOfRows();
        List<UserNews> userNewsList = new ArrayList<>();

        for (int i = skipRows; i < rownums; i++) {
            UserNews userNews = new UserNews();
            Row row = UserNewsSheet.getRow(i);
            if (StringUtils.isEmpty(String.valueOf(ExcelUtil.getCellValue(row, 0)))) {
                break;
            }
            userNews.setCaseId(caseId);
            userNews.setGroupNum(Integer.valueOf(String.valueOf(ExcelUtil.getCellValue(row, 0))));
            userNews.setPhaseNum(Integer.parseInt(String.valueOf(ExcelUtil.getCellValue(row, 1))));
            userNews.setTimeNum(Integer.parseInt(String.valueOf(ExcelUtil.getCellValue(row, 2))));
            userNews.setTargetName(String.valueOf(ExcelUtil.getCellValue(row, 3)));
            userNews.setNumber(Double.parseDouble(String.valueOf(ExcelUtil.getCellValue(row, 4))));
            userNews.setPrice(new Double(String.valueOf(ExcelUtil.getCellValue(row, 5, 10))));
            userNews.setTradeType(String.valueOf(ExcelUtil.getCellValue(row, 6)));
//            userNews.setAimType(String.valueOf(ExcelUtil.getCellValue(row, 6)));
            userNews.setAim(String.valueOf(ExcelUtil.getCellValue(row, 7)));
            userNews.setContinueTime(Integer.parseInt(String.valueOf(ExcelUtil.getCellValue(row, 8))));
            userNewsList.add(userNews);
//            Double aDouble = new Double(String.valueOf(ExcelUtil.getCellValue(row, 5, 10)));
//            if (i > 993) {
//                System.out.println();
//                System.out.println();
//            }
//            System.err.print(aDouble);
//            System.err.print("   ");
//            System.err.print(String.valueOf(ExcelUtil.getCellValue(row, 4, 10)));
//            System.err.print("   ");
//            System.err.print(ExcelUtil.getCellValue(row, 4, 10));
        }

        // 检查数据是否已存在
        UserNews userNews = new UserNews();
        userNews.setCaseId(caseId);
        List<UserNews> oldUserNews = userNewsMapper.selectUserNewsList(userNews);
        if (!CollectionUtils.isEmpty(oldUserNews) && oldUserNews.size() > 0) {
            List<String> collect = oldUserNews.stream().map(e -> String.valueOf(e.getId())).collect(Collectors.toList());
            String[] ids = collect.toArray(new String[collect.size()]);
            logger.info("案例 id 为 {} 的 '交易约束' 已存在，将删除 用户新闻数据 ids : {}", caseId, ids);
            userNewsMapper.deleteUserNewsByIds(ids);
        }
        if (userNewsList.size() == 0) return true;
        return userNewsMapper.insertUserNewsList(userNewsList) > 0;

    }
}
