package com.steam.entity;

import java.math.BigDecimal;

/**
 * Entity - 会员等级
 * 
 * @author ADMIN
 */
public class MemberRank extends BaseEntity{

	private static final long serialVersionUID = 1L;

	/** 累计消费金额 */
	private BigDecimal amount;

	/** 是否默认 */
    private Boolean isDefault;

    /** 是否特殊 */
    private Boolean isSpecial;

    /** 名称 */
    private String name;

    /** 优惠比例 */
    private Double scale;

    /** 一次性消费金额 */
    private BigDecimal disposableAmount;

    /** 折上折优惠折扣比例 */
    private Double foldScale;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Boolean getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Boolean isSpecial) {
        this.isSpecial = isSpecial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Double getScale() {
        return scale;
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }

    public BigDecimal getDisposableAmount() {
        return disposableAmount;
    }

    public void setDisposableAmount(BigDecimal disposableAmount) {
        this.disposableAmount = disposableAmount;
    }

    public Double getFoldScale() {
        return foldScale;
    }

    public void setFoldScale(Double foldScale) {
        this.foldScale = foldScale;
    }

}