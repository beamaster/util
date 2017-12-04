/*
 * Copyright 2012-2014 sencloud.com.cn. All rights reserved.
 * Support: http://www.sencloud.com.cn
 * License: http://www.sencloud.com.cn/license
 */
package com.steam.interceptor;

import com.steam.entity.Member;
import com.steam.entity.Principal;
import com.steam.util.api.HttpRequestDeviceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;


/**
 * Interceptor - 会员权限
 *
 * @author Sencloud Team
 * @version 3.0
 */
public class MemberInterceptor extends HandlerInterceptorAdapter {

    /**
     * 重定向视图名称前缀
     */
    private static final String REDIRECT_VIEW_NAME_PREFIX = "redirect:";

    /**
     * "重定向URL"参数名称
     */
    private static final String REDIRECT_URL_PARAMETER_NAME = "redirectUrl";

    /**
     * "会员"属性名称
     */
    private static final String MEMBER_ATTRIBUTE_NAME = "member";

    /**
     * 默认登录URL
     */
    private static final String DEFAULT_LOGIN_URL = "/login/login.jhtml";

    private static final String DEFAULT_MOBILE_LOGIN_RUL = "/mobile/login/index.jhtml";

    /**
     * 登录URL
     */
    private String loginUrl = DEFAULT_LOGIN_URL;

    private static final Logger logger = LoggerFactory
            .getLogger(MemberInterceptor.class);

    @Value("${url_escaping_charset}")
    private String urlEscapingCharset;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        logger.error("已进入MemberInterceptor");
        HttpSession session = request.getSession();
        Principal principal = (Principal) session
                .getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
        if (HttpRequestDeviceUtils.isMobileDevice(request)) {
            loginUrl = DEFAULT_MOBILE_LOGIN_RUL;
        } else {
            // 2014年12月08日12:02:08，Bug fix
            // ，登陆网站后如果wap端出现先登陆，loginUrl变量会保持DEFAULT_MOBILE_LOGIN_RUL的值（内存一直是这个）
            loginUrl = DEFAULT_LOGIN_URL;
        }
        if (principal != null) {
            return true;
        } else {
            String requestType = request.getHeader("X-Requested-With");
            if (requestType != null
                    && requestType.equalsIgnoreCase("XMLHttpRequest")) {
                response.addHeader("loginStatus", "accessDenied");
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return false;
            } else {
                if (HttpRequestDeviceUtils.isMobileDevice(request)) {
                    loginUrl = DEFAULT_MOBILE_LOGIN_RUL;
                } else {
                    loginUrl = getRequestLoginUrl(request.getServletPath());
                }
                if (request.getMethod().equalsIgnoreCase("GET")) {
                    String redirectUrl = request.getQueryString() != null ? request
                            .getRequestURI() + "?" + request.getQueryString()
                            : request.getRequestURI();
                    response.sendRedirect(request.getContextPath()
                            + loginUrl
                            + "?"
                            + REDIRECT_URL_PARAMETER_NAME
                            + "="
                            + URLEncoder
                            .encode(redirectUrl, urlEscapingCharset));
                } else {
                    response.sendRedirect(request.getContextPath() + loginUrl);
                }

                return false;
            }
        }
    }

    /**
     * 获取登陆的url
     *
     * @param url
     * @return
     */
    public String getRequestLoginUrl(String url) {
        String loginUrl = DEFAULT_LOGIN_URL;
        if (url.equals("/cart/list.jhtml")
                || url.equals("/member/order/list.jhtml")
                || url.equals("/member/favorite/list.jhtml")
                || url.equals("/member/personalCenter/accountSafety/index.jhtml")
                || url.equals("/member/personalCenter/editLogistics.jhtml")
                || url.equals("/sh/goods/goodsList.jhtml")
                || url.equals("/sh/car/initReleaseList.jhtml")
                || url.equals("/sh/house/initReleaseHouse.jhtml")
                || url.equals("/sh/message/receivedMessage.jhtml")
                || url.equals("/sh/message/receivedMessage.jhtml")
                || url.equals("/sh/shop/order/list.jhtml")
                || url.equals("/sh/shop/order/sellerList.jhtml")
                || url.equals("/sh/goods/secondHandFavoriteList.jhtml")
                || url.equals("/sh/car/secondHandFavoriteList.jhtml")
                || url.equals("/sh/house/secondHandFavoriteList.jhtml")
                || url.equals("/sh/car/secondHandFavoriteList.jhtml")
                || url.equals("/member/receiver/list.jhtml")) {
            loginUrl = "/login/login.jhtml";
        }
        return loginUrl;
    }

    /**
     * 获取登录URL
     *
     * @return 登录URL
     */
    public String getLoginUrl() {
        return loginUrl;
    }

    /**
     * 设置登录URL
     *
     * @param loginUrl 登录URL
     */
    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

}