# ***webmagic爬虫项目***
    爬取xp1024的项目.基本功能如下
        + 某主题(分类)下爬取当前主题所有文章列表. ()
        + 基于数据库爬取最新更新的内容. (这个存在问题,只会爬取最近一天的,超过n天的都会有n-1天爬取不到,如果每天都定时爬取不会有这个问题. 未增加定时任务,没必要.)
        + 基于内容的爬取视频地址和视频.(爬取视频地址完成,爬取视频太难,网络太差,速度太慢,只是功能的完成,没有加入项目中)

    所有功能在测试单元里面过.
    
    待增加的功能:
        + 爬取图片
    
## _项目目录结构_
    + config 配置目录
    + constants 常量目录
    + dao 数据库持久层
    + domain 数据库实体目录
    + pipeline 管道目录 为抓取的数据做后续处理
    + processor 页面解析目录
    + service 业务层
    + tools 纯工具类目录
    + util 和业务相关的工具类目录

## _webmagic框架的缺点_
    + xpath部分语法不支持