#!/bin/sh

### java env
#export JAVA_HOME=/xxxx
#export JRE_HOME=$JAVA_HOME/jre

## service name
## 服务所在目录
export SERVICE_DIR=/zhw/javaweb
## 服务名称
SERVICE_NAME=wxSend-0.0.1-SNAPSHOT.jar
JAR_NAME=wxSend-0.0.1-SNAPSHOT.jar
PID=$SERVICE_NAME\.pid

#echo $JAR_NAME
#echo $SERVICE_DIR
cd $SERVICE_DIR

stop (){
    kill `cat $SERVICE_DIR/$PID`
    rm -rf $SERVICE_DIR/$PID
    echo "=== stop $SERVICE_NAME"

    ## 停止5秒
    sleep 5
    ##
    P_ID=`ps -ef | grep -w "$SERVICE_NAME" | grep -v "grep" | awk '{print $2}'`
    if [ "$P_ID" == "" ]; then
        echo "=== $SERVICE_NAME process not exists or stop success"
    else
        echo "=== $SERVICE_NAME process pid is:$P_ID"
        echo "=== begin kill $SERVICE_NAME process, pid is:$P_ID"
        kill -9 $P_ID
    fi
}

start(){
    ##nohup &  以守护进程启动
    ##/home/logs/webLog.txt  日志输出地址
    nohup java $JAVA_ARGS -Duser.timezone=Asia/Shanghai -jar $JAR_NAME --spring.profiles.active=prod >$SERVICE_DIR/webLog.txt 2>&1 &
    echo $! > $SERVICE_DIR/$PID
    echo "=== start $SERVICE_NAME"
}

case "$1" in

    stop)
        stop
        ;;

    start)
        stop
        sleep 2
        start
        ;;

    restart)
        stop
        sleep 2
        start
        echo "=== restart $SERVICE_NAME"
        ;;

    *)
        ## restart
        $0 stop
        sleep 2
        $0 start
        ;;

esac
exit 0

