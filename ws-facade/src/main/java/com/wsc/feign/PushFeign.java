package com.wsc.feign;

import com.wsc.config.DynamicRoutingConfig;
import com.wsc.dto.MessageDTO;
import com.wsc.proto.Message;
import com.wsc.vo.ResponseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 
 */
@FeignClient(name = "pushFeign", configuration = DynamicRoutingConfig.class)
public interface PushFeign {

    /**
     * 推送消息
     *
     * @param serviceName 服务名
     * @param userId      用户
     * @param vo          消息体
     */
    @PostMapping(value = "//{serviceName}/push/{userId}", consumes = MediaType.APPLICATION_PROTOBUF_VALUE, produces = MediaType.APPLICATION_PROTOBUF_VALUE)
    ResponseVO<Void> pushMessage(@PathVariable("serviceName") String serviceName, @PathVariable("userId") Long userId, @RequestBody Message.Data vo);

    /**
     * 群发
     *
     * @param serviceName 服务名
     * @param vo          消息体
     */
    @PostMapping(value = "//{serviceName}/push", consumes = MediaType.APPLICATION_PROTOBUF_VALUE, produces = MediaType.APPLICATION_PROTOBUF_VALUE)
    ResponseVO<Void> pushMessage(@PathVariable("serviceName") String serviceName, @RequestBody Message.Data vo);

    /**
     * 群发
     *
     * @param serviceName 服务名
     * @param dto         消息体
     */
    @PostMapping(value = "//{serviceName}/push/batch", consumes = MediaType.APPLICATION_PROTOBUF_VALUE, produces = MediaType.APPLICATION_PROTOBUF_VALUE)
    ResponseVO<Void> pushBatchMessage(@PathVariable("serviceName") String serviceName, @RequestBody MessageDTO dto);

}
