package com.wsc.dto;

import com.wsc.proto.Message;
import lombok.Data;

import java.util.Set;

/**
 * @author Administrator
 */
@Data
public class MessageDTO {

    /**
     * 用户列表
     */
    private Set<Long> userId;

    /**
     * 数据
     */
    private Message.Data data;
}
