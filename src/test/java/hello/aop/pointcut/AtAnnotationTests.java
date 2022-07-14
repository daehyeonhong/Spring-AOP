package hello.aop.pointcut;

import hello.aop.order.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import(value = {AtAnnotationTests.AtAnnotationAspect.class}
)
class AtAnnotationTests {
    @Autowired
    MemberService memberService;

    @Test
    @DisplayName(value = "success")
    void success() {
        log.info("memberService Proxy={}", this.memberService.getClass());
        this.memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class AtAnnotationAspect {
        @Around("@annotation(hello.aop.order.member.annotation.MethodAop)")
        public Object doAtAnnotation(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            log.info("[@Annotation] {}", proceedingJoinPoint.getSignature());
            return proceedingJoinPoint.proceed();
        }
    }
}
