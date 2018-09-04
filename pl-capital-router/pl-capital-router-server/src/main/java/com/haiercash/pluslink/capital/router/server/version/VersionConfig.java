package com.haiercash.pluslink.capital.router.server.version;

import com.haiercash.pluslink.capital.common.utils.BaseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 版本控制
 * @Auther: WDY
 * @Date: 2018/8/16 16:55
 * @rmk:
 */
@Service("routerVersionConfig")
public class VersionConfig extends BaseService{

    @Value("${router.version.project.app:}")
    private String appVersion;
    @Value("${router.version.project.cache:}")
    private String cacheVersion;
    @Value("${router.version.server.RouterController:}")
    private String routerControllerVersion;
    @Value("${router.version.server.GeneralInfoController:}")
    private String generalInfoControllerVersion;
    @Value("${haiercash.pl.profile:}")
    private String profile;
    @Value("${server.port:}")
    private String port;
    @Value("${eureka.client.serviceUrl.defaultZone:}")
    private String eurekaAddress;
    @Value("${redis.cluster.address:}")
    private String redisAddress;
    @Value("${spring.datasource.url:}")
    private String databaseAddress;
    @Value("${spring.profiles.include:}")
    private String configYml;
    @Value("${redis.cache.flag:}")
    private String redisCacheFlag;

    public void configVersionAfterBoot(){

        Map<String,Integer> map = new HashMap();

        logger.info("");
        logger.info("===========================检查配置开始=========================");
        logger.info("");
        logger.info("[环境类型]：{}",profile);
        logger.info("[服务端口]：{}",port);
        logger.info("[引用配置(*.yml)]：{}",configYml);
        logger.info("[缓存数据启用状态]：{}",redisCacheFlag);
        logger.info("[eureka地址]：{}",eurekaAddress);
        logger.info("[redis地址]：{}",redisAddress);
        logger.info("[数据库地址]：{}",databaseAddress);
        logger.info("");
        logger.info("===========================检查系统版本开始=========================");

        printLog("服务",appVersion);
        printLog("资金平台配置参数初始化",cacheVersion);

        logger.info("===========================检查系统版本结束=========================");
        logger.info("");
        logger.info("");
        logger.info("===========================检查接口版本开始=========================");

        printLog("通知路由处理接口",routerControllerVersion);
        printLog("资金方综合信息查询接口",generalInfoControllerVersion);

        logger.info("===========================检查接口版本结束=========================");
        logger.info("");
        logger.info("===========================检查配置结束=========================");
        logger.info("");

        addVersion();
    }

    public void printLog(String name,String version){
        logger.info("[{}]版本号：[version:{}]",name,version);
    }

    public void addVersion(){

        /**通知路由处理接口版本号**/
        VersionConstant.routerControllerVersion = this.routerControllerVersion;

        /**资金方综合信息查询接口版本号**/
        VersionConstant.generalInfoControllerVersion = this.generalInfoControllerVersion;

    }
}
