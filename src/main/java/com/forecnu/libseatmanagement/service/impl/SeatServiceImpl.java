package com.forecnu.libseatmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forecnu.libseatmanagement.dao.SeatMapper;
import com.forecnu.libseatmanagement.entity.Seat;
import com.forecnu.libseatmanagement.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wuwc
 * @version 1.0
 * @date 2020/4/15 15:14
 */
@Service
public class SeatServiceImpl extends ServiceImpl<SeatMapper,Seat> implements SeatService {

    @Autowired
    private SeatMapper seatMapper;

    @Override
    public void callBackAllSeats() {
        QueryWrapper<Seat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available",false);
        List<Seat> seats = seatMapper.selectList(queryWrapper);
        for(Seat seat:seats) {
            seat.setCk(false);
            seat.setUser_id(null);
            seat.setAvailable(true);
            seatMapper.updateById(seat);
        }
    }
}
