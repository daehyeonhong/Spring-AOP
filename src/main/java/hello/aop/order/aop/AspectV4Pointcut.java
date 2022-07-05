package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class AspectV4Pointcut {
    @Around(value = "hello.aop.order.aop.Pointcuts.allOrder()")
    public Object doLog(final ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[LOG] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

    //hello.aop.order package 의 하위에 있으며 classNamePattern *Service
    @Around(value = "hello.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(final ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[Transaction Start]{}", joinPoint.getSignature());
            final Object result = joinPoint.proceed();
            log.info("[Transaction Commit]{}", joinPoint.getSignature());
            return result;
        } catch (final Exception exception) {
            log.error("[Transaction Rollback]{}", joinPoint.getSignature());
            throw exception;
        } finally {
            log.error("[Transaction Release]{}", joinPoint.getSignature());
        }
    }
}
