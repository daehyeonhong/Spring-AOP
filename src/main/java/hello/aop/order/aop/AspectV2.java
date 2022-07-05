package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {
    @Pointcut(value = "execution(* hello.aop.order..*(..))")
    private void allOrder() {
    }//Pointcut signature

    @Around(value = "allOrder()")
    public Object doLog(final ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[LOG] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }
}
