package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {
    @Around(value = "hello.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(final ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            //@Before
            log.info("[Transaction Start]{}", joinPoint.getSignature());
            final Object result = joinPoint.proceed();
            //@AfterReturning
            log.info("[Transaction Commit]{}", joinPoint.getSignature());
            return result;
        } catch (final Exception exception) {
            //@AfterThrowing
            log.error("[Transaction Rollback]{}", joinPoint.getSignature());
            throw exception;
        } finally {
            //@After
            log.error("[Transaction Release]{}", joinPoint.getSignature());
        }
    }

    @Before(value = "hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(final JoinPoint joinPoint) {
        log.info("[Before] {}", joinPoint.getSignature());
    }

    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(final JoinPoint joinPoint, final Object result) {
        log.info("[Return] {} return {}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "exception")
    public void doAfterThrowing(final JoinPoint joinPoint, final Exception exception) {
        log.info("[Exception] {} message={}", joinPoint.getSignature(), exception.getMessage());
    }

    @After(value = "hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(final JoinPoint joinPoint) {
        log.info("[After] {}", joinPoint.getSignature());
    }
}
