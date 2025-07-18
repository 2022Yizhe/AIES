# AI 赋能的基于 SSM 框架的智能教学管理系统

### 摘要

随着学生与教师数量的不断增多，教学业务的数据智能化管理显得尤为重要。

对此我们在 SSM 框架的教学管理系统的基础上，将大模型嵌入系统，支持用户对话，并且通过意图识别提高了用户对系统的需求定位，旨在提升管理效率，降低系统维护门槛。

该系统采用的是 Spring、SpringMVC、Mybatis、Shiro、LayUI 等技术。主要实现了用户的登录，教学通知的浏览，选课操作，不同的用户对不同信息进行管理，学生进行选课，教师对课程评分和结课等功能。

同时，使用 Echart 加入了可视化数据，进行简单的可视化操作示例；使用了流加载对通知公告进行显示。


关键词：教学系统；JAVA；SSM；云存储；AI

---

### 软件架构

项目整体框架：Spring + SpringMVC + Mybatis + Shiro + Layui + Tomcat + VolcEngine sdk + AliYun OSS


操作系统：Windows 11

数据库：Mysql 8.4.5

服务器：Tomcat 8.5.85

开发工具：IntelliJ IDEA 2024.2.4 、Xftp 7

JDK版本：JDK version 17 (azul zulu, note)

安全框架：Shiro

UI框架: Layui

推理服务：VolcEngine sdk

对象存储服务：AliYun OSS

---

### 注意

你可能需要使用 Azul 提供的 JDK17 来运行该项目！

---

### 使用说明（云服务器操作仅供参考）

Mysql查看状态：service mysqld status 

Mysql启动：systemctl start mysqld.service

Tomcat启动：tomcat8/bin/startup.sh 

Tomcat结束：tomcat8/bin/shutdown.sh

---

### 密码说明

管理员：admin 123456

学生：zhangsan 123456

教师：wangliu 123456

---

### 项目小结

1）请避免采用具有物理外键约束的数据库表设计，因为物理外键约束会限制数据库的并发性，从而导致数据库性能下降。

原因：

在为一个表添加新记录时，添加外键约束会检查该记录所引用的记录是否存在，如果引用的记录不存在，则添加失败。

因此，必须采用事务控制来进行新记录的添加操作，而频繁的事务控制会导致服务器的并发性能下降。

2）将大模型嵌入教学管理系统是一个棘手的课题，既要考虑到权限划分，又要考虑到用户体验。本项目仅采用了用户意图识别，支持自然语言识别并进行各种表单导出（Excel）。

3）将意图识别模块的导出的文件，永久存储在 AliYun OSS，避免了文件丢失。但是可以进一步优化，将会话历史中的导出文件 yrl 提取为单独的表，并且适当设计文件过期时间。

