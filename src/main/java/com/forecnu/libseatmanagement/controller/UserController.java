package com.forecnu.libseatmanagement.controller;

import com.forecnu.libseatmanagement.entity.Seat;
import com.forecnu.libseatmanagement.entity.User;
import com.forecnu.libseatmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

/**
 * @author wuwc
 * @version 1.0
 * @date 2020/4/14 19:35
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final String HEAD = "SEAT_PREFIX_";

    @Autowired
    private UserService userService;

    /**
     * redis模板引擎
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/take")
    public String takeSeat(){
        User user = new User(1);
        Seat seat = new Seat(2);

        //先到redis中排队，设置1000毫秒有效时长,过滤同一时间的大量并发
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(HEAD + seat.getId(), user.getId().toString(), Duration.ofMillis(30000));
        if(result){
            if(userService.takeSeat(user,seat)){
                System.out.println("占座成功");
                stringRedisTemplate.delete(HEAD + seat.getId());
                return "success!";
            }else{
                System.out.println("占座失败");
                stringRedisTemplate.delete(HEAD + seat.getId());
                return "failed!";
            }
        }else{
            System.out.println("占座失败");
            return "failed!";
        }
    }



}
