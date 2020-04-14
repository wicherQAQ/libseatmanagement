package com.forecnu.libseatmanagement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wuwc
 * @version 1.0
 * @date 2020/4/14 17:26
 */
@Getter
@Setter
public class User {
    public User(){
    }
    public User(Integer id){
        this.id=id;
    }
    public User(Integer id,String password){
        this.id=id;
        this.password=password;
    }
    @TableId(type=IdType.AUTO)
    private Integer id;
    private String password;
}
