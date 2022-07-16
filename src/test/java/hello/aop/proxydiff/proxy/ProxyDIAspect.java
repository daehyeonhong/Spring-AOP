package hello.aop.proxydiff.proxy;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class ProxyDIAspect {
    @Before(value = "execution(* hello.aop..*.*(..))")
    public void doTrace(final JoinPoint joinPoint) {
        log.info("[ProxyDIAdvice] {}",joinPoint.getSignature());
    }
}
