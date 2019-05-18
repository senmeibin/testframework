package com.bpms.core.excel;

import com.bpms.core.AppContext;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.utils.*;
import com.bpms.sys.dao.SensitiveDataExportLogDao;
import com.bpms.sys.entity.ext.SensitiveDataExportLogExt;
import com.bpms.sys.service.ParameterService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.*;
import org.apache.poi.ss.formula.ptg.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.*;
import org.apache.shiro.SecurityUtils;
import org.dozer.util.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 用于按指定格式导入导出excel
 */
public class ExcelUtils {
    protected static final Logger log = LoggerFactory.getLogger(LoggerFactory.class);
    private static Map<String, Map<Field, ExcelColumn>> result = new LinkedHashMap<>();

    /**
     * 取得Excel导出模板文件
     *
     * @param path Excel模板文件路径
     * @return XSSFWorkbook
     */
    public static XSSFWorkbook getExcelTemplate(String path) {
        //模板文件
        path = AppContext.getRealPath() + path;
        try {
            InputStream in = new FileInputStream(new File(path));
            return new XSSFWorkbook(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Excel文件导出
     *
     * @param template ExcelExportTemplate
     * @param <T>      实体对象
     * @return 字节流
     * @throws IOException
     */
    public static <T> byte[] exportFile(ExcelExportTemplate<T> template) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        workbook.createSheet();
        if (StringUtils.isEmpty(template.getSheetName())) {
            template.setSheetName("一览数据");
        }

        return exportFile(template, workbook, 0);
    }

    /**
     * Excel文件导出
     *
     * @param template ExcelExportTemplate
     * @param <T>      实体对象
     * @return 字节流
     * @throws IOException
     */
    public static <T> byte[] exportFile(ExcelExportTemplate<T> template, XSSFWorkbook workbook, int startRow) throws IOException {
        XSSFSheet sheet = workbook.getSheetAt(0);

        //隐藏网格线
        sheet.setDisplayGridlines(false);

        if (StringUtils.isEmpty(template.getSheetName())) {
            template.setSheetName("一览数据");
        }
        //设置工作表名称
        workbook.setSheetName(0, template.getSheetName());

        //需要导出的数据列集合
        List<ExcelColInfo> colList = template.getColList();

        //内容列表
        List<T> contents = template.getContents();

        //列循环
        long startTime = System.currentTimeMillis();

        //标准导出的场合、创建表头
        if (startRow == 0) {
            //创建标题行
            createHeaderRow(workbook, sheet, colList);
        }

        //创建内容行样式
        createContentsRowStyle(workbook, sheet, colList, contents, startRow);

        //创建内容行数据
        createContentsRowData(sheet, colList, contents, startRow);

        long endTime = System.currentTimeMillis();
        log.info(String.format("导出用时totalTime = %s 毫秒。", endTime - startTime));
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        byte[] result = os.toByteArray();
        IOUtils.closeQuietly(os);
        return result;
    }

    /**
     * 创建标题行
     *
     * @param workbook    工作薄
     * @param sheet       工作表
     * @param colInfoList 列信息列表
     */
    private static void createHeaderRow(XSSFWorkbook workbook, Sheet sheet, List<ExcelColInfo> colInfoList) {
        int colSize = colInfoList.size();
        for (int colIndex = 0; colIndex < colInfoList.size(); colIndex++) {
            ExcelColInfo colInfo = colInfoList.get(colIndex);
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            //绿色背景
            cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

            //实线上边框
            cellStyle.setBorderTop(BorderFormatting.BORDER_THIN);

            //虚线下边框
            cellStyle.setBorderBottom(BorderFormatting.BORDER_DOTTED);

            //第一列实线左边框
            cellStyle.setBorderLeft(colIndex == 0 ? BorderFormatting.BORDER_THIN : BorderFormatting.BORDER_DOTTED);

            //最后一列实线右边框
            cellStyle.setBorderRight((colIndex == colSize - 1) ? BorderFormatting.BORDER_THIN : BorderFormatting.BORDER_DOTTED);

            //设置标题行的字体

            Font font = workbook.createFont();
            //加粗
            //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            //白色
            font.setColor(HSSFColor.WHITE.index);
            font.setFontName("宋体");
            cellStyle.setFont(font);

            //上下居中
            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

            //固定像素宽度的场合
            if (StringUtils.endsWithIgnoreCase(colInfo.getWidth(), "px") || StringUtils.isNumeric(colInfo.getWidth())) {
                int width = Integer.parseInt(StringUtils.remove(colInfo.getWidth(), "px")) / 5 * 250;
                sheet.setColumnWidth(colIndex, width);
            }
            //百分比宽度的场合
            else if (StringUtils.endsWithIgnoreCase(colInfo.getWidth(), "%")) {
                sheet.setColumnWidth(colIndex, Integer.parseInt(StringUtils.remove(colInfo.getWidth(), "%")) * 500);
            }

            Row row = sheet.getRow(0) != null ? sheet.getRow(0) : sheet.createRow(0);

            //设置行高
            row.setHeight((short) (20 * 20));

            Cell cell = row.createCell(colIndex);
            cell.setCellStyle(cellStyle);

            //设置标题内容
            cell.setCellValue(colInfo.getTitle());
        }
    }

    /**
     * 创建内容行的样式
     *
     * @param workbook    工作薄
     * @param sheet       工作表
     * @param colInfoList 列信息列表
     * @param contents    列表内容
     * @param <T>         实体对象
     * @param startRow    正文开始行号
     */
    private static <T> void createContentsRowStyle(XSSFWorkbook workbook, XSSFSheet sheet, List<ExcelColInfo> colInfoList, List<T> contents, int startRow) {
        int colSize = colInfoList.size();
        for (int i = startRow; i < contents.size() + startRow; i++) {
            XSSFRow row = sheet.getRow(i + 1);
            //第一行
            if (i == startRow) {
                //第一列创建行
                row = sheet.createRow(i + 1);
                //设置行高
                row.setHeight((short) (20 * 20));
                //创建列
                for (int colIndex = 0; colIndex < colInfoList.size(); colIndex++) {
                    ExcelColInfo colInfo = colInfoList.get(colIndex);
                    Cell cell = row.createCell(colIndex);
                    CellStyle cellStyle = workbook.createCellStyle();

                    //虚线上边框
                    cellStyle.setBorderTop(BorderFormatting.BORDER_DOTTED);

                    //下边框虚线
                    cellStyle.setBorderBottom(BorderFormatting.BORDER_DOTTED);

                    //第一列实线左边框
                    cellStyle.setBorderLeft(colIndex == 0 ? BorderFormatting.BORDER_THIN : BorderFormatting.BORDER_DOTTED);

                    //最后一列实线右边框
                    cellStyle.setBorderRight((colIndex == colSize - 1) ? BorderFormatting.BORDER_THIN : BorderFormatting.BORDER_DOTTED);

                    //上下居中
                    cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

                    //左右表示位置设定
                    if (colInfo.getCellAlign() != null) {
                        cellStyle.setAlignment(colInfo.getCellAlign().getValue());
                    }

                    //设置样式
                    cell.setCellStyle(cellStyle);
                }
            }
            //非内容首行
            else {
                XSSFRow firstRow = sheet.getRow(startRow + 1);
                row = sheet.createRow(i + 1);
                ExcelUtils.copyRow(workbook, firstRow, row, false);
            }
        }
    }

    /**
     * 从Map集合中取得指定属性的值
     *
     * @param map          Map集合
     * @param propertyName 属性名称
     * @return 属性值
     */
    private static Object getMapValue(Map map, String propertyName) {
        //通过字段名称取得值
        String columnName = DaoUtils.convertAttributeNameToColumnName(propertyName);
        if (map.containsKey(columnName)) {
            return map.getOrDefault(columnName, null);
        }

        //直接通过属性名称取得值
        if (map.containsKey(propertyName)) {
            return map.getOrDefault(propertyName, null);
        }
        return null;
    }

    /**
     * 根据Class取得Bean实例
     *
     * @param cls Class
     * @param <E> 返回值的类型
     * @return Bean实例
     */
    private static <E> E getBean(Class<E> cls) {
        try {
            return SpringUtils.getBean(cls);
        } catch (Exception e) {
            log.error(String.format("获取Bean实例（%s）失败。", cls.getName()));
            return null;
        }
    }

    /**
     * 是否包含敏感数据列？
     *
     * @param colInfoList               列信息列表
     * @param sensitiveMobileColumnList 敏感数据列（手机号码列名称）
     * @param sensitiveEmailColumnList  敏感数据列（邮件地址列名称）
     * @return true：包含敏感数据列
     */
    private static boolean hasSensitiveDataColumn(List<ExcelColInfo> colInfoList, String[] sensitiveMobileColumnList, String[] sensitiveEmailColumnList) {
        if (SecurityUtils.getSubject() == null || SecurityUtils.getSubject().getPrincipal() == null) {
            return false;
        }

        for (int colIndex = 0; colIndex < colInfoList.size(); colIndex++) {
            ExcelColInfo colInfo = colInfoList.get(colIndex);
            //手机号码敏感列
            for (String column : sensitiveMobileColumnList) {
                if (StringUtils.equals(DaoUtils.convertAttributeNameToColumnName(colInfo.getColName()), column)) {
                    return true;
                }
            }

            //邮件地址敏感列
            for (String column : sensitiveEmailColumnList) {
                if (StringUtils.equals(DaoUtils.convertAttributeNameToColumnName(colInfo.getColName()), column)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 创建内容行
     *
     * @param sheet       工作表
     * @param colInfoList 列信息列表
     * @param contents    列表内容
     * @param <T>         实体对象
     * @param startRow    正文开始行号
     */
    private static <T> void createContentsRowData(XSSFSheet sheet, List<ExcelColInfo> colInfoList, List<T> contents, int startRow) {
        List<SensitiveDataExportLogExt> list = new ArrayList<>();

        ParameterService parameterService = getBean(ParameterService.class);
        //敏感数据列（手机号码列名称）
        String[] sensitiveMobileColumnList = parameterService.getValue(AppCodeConsts.APP_COMMON, "SENSITIVE_DATA_MOBILE_COLUMN_LIST", "mobile").split(",");
        //敏感数据列（邮件地址列名称）
        String[] sensitiveEmailColumnList = parameterService.getValue(AppCodeConsts.APP_COMMON, "SENSITIVE_DATA_EMAIL_COLUMN_LIST", "email").split(",");

        //是否包含敏感数据列？
        boolean hasSensitiveData = hasSensitiveDataColumn(colInfoList, sensitiveMobileColumnList, sensitiveEmailColumnList);

        //请求URI
        String requestUri = StringUtils.EMPTY;
        //远程IP
        String remoteIp = StringUtils.EMPTY;

        //包含敏感数据列的场合
        if (hasSensitiveData) {
            requestUri = AppContext.getRequest().getRequestURI().replaceFirst(AppContext.getRequest().getContextPath(), StringUtils.EMPTY);
            remoteIp = HttpUtils.getRemoteIp(AppContext.getRequest());
        }

        for (int i = startRow; i < contents.size() + startRow; i++) {
            SensitiveDataExportLogExt sensitiveDataExportLog = new SensitiveDataExportLogExt();

            //包含敏感数据列的场合
            if (hasSensitiveData) {
                sensitiveDataExportLog.setUrl(requestUri);
                sensitiveDataExportLog.setRemoteIp(remoteIp);
                list.add(sensitiveDataExportLog);
            }

            try {
                T entity = contents.get(i - startRow);
                XSSFRow row = sheet.getRow(i + 1);
                for (int colIndex = 0; colIndex < colInfoList.size(); colIndex++) {
                    ExcelColInfo colInfo = colInfoList.get(colIndex);

                    Cell cell = row.getCell(colIndex);

                    Object fieldValue = null;
                    Field field = null;

                    //通过Map集合数据导出的场合
                    if (entity instanceof Map) {
                        fieldValue = getMapValue((Map) entity, colInfo.getColName());
                    }
                    //通过Entity实体对象导出的场合
                    else {
                        try {
                            field = EntityUtils.getEntityField(entity, colInfo.getColName());
                            fieldValue = field.get(entity);
                        } catch (Exception e) {
                            log.info("对应列不存在，colInfo.getColName() = " + colInfo.getColName());
                        }
                    }

                    //包含敏感数据列的场合
                    if (hasSensitiveData && fieldValue != null) {
                        //手机号码敏感列
                        for (String column : sensitiveMobileColumnList) {
                            if (StringUtils.equals(DaoUtils.convertAttributeNameToColumnName(colInfo.getColName()), column)) {
                                sensitiveDataExportLog.setMobile(fieldValue.toString());
                            }
                        }

                        //邮件地址敏感列
                        for (String column : sensitiveEmailColumnList) {
                            if (StringUtils.equals(DaoUtils.convertAttributeNameToColumnName(colInfo.getColName()), column)) {
                                sensitiveDataExportLog.setEmail(fieldValue.toString());
                            }
                        }
                    }

                    //如果对应属性值为空的场合
                    if (fieldValue == null) {
                        cell.setCellValue(StringUtils.EMPTY);
                    }
                    //属性存在时
                    else {
                        //如果数值是时间类型，进行格式化
                        if (fieldValue instanceof Date) {
                            String format = CmnConsts.DATE_TIME_FORMAT;
                            //Entity字段的场合
                            if (field != null) {
                                //读取注解上的日期格式
                                DateTimeFormat dateTimeFormat = field.getAnnotation(DateTimeFormat.class);
                                format = StringUtils.isEmpty(dateTimeFormat.pattern()) ? CmnConsts.DATE_TIME_FORMAT : dateTimeFormat.pattern();
                            }

                            String v = DateFormatUtils.format((Date) fieldValue, format);
                            //过滤全为 0 时分秒
                            v = StringUtils.removeEnd(v, " 00:00:00");
                            cell.setCellValue(v);
                        }
                        //数字类型的场合
                        else if (fieldValue instanceof BigDecimal || fieldValue instanceof BigInteger || fieldValue instanceof Integer || fieldValue instanceof Double || fieldValue instanceof Float) {
                            if (fieldValue != null) {
                                Double cellValue = 0.0;
                                if (fieldValue instanceof BigDecimal || fieldValue instanceof Double || fieldValue instanceof Float) {
                                    DecimalFormat df = new DecimalFormat("#.00");
                                    //强制保留2位小数
                                    cellValue = Double.parseDouble(df.format(fieldValue));
                                }
                                else {
                                    cellValue = Double.parseDouble(fieldValue.toString());
                                }

                                //百分比率的场合
                                if (colInfo.getTitle().indexOf("%") >= 0 || colInfo.getTitle().indexOf("％") >= 0) {
                                    //百分比率为【0】的场合，显示为【-】
                                    CellStyle cellStyle = cell.getCellStyle();
                                    cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
                                    cell.setCellStyle(cellStyle);
                                    if (cellValue == 0) {
                                        cell.setCellValue("-");
                                    }
                                    //追加百分率【%】符号
                                    else {
                                        //百分比率(半角百分号，传入的数值是乘了100的数值)的场合
                                        if (colInfo.getTitle().indexOf("%") > 0) {
                                            cell.setCellValue(cellValue / 100);
                                        }
                                        //百分比率(全角百分号，传入的数值是未乘100的数值)的场合
                                        else {
                                            cell.setCellValue(cellValue);
                                        }
                                    }
                                }
                                else {
                                    cell.setCellValue(cellValue);
                                }
                            }
                        }
                        else {
                            cell.setCellValue(fieldValue.toString());
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Excel导出异常：", e);
            }
        }

        //包含敏感数据列的场合
        if (hasSensitiveData) {
            SensitiveDataExportLogDao sensitiveDataExportLogDao = getBean(SensitiveDataExportLogDao.class);
            sensitiveDataExportLogDao.batchInsert(list);
        }
    }

    private static Map<Field, ExcelColumn> getMappingFields(Class<?> clazz, String name) {
        //防止不同类中有同样的excel column 名称，所以以类名+字段名的类型
        String mapKey = clazz + name;
        if (result.containsKey(mapKey)) {
            return result.get(mapKey);
        }
        PropertyDescriptor[] propertyDescriptors = ReflectionUtils.getPropertyDescriptors(clazz);
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            if (descriptor.getWriteMethod() != null) {
                Field field = ReflectionUtils.getFieldFromBean(clazz, descriptor.getDisplayName());
                ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                if (annotation == null || StringUtils.isEmpty(annotation.name())) {
                    continue;
                }

                //如果没有多个列名（无逗号分隔时）
                if (annotation.name().indexOf(",") == -1 && Objects.equals(annotation.name(), name)) {
                    Map<Field, ExcelColumn> map = new HashMap<>();
                    map.put(field, annotation);
                    result.put(mapKey, map);
                    break;
                }
                //有多个列名（多个以逗号分隔）
                else if (annotation.name().indexOf(",") != -1) {
                    //name 如果有多个时， 以逗号分隔
                    String[] excelColumnArray = StringUtils.split(annotation.name(), ",");
                    boolean isFound = false;
                    for (String columnName : excelColumnArray) {
                        //列名如果在注解中存在，则返回该field，跳出循环
                        columnName = StringUtils.trim(columnName);
                        if (StringUtils.equals(columnName, name)) {
                            Map<Field, ExcelColumn> map = new HashMap<>();
                            map.put(field, annotation);
                            result.put(mapKey, map);
                            isFound = true;
                            break;
                        }
                    }
                    //找到对应的列名的话， 跳出本循环
                    if (isFound) {
                        break;
                    }
                }
            }
        }
        return result.get(mapKey);
    }

    /**
     * 从Excel文件流中读取数据
     *
     * @param clazz 行数据存储类
     * @param is    文件流
     * @param <T>   泛型定义
     * @return 以工作表名称分离存贮的列表数据集合
     * @throws IOException
     * @throws IllegalAccessException
     */
    public static <T> Map<String, List<T>> read(Class<T> clazz, InputStream is) throws IOException, IllegalAccessException {
        Map<String, List<T>> map = new LinkedHashMap<>();
        //TITLE 位置
        ExcelEntity excelEntity = clazz.getAnnotation(ExcelEntity.class);
        if (excelEntity == null) {
            return map;
        }
        try {
            //WorkbookFactory解决Excel兼容性问题
            Workbook workbook = WorkbookFactory.create(is);
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                //隐藏的表单不处理
                if (workbook.isSheetHidden(i)) {
                    continue;
                }
                Sheet sheet = workbook.getSheetAt(i);
                List<T> content = new ArrayList<>();
                Row titleRow = sheet.getRow(excelEntity.titleRow());
                boolean skip;
                for (Row row : sheet) {
                    skip = false;
                    if (row.getRowNum() <= excelEntity.titleRow()) {
                        continue;
                    }
                    T entity = ReflectionUtils.newInstance(clazz);
                    for (Cell cell : row) {
                        Cell titleCell;
                        try {
                            titleCell = titleRow.getCell(cell.getColumnIndex());
                        } catch (Exception e) {
                            //没有标题设置的场合 (内容列的列数超过 列头时，getCell会报错）
                            break;
                        }
                        if (titleCell == null) {
                            //没有标题设置的场合
                            break;
                        }
                        Map<Field, ExcelColumn> fieldMap = getMappingFields(clazz, titleCell.getStringCellValue());
                        if (fieldMap == null) {
                            //entity 没有定义excel字段映射的场合
                            continue;
                        }
                        Field field = (Field) fieldMap.keySet().toArray()[0];
                        field.setAccessible(true);
                        String value = null;
                        String dateFormat = fieldMap.get(field).dateFormat();
                        if (titleCell.getCellType() != Cell.CELL_TYPE_BLANK) {
                            value = getCellValueByType(cell, dateFormat, field);
                        }
                        if (fieldMap.get(field).required() && StringUtils.isEmpty(value)) {
                            skip = true;
                            break;
                        }

                        //其他类型增加
                        if (field.getType() == Integer.class) {
                            field.set(entity, NumberUtils.toInt(value));
                        }
                        //日期类型
                        else if (field.getType() == Date.class) {
                            field.set(entity, StringUtils.isEmpty(dateFormat) ? DateUtils.parseDate(value) : DateUtils.parseDate(value, dateFormat));
                        }
                        //BigDecimal类型
                        else if (field.getType() == BigDecimal.class) {
                            value = StringUtils.isEmpty(value) ? "0" : value;
                            field.set(entity, new BigDecimal(value));
                        }
                        else {
                            field.set(entity, value);
                        }
                    }
                    if (!skip) {
                        content.add(entity);
                    }
                }
                if (content.size() > 0) {
                    //定义sheet区分名称（行列）   默认Sheet名
                    map.put(sheet.getSheetName(), content);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return map;
    }

    /**
     * 取得单元格的值
     *
     * @param cell       单元格
     * @param dateFormat 日期格式
     * @param field      字段
     * @return 字符串值
     */
    public static String getCellValueByType(Cell cell, String dateFormat, Field field) {
        String value = "";
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            if (StringUtils.isNotEmpty(dateFormat)) {
                value = DateFormatUtils.format(cell.getDateCellValue(), dateFormat);
            }
            else {
                //如果是数字类型，判断是否带.0
                long longVal = Math.round(cell.getNumericCellValue());
                if (Double.parseDouble(longVal + ".0") == cell.getNumericCellValue()) {
                    value = String.valueOf(longVal);
                }
                else {
                    value = String.valueOf(cell.getNumericCellValue());
                }
            }
        }
        else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            value = cell.getStringCellValue();
        }
        else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            value = null;
        }
        else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            //日期的时候
            if (StringUtils.isNotEmpty(dateFormat)) {
                value = DateFormatUtils.format(cell.getDateCellValue(), dateFormat);
            }
            // 日期以外的场合
            else {
                //如果字段类型是字符串的时候，
                if (field.getType() == String.class) {
                    //有些值是数字类型， excelentity设定为了字符串，这时取值会出错， 所以取不到文字时再取一下数字的值
                    try {
                        value = String.valueOf(cell.getStringCellValue());
                    } catch (IllegalStateException e) {
                        try {
                            value = String.valueOf(cell.getNumericCellValue());
                        } catch (IllegalStateException e1) {
                            value = String.valueOf(cell.getRichStringCellValue());
                        }
                    }

                }
                //字符串以外的场合
                else {
                    try {
                        value = String.valueOf(cell.getNumericCellValue());
                    } catch (IllegalStateException e) {
                        value = String.valueOf(cell.getRichStringCellValue());
                    }
                }
            }
        }
        //去除导入时的前后半角空格
        return value != null ? value.trim() : value;
    }

    /**
     * 根据上传的excel路径转换为list<map>
     *
     * @param excelFilePath excel的上传路径
     * @return list<map>
     * @throws Exception
     */
    public static List readExcel(String excelFilePath) throws Exception {
        List<Map> list = new ArrayList<>();
        InputStream stream = null;
        try {
            Workbook workbook = null;
            if (excelFilePath.endsWith(".xls")) {
                stream = new FileInputStream(excelFilePath);
                workbook = new HSSFWorkbook(stream);
            }
            else if (excelFilePath.endsWith(".xlsx")) {
                stream = new FileInputStream(excelFilePath);
                workbook = new XSSFWorkbook(stream);
            }
            Sheet sheet = workbook.getSheetAt(0);
            // 行数(从0开始,相当于最后一行的索引),列数
            int countRow = sheet.getLastRowNum();
            int countCell = sheet.getRow(0).getPhysicalNumberOfCells();
            Object k = null;
            for (int i = 0; i < countRow; i++) {
                Map map = new HashMap<>();
                for (int j = 0; j < countCell; j++) {
                    // 过滤掉第一行，从第二行开始读取
                    Row row = sheet.getRow(i + 1);
                    // 获取对应行的指定列
                    Cell cell = row.getCell(j);

                    // 得到单元格数据类型
                    int type = cell.getCellType();
                    // 判断数据类型
                    switch (type) {
                        //单元格为空
                        case Cell.CELL_TYPE_BLANK:
                            k = "";
                            break;
                        //单元格为布尔型
                        case Cell.CELL_TYPE_BOOLEAN:
                            k = cell.getBooleanCellValue() + "";
                            break;
                        //单元格为单元的误差值
                        case Cell.CELL_TYPE_ERROR:
                            k = cell.getErrorCellValue() + "";
                            break;
                        //单元格为公式计算后的结果
                        case Cell.CELL_TYPE_FORMULA:
                            k = cell.getCellFormula();
                            break;
                        //单元格为数字类型
                        case Cell.CELL_TYPE_NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                // 格式化日期
                                k = new DataFormatter().formatRawCellContents(cell.getNumericCellValue(), 0, "yyyy-mm-dd");
                            }
                            //如果是数字类型，判断是否带.0
                            else {
                                long longVal = Math.round(cell.getNumericCellValue());
                                if (Double.parseDouble(longVal + ".0") == cell.getNumericCellValue()) {
                                    k = longVal;
                                }
                                else {
                                    k = cell.getNumericCellValue();
                                }
                            }
                            break;
                        //单元格为字符串类型
                        case Cell.CELL_TYPE_STRING:
                            k = cell.getStringCellValue();
                            break;
                        default:
                            break;
                    }
                    map.put("column" + (j + 1), k);
                }
                list.add(map);
            }
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        finally {
            IOUtils.closeQuietly(stream);
        }
        return list;
    }

    /**
     * 行复制功能
     *
     * @param fromRow 源行
     * @param toRow   目标行
     */
    public static void copyRow(XSSFWorkbook workbook, XSSFRow fromRow, XSSFRow toRow, boolean copyValueFlag) {
        //设置行高
        toRow.setHeight(fromRow.getHeight());

        for (Iterator cellIt = fromRow.cellIterator(); cellIt.hasNext(); ) {
            XSSFCell srcCell = (XSSFCell) cellIt.next();
            XSSFCell newCell = toRow.createCell(srcCell.getColumnIndex());
            copyCell(workbook, srcCell, newCell, copyValueFlag);
        }
    }

    /**
     * 复制单元格
     *
     * @param srcCell       源单元格
     * @param destCell      目标单元格
     * @param copyValueFlag true则连同cell的内容一起复制
     */
    public static void copyCell(XSSFWorkbook workbook, XSSFCell srcCell, XSSFCell destCell, boolean copyValueFlag) {
        //样式
        //XSSFCellStyle destCellStyle = destCell.getCellStyle() == null? workbook.createCellStyle() : destCell.getCellStyle();
        //copyCellStyle(srcCell.getCellStyle(), destCellStyle);
        destCell.setCellStyle(srcCell.getCellStyle());
        //评论
        if (srcCell.getCellComment() != null) {
            destCell.setCellComment(srcCell.getCellComment());
        }
        // 不同数据类型处理
        int srcCellType = srcCell.getCellType();
        destCell.setCellType(srcCellType);
        if (copyValueFlag) {
            if (srcCellType == HSSFCell.CELL_TYPE_NUMERIC) {
                if (HSSFDateUtil.isCellDateFormatted(srcCell)) {
                    destCell.setCellValue(srcCell.getDateCellValue());
                }
                else {
                    destCell.setCellValue(srcCell.getNumericCellValue());
                }
            }
            else if (srcCellType == HSSFCell.CELL_TYPE_STRING) {
                destCell.setCellValue(srcCell.getRichStringCellValue());
            }
            else if (srcCellType == HSSFCell.CELL_TYPE_BLANK) {

            }
            else if (srcCellType == HSSFCell.CELL_TYPE_BOOLEAN) {
                destCell.setCellValue(srcCell.getBooleanCellValue());
            }
            else if (srcCellType == HSSFCell.CELL_TYPE_ERROR) {
                destCell.setCellErrorValue(srcCell.getErrorCellValue());
            }
            else if (srcCellType == HSSFCell.CELL_TYPE_FORMULA) {
                destCell.setCellFormula(srcCell.getCellFormula());
                destCell.setCellFormula(getUpdateFormulaString(workbook, destCell));
            }
            else {
            }
        }
    }

    /**
     * 修正过cell的公式 取得
     *
     * @param workbook    当前工作簿
     * @param currentCell 当前cell
     * @return 修正过cell的公式
     */
    private static String getUpdateFormulaString(XSSFWorkbook workbook, XSSFCell currentCell) {
        String formula = currentCell.getCellFormula();
        FormulaParsingWorkbook parseBook;
        FormulaRenderingWorkbook renderBook;
        XSSFEvaluationWorkbook book = XSSFEvaluationWorkbook.create(workbook);
        parseBook = book;
        renderBook = book;
        Ptg[] ptgs = FormulaParser.parse(formula, parseBook, FormulaType.CELL, -1);
        for (Ptg ptg : ptgs) {
            //「A1」形式
            if (ptg instanceof RefPtg) {
                RefPtg refptg = (RefPtg) ptg;
                if (refptg.isRowRelative()) {
                    refptg.setRow(currentCell.getRowIndex());
                }
            }
            //「A1:C1」形式
            if (ptg instanceof AreaPtg) {
                AreaPtg areaPtg = (AreaPtg) ptg;
                //开始行是否相对
                if (areaPtg.isFirstRowRelative()) {
                    areaPtg.setFirstRow(currentCell.getRowIndex());
                }
                if (areaPtg.isLastRowRelative()) {
                    areaPtg.setLastRow(currentCell.getRowIndex());
                }
            }

            //「Sheet!A1」形式
            if (ptg instanceof Ref3DPtg) {
                Ref3DPtg ref3dptg = (Ref3DPtg) ptg;
                System.out.printf("sheetIndex=%d (row=%s%d, col=%s%d)\n",
                        ref3dptg.getExternSheetIndex(),
                        ref3dptg.isRowRelative() ? "" : "$", ref3dptg.getRow(),
                        ref3dptg.isColRelative() ? "" : "$", ref3dptg.getColumn());
            }
            //「SheetA1:C1」形式
            if (ptg instanceof Area3DPtg) {
                Area3DPtg area3DPtg = (Area3DPtg) ptg;
                //开始行是否相对
                if (area3DPtg.isFirstRowRelative()) {
                    area3DPtg.setFirstRow(currentCell.getRowIndex());
                }
                if (area3DPtg.isLastRowRelative()) {
                    area3DPtg.setLastRow(currentCell.getRowIndex());
                }
            }
        }
        String updateFormula = FormulaRenderer.toFormulaString(renderBook, ptgs);

        return updateFormula;
    }

    /**
     * 复制一个单元格样式到目的单元格样式
     *
     * @param fromStyle
     * @param toStyle
     */
    public static void copyCellStyle(XSSFCellStyle fromStyle, XSSFCellStyle toStyle) {
        toStyle.setAlignment(fromStyle.getAlignment());
        //边框和边框颜色
        toStyle.setBorderBottom(fromStyle.getBorderBottom());
        toStyle.setBorderLeft(fromStyle.getBorderLeft());
        toStyle.setBorderRight(fromStyle.getBorderRight());
        toStyle.setBorderTop(fromStyle.getBorderTop());
        toStyle.setTopBorderColor(fromStyle.getTopBorderColor());
        toStyle.setBottomBorderColor(fromStyle.getBottomBorderColor());
        toStyle.setRightBorderColor(fromStyle.getRightBorderColor());
        toStyle.setLeftBorderColor(fromStyle.getLeftBorderColor());

        //背景和前景
        toStyle.setFillBackgroundColor(fromStyle.getFillBackgroundColor());
        toStyle.setFillForegroundColor(fromStyle.getFillForegroundColor());

        toStyle.setDataFormat(fromStyle.getDataFormat());
        toStyle.setFillPattern(fromStyle.getFillPattern());
        toStyle.setHidden(fromStyle.getHidden());
        //首行缩进
        toStyle.setIndention(fromStyle.getIndention());
        toStyle.setLocked(fromStyle.getLocked());
        //旋转
        toStyle.setRotation(fromStyle.getRotation());
        toStyle.setVerticalAlignment(fromStyle.getVerticalAlignment());
        toStyle.setWrapText(fromStyle.getWrapText());
    }

    /**
     * 判断是否是空行
     *
     * @param row 行数据
     * @return true：空行
     */
    public static boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            //列非空的场合
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断指定的单元格是否是合并单元格
     *
     * @param sheet  工作表
     * @param row    行下标
     * @param column 列下标
     * @return　是否合并单元格
     */
    public static boolean isMergedRegion(Sheet sheet, int row, int column) {
        //获取表中所有的合并单元格
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            //获取第i个合并的单元格
            CellRangeAddress range = sheet.getMergedRegion(i);
            //获取合并单元格的起始列
            int firstColumn = range.getFirstColumn();
            //获取合并单元格的结合列
            int lastColumn = range.getLastColumn();
            //获取合并单元格的起始行
            int firstRow = range.getFirstRow();
            //获取合并单元格的结束行
            int lastRow = range.getLastRow();
            //传入的行列，在合并单元格的行列中,则返回true
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取合并单元格的值
     *
     * @param sheet  工作表
     * @param row    行下标
     * @param column 列下标
     * @return 获取合并单元格的值
     */
    public static String getMergedRegionValue(Sheet sheet, int row, int column) {
        //如果不是合并单元格，则返回当前单元格的值
        if (!isMergedRegion(sheet, row, column)) {
            return getCellValue(sheet.getRow(row).getCell(column));
        }
        //获取表中所有的合并单元格
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            //获取第i个合并的单元格
            CellRangeAddress cellRangeAddress = sheet.getMergedRegion(i);
            //获取合并单元格的起始列
            int firstColumn = cellRangeAddress.getFirstColumn();
            //获取合并单元格的结合列
            int lastColumn = cellRangeAddress.getLastColumn();
            //获取合并单元格的起始行
            int firstRow = cellRangeAddress.getFirstRow();
            //获取合并单元格的结束行
            int lastRow = cellRangeAddress.getLastRow();
            //传入的行列，在合并单元格的行列中,则返回合并单元格的起始行其实列的值
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    Row sheetRow = sheet.getRow(firstRow);
                    Cell sheetShell = sheetRow.getCell(firstColumn);
                    return getCellValue(sheetShell);
                }
            }
        }
        return null;
    }

    /**
     * 获取单元格的值
     *
     * @param cell 单元格
     * @return 单元格的值
     */
    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        //字符串类型
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        }
        //布尔类型
        else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        }
        //公式类型
        else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            return cell.getCellFormula();
        }
        //数字类型
        else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        }
        return "";
    }

    /**
     * 判断Row(行)是否存在空的单元格或者这行是否存在单元格
     *
     * @param row 行下标
     * @return 是否存在空的单元格
     */
    public static boolean rowContainEmptyCell(Row row) {
        if (row != null) {
            short lastCellNum = row.getLastCellNum();
            // 如果不存在单元格则返回true
            if (lastCellNum == 0) {
                return true;
            }
            else {
                for (int i = 0; i < lastCellNum; i++) {
                    Cell cell = row.getCell(i);
                    if (isEmptyCell(cell)) {
                        return true;
                    }
                }
            }
        }
        else {
            return true;
        }
        return false;
    }

    /**
     * 判断Cell(单元格)是否为空
     *
     * @param cell 单元格
     * @return Cell(单元格)是否为空
     */
    public static boolean isEmptyCell(Cell cell) {
        String cellContent = getCellValue(cell);
        if (StringUtils.isNotEmpty(cellContent)) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获取参数指定行的指定列对应的cell（取不到时，创建cell）
     *
     * @param row       指定行
     * @param cellIndex 指定列索引
     * @return 参数指定行的指定列对应的cell
     */
    public static Cell getCell(XSSFRow row, int cellIndex) {
        if (row.getCell(cellIndex) != null) {
            return row.getCell(cellIndex);
        }
        return row.createCell(cellIndex);
    }
}
