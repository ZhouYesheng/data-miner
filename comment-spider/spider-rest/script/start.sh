#!/usr/bin/env bash
APP_HOME=`dirname $(pwd)`
echo $APP_HOME
JAVA_PATH=/usr/local/spider/chat-spider/tools/jdk1.8.0_121/bin
command="$JAVA_PATH/java -classpath $APP_HOME/config:$APP_HOME/libs/* -Xmn256m -Xmx1024m -Xms1024m -XX:PermSize=64m -XX:MaxPermSize=128m -XX:+PrintGCDetails -verbose:gc -XX:+PrintGCDateStamps  -Xloggc:$APP_HOME/logs/"$current_time" le.data.scs.spider.rest.SpiderRestBoot"
echo $command
nohup $command >>$APP_HOME/logs/"$current_time"log.log &

