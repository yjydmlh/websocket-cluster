package com.wsc.service;

import cn.hutool.json.JSONUtil;
import com.wsc.constants.MqTopicConstants;
import com.wsc.dto.UserServerDTO;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 用户服务关系建立与解除
 *
 * @author 10263
 */
@Service
public class UserMqServer {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 解除关系
     *
     * @param userId 用户ID
     * @param server 服务
     */
    public void relieve(Long userId, String server) {
        UserServerDTO dto = new UserServerDTO();
        dto.setUserId(userId);
        dto.setServer(server);
        this.stringRedisTemplate.convertAndSend(MqTopicConstants.USER_SERVER_NAME, JSONUtil.toJsonStr(dto));
    }

    /**
     * 建立关系
     *
     * @param userId     用户ID
     * @param server     服务
     * @param serverName 服务名
     */
    public void build(Long userId, String server, String serverName) {
        UserServerDTO dto = new UserServerDTO();
        dto.setUserId(userId);
        dto.setServer(server);
        dto.setServerName(serverName);
        this.stringRedisTemplate.convertAndSend(MqTopicConstants.USER_SERVER_NAME, JSONUtil.toJsonStr(dto));
    }
}
