package com.wsc.support;

import com.wsc.pojo.PojoEndpointServer;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.springframework.util.AntPathMatcher;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 
 */
public class AntPathMatcherWrapper extends AntPathMatcher implements WsPathMatcher {

    private final String pattern;

    public AntPathMatcherWrapper(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return this.pattern;
    }

    @Override
    public boolean matchAndExtract(QueryStringDecoder decoder, Channel channel) {
        Map<String, String> variables = new LinkedHashMap<>();
        boolean result = doMatch(pattern, decoder.path(), true, variables);
        if (result) {
            channel.attr(PojoEndpointServer.URI_TEMPLATE).set(variables);
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AntPathMatcherWrapper point)) {
            return false;
        }
        return Objects.equals(pattern, point.getPattern());
    }

    @Override
    public int hashCode() {
        return Objects.hash(pattern);
    }
}
