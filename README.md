
**项目结构说明**

```
pl-capital -> PlusLink-资金平台
    bin -> 发布和启动脚本
    lib -> 本地jar存放目录；如果用到公司maven仓库不存的jar包放到该目录，但是尽可能主动传到公司maven仓库
    pl-capital-base -> 基础类存放，例如：枚举值、实体类、常量等，可能被其它项目引用
    pl-capital-common -> 封装工具类存放，例如：utils、mybatis封装、项目异常处理类封装等；
    pl-capital-manager -> 管理端后台服务，打包时会自动将static目录打到发布包
    pl-capital-processer -> 处理中心
        pl-capital-processer-api ->  处理中心对外提供的api,api中一个方法都对应一个地址
        pl-capital-processer-inside-api -> 处理中心内部api，如果没有特殊的内部api可以删除此工程
        pl-capital-processer-server -> 处理中心server，处理中心主进程
    pl-capital-router -> 路由服务
        pl-capital-router-api -> 路由服务api(提供server)
        pl-capital-router-inside-api ->  路由服务内部调用api(提供client)
        pl-capital-router-server -> 路由服务server
    static -> 运营端页面
    .gitignore -> 该文件用于排除某些文件不上传
    build.gradle -> 编译总控脚本
    changelog.txt -> 版本变更日志，只是记录
    gradle.properties -> jar包属性配置
    installation_package.gradle -> 生产包编译控制脚本
    maven_push.gradle -> 非运行类工程jar自动上传maven仓库，例如base\common\api等，上传后其它项目可以通过仓库引用
    properties_replace.gradle -> 用于发布时自动替换版本号；
    README.md -> 项目说明文件
    settings.gradle ->  gradle编译核心文件，用户控制该工程引入哪些子工程
    
```
    
    