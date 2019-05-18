package com.bpms.core.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {
    /**
     * 请求超时时间
     */
    private static final int REQUEST_TIME_OUT = 1 * 60 * 1000;

    /**
     * 连接超时时间
     */
    private static final int CONN_TIME_OUT = 5 * 60 * 1000;

    /**
     * 数据传输时间
     */
    private static final int SO_TIME_OUT = 10 * 60 * 1000;

    static Logger log = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 取得远程地址IP
     *
     * @param request HttpServletRequest
     * @return 远程地址IP
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String remoteIp = request.getHeader("x-forwarded-for");
        if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getHeader("X-Real-IP");
        }
        if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getHeader("X-Forwarded-For");
        }
        if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getHeader("Proxy-Client-IP");
        }
        if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getHeader("HTTP_CLIENT_IP");
        }
        if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getRemoteAddr();
        }
        if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getRemoteHost();
        }
        return remoteIp;
    }

    /**
     * 是否是手机浏览访问？
     *
     * @param request HttpServletRequest
     * @return true：手机浏览访问
     */
    public static boolean isMobileAccess(HttpServletRequest request) {
        boolean isMobile = false;
        String[] mobileAgents = {"iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
                "opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
                "nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
                "docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
                "techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
                "wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
                "pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
                "240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
                "blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
                "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
                "mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
                "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
                "smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
                "voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
                "Googlebot-Mobile"};
        if (request.getHeader("User-Agent") != null) {
            for (String mobileAgent : mobileAgents) {
                if (request.getHeader("User-Agent").toLowerCase().indexOf(mobileAgent) >= 0) {
                    isMobile = true;
                    break;
                }
            }
        }
        return isMobile;
    }

    /**
     * GET请求
     *
     * @param url 请求URL
     * @return 返回请求结果
     */
    public static String doGet(String url) {
        return doGet(url, "utf-8", null, 0, 0);
    }

    /**
     * GET请求
     *
     * @param url    请求URL
     * @param encode 发送编码格式
     * @param params 参数
     * @return 请求结果
     */
    public static String doGet(String url, String encode, Map<String, Object> params) {
        return doGet(url, encode, params, 0, 0);
    }

    /**
     * GET请求
     *
     * @param url            请求URL
     * @param encode         发送编码格式
     * @param params         参数
     * @param connectTimeOut 连接超时时间[毫秒]
     * @param soTimeOut      数据传输时间[毫秒]
     * @return
     */
    public static String doGet(String url, String encode, Map<String, Object> params, int connectTimeOut, int soTimeOut) {
        return doGet(url, encode, null, params, connectTimeOut, soTimeOut);
    }

    /**
     * GET请求
     *
     * @param url            请求URL
     * @param encode         发送编码格式
     * @param otherHeaders   请求头
     * @param params         参数
     * @param connectTimeOut 连接超时时间[毫秒]
     * @param soTimeOut      数据传输时间[毫秒]
     * @return 请求结果
     */
    public static String doGet(String url, String encode, Header[] otherHeaders, Map<String, Object> params, int connectTimeOut, int soTimeOut) {
        HttpClient client = new HttpClient();

        if (params != null) {
            if (url.indexOf("?") != -1) {
                url += "&" + getParams(params).toString();
            }
            else {
                url += "?" + getParams(params).toString();
            }
        }

        HttpMethod method = new GetMethod(url);
        setGetHeader(method, otherHeaders);

        String result = "";
        try {
            method.setRequestHeader("Content-Type", "text/html;charset=utf-8");

            //设置页面编码
            if (StringUtils.isEmpty(encode)) {
                //默认UTF-8
                encode = "utf-8";
            }
            method.getParams().setContentCharset(encode);

            HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();

            // 设置连接超时时间(单位毫秒)
            managerParams.setConnectionTimeout(connectTimeOut > 0 ? connectTimeOut : CONN_TIME_OUT);

            // 设置读数据超时时间(单位毫秒)
            managerParams.setSoTimeout(soTimeOut > 0 ? soTimeOut : SO_TIME_OUT);

            client.executeMethod(method);

            //获取返回的JSON数据
            result = method.getResponseBodyAsString();
        } catch (Exception e) {
            log.error("GET请求发生系统异常：", e);
        } finally {
            try {
                // 释放连接
                method.releaseConnection();
                client.getHttpConnectionManager().closeIdleConnections(0);
            } catch (Exception ex) {
            }
        }
        return result;
    }

    /**
     * 获取参数
     *
     * @param params 键值对参数集合
     * @return 参数键值字符串
     */
    public static StringBuffer getParams(Map<String, Object> params) {
        StringBuffer sb = new StringBuffer();

        //拼装参数
        if (params != null) {
            for (Map.Entry<String, Object> e : params.entrySet()) {
                sb.append(e.getKey());
                sb.append("=");
                sb.append(e.getValue());
                sb.append("&");
            }
            sb.substring(0, sb.length() - 1);
        }

        return sb;
    }

    /**
     * POST请求
     *
     * @param url       请求URL
     * @param paramsMap 请求参数MAP
     * @return 响应结果
     */
    public static String doPost(String url, Map<String, Object> paramsMap) {
        return doPost(url, null, paramsMap);
    }

    /**
     * POST请求
     *
     * @param url        请求URL
     * @param paramsJson 请求参数json字符
     * @return 响应结果
     */
    public static String doPost(String url, String paramsJson) {
        return doPost(url, null, paramsJson);
    }

    /**
     * POST请求
     *
     * @param url        请求URL
     * @param paramsJson 请求参数json字符
     * @return 响应结果
     */
    public static String doPost(String url, String encode, String paramsJson) {
        return doPost(url, encode, null, paramsJson, 0, 0, 0);
    }

    /**
     * POST请求
     *
     * @param url       请求URL
     * @param paramsMap 请求参数MAP
     * @return 响应结果
     */
    public static String doPost(String url, String encode, Map<String, Object> paramsMap) {
        return doPost(url, encode, paramsMap, null, 0, 0, 0);
    }

    /**
     * POST请求
     *
     * @param url            请求URL
     * @param encode         发送编码格式
     * @param paramsMap      请求参数MAP
     * @param connectTimeOut 连接超时时间[毫秒]
     * @param soTimeOut      数据传输时间[毫秒]
     * @param requestTimeOut 请求超时时间[毫秒]
     * @return 响应结果
     */
    public static String doPost(String url, String encode, Map<String, Object> paramsMap, int connectTimeOut, int soTimeOut, int requestTimeOut) {
        return doPost(url, encode, paramsMap, null, connectTimeOut, soTimeOut, requestTimeOut);
    }

    /**
     * POST请求
     *
     * @param url            请求URL
     * @param encode         发送编码格式
     * @param paramsMap      请求参数MAP[可选]
     * @param paramsJson     请求参数json字符[可选]
     * @param connectTimeOut 连接超时时间[毫秒]
     * @param soTimeOut      数据传输时间[毫秒]
     * @param requestTimeOut 请求超时时间[毫秒]
     * @return 响应结果
     */
    private static String doPost(String url, String encode, Map<String, Object> paramsMap, String paramsJson, int connectTimeOut, int soTimeOut, int requestTimeOut) {
        return doPost(url, encode, null, paramsMap, paramsJson, connectTimeOut, soTimeOut, requestTimeOut);
    }

    /**
     * POST请求
     *
     * @param url            请求URL
     * @param encode         发送编码格式
     * @param otherHeaders   附加请求头部信息
     * @param paramsMap      请求参数MAP[可选]
     * @param paramsJson     请求参数json字符[可选]
     * @param connectTimeOut 连接超时时间[毫秒]
     * @param soTimeOut      数据传输时间[毫秒]
     * @param requestTimeOut 请求超时时间[毫秒]
     * @return 响应结果
     */
    public static String doPost(String url, String encode, Header[] otherHeaders, Map<String, Object> paramsMap, String paramsJson, int connectTimeOut, int soTimeOut, int requestTimeOut) {
        HttpPost httpPost = new HttpPost(url);
        // set header
        setDefaultHeader(httpPost, url);
        setOtherHeader(httpPost, otherHeaders);

        //设置页面编码
        if (StringUtils.isEmpty(encode)) {
            //默认UTF-8
            encode = "utf-8";
        }

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(soTimeOut > 0 ? soTimeOut : SO_TIME_OUT).setConnectTimeout(connectTimeOut > 0 ? connectTimeOut : CONN_TIME_OUT)
                .setConnectionRequestTimeout(requestTimeOut > 0 ? requestTimeOut : REQUEST_TIME_OUT).setExpectContinueEnabled(false).build();

        // RequestConfig.DEFAULT
        httpPost.setConfig(requestConfig);

        // 响应内容
        String responseContent = null;
        ThreadLocal<CloseableHttpClient> httpClient = new ThreadLocal<CloseableHttpClient>();
        try {
            if (CollectionUtils.isNotEmpty(paramsMap) && StringUtils.isEmpty(paramsJson)) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(getParamsList(paramsMap), encode);
                httpPost.setEntity(entity);
            }
            else {
                if (StringUtils.isNotEmpty(paramsJson)) {
                    httpPost.setEntity(new StringEntity(paramsJson, encode));
                }
            }

            // 执行post请求
            CloseableHttpClient client = HttpConnectionManager.getHttpClient();

            httpClient.set(client);
            HttpResponse httpResponse = httpClient.get().execute(httpPost);

            // 获取响应消息实体
            HttpEntity entityRep = httpResponse.getEntity();
            if (entityRep != null) {
                responseContent = EntityUtils.toString(httpResponse.getEntity(), encode);

                // 获取HTTP响应的状态码
                int statusCode = httpResponse.getStatusLine().getStatusCode();

                if (statusCode == HttpStatus.SC_OK) {

                }
                else if ((statusCode == HttpStatus.SC_MOVED_TEMPORARILY)
                        || (statusCode == HttpStatus.SC_MOVED_PERMANENTLY)
                        || (statusCode == HttpStatus.SC_SEE_OTHER)
                        || (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
                    responseContent = null;
                }
                // Consume response content
                EntityUtils.consume(entityRep);
                // Do not need the rest
                httpPost.abort();
            }
        } catch (Exception e) {
            log.error("POST请求发生系统异常：", e);
        } finally {
            httpPost.releaseConnection();
        }
        return responseContent;
    }

    /**
     * 将传入的键/值对参数转换为NameValuePair参数集
     *
     * @param paramsMap 参数集, 键/值对
     * @return NameValuePair参数集
     */
    private static List<NameValuePair> getParamsList(Map<String, Object> paramsMap) {
        if (paramsMap == null || paramsMap.size() == 0) {
            return null;
        }
        // 创建参数队列
        List<org.apache.http.NameValuePair> params = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> map : paramsMap.entrySet()) {
            params.add(new BasicNameValuePair(map.getKey(), map.getValue().toString()));
        }
        return params;
    }

    /**
     * 默认请求头设置
     *
     * @param httpPost HttpPost
     * @param url      URL地址
     */
    private static void setDefaultHeader(HttpPost httpPost, String url) {
        if (null != httpPost) {
            httpPost.addHeader("Referer", url);
            httpPost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpPost.addHeader("Connection", "keep-alive");
            httpPost.addHeader("Cache-Control", "max-age=0");
            httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
            httpPost.addHeader("Accept-Charset", "zh-GBK,utf-8;q=0.7,*;q=0.3");
        }
    }

    /**
     * GET请求头设置
     *
     * @param method  HTTP请求方法
     * @param headers 头部信息
     */
    private static void setGetHeader(HttpMethod method, Header[] headers) {
        if (null != method && headers != null) {
            for (Header header : headers) {
                method.setRequestHeader(header.getName(), header.getValue());
            }
        }
    }

    /**
     * POST请求头设置
     *
     * @param httpPost HttpPost
     * @param headers  头部信息
     */
    private static void setOtherHeader(HttpPost httpPost, Header[] headers) {
        if (null != httpPost && headers != null) {
            for (Header header : headers) {
                httpPost.addHeader(header);
            }
        }
    }

    /**
     * POST请求 文件信息推送
     *
     * @param url            请求URL
     * @param encode         发送编码格式
     * @param otherHeaders   附加请求头部信息
     * @param fileBodys      数组
     * @param fileName       参数名称
     * @param connectTimeOut 连接超时时间[毫秒]
     * @param soTimeOut      数据传输时间[毫秒]
     * @param requestTimeOut 请求超时时间[毫秒]
     * @return 响应结果
     */
    public static String doPostFile(String url, String encode, Header[] otherHeaders, FileBody[] fileBodys, String fileName, int connectTimeOut, int soTimeOut, int requestTimeOut) {
        HttpPost httpPost = new HttpPost(url);
        // set header
        setDefaultHeader(httpPost, url);
        setOtherHeader(httpPost, otherHeaders);

        //设置页面编码
        if (StringUtils.isEmpty(encode)) {
            //默认UTF-8
            encode = "utf-8";
        }

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(soTimeOut > 0 ? soTimeOut : SO_TIME_OUT).setConnectTimeout(connectTimeOut > 0 ? connectTimeOut : CONN_TIME_OUT)
                .setConnectionRequestTimeout(requestTimeOut > 0 ? requestTimeOut : REQUEST_TIME_OUT).setExpectContinueEnabled(false).build();

        // RequestConfig.DEFAULT
        httpPost.setConfig(requestConfig);

        // 响应内容
        String responseContent = null;
        ThreadLocal<CloseableHttpClient> httpClient = new ThreadLocal<CloseableHttpClient>();
        try {
            HttpEntity entity;
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            for (FileBody fileBody : fileBodys) {
                if (fileBody == null) {
                    continue;
                }
                builder.addPart(StringUtils.isEmpty(fileName) ? "file" : fileName, fileBody);
            }
            entity = builder.build();
            httpPost.setEntity(entity);
            // 执行post请求
            CloseableHttpClient client = HttpConnectionManager.getHttpClient();

            httpClient.set(client);
            HttpResponse httpResponse = httpClient.get().execute(httpPost);

            // 获取响应消息实体
            HttpEntity entityRep = httpResponse.getEntity();
            if (entityRep != null) {
                responseContent = EntityUtils.toString(httpResponse.getEntity(), encode);

                // 获取HTTP响应的状态码
                int statusCode = httpResponse.getStatusLine().getStatusCode();

                if (statusCode == HttpStatus.SC_OK) {

                }
                else if ((statusCode == HttpStatus.SC_MOVED_TEMPORARILY)
                        || (statusCode == HttpStatus.SC_MOVED_PERMANENTLY)
                        || (statusCode == HttpStatus.SC_SEE_OTHER)
                        || (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
                    responseContent = null;
                }
                // Consume response content
                EntityUtils.consume(entityRep);
                // Do not need the rest
                httpPost.abort();
            }
        } catch (Exception e) {
            log.error("POST请求发生系统异常：", e);
        } finally {
            httpPost.releaseConnection();
        }
        return responseContent;
    }

    /**
     * 是否是Ajax请求
     *
     * @param request HttpServletRequest
     * @return true：Ajax请求
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxTime = request.getParameter("ajaxTime");
        return ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) || StringUtils.isNumeric(ajaxTime);
    }

    /**
     * 是否是静态内容访问
     *
     * @param requestUri 请求URI
     * @return true：静态内容访问/false：非静态内容访问
     */
    public static boolean isStaticContentsAccess(String requestUri) {
        if (StringUtils.startsWithIgnoreCase(requestUri, "/static/") || StringUtils.startsWithIgnoreCase(requestUri, "/template/") || StringUtils.startsWithIgnoreCase(requestUri, "/upload/")) {
            return true;
        }
        requestUri = requestUri.toLowerCase();

        if (requestUri.indexOf(".ico") > 0 || requestUri.indexOf(".gif") > 0 || requestUri.indexOf(".png") > 0 || requestUri.indexOf(".jpg") > 0) {
            return true;
        }
        return false;
    }
}
