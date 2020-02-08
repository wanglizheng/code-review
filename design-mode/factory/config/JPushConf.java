package com.inspur.jpush.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;


    @Configuration
    @ConfigurationProperties("jpush")
    public class JPushConf {

        private List<ConfItem> conf;

        public List<ConfItem> getConf() {
            return conf;
        }

        public void setConf(List<ConfItem> conf) {
            this.conf = conf;
        }
    }
