# AI 赋能的基于 SSM 框架的智能教学管理系统

#### 摘要

随着学生与教师数量的不断增多，教务管理的容量，安全性，便捷性显得尤为重要。 传统的人工管理的劣势也慢慢显现出来，但是其中的一优点还需要继续采纳，所以传统的人工与计算机的结合成为了目前的主流。对此我们开发了一套基于 SSM 框架的教务管理系统。 
该系统采用的是 Spring、SpringMVC、Mybatis、Shiro、LayUI 等技术。主要实现了用户的登录注册，公告的浏览，选课操作，不同的管理员对不同信息的管理，教师对课程评分，教师结课等功能。 
该系统我在完成基础功能的前提下将测试上线，将 MYSQL 数据库以及 TOMCAT 服务器部署到了云上。并且将该系统打包成 WAR 包装载到了云主机上的 TOMCAT 中。 
同时，我使用 Echart 加入了可视化数据，进行简单的可视化操作。使用了流加载对通知公告进行显示。使用 AI 推理服务增强用户使用体验。

关键词：教学管理系统；JAVA；SSM；云服务器；AI

---

#### 软件架构

项目整体框架：Spring + SpringMVC + Mybatis + Shiro + Layui + Tomcat + VolcEngine sdk 

操作系统：Windows 
数据库：Mysql8.0.16 
服务器：Tomcat8.5.38 
开发工具：IntelliJ IDEA 、Xftp 7 
JDK版本：JDK version 17 (azul zulu, note) 
安全框架：Shiro 
UI框架: Layui 
推理服务：VolcEngine sdk 

注意：你可能需要使用 Azul 提供的 JDK17 来运行该项目！

---

#### 使用说明（云服务器操作仅供参考）

Mysql查看状态：service mysqld status 
Mysql启动：systemctl start mysqld.service

Tomcat启动：tomcat8/bin/startup.sh 
Tomcat结束：tomcat8/bin/shutdown.sh

#### 密码说明

管理员：admin 123456 
学生：zhangsan 123456 
教师：wangliu 123456

