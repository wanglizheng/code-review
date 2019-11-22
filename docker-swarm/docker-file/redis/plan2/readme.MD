主容器查询集群状态
````
docker exec -it fdace43bff52 bash 
redis-cli -a inspur2019 -p 6379 info Replication

````

从库退出集群并保持原先数据(通过bash登录从库容器后,使用redis-cli客户端)

````
slaveof no one
slaveof redis-master 6379

````


验证哨兵模式+集群是否高可用(通过bash登录从库容器后,使用redis-cli客户端)

````

redis-cli -a Mypwd@123456 -p 6379 DEBUG sleep 60

````