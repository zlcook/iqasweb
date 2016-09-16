package com.cnu.iqas.service.user.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cnu.iqas.bean.user.User;
import com.cnu.iqas.service.user.StudyDateService;

/**
* @author 周亮 
* @version 创建时间：2016年1月19日 上午9:44:30
* 类说明:用户学习数据
*/
@Service("studyDateService")
public class StudyDateServiceImpl implements StudyDateService{

	public final static String picRoot="ifilesystem/first/study/";
	@Override
	public String studyRecord(User user) {
		// TODO Auto-generated method stub
		
		return picRoot+"studyRecord.jpg";
	}

	@Override
	public List<String> gameTypeContrast(User user) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<>();
		list.add(picRoot+"gameTypeContrast1.jpg");
		list.add(picRoot+"learningTypes.jpg");
		return list;
	}

	@Override
	public String successRate(User user) {
		// TODO Auto-generated method stub
		return picRoot+"successRate.jpg";
	}

	@Override
	public String favoriteGameType(User user) {
		// TODO Auto-generated method stub
		return picRoot+"favoriteGameType.jpg";
	}

	@Override
	public String timeOfGame(User user) {
		// TODO Auto-generated method stub
		return picRoot+"successRate.jpg";
	}

}
