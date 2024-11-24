package com.wsc.listener;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.wsc.dto.UserServerDTO;
import com.wsc.util.LocalCache;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * 用户服务绑定通知
 */
@Component
public class UserServerListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        UserServerDTO wsUser = JSONUtil.toBean(message.toString(), UserServerDTO.class);
        if (StrUtil.isBlank(wsUser.getServerName())) {
            LocalCache.userServer.remove(wsUser.getUserId() + wsUser.getServer());
        } else {
            LocalCache.userServer.put(wsUser.getUserId() + wsUser.getServer(), wsUser.getServerName());
        }
    }

}
