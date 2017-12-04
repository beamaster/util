package com.steam.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.steam.interceptor.MemberInterceptor;

/**
 * Entity - 会员
 * 
 * @author ADMIN
 */
public class Member extends BaseEntity{
	
	private static final long serialVersionUID = -1437359595536038971L;
	
	/** "身份信息"参数名称 */
    public static final String PRINCIPAL_ATTRIBUTE_NAME = MemberInterceptor.class.getName() + ".PRINCIPAL";

    /** "用户名"Cookie名称 */
    public static final String USERNAME_COOKIE_NAME = "username";

    /** "用户名"Cookie名称 */
    public static final String NAME_COOKIE_NAME = "name";

    /** "用户email"Cookie名称 */
    public static final String EMAIL_COOKIE_NAME = "email";

    /** 会员注册项值属性个数 */
    public static final int ATTRIBUTE_VALUE_PROPERTY_COUNT = 10;

    /** 会员注册项值属性名称前缀 */
    public static final String ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX = "attributeValue";

    /** 最大收藏商品数 */
    public static final Integer MAX_FAVORITE_COUNT = 10;

    /** 地址 */
    private String address;

    /** 消费金额 */
    private BigDecimal amount;

    /** 微信或者第三方登陆标识 */
    private Long wxUser;

    private String attributeValue0;

    private String attributeValue1;

    private String attributeValue2;

    private String attributeValue3;

    private String attributeValue4;

    private String attributeValue5;

    private String attributeValue6;

    private String attributeValue7;

    private String attributeValue8;

    private String attributeValue9;

    /** 余额 */
    private BigDecimal balance;

    /** 出生日期 */
    private Date birth;

    /** E-mail */
    private String email;

    /** 性别 */
    private Integer gender;

    /** 是否启用 */
    private Boolean isEnabled;

    /** 是否锁定 */
    private Boolean isLocked;

    /** 锁定日期 */
    private Date lockedDate;

    /** 最后登录日期 */
    private Date loginDate;

    /** 连续登录失败次数 */
    private Integer loginFailureCount;

    /** 最后登录IP */
    private String loginIp;

    /** 手机 */
    private String mobile;

    /** 姓名 */
    private String name;

    /** 密码 */
    private String password;

    /** 会员号(绑定 ERP的VIP会员号) */
    private String vipCode;

    /** 会员VIP卡号 */
    private String vipNumber;

    /** 电话 */
    private String phone;

    /** 积分 */
    private Long point;

    /** 注册IP */
    private String registerIp;

    private Date safeKeyExpire;

    private String safeKeyValue;

    /** 用户名 */
    private String username;

    /** 邮编 */
    private String zipCode;

    /** 地区 */
    private Long area;

    /** 会员等级 */
    private Long memberRank;

    /** 找回密码时手机验证码 */
    private String validateCode;

    /** 会员VIP卡号 */
    private String cardNumber;

    /** 居住国家 */
    private String country;

    /** 主要语言选择 */
    private Long languagePreference;

    /** 身高 */
    private Double height;

    /** 体重 */
    private Double weight;

    /** 胸围 */
    private Double bust;

    /** 臀围 */
    private Double hip;

    /** 腰围 */
    private Double waist;

    /** 职业 */
    private Long occupation;

    /** 会员标签 */
    private String memberLabel;

    /** 会员类型 */
    private String type;

    /** 会员头像 文件*/
    private String portraitImage;

    /** 用户id*/
    private String userId;

    /** 新店促销折扣 */
    private Double discounts;

    /** 店铺*/
    private Long shopInfo;

    private Long members;
    
    /**分销商编号**/
    private String drpSn;
    
    /**会员等级**/
    private Integer memberLevel;
    
    /**会员成长值**/
    private Integer memberScore;
    
    public Integer getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(Integer memberLevel) {
		this.memberLevel = memberLevel;
	}

	public Integer getMemberScore() {
		return memberScore;
	}

	public void setMemberScore(Integer memberScore) {
		this.memberScore = memberScore;
	}

	public String getDrpSn() {
		return drpSn;
	}

	public void setDrpSn(String drpSn) {
		this.drpSn = drpSn;
	}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getWxUser() {
        return wxUser;
    }

    public void setWxUser(Long wxUser) {
        this.wxUser = wxUser;
    }

    public String getAttributeValue0() {
        return attributeValue0;
    }

    public void setAttributeValue0(String attributeValue0) {
        this.attributeValue0 = attributeValue0 == null ? null : attributeValue0.trim();
    }

    public String getAttributeValue1() {
        return attributeValue1;
    }

    public void setAttributeValue1(String attributeValue1) {
        this.attributeValue1 = attributeValue1 == null ? null : attributeValue1.trim();
    }

    public String getAttributeValue2() {
        return attributeValue2;
    }

    public void setAttributeValue2(String attributeValue2) {
        this.attributeValue2 = attributeValue2 == null ? null : attributeValue2.trim();
    }

    public String getAttributeValue3() {
        return attributeValue3;
    }

    public void setAttributeValue3(String attributeValue3) {
        this.attributeValue3 = attributeValue3 == null ? null : attributeValue3.trim();
    }

    public String getAttributeValue4() {
        return attributeValue4;
    }

    public void setAttributeValue4(String attributeValue4) {
        this.attributeValue4 = attributeValue4 == null ? null : attributeValue4.trim();
    }

    public String getAttributeValue5() {
        return attributeValue5;
    }

    public void setAttributeValue5(String attributeValue5) {
        this.attributeValue5 = attributeValue5 == null ? null : attributeValue5.trim();
    }

    public String getAttributeValue6() {
        return attributeValue6;
    }

    public void setAttributeValue6(String attributeValue6) {
        this.attributeValue6 = attributeValue6 == null ? null : attributeValue6.trim();
    }

    public String getAttributeValue7() {
        return attributeValue7;
    }

    public void setAttributeValue7(String attributeValue7) {
        this.attributeValue7 = attributeValue7 == null ? null : attributeValue7.trim();
    }

    public String getAttributeValue8() {
        return attributeValue8;
    }

    public void setAttributeValue8(String attributeValue8) {
        this.attributeValue8 = attributeValue8 == null ? null : attributeValue8.trim();
    }

    public String getAttributeValue9() {
        return attributeValue9;
    }

    public void setAttributeValue9(String attributeValue9) {
        this.attributeValue9 = attributeValue9 == null ? null : attributeValue9.trim();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Date getLockedDate() {
        return lockedDate;
    }

    public void setLockedDate(Date lockedDate) {
        this.lockedDate = lockedDate;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Integer getLoginFailureCount() {
        return loginFailureCount;
    }

    public void setLoginFailureCount(Integer loginFailureCount) {
        this.loginFailureCount = loginFailureCount;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getVipCode() {
        return vipCode;
    }

    public void setVipCode(String vipCode) {
        this.vipCode = vipCode == null ? null : vipCode.trim();
    }

    public String getVipNumber() {
        return vipNumber;
    }

    public void setVipNumber(String vipNumber) {
        this.vipNumber = vipNumber == null ? null : vipNumber.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp == null ? null : registerIp.trim();
    }

    public Date getSafeKeyExpire() {
        return safeKeyExpire;
    }

    public void setSafeKeyExpire(Date safeKeyExpire) {
        this.safeKeyExpire = safeKeyExpire;
    }

    public String getSafeKeyValue() {
        return safeKeyValue;
    }

    public void setSafeKeyValue(String safeKeyValue) {
        this.safeKeyValue = safeKeyValue == null ? null : safeKeyValue.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public Long getMemberRank() {
        return memberRank;
    }

    public void setMemberRank(Long memberRank) {
        this.memberRank = memberRank;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode == null ? null : validateCode.trim();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber == null ? null : cardNumber.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public Long getLanguagePreference() {
        return languagePreference;
    }

    public void setLanguagePreference(Long languagePreference) {
        this.languagePreference = languagePreference;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getBust() {
        return bust;
    }

    public void setBust(Double bust) {
        this.bust = bust;
    }

    public Double getHip() {
        return hip;
    }

    public void setHip(Double hip) {
        this.hip = hip;
    }

    public Double getWaist() {
        return waist;
    }

    public void setWaist(Double waist) {
        this.waist = waist;
    }

    public Long getOccupation() {
        return occupation;
    }

    public void setOccupation(Long occupation) {
        this.occupation = occupation;
    }

    public String getMemberLabel() {
        return memberLabel;
    }

    public void setMemberLabel(String memberLabel) {
        this.memberLabel = memberLabel == null ? null : memberLabel.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getPortraitImage() {
        return portraitImage;
    }

    public void setPortraitImage(String portraitImage) {
        this.portraitImage = portraitImage == null ? null : portraitImage.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Double getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Double discounts) {
        this.discounts = discounts;
    }

    public Long getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(Long shopInfo) {
        this.shopInfo = shopInfo;
    }

    public Long getMembers() {
        return members;
    }

    public void setMembers(Long members) {
        this.members = members;
    }
}