FROM 10.10.9.26:5000/openjdk:8-jre-alpine
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS
WORKDIR /home/app
EXPOSE 8866
RUN sleep 20
ARG JAR_FILE
ADD target/${JAR_FILE} app.jar
CMD ["java","-Dfile.encoding=UTF-8","-jar","app.jar"]