package com.forecnu.libseatmanagement.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forecnu.libseatmanagement.dao.SeatMapper;
import com.forecnu.libseatmanagement.dao.UserMapper;
import com.forecnu.libseatmanagement.entity.Seat;
import com.forecnu.libseatmanagement.entity.User;
import com.forecnu.libseatmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wuwc
 * @version 1.0
 * @date 2020/4/14 17:38
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {


    private static final String HEAD = "SEAT_PREFIX_";

    //注入模式单例的，普通属性和静态属性都会被共用。
    @Autowired
    private SeatMapper seatMapper;

    /**
     * redis模板引擎
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final Lock lock = new ReentrantLock();

    /**
     * 占座接口，测试实际可支持4000+的并发量
     * 可完美支持普通高效的占座需求
     * 多线程副本执行代码段，
     * @param user 学生信息
     * @param seat 座位信息
     * @return
     */
    @Override
    public Boolean takeSeat(User user, Seat seat) {
        //先到redis中排队，设置1000毫秒有效时长,过滤同一时间的大量并发
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(HEAD + seat.getId(), user.getId().toString(), Duration.ofMillis(30000));
        if(result){
            //同一时间，只能有一个线程进来修改
            //synchronized (seatMapper){
            /*try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            Seat st = seatMapper.selectById(seat.getId());
            //判断是否已经被抢走了
            if(!st.getAvailable()){
                stringRedisTemplate.delete(HEAD + seat.getId());
                return false;
            }
            st.setUser_id(user.getId());
            st.setAvailable(false);
            seatMapper.updateById(st);
            stringRedisTemplate.delete(HEAD + seat.getId());
            return true;
            //}
        }else{
            return false;
        }
    }

    @Override
    public String checkFor(User user, Seat seat) {
        String backMsg = "success";
        Seat st = seatMapper.selectById(seat.getId());
        if(st.getAvailable()&&st.getUser_id()==null){
            backMsg = "this seat is available";
        }else if(!user.getId().equals(seat.getUser_id())){
            backMsg = "this seat has been ordered";
        }else{
            st.setCk(true);
            seatMapper.updateById(st);
        }
        return backMsg;
    }
}
