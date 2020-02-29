# gmall本地修改文件

# gmall-user用户服务8080

gmall-user-service 用户服务的service层 8060
gmall-user-web 用户服务的web层 8080

gmall-manage-service 用户服务的service层 8061
gmall-manage-web 用户服务的web层 8081

# gmall-item-service 前台的商品详情服务8062
gmall-item-web  前台的商品详情展示 8082

2020-02-17
在github上创建工程，使用git来维护工程版本。
搭建搭建SpringBoot框架，测试整合Mybatis通用Mapper，实现一些查询小功能。

2020-02-18
创建父工程，用以统一维护各个框架版本
抽取bean和service接口到gmall-api中
抽取util工程（项目中的通用框架）
    基于SOA的架构理念，前后端分离。项目分为web前端(Controller)和web后端(service)
    抽取common-util
    抽取service-util（其中引用了common-util）
    抽取web-util

2020-02-19
启动linux，安装jdk、tomcat、dubbo、zookeeper
设置监控中心和zookeeper的开机自启动
将项目改造为dubbo的分布式架构
分离user项目，分为user-service和user-web
引入Dubbo框架到common-util中（因为将来web层和service层都需要使用dubbo进行交互）
在web和service中配置dubbo和zookeeper

    spring.dubbo.application=user-web/user-service
    #dubbo的通讯协议名称
    spring.dubbo.protocol.name=dubbo
    
    #zookeeper的注册中心地址
    spring.dubbo.registry.address=192.168.183.128:2181
    
    #zookeeper通讯协议的名称
    spring.dubbo.registry.protocol=zookeeper
    
    #dubbo服务扫描路径
    spring.dubbo.base-package=com.atguigu.gmall
 
    #设置超时时间
    spring.dubbo.consumer.timeout=600000
    
    #设置是否检查服务存在
    spring.dubbo.consumer.check=false
    
    
2020-02-20
商品数据结构的划分：
    sku的结构
    spu的结构
    类目的结构:分为三级
    属性的结构：平台属性的外键是三级分类id，在使用平台属性之前必须先选择三级分类
前后端分离：
功能列表：
    1.三级分类的查询
    2.商品的平台属性列表的增删改查
    3.商品spu的添加
      spu列表查询
      spu的销售属性、属性值、Fastdfs图片上传
    4.商品sku的添加
      sku信息、sku关联的销售属性、sku关联的平台属性、sku图片

更改文件dev.env.js中的后端接口地址
index.js  前端的服务器端口  更改其中的host地址

创建两个module：gmall-manage-web和gmall-mangage-service并配置(商城系统的后台管理系统)
    1.写一个Catalog1方法给前端项目调用
    2.返回给前端一个分类列表的集合（json）
    3.在gmall-api项目的service中新建一个CatalogService的服务接口
    4.方法的实现写入gmall-manage-service中
    5.mapper写在gmall-manage-service
    
跨域出现的问题：如果没有授权（Access-Control-Allow-Origin），不通服务器之间，一台服务器的请求是无法容许另一台服务器的响应数据出现
后端解决方法：在springmvc控制层加允许跨域注解@CrossOrigin

2020-02-21
商品平台属性的管理功能（增删改查）：
1.根据三级分类的id来查询平台属性列表
    a.新建AttrController，方法attrInfoList（将这些controller分开编辑，有利于后期的维护）
    b.新建PmsBaseAttrInfo和PmsBaseAttrValue的映射类
    c.新建service和mapper
查：getAttrValueList
增、删和改是同一个方法(saveAttrInfo)：根据主键id的有无判断是增还是修改。

商品属性的spu管理：
    三级分类与上面的三级分类通用
    查询三级分类下面的spu列表
    
    
2020-02-22
spu的添加功能：spu信息、名称、描述和
    图片上传（图片的对象数据存储在分布式的文件存储服务器上  fastdfs；图片的原数据信息保存在数据库中）
这里选择的策略为：当用户添加完图片之后，就直接将图片对象数据上传值服务器，当用户点击提交时，只需将原数据信息保存在数据库即可。

FastDfs的工作原理：
在用户端和Storage Server（存储服务器）之间加一个Tracker Server，它用来实时同步存储存储服务器的状态。
当用户端发送请求到TS端，TS检查可用的SS，并返回给用户端一个可用的SS的ip和端口，用户端拿着返回的结果去请求SS，
SS为文件生成fileId然后将文件写入磁盘，最后将fileId（路径信息和文件名）返回。

2020-02-23
在linux上安装配置fastdfs、nginx和fdfs_nginx_module（身体累……心里开心）
fdfs和SpringBoot的整合:在github上下载fastdfs-client-java，安装至maven仓库使用
添加spu功能
    
    
2020-02-24
平台属性列表（attrInfoList方法的完善，属性值的添加，在添加sku的时候提供可选择的属性值）
销售属性列表
spu的图片列表
保存sku整个信息
后台系统完毕

2020-02-25
前台系统中包含的业务功能
    首页（静态化）
    检索页（搜索引擎）
    详情页（缓存、切换、推荐）
    购物车页（cookie、redis）
    结算页（订单页）（一致性校验、安全）
    支付页（安全、对接支付平台）

2020-02-26
新建gmall-item-web商品详情工程
引入Thymeleaf的依赖，Thymeleaf是springboot的原生渲染模板，它使用的是html的扩展标签
添加约束: <html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
sku商品的属性动态切换展示

