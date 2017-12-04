package com.steam.util.math;

/**
 * Created by steam on 2017/8/28.
 * 来源：http://blog.csdn.net/wyc_cs/article/details/51742754
 * 一、将ip地址转成long数值
 * <p>
 * 将IP地址转化成整数的方法如下：
 * <p>
 * 1、通过String的split方法按.分隔得到4个长度的数组
 * <p>
 * 2、通过左移位操作（<<）给每一段的数字加权，第一段的权为2的24次方，第二段的权为2的16次方，第三段的权为2的8次方，最后一段的权为1
 * <p>
 * 二、将数值转换为ip地址
 * <p>
 * 将十进制整数形式转换成127.0.0.1形式的ip地址
 * <p>
 * 将整数形式的IP地址转化成字符串的方法如下：
 * <p>
 * 1、将整数值进行右移位操作（>>>），右移24位，右移时高位补0，得到的数字即为第一段IP。
 * <p>
 * 2、通过与操作符（&）将整数值的高8位设为0，再右移16位，得到的数字即为第二段IP。
 * <p>
 * 3、通过与操作符吧整数值的高16位设为0，再右移8位，得到的数字即为第三段IP。
 * <p>
 * 4、通过与操作符吧整数值的高24位设为0，得到的数字即为第四段IP。
 */
public class IPUtil {

    public static Long ip2int(String ip) {
        Long num = 0L;
        if (ip == null) {
            return num;
        }

        try {
            ip = ip.replaceAll("[^0-9\\.]", ""); //去除字符串前的空字符
            String[] ips = ip.split("\\.");
            if (ips.length == 4) {
                num = Long.parseLong(ips[0], 10) * 256L * 256L * 256L + Long.parseLong(ips[1], 10) * 256L * 256L + Long.parseLong(ips[2], 10) * 256L + Long.parseLong(ips[3], 10);
                num = num >>> 0;
            }
            System.out.println("StringUtil.ip2int" + num);
        } catch (NullPointerException ex) {
            System.out.println(ip);
        }

        return num;
    }


    /**
     * ip地址转成long型数字
     * 将IP地址转化成整数的方法如下：
     * 1、通过String的split方法按.分隔得到4个长度的数组
     * 2、通过左移位操作（<<）给每一段的数字加权，第一段的权为2的24次方，第二段的权为2的16次方，第三段的权为2的8次方，最后一段的权为1
     *
     * @param strIp
     * @return
     */
    public static long ipToLong(String strIp) {
        String[] ip = strIp.split("\\.");
        return (Long.parseLong(ip[0]) << 24) + (Long.parseLong(ip[1]) << 16) + (Long.parseLong(ip[2]) << 8) + Long.parseLong(ip[3]);
    }

    /**
     * 将十进制整数形式转换成127.0.0.1形式的ip地址
     * 将整数形式的IP地址转化成字符串的方法如下：
     * 1、将整数值进行右移位操作（>>>），右移24位，右移时高位补0，得到的数字即为第一段IP。
     * 2、通过与操作符（&）将整数值的高8位设为0，再右移16位，得到的数字即为第二段IP。
     * 3、通过与操作符吧整数值的高16位设为0，再右移8位，得到的数字即为第三段IP。
     * 4、通过与操作符吧整数值的高24位设为0，得到的数字即为第四段IP。
     *
     * @param longIp
     * @return
     */
    public static String longToIP(long longIp) {
        StringBuffer sb = new StringBuffer("");
        // 直接右移24位
        sb.append(String.valueOf((longIp >>> 24)));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
        sb.append(".");
        // 将高16位置0，然后右移8位
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
        sb.append(".");
        // 将高24位置0
        sb.append(String.valueOf((longIp & 0x000000FF)));
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(ipToLong("219.239.110.138"));
        System.out.println(longToIP(18537472));
    }

}
