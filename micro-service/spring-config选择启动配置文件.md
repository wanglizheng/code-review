# 选择 ihealth-hospital-server-test.yml 启动

````

spring:
  application:
    name: ihealth-hospital-server

  cloud:
    config:
      uri: ${CONFIG_URL:http://10.10.9.26:8889}
      username: ${CONFIG_USER:admin}
      password: ${CONFIG_PASSWORD:20080623}
      profile: test

ihealth-hospital-server-test.yml
ihealth-hospital-server-dev.yml
ihealth-hospital-server-product.yml


````