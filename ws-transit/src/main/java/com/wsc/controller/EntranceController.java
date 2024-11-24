package com.wsc.controller;

import cn.hutool.json.JSONUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.wsc.dto.RequestDTO;
import com.wsc.feign.PushFeign;
import com.wsc.proto.Message;
import com.wsc.service.EntranceService;
import com.wsc.vo.ResponseVO;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 消息入口
 *
 * 
 */
@RestController
@RequestMapping("entrance")
@Slf4j
public class EntranceController {


    @Resource
    private EntranceService entranceService;

    @Resource
    private PushFeign pushFeign;

    @PostMapping("{userId}")
    public Message.Data entrance(@PathVariable Long userId, @RequestBody @Valid Message.Data dto) {
        return operation(userId, dto);
    }


    /**
     * 异步消息
     * 不需要实时返回时使用
     *
     * @param userId
     * @param dto
     */
    @Async
    @PostMapping("{socketServerName}/{userId}")
    public void asyncEntrance(@PathVariable String socketServerName, @PathVariable Long userId, @RequestBody @Valid Message.Data dto) {
        Message.Data vo = operation(userId, dto);
        if (Objects.nonNull(vo)) {
            //返回部位空，则发送消息到客户端
            this.pushFeign.pushMessage(socketServerName, userId, vo);
        }
    }

    /**
     * 具体业务处理
     *
     * @param userId
     * @param dto
     * @return
     */
    private Message.Data operation(Long userId, Message.Data dto) {
        try {
            ResponseVO<?> vo = this.entranceService.operation(userId, JSONUtil.toBean(JsonFormat.printer().print(dto), RequestDTO.class));
            if (vo == null) {
                return null;
            }
            return Message.Data.newBuilder().setCode(vo.getCode())
                    .setMessage(vo.getMessage())
                    .setServerName(vo.getServerName())
                    .setData(JSONUtil.toJsonStr(vo.getData()))
                    .build();
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }
}
