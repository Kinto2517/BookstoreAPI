package com.kinto2517.bookstoreapi.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceLoggingAspect.class);

    @Around("execution(* com.kinto2517.bookstoreapi.controller..*(..)) || " +
            "execution(* com.kinto2517.bookstoreapi.service..*(..))")
    public Object logPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        if (executionTime > 5) {
            logger.warn("Method {} in class {} took {} ms to execute.", joinPoint.getSignature().getName(),
                    joinPoint.getTarget().getClass().getName(), executionTime);
        }

        return result;
    }
}