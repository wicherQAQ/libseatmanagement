## 基本信息

&emsp;&emsp;项目使用 Spring Boot 搭建, 用于加深对 Spring Boot 与 高并发场景下处理请求的学习, 项目主要应用场景是对高校图书馆座位的使用进行管理。随着考研人数越来越多，图书馆的座位也变得炙手可热。该项目通过将座位的使用分为上下午两场，通过系统预约的方式使用座位。并且利用同步和redis缓存合理的处理了存在某一时刻内对同一个位置的**秒杀抢占**的并发场景。小小项目，就当作为可能要找工作提前做准备了。

## 运行环境

* JDK 1.8
* MySQL  8.0.11
* Redis

## 数据库表

&emsp;&emsp;项目所用到的数据表如下：学生和座位是1：1的关系；座位和桌子是1：n的关系；桌子和区域是n：1的关系

```
//学生表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '123456',
  PRIMARY KEY (`id`) USING BTREE
);

//座位表
DROP TABLE IF EXISTS `seat`;
CREATE TABLE `seat`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `available` tinyint(1) NOT NULL DEFAULT 1,
  `user_id` int(11) NULL DEFAULT NULL,
  `disk_id` int(11) NOT NULL,
  `ck` tinyint(1) NULL DEFAULT 0 COMMENT '确认使用',
  PRIMARY KEY (`id`) USING BTREE
);

//桌子表
DROP TABLE IF EXISTS `disk`;
CREATE TABLE `disk`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `area_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
); 

//区域表
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_num` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门牌号',
  `floor` int(5) NULL DEFAULT NULL COMMENT '所在楼层',
  `describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址描述',
  PRIMARY KEY (`id`) USING BTREE
);
```
