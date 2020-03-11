package com.bytetcp.finalab.serve.cases.service;

import com.bytetcp.finalab.serve.cases.domain.Cases;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;

/**
 * 案例 服务层
 * 
 * @author finalab
 * @date 2019-03-09
 */
public interface ICasesService extends IExcelHandler
{
	/**
     * 查询案例信息
     * 
     * @param id 案例ID
     * @return 案例信息
     */
	public Cases selectCasesById(Long id);
	
	/**
     * 查询案例列表
     * 
     * @param cases 案例信息
     * @return 案例集合
     */
	public List<Cases> selectCasesList(Cases cases);
	
	/**
     * 新增案例
     * 
     * @param cases 案例信息
     * @return 结果
     */
	public int insertCases(Cases cases);
	
	/**
     * 修改案例
     * 
     * @param cases 案例信息
     * @return 结果
     */
	public int updateCases(Cases cases);
		
	/**
     * 删除案例信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCasesByIds(String ids);

	/**
	 * 校验导入的excel
	 * @param workbook
	 * @return
	 */
	Map<String, Object> checkExcel(Workbook workbook);



	public int deleteCasesById(Long id);

}
