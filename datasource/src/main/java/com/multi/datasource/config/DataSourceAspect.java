package com.multi.datasource.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Aspect
@Component
public class DataSourceAspect implements Ordered {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(com.multi.datasource.config.DataSource)")
    public void dataSourcePointCut() {

    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        DataSource ds = method.getAnnotation(DataSource.class);
        String value;
        if(ds == null){
            value = DataSourceNames.FIRST;
            logger.debug("set datasource is " + DataSourceNames.FIRST);
        }else {
            value = ds.name();
            logger.debug("set datasource is " + ds.name());
        }

        DynamicContextHolder.push(value);
        try {
            return point.proceed();
        } finally {
            DynamicContextHolder.poll();
            logger.debug("clean datasource");
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }

}
