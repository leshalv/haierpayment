#!/bin/bash

##################################################################
#
# 功能: ./project_release.sh <projectname> <runtime_package_name> <profile reboot_flag> [APP_PORT]
#
# 参数: 脚本程序名称
#
# 返回: 0-执行成功 非0-执行失败
#
###################################################################
appbak()
{
    funret=1

    if [ -f ${APP_BACKUPS_PATH}/${RUNTIME_PACKAGE_NAME}_${NOWTIME}.zip ]
    then
        echo "Application of the backup file already exists, please confirm!! [${APP_BACKUPS_PATH}/${RUNTIME_PACKAGE_NAME}_${NOWTIME}.zip]" E
        return $funret
    fi

    if [ -d ${APP_BACKUPS_PATH}/${NOWTIME} ]
    then
       echo "Backup file directory does not exist"
     else
       mkdir -p ${APP_BACKUPS_PATH}/${NOWTIME}
       echo "Create backup file directory"
    fi
    #
    # 应用备份
    #
    if [ -d ${APP_HOME}/${RUNTIME_PACKAGE_NAME} ]
    then
        /usr/bin/zip -r ${APP_BACKUPS_PATH}/${NOWTIME}/${RUNTIME_PACKAGE_NAME}_${NOWTIME}.zip  ${APP_HOME}/${RUNTIME_PACKAGE_NAME}

        if [ -f  ${APP_BACKUPS_PATH}/${NOWTIME}/${RUNTIME_PACKAGE_NAME}_${NOWTIME}.zip  ]
        then
            echo "After application of backup [${APP_BACKUPS_PATH}/${NOWTIME}/${RUNTIME_PACKAGE_NAME}_${NOWTIME}.zip]"
            funret=0
        else
            echo "Backup application failure![${APP_BACKUPS_PATH}/${NOWTIME}/${RUNTIME_PACKAGE_NAME}_${NOWTIME}.zip]" E
        fi

     else
        echo "No run the package"
        funret=0
    fi
    
   return $funret
}

get_updpkg()
{
	funret=1

	#
	# 获取更新包，代码清单
	#

	mkdir -p $BACKUPS_CURRENT_PATH

	rm -rf  $BACKUPS_CURRENT_PATH/*

	expect -c "
	    spawn scp -o GSSAPIAuthentication=no -P ${RELEASE_PORT} ${RELEASE_USER}@${RELEASE_HOST}:\"${RELEASE_PATH}\"  ${BACKUPS_CURRENT_PATH}/${RUNTIME_PACKAGE_NAME}
	    set timeout 3
        expect {
        \"yes/no\" {send \"yes\r\";exp_continue}
        }
        expect \"${RELEASE_PORT} ${RELEASE_USER}@${RELEASE_HOST}'s password:\"
        set timeout 3
        send \"${RELEASE_PWD}\r\"
        set timeout 300
        send \"exit\r\"
        expect eof
    "
	if [ ! -f ${BACKUPS_CURRENT_PATH}/${RUNTIME_PACKAGE_NAME} ]
	then
	    echo "Update package does not exist[${BACKUPS_CURRENT_PATH}/${RUNTIME_PACKAGE_NAME}]"
	    return $funret
	else
	    funret=0
	    echo "Update package to get finished [${BACKUPS_CURRENT_PATH}/${RUNTIME_PACKAGE_NAME}]"
	fi
    
    return $funret
}
##################################################################
#
# 功能: 准备更新包，解压更新文件包，并检查更新文件是否正确
#
# 参数: 脚本程序名称
#
# 返回: 0-执行成功 非0-执行失败
#
###################################################################
unzip_updpkg()
{
	funret=1
	#
	#检查根目录
	#
	if [ -f ${APP_HOME} ]
	then
		echo "The environment of OK[${APP_HOME}]"
	else
		echo "Environmental uninitialized [${APP_HOME}]"
		mkdir -p ${APP_HOME}
	fi
	#
	# 清除旧文件包 直接覆盖
	#
#	if [ -f ${APP_HOME}/${RUNTIME_PACKAGE_NAME} ]
#	then
#		rm  ${APP_HOME}/${RUNTIME_PACKAGE_NAME}
#		echo "旧文件包已经删除! [${APP_HOME}/${RUNTIME_PACKAGE_NAME}]"
#		if [ -f ${APP_HOME}/${RUNTIME_PACKAGE_NAME} ]
#        then
#            echo "旧文件包删除失败!! [${APP_HOME}/${RUNTIME_PACKAGE_NAME}]"
#            return $funret
#        else
#            echo "旧文件包删除完毕 [${APP_HOME}/${RUNTIME_PACKAGE_NAME}]"
#        fi
#	else
#		echo "旧文件包删除完毕! [${APP_HOME}/${RUNTIME_PACKAGE_NAME}]"
#	fi

	#
	# 准备新代码包
	#
	cp -a ${BACKUPS_CURRENT_PATH}/${RUNTIME_PACKAGE_NAME} ${APP_HOME}/${RUNTIME_PACKAGE_NAME}

	#
	# 检查NEW_APP_NAME是否存在
	#
	if [ ! -f ${APP_HOME}/${RUNTIME_PACKAGE_NAME} ]
	then
		echo "The new package does not exist [${APP_HOME}/${RUNTIME_PACKAGE_NAME}]"
		return $funret
	else
		echo "The new package ready [${APP_HOME}/${RUNTIME_PACKAGE_NAME}]"
	fi
	funret=0
	return $funret
}

reboot_srv()
{

    funret=1

    #
    # 启动服务
    #

    sh ${ROOT_PATH}/bin/./startSpringJarServer.sh restart ${PROJECT_NAME} ${RUNTIME_PACKAGE_NAME} ${APP_PORT}&
    
    funret=0
    
    return $funret
}

stop_srv()
{

    funret=1

    #
    # 启动服务
    #

    sh ${ROOT_PATH}/bin/./startSpringJarServer.sh stop ${PROJECT_NAME} ${RUNTIME_PACKAGE_NAME} ${APP_PORT}&
    
    funret=0
    
    return $funret
}
#################################################################
#
# 功能: 更新代码版本
#
# 参数: 脚本程序名称
#
# 返回: 0-执行成功 非0-执行失败
#
###################################################################
upd_srv()
{
    funret=1
    #
    # 应用备份
    #
    appbak
    ret=$?
    if [ $ret -ne 0 ]
    then
         return $funret
    fi
    echo "step 1:the backup to complete--OK"

    #
    # 获取更新包，代码清单
    #
    get_updpkg
    ret=$?
    if [ $ret -ne 0 ]
    then
         return $funret
    fi
    echo "step 2:Update packages for completion--OK"
     #
     # 停止应用
     #
    stop_srv
    ret=$?
    if [ $ret -ne 0 ]
    then
         return $funret
    fi
    echo "step 3:Stop server --OK"
    #
    # 解压并检查更新包
    #
    unzip_updpkg
    ret=$?
    if [ $ret -ne 0 ]
    then
         return $funret
    fi
    echo "step 3:Update package decompression and check is complete--OK.Step 3: update extract & check is complete"

    #
    # 重启应用服务器
    #
    if [ "$REBOOT_FLAG" == "N" ]
    then
        echo "step 4:Only to update the program does not restart,REBOOT_FLAG=[$REBOOT_FLAG]--OK!Step 4: only to update the program does not restart"
    else
        reboot_srv
        ret=$?
        if [ $ret -ne 0 ]
        then
             return $funret
        fi
        echo "step 4:Restart the application to finish--OK!Step 4: restart the application"
    fi

    return 0
    
}


echo ##################################################################
echo #
echo #自动部署开始执行
echo #	1.备份现有程序
echo #	2.下载最新包 存放到最新包目录
echo #	3.替换新包到 运行环境
echo #	4.启动 
echo # JAVA_HOME APP_HOME LOG_DIR Must be setted
echo # PROJECT_NAME=pgw-manager
echo # RUNTIME_PACKAGE_NAME=pgw-manager.jar
echo # PROFILE=bate
echo # REBOOT_FLAG=Y
echo ###################################################################
echo ####define param ######

PROJECT_NAME=$1
RUNTIME_PACKAGE_NAME=$2
PROFILE=$3
REBOOT_FLAG=$4
APP_PORT=$5

RELEASE_HOST=$6
RELEASE_PORT=$7
RELEASE_USER=$8
RELEASE_PWD=$9

if [ -z "$PROJECT_NAME"  ]; then
  echo "projectname error,you must use like this : ./project_release.sh <projectname> <runtime_package_name> <profile reboot_flag> [APP_PORT]"
  exit    
fi
if [ -z "$RUNTIME_PACKAGE_NAME"  ]; then
  echo "runtime_package_name error,you must use like this : ./project_release.sh  <projectname> <runtime_package_name> <profile reboot_flag> [APP_PORT]"
  exit    
fi
if [ -z "$PROFILE"  ]; then
  echo "runtime_package_name error,you must use like this : ./project_release.sh  <projectname> <runtime_package_name> <profile reboot_flag> [APP_PORT]"
  exit    
fi
if [ -z "$REBOOT_FLAG"  ]; then
  echo "runtime_package_name is null,now REBOOT_FLAG=Y,you must use like this : ./project_release.sh  <projectname> <runtime_package_name> <profile reboot_flag> [APP_PORT]"
  REBOOT_FLAG=Y;
fi

if [ -z "$RELEASE_HOST"  ]; then
  echo "RELEASE_HOST is not set,defaut beta 10.164.196.37"
  RELEASE_HOST=10.164.196.37
fi
if [ -z "$RELEASE_PORT"  ]; then
  echo "RELEASE_PORT is not set,defaut beta 10.164.196.37"
  RELEASE_HOST=22
fi
if [ -z "$RELEASE_HOST"  ]; then
  echo "RELEASE_USER is not set,defaut beta 10.164.196.37"
  RELEASE_HOST=release
fi
if [ -z "$RELEASE_HOST"  ]; then
  echo "RELEASE_PWD is not set"
  RELEASE_HOST=release^123
fi

#RELEASE_PORT=22
#RELEASE_USER=release
#RELEASE_PWD=release^123

NOWTIME=`date --date='0 days ago' "+%Y%m%d%H%M%S"`

RELEASE_PATH=/export/home/release/${PROFILE}/current/${PROJECT_NAME}/${RUNTIME_PACKAGE_NAME}

if [ "${JAVA_HOME}" = "" ]
then
    echo "JAVA_HOME must be setted."
    export JAVA_HOME=/usr/java/jdk1.8.0_40
    export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
    echo "Using JAVA_HOME: ${JAVA_HOME}"
fi
ROOT_PATH=/export/home/app/server/
APP_HOME=${ROOT_PATH}/${PROJECT_NAME}
echo "set to APP_HOME=${APP_HOME}"

APP_BACKUPS_HOME=/export/home/app/backups
echo "set to APP_BACKUPS_HOME==${APP_BACKUPS_HOME=}"

APP_BACKUPS_PATH=${APP_BACKUPS_HOME}/${PROJECT_NAME}/backups
echo "set to APP_PACKAGE_PATH=${APP_BACKUPS_PATH}"

BACKUPS_CURRENT_PATH=${APP_BACKUPS_HOME}/${PROJECT_NAME}/current
echo "set to BACKUPS_CURRENT_PATH=${BACKUPS_CURRENT_PATH}"

#
# 执行代码更新
#
upd_srv
ret=$?
if [ $ret -ne 0 ]
then   
     exit 1
fi

echo "Update complete"
