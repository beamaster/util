package com.steam.util.api;

import com.steam.common.CommonAttributes;
import com.steam.entity.Member;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ExcelUtil {
    SXSSFWorkbook wb = null;
    String fileName = null;
    Sheet sh = null;

    public SXSSFWorkbook getWb() {
        return wb;
    }

    public void setWb(SXSSFWorkbook wb) {
        this.wb = wb;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Sheet getSh() {
        return sh;
    }

    public void setSh(Sheet sh) {
        this.sh = sh;
    }

    public int getRownum() {
        return rownum;
    }

    public void setRownum(int rownum) {
        this.rownum = rownum;
    }

    public int getCellnum() {
        return cellnum;
    }

    public void setCellnum(int cellnum) {
        this.cellnum = cellnum;
    }

    int rownum = 0;
    int cellnum = 0;
    private final static int MAXLINE = 65536;

    private static String upperCaseFirstChar(String str) {
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
        return str;
    }

    public void createExcel(String str) {
        wb = new SXSSFWorkbook(1000);
        sh = wb.createSheet();
        fileName = str;
    }

    //设置行标签
    public void setTitle(List<String> titles) {
        cellnum = 0;
        Row rowTitle = sh.createRow(rownum++);
        for (String colName : titles) {
            Cell cell = rowTitle.createCell(cellnum++);
//            CellStyle cellStyle = wb.createCellStyle();
//            //cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
//            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
//            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//            Font font = wb.createFont();
//            font.setFontHeightInPoints((short) 11);
//            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
//            cellStyle.setFont(font);
//            cell.setCellStyle(cellStyle);
            cell.setCellValue(colName);
        }
    }

    //设置行标签
    public int setRow(List<String> titles) {
        cellnum = 0;
        if (rownum > MAXLINE) {
            return -1;
        }
        Row newRow = sh.createRow(rownum++);
        for (String colContent : titles) {
            Cell cell = newRow.createCell(cellnum++);
//            CellStyle cellStyle = wb.createCellStyle();
//            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
//            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
//            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//            Font font = wb.createFont();
//            font.setFontHeightInPoints((short) 11);
//            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
//            cellStyle.setFont(font);
//            cell.setCellStyle(cellStyle);
            cell.setCellValue(colContent);
        }
        return 0;
    }

    /**
     * 获取保存临时文件路径
     *
     * @return
     */
    public static String getXlsxPath() {
        try {
            String path = PropertiesLoaderUtils.loadProperties(new ClassPathResource(CommonAttributes.LENDINGCLOUDLIFE_PROPERTIES_PATH)).getProperty("xlsxTemporaryFilePath");
            if (!path.substring(path.length() - 1, path.length()).equals("/")) {
                return path += "/";
            }
            return path;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public HttpServletResponse transPort(HttpServletResponse response) throws IOException {
        String path = getXlsxPath();

        File file = new File(path + fileName);
        file.createNewFile();
        FileOutputStream out = new FileOutputStream(file);
        wb.write(out);
        out.close();

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("gbk"), "iso-8859-1") + "\"");
        response.addHeader("Content-Length", "" + file.length());
        response.setContentType("application/octet-stream;charset=UTF-8");
        InputStream in = null;
        OutputStream toClient = null;
        try {
            //以流的形式下载文件
            in = new BufferedInputStream(new FileInputStream(path + fileName));
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            in.close();

            toClient = new BufferedOutputStream(response.getOutputStream());
            toClient.write(buffer);
            toClient.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            toClient.close();
        }
        return response;
    }

    /**
     * 导出xls文件，考虑到一般只是导出某种对象信息，所以使用泛型去解析数据，只用传入需要导出的字段及表头信息，以及数据，
     * 所以这样可以完全地专注于业务， 同时有可能对于某些字段可能要设置一些默认值
     * ，这样当数据为null时，就可以设置，还有一个问题就是有些字段可能需要进行一些转换，比如性别用0和1表示，这样导出的时候就可以显示为男和女。
     *
     * @param output        导出的数据流，有可能是文件流，可能是网络数据流
     * @param workSheetName 工作表名称，涉及到一个表只能有65536行，256列，所以，当数据量较大时会直接写入下一个表。列超出则直接抛出异常。
     * @param titles        表头信息
     * @param property      对象属性，对对象的哪些属性进行导出
     * @param data          数据集合
     * @param clazz         进行类型转换的类，需要注意的是方法必须使用get+属性名（首字母大写）。参数为属性类型。如username为String类型
     *                      ，则方法为public String getUsername(String
     *                      username),返回值原则上可以为任何类型，返回以后直接进行String
     *                      .valuOf()操作。如果使用转换类，则所有字段必须全部包含，不需要转换的字段直接返回。
     *                      考虑到一般情况下可能输出字段较多，进行转换的字段不是很多，所以提供该类的生成工具类
     *                      {@link com.gsww.jup.util.BornParser},详情及使用请参看
     *                      {@link com.gsww.jup.util.BornParser}
     */
    public void export(OutputStream output, String workSheetName, String[] titles, String[] property, List<?> data,
                       Class<?> clazz) {

        // 声明一个转换对象
        Object parser = null;
        // 如果传入的转换类型为null，则说明不用转换的，所有字段直接输出就可以了。
        if (clazz != null) {
            try {
                // 实例化转换对象
                parser = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // 每个sheet中的总显示数（最大行数）
        int sheetMaxRowCount = 65534;
        // 每个sheet的列数
        int columNumber = titles.length;
        try {
            // 创建工作薄
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFCellStyle setBorder = wb.createCellStyle();
            // sheet索引
            int sheetNum = 1;
            // 行
            int rowNum = 0;
            // 声明工作表
            HSSFSheet sheet = null;
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            for (Object bean : data) {
                // 此处加1.00，用于强制转换类型留下一行用于表头信息，然后肯定是要向上进位了
                Double sheetIndex = Math.ceil((rowNum + 1.00) / sheetMaxRowCount);
                // 返回的Double，当然要取int类型了
                sheetNum = sheetIndex.intValue();
                HSSFRow rowData = null;// 表内容k
                // 如果行数刚好是最大行数的整数倍，就应该加一个工作表了
                if (rowNum == sheetMaxRowCount * (sheetNum - 1)) {
                    // sheet表名依次为表0、表1、表2...
                    sheet = wb.createSheet(workSheetName + sheetNum);
                    // 创建行
                    rowData = sheet.createRow(0);
                    // 在表头每列 放值
                    for (int columnIndex = 0; columnIndex < columNumber; columnIndex++) {
                        // 按照表头信息生成每一个表头单元格信息
                        HSSFCell headerCell = rowData.createCell(columnIndex);
                        // 设置每一列的宽度
                        sheet.setColumnWidth(columnIndex, 6000);
                        // 居中
                        setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                        // 设置单元格值
                        headerCell.setCellValue(titles[columnIndex]);
                    }
                }
                // 创建行
                rowData = sheet.createRow(rowNum - sheetMaxRowCount * (sheetNum - 1) + 1);
                for (int columnIndex = 0; columnIndex < columNumber; columnIndex++) {
                    // 按照表头信息生成每一个表头单元格信息
                    HSSFCell cellData = rowData.createCell(columnIndex);
                    // 设置每一列的宽度
                    sheet.setColumnWidth(columnIndex, 6000);
                    // 居中
                    setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);

                    String fieldName = property[columnIndex];

                    Method method = clazz.getMethod("get" + ExcelUtil.upperCaseFirstChar(fieldName), new Class[]{});
                    Object value = method.invoke(bean, new Object[]{});
                    String cellValue = "";
                    if (value == null) {
                        // parser.getClass();
                    } else if (value instanceof Member) {
                        cellValue = ((Member) value).getUsername();
                    } else if (value instanceof Date) {
                        Date date = (Date) value;
                        cellValue = sd.format(date);
                    } else {
                        cellValue = value.toString();
                    }
                    cellData.setCellValue(cellValue);
                }
                // 行数当然要加1了。
                rowNum++;
            }
            wb.write(output);
            output.flush();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
