###获取kafka中所有组
````
./kafka-consumer-groups.sh  --bootstrap-server 10.10.9.40:9092 --list
./kafka-consumer-groups.sh  --bootstrap-server 172.22.19.157:9092,172.22.19.154:9092,172.22.19.155:9092 --list
````
###查看kafka消费端堆积量

````
./kafka-consumer-groups.sh  --bootstrap-server 172.22.19.157:9092,172.22.19.154:9092,172.22.19.155:9092 --group great-soft --describe
````
###查看topic下的内容
````
./kafka-console-consumer.sh --bootstrap-server 172.22.19.157:9092,172.22.19.154:9092,172.22.19.155:9092 --topic GREATSOFT --from-beginning
````
###查看kafka某个topic下partition信息
````
./kafka-topics.sh --zookeeper 172.22.19.157:2181,172.22.19.154:2181,172.22.19.155:2181  --topic  GREATSOFT --describe
````

###查看kafka所有topic
````
./kafka-topics.sh --list --zookeeper 172.22.19.157:2181,172.22.19.154:2181,172.22.19.155:2181
````