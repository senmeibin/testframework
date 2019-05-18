package com.bpms.core.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.helpers.BasicMarkerFactory;

import java.util.Objects;

/**
 * 日志标记过滤器
 */
public class LogbackMarkerFilter extends Filter<ILoggingEvent> {
    /**
     * 日志标记
     */
    private String marker;

    /**
     * 标记设置
     *
     * @param marker 日志标记
     */
    public void setMarker(String marker) {
        this.marker = marker;
    }

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (Objects.equals(new BasicMarkerFactory().getMarker(marker), event.getMarker())) {
            return FilterReply.NEUTRAL;
        }
        else {
            return FilterReply.DENY;
        }
    }
}
