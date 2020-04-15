package com.forecnu.libseatmanagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.forecnu.libseatmanagement.entity.Seat;
import com.forecnu.libseatmanagement.entity.User;

/**
 * @author wuwc
 * @version 1.0
 * @date 2020/4/14 17:37
 */
public interface UserService extends IService<User> {

    /**
     * 占座
     * @param user 学生信息
     * @param seat 座位信息
     * @return
     */
    Boolean takeSeat(User user, Seat seat);

    /**
     * 学生确认使用接口
     * @param user 学生信息
     * @param seat 座位信息
     */
    /**
     * 学生确认使用接口
     * @param user 学生信息
     * @param seat 座位信息
     * @return 返回信息
     */
    String checkFor(User user, Seat seat);

}
