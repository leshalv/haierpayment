#!/bin/bash

CMD=$1
PROJECT_NAME=$2
JAR_NAME=$3
SERVER_PORT=$4

echo "#########################haier cash#####################################"
echo " ${CMD} ${PROJECT_NAME}  ${JAR_NAME} ${SERVER_PORT} "
echo "#########################haier cash#####################################"

HELP_CMD="./startSpringJarServer.sh.sh {start|stop|restart|status} projectname jar_name server_port"

if [ -z "$PROJECT_NAME"  ]; then
  echo "projectname error,you must use like this : ${HELP_CMD} "
  exit 1
fi
if [ -z "$JAR_NAME"  ]; then
  echo "JAR_NAME error,you must use like this : ${HELP_CMD}"
  exit 1
fi
if [ -z "$SERVER_PORT"  ]; then
  echo "SERVER_PORT error,you must use like this : ${HELP_CMD}"
  exit 1
fi
echo "JAVA_HOME="${JAVA_HOME}
if [ "${JAVA_HOME}" = "" ]
then
    echo "JAVA_HOME must be setted."
    export JAVA_HOME=/usr/java/jdk1.8.0_40
    export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
    echo "Using JAVA_HOME: ${JAVA_HOME}"
fi

if [ "${APP_HOME}" = "" ]
then
    echo "WARN! APP_HOME must be setted. set  APP_HOME = /export/home/app/server/${PROJECT_NAME}"
    APP_HOME=/export/home/app/server/${PROJECT_NAME}
fi

if [ "${LOG_DIR}" = "" ]
then
    LOG_DIR=$APP_HOME/logs
fi

mkdir -p $LOG_DIR

#CLASSPATH=$CAP_LIB:$APP_LIB

#for jar in $APP_HOME/lib/*.jar
#do
#  CLASSPATH=$CLASSPATH:$jar
#done

JAVA_OPTS=""
#JAVA_OPTS="$JAVA_OPTS "-Dlog.path="${LOG_DIR}"
#JAVA_OPTS="$JAVA_OPTS "-Dlog.filename="${PROJECT_NAME}.log"
JAVA_OPTS="$JAVA_OPTS "-Dcom.sun.management.jmxremote
JAVA_OPTS="$JAVA_OPTS "-Dcom.sun.management.jmxremote.port=5${SERVER_PORT}
JAVA_OPTS="$JAVA_OPTS "-Dcom.sun.management.jmxremote.authenticate=false
JAVA_OPTS="$JAVA_OPTS "-Dcom.sun.management.jmxremote.ssl=false
#JAVA_OPTS="$JAVA_OPTS "-DPROCESS_ID=0

FILE_OUT=${LOG_DIR}/${PROJECT_NAME}.log

FILE_ERR=${LOG_DIR}/${PROJECT_NAME}.log

PIDFile=${LOG_DIR}/${PROJECT_NAME}.pid

#FILE_GC=${LOG_DIR}/${PROJECT_NAME}.gc

function check_if_pid_file_exists() {
    if [ ! -f $PIDFile ]
        then
            echo "PID file not found: $PIDFile"
            return 1
        else
            return 0
    fi
}
function check_if_process_is_running() {
    if ps -p $(print_process) > /dev/null
        then
            return 0
        else
            return 1
    fi
}
function print_process() {
    echo $(<"$PIDFile")
}
function checkStatus() {
    if ! check_if_pid_file_exists
     then
        echo "check_if_pid_file_exists file not exists.is stop"
        return 1
    fi
    if check_if_process_is_running
        then
            echo $(print_process)" is running"
             return 0
        else
            echo "Process not running: $(print_process)"
             return 1
    fi

}
function stopApp() {
    if ! check_if_pid_file_exists
     then
        echo "stopApp check_if_pid_file_exists file not exists.is stop"
        return 0
    fi
    if ! check_if_process_is_running
        then
            echo "Process $(print_process) already stopped"
            return 0
    fi
    kill -TERM $(print_process)
    echo -ne "Waiting for process to stop"
    NOT_KILLED=1
    for i in {1..120}; do
        if check_if_process_is_running
            then
                echo -ne "."
                sleep 1
            else
                NOT_KILLED=0
        fi
    done
    echo
    if [ $NOT_KILLED = 1 ]
        then
            echo "Cannot kill process $(print_process)"
            return 1
    fi
    echo "Process stopped"
    return 0
}
function startApp() {
    echo "begin process start"
    if [ -f $PIDFile ] && check_if_process_is_running
        then
            echo "Process $(print_process) already running"
        return 1
    fi
    echo `date` to start ... >>$FILE_OUT

    GC_OPT="-d64 -Xms1g -Xmx3g "

    osname=`uname`
    if [ $osname = "HP-UX" ];then
                    GC_OPT="$GC_OPT -Xverbosegc:file=$FILE_GC"
    else
        if [ $osname = "SunOS" ];then
                    GC_OPT="$GC_OPT -Xloggc:$FILE_GC -XX:+PrintGCDetails"
        else
                if [ $osname = "AIX" ];then
                            GC_OPT="$GC_OPT "
            else
                    if [ $osname = "Linux" ];then
                                    GC_OPT="$GC_OPT "
                            else
                                    echo "OS:$osname"
                            fi
            fi
        fi
    fi

    JAVA_OPTS="$JAVA_OPTS "$GC_OPT

    nohup $JAVA_HOME/bin/java $JAVA_OPTS -jar -D$PROJECT_NAME $APP_HOME/$JAR_NAME --spring.config.location=./config/application.yml   $PROJECT_NAME $SERVER_PORT    >/dev/null  2>>$FILE_ERR &  

    echo $! >$PIDFile
    echo $PROJECT_NAME started;

    echo "Process started"
    return 0
}
cd $APP_HOME

case "$1" in
status)
   checkStatus
   if [ $? = 1 ]
      then
        exit 1
       else
        exit 0
    fi
;;
stop)
   stopApp
   if [ $? = 1 ]
      then
        exit 1
       else
        exit 0
    fi
;;
start)
   startApp
   if [ $? = 1 ]
      then
        exit 1
       else
        exit 0
    fi
;;
restart)
    stopApp
    if [ $? = 1 ]
      then
        echo "error: not stop!"
        exit 1
    fi
    echo "begin start!"
    startApp
    if [ $? = 1 ]
      then
        exit 1
    fi
    checkStatus
    if [ $? = 1 ]
      then
        exit 1
       else
        exit 0
    fi
;;
*)
echo "Usage: $0 {start|stop|restart|status}"
exit 1
esac
exit 0
