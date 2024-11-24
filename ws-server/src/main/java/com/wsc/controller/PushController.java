package com.wsc.controller;

import com.wsc.dto.MessageDTO;
import com.wsc.proto.Message;
import com.wsc.util.WebSocketUtil;
import com.wsc.vo.R;
import com.wsc.vo.ResponseVO;
import org.springframework.web.bind.annotation.*;

/**
 * 推送
 *
 * 
 */
@RestController
@RequestMapping("push")
public class PushController {


    @PostMapping("{userId}")
    public ResponseVO<Void> pushMessage(@PathVariable Long userId, @RequestBody Message.Data vo) {
        WebSocketUtil.sendMessage(userId, vo);
        return R.success();
    }

    /**
     * 群发消息
     *
     * @param vo
     */
    @PostMapping()
    public ResponseVO<Void> pushMessage(@RequestBody Message.Data vo) {
        WebSocketUtil.sendMessage(vo);
        return R.success();
    }

    /**
     * 批量发送消息，直接把用户传给ws，减少feign调用
     *
     * @param dto
     */
    @PostMapping("batch")
    public ResponseVO<Void> pushBatchMessage(@RequestBody MessageDTO dto) {
        WebSocketUtil.sendMessage(dto);
        return R.success();
    }
}
