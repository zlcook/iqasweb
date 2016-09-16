package com.cnu.iqas.controller.mobile.store;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.bean.base.DateJsonValueProcessor;
import com.cnu.iqas.bean.base.MyStatus;
import com.cnu.iqas.bean.base.PageView;
import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.bean.store.Commodity;
import com.cnu.iqas.bean.store.CommodityType;
import com.cnu.iqas.bean.store.UserCommodityRel;
import com.cnu.iqas.bean.user.User;
import com.cnu.iqas.constant.PageViewConstant;
import com.cnu.iqas.constant.StatusConstant;
import com.cnu.iqas.service.common.IUserBaseService;
import com.cnu.iqas.service.stroe.CommodityService;
import com.cnu.iqas.service.stroe.CommodityTypeService;
import com.cnu.iqas.service.stroe.StoreService;
import com.cnu.iqas.service.stroe.UserCommodityRelService;
import com.cnu.iqas.utils.JsonTool;
import com.hp.hpl.jena.sparql.pfunction.library.str;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * @author 周亮
 * @version 创建时间：2015年12月22日 上午11:30:53 类说明 商店服务类，功能有：
 *          1.根据商品类型型等级来查看该商品类型下商品，及用户的金币和已购买该类型下商品的信息 2.用户购买某个商品
 * 
 */
@Controller
@RequestMapping(value = "/mobile/store/")
public class MStoreController {
	/**
	 * 商品类型服务接口
	 */
	private CommodityTypeService commodityTypeService;
	/**
	 * 商品服务接口
	 */
	private CommodityService commodityService;
	/**
	 * 用户服务接口
	 */
	private IUserBaseService userService;
	/**
	 * 商店服务接口
	 */
	private StoreService storeService;
	/**
	 * 用户购买商品记录服务接口
	 */
	private UserCommodityRelService userCommodityRelService;

	/**
	 * 获取用户的背包
	 * 
	 * @param userName
	 * @param password
	 * @return { status:1, message:"ok", 
	 * "result": 
	 * { //背包商品 
	 * "count": 2,
	 *         //包含商品类型数 
	 *         "data": 
	 *         [ //商品类型信息集合 
	 *         "0":[ //0类型商品集合 
		 *         { 
		 *         "id": "2c934bc05242e0c1015242e279990005", //商品id 
		 *         "savePath": "ifilesystem/first/store/commodity/pictures/043773b6-1965-4717-b032-3158d6e15209.png",
		 *         //图片路径 
		 *         "buyCount": 2 //用户购买该商品的数量 
		 *         },
		 *          ... 
	 *          ]
	 *           "1":[  //1类型商品集合 
		 *           {
		 *         "id": "2c934bc05242e0c1015242e279990005", //商品id 
		 *         "savePath":"ifilesystem/first/store/commodity/pictures/043773b6-1965-4717-b032-3158d6e15209.png",
		 *         //图片路径 
		 *         "buyCount": 2  //用户购买该商品的数量
		 *          },
	 *          ... ]
	 *        ] 
	 *       }
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "getBag")
	public ModelAndView getBag(String userName, String password) {
		ModelAndView mv = new ModelAndView(PageViewConstant.JSON);
		// 此次操作返回的json
		JSONObject jsonObject = new JSONObject();
		// 此次操作结果描述
		MyStatus status = new MyStatus();
		// 存放result内容的json对象
		JSONObject resultJson = new JSONObject();
		// 存放data内容的json数组
		JSONArray dataArray = new JSONArray();

		try {
			// 1.根据用户名密码查询用户是否存在
			User user = (User) userService.findUser(userName, password);
			if (user != null) {
				// 2.查看用户购买记录

				// 用户可查看商品类型等级
				int typeGrade = user.getStoreGrade();
				// 获取用户购买的所有商品
				// 1.获取typeGrade等级之前的所有类型商品
				int count = 0;// 统计商品类型数
				for (int i = 0; i <= typeGrade; i++) {
					CommodityType type = commodityTypeService.findByGrade(i);
					// 商品类型存在并且可见
					if (type != null && type.getVisible()) {
						// 包含商品类型夹1
						count++;
						// 4.构造查询条件
						String wherejpql = "o.typeid=?";
						// 5.查询出某个类型下的商品
						List<Commodity> commoditys = commodityService.getCommodityByParam(wherejpql, type.getId());

						// 7.查看用户在该商品类型下已购买的所有商品信息
						List<UserCommodityRel> userCommodityRels = userCommodityRelService
								.findUserCommodityRels(user.getUserId(), type.getId());
						JSONArray typeJsonArray = new JSONArray();
						for (Commodity com : commoditys) {
							int buyCount = getBuyCount(userCommodityRels, com);
							if (buyCount != 0) {
								// 6.生成该类型商品的json格式
								JSONObject commJson = new JSONObject();
								commJson.put("id", com.getId());
								commJson.put("savePath", com.getSavePath());
								// 用户购买该商品的数量

								commJson.put("buyCount", buyCount);
								// 添加到类型数组中
								typeJsonArray.add(commJson);
							}
						}

						/*
						 * JSONObject typeJson = new JSONObject();
						 * typeJson.put(type.getGrade(), typeJsonArray);
						 */
						// 将一种类型商品存放到data集合中
						dataArray.add(typeJsonArray);
					}
				}
				resultJson.put("count", count);
				resultJson.put("data", dataArray);
			} else {
				status.setMessage("用户名或密码不正确！");
				status.setStatus(StatusConstant.USER_NAME_OR_PASSWORD_ERROR);
			}

		} catch (Exception e) {
			e.printStackTrace();
			status.setStatus(StatusConstant.UNKONWN_EXECPTION);
			status.setMessage("出现未知异常！");
		} finally {
			JsonTool.createJsonObject(jsonObject, resultJson, status);
			mv.addObject("json", jsonObject.toString());
			return mv;
		}
	}

	/**
	 * 获取商店的商品信息及用户的金币数
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @return { "userDes": { "coinCount": 58, //还剩金币数 "spieces": 6 //已购买种数 },
	 *         "result": { //商店商品 "count": 6, //包含商品总数 "data": [ //商品信息集合 {
	 *         "id": "2c934bc05242e0c1015242e279990005", //商品id "coinCount": 3,
	 *         //商品所值金币数 "savePath":
	 *         "ifilesystem/first/store/commodity/pictures/043773b6-1965-4717-b032-3158d6e15209.png",
	 *         //图片路径 "buyCount": 1 //用户购买该商品的数量 }, .... ] }, "status": 1, //状态码
	 *         "message": "ok" //状态码描述 }
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "getStore")
	public ModelAndView getCommoditysOfType(String userName, String password) {

		ModelAndView mv = new ModelAndView(PageViewConstant.JSON);
		// 此次操作返回的json
		JSONObject jsonObject = new JSONObject();
		// 此次操作结果描述
		MyStatus status = new MyStatus();
		/**
		 * 存放：金币总数，当前可查看商品类型下购买商品种数,封装在json中
		 */
		JSONObject jsonDes = new JSONObject();
		// 构造的商品集合信息json格式数组
		JSONArray jsonArrayCommoditys = new JSONArray();

		try {
			// 1.根据用户名密码查询用户是否存在
			User user = (User) userService.findUser(userName, password);
			if (user != null) {
				jsonDes.put("coinCount", user.getAllCoins());// 金币总数
				// 当前可查看商品类型下已购买商品种数
				jsonDes.put("spieces", user.getSpieces()); // 当前可查看商品类型下已购买商品种数

				// 2.获得用户商品类型等级
				Integer storeGrade = user.getStoreGrade();
				// 3.根据商品类型等级查询商品类型
				CommodityType type = commodityTypeService.findByGrade(storeGrade);
				// 商品类型下所有商品
				List<Commodity> commoditys = null;

				if (type != null) {
					// 7.查看用户在该商品类型下已购买的所有商品信息
					List<UserCommodityRel> userCommodityRels = userCommodityRelService
							.findUserCommodityRels(user.getUserId(), type.getId());
					// 4.构造查询条件
					String wherejpql = "o.typeid=? ";
					commoditys = commodityService.getCommodityByParam(wherejpql, type.getId());
					// 8.构造返回的商品信息json数组，数组中数据为：{商品id,商品购买数量，商品金币数，商品图片保存路径}

					for (Commodity comm : commoditys) {
						// 存放一个商品的json数据
						JSONObject jsonCom = new JSONObject();
						jsonCom.put("id", comm.getId()); // id
						jsonCom.put("coinCount", comm.getCoinCount());// 该商品金币数
						jsonCom.put("savePath", comm.getSavePath());// 图片保存路径
						// 获取用购买该商品的数量
						int buyCount = getBuyCount(userCommodityRels, comm);
						jsonCom.put("buyCount", buyCount); // 购买该商品数量
						jsonArrayCommoditys.add(jsonCom);
					}
				}

			} else {
				status.setMessage("用户名或密码不正确！");
				status.setStatus(StatusConstant.USER_NAME_OR_PASSWORD_ERROR);
			}
		} catch (Exception e) {
			status.setStatus(StatusConstant.UNKONWN_EXECPTION);
			status.setMessage("未知异常");
			e.printStackTrace();
		} finally {
			/**
			 * 将此次操作描述,用户金币数、已买商品种数，商品数组，添加到json中
			 */
			jsonObject.put("userDes", jsonDes);
			JsonTool.createJsonObject(jsonObject, jsonArrayCommoditys, status);
			mv.addObject("json", jsonObject.toString());
			return mv;
		}
	}

	/**
	 * 用户购买商品
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @param id
	 *            购买商品的id
	 * @return { "status": 1, "message": "ok", "isOpen": true //是否可以开启隐藏关 }
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "buyCommodity")
	public ModelAndView buyCommodity(String userName, String password, String id) {
		/**
		 * 购买成功后，用户金币数减；用户商品关系表中添加一条记录，或者刷新该条记录的购买个数。这些操作在事务中完成
		 */
		ModelAndView mv = new ModelAndView(PageViewConstant.JSON);
		// 此次操作返回的json
		JSONObject jsonObject = new JSONObject();
		// 此次操作结果描述
		MyStatus status = new MyStatus();
		// 标识用户是否可以打开当前等级商品类下的隐藏关
		boolean isOpen = false;
		try {
			// 1.根据用户名密码查询用户是否存在
			User user = (User) userService.findUser(userName, password);
			if (user != null) {
				if (id != null && !"".equals(id.trim())) {
					// 1.查询商品
					Commodity com = commodityService.find(id);
					if (com != null) {
						// 2.商品金币数
						int coinValue = com.getCoinCount();
						// 3.商品类型
						String typeid = com.getTypeid();
						CommodityType type = commodityTypeService.find(com.getTypeid());
						// 3.5商品类型和用户可购买的商品类型是否相同
						if (type.getGrade() == user.getStoreGrade()) {
							// 4.用户当前拥有的金币数
							int ownCoins = user.getAllCoins();
							// 5.用户拥有金币数大于商品价格可以买
							if (ownCoins >= coinValue) {
								// 6。用户金币减少
								user.setAllCoins(ownCoins - coinValue);
								// 7.查看购买记录是否存在，存在则在数量加1
								UserCommodityRel ucRel = userCommodityRelService.findUserCommodityRel(user.getUserId(),
										com.getId());
								// 购买记录不存在
								if (ucRel == null) {
									// 7.生成一条购买记录
									ucRel = new UserCommodityRel();
									ucRel.setCoId(id);
									ucRel.setTypeId(typeid);
									ucRel.setUserId(user.getUserId());
									ucRel.setCount(1);
									// 购买商品种数加1
									user.setSpieces(user.getSpieces() + 1);
								} else {
									// 购买记录存在，购买数量加1
									ucRel.setCount(ucRel.getCount() + 1);
								}
								// 购买记录修改日期设为当前日期
								ucRel.setModifyTime(new Date());
								// 刷新用户和保存购买记录
								storeService.updateUserAndCommodity(user, ucRel);
								// 判断用户是否可以开启隐藏关
								// 1.获取商品类型
								// 2.获取商品类型下商品数
								int commCount = type.getCount();
								// 3.已购买商品种数是否完
								isOpen = (commCount == user.getSpieces()) ? true : false;

							} else {
								status.setStatus(StatusConstant.USER_COINS_NOT_ENOUGH);
								status.setMessage("您的金币数不足!");
							}
						} else {
							status.setStatus(StatusConstant.STORE_UNEXIST_COMMODITY);
							status.setMessage("你当前不能购买该类型下的商品! 类型号:" + type.getGrade());
						}
					} else {
						status.setStatus(StatusConstant.STORE_UNEXIST_COMMODITY);
						status.setMessage("该商品不存在!");
					}
				} else {
					status.setMessage("商品id参数有误！");
					status.setStatus(StatusConstant.PARAM_ERROR);
				}
			} else {
				status.setMessage("用户名或密码不正确！");
				status.setStatus(StatusConstant.USER_NAME_OR_PASSWORD_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			status.setStatus(StatusConstant.UNKONWN_EXECPTION);
			status.setMessage("未知异常啊！");
		} finally {
			JsonTool.putStatusJson(status, jsonObject);
			jsonObject.put("isOpen", isOpen);// 是否可以开启隐藏关
			mv.addObject("json", jsonObject.toString());
			return mv;
		}
	}

	/**
	 * 获取用户购买某商品的数量,如果某商品不在用户购买的商品之内则返回0
	 * 
	 * @param userCommodityRels
	 *            用户已购买的商品
	 * @param comm
	 *            要判断的商品
	 * @return ,
	 */
	private int getBuyCount(List<UserCommodityRel> userCommodityRels, Commodity comm) {
		for (UserCommodityRel rel : userCommodityRels) {
			if (rel.getCoId().equals(comm.getId())) {
				return rel.getCount();
			}
		}
		return 0;
	}

	public CommodityTypeService getCommodityTypeService() {
		return commodityTypeService;
	}

	@Resource
	public void setCommodityTypeService(CommodityTypeService commodityTypeService) {
		this.commodityTypeService = commodityTypeService;
	}

	public CommodityService getCommodityService() {
		return commodityService;
	}

	@Resource
	public void setCommodityService(CommodityService commodityService) {
		this.commodityService = commodityService;
	}

	public IUserBaseService getUserService() {
		return userService;
	}

	@Resource
	public void setUserService(IUserBaseService userService) {
		this.userService = userService;
	}

	public StoreService getStoreService() {
		return storeService;
	}

	@Resource
	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}

	public UserCommodityRelService getUserCommodityRelService() {
		return userCommodityRelService;
	}

	@Resource
	public void setUserCommodityRelService(UserCommodityRelService userCommodityRelService) {
		this.userCommodityRelService = userCommodityRelService;
	}

}
