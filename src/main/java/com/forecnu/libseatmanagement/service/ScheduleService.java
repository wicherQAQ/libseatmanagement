package com.forecnu.libseatmanagement.service;

/**
 * 定时任务接口
 * 用于回收座位
 * @author wuwc
 * @version 1.0
 * @date 2020/4/15 0:56
 */
public interface ScheduleService {

    /**
     * 早上7：30执行一次
     */
    void scheduledMorning();

    /**
     * 中午12：30执行一次
     */
    void scheduledAfternoon();

    /**
     *  AM restore
     */
    void amRestore();

    /**
     * PM restore
     */
    void pmRestore();

}
