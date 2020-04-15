package com.forecnu.libseatmanagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.forecnu.libseatmanagement.entity.Seat;

/**
 * @author wuwc
 * @version 1.0
 * @date 2020/4/15 15:13
 */
public interface SeatService extends IService<Seat> {

    /**
     * 回收所有座位
     */
    void callBackAllSeats();
}
