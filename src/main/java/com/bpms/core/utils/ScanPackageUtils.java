package com.bpms.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 路径扫描工具类
 */
public class ScanPackageUtils {

    private static Logger logger = LoggerFactory.getLogger(ScanPackageUtils.class);

    /**
     * Get all fully qualified names located in the specified package
     * and its sub-package.
     *
     * @return A list of fully qualified names.
     * @throws IOException
     */
    public static List<String> getFullyQualifiedClassNameList(String basePackage) throws IOException {
        if (logger.isInfoEnabled()) {
            logger.info("开始扫描包{}下的所有类", basePackage);
        }
        return doScan(basePackage, new ArrayList<>());
    }

    /**
     * 扫描包
     *
     * @param basePackage 包
     * @param nameList    A list to contain the result.
     * @return A list of fully qualified names.
     * @throws IOException
     */
    private static List<String> doScan(String basePackage, List<String> nameList) throws IOException {
        // 将.转换为/
        String splashPath = basePackage.replaceAll("\\.", "/");

        // 获取文件path
        URL url = ScanPackageUtils.class.getClassLoader().getResource(splashPath);

        String filePath = getRootPath(url);

        List<String> names = readFromDirectory(filePath);

        for (String name : names) {
            if (isClassFile(name)) {
                nameList.add(toFullyQualifiedName(name, basePackage));
            }
            else {
                doScan(basePackage + "." + name, nameList);
            }
        }

        return nameList;
    }

    /**
     * Convert short class name to fully qualified name.
     * e.g., String -> java.lang.String
     */
    private static String toFullyQualifiedName(String shortName, String basePackage) {
        return basePackage + '.' + trimExtension(shortName);
    }


    /**
     * 读取路径下的所有文件
     */
    private static List<String> readFromDirectory(String path) {
        File file = new File(path);
        String[] names = file.list();
        if (null == names) {
            return null;
        }

        return Arrays.asList(names);
    }

    /**
     * 判断是否为class文件
     */
    private static boolean isClassFile(String name) {
        return name.endsWith(".class");
    }

    /**
     * 获取rootPath
     */
    private static String getRootPath(URL url) {
        String fileUrl = url.getFile();
        int pos = fileUrl.indexOf('!');

        if (-1 == pos) {
            return fileUrl;
        }

        return fileUrl.substring(5, pos);
    }

    /**
     * 去除class后缀名
     * "Apple.class" -> "Apple"
     */
    private static String trimExtension(String name) {
        int pos = name.indexOf('.');
        if (-1 != pos) {
            return name.substring(0, pos);
        }

        return name;
    }
}
