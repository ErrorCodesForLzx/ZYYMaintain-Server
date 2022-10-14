[中文](README.md)
# ZYYMaintain-Server
中医院设备管理系统服务端  

# 当前功能
* 使用SpringBoot
* 使用Mybatis持久层框架
* 使用MP框架
* 使用MVC设计概型
* MySQL DBMS
# 未来功能
* 修补 BUG
* 添加WEB控制界面
* 改善通知接口
* 增加WebSocket并发量
# 如何使用？（方法一）
1. 克隆本项目
2. 安装[MySQL](https://www.mysql.com/)数据库
3. 执行源码里面的数据库脚本：`source data-script.sql`，或使用宝塔面板导入
4. 执行`mvn package -f pom.xml`，编译jar包。
5. java -jar
# 版本更新
* 修复文件过大上传失败的问题
* 添加时间线功能
* 完善数据库关系