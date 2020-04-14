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
public class Seat {
    public Seat(){
    }
    public Seat(Integer id){
        this.id=id;
    }
    public Seat(Integer id,Boolean available,Integer user_id,Integer disk_id){
        this.id=id;
        this.available=available;
        this.user_id=user_id;
        this.disk_id=disk_id;
    }
    @TableId(type=IdType.AUTO)
    private Integer id;
    private Boolean available;
    private Integer user_id;
    private Integer disk_id;
}
