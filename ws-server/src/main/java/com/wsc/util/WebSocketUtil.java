package com.wsc.util;

import com.wsc.dto.MessageDTO;
import com.wsc.enums.DeviceEnum;
import com.wsc.proto.Message;
import com.wsc.socket.WebSocket;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket操作类
 *
 * 
 */
public class WebSocketUtil {

    /**
     * 存放用户信息
     */
    private static final ConcurrentHashMap<Long, ConcurrentHashMap<DeviceEnum, WebSocket>> WEB_SOCKET_MAP = new ConcurrentHashMap<>(16);


    /**
     * 存储缓存关系
     *
     * @param userId
     * @param webSocket
     */
    public static void putMap(Long userId, WebSocket webSocket, DeviceEnum deviceEnum) {
        ConcurrentHashMap<DeviceEnum, WebSocket> webSocketMap = get(userId);
        if (Objects.isNull(webSocketMap)) {
            webSocketMap = new ConcurrentHashMap<>(16);
        }
        webSocketMap.put(deviceEnum, webSocket);
        WEB_SOCKET_MAP.put(userId, webSocketMap);
    }

    /**
     * 删除缓存
     *
     * @param userId
     */
    public static void removeMap(Long userId, String device, String uuid) {
        DeviceEnum deviceEnum = DeviceEnum.getEnum(device);
        ConcurrentHashMap<DeviceEnum, WebSocket> webSocketMap = get(userId);
        if (Objects.nonNull(webSocketMap) && Objects.nonNull(deviceEnum)) {
            WebSocket webSocket = webSocketMap.get(deviceEnum);
            if (Objects.nonNull(webSocket) && Objects.equals(webSocket.getUuid(), uuid)) {
                webSocketMap.remove(deviceEnum);
            }
        }
    }


    /**
     * 批量发送消息
     *
     * @param dto
     */
    public static void sendMessage(MessageDTO dto) {
        dto.getUserId().forEach(e -> {
            Map<DeviceEnum, WebSocket> webSocketMap = get(e);
            if (Objects.nonNull(webSocketMap)) {
                webSocketMap.forEach((k, v) -> sendMessage(v, dto.getData()));
            }
        });
    }

    /**
     * 实现服务器主动推送
     *
     * @param userId
     * @param vo
     * @return
     */
    public static void sendMessage(Long userId, Message.Data vo) {
        Map<DeviceEnum, WebSocket> webSocketMap = get(userId);
        if (Objects.nonNull(webSocketMap)) {
            webSocketMap.forEach((k, v) -> sendMessage(v, vo));
        }
    }

    /**
     * 查询用户信息
     *
     * @param userId
     * @return
     */
    public static ConcurrentHashMap<DeviceEnum, WebSocket> get(Long userId) {
        return WEB_SOCKET_MAP.get(userId);
    }


    /**
     * 群发消息
     *
     * @param vo
     */
    public static void sendMessage(Message.Data vo) {
        WEB_SOCKET_MAP.forEach((k, v) -> v.forEach((k1, v2) -> sendMessage(v2, vo)));
    }

    /**
     * 发送消息
     *
     * @param webSocket
     * @param vo
     */
    private static void sendMessage(WebSocket webSocket, Message.Data vo) {
        //转换成字节数组
        webSocket.getSession().sendBinary(vo.toByteArray());
    }
}
