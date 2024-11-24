package com.wsc.support;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.QueryStringDecoder;


/**
 * 
 */
public interface WsPathMatcher {

    /**
     * xxx
     *
     * @return
     */
    String getPattern();

    /**
     * xxx
     *
     * @param decoder
     * @param channel
     * @return
     */
    boolean matchAndExtract(QueryStringDecoder decoder, Channel channel);
}
