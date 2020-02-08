# code-review
分类记录常用写法和逻辑

#1、Spring cloud Stream 集成kafka：  

https://www.jianshu.com/p/a94c67f02c16

#2、zookeeper 入门参考

https://blog.csdn.net/java_66666/article/details/81015302
````
# vim conf/zoo-1.cfg
dataDir=/tmp/zookeeper-1
clientPort=2181
server.1=127.0.0.1:2888:3888
server.2=127.0.0.1:2889:3889
server.3=127.0.0.1:2890:3890
````
server.A=B：C：D：其中 A 是一个数字，表示这个是第几号服务器；B 是这个服务器的 ip 地址；C 表示的是这个服务器与集群中的 Leader 服务器交换信息的端口；D 表示的是万一集群中的 Leader 服务器挂了，需要一个端口来重新进行选举，选出一个新的 Leader，而这个端口就是用来执行选举时服务器相互通信的端口。如果是伪集群的配置方式，由于 B 都是一样，所以不同的 Zookeeper 实例通信端口号不能一样，所以要给它们分配不同的端口号。

#3、kafka 参考
https://blog.csdn.net/miss1181248983/article/details/90737762
https://blog.csdn.net/miss1181248983/article/details/90737762
https://www.cnblogs.com/breezey/p/10850855.html

````
kafka-topics.sh --create --zookeeper 172.22.19.152:2181 --replication-factor 2 --partitions 1 --topic jing
kafka-console-producer.sh --broker-list 172.22.19.152:9092 --topic jing
kafka-console-consumer.sh --bootstrap-server 172.22.19.152:9092  --topic jing --from-beginning
````
#4、Redis集群部署
https://blog.csdn.net/sunbocong/article/details/85252071

#5、Mysql集群部署

