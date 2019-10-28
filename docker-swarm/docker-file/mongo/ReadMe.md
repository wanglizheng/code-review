
1、【Manager】创建集群网络
````
docker network create -d overlay --attachable mongo
````

2、创建 9 个 Data 服务，3 个 Config 服务，1 个 Global 模式的 Mongos 服务

2.1、【所有机器】创建相关文件夹
````
mkdir /root/mongo/config /root/mongo/shard1 /root/mongo/shard2 /root/mongo/shard3
````
2.2、【Manager】创建 stack.yml 如mongo.yml

2.3、启动服务，在 Manager 上执行
````
docker stack deploy -c mongo.yml mongo
````
2.4、【Manager】查看服务的启动情况
````
docker service ls
````
3、初始化集群

3.1 【Manager】初始化 Mongo 配置集群
````
docker exec -it $(docker ps | grep "cfg1" | awk '{ print $1 }') bash -c "echo 'rs.initiate({_id: \"cfgrs\",configsvr: true, members: [{ _id : 0, host : \"cfg1\" },{ _id : 1, host : \"cfg2\" }, { _id : 2, host : \"cfg3\" }]})' | mongo"
````
3.2 【Manager】初始化三个 Mongo 数据集群
````
docker exec -it $(docker ps | grep "mongors1n1" | awk '{ print $1 }') bash -c "echo 'rs.initiate({_id : \"shard1\", members: [{ _id : 0, host : \"mongors1n1\" },{ _id : 1, host : \"mongors1n2\" },{ _id : 2, host : \"mongors1n3\", arbiterOnly: true }]})' | mongo"
docker exec -it $(docker ps | grep "mongors2n1" | awk '{ print $1 }') bash -c "echo 'rs.initiate({_id : \"shard2\", members: [{ _id : 0, host : \"mongors2n1\" },{ _id : 1, host : \"mongors2n2\" },{ _id : 2, host : \"mongors2n3\", arbiterOnly: true }]})' | mongo"
docker exec -it $(docker ps | grep "mongors3n1" | awk '{ print $1 }') bash -c "echo 'rs.initiate({_id : \"shard3\", members: [{ _id : 0, host : \"mongors3n1\" },{ _id : 1, host : \"mongors3n2\" },{ _id : 2, host : \"mongors3n3\", arbiterOnly: true }]})' | mongo"
````
3.3 【Manager】将三个数据集群当做分片加入 mongos
````
docker exec -it $(docker ps | grep "mongos" | awk '{ print $1 }') bash -c "echo 'sh.addShard(\"shard1/mongors1n1:27017,mongors1n2:27017,mongors1n3:27017\")' | mongo "
docker exec -it $(docker ps | grep "mongos" | awk '{ print $1 }') bash -c "echo 'sh.addShard(\"shard2/mongors2n1:27017,mongors2n3:27017,mongors2n3:27017\")' | mongo "
docker exec -it $(docker ps | grep "mongos" | awk '{ print $1 }') bash -c "echo 'sh.addShard(\"shard3/mongors3n1:27017,mongors3n2:27017,mongors3n3:27017\")' | mongo "
````
4、连接集群
4.1 内部：在 mongo 网络下的容器，通过 mongos:27017 连接
4.2 外部：通过 IP:27017 连接，IP 可以为三台服务的中的一个的 IP

参考网址： https://www.centos.bz/?s=swarm