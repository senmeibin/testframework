package com.bpms.core.exception;

import com.bpms.core.entity.AjaxResult;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class ServiceExceptionResolver extends ExceptionHandlerExceptionResolver {

    private Logger log = LoggerFactory.getLogger(ServiceExceptionResolver.class);

    @Override
    protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Exception exception) {
        if (handlerMethod == null) {
            return null;
        }

        Method method = handlerMethod.getMethod();

        if (method == null) {
            return null;
        }
        if (exception != null) {
            if (exception instanceof ServiceValidationException) {
                log.warn("服务端验证异常", exception);
            }
            else {
                log.error("出现系统异常", exception);
            }
        }
        // 如果定义了ExceptionHandler则返回相应的Map中的数据
        ModelAndView returnValue = super.doResolveHandlerMethodException(request, response, handlerMethod, exception);
        ResponseBody responseBodyAnn = AnnotationUtils.findAnnotation(method, ResponseBody.class);
        if (responseBodyAnn != null) {
            try {
                ResponseStatus responseStatusAnn = AnnotationUtils.findAnnotation(method, ResponseStatus.class);
                if (responseStatusAnn != null) {
                    HttpStatus responseStatus = responseStatusAnn.value();
                    String reason = responseStatusAnn.reason();
                    if (!StringUtils.hasText(reason)) {
                        response.setStatus(responseStatus.value());
                    }
                    else {
                        try {
                            response.sendError(responseStatus.value(), reason);
                        } catch (IOException ignored) {
                        }
                    }
                }
                // 如果没有ExceptionHandler注解那么returnValue就为空
                if (returnValue == null) {
                    AjaxResult res = AjaxResult.createFailResult(exception.getLocalizedMessage());
                    //系统级别异常
                    if (!(exception instanceof ServiceException) && !(exception instanceof ServiceValidationException)) {
                        //系统异常标识
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        exception.printStackTrace(pw);
                        res.setMessage("SYSTEM_ERROR_FLAG:" + sw.toString());

                        IOUtils.closeQuietly(pw);
                        IOUtils.closeQuietly(sw);
                    }
                    handleResponseError(res, request, response);
                    return new ModelAndView();
                }
                return handleResponseBody(returnValue, request, response);
            } catch (Exception e) {
                return null;
            }
        }
        else {
            if (exception instanceof org.apache.shiro.authz.AuthorizationException) {
                //跳转未授权页面
                return new ModelAndView("error/401");
            }
        }

        if (null == returnValue) {
            returnValue = new ModelAndView();
            if (null == returnValue.getViewName()) {
                returnValue.setViewName("error/500");
            }
        }
        return returnValue;
    }

    @SuppressWarnings({"rawtypes"})
    private ModelAndView handleResponseBody(ModelAndView returnValue, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map value = returnValue.getModelMap();
        HttpInputMessage inputMessage = new ServletServerHttpRequest(request);
        List<MediaType> acceptedMediaTypes = inputMessage.getHeaders().getAccept();
        if (acceptedMediaTypes.isEmpty()) {
            acceptedMediaTypes = Collections.singletonList(MediaType.ALL);
        }
        MediaType.sortByQualityValue(acceptedMediaTypes);
        HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);
        Class<?> returnValueType = value.getClass();
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
        return makeReturnView(value, acceptedMediaTypes, outputMessage, returnValueType, messageConverters);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private ModelAndView makeReturnView(Object value, List<MediaType> acceptedMediaTypes, HttpOutputMessage outputMessage, Class<?> returnValueType, List<HttpMessageConverter<?>> messageConverters)
            throws IOException {
        if (messageConverters != null) {
            for (MediaType acceptedMediaType : acceptedMediaTypes) {
                for (HttpMessageConverter messageConverter : messageConverters) {
                    if (messageConverter.canWrite(returnValueType, acceptedMediaType)) {
                        messageConverter.write(value, acceptedMediaType, outputMessage);
                        return new ModelAndView();
                    }
                }
            }
        }
        if (log.isWarnEnabled()) {
            log.warn("Could not find HttpMessageConverter that supports return type [" + returnValueType + "] and " + acceptedMediaTypes);
        }
        return null;
    }

    private ModelAndView handleResponseError(AjaxResult returnValue, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpInputMessage inputMessage = new ServletServerHttpRequest(request);
        List<MediaType> acceptedMediaTypes = inputMessage.getHeaders().getAccept();
        if (acceptedMediaTypes.isEmpty()) {
            acceptedMediaTypes = Collections.singletonList(MediaType.ALL);
        }
        MediaType.sortByQualityValue(acceptedMediaTypes);
        HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);
        Class<?> returnValueType = returnValue.getClass();
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
        return makeReturnView(returnValue, acceptedMediaTypes, outputMessage, returnValueType, messageConverters);
    }
}
