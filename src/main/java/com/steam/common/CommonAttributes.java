/*
 * Copyright 2012-2014 sencloud.com.cn. All rights reserved.
 * Support: http://www.sencloud.com.cn
 * License: http://www.sencloud.com.cn/license
 */
package com.steam.common;
/**
 * Copyright (C), 2015-2017, XXX有限公司
 * FileName: C
 * Author:   steam
 * Date:     2017/12/5 9:20
 * Description:
 */
public final class CommonAttributes
{

    /** 日期格式配比 */
    public static final String[] DATE_PATTERNS = new String[] {"yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd",
            "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss"};

    /** LendingCloudLife.xml文件路径 */
    public static final String LENDINGCLOUDLIFE_XML_PATH = "/LendingCloudLife.xml";

    /** LendingCloudLife.properties文件路径 */
    public static final String LENDINGCLOUDLIFE_PROPERTIES_PATH = "/LendingCloudLife.properties";

    /**
     * 不可实例化
     */
    private CommonAttributes()
    {
    }

}
