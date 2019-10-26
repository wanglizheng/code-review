FROM 10.10.9.26:5000/openjdk:8-jre-alpine
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS 
WORKDIR /home/app
EXPOSE 8889
RUN sleep 10
ARG JAR_FILE
ADD target/${JAR_FILE} app.jar
CMD ["java","-server -Xms256m -Xmx512m -Xss1m -Dfile.encoding=UTF-8","-jar","app.jar"]