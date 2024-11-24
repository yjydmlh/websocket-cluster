package com.wsc.listener;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.wsc.dto.UserServerDTO;
import com.wsc.enums.DeviceEnum;
import com.wsc.socket.WebSocket;
import com.wsc.util.WebSocketUtil;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * 多服务挤下线
 *
 * @author 10263
 */
@Component
public class WsUserServerCloseListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        UserServerDTO wsUser = JSONUtil.toBean(message.toString(), UserServerDTO.class);
        Map<DeviceEnum, WebSocket> webSocketMap = WebSocketUtil.get(wsUser.getUserId());
        //不是同个客户端，挤下线
        if (Objects.nonNull(webSocketMap) && StrUtil.isNotBlank(wsUser.getServerName()) && !Objects.equals(wsUser.getServerName(), System.getProperty("SpringApplicationName"))) {
            WebSocket webSocket = webSocketMap.get(DeviceEnum.getEnum(wsUser.getDevice()));
            if (Objects.nonNull(webSocket)) {
                webSocket.getSession().close();
            }
        }
    }

}
