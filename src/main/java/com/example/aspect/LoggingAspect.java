package com.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {
    private final Logger log = Logger.getLogger(LoggingAspect.class.getName());

    @Before("execution(* com.example.service.impl.*.*(..))")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info(String.format("Executing method: %s with arguments: %s", methodName, args));
    }

    @AfterReturning(value = "execution(* com.example.service.impl.*.*(..))", returning = "result")
    public void logAfterMethodExecution(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info(String.format("Method executed: %s, Result: %s", methodName, result));
    }

    @AfterThrowing(value = "execution(* com.example.service.impl.*.*(..))", throwing = "exception")
    public void logAfterException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        log.severe(String.format("Exception in method: %s, Exception: %s", methodName, exception.getMessage()));
    }
}
