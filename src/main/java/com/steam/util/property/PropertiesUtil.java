/**
 * Copyright © 2016 南京云融金融信息服务有限公司. All rights reserved.
 *
 * @Title: MyPropertiesLoaderUtils.java
 * @Prject: yrlife_mall2
 * @Package: com.module.util
 * @Description: TODO
 * @author: weifengtao
 * @date: 2016年10月28日 上午11:27:04
 * @version: V1.0
 */
package com.steam.util.property;

import com.sencloud.CommonAttributes;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @ClassName: MyPropertiesLoaderUtils
 * @Description: 获取属性文件配置信息
 * @author: weifengtao
 * @date: 2016年10月28日 上午11:27:04  
 */
public class PropertiesUtil {

    private static Properties properties;
    public static Map<String, ResourceBundle> bundles = new HashMap<String, ResourceBundle>();

    static {
        try {
            properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(CommonAttributes.LENDINGCLOUDLIFE_PROPERTIES_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ResourceBundle load(String name) {
        ResourceBundle res = bundles.get(name);
        try {
            if (res == null) {
                res = ResourceBundle.getBundle(name);
                if (res == null) {
                    res = ResourceBundle.getBundle(name, null, PropertiesUtil.class.getClassLoader());
                }
                if (null != res) {
                    bundles.put(name, res);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public PropertiesUtil() {

    }

    public static String get(String key) {
        return get(null, key);
    }

    /**
     * @Title: get
     * @Description: 获取属性配置信息
     * @param path   属性所在文件
     * @param key    属性key
     * @return
     * @return: String
     */
    public static String get(String path, String key) {
        if (null == path) {
            return properties.getProperty(key);
        } else {
            try {
                return PropertiesLoaderUtils.loadProperties(new ClassPathResource(path)).getProperty(key);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static String get2(String baseName, String key) {
        try {
            return load(baseName).getString(key);
        } catch (Throwable e) {
            return null;
        }
    }

    public static String get2(String baseName, String key, String defaultValue) {
        try {
            return load(baseName).getString(key);
        } catch (Throwable e) {
            return defaultValue;
        }
    }
}
