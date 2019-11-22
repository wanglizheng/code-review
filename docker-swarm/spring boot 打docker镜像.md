#基本步骤

````
1、Git clone 项目
git clone -b dev http://git.inspur.com/inspur_health/common-service/sso-authenticate.git

2、打包
cd /root/docker/sso-authenticate
mvn package -f pom.xml -s settings.xml -Dmaven.test.skip=true
或者  mvn clean install -DskipTests  -f pom.xml -s settings.xml

3、构建docker镜像
docker build --build-arg JAR_FILE=sso-authenticate.jar . -t sso-authenticate:101706
验证镜像可运行：
docker run -d sso-authenticate:101706
docker exec  -it 4caad740d0ae sh

4、push到docker镜像仓库
docker tag sso-authenticate:101706 10.10.9.26:5000/sso-authenticate:101706
docker push 10.10.9.26:5000/sso-authenticate:101706


vi /lib/systemd/system/docker.service 
在ExecStart里添加--insecure-registry 192.168.119.133:5000
systemctl daemon-reload
systemctl restart docker 


5、查看镜像

[root@icloud_158 sso-authenticate]# docker ps
CONTAINER ID        IMAGE                                             COMMAND                  CREATED             STATUS              PORTS                    NAMES
d2b494e011b9        hmu-docker-local.repo.inspur.com/mariadb:latest   "docker-entrypoint.s…"   5 hours ago         Up 5 hours          3306/tcp                 1017-db_my_slave_mariadb.1.xm4hpvgulvvd6nwiw9yq2vr6d
f09125e6db33        hmu-docker-local.repo.inspur.com/redis:4          "docker-entrypoint.s…"   5 hours ago         Up 5 hours          6379/tcp                 1017-db_my_redis.1.przh3ua8gb3emgcagzu3rispk

6、运行镜像
[root@icloud_158 sso-authenticate]# docker run -d sso-authenticate:101706
4caad740d0ae2a00b287a388d21b36d20581c146736599c632ac172c364f81e4
You have new mail in /var/spool/mail/root


7、镜像交互




````