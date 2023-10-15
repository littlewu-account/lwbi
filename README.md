# lw智能数据分析系统

> 作者：[吴峻阳](https://gitee.com/littlewuu)

#### 系统介绍
根据用户上传的源数据、分析诉求，智能生成可视化图表以及分析结论，提高数据分析效率。


[toc]


#### 系统架构
![system_framework](../resources/system_framework.jpg "system_framework")


#### 系统特点
前端：
    1.基于Umi OpenAPI插件，结合后端Swagger接口文档快速生成请求service层代码，提高开发效率。
	2.集成Echarts库，接收后端AI生成的JSON自动渲染可视化图表。
后端：
    1.引入SpringSecurity框架实现用户认证与鉴权，提高系统资源安全性。
    2.利用Easy Excel解析用户上传的表格文件并压缩为CSV，节约AI模型的输入成本。
	3.通过对用户上传的文件进行后缀、大小校验，提高系统安全性。
    4.基于Redisson的RateLimiter实现分布式限流，控制单用户的访问频率。
    5.基于自定义IO密集型线程池，实现AIGC的并发执行和异步化。
    6.使用RabbitMQ接收并持久化AI分析的任务消息，提高系统可靠性。

