package com.steam.util;

import com.sencloud2.service.CityPriceRefService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class IndexUtil {

    public static void shopIndex(HttpServletRequest request, HttpServletResponse response) {
        CityPriceRefService cityPriceRefService = (CityPriceRefService) SpringUtils.getBean("cityPriceRefServiceImpl", CityPriceRefService.class);
        String provinceCity = cityPriceRefService.getRegionInfoByAreaId(request, response);
        String path = "/shop/" + provinceCity + "/index.html";
        String staticPath = request.getRealPath(path);
        File staticFile = new File(staticPath);
        try {
            if (staticFile.exists()) {
                System.out.println("static page exist");
                render(request, response, staticFile);
                return;
            } else {
                System.out.println("static page is not exist ,file length:" + staticFile.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出html到response里
     *
     * @param request
     * @param response
     * @param staticFile
     * @throws IOException
     */
    public static void render(HttpServletRequest request, HttpServletResponse response, File staticFile) throws FileNotFoundException, IOException {
        System.out.println("render html,file length:" + staticFile.length());
        OutputStream output = response.getOutputStream();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {//bug7077 找不到文件或目录
            inputStream = new FileInputStream(staticFile);
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
                buffer.append("\r\n");//必须换行，否则js事件无法执行
            }
            output.write(buffer.toString().getBytes());
        } catch (FileNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new IOException();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

}
