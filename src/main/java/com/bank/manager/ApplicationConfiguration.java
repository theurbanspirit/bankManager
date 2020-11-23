package com.bank.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.apache.catalina.core.ApplicationContext;

@Configuration
public class ApplicationConfiguration {
    @Autowired
    private ApplicationContext applicationContext;
}
