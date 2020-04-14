package com.forecnu.libseatmanagement.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forecnu.libseatmanagement.dao.SeatMapper;
import com.forecnu.libseatmanagement.entity.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wuwc
 * @version 1.0
 * @date 2020/4/15 0:56
 */
@Service
public class SchedulService {
    @Autowired
    SeatMapper seatMapper;

    /**
     * 早上7：30执行一次
     */
    @Scheduled(cron = "0 30 7 * * ? *")
    void scheduledMorning(){
        resetSeat();
    }

    /**
     * 中午12：30执行一次
     */
    @Scheduled(cron = "0 30 12 * * ? *")
    void scheduledAfternoon(){
        resetSeat();
    }

    void resetSeat(){
        QueryWrapper<Seat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available",false);
        List<Seat> seats = seatMapper.selectList(queryWrapper);
        for(Seat seat:seats){
            //将没有及时使用的座位回收
            if(!seat.getCheck()){
                seat.setUser_id(null);
                seat.setAvailable(true);
                seatMapper.updateById(seat);
            }
        }
    }

}
