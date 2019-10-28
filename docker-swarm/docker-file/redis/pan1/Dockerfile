FROM registry.cn-beijing.aliyuncs.com/rj-bai/centos:7.5
RUN yum -y install wget make gcc && yum clean all && \
    wget http://download.redis.io/releases/redis-5.0.3.tar.gz && tar zxf redis-5.0.3.tar.gz && rm -f redis-5.0.3.tar.gz && \
    cd redis-5.0.3/ && make && make install
COPY start.sh /
COPY redis.conf /
CMD ["/bin/bash", "/start.sh"]