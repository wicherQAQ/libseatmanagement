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
public class Area {
    @TableId(type=IdType.AUTO)
    private Integer id;
    private String room_num;
    private Integer floor;
    private String describe;
}
