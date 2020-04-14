package com.forecnu.libseatmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forecnu.libseatmanagement.dao.SeatMapper;
import com.forecnu.libseatmanagement.dao.UserMapper;
import com.forecnu.libseatmanagement.entity.Seat;
import com.forecnu.libseatmanagement.entity.User;
import com.forecnu.libseatmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @author wuwc
 * @version 1.0
 * @date 2020/4/14 17:38
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    private static final String HEAD = "SEAT_PREFIX_";
    @Autowired
    private SeatMapper seatMapper;
    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public Boolean takeSeat(User user, Seat seat) {

        if(redisTemplate.hasKey(HEAD+seat.getId())){
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //上占座锁
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        //占座的时间有200毫秒，失效后没有占好座则占座失败
        operations.set(HEAD+seat.getId(),user.getId().toString(),20000,TimeUnit.MILLISECONDS );

        Seat st = seatMapper.selectById(seat.getId());
        if(st.getAvailable()){
            //根据seat的id于redis中注册锁，若锁已经存在则继续等待或者放弃
            st.setUser_id(user.getId());
            st.setAvailable(false);
            seatMapper.updateById(st);
            //释放占座锁
            redisTemplate.delete(HEAD+seat.getId());
            return true;
        }

        return false;
    }
}
