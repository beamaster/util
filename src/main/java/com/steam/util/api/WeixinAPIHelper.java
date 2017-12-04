package com.steam.util.api;

import com.sencloud.util.encrypt.EncryptionUtil;
import com.steam.util.des.MD5;
import com.steam.util.string.LifeUtil;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.nutz.json.Json;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.*;


/**
 * 最好把这个类写成单纯调用微信公众平台接口的服务类，对外统一返回Map形式的数据包，数据处理可以放在其他服务层或者控制层
 * 数据处理时需要注意接口返回错误的情况，可以考虑在这个服务类中加入接口异常编码与提示信息的映射
 *
 * @author
 */
public class WeixinAPIHelper {
    private String path = this.getClass().getResource("/").getPath();
    /**
     * 获取微信接口授权access_token
     */
    private String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";

    private String getJsApiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={0}&type=jsapi";
    /**
     * 通过用户open_id和接口授权access_token拉取用户信息
     */
    private String getUserInfoUrl_intfAuth = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={0}&openid={1}";
    /**
     * 微信OAuth2.0授权接口：通过用户授权code获取用户授权access_token和用户open_id
     */
    private String getOAuthTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";
    /**
     * 微信OAuth2.0授权接口：通过用户open_id和用户授权access_token拉取用户信息
     */
    private String getUserInfoUrl_userAuth = "https://api.weixin.qq.com/sns/userinfo?access_token={0}&openid={1}&lang=zh_CN";
    /**
     * 微信长链接转短链接接口
     */
    private String getLongtoshort_url = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token={0}";
    /**
     * 微信OAuth2.0授权接口：需要用户授权的标识
     */
    public String WEIXIN_USER_AUTH = "snsapi_userinfo";

    private String UPLOAD_MATERIAL = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=";
    private String SEND_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=";
    private String USER_AGENT_H = "User-Agent";
    private String REFERER_H = "Referer";
    private String USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 Safari/537.22";
    private HttpClient webClient;
    private Logger log = Logger.getLogger(WeixinAPIHelper.class);

    private boolean isLogin = false;
    private String cookies = "";
    String token = "";
    private static WeixinAPIHelper instance;

    /**
     * @desc 初始化创建 WebClient
     */
    private WeixinAPIHelper() {
        webClient = new HttpClient();
    }

    public static WeixinAPIHelper getInstance() {
        if (instance == null) {
            synchronized (WeixinAPIHelper.class) {
                if (instance == null) {
                    instance = new WeixinAPIHelper();
                }
            }
        }
        return instance;
    }

    public String createNonceStr() {
        return LifeUtil.getNonceStr();
    }

    public long getTimestamp() {
        Calendar c = Calendar.getInstance();
        long now = c.getTimeInMillis() / 1000;
        return now;
    }
//	public String getSignature(String nonceStr,long timestamp,String url){
//		String access_token = getAccessToken();
//		String jsapi_ticket = getJsApiTicket(access_token);
//		// 这里参数的顺序要按照 key 值 ASCII 码升序排序
//		String $string = "jsapi_ticket={0}&noncestr={1}&timestamp={2}&url={3}";
//		String str = MessageFormat.format($string,jsapi_ticket,nonceStr,timestamp,url);
//		return EncryptionUtil.sha1(str);
//	}

    /**
     * 获取微信接口授权access_token
     *
     * @param appid
     * @param appSecret
     * @return
     */
    @SuppressWarnings("unchecked")
    public synchronized String getAccessToken(String appId, String appSecret) {
        String access_token = "";
        long expire_time = 0L;
        Calendar c = Calendar.getInstance();
        long now = c.getTimeInMillis() / 1000;
        try {
            File f = new File(path + File.separator + "access_token.json");
            boolean legalToken = true;
            if (f.exists()) {
                Map m = Json.fromJsonFile(Map.class, f);
                if (Long.parseLong(m.get("expire_time").toString()) < now) {// 已过期
                    legalToken = false;
                } else {
                    access_token = (String) m.get("access_token");
                }
            } else {
                legalToken = false;
            }
            if (!legalToken) {
                String reqUrl = MessageFormat.format(getAccessTokenUrl, appId,
                        appSecret);
                String response = executeHttpGet(reqUrl);// {"access_token":"ACCESS_TOKEN","expires_in":7200}
                Map resultMap = Json.fromJson(Map.class, response);
                access_token = (String) resultMap.get("access_token");
                if (access_token != null) {
                    expire_time = now + 7000;
                    resultMap.put("expire_time", expire_time);
                    Json.toJsonFile(f, resultMap);
                }
            }
        } catch (Exception e) {
            log.error("get access toekn exception", e);
        }
        return access_token;
    }

    /**
     * 卡券 api_ticket 是用于调用卡券相关接口的临时票据 获取微信接口授权jsapi_ticket
     *
     * @param access_token
     * @return
     */
    @SuppressWarnings("unchecked")
    public synchronized String getJsApiTicket(String access_token) {
        String ticket = "";
        long expire_time = 0L;
        Calendar c = Calendar.getInstance();
        long now = c.getTimeInMillis() / 1000;
        try {
            File f = new File(path + File.separator + "jsapi_ticket.json");
            boolean legalToken = true;
            if (f.exists()) {
                Map m = Json.fromJsonFile(Map.class, f);
                if (Long.parseLong(m.get("expire_time").toString()) < now) {// 已过期
                    legalToken = false;
                } else {
                    ticket = (String) m.get("ticket");
                }
            } else {
                legalToken = false;
            }
            if (!legalToken) {
                String reqUrl = MessageFormat.format(getJsApiTicketUrl,
                        access_token);
                String response = executeHttpGet(reqUrl);// {"errcode":0,"errmsg":"ok","ticket":"ticket","expires_in":7200}
                Map resultMap = Json.fromJson(Map.class, response);
                ticket = (String) resultMap.get("ticket");
                if (ticket != null) {
                    expire_time = now + 7000;
                    resultMap.put("expire_time", expire_time);
                    Json.toJsonFile(f, resultMap);
                }
            }
        } catch (Exception e) {
            log.error("get access toekn exception", e);
        }
        return ticket;
    }

    /**
     * 通过用户open_id和接口授权access_token拉取用户信息
     *
     * @param token
     * @param openid
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map getUserInfo(String ACCESS_TOKEN, String openId) {
        Map resultMap = new HashMap();
        try {
            String reqUrl = MessageFormat.format(getUserInfoUrl_intfAuth,
                    ACCESS_TOKEN, openId);
            String response = executeHttpGet(reqUrl);
            resultMap = Json.fromJson(Map.class, response);
        } catch (Exception e) {
            log.error("获取用户信息异常", e);
        }
        return resultMap;
    }

    /**
     * 微信OAuth2.0授权接口：通过用户授权code获取用户的授权access_token和open_id
     *
     * @param appId
     * @param appSecret
     * @param code
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map getOAuthToken(String appId, String appSecret, String code) {
        Map resultMap = new HashMap();
        try {
            String reqUrl = MessageFormat.format(getOAuthTokenUrl, appId,
                    appSecret, code);
            String response = executeHttpGet(reqUrl);
            log.info("reqUrl" + reqUrl);
            log.info("response" + response);
            resultMap = Json.fromJson(Map.class, response);
        } catch (Exception e) {
            log.error("获取用户授权token异常", e);
        }
        return resultMap;
    }

    /**
     * 微信OAuth2.0授权接口：通过用户open_id和用户授权access_token拉取用户信息
     *
     * @param accessToken
     * @param openId
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map getLongtoshort_url(String access_token, String long_url) {
        Map map_media_id = new HashMap();
        try {
            String reqUrl = MessageFormat.format(getLongtoshort_url,
                    access_token);
            Map<String, Object> msd = new HashMap<String, Object>();
            msd.put("access_token", access_token);
            msd.put("action", "long2short");
            msd.put("long_url", long_url);
            String resultMap = Json.toJson(msd);
            String responseContent = post(reqUrl, resultMap);
            map_media_id = Json.fromJson(Map.class, responseContent);
        } catch (Exception e) {
            log.error("授权获取用户信息异常", e);
        }
        return map_media_id;
    }

    /**
     * 微信OAuth2.0授权接口：通过用户open_id和用户授权access_token拉取用户信息
     *
     * @param accessToken
     * @param openId
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map getUserInfo_userAuth(String access_token, String openId) {
        Map resultMap = new HashMap();
        try {
            String reqUrl = MessageFormat.format(getUserInfoUrl_userAuth,
                    access_token, openId);
            String response = executeHttpGet(reqUrl);
            resultMap = Json.fromJson(Map.class, response);
        } catch (Exception e) {
            log.error("授权获取用户信息异常", e);
        }
        return resultMap;
    }

    /**
     * @param url
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     * @desc 发起HTTP GET请求返回数据
     */
    private String executeHttpGet(String url) throws IOException {
        GetMethod getMethod = new GetMethod(url);
        getMethod.getParams().setContentCharset("UTF-8");
        webClient.executeMethod(getMethod);
        String response = getMethod.getResponseBodyAsString();

        return response;
    }

    /**
     * 模拟网页登陆
     *
     * @param name
     * @param pwd
     * @return
     */
    private boolean login(String name, String pwd) {
        try {
            PostMethod postMethod = new PostMethod(
                    "https://mp.weixin.qq.com/cgi-bin/login");
            postMethod.addRequestHeader("Referer",
                    "  https://mp.weixin.qq.com/");
            postMethod.addParameter("username", name);
            postMethod.addParameter("pwd", MD5.crypt(pwd));
            postMethod.addParameter("imgcode", "");
            postMethod.addParameter("f", "json");

            int status = webClient.executeMethod(postMethod);
            if (status == HttpStatus.SC_OK) {
                String response = postMethod.getResponseBodyAsString();
                Map _map = Json.fromJson(Map.class, response);
                String redirectUrl = String.valueOf(_map.get("redirect_url"));
                token = redirectUrl
                        .substring(redirectUrl.indexOf("token=") + 6);
                StringBuffer cookie = new StringBuffer();
                for (Cookie c : webClient.getState().getCookies()) {
                    cookie.append(c.getName()).append("=").append(c.getValue())
                            .append(";");
                }
                this.cookies = cookie.toString();
                this.isLogin = true;
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("登录异常", e);
            return false;
        }
    }

    public static List friendsList;

    /**
     * 根据用户名称 获取Fakeid<br>
     * 在推送消息时需要用到
     *
     * @param nickName
     * @return
     * @throws Exception
     */
    private String getFakeid(String nickName) throws Exception {
        if (null == friendsList) {
            getFriendsList();
        }

        for (Object obj : friendsList) {
            Map friend = (Map) obj;
            if (friend.get("nick_name").equals(nickName)) {
                return String.valueOf(friend.get("id"));
            }
        }
        return null;
    }

    /**
     * 获取粉丝列表
     *
     * @throws Exception
     */
    private void getFriendsList() throws Exception {
        GetMethod getMethod = new GetMethod(
                "https://mp.weixin.qq.com/cgi-bin/contactmanage?t=user/index&pagesize=-1&pageidx=0&type=0&token="
                        + token + "&lang=zh_CN");
        webClient.executeMethod(getMethod);
        String friendsListHtml = getMethod.getResponseBodyAsString();

        String friendsListJson = friendsListHtml.substring(
                friendsListHtml.indexOf("friendsList : ({\"contacts\":") + 27,
                friendsListHtml.indexOf("}).contacts"));
        friendsList = Json.fromJson(List.class, friendsListJson);
    }

    /**
     * 发送消息
     *
     * @throws Exception
     * @throws IllegalArgumentException
     */
    public boolean sendMsg(String msg, String nickName) {
        try {

            if (!isLogin) {
                login("kefu@taizer.cn", "qazwsx123");
            }

            String fakeid = getFakeid(nickName);

            PostMethod messagePostMethod = new PostMethod(
                    "https://mp.weixin.qq.com/cgi-bin/singlesend");
            messagePostMethod
                    .addRequestHeader("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:31.0) Gecko/20100101 Firefox/31.0");
            messagePostMethod.addRequestHeader("Referer",
                    "https://mp.weixin.qq.com");
            messagePostMethod.addRequestHeader("Cookie", cookies);
            messagePostMethod.addRequestHeader("Content-Type",
                    "application/x-www-form-urlencoded; charset=UTF-8");

            messagePostMethod.addParameter("t", "ajax-response");
            messagePostMethod.addParameter("ajax", "1");
            messagePostMethod.addParameter("content", msg);

            messagePostMethod.addParameter("f", "json");
            messagePostMethod.addParameter("imgcode", "");
            messagePostMethod.addParameter("lang", "zh_CN");
            messagePostMethod.addParameter("type", "1");
            messagePostMethod.addParameter("tofakeid", fakeid);
            messagePostMethod.addParameter("token", token);
            messagePostMethod.addParameter("random",
                    String.valueOf(new Random().nextDouble()));

            int status = webClient.executeMethod(messagePostMethod);
            if (status == HttpStatus.SC_OK) {
                String response = messagePostMethod.getResponseBodyAsString();
                Map _map = Json.fromJson(Map.class, response);
                Map baseResp = (Map) _map.get("base_resp");
                if (String.valueOf(baseResp.get("ret")).equals("0")) {
                    log.debug("发送成功");
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("发送失败", e);
            return false;
        }
    }

    private String createGroupText(String array) {
        Map gjson = new HashMap<String, String>();
        gjson.put("touser", array);
        gjson.put("msgtype", "text");
        Map text = new HashMap<String, String>();
        text.put("content", "hello from boxer.");
        gjson.put("text", text);
        return gjson.toString();
    }

    // 获取openid
    public String getOpenids() {
        String array = null;
        String urlstr = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
        urlstr = urlstr.replace("ACCESS_TOKEN", "123");
        urlstr = urlstr.replace("NEXT_OPENID", "");
        URL url;
        try {
            url = new URL(urlstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoInput(true);
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] buf = new byte[size];
            is.read(buf);
            String resp = new String(buf, "UTF-8");
            Object jsonObject = Json.fromJson(resp);
            // array
            // =(String)JackJson.fromJsonToObjectWithDate("data",jsonObject,"openid");
            // array =JackJson.fromJsonToObjectWithDate(array, Object,
            // "openid");
            return array;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return array;
        } catch (IOException e) {
            e.printStackTrace();
            return array;
        }
    }

    /*
     * type 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb） media
     * form-data中媒体文件标识，有filename、filelength、content-type等信息
     * 图片（image）:128K，支持JPG格式 语音（voice）：256K，播放长度不超过60s，支持AMR\MP3格式
     * 视频（video）：1MB，支持MP4格式 缩略图（thumb）：64KB，支持JPG格式 视频文件不支持下载
     */
    public String upLoadImg(String url, String filePath) throws IOException {
        String result = null;
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在");
        }

        /**
         * 第一部分
         */
        URL urlObj = new URL(url);
        // 连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

        /**
         * 设置关键值
         */
        con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存

        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");

        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary="
                + BOUNDARY);

        // 请求正文信息

        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // 必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\""
                + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);

        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();

        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线

        out.write(foot);

        out.flush();
        out.close();

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                // System.out.println(line);
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("数据读取异常");
        } finally {
            if (reader != null) {
                reader.close();
            }

        }
        Map map = Json.fromJson(Map.class, result);

        String mediaId = (String) map.get("media_id");
        return mediaId;
    }

    public void updateImg(Map map) {

        try {

            if (this.isLogin) {
                // form.setToken(token);
                // map.put("token", token);
                DefaultHttpClient httpClient = new DefaultHttpClient(); // 创建默认的httpClient实例
                X509TrustManager xtm = new X509TrustManager() { // 创建TrustManager
                    public void checkClientTrusted(X509Certificate[] chain,
                                                   String authType) throws CertificateException {
                    }

                    public void checkServerTrusted(X509Certificate[] chain,
                                                   String authType) throws CertificateException {
                    }

                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                };
                SSLContext ctx = SSLContext.getInstance("TLS");

                // 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
                ctx.init(null, new TrustManager[]{xtm}, null);

                // 创建SSLSocketFactory
                SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);

                // 通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
                httpClient.getConnectionManager().getSchemeRegistry()
                        .register(new Scheme("https", 443, socketFactory));
                HttpPost post = new HttpPost(UPLOAD_MATERIAL + map.get("token"));
                post.setHeader(USER_AGENT_H, USER_AGENT);
                post.setHeader(REFERER_H, UPLOAD_MATERIAL + map.get("token"));
                post.setHeader("Cookie", cookies);
                post.setHeader("Accept",
                        "application/json, text/javascript, */*; q=0.01");
                post.setHeader("Accept-Encoding", "gzip, deflate");
                post.setHeader("Accept-Language",
                        "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
                post.setHeader("Cache-Control", "no-cache");
                post.setHeader("Connection", "keep-alive");
                // post.setHeader("Content-Length", "130");
                post.setHeader("Content-Type",
                        "application/x-www-form-urlencoded; charset=UTF-8");
                post.setHeader("Host", "api.weixin.qq.com");
                post.setHeader("Pragma", "no-cache");
                post.setHeader("X-Requested-With", "XMLHttpRequest");

                /**
                 * private String cgi = "uploadmaterial"; private String type =
                 * "2"; private String token = ""; private String t =
                 * "iframe-uploadfile"; private String lang = "zh_CN"; private
                 * String formId = "1";
                 */

                // FilePart file = new FilePart("uploadfile",
                // form.getUploadfile(), "image/jpeg", "UTF-8");
                List articles = new ArrayList();
                List articles1 = new ArrayList();
                Map<String, Object> paramMap = new HashMap<String, Object>();
                Map<String, ArrayList> acList = new HashMap<String, ArrayList>();

                paramMap.put("thumb_media_id", map.get("thumb_media_id"));
                paramMap.put("author", map.get("author"));
                paramMap.put("title", map.get("title"));
                paramMap.put("content_source_url",
                        map.get("content_source_url"));
                paramMap.put("content", map.get("content"));
                paramMap.put("digest", map.get("digest"));
                articles.add(paramMap);
                acList.put("articles", (ArrayList) articles);
                articles1.add(acList);
                String respMessage = Json.toJson(acList);
                // post.setEntity(new UrlEncodedFormEntity(articles1, "UTF-8"));
                // post(UPLOAD_MATERIAL+map.get("token"),respMessage);
                // //post.setEntity(new StringEntity(respMessage, "UTF-8"));
                // HttpResponse response = httpClient.execute(post); //执行POST请求
                // //HttpEntity entity = response.getEntity(); //获取响应实体
                // HttpEntity entity = response.getEntity(); //获取响应实体
                // long responseLength = 0; //响应长度
                String responseContent = post(
                        UPLOAD_MATERIAL + map.get("token"), respMessage);
                Map map_media_id = Json.fromJson(Map.class,
                        responseContent);

                String mediaId = (String) map_media_id.get("media_id");
                if (mediaId != null) {
                    List smsd = new ArrayList();
                    Map<String, String> filter = new HashMap<String, String>();
                    Map<String, String> mpnews = new HashMap<String, String>();
                    Map<String, Object> msd = new HashMap<String, Object>();
                    filter.put("group_id", "0");
                    mpnews.put("media_id", mediaId);
                    msd.put("filter", filter);
                    msd.put("mpnews", mpnews);
                    msd.put("msgtype", "mpnews");
                    String Message = Json.toJson(msd);
                    // post(UPLOAD_MATERIAL+map.get("token"),Message);
                    String Content = post(SEND_MESSAGE_URL + map.get("token"),
                            Message);
                }
                // if (null != entity) {
                // responseLength = entity.getContentLength();
                // responseContent = EntityUtils.toString(entity, "UTF-8");
                // EntityUtils.consume(entity); //Consume response content
                // }
                // System.out.println("请求地址: " + post.getURI());
                // System.out.println("响应状态: " + response.getStatusLine());
                // System.out.println("响应长度: " + responseLength);
                // System.out.println("响应内容: " + responseContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // post请求工具
    public static String post(String strURL, String params) {
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(params);
            out.flush();
            out.close();
            // 读取响应
            int length = (int) connection.getContentLength();// 获取长度
            InputStream is = connection.getInputStream();
            if (length != -1) {
                byte[] data = new byte[length];
                byte[] temp = new byte[512];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = is.read(temp)) > 0) {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                String result = new String(data, "UTF-8"); // utf-8编码
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error"; // 自定义错误信息
    }

    public Map<String, Object> getWxApiMap(String appId, String appSecret, String url) {
        long timeStamp = getTimestamp();
        String nonceStr = createNonceStr();
        String access_token = getAccessToken(appId, appSecret);
        String jsapi_ticket = getJsApiTicket(access_token);
        String $string = "jsapi_ticket={0}&noncestr={1}&timestamp={2}&url={3}";
        String str = MessageFormat.format($string, jsapi_ticket, nonceStr, timeStamp + "", url);
        String signature = EncryptionUtil.sha1(str);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("timeStamp", timeStamp);
        map.put("nonceStr", nonceStr);
        map.put("signature", signature);
        return map;
    }

}
