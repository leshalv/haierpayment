#Sql文件夹：项目的表结构，初始化数据，公用数据
##object : 
###数据库模型：table(表结构) 
###sequence(序列)，
###create_tablespace_user.sql(表空间用户创建sql)等 
##common_data : 系统公共数据sql(投产前不需要修改，例如数据字典等)
##init_data : 初始化数据(投产前需要明确,例如环境参数，部分关联地址等)
##rollback : 数据回退

#注意:创建完sql文件看一下文件是否为：
##(1)utf-8无bom头
##(2)档案格式为UNIX格式