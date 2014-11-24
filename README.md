baas
====

目标

支持多客户端 web ios android

高效： 并发连接数1万以上

易扩展： 支持第三方SSO登录
               方便和其他现有系统的互交互
               方便二次开发



与XMPP方案比较的优点：


Netty基于NIO的高效通讯底层性能优于openfire 使用的传统Servlet架构
Redis 的基于内存＋硬盘的会话数据存储 比 openfire的 mysql高效很多
Socket.IO对于浏览器,Websocket的支持 优于openfire 基于事件的异步编程模式 简单高效
基于AKKA的核心并发性能优异，扩展能力强
XMPP 基于xml的协议过重
Openfire整套方案过于笨重，对其改造难度大

Openfire的优点：
整套方案 首次部署难度小
方案比较成熟
