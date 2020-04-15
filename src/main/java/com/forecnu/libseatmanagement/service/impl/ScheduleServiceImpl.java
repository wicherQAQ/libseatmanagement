package com.forecnu.libseatmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forecnu.libseatmanagement.entity.Seat;
import com.forecnu.libseatmanagement.service.ScheduleService;
import com.forecnu.libseatmanagement.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wuwc
 * @version 1.0
 * @date 2020/4/15 15:11
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    SeatService seatService;


    /**
     * 早上7：30执行一次
     */
    @Override
    @Scheduled(cron = "0 30 7 ? * *")
    public void scheduledMorning() {
        resetSeat();
    }

    /**
     * 中午12：30执行一次
     */
    @Override
    @Scheduled(cron = "0 30 12 ? * *")
    public void scheduledAfternoon() {
        resetSeat();
    }


    /**
     * 中午12：00清场一次
     */
    @Override
    @Scheduled(cron = "0 0 12 ? * *")
    public void amRestore() {
        restore();
    }

    /**
     * 晚上11：00清场一次
     */
    @Override
    @Scheduled(cron = "0 0 23 ? * *")
    public void pmRestore() {
        restore();
    }

    void resetSeat(){
        QueryWrapper<Seat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available",false);
        List<Seat> seats = seatService.list(queryWrapper);
        for(Seat seat:seats){
            //将没有及时使用的座位回收
            if(!seat.getCk()){
                seat.setUser_id(null);
                seat.setAvailable(true);
                seatService.updateById(seat);
            }
        }
    }

    void restore(){
        seatService.callBackAllSeats();
    }
}
