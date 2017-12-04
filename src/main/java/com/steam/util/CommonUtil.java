package com.steam.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.allinpay.ets.client.StringUtil;
import com.module.util.PropertiesUtil;
import com.sencloud2.util.APIUtils;

/**
 * 通用工具类
 * 
 * @author guoling
 * @date 2015-6-30
 */
public class CommonUtil {

	/**
	 * 发送https请求
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpsRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);

			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
		} catch (Exception e) {
		}
		return jsonObject;
	}

	/**
	 * URL编码（utf-8）
	 * 
	 * @param source
	 * @return
	 */
	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String getIpAddr(HttpServletRequest request) {

        String ipAddress = request.getHeader("x-forwarded-for");

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknow".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();

            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡获取本机配置的IP地址
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inetAddress.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实的IP地址，多个IP按照','分割
        if (null != ipAddress && ipAddress.length() > 15) {
            //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }
	
	public static String getCityNameByIP(String ip){
		String cityName = "";
        //新浪IP接口
        String interfaceIp = PropertiesUtil.get("taobao_api_url")+ip;
        //调用接口获取json字符串
        String jsonStr = APIUtils.loadJSON(interfaceIp);
        com.alibaba.fastjson.JSONObject jsonObject = null;
        if(jsonStr != null && !"".equals(jsonStr)){
        	jsonObject = JSON.parseObject(jsonStr);
        	cityName = JSON.parseObject(jsonObject.get("data").toString()).get("city").toString();
        }
		return cityName;
	}
	
	public static String getCityNameByLatLon(Double longitude,Double latitude) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL getUrl = new URL("http://api.map.baidu.com/geocoder/v2/?location="+ latitude +","+longitude+"&output=json&ak=" + PropertiesUtil.get("baidu_api_ak"));
            connection= (HttpURLConnection) getUrl.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            reader= new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line =reader.readLine()) !=null) {
                builder.append(line);
            }
            JSONArray newArray = JSONArray.fromObject("["+builder.toString()+"]");
            JSONObject obj = (JSONObject)newArray.get(0);
            JSONObject result = (JSONObject)obj.get("result");
            JSONObject addressComponent =(JSONObject)result.get("addressComponent");
            String cityName =(String)addressComponent.get("city");
            return cityName;
        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            try {
                reader.close();
            }catch(IOException e) {
                e.printStackTrace();
            }finally{
                connection.disconnect();
            }
        }
        return null;
    }	
	
	 /** 
     * 判断sourceStr中包含targetStr的个数 
      * @param sourceStr 
     * @param targetStr 
     * @return counter 
     */  
    public static int countStr(String sourceStr, String targetStr) {  
    	int counter = 0;
    	if(StringUtil.isEmpty(sourceStr) || StringUtil.isEmpty(targetStr)){
    		return 0;
    	}
        int sourceLength = sourceStr.length();
        int targetLength = targetStr.length();
        String temp = sourceStr.replaceAll(targetStr, "");
        counter = (sourceLength-temp.length())/targetLength;
        return counter;
    }  
    
    public static String getAgentType(HttpServletRequest request){
//    	Enumeration  typestr = request.getHeaderNames(); 
		String agentType = request.getHeader("user-agent");
		if(agentType.contains("Android") ||agentType.contains("iPad") || agentType.contains("iPhone")) {
			return "mobile";
		}
		if(agentType.contains("Windows") || agentType.contains("Mac")){
			return "pc";
		}
		return "pc";
    }
    
    public static void main(String[] args){
    	System.out.print(countStr(",15,21539,2415,","15"));
    }
}