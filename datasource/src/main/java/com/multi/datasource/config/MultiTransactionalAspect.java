package com.multi.datasource.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

@Aspect
@Component
public class MultiTransactionalAspect implements Ordered {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(com.multi.datasource.config.MultiTransactional)")
    public void dataSourcePointCut() {
    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object proceed = null;
        try {
            proceed = point.proceed();
        } catch (Exception e) {
            List<Connection> connections = MultiTransactionalHolder.get();
            for (Connection connection1 : connections) {
                connection1.rollback();
            }
            throw e;
        }
        List<Connection> connections = MultiTransactionalHolder.get();
        for (Connection connection1 : connections) {
            connection1.commit();
        }
        return proceed;
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
