package hello.aop.pointcut;

import hello.aop.order.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * application.properties
 * spring.aop.proxy-target-class=true  => CGLIB(default)
 * spring.aop.proxy-target-class=false => JDK Dynamic Proxy
 */
@Slf4j
@SpringBootTest(properties = {"spring.aop.proxy-target-class=true"})
@Import(value = {ThisTargetTests.ThisTargetAspect.class})
class ThisTargetTests {
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName(value = "success")
    void success() {
        log.info("memberService Proxy={}", this.memberService.getClass());
        this.memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ThisTargetAspect {
        @Before(value = "this(hello.aop.order.aop.member.MemberService)")
        public void doThisInterface(final JoinPoint joinPoint) {
            log.info("[This-interface] {}", joinPoint.getSignature());
        }

        @Before(value = "target(hello.aop.order.aop.member.MemberService)")
        public void doTargetInterface(final JoinPoint joinPoint) {
            log.info("[Target-interface] {}", joinPoint.getSignature());
        }

        @Before(value = "this(hello.aop.order.aop.member.MemberServiceImpl)")
        public void doThis(final JoinPoint joinPoint) {
            log.info("[This] {}", joinPoint.getSignature());
        }

        @Before(value = "target(hello.aop.order.aop.member.MemberServiceImpl)")
        public void doTarget(final JoinPoint joinPoint) {
            log.info("[Target] {}", joinPoint.getSignature());
        }
    }
}
