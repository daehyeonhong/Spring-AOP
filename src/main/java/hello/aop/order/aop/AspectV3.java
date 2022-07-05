package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV3 {
    @Pointcut(value = "execution(* hello.aop.order..*(..))")
    private void allOrder() {
    }//Pointcut signature

    @Pointcut(value = "execution(* *..*Service.*(..))")
    public void allService() {
    }

    @Around(value = "allOrder()")
    public Object doLog(final ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[LOG] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

    //hello.aop.order package 의 하위에 있으며 classNamePattern *Service
    @Around(value = "allOrder()&&allService()")
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
