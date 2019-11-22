
##Maven 将本地jar包添加到本地仓库



`````
准备好本地jar包，我以zookeeper-3.4.10.jar为例，本地路径为E:\softwares\zookeeper-3.4.10\zookeeper-3.4.10.jar
指定jar包在maven仓库的groupId，artifactId，version等信息，此处保留zookeeper-3.4.10.jar的原本信息

mvn install:install-file -Dfile=E:\softwares\zookeeper-3.4.10\zookeeper-3.4.10.jar -DgroupId=org.apache.zookeeper -DartifactId=zookeeper -Dversion=3.4.10 -Dpackaging=jar

mvn install:install-file -Dfile=E:\softwares\zookeeper-3.4.10\zookeeper-3.4.10.jar -DgroupId=org.apache.zookeeper -DartifactId=zookeeper -Dversion=3.4.10 -Dpackaging=jar

`````