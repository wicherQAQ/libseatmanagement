package com.forecnu.libseatmanagement.Thread;

import com.forecnu.libseatmanagement.entity.Seat;
import com.forecnu.libseatmanagement.entity.User;
import com.forecnu.libseatmanagement.service.UserService;

import java.util.concurrent.CountDownLatch;

/**
 * @author wuwc
 * @version 1.0
 * @date 2020/4/14 23:40
 */
public class MyThread implements Runnable{
    UserService userService;
    CountDownLatch countDownLatch;
    Integer user_id;
    public MyThread(UserService userService,CountDownLatch countDownLatch,Integer user_id){
        this.userService=userService;
        this.countDownLatch = countDownLatch;
        this.user_id = user_id;
    }

    @Override
    public void run() {
        countDownLatch.countDown();
        User user = new User(user_id,"123456");
        Seat seat = new Seat(2,true,null,1);
        System.out.println("线程："+Thread.currentThread().getName()+"对应的用户："+user.getId()+" 开始占座："+System.currentTimeMillis());
        Boolean result = userService.takeSeat(user,seat);
        if(result){
            System.out.println("用户："+user.getId()+"占座成功");
        }else{
            System.out.println("用户："+user.getId()+"占座失败");
        }
    }
}
