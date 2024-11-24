package com.wsc.support;

import io.netty.channel.Channel;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;


/**
 * 
 */
public interface MethodArgumentResolver {

    /**
     * xxx
     * Whether the given {@linkplain MethodParameter method parameter} is
     * supported by this resolver.
     *
     * @param parameter the method parameter to check
     * @return {@code true} if this resolver supports the supplied parameter;
     * {@code false} otherwise
     */
    boolean supportsParameter(MethodParameter parameter);


    /**
     * xxx
     *
     * @param parameter
     * @param channel
     * @param object
     * @return
     * @throws Exception
     */
    @Nullable
    Object resolveArgument(MethodParameter parameter, Channel channel, Object object) throws Exception;

}
