package com.wsc.support;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.Objects;


/**
 * 
 */
public class DefaultPathMatcher implements WsPathMatcher {

    private final String pattern;

    public DefaultPathMatcher(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return this.pattern;
    }

    @Override
    public boolean matchAndExtract(QueryStringDecoder decoder, Channel channel) {
        return pattern.equals(decoder.path());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DefaultPathMatcher point)) {
            return false;
        }
        return Objects.equals(pattern, point.getPattern());
    }

    @Override
    public int hashCode() {
        return Objects.hash(pattern);
    }
}
