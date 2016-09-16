package com.cnu.iqas.service.iword;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cnu.iqas.bean.iword.WordAttributeResource;
import com.cnu.iqas.bean.iword.WordResource;
import com.cnu.iqas.dao.base.DAO;
import com.cnu.iqas.enumtype.WordAttributeEnum;

/**
* @author 王文辉 
* @version 创建时间：2015年12月2日 
* @version 修改时间：2016年1月29号
* 类说明  单词属性资源服务接口
*/
public interface WordAttributeResourceService extends IfetchResource<WordAttributeResource>{
	
	/**
	 * 保存单词资源文件，并返回保存的相对路径
	 * @param servletContext    应用上下文
	 * @param file				保存的文件
	 * @param filetype			文件类型：1:图片、2:绘本、3:声音、4:视频
	 * @return					返回文件在工程中的先对路径
	 * @throws Exception
	 */
	public String saveWordResourceFile(ServletContext servletContext, CommonsMultipartFile file, int filetype)throws Exception;
	/**
	 * 保存单词属性资源
	 * @param resourceattribute
	 */
	public void save(WordAttributeResource resourceattribute);
	/**
	 * 根据属性来获取实体
	 * @param <T>
	 * @param wherejpql 查询条件  "o.email=? "
	 * @param attribute 实体的属性值
	 * @return
	 */
	public WordAttributeResource find(String wherejpql, Object attribute);
	
	/**
	 * 查看单词的某个属性的某中类型的资源
	 * @param wordId  单词id
	 * @param attributeType 单词属性类型
	 * @param resourceType  资源类型由ResourceConstant中的值提供
	 * @return
	 */
	public List<WordAttributeResource> find(String wordId,WordAttributeEnum attributeType,int resourceType);
	/**
	 * 根据条件查询所有数据
	 * @param wherejpql 查询条件  "o.email=? and o.account=?"
	 * @param queryParams 查询条件占位符对应的参数值，
	 */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<WordAttributeResource> getAllData(String wherejpql, Object[] queryParams);

}

