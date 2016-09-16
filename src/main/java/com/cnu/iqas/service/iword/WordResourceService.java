package com.cnu.iqas.service.iword;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.bean.iword.WordAttributeResource;
import com.cnu.iqas.bean.iword.WordResource;
import com.cnu.iqas.dao.base.DAO;

/**
* @author 周亮 
* @version 创建时间：2015年11月23日 上午11:34:22
* 类说明  单词资源服务接口
*/
public interface WordResourceService  extends IfetchResource<WordResource>{

	/**
	 * 保存单词资源文件，并返回保存的相对路径
	 * @param servletContext    应用上下文
	 * @param file				保存的文件
	 * @param filetype			文件类型：1:图片、2:绘本、3:声音、4:视频
	 * @return					返回文件在工程中的先对路径
	 * @throws Exception
	 */
	public String saveWordResourceFile(ServletContext servletContext, CommonsMultipartFile file, int filetype) throws Exception;

	/**
	 * 根据单词资源id查询单词资源
	 * @param id
	 * @return
	 */
	public WordResource find(String id);
	/**
	 * 保存单词资源
	 * @param wr
	 */
	public void update(WordResource wr);

	/**
	 * 根据条件查询所有单词
	 * @param string 查询语句
	 * @param array  查询语句中的查询条件
	 * @return
	 */
	public List<WordResource> getAllData(String string, Object[] array);
	/**
	 * 保存单词资源
	 * @param resource
	 */
	public void save(WordResource resource);
	/**
	 * 根据id删除单词资源
	 * @param id
	 */
	public void delete(String id);
	/**
	 * 根据属性来获取实体
	 * @param <T>
	 * @param wherejpql 查询条件  "o.email=? "
	 * @param attribute 实体的属性值
	 * @return
	 */
	public WordResource find(String wherejpql, Object attribute);
	/**
	 * 根据单词内容查找一个单词资源对象
	 * @return
	 */
	public WordResource findByContent();
		
}
