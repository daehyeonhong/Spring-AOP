package hello.aop.order.exam.aop;

import hello.aop.order.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {
    @Around(value = "@annotation(retry)")
    public Object doRetry(final ProceedingJoinPoint proceedingJoinPoint, final Retry retry) throws Throwable {
        log.info("[retry] {} retry={}", proceedingJoinPoint.getSignature(), retry);

        final int maxRetry = retry.value();
        Exception exceptionHolder = null;
        for (int retryCount = 1; retryCount <= maxRetry; retryCount++) {
            try {
                log.info("[retry] try count={}/{}", retryCount, maxRetry);
                return proceedingJoinPoint.proceed();
            } catch (final Exception exception) {
                exceptionHolder = exception;
            }
        }
        assert exceptionHolder != null;
        throw exceptionHolder;
    }
}
