package com.forecnu.libseatmanagement.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forecnu.libseatmanagement.dao.SeatMapper;
import com.forecnu.libseatmanagement.dao.UserMapper;
import com.forecnu.libseatmanagement.entity.Seat;
import com.forecnu.libseatmanagement.entity.User;
import com.forecnu.libseatmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

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

    /**
     * redis模板引擎
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 占座接口，测试实际可支持4000+的并发量
     * 可完美支持普通高效的占座需求
     * @param user 学生信息
     * @param seat 座位信息
     * @return
     */
    @Override
    public Boolean takeSeat(User user, Seat seat) {
        //同步代码块，保证高并发环境下的原子性
        synchronized (seatMapper){
            Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(HEAD + seat.getId(), user.getId().toString(), Duration.ofMillis(200));
            if(result){
                //如果设置成功，则可以抢
                Seat st = seatMapper.selectById(seat.getId());
                //判断是否已经被抢走了
                if(!st.getAvailable()){
                    return false;
                }
                st.setUser_id(user.getId());
                st.setAvailable(false);
                int back = seatMapper.updateById(st);
                return back==1?true:false;
            }else{
                //设置失败说明已经有人抢到了
                return false;
            }
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
