package com.challenge.heroes.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeMethodAspect
{
   private static Logger LOG = LoggerFactory.getLogger(TimeMethodAspect.class);
   @Around("execution(* *(..)) && @annotation(com.challenge.heroes.utils.TimeMethod)")
    public Object log(ProceedingJoinPoint point) throws Throwable
   {
      long start = System.currentTimeMillis();
       Object result = point.proceed();
       LOG.info("className={}, methodName={}, timeMs={},threadId={}",new Object[]{
            MethodSignature.class.cast(point.getSignature()).getDeclaringTypeName(),
            MethodSignature.class.cast(point.getSignature()).getMethod().getName(),
            System.currentTimeMillis() - start,
            Thread.currentThread().getId()}
          );
       return result;
   }
}