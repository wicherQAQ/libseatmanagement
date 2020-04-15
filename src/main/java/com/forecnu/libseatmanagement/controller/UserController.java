package com.forecnu.libseatmanagement.controller;

import com.forecnu.libseatmanagement.entity.Seat;
import com.forecnu.libseatmanagement.entity.User;
import com.forecnu.libseatmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wuwc
 * @version 1.0
 * @date 2020/4/14 19:35
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/take")
    public String takeSeat(){
        User user = new User(1);
        Seat seat = new Seat(2);
        Boolean result = userService.takeSeat(user,seat);
        if(result){
            System.out.println("占座成功");
            return "success!";
        }else{
            System.out.println("占座失败");
            return "failed!";
        }
    }



}
