package com.steam.util.string;

import com.sencloud.Message;
import com.sencloud.entity.Order;
import com.sencloud.entity.PaymentMethod;
import com.sencloud.entity.ShippingConditions;
import com.sencloud.meta.Dto;
import com.steam.util.LifeConstants;
import com.steam.util.TypeCaseHelper;
import com.steam.util.date.DateUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LifeUtil {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(LifeUtil.class);

    public static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 判断对象是否Empty(null或元素为0)<br>
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj 待检查对象
     * @return boolean 返回的布尔值
     */
    public static boolean isEmpty(Object pObj) {
        if (pObj == null)
            return true;
        if (pObj == "")
            return true;
        if (pObj instanceof String) {
            if (((String) pObj).length() == 0) {
                return true;
            }
        } else if (pObj instanceof Collection) {
            if (((Collection) pObj).size() == 0) {
                return true;
            }
        } else if (pObj instanceof Map) {
            if (((Map) pObj).size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断对象是否为NotEmpty(!null或元素>0)<br>
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj 待检查对象
     * @return boolean 返回的布尔值
     */
    public static boolean isNotEmpty(Object pObj) {
        if (pObj == null)
            return false;
        if (pObj == "")
            return false;
        if (pObj instanceof String) {
            if (((String) pObj).length() == 0) {
                return false;
            }
        } else if (pObj instanceof Collection) {
            if (((Collection) pObj).size() == 0) {
                return false;
            }
        } else if (pObj instanceof Map) {
            if (((Map) pObj).size() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一个字符串是否由数字、字母、数字字母组成
     *
     * @param pStr   需要判断的字符串
     * @param pStyle 判断规则
     * @return boolean 返回的布尔值
     */
    public static boolean isTheStyle(String pStr, String pStyle) {
        for (int i = 0; i < pStr.length(); i++) {
            char c = pStr.charAt(i);
            if (pStyle.equals(LifeConstants.S_STYLE_N)) {
                if (!Character.isDigit(c))
                    return false;
            } else if (pStyle.equals(LifeConstants.S_STYLE_L)) {
                if (!Character.isLetter(c))
                    return false;
            } else if (pStyle.equals(LifeConstants.S_STYLE_NL)) {
                if (Character.isLetterOrDigit(c))
                    return false;
            }
        }
        return true;
    }

    /**
     * JavaBean之间对象属性值拷贝
     *
     * @param pFromObj Bean源对象
     * @param pToObj   Bean目标对象
     */
    public static void copyPropBetweenBeans(Object pFromObj, Object pToObj) {
        if (pToObj != null) {
            try {
                BeanUtils.copyProperties(pToObj, pFromObj);
            } catch (Exception e) {
                logger.error("==开发人员请注意:==\n JavaBean之间的属性值拷贝发生错误啦!"
                        + "\n详细错误信息如下:");
                e.printStackTrace();
            }
        }
    }

    /**
     * 将JavaBean对象中的属性值拷贝到Dto对象
     *
     * @param pFromObj JavaBean对象源
     * @param pToDto   Dto目标对象
     */
    public static void copyPropFromBean2Dto(Object pFromObj, Dto pToDto) {
        if (pToDto != null) {
            try {
                pToDto.putAll(BeanUtils.describe(pFromObj));
                // BeanUtils自动加入了一个Key为class的键值,故将其移除
                pToDto.remove("class");
            } catch (Exception e) {
                logger.error("==开发人员请注意:==\n 将JavaBean属性值拷贝到Dto对象发生错误啦!"
                        + "\n详细错误信息如下:");
                e.printStackTrace();
            }
        }
    }

    /**
     * 实现字符串首字母大写
     *
     * @param str 字符串(任意字符串)
     * @return String 首字母大写
     */
    public static String upperCaseFirstChar(String str) {
        str = str.toLowerCase();// 先转换成小写
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
        return str;
    }

    /**
     * 获取ip地址,防止集群、代理
     *
     * @param request
     * @return ip
     */
    public static String getAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * String 转成Long数组<一句话功能简述> <功能详细描述>
     *
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Long[] string2Long(String str) {
        if ("".equals(str) || null == str) {
            return null;
        }
        String[] strArray = str.split(",");
        Long[] longArray = new Long[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            longArray[i] = Long.valueOf(strArray[i]);
        }
        return longArray;
    }

    /**
     * String 转成BigDeciaml集合<一句话功能简述> <功能详细描述>
     *
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static List<BigDecimal> string2BigDecimal(String str) {
        if ("".equals(str) || null == str) {
            return null;
        }
        String[] strArray = str.split(",");
        List<BigDecimal> bigDecimalList = new ArrayList<BigDecimal>();
        for (int i = 0; i < strArray.length; i++) {
            BigDecimal bigDecimal = new BigDecimal(strArray[i]);
            bigDecimalList.add(bigDecimal);
        }
        return bigDecimalList;
    }

    /**
     * 验证电话号码是否正确
     *
     * @param mobiles
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,2-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证是否是邮件格式<一句话功能简述> <功能详细描述>
     *
     * @param email
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9]*([-_]|[-.])?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 计算Map合
     *
     * @param totalMap
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static BigDecimal totalMap(Map<Long, BigDecimal> totalMap) {
        BigDecimal bd = new BigDecimal(0);
        for (Long key : totalMap.keySet()) {
            bd = bd.add(totalMap.get(key));
        }
        return bd;
    }

    /**
     * <一句话功能简述> <功能详细描述>
     *
     * @param content
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String html(String content) {
        if (content == null)
            return "";
        String html = content;

        html = html.replaceAll("&", "&amp;");
        html = html.replace("\"", "&quot;"); // "
        html = html.replace("\t", "&nbsp;&nbsp;");// 替换跳格
        html = html.replace(" ", "&nbsp;");// 替换空格
        html = html.replace("<", "&lt;");

        html = html.replaceAll(">", "&gt;");

        return html;
    }

    /**
     * <一句话功能简述> <功能详细描述>
     *
     * @param content
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String htmlToString(String content) {
        if (content == null)
            return "";
        String html = content;

        html = html.replaceAll("&amp;", "&");
        html = html.replace("&quot;", "\""); // "
        html = html.replace("&nbsp;&nbsp;", "\t");// 替换跳格
        html = html.replace("&nbsp;", " ");// 替换空格
        html = html.replace("&lt;", "<");

        html = html.replaceAll("&gt;", ">");

        return html;
    }

    /**
     * 获取随机字符串
     *
     * @return
     */
    public static String getNonceStr() {
        // 随机数
        String currTime = DateUtil.generateTime();
        // 8位日期
        String strTime = currTime.substring(8, currTime.length());
        // 四位随机数
        String strRandom = LifeUtil.buildRandom(4) + "";
        // 10位序列号,可以自行调整。
        return strTime + strRandom;
    }

    /**
     * 取出一个指定长度大小的随机正整数.
     *
     * @param length int 设定所取出随机数的长度。length小于11
     * @return int 返回生成的随机数。
     */
    public static int buildRandom(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }

    /**
     * 获取订单超时时间
     *
     * @param order
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getOrderTimeOut(Order order) {
        PaymentMethod paymethod = order.getPaymentMethod();
        StringBuffer timeoutStr = new StringBuffer("");
        String tipHour = Message
                .warn("shop.orderDetail.orderCompleteHelp.hour").getContent();
        String tipDay = Message.warn("shop.orderDetail.orderCompleteHelp.day")
                .getContent();
        String tipMinutes = Message.warn(
                "shop.orderDetail.orderCompleteHelp.minute").getContent();
        if (paymethod != null) {
            // if (PaymentMethod.Method.offline == paymethod.getMethod())
            // {
            Integer timeout = paymethod.getTimeout();
            if (timeout > 60 && timeout < 1440) {
                Integer hour = timeout / 60;
                timeoutStr.append(hour);
                timeoutStr.append(tipHour);
                Integer minutes = timeout % 60;
                if (minutes != 0) {
                    timeoutStr.append(minutes);
                    timeoutStr.append(tipMinutes);
                }
            }
            if (timeout >= 1440) {
                Integer day = timeout / (24 * 60);
                timeoutStr.append(day);
                timeoutStr.append(tipDay);
            }
            if (timeout <= 60) {
                timeoutStr.append(timeout);
                timeoutStr.append(tipMinutes);
            }
            // }
        }
        return timeoutStr.toString();
    }

    /**
     * double类型的数字转换成百分比string
     *
     * @return
     */
    public static String doubleToPercentString(Double rate) {
        NumberFormat nFromat = NumberFormat.getPercentInstance();
        String rates = nFromat.format(rate);
        return rates;
    }

    /**
     * 产生随机字符串
     *
     * @param length
     * @return
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 计算运费
     *
     * @param shippingMethodFeightDefault 默认运费的运费方式参数
     * @param shippingMethodFeights       非默认运费的运费方式
     * @param shippingConditionss         指定条件包邮
     * @param toAreaId                    寄送地区
     * @param pricing                     计价方式
     * @param quantity                    寄送数量(件数 重量 体积)
     * @param weight                      寄送重量
     * @param volume                      寄送体积
     * @param amount                      金额
     * @return
     */
    public static double caculateFreightPrice(Map shippingMethodFeightDefault,
                                              List<Map> shippingMethodFeights,
                                              List<ShippingConditions> shippingConditionss, Long toAreaId,
                                              String pricing, Integer quantity, Double weight, Double volume,
                                              Double amount) {
        double freightPrice = 0.0;// 运费
        double firstFree = 0.0;// 首费
        double firstWeight = 0.0;// 首件（重）
        double continuedFree = 0.0;// 续费
        double continuedHeavy = 0.0;// 续件（重）

        firstFree = TypeCaseHelper.convert2Double(shippingMethodFeightDefault
                .get("firstFree"));
        firstWeight = TypeCaseHelper.convert2Double(shippingMethodFeightDefault
                .get("firstWeight"));
        continuedFree = TypeCaseHelper
                .convert2Double(shippingMethodFeightDefault
                        .get("continuedFree"));
        continuedHeavy = TypeCaseHelper
                .convert2Double(shippingMethodFeightDefault
                        .get("continuedHeavy"));

        // 判断是否符合包邮
        if (shippingConditionss != null && shippingConditionss.size() > 0) {
            for (ShippingConditions shippingConditions : shippingConditionss) {
                String selectArea = shippingConditions.getSelectArea();
                String[] selectAreaArray = selectArea.split(",");
                boolean flag = false;
                for (String selectAreaItem : selectAreaArray) {
                    if (toAreaId.toString().equals(selectAreaItem)) {
                        flag = true;
                    }
                }
                if (flag) {
                    double param = 0.0;//判断包邮的入参(件数，重量，体积)
                    // 计价方式（1：按件数；2：按重量；3：按体积）
                    if ("1".equals(pricing)) {
                        param = (double) quantity;
                        if (isShippingConditioned(shippingConditions, param, amount)) {
                            return freightPrice;
                        }
                    } else if ("2".equals(pricing)) {
                        param = (double) weight;
                        if (isShippingConditioned(shippingConditions, param, amount)) {
                            return freightPrice;
                        }
                    } else {
                        param = (double) volume;
                        if (isShippingConditioned(shippingConditions, param, amount)) {
                            return freightPrice;
                        }
                    }
                }
            }
        }

        // 指定地区运费定义
        if (shippingMethodFeights != null && shippingMethodFeights.size() > 0) {
            for (Map map : shippingMethodFeights) {
                String deliveryArea = TypeCaseHelper.convert2String(map
                        .get("deliveryArea"));
                String[] deliveryAreaArray = deliveryArea.split(",");
                boolean flag = false;
                for (String deliveryAreaItem : deliveryAreaArray) {
                    if (toAreaId.toString().equals(deliveryAreaItem)) {
                        flag = true;
                    }
                }
                if (flag) {
                    firstFree = TypeCaseHelper.convert2Double(map
                            .get("firstFree"));
                    firstWeight = TypeCaseHelper.convert2Double(map
                            .get("firstWeight"));
                    continuedFree = TypeCaseHelper.convert2Double(map
                            .get("continuedFree"));
                    continuedHeavy = TypeCaseHelper.convert2Double(map
                            .get("continuedHeavy"));
                    break;
                }
            }
        }
        // 计价方式（1：按件数；2：按重量；3：按体积）
        double parameter = 0.0; //用来存放计算指定地区某种计价方式的入参（总件数或总体积或总重量）
        if ("1".equals(pricing)) {
            parameter = (double) quantity;
        } else if ("2".equals(pricing)) {
            parameter = (double) weight;
        } else {
            parameter = (double) volume;
        }
        // 首件（重）为0，表示卖家承担运费
        if (firstWeight == 0.0) {
            freightPrice = firstFree;
        } else if (parameter <= firstWeight) {
            freightPrice = firstFree;
        } else {
            freightPrice = firstFree
                    + Math.ceil(((parameter - firstWeight) / continuedHeavy))
                    * continuedFree;
        }
        return freightPrice;
    }

    /**
     * 判断是否符合包邮
     *
     * @param shippingConditions 指定包邮纪录
     * @param param              总件数或总重量或总体积
     * @param amount             总金额
     */
    private static boolean isShippingConditioned(ShippingConditions shippingConditions, double param, double amount) {

        boolean isShippingConditioned = false;
        String shippingCondition = shippingConditions
                .getShippingConditions();//包邮条件（1：重量(也可以是件数)；2：金额；3：重量+金额(件数＋金额)）
        // 设置包邮条件（1：重量(也可以是件数)；2：金额；3：重量+金额(件数＋金额)）
        if ("1".equals(shippingCondition)) {
            if (shippingConditions.getConditionsWeight() <= param) {
                isShippingConditioned = true;
            }
        } else if ("2".equals(shippingCondition)) {
            if (shippingConditions.getConditionsFree() <= amount) {
                isShippingConditioned = true;
            }
        } else {
            if ((shippingConditions.getConditionsFree() <= amount)
                    && (shippingConditions
                    .getConditionsWeight() <= param)) {
                isShippingConditioned = true;
            }
        }
        return isShippingConditioned;
    }

    public static String addComma(String str) {
        // 将传进数字反转
        String reverseStr = "";
        String decimal = "";
        if (str.indexOf(".") > 0) {
            reverseStr = new StringBuilder(str.substring(0, str.indexOf(".")))
                    .reverse().toString();
            decimal = str.substring(str.indexOf(".") + 1);
        } else {
            reverseStr = new StringBuilder(str).reverse().toString();
        }
        String strTemp = "";
        for (int i = 0; i < reverseStr.length(); i++) {
            if (i * 3 + 3 > reverseStr.length()) {
                strTemp += reverseStr.substring(i * 3, reverseStr.length());
                break;
            }
            strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";
        }
        if (strTemp.endsWith(",")) {
            strTemp = strTemp.substring(0, strTemp.length() - 1);
        }

        // 将数字重新反转
        String resultStr = new StringBuilder(strTemp).reverse().toString();
        if (!decimal.equals("")) {
            resultStr = resultStr + "." + decimal;
        }
        return resultStr;
    }

    /**
     * 根据指定的位数 输出前几位,超出部分使用"..."代替
     *
     * @param overDigit   超出位数限制
     * @param unDealedStr 待处理的字符串
     * @return
     */
    public static String outputFormatStr(Integer overDigit, String unDealedStr) {
        //System.out.println(unDealedStr.length());
        String resultStr = "";
        if (unDealedStr.length() <= overDigit) {
            resultStr = unDealedStr;
            return resultStr;
        }
        resultStr = unDealedStr.substring(0, overDigit) + "...";
        return resultStr;
    }
}
