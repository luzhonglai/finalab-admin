package com.bytetcp.finalab.serve.marketNews.controller;

import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.marketNews.domain.MarketNews;
import com.bytetcp.finalab.serve.marketNews.service.IMarketNewsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 案例市场新闻 信息操作处理
 *
 * @author finalab
 * @date 2019-05-25
 */
@Controller
@RequestMapping("/serve/marketNews")
public class MarketNewsController extends BaseController {
    private String prefix = "marketNews";

    @Autowired
    private IMarketNewsService marketNewsService;

    @RequiresPermissions("serve:marketNews:view")
    @GetMapping()
    public String marketNews() {
        return prefix + "/marketNews";
    }

    /**
     * 查询案例市场新闻列表
     */
    @RequiresPermissions("serve:marketNews:list")
    @PostMapping("/list/{caseId}")
    @ResponseBody
    public TableDataInfo list(@PathVariable("caseId") long caseId, MarketNews marketNews) {
        marketNews.setCaseId(caseId);
        startPage();
        List<MarketNews> list = marketNewsService.selectMarketNewsList(marketNews);
        return getDataTable(list);
    }


    /**
     * 导出案例市场新闻列表
     */
    @RequiresPermissions("serve:marketNews:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MarketNews marketNews) {
        List<MarketNews> list = marketNewsService.selectMarketNewsList(marketNews);
        ExcelUtil<MarketNews> util = new ExcelUtil<MarketNews>(MarketNews.class);
        return util.exportExcel(list, "marketNews");
    }

    /**
     * 新增案例市场新闻
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存案例市场新闻
     */
    @RequiresPermissions("serve:marketNews:add")
    @Log(title = "案例市场新闻", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MarketNews marketNews) {
        return toAjax(marketNewsService.insertMarketNews(marketNews));
    }

    /**
     * 修改案例市场新闻
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        MarketNews marketNews = marketNewsService.selectMarketNewsById(id);
        mmap.put("marketNews", marketNews);
        return prefix + "/edit";
    }

    /**
     * 修改保存案例市场新闻
     */
    @RequiresPermissions("serve:marketNews:edit")
    @Log(title = "案例市场新闻", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MarketNews marketNews) {
        return toAjax(marketNewsService.updateMarketNews(marketNews));
    }

    /**
     * 删除案例市场新闻
     */
    @RequiresPermissions("serve:marketNews:remove")
    @Log(title = "案例市场新闻", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(marketNewsService.deleteMarketNewsByIds(ids));
    }

}
