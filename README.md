# 水星智慧平台

该项目是基于Spring Boot + Gateway + Redis + Elastic Search + Netty + RabbitMQ的编程帖子分享平台，分为网关、用户、帖子、搜索4个微服务。用户模块实现了标签系统和好友推荐、关注、私聊、消息通知等功能；帖子模块实现了发布、推送、点赞、收藏、评论等功能；搜索模块实现了帖子、用户和站外信息的聚合搜索。

## 目录

- [介绍](https://github.com/qk-mercury/mercury-blog-backend/blob/master/README.md)
- [用户服务](https://github.com/qingshan2022/Mercury-Smart-Platform-backend/blob/main/doc/USER_MODULE.md)
- [帖子服务](https://github.com/qingshan2022/Mercury-Smart-Platform-backend/blob/main/doc/ARTICLE_MODULE.md)
- [搜索服务](https://github.com/qingshan2022/Mercury-Smart-Platform-backend/blob/main/doc/SEARCH_MODULE.md)
- [部署相关](https://github.com/qingshan2022/Mercury-Smart-Platform-backend/blob/main/doc/DEPLOY.md)

## 技术栈

### 后端

- Spring Cloud Gateway：
  1. 最简单的应用，将请求转发到不同的微服务
- Spring Boot：
  1. AOP切面编程搭配自定义异常来对异常做统一处理；
  2. 定时任务（计算文章得分并刷新热榜，每日刷新推荐用户，定时将文章浏览量从redis同步到数据库）
  3. 使用validation相关注解对请求的参数进行校验
- MySQL
  1. 数据持久化，项目涉及到了极少量复杂查询
- Redis：
  1. 缓存，包括热点文章，文章点赞、收藏、浏览量，推荐用户，消息通知数等。其中文章点赞、收藏、浏览量、消息通知是永久存储的（结合OpenResty的lua脚本，把查询缓存的逻辑前置到nginx，进一步提高响应速度）
  2. 分布式锁（只用到了定时任务）
- Elastic Search：
  1. 搭配Jsoup爬虫，实现了聚合搜索功能
  2. 搭配canal实现MySQL和ES的数据同步
- Netty：
  1. 私聊（消息持久化、离线消息、消息通知，在线聊天过程中切换对话，接收不同对话的消息）
- RabbitMQ：
  1. 最简单的应用，异步处理点赞、收藏、关注、发邮件等消息，提高响应速度。只用到了直接交换机。
- Nginx：
  1. 反向代理服务器
  2. lua脚本
- 其他：
  1. CompletableFuture异步编程
  2. 余弦相似度算法

---

### 前端

- React+umijs4+ant design pro

---

## 功能模块

### 1. 用户模块

#### 1.1 登录、注册

- OAuth2第三方登录
- 实现了短信和邮件注册服务（短信是榛子云，要花钱，测试一下后面就关了）
- 使用token作为会话保持的途径（存放在cookie里）

![](https://web-hehe-wocao.oss-cn-beijing.aliyuncs.com/shuixinfor/image-20240405233542789.png)

#### 1.2 用户信息

- 七牛云OSS存储用户上传的头像

- 个人标签，支持新增标签，但不支持新增分类

- 支持账号密码和邮箱/电话绑定

- 用户中心（修改/删除以前发布的帖子）也可以查看自己的文章、收藏夹、关注、粉丝信息

  ![](https://web-hehe-wocao.oss-cn-beijing.aliyuncs.com/shuixinfor/image-20240405233755316.png)



#### 1.3 用户推荐

![](https://web-hehe-wocao.oss-cn-beijing.aliyuncs.com/shuixinfor/image-20240405233610538.png)

相似度计算基于余弦相似度，所有标签都具备相同的权重。

这个推荐计算相对消耗时间，写成定时任务后每24小时刷新到redis中/主动点击刷新获取新的推荐用户。

#### 1.4 关注功能

![](https://web-hehe-wocao.oss-cn-beijing.aliyuncs.com/shuixinfor/image-20240405234122923.png)

#### 1.5 聊天功能

![](https://web-hehe-wocao.oss-cn-beijing.aliyuncs.com/shuixinfor/image-20240405233902386.png)

实现了消息持久化、保留离线消息、滚动分页历史消息、在线消息通知。左侧可以搜索用户，在线过程中可以正确处理来自多个用户的消息（例如处理来自新对话的新消息，刷新对话的未读部分和最新消息部分）

#### 1.6 动态推荐功能

![](https://web-hehe-wocao.oss-cn-beijing.aliyuncs.com/shuixinfor/image-20240405233921932.png)

关注用户的帖子将作为动态推荐给本地用户

### 2. 帖子模块

#### 2.1 创建帖子

![](https://web-hehe-wocao.oss-cn-beijing.aliyuncs.com/shuixinfor/image-20240405233825912.png)

- 可以给文章配图，也支持自定义标签


#### 2.2 在线编辑帖子

使用了md-editor-rt这个组件库来提供在线编辑功能，同时用户也可以在创建笔记后对笔记的标题、摘要、缩略图等信息进行修改。

![](https://web-hehe-wocao.oss-cn-beijing.aliyuncs.com/shuixinfor/image-20240405233625586.png)

#### 2.3 帖子浏览

- 分页查询帖子：

  ![](https://web-hehe-wocao.oss-cn-beijing.aliyuncs.com/shuixinfor/image-20240405234318259.png)

- 帖子动态，本质还是分页查询，只不过查询的条件是在自己关注的用户

  全部动态：

  ![](https://web-hehe-wocao.oss-cn-beijing.aliyuncs.com/shuixinfor/image-20240405234036849.png)

  单个用户的动态：

  ![](https://web-hehe-wocao.oss-cn-beijing.aliyuncs.com/shuixinfor/image-20240405234057631.png)

帖子是整个系统的核心，因此在设计过程中大量使用了Redis缓存来优化查询。

#### 2.4 帖子点赞

![](https://web-hehe-wocao.oss-cn-beijing.aliyuncs.com/shuixinfor/image-20240405233610538.png)

如图所示，用户可以对帖子点赞，并且点赞记录使用set存储在redis中的，因此单个用户的点赞仅算做一次。

#### 2.5 帖子收藏

提供了类似B站的收藏夹系统，一篇笔记可以放在多个收藏夹内，用户可以将笔记在收藏夹中自由移动，当这篇笔记在用户的所有收藏夹内都删除时，笔记的收藏数-1

header也提供了访问文件夹的方式（分页的）：

![](https://web-hehe-wocao.oss-cn-beijing.aliyuncs.com/shuixinfor/image-20240405233938920.png)

#### 2.6 帖子评论

评论系统也参考了B站，默认展示根评论，当点击展开回复查看子评论，支持子评论和更次级的评论

![](https://web-hehe-wocao.oss-cn-beijing.aliyuncs.com/shuixinfor/image-20240405234014865.png)

#### 2.7 热点帖子和全局置顶帖子

文章有score和hot两个属性，前者取决于文章的总浏览量、收藏量、点赞量等，而后者取决于前一个时间段的hot和这个时间段内score的增加值。启动一个定时任务每隔1小时来刷新score和hot，并把热点帖子预热到redis缓存中。全局置顶帖子也采用这个方式去刷新，否则管理员也无法更换全局置顶帖子。

#### 2.8 通知功能

包括点赞、评论和消息通知

![](https://web-hehe-wocao.oss-cn-beijing.aliyuncs.com/shuixinfor/image-20240405233841710.png)

### 3. 聚合搜索模块

帖子和用户搜索支持关键词+标签

帖子：

![](https://web-hehe-wocao.oss-cn-beijing.aliyuncs.com/shuixinfor/image-20240405233707272.png)

用户：

![](https://web-hehe-wocao.oss-cn-beijing.aliyuncs.com/shuixinfor/image-20240405234122923.png)

利用Jsoup爬虫，可以获取站外搜索结果（博客园做了防爬虫处理，搜索要通过验证，然后返回cookie，该cookie有一定期限）

![](https://web-hehe-wocao.oss-cn-beijing.aliyuncs.com/shuixinfor/image-20240405234140031.png)



#### 项目框架图

![](https://i0.hdslb.com/bfs/new_dyn/fc00cad1ad611ac68315468b0878995833872539.png@1048w_!web-dynamic.avif)
































































































