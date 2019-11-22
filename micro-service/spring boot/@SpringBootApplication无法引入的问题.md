###在自己创建的HelloApplication中，@SpringBootApplication无法被解析引入。


springboot的包冲突了所致。需要删掉 c:\users\youruser\.m2\repository\org\springframework\boot 目录下的spring-boot-autoconfigure 文件夹即可，然后在工程中maven -> update project即可