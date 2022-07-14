package hello.aop.pointcut;

import hello.aop.order.member.annotation.ClassAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(value = {AtTargetAtWithinTests.Config.class})
@SpringBootTest
class AtTargetAtWithinTests {
    @Autowired
    Child child;

    @Test
    @DisplayName(value = "success")
    void success() {
        log.info("child Proxy={}", this.child.getClass());
        this.child.childMethod();
        this.child.parentMethod();
    }

    static class Config {
        @Bean
        public Parent parent() {
            return new Parent();
        }

        @Bean
        public Child child() {
            return new Child();
        }

        @Bean
        public AtTargetAtWithinAspect atTargetAtWithinAspect() {
            return new AtTargetAtWithinAspect();
        }
    }

    static class Parent {
        public void parentMethod() {
        }
    }

    @ClassAop
    static class Child extends Parent {
        public void childMethod() {
        }
    }

    @Slf4j
    @Aspect
    static class AtTargetAtWithinAspect {
        @Around(value = "execution(* hello.aop..*(..))&&@target(hello.aop.order.member.annotation.ClassAop)")
        public Object atTarget(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            log.info("[@Target] {}", proceedingJoinPoint.getSignature());
            return proceedingJoinPoint.proceed();

        }

        @Around(value = "execution(* hello.aop..*(..))&&@within(hello.aop.order.member.annotation.ClassAop)")
        public Object atWithin(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            log.info("[@Within] {}", proceedingJoinPoint.getSignature());
            return proceedingJoinPoint.proceed();
        }
    }
}
