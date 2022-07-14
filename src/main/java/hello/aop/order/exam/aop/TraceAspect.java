package hello.aop.order.exam.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class TraceAspect {
    @Before(value = "@annotation(hello.aop.order.exam.annotation.Trace)")
    public void doTrace(final JoinPoint joinPoint) {
        final Object[] args = joinPoint.getArgs();
        log.info("[trace] {} args={}", joinPoint.getSignature(), args);
    }
}
