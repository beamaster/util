package com.steam.constant;

/**
 * @ClassName: JdConstants
 * @Description: 京东常量类
 * @author: taoweifeng
 * @date: 2017年6月8日 下午3:02:40
 */
public abstract class JdConstants {

	/** 默认时间格式 **/
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/** Date默认时区 **/
	public static final String DATE_TIMEZONE = "GMT+8";

	/** UTF-8字符集 **/
	public static final String CHARSET_UTF8 = "UTF-8";

	/** GBK字符集 **/
	public static final String CHARSET_GBK = "GBK";

	/** JSON 应格式 */
	public static final String FORMAT_JSON = "json";
	/** MD5签名方式 */
	public static final String SIGN_METHOD_MD5 = "md5";
	/** HMAC签名方式 */
	public static final String SIGN_METHOD_HMAC = "hmac";
	/** 授权地址 */
	public static final String PRODUCT_AUTH_URL = "https://kploauth.jd.com/oauth/token";

	public static final String VERSION = "1.0";

	/** 成功的响应吗 */
	public static final String SUCCESS_CODE = "0";
	public static final String SUCCESS_RESULTCODE = "0000";

	/** 返回的错误码 */
	public static final String ERROR_RESPONSE = "error_response";
	public static final String ERROR_CODE = "code";
	public static final String ERROR_MSG = "msg";

	/** 响应消息 */
	public static final String RESPONSE_RESULTCODE = "resultCode";
	public static final String RESPONSE_RESULTMESSAGE = "resultMessage";
	
	/* ----------- 接口常量类型维护 start ---------------------------------- */
	/** 京东支付方式 paymentType*/
	/** 1：货到付款*/
	public static final Integer PAYMENT_TYPE_1 = 1;
	/** 2：邮局付款*/
	public static final Integer PAYMENT_TYPE_2 = 2;
	/** 4：在线支付（余额支付） 注：我们固定用这种支付方式*/
	public static final Integer PAYMENT_TYPE_4 = 4;
	/** 5：公司转账*/
	public static final Integer PAYMENT_TYPE_5 = 5;
	/** 6：银行转账*/
	public static final Integer PAYMENT_TYPE_6 = 6;
	/** 7：网银钱包*/
	public static final Integer PAYMENT_TYPE_7 = 7;
	/** 101：金采支付*/
	public static final Integer PAYMENT_TYPE_101 = 101;
	
	/** 分类等级 catClass*/
	/** 0:一级*/
	public static final Integer CATCLASS_1 = 0;
	/** 1:二级*/
	public static final Integer CATCLASS_2 = 1;
	/** 2：三级*/
	public static final Integer CATCLASS_3 = 2;
	
	/** 推送消息类型 type*/
	/** 1代表订单拆分变更*/
	public static final String MESSAGE_TYPE_1 = "1";
	/** 2代表商品价格变更*/
	public static final String MESSAGE_TYPE_2 = "2";
	/** 4商品上下架变更消息*/
	public static final String MESSAGE_TYPE_4 = "4";
	/** 5代表该订单已妥投*/
	public static final String MESSAGE_TYPE_5 = "5";
	/** 6代表添加、删除商品池内商品*/
	public static final String MESSAGE_TYPE_6 = "6";
	/** 10代表订单取消（不区分取消原因）*/
	public static final String MESSAGE_TYPE_10 = "10";
	/** 11申请开票信息*/
	public static final String MESSAGE_TYPE_11 = "11";
	/** 12代表配送单生成（打包完成后推送，仅提供给买卖宝类型客户）*/
	public static final String MESSAGE_TYPE_12 = "12";
	/** 13换新订单生成（换新单下单后推送，仅提供给买卖宝类型客户）*/
	public static final String MESSAGE_TYPE_13 = "13";
	/** 14支付失败消息*/
	public static final String MESSAGE_TYPE_14 = "14";
	/** 15 7天未支付取消消息/未确认取消（cancelType,1:7天未支付取消消息;2:未确认取消）*/
	public static final String MESSAGE_TYPE_15 = "15";
	/** 16商品介绍及规格参数变更消息*/
	public static final String MESSAGE_TYPE_16 = "16";
	/** 17 赠品促销变更消息*/
	public static final String MESSAGE_TYPE_17 = "17";
	/** 25新订单消息*/
	public static final String MESSAGE_TYPE_25 = "25";
	/** 50京东地址变更消息推送*/
	public static final String MESSAGE_TYPE_50 = "50";
	
	/**开票方式 invoiceState*/
	/** 0为订单预借*/
	public static final Integer INVOICE_STATE_0 = 0;
	/** 1为随货开票*/
	public static final Integer INVOICE_STATE_1 = 1;
	/** 2为集中开票(买断模式固定传2) 注：我们固定使用这种方式*/
	public static final Integer INVOICE_STATE_2 = 2;
	
	/**发票类型 invoiceType*/
	/** 1普通发票*/
	public static final Integer INVOICE_TYPE_1 = 1;
	/** 2增值税发票*/
	public static final Integer INVOICE_TYPE_2 = 2;
	
	/**发票抬头类型 selectedInvoiceTitle*/
	/** 4个人*/
	public static final Integer SELECTED_INVOICE_TITLE_4 = 4;
	/** 5单位*/
	public static final Integer SELECTED_INVOICE_TITLE_5 = 5;
	
	/**发票内容 invoiceContent 备注:若增值发票则只能选1 明细*/
	/** 1:明细*/
	public static final Integer INVOICE_CONTENT_1 = 1;
	/** 3：电脑配件*/
	public static final Integer INVOICE_CONTENT_3 = 3;
	/** 19:耗材*/
	public static final Integer INVOICE_CONTENT_19 = 19;
	/** 22：办公用品*/
	public static final Integer INVOICE_CONTENT_22 = 22;
	
	/**是否使用余额 isUseBalance*/
	/** 预存款下单固定传1， 使用余额*/
	public static final Integer USE_BALANCE_1 = 1;
	/** 非预存款下单固定0 不使用余额*/
	public static final Integer USE_BALANCE_0 = 0;
	
	/**是否预占库存 submitState*/
	/** 0是预占库存（需要调用确认订单接口） 注：我们固定使用此方式*/
	public static final Integer SUBMIT_STATE_0 = 0;
	/** 1不预占库存*/
	public static final Integer SUBMIT_STATE_1 = 1;
	
	/** 日期 */
	/** 0表示当天*/
	public static final Integer DATE_0 = 0;
	/** 1表示明天*/
	public static final Integer DATE_1 = 1;
	/** 2表示后天*/
	public static final Integer DATE_2 = 2;
	
	/** 下单价格模式 doOrderPriceMode*/
	/** 0: 客户端订单价格快照不做验证对比，还是以京东端价格正常下单;*/
	public static final Integer DO_ORDER_PRICE_MODE_0 = 0;
	/** 1:必需验证客户端订单价格快照，如果快照与京东端价格不一致返回下单失败，需要更新商品价格后，重新下单*/
	public static final Integer DO_ORDER_PRICE_MODE_1 = 1;
	
	/** 开票 billingType*/
	/** 1-集中开票*/
	public static final Integer BILLING_TYPE_1 = 1;
	/** 2-分别开票（不传默认为集中开票）*/
	public static final Integer BILLING_TYPE_2 = 2;
	
	/** state*/
	public static final Integer STATE_0 = 0;
	public static final Integer STATE_1 = 1;
	public static final Integer STATE_2 = 2;
	
	
	/* ----------- 接口常量类型维护 end ------------------------------------ */
	

	/* ----------- 系统错误码code，除售后API接口部分 start ------------------ */

	/** 无效token(请检查app_key和token是否是同一条记录。或者授权账号是否改过密码，改密码会导致token过期) */
	public static final String CODE_1003 = "1003";
	/** token过期(token过期，调用刷新token服务获取新令牌) */
	public static final String CODE_1004 = "1004";
	/** 无效app_key(所选的appkey必须是对接邮件分配的) */
	public static final String CODE_1005 = "1005";
	/** 缺少app_key参数(添加app_key参数) */
	public static final String CODE_1020 = "1020";
	/** 缺少token参数(添加token参数) */
	public static final String CODE_1022 = "1022";
	/** refresh_token过期(重新获取token，会得到新的refresh_token) */
	public static final String CODE_2011 = "2011";
	/** refresh_token不存在(refresh_token无效，重新获取) */
	public static final String CODE_2012 = "2012";
	/** http 调用参数param_json为空(请检查参数) */
	public static final String CODE_3001 = "3001";
	/** 检查param_json参数格式(请校验改参数格式) */
	public static final String CODE_3002 = "3002";
	/** 请检查param_json参数格式(请校验改参数格式) */
	public static final String CODE_3003 = "3003";
	/** http调用 API接口连接超时(查看超时原因) */
	public static final String CODE_3004 = "3004";
	/** 缺少配置参数api_host，调用失败(请检查参数是否完整无误) */
	public static final String CODE_3020 = "3020";
	/** 限制时间内调用失败次数(调整程序合理调用API，请隔日再调用) */
	public static final String CODE_3021 = "3021";
	/** 缺少版本参数(版本为必传参数) */
	public static final String CODE_3022 = "3022";
	/** 获取api信息调用异常(请检查网络连接) */
	public static final String CODE_3023 = "3023";
	/** 缺少方法名参数(方法名为必传参数) */
	public static final String CODE_3024 = "3024";
	/** 不存在的方法名或者版本号(请检查 方法名和版本号是否已存在并正确) */
	public static final String CODE_3025 = "3025";
	/** jsf调用错误，参数param_json格式错误(请检查param_json格式) */
	public static final String CODE_3030 = "3030";
	/** jsf调用API接口连接超时(查看超时原因) */
	public static final String CODE_3035 = "3035";
	/** API接口响应超时(查看超时原因) */
	public static final String CODE_3036 = "3036";
	/** 服务器系统处理错误(后台错误) */
	public static final String CODE_3038 = "3038";
	/** 无增值包权限，请申请开通(开通api对应增值包) */
	public static final String CODE_3039 = "3039";
	/** appkey 已加入黑名单，被禁用 */
	public static final String CODE_3040 = "3040";
	/** 已达到并发数限制(请稍后再试) */
	public static final String CODE_3041 = "3041";
	/** API 等级为 X */
	public static final String CODE_3042 = "3042";
	/** http服务返回结果json解析出错(后台返回结果格式错误) */
	public static final String CODE_4000 = "4000";

	/* ----------- 系统错误码code，除售后API接口部分 end ------------------ */

	/* ----------- 业务错误码resultCode start --------------------------- */
	/** 0*** 成功 */
	/** 操作成功(查询相关接口，调用成功时返回) */
	public static final String RESULTCODE_0000 = "0000";
	/** 下单成功 */
	public static final String RESULTCODE_0001 = "0001";
	/** 取消订单成功 */
	public static final String RESULTCODE_0002 = "0002";
	/** 确认订单成功 */
	public static final String RESULTCODE_0003 = "0003";
	/** 申请开票成功(目前只有联通使用) */
	public static final String RESULTCODE_0004 = "0004";
	/** 全部开票成功(目前只有联通使用) */
	public static final String RESULTCODE_0005 = "0005";
	/** 部分开票成功(目前只有联通使用) */
	public static final String RESULTCODE_0006 = "0006";
	/** 取消开票成功(目前只有联通使用) */
	public static final String RESULTCODE_0007 = "0007";
	/** 重复提交(同一三方订单号已经存在有效订单。此时下单结果 result会返回该三方订单号对应订单信息) */
	public static final String RESULTCODE_0008 = "0008";
	/** 返回数据为空 */
	public static final String RESULTCODE_0010 = "0010";

	/** 1*** 参数问题(通用，请检查参数) */
	/** 参数为空 */
	public static final String RESULTCODE_1001 = "1001";
	/** 参数格式不正确 */
	public static final String RESULTCODE_1002 = "1002";
	/** 参数值不正确 */
	public static final String RESULTCODE_1003 = "1003";
	/** 参数重复 */
	public static final String RESULTCODE_1004 = "1004";
	/** 入参转化错误(请检查输入参数) */
	public static final String RESULTCODE_1005 = "1005";

	/** 2*** 权限问题(通用，请联系业务人员咨询对应权限问题) */
	/** 用户权限不足 */
	public static final String RESULTCODE_2001 = "2001";
	/** 合同权限不足(1.重新获取token 2、联系京东运营咨询是否修改了主数据) */
	public static final String RESULTCODE_2002 = "2002";
	/** 企业权限不足 */
	public static final String RESULTCODE_2003 = "2003";
	/** 商品池权限不足 */
	public static final String RESULTCODE_2004 = "2004";
	/** 金彩权限问题 */
	public static final String RESULTCODE_2005 = "2005";
	/** 无有效增票资质 */
	public static final String RESULTCODE_2006 = "2006";
	/** token已过期(请重新刷新或者获取token) */
	public static final String RESULTCODE_2007 = "2007";

	/** 3*** 业务问题、31** 下单业务问题 */
	/** 价格不存在 */
	public static final String RESULTCODE_3001 = "3001";
	/** 提交订单过快(1分钟后提交订单) */
	public static final String RESULTCODE_3002 = "3002";
	/** 订单类型不支持(订单类型只支持普通自营、厂家直送、实物礼品卡、延保商品) */
	public static final String RESULTCODE_3003 = "3003";
	/** 商品类型受限制(Sku不在商品池，或者商品正在参加秒杀活动等) */
	public static final String RESULTCODE_3004 = "3004";
	/** 商品没查询到(Sku对应商品不存在，检查sku输入是否正确) */
	public static final String RESULTCODE_3005 = "3005";
	/** 商品不能进行货到付款下单(更换其他支付方式) */
	public static final String RESULTCODE_3006 = "3006";
	/** 地址不能进行货到付款下单(更换其他支付方式) */
	public static final String RESULTCODE_3007 = "3007";
	/** 库存不足 */
	public static final String RESULTCODE_3008 = "3008";
	/** 区域限制校验没通过(商品在该区域受限) */
	public static final String RESULTCODE_3009 = "3009";
	/** 实体礼品卡和其他实物不能混合下单 */
	public static final String RESULTCODE_3010 = "3010";
	/** 大家电暂不支持公司转账 预占下单 */
	public static final String RESULTCODE_3011 = "3011";
	/** 海尔仓大家电，不支持后款预占下单 */
	public static final String RESULTCODE_3012 = "3012";
	/** 厂家直送商品只能下先款订单 */
	public static final String RESULTCODE_3013 = "3013";
	/** 厂家直送商品不能使用普票随货下单 */
	public static final String RESULTCODE_3014 = "3014";
	/** 实物礼品卡订单只能下普票订单 */
	public static final String RESULTCODE_3015 = "3015";
	/** 配额不足或者已被锁定(月结权限用户、检查月结额度) */
	public static final String RESULTCODE_3016 = "3016";
	/** 余额不足(检查对应支付方式剩余额度) */
	public static final String RESULTCODE_3017 = "3017";

	/** 3051开始的为下游接口异常 */
	/** 价格获取失败(接口调用失败，可重试) */
	public static final String RESULTCODE_3051 = "3051";
	/** 主数据接口业务异常(接口调用失败，可重试) */
	public static final String RESULTCODE_3052 = "3052";
	/** 商品基本信息接口调用失败(接口调用失败，可重试) */
	public static final String RESULTCODE_3053 = "3053";
	/** 商品扩展接口调用失败(接口调用失败，可重试) */
	public static final String RESULTCODE_3054 = "3054";
	/** 大家电接口调用失败(接口调用失败，可重试) */
	public static final String RESULTCODE_3055 = "3055";
	/** 赠品附件接口调用失败(接口调用失败，可重试) */
	public static final String RESULTCODE_3056 = "3056";
	/** 区分大家电和中小件商品失败(接口调用失败，可重试) */
	public static final String RESULTCODE_3057 = "3057";
	/** 下单失败，请重新提交订单(接口调用失败，可重试（下游下单接口返回，不确定重试能否成功）) */
	public static final String RESULTCODE_3058 = "3058";

	/** 31** 确认订单业务问题 */
	/** 确认下单最终失败，请重新确认订单(可重新确认) */
	public static final String RESULTCODE_3101 = "3101";
	/** jdOrderId不存在 */
	public static final String RESULTCODE_3102 = "3102";
	/** 该订单已确认下单(已确认订单，不需要重复确认) */
	public static final String RESULTCODE_3103 = "3103";
	/** 不能单独确认子订单 */
	public static final String RESULTCODE_3104 = "3104";
	/** 订单对应子单已取消，不能确认！ */
	public static final String RESULTCODE_3105 = "3105";
	/** 查询子单异常 */
	public static final String RESULTCODE_3106 = "3106";
	/** 本地子单与ERP子单不一致 */
	public static final String RESULTCODE_3107 = "3107";
	/** 确认订单操作失败 */
	public static final String RESULTCODE_3108 = "3108";

	/** 32** 取消订单业务问题 */
	/** 取消订单失败，请重新取消订单(可重新取消) */
	public static final String RESULTCODE_3201 = "3201";
	/** jdOrderId不存在 */
	public static final String RESULTCODE_3202 = "3202";
	/** 该订单已经被取消(已取消订单，不需要重复取消) */
	public static final String RESULTCODE_3203 = "3203";
	/** 不能取消已经生产订单 */
	public static final String RESULTCODE_3204 = "3204";
	/** 不能取消未确认订单(取消已确认订单接口使用) */
	public static final String RESULTCODE_3205 = "3205";
	/** 不能取消预占并且已确认订单(取消已确认订单接口使用) */
	public static final String RESULTCODE_3206 = "3206";
	/** 不能取消父订单(取消已确认订单接口使用) */
	public static final String RESULTCODE_3207 = "3207";
	/** 不能取消已确认订单 */
	public static final String RESULTCODE_3208 = "3208";
	/** 不能取消子订单 */
	public static final String RESULTCODE_3209 = "3209";
	/** 查询子单异常 */
	public static final String RESULTCODE_3210 = "3210";
	/** 取消订单失败，存在已确认的子单 */
	public static final String RESULTCODE_3211 = "3211";
	/** 取消订单操作失败 */
	public static final String RESULTCODE_3212 = "3212";

	/** 33** 发票业务问题 */
	/** 待审核(目前只有联通使用) */
	public static final String RESULTCODE_3301 = "3301";
	/** 驳回(目前只有联通使用) */
	public static final String RESULTCODE_3302 = "3302";
	/** 通过待开票(目前只有联通使用) */
	public static final String RESULTCODE_3303 = "3303";
	/** 申请单不存在(目前只有联通使用) */
	public static final String RESULTCODE_3304 = "3304";

	/** 34** 其余接口问题 */
	/** 订单不存在(没查询到对应订单) */
	public static final String RESULTCODE_3401 = "3401";
	/** 订单配送信息不存在 */
	public static final String RESULTCODE_3402 = "3402";
	/** 支付时余额不足 */
	public static final String RESULTCODE_3403 = "3403";
	/** 订单不能发起支付 */
	public static final String RESULTCODE_3404 = "3404";
	/** 没查询到对应地址 */
	public static final String RESULTCODE_3405 = "3405";
	/** 价格不存在 */
	public static final String RESULTCODE_3406 = "3406";
	/** 获取余额业务异常 */
	public static final String RESULTCODE_3407 = "3407";
	/** 该支付类型不支持余额查询 */
	public static final String RESULTCODE_3408 = "3408";
	/** 区分大家电和中小件商品失败 */
	public static final String RESULTCODE_3409 = "3409";
	/** 订单未被挂起(目前只有联通使用) */
	public static final String RESULTCODE_3410 = "3410";

	/** 5*** 系统异常 */
	/** 服务异常，请稍后重试(可重试) */
	public static final String RESULTCODE_5001 = "5001";
	/** 未知错误 */
	public static final String RESULTCODE_5002 = "5002";

	/* ----------- 业务错误码resultCode end --------------------------- */

	/**京东发票所需参数*/
	/**手机号*/
	public static final String MOBILE = "18115187140";
	/**邮箱地址*/
	public static final String EMAIL = "custom-service@lending-cloud.com";
	/**发票抬头*/
	public static final String COMPANYNAME = "苏州云融生活商贸有限公司";
	/**增值税发票收货四级地址**/
	public static final String ADDRESS = "52651";
	/**增值票收票人姓名**/
	public static final String INVOICENAME = "焦娇";
	/**增值票收票人电话**/
	public static final String INVOICEPHONE = "18120161850";
	/**增值票收票人所在地址**/
	public static final String INVOICADDRESS = "新城科技园研发总部园2栋18楼云融网";

}
