package com.bank.manager;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    @Autowired
    private ApplicationContext applicationContext;
}
