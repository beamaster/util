/*
 * Copyright 2012-2014 sencloud.com.cn. All rights reserved.
 * Support: http://www.sencloud.com.cn
 * License: http://www.sencloud.com.cn/license
 */
package com.steam.filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.steam.util.json.JsonUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * 分页信息
 *
 * @author Sencloud Team
 * @version 3.0
 */
public class Pageable implements Serializable {

    private static final long serialVersionUID = -3930180379790344299L;

    /**
     * 默认页码
     */
    private static final int DEFAULT_PAGE_NUMBER = 1;

    /**
     * 默认每页记录数
     */
    private static final int DEFAULT_PAGE_SIZE = 50;

    /**
     * 最大每页记录数
     */
    private static final int MAX_PAGE_SIZE = 1000;

    /**
     * 页码
     */
    private int pageNumber = DEFAULT_PAGE_NUMBER;

    /**
     * 每页记录数
     */
    private int pageSize = DEFAULT_PAGE_SIZE;

    /**
     * 总条数
     */
    private int totalSize;

    /**
     * 缓存每页的ID
     **/
    private Map<String, List<Integer>> pageIds;

    /**
     * 搜索属性
     */
    private String searchProperty;

    /**
     * 搜索值
     */
    private String searchValue;

    /**
     * 排序属性
     */
    private String orderProperty;

    /**
     * 排序方向
     */
    private Order.Direction orderDirection;

    /**
     * 筛选
     */
    private List<Filter> filters = new ArrayList<Filter>();

    /**
     * 排序
     */
    private List<Order> orders = new ArrayList<Order>();

    /**
     * 初始化一个新创建的Pageable对象
     */
    public Pageable() {
    }

    /**
     * 初始化一个新创建的Pageable对象
     *
     * @param pageNumber 页码
     * @param pageSize   每页记录数
     */
    public Pageable(Integer pageNumber, Integer pageSize) {
        if (pageNumber != null && pageNumber >= 1) {
            this.pageNumber = pageNumber;
        }
        if (pageSize != null && pageSize >= 1 && pageSize <= MAX_PAGE_SIZE) {
            this.pageSize = pageSize;
        }
    }

    /**
     * 获取页码
     *
     * @return 页码
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * 设置页码
     *
     * @param pageNumber 页码
     */
    public void setPageNumber(int pageNumber) {
        if (pageNumber < 1) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        this.pageNumber = pageNumber;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public Map<String, List<Integer>> getPageIds() {
        return pageIds;
    }

    public void setPageIds(Map<String, List<Integer>> pageIds) {
        this.pageIds = pageIds;
    }

    public String getPageIdsJson() {
        return JsonUtils.toJson(pageIds).replace("\"", "\'");
    }

    public void setPageIdsJson(String json) {
        if (json == null || json.isEmpty() || "null".equals(json)) {
            json = "{}";
        }
        json = json.replace("\'", "\"");
        Map<String, List<Integer>> pageIdsMap = JsonUtils.toObject(json, Map.class);
        this.pageIds = pageIdsMap;
    }

    public List<Integer> getPageIdsList() {
        List<Integer> list = new ArrayList<Integer>();
        if (pageIds != null) {
            for (Map.Entry<String, List<Integer>> map : pageIds.entrySet()) {
                list.addAll(map.getValue());
            }
        }
        return list;
    }

    /**
     * 获取每页记录数
     *
     * @return 每页记录数
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页记录数
     *
     * @param pageSize 每页记录数
     */
    public void setPageSize(int pageSize) {
        if (pageSize < 1 || pageSize > MAX_PAGE_SIZE) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.pageSize = pageSize;
    }

    /**
     * 设置每页记录数,忽略最大条数限制
     *
     * @param pageSize 每页记录数
     */
    public void setIngoreMaxPageSize(int pageSize) {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.pageSize = pageSize;
    }

    /**
     * 获取搜索属性
     *
     * @return 搜索属性
     */
    public String getSearchProperty() {
        return searchProperty;
    }

    /**
     * 设置搜索属性
     *
     * @param searchProperty 搜索属性
     */
    public void setSearchProperty(String searchProperty) {
        this.searchProperty = searchProperty;
    }

    /**
     * 获取搜索值
     *
     * @return 搜索值
     */
    public String getSearchValue() {
        return searchValue;
    }

    /**
     * 设置搜索值
     *
     * @param searchValue 搜索值
     */
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    /**
     * 获取排序属性
     *
     * @return 排序属性
     */
    public String getOrderProperty() {
        return orderProperty;
    }

    /**
     * 设置排序属性
     *
     * @param orderProperty 排序属性
     */
    public void setOrderProperty(String orderProperty) {
        this.orderProperty = orderProperty;
    }

    /**
     * 获取排序方向
     *
     * @return 排序方向
     */
    public Order.Direction getOrderDirection() {
        return orderDirection;
    }

    /**
     * 设置排序方向
     *
     * @param orderDirection 排序方向
     */
    public void setOrderDirection(Order.Direction orderDirection) {
        this.orderDirection = orderDirection;
    }

    /**
     * 获取筛选
     *
     * @return 筛选
     */
    public List<Filter> getFilters() {
        return filters;
    }

    /**
     * 设置筛选
     *
     * @param filters 筛选
     */
    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    /**
     * 获取排序
     *
     * @return 排序
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * 设置排序
     *
     * @param orders 排序
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Pageable other = (Pageable) obj;
        return new EqualsBuilder().append(getPageNumber(), other.getPageNumber()).append(getPageSize(),
                other.getPageSize()).append(getSearchProperty(), other.getSearchProperty()).append(getSearchValue(),
                other.getSearchValue()).append(getOrderProperty(), other.getOrderProperty()).append(
                getOrderDirection(), other.getOrderDirection()).append(getFilters(), other.getFilters()).append(
                getOrders(), other.getOrders()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getPageNumber()).append(getPageSize()).append(getSearchProperty()).append(
                getSearchValue()).append(getOrderProperty()).append(getOrderDirection()).append(getFilters()).append(
                getOrders()).toHashCode();
    }

}