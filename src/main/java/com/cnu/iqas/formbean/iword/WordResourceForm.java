package com.cnu.iqas.formbean.iword;

import com.cnu.iqas.bean.iword.WordResource;
import com.cnu.iqas.constant.ResourceConstant;
import com.cnu.iqas.formbean.BaseForm;

/**
* @author 周亮 
* @version 创建时间：2015年11月23日 下午3:52:42
* 类说明 单词资源表单类
*/
public class WordResourceForm extends BaseForm {

	/**
	 * 资源类型,有1图片、2视频、3声音、4绘本
	 */
	private int type;
	//资源所属单词的uuid
	private String uuid;
	
	

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	/**
	 * 判断资源类型是否有效
	 * @return 有效返回true
	 */
	public boolean validateType() {
		// TODO Auto-generated method stub
		if( type ==ResourceConstant.TYPE_IMAGE ||type ==ResourceConstant.TYPE_PICTUREBOOK || type ==ResourceConstant.TYPE_VIDEO || type ==ResourceConstant.TYPE_VOICE)
			return true;
		return false;
	}
	
	

}
