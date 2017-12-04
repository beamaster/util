package com.steam.util.api;

import com.sencloud.Setting;
import com.sencloud.entity.PluginConfig;
import com.sencloud.enums.WxShareEnum;
import com.steam.util.SpringUtils;
import com.steam.util.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: WxShareUtil
 * @Description: 微信分享通用模板
 * @author: yangyong
 * @date: 2016年10月14日 上午09:20:00
 */
public class WxShareUtil {

    private static final Logger logger = LoggerFactory
            .getLogger(WxShareUtil.class);

    /**
     * 返回一个微信工具类对象
     */
    public static WxShareUtil getInstance() {

        return new WxShareUtil();
    }

    /**
     * 微信分享通用方法
     *
     * @param request      当前调取微信工具的请求的request
     * @param model        当前调取微信工具的请求的model
     * @param pluginConfig 微信插件信息
     * @param shareEnum    微信分享类型
     * @param parameters   分享内容参数集
     */
    public void WxShare(HttpServletRequest request, ModelMap model, PluginConfig pluginConfig, WxShareEnum shareEnum, Map<String, String> parameters) {

        if (pluginConfig == null) {
            logger.error("获取微信插件失败" + DateUtil.getCurrentDate());
        }
        /**获取微信appid*/
        String appId = pluginConfig.getAttribute("appId");
        /**获取微信appSercet*/
        String appSecret = pluginConfig.getAttribute("appSecret");
        /**获取当前请求路径*/
        String url = request.getRequestURL().toString().replaceAll("123.58.5.243:19080", "www.lcuat.com");
        /**如果当前请求含有参数，获取当前参数内容*/
        if (request.getQueryString() != null) {
            url += "?" + request.getQueryString();
        }
        /**调取微信工具*/
        WeixinAPIHelper helper = WeixinAPIHelper.getInstance();
        /**微信工具返回前台微信接口所需参数集合*/
        Map<String, Object> map = helper.getWxApiMap(appId, appSecret, url);
        /**调取微信分享内容方法*/
        Map<String, String> contents = getShareContent(shareEnum, parameters);
        /**将微信分享所需要的所有参数塞入model*/
        model.addAttribute("appId", appId);
        model.addAttribute("timeStamp", map.get("timeStamp"));
        model.addAttribute("nonceStr", map.get("nonceStr"));
        model.addAttribute("signature", map.get("signature"));
        model.addAttribute("url", url);
        model.addAttribute("wxShareImg", contents.get("imgUrl"));
        model.addAttribute("wxShareTitle", contents.get("title"));
        model.addAttribute("wxShareDescribe", contents.get("describe"));

    }

    /**
     * 获取微信分享内容
     *
     * @param shareEnum  微信分享类型
     * @param parameters 分享内容参数集
     */
    public Map<String, String> getShareContent(WxShareEnum shareEnum, Map<String, String> parameters) {
        String imgUrl = "";
        String title = "";
        String describe = "";
        /**将分享信息封装到map*/
        Map<String, String> contents = new HashMap<String, String>();

        /**根据唯一微信分享类型做内容处理*/
        if (shareEnum.equals(WxShareEnum.mobileMallHomePage)) {  //微站首页
            imgUrl = getLogo(WxShareEnum.mobileMallHomePage);
            title = SpringUtils.getMessage("wx.mobile.share.main.title");
            describe = SpringUtils.getMessage("wx.mobile.share.main.describe");
        } else if (shareEnum.equals(WxShareEnum.mobileMallList)) {  //微站列表页
            imgUrl = getLogo(WxShareEnum.mobileMallList);
            if (parameters != null && !parameters.isEmpty() && parameters.get("tagName") != null) {
                title = SpringUtils.getMessage("wx.mobile.share.tag.list.title", parameters.get("tagName"));
                describe = SpringUtils.getMessage("wx.mobile.share.tag.list.describe");
            } else if (parameters != null && !parameters.isEmpty() && parameters.get("productCategoryName") != null) {
                title = SpringUtils.getMessage("wx.mobile.share.list.title", parameters.get("productCategoryName"));
                describe = SpringUtils.getMessage("wx.mobile.share.list.describe");
            } else if (parameters != null && !parameters.isEmpty() && parameters.get("searchValue") != null) {
                title = SpringUtils.getMessage("wx.mobile.share.search.title", parameters.get("searchValue"));
                describe = SpringUtils.getMessage("wx.mobile.share.search.describe");
            }
        } else if (shareEnum.equals(WxShareEnum.mobileMallProductDetail)) { //微站商品详情页
            if (parameters != null && !parameters.isEmpty()) {
                imgUrl = parameters.get("imgUrl");
                title = SpringUtils.getMessage("wx.mobile.share.product.content.title", parameters.get("productName"));
                describe = SpringUtils.getMessage("wx.mobile.share.product.content.describe");
            } else {
                logger.error("微信分享，微站商品详情页未获取商品信息" + DateUtil.getCurrentDate());
            }
        } else if (shareEnum.equals(WxShareEnum.mobileTravelHomePage)) {
            imgUrl = getLogo(WxShareEnum.mobileTravelHomePage);
            title = SpringUtils.getMessage("wx.mobile.share.travel.main.title");
            describe = SpringUtils.getMessage("wx.mobile.share.travel.main.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileTravelSearchPage)) {
            imgUrl = getLogo(WxShareEnum.mobileTravelSearchPage);
            title = SpringUtils.getMessage("wx.mobile.share.travel.search.title");
            describe = SpringUtils.getMessage("wx.mobile.share.travel.search.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileTravelInternationalSearchPage)) {
            imgUrl = getLogo(WxShareEnum.mobileTravelInternationalSearchPage);
            title = SpringUtils.getMessage("wx.mobile.share.travel.search.international.title");
            describe = SpringUtils.getMessage("wx.mobile.share.travel.search.international.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileSecondHomePage)) {
            imgUrl = getLogo(WxShareEnum.mobileSecondHomePage);
            title = SpringUtils.getMessage("wx.mobile.share.secondhand.main.title");
            describe = SpringUtils.getMessage("wx.mobile.share.secondhand.main.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileReleaseSecondHandGoodsPage)) {
            imgUrl = getLogo(WxShareEnum.mobileReleaseSecondHandGoodsPage);
            title = SpringUtils.getMessage("wx.mobile.share.secondhand.release.goods.title");
            describe = SpringUtils.getMessage("wx.mobile.share.secondhand.release.goods.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileReleaseSecondHandhousingPage)) {
            imgUrl = getLogo(WxShareEnum.mobileReleaseSecondHandhousingPage);
            title = SpringUtils.getMessage("wx.mobile.share.secondhand.release.house.title");
            describe = SpringUtils.getMessage("wx.mobile.share.secondhand.release.house.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileReleaseSecondHandCarPage)) {
            imgUrl = getLogo(WxShareEnum.mobileReleaseSecondHandCarPage);
            title = SpringUtils.getMessage("wx.mobile.share.secondhand.release.car.title");
            describe = SpringUtils.getMessage("wx.mobile.share.secondhand.release.car.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileSecondDealList)) {
            imgUrl = getLogo(WxShareEnum.mobileSecondDealList);
            title = SpringUtils.getMessage("wx.mobile.share.secondhand.deal.title");
            describe = SpringUtils.getMessage("wx.mobile.share.secondhand.deal.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileSecondHandGoodsList)) {
            imgUrl = getLogo(WxShareEnum.mobileSecondHandGoodsList);
            title = SpringUtils.getMessage("wx.mobile.share.secondhand.goods.list.title");
            describe = SpringUtils.getMessage("wx.mobile.share.secondhand.goods.list.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileSecondHandHousingList)) {
            imgUrl = getLogo(WxShareEnum.mobileSecondHandHousingList);
            title = SpringUtils.getMessage("wx.mobile.share.secondhand.house.list.title");
            describe = SpringUtils.getMessage("wx.mobile.share.secondhand.house.list.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileSecondHandCarList)) {
            imgUrl = getLogo(WxShareEnum.mobileSecondHandCarList);
            title = SpringUtils.getMessage("wx.mobile.share.secondhand.car.list.title");
            describe = SpringUtils.getMessage("wx.mobile.share.secondhand.car.list.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileSecondHandGoodsDetail)) {
            if (parameters != null && !parameters.isEmpty()) {
                imgUrl = parameters.get("imgUrl");
                title = SpringUtils.getMessage("wx.mobile.share.secondhand.goods.detail.title", parameters.get("productName"));
                describe = SpringUtils.getMessage("wx.mobile.share.secondhand.goods.detail.describ");
            } else {
                logger.error("微信分享，微站旧货堂二手物品详情页未获取商品信息" + DateUtil.getCurrentDate());
            }
        } else if (shareEnum.equals(WxShareEnum.mobileSecondHandHousingDetail)) {
            if (parameters != null && !parameters.isEmpty()) {
                imgUrl = parameters.get("imgUrl");
                title = SpringUtils.getMessage("wx.mobile.share.secondhand.house.detail.title", parameters.get("productName"));
                describe = SpringUtils.getMessage("wx.mobile.share.secondhand.house.detail.describ");
            } else {
                logger.error("微信分享，微站旧货堂二手房详情页未获取商品信息" + DateUtil.getCurrentDate());
            }
        } else if (shareEnum.equals(WxShareEnum.mobileSecondHandCarDetail)) {
            if (parameters != null && !parameters.isEmpty()) {
                imgUrl = parameters.get("imgUrl");
                title = SpringUtils.getMessage("wx.mobile.share.secondhand.car.detail.title", parameters.get("productName"));
                describe = SpringUtils.getMessage("wx.mobile.share.secondhand.car.detail.describ");
            } else {
                logger.error("微信分享，微站旧货堂二手车详情页未获取商品信息" + DateUtil.getCurrentDate());
            }
        } else if (shareEnum.equals(WxShareEnum.mobileSecondHandCarApplyPage)) {
            imgUrl = getLogo(WxShareEnum.mobileSecondHandCarApplyPage);
            title = SpringUtils.getMessage("wx.mobile.share.secondhand.car.apply.title");
            describe = SpringUtils.getMessage("wx.mobile.share.secondhand.car.apply.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileSecondHandCarSearchPage)) {
            imgUrl = getLogo(WxShareEnum.mobileSecondHandCarSearchPage);
            title = SpringUtils.getMessage("wx.mobile.share.secondhand.car.search.title");
            describe = SpringUtils.getMessage("wx.mobile.share.secondhand.car.search.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileSecondSellCarPage)) {
            imgUrl = getLogo(WxShareEnum.mobileSecondSellCarPage);
            title = SpringUtils.getMessage("wx.mobile.share.second.sellcar.apply.title");
            describe = SpringUtils.getMessage("wx.mobile.share.second.sellcar.apply.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileSecondSellCarSearchPage)) {
            imgUrl = getLogo(WxShareEnum.mobileSecondSellCarSearchPage);
            title = SpringUtils.getMessage("wx.mobile.share.second.sellcar.search.title");
            describe = SpringUtils.getMessage("wx.mobile.share.second.sellcar.search.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileAdmissionHomePage)) {
            imgUrl = getLogo(WxShareEnum.mobileAdmissionHomePage);
            title = SpringUtils.getMessage("wx.mobile.share.admission.main.title");
            describe = SpringUtils.getMessage("wx.mobile.share.admission.main.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileTravelSearchList)) {
            if (parameters != null && !parameters.isEmpty()) {
                imgUrl = getLogo(WxShareEnum.mobileTravelSearchList);
                title = SpringUtils.getMessage("wx.mobile.share.travel.search.list.title", parameters.get("fromCity"), parameters.get("toCity"));
                describe = SpringUtils.getMessage("wx.mobile.share.travel.search.list.describ");
            } else {
                logger.error("微信分享，微站机票查询列表页未获取机票信息" + DateUtil.getCurrentDate());
            }
        } else if (shareEnum.equals(WxShareEnum.mobileRecycleIndex)) {
            imgUrl = getLogo(WxShareEnum.mobileRecycleIndex);
            title = SpringUtils.getMessage("wx.mobile.share.yrlife.recycle.index.title");
            describe = SpringUtils.getMessage("wx.mobile.share.yrlife.recycle.index.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileRecycleProductList)) {
            imgUrl = getLogo(WxShareEnum.mobileRecycleProductList);
            title = SpringUtils.getMessage("wx.mobile.share.yrlife.recycle.list.title");
            describe = SpringUtils.getMessage("wx.mobile.share.yrlife.recycle.list.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileRecycleSearchList)) {
            if (parameters != null && !parameters.isEmpty()) {
                imgUrl = getLogo(WxShareEnum.mobileRecycleSearchList);
                title = SpringUtils.getMessage("wx.mobile.share.yrlife.recycle.search.title", parameters.get("searchValue"));
                describe = SpringUtils.getMessage("wx.mobile.share.yrlife.recycle.search.describ");
            }
        } else if (shareEnum.equals(WxShareEnum.mobileTrainSearchPage)) {
            imgUrl = getLogo(WxShareEnum.mobileTrainSearchPage);
            title = SpringUtils.getMessage("wx.mobile.share.yrlife.train.search.title");
            describe = SpringUtils.getMessage("wx.mobile.share.yrlife.train.search.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileTrainSearchList)) {
            if (parameters != null && !parameters.isEmpty()) {
                imgUrl = getLogo(WxShareEnum.mobileTrainSearchList);
                title = SpringUtils.getMessage("wx.mobile.share.yrlife.train.search.list.title", parameters.get("fromCity"), parameters.get("toCity"));
                describe = SpringUtils.getMessage("wx.mobile.share.yrlife.train.search.list.describ");
            }
        } else if (shareEnum.equals(WxShareEnum.mobileHeiLongJiangIndex)) {
            imgUrl = getLogo(WxShareEnum.mobileHeiLongJiangIndex);
            title = SpringUtils.getMessage("wx.mobile.share.heilongjiang.prefecture.title");
            describe = SpringUtils.getMessage("wx.mobile.share.heilongjiang.prefecture.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileImportedIndex)) {
            imgUrl = getLogo(WxShareEnum.mobileImportedIndex);
            title = SpringUtils.getMessage("wx.mobile.share.imported.prefecture.title");
            describe = SpringUtils.getMessage("wx.mobile.share.imported.prefecture.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileRechargeIndex)) {
            imgUrl = getLogo(WxShareEnum.mobileRechargeIndex);
            title = SpringUtils.getMessage("wx.mobile.share.recharge.title");
            describe = SpringUtils.getMessage("wx.mobile.share.recharge.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileFruitGame)) {
            imgUrl = getLogo(WxShareEnum.mobileFruitGame);
            title = SpringUtils.getMessage("wx.mobile.share.yrlife.game.title");
            describe = SpringUtils.getMessage("wx.mobile.share.yrlife.game.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileDrpProductDetail)) {
            imgUrl = parameters.get("imgUrl");
            title = SpringUtils.getMessage("wx.mobile.drp.product.detail.title", parameters.get("productName"));
            describe = SpringUtils.getMessage("wx.mobile.drp.product.detail.describ");
        } else if (shareEnum.equals(WxShareEnum.wxShareAcitivity)) {
            imgUrl = getLogo(WxShareEnum.wxShareAcitivity);
            title = SpringUtils.getMessage("wx.share.order.activity.title");
            describe = SpringUtils.getMessage("wx.share.order.activity.describ");
        } else if (shareEnum.equals(WxShareEnum.mobileAcitivityPage)) {
            imgUrl = getLogo(WxShareEnum.mobileAcitivityPage);
            title = SpringUtils.getMessage("wx.mobile.activity.page.title", parameters.get("wxShareTitle"));
            describe = SpringUtils.getMessage("wx.mobile.activity.page.desc", parameters.get("wxShareDesc"));
        } else if (shareEnum.equals(WxShareEnum.mobileOrderProductDetail)) {
            if (parameters != null && !parameters.isEmpty()) {
                imgUrl = parameters.get("imgUrl");
                if (new BigDecimal(parameters.get("wxShareProductDis")).compareTo(new BigDecimal(0)) == 1) {
                    title = SpringUtils.getMessage("wx.mobile.share.order.product.hasDis.title", parameters.get("wxShareProductPrice"), parameters.get("wxShareProductDis"));
                } else {
                    title = SpringUtils.getMessage("wx.mobile.share.order.product.title", parameters.get("wxShareProductPrice"));
                }
                describe = SpringUtils.getMessage("wx.mobile.share.order.product.describ");
            } else {
                logger.error("微信分享，微站商品详情页未获取商品信息" + DateUtil.getCurrentDate());
            }
        } else if (shareEnum.equals(WxShareEnum.mobileMembercardPage)) {
            imgUrl = getLogo(WxShareEnum.mobileMembercardPage);
            title = SpringUtils.getMessage("wx.mobile.share.membercard.page.title");
            describe = SpringUtils.getMessage("wx.mobile.share.membercard.page.desc");
        } else if (shareEnum.equals(WxShareEnum.answerPresent)) {
            imgUrl = getLogo(WxShareEnum.answerPresent);
            title = SpringUtils.getMessage("wx.mobile.share.answerpresent.page.title");
            describe = SpringUtils.getMessage("wx.mobile.share.answerpresent.page.desc");
        } else if (shareEnum.equals(WxShareEnum.mobileNovActivityShare)) {
            imgUrl = getLogo(WxShareEnum.mobileNovActivityShare);
            title = SpringUtils.getMessage("wx.mobile.share.novactivity.title");
            describe = SpringUtils.getMessage("wx.mobile.share.novactivity.desc");
        }
        contents.put("imgUrl", imgUrl);
        contents.put("title", title);
        contents.put("describe", describe);
        return contents;
    }

    /**
     * 获取微信分享logo
     *
     * @param shareEnum 微信分享类型
     */
    public String getLogo(WxShareEnum wxShareEnum) {
        String logo = "";
        /**获取项目名称*/
        Setting setting = SettingUtils.get();
        String siteUrl = setting.getSiteUrl();
        /**云融生活logo*/
        if (wxShareEnum.equals(WxShareEnum.mobileMallHomePage) || wxShareEnum.equals(WxShareEnum.mobileMallList)
                || wxShareEnum.equals(WxShareEnum.mobileSecondHandCarApplyPage) || wxShareEnum.equals(WxShareEnum.mobileSecondHandCarSearchPage)
                || wxShareEnum.equals(WxShareEnum.mobileSecondSellCarPage) || wxShareEnum.equals(WxShareEnum.mobileSecondSellCarSearchPage)) {
            logo = new StringBuffer()
                    .append(siteUrl)
                    .append(SpringUtils.getMessage("wx.mobile.share.yrlife.logo")).toString();
        } else if (wxShareEnum.equals(WxShareEnum.mobileTravelHomePage)) {
            /**云融生活旅游logo*/
            logo = new StringBuffer()
                    .append(siteUrl)
                    .append(SpringUtils.getMessage("wx.mobile.share.yrlife.travel.logo")).toString();
        } else if (wxShareEnum.equals(WxShareEnum.mobileAdmissionHomePage)) {
            /**云融生活迪士尼logo*/
            logo = new StringBuffer()
                    .append(siteUrl)
                    .append(SpringUtils.getMessage("wx.mobile.share.yrlife.admission.logo")).toString();
        } else if (wxShareEnum.equals(WxShareEnum.mobileSecondHandCarList) || wxShareEnum.equals(WxShareEnum.mobileReleaseSecondHandCarPage)) {
            /**云融生活二手车logo*/
            logo = new StringBuffer()
                    .append(siteUrl)
                    .append(SpringUtils.getMessage("wx.mobile.share.yrlife.car.logo")).toString();
        } else if (wxShareEnum.equals(WxShareEnum.mobileSecondHandHousingList) || wxShareEnum.equals(WxShareEnum.mobileReleaseSecondHandhousingPage)) {
            /**云融生活二手房logo*/
            logo = new StringBuffer()
                    .append(siteUrl)
                    .append(SpringUtils.getMessage("wx.mobile.share.yrlife.house.logo")).toString();
        } else if (wxShareEnum.equals(WxShareEnum.mobileSecondHomePage) || wxShareEnum.equals(WxShareEnum.mobileReleaseSecondHandGoodsPage)
                || wxShareEnum.equals(WxShareEnum.mobileSecondDealList) || wxShareEnum.equals(WxShareEnum.mobileSecondHandGoodsList)) {
            /**云融生活二手物品logo*/
            logo = new StringBuffer()
                    .append(siteUrl)
                    .append(SpringUtils.getMessage("wx.mobile.share.yrlife.second.logo")).toString();

        } else if (wxShareEnum.equals(WxShareEnum.mobileTravelSearchPage) || wxShareEnum.equals(WxShareEnum.mobileTravelInternationalSearchPage)
                || wxShareEnum.equals(WxShareEnum.mobileTravelSearchList)) {
            /**云融生活机票logo*/
            logo = new StringBuffer()
                    .append(siteUrl)
                    .append(SpringUtils.getMessage("wx.mobile.share.yrlife.ticket.logo")).toString();
        } else if (wxShareEnum.equals(WxShareEnum.mobileRecycleIndex) || wxShareEnum.equals(WxShareEnum.mobileRecycleProductList)
                || wxShareEnum.equals(WxShareEnum.mobileRecycleSearchList)) {
            /**云融生活回收购logo*/
            logo = new StringBuffer()
                    .append(siteUrl)
                    .append(SpringUtils.getMessage("wx.mobile.share.yrlife.recycle.logo")).toString();
        } else if (wxShareEnum.equals(WxShareEnum.mobileTrainSearchPage) || wxShareEnum.equals(WxShareEnum.mobileTrainSearchList)) {
            /**云融生活火车票logo*/
            logo = new StringBuffer()
                    .append(siteUrl)
                    .append(SpringUtils.getMessage("wx.mobile.share.yrlife.train.logo")).toString();
        } else if (wxShareEnum.equals(WxShareEnum.mobileHeiLongJiangIndex)) {
            /**云融生活微站黑龙江专区logo*/
            logo = new StringBuffer()
                    .append(siteUrl)
                    .append(SpringUtils.getMessage("wx.mobile.share.heilongjiang.prefecture.logo")).toString();
        } else if (wxShareEnum.equals(WxShareEnum.mobileImportedIndex)) {
            /**云融生活微站精品专区logo*/
            logo = new StringBuffer()
                    .append(siteUrl)
                    .append(SpringUtils.getMessage("wx.mobile.share.imported.prefecture.logo")).toString();
        } else if (wxShareEnum.equals(WxShareEnum.mobileRechargeIndex)) {
            /**微站充值缴费页logo*/
            logo = new StringBuffer()
                    .append(siteUrl)
                    .append(SpringUtils.getMessage("wx.mobile.share.yrlife.recharge.logo")).toString();
        } else if (wxShareEnum.equals(WxShareEnum.mobileFruitGame)) {
            /**微站游戏页logo*/
            logo = new StringBuffer()
                    .append(siteUrl)
                    .append(SpringUtils.getMessage("wx.mobile.share.yrlife.game.logo")).toString();
        } else if (wxShareEnum.equals(WxShareEnum.wxShareAcitivity)) {
            logo = new StringBuffer()
                    .append(siteUrl)
                    .append(SpringUtils.getMessage("wx.share.order.activity.image.url")).toString();
        } else if (wxShareEnum.equals(WxShareEnum.mobileAcitivityPage)) {
            logo = new StringBuffer()
                    .append(siteUrl)
                    .append(SpringUtils.getMessage("wx.mobile.share.activity.page.logo")).toString();
        } else if (wxShareEnum.equals(WxShareEnum.mobileMembercardPage)) {
            logo = new StringBuffer()
                    .append(siteUrl)
                    .append(SpringUtils.getMessage("wx.mobile.share.membercard.page.logo")).toString();
        } else if (wxShareEnum.equals(WxShareEnum.answerPresent)) {
            logo = new StringBuffer()
                    .append(siteUrl)
                    .append(SpringUtils.getMessage("wx.mobile.share.answerpresent.page.logo")).toString();
        } else if (wxShareEnum.equals(WxShareEnum.mobileNovActivityShare)) {
            logo = new StringBuffer()
                    .append(siteUrl)
                    .append(SpringUtils.getMessage("wx.mobile.share.novactivity.logo")).toString();
        }
        return logo;
    }

    /**
     * 静态页面分享（静态页面有所区别 无法通过后台controller直接带参返回前台 通过ajax获取）
     **/
    public Map<String, Object> staticWxShare(PluginConfig pluginConfig, WxShareEnum shareEnum, Map<String, String> parameters, String url) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (pluginConfig == null) {
            logger.error("获取微信插件失败" + DateUtil.getCurrentDate());
        }
        /**获取微信appid*/
        String appId = pluginConfig.getAttribute("appId");
        /**获取微信appSercet*/
        String appSecret = pluginConfig.getAttribute("appSecret");
        /**调取微信工具*/
        WeixinAPIHelper helper = WeixinAPIHelper.getInstance();
        /**微信工具返回前台微信接口所需参数集合*/
        Map<String, Object> map = helper.getWxApiMap(appId, appSecret, url);
        /**调取微信分享内容方法*/
        Map<String, String> contents = getShareContent(shareEnum, parameters);
        /**将微信分享所需要的所有参数塞入model*/
        result.put("appId", appId);
        result.put("timeStamp", map.get("timeStamp"));
        result.put("nonceStr", map.get("nonceStr"));
        result.put("signature", map.get("signature"));
        result.put("url", url);
        result.put("wxShareImg", contents.get("imgUrl"));
        result.put("wxShareTitle", contents.get("title"));
        result.put("wxShareDescribe", contents.get("describe"));
        return result;
    }
}
