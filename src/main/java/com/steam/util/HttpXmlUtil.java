package com.steam.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpXmlUtil {
    final static Logger log = Logger.getLogger(HttpXmlUtil.class);

    public static boolean existsUrl(String URLName) {
        boolean connect = false;
        HttpURLConnection uConnection = null;
        try {
            URL u = new URL(URLName);
            try {
                uConnection = (HttpURLConnection) u.openConnection();
                try {
                    uConnection.connect();
                    int status = uConnection.getResponseCode();
                    System.out.println(status);
                    if (status >= 200 && status < 300) {
                        connect = true;
                        log.error("connect cnzz success");
                    }
                } catch (Exception e) {
                    connect = false;
                    e.printStackTrace();
                    System.out.println("connect failed");
                }
            } catch (IOException e) {
                System.out.println("build failed");
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            System.out.println("build url failed");
            e.printStackTrace();
        } finally {
            if (uConnection != null)
                uConnection.disconnect();
        }
        return connect;
    }

    public static boolean _existsUrl(String URLName) {
        GetMethod getMethod = new GetMethod(URLName);
        HttpClient client = new HttpClient();
        boolean connect = false;
        try {
            client.executeMethod(getMethod);
            int status = getMethod.getStatusCode();
            System.out.println(status);
            if (status >= 200 && status < 300) {
                connect = true;
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connect;
    }
}
