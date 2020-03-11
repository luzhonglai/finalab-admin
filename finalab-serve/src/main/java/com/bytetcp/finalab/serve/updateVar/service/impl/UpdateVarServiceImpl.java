package com.bytetcp.finalab.serve.updateVar.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.bytetcp.finalab.common.annotation.Excel;
import com.bytetcp.finalab.common.utils.StringUtils;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bytetcp.finalab.serve.updateVar.mapper.UpdateVarMapper;
import com.bytetcp.finalab.serve.updateVar.domain.UpdateVar;
import com.bytetcp.finalab.serve.updateVar.service.IUpdateVarService;
import com.bytetcp.finalab.common.support.Convert;

/**
 * 变量更新 服务层实现
 * 
 * @author finalab
 * @date 2019-08-17
 */
@Service
public class UpdateVarServiceImpl implements IUpdateVarService 
{
	@Autowired
	private UpdateVarMapper updateVarMapper;

	/**
     * 查询变量更新信息
     * 
     * @param id 变量更新ID
     * @return 变量更新信息
     */
    @Override
	public UpdateVar selectUpdateVarById(Long id)
	{
	    return updateVarMapper.selectUpdateVarById(id);
	}
	
	/**
     * 查询变量更新列表
     * 
     * @param updateVar 变量更新信息
     * @return 变量更新集合
     */
	@Override
	public List<UpdateVar> selectUpdateVarList(UpdateVar updateVar)
	{
	    return updateVarMapper.selectUpdateVarList(updateVar);
	}
	
    /**
     * 新增变量更新
     * 
     * @param updateVar 变量更新信息
     * @return 结果
     */
	@Override
	public int insertUpdateVar(UpdateVar updateVar)
	{
	    return updateVarMapper.insertUpdateVar(updateVar);
	}
	
	/**
     * 修改变量更新
     * 
     * @param updateVar 变量更新信息
     * @return 结果
     */
	@Override
	public int updateUpdateVar(UpdateVar updateVar)
	{
	    return updateVarMapper.updateUpdateVar(updateVar);
	}

	/**
     * 删除变量更新对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteUpdateVarByIds(String ids)
	{
		return updateVarMapper.deleteUpdateVarByIds(Convert.toStrArray(ids));
	}

	@Override
	public boolean handlExcelSheet(Sheet sheet, Long caseId) {
		if (Objects.isNull(sheet)) {
			return false;
		}
		boolean result = true;
		int skipRows = 1;//跳过sheet的前两行数据(一般是跳过表头)
        List<UpdateVar> updateVarList = new ArrayList<>();
		int rownums = sheet.getPhysicalNumberOfRows();
		for (int i = skipRows; i < rownums; i++) {
			UpdateVar updateVar = new UpdateVar();
			Row row = sheet.getRow(i);
			updateVar.setCaseId(caseId);
			if (StringUtils.isEmpty(String.valueOf(ExcelUtil.getCellValue(row, 0)))) {
				break;
			}
			updateVar.setPeriod(Integer.valueOf(String.valueOf(ExcelUtil.getCellValue(row, 0))));
			updateVar.setTick(Integer.valueOf(String.valueOf(ExcelUtil.getCellValue(row, 1))));
			updateVar.setType(String.valueOf(ExcelUtil.getCellValue(row, 2)));
			updateVar.setSubType(String.valueOf(ExcelUtil.getCellValue(row, 3)));
			updateVar.setVariable(String.valueOf(ExcelUtil.getCellValue(row, 4)));
			updateVar.setValue(String.valueOf(ExcelUtil.getCellValue(row, 5)));
            updateVarList.add(updateVar);
		}
		return updateVarMapper.insertUpdateVarList(updateVarList);
	}
}
