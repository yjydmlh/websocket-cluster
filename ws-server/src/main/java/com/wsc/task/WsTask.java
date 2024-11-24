package com.wsc.task;


import cn.hutool.json.JSONUtil;
import com.wsc.enums.WebSocketEnum;
import com.wsc.proto.Message;
import com.wsc.util.WebSocketUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 *
 * 
 */
@Component
public class WsTask {

    /**
     * socket心跳
     * 每5秒执行一次
     */
    @Scheduled(fixedDelay = 5000)
    public void heartbeatTask() {
        WebSocketEnum heartbeat = WebSocketEnum.HEARTBEAT;
        Message.Data.Builder demo = Message.Data.newBuilder();
        demo.setCode(heartbeat.getCode())
                .setMessage(heartbeat.getMessage())
                .setServerName(heartbeat.getServerName())
                .setData(JSONUtil.toJsonStr(heartbeat.getData()))
                .build();
        WebSocketUtil.sendMessage(demo.build());
    }
}
