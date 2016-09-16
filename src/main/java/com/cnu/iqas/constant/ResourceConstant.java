package com.cnu.iqas.constant;
/**
* @author 周亮 
* @version 创建时间：2015年11月23日 下午4:33:00
* 类说明 资源类型常量
*/
public class ResourceConstant {
	public final static int TYPE_IMAGE=1; //图片类型
	public final static int TYPE_PICTUREBOOK=2; //绘本类型
	public final static int TYPE_VOICE=3;  //声音类型
	public final static int TYPE_VIDEO=4;  //视频类型
	

	public final static long UPLOAD_SIZE_IMAGE=1024*1024*2; //图片上传最大值,2M
	public final static long UPLOAD_SIZE_VIDEO=1024*1024*5;  //视频上传最大值,5M
	public final static long UPLOAD_SIZE_VOICE=1024*1024*5;  //声音上传最大值,5M
	public final static long UPLOAD_SIZE_PICTUREBOOK=1024*1024*2; //绘本上传最大值,2M
	
	
	
	/**
	 * 判断资源类型是否是图片、绘本、声音、视频
	 * @param type
	 * @return
	 */
	public static boolean isResouceType(int type){
		if( type!=TYPE_IMAGE&& type!=TYPE_PICTUREBOOK&&type!=TYPE_VIDEO&& type!=TYPE_VOICE)
			return false;
		return true;
	}

}
