package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
public class AspectV5Order {
    @Aspect
    @Order(value = 2)
    public static class LogAspect {
        @Around(value = "hello.aop.order.aop.Pointcuts.allOrder()")
        public Object doLog(final ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[LOG] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }

    @Aspect
    @Order(value = 1)
    public static class TransactionAspect {
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
}
