package com.bpms.core.security;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 隐藏手机联系方式 保留前三后四
 */
public class MobileHiddenSerializer extends JsonSerializer {
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String mobile = (String) value;
        String newMobile = mobile.replaceAll("(?<=[\\d]{3})\\d(?=[\\d]{4})", "*");
        gen.writeString(newMobile);
    }
}
