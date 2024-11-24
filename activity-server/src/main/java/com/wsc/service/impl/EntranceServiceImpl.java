package com.wsc.service.impl;

import com.wsc.dto.RequestDTO;
import com.wsc.enums.RequestEnum;
import com.wsc.enums.ServerEnum;
import com.wsc.service.EntranceService;
import com.wsc.vo.ResponseVO;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 
 */
@Service
public class EntranceServiceImpl implements EntranceService {
    @Override
    public ResponseVO<?> operation(Long userId, RequestDTO dto) {
        if (!Objects.equals(ServerEnum.ACTIVITY, ServerEnum.getEnum(dto.getServerName()))) {
            return null;
        }
        //匹配请求
        RequestEnum anEnum = RequestEnum.getEnum(dto.getCode());
        if (anEnum == null) {
            return null;
        }
        //业务处理 在入口处已经做了同步，在此处其实可以不用再次同步
        switch (anEnum) {
            case ONE -> {
                ResponseVO<String> vo = new ResponseVO<>();
                vo.setData("123123");
                return vo;
            }
            case TWO -> {
                ResponseVO<Integer> vo = new ResponseVO<>();
                vo.setData(123);
                return vo;
            }
            default -> {
                System.out.println("处理ddd业务");
                return null;
            }
        }
    }
}
