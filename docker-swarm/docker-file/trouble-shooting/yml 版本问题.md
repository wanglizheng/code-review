version: '3.3' 改为： version: '3.6' 解决  "name Additional property name is not allowed"

````
[root@icloud_158 docker]# docker stack deploy -c /root/docker/zookeeper.yml zookeeper
name Additional property name is not allowed
[root@icloud_158 docker]# vi zookeeper.yml 
version: '3.6'

services:
  zoo1:
    image: hmu-docker-local.repo.inspur.com/wurstmeister/zookeeper
    hostname: zoo1
    deploy:
      replicas: 1
      placement:
        constraints: [node.hostname == kafka-1]
    environment:
      ZOO_MY_ID: 1
      JVMFLAGS: -Xms1024m -Xmx1024m
      ZOO_SERVERS: server.1=zoo1:2888:3888 server.2=zoo2:2888:3888 server.3=zoo3:2888:3888
    healthcheck:
      test: ["CMD-SHELL","zkServer.sh","status || exit 1"]
      interval: 5s
      timeout: 3s
      retries: 3
    networks:
      - overlay1

  zoo2:
    image: hmu-docker-local.repo.inspur.com/wurstmeister/zookeeper
    hostname: zoo2
    deploy:
      replicas: 1
      placement:
        constraints: [node.hostname == kafka-2]
    environment:
      ZOO_MY_ID: 2
      JVMFLAGS: -Xms1024m -Xmx1024m
      ZOO_SERVERS: server.1=zoo1:2888:3888 server.2=zoo2:2888:3888 server.3=zoo3:2888:3888
    healthcheck:
      test: ["CMD-SHELL","zkServer.sh","status || exit 1"]
      interval: 5s
      timeout: 3s
      retries: 3
    networks:
      - overlay1

"zookeeper.yml" 65L, 1629C written
[root@icloud_158 docker]# 
[root@icloud_158 docker]# docker stack deploy -c /root/docker/zookeeper.yml zookeeper
Creating service zookeeper_zoo2
Creating service zookeeper_zoo3
Creating service zookeeper_zoo1
[root@icloud_158 docker]# 

````

参考： https://blog.csdn.net/u010886217/article/details/95935169