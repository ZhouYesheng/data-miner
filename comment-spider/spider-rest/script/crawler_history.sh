#!/usr/bin/env bash
APP_HOME=`dirname $(pwd)`
data_time=$1
command="java -classpath $APP_HOME/config:$APP_HOME/libs/* -Xmn256m -Xmx1024m -Xms1024m -XX:PermSize=64m -XX:MaxPermSize=128m le.data.scs.spider.crawler.task.LeMallWebChatTask $data_time"
echo $command
nohup $command >>$APP_HOME/logs/crawler_history_log.log &
