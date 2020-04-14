package com.forecnu.libseatmanagement.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * 为redis设置一些全局配置
 * @author wuwc
 * @version 1.0
 * @date 2020/4/14 18:01
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

}
