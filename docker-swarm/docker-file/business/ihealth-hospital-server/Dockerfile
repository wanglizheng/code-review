FROM 10.10.9.26:5000/openjdk:8-jre-alpine

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    APP_SLEEP=10 \
    JAVA_OPTS="-server -Xms256m -Xmx512m -Xss1m -Dfile.encoding=UTF-8 -Dspring.cloud.config.uri=http://172.22.19.158:8889 -Dspring.cloud.config.username=admin -Dspring.cloud.config.password=20080623"

RUN adduser -D -s /bin/sh app
WORKDIR /home/app

ADD entrypoint.sh entrypoint.sh
RUN chmod 755 entrypoint.sh && chown app:app entrypoint.sh
USER app

ENTRYPOINT ["./entrypoint.sh"]

EXPOSE 29999

ARG JAR_FILE
ADD target/${JAR_FILE} app.jar
