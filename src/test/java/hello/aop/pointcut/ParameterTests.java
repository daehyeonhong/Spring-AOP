package hello.aop.pointcut;

import hello.aop.order.member.MemberService;
import hello.aop.order.member.annotation.ClassAop;
import hello.aop.order.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import(value = {ParameterTests.ParameterAspect.class})
public class ParameterTests {
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
    static class ParameterAspect {
        @Pointcut(value = "execution(* hello.aop.order.member..*.*(..))")
        private void allMember() {
        }

        @Around(value = "allMember()")
        public Object logArgs(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            final Object arg1 = proceedingJoinPoint.getArgs()[0];
            log.info("[logArgs1]{}, arg={}", proceedingJoinPoint.getSignature(), arg1);
            return proceedingJoinPoint.proceed();
        }

        @Around(value = "allMember()&&args(arg,..)")
        public Object logArg2(final ProceedingJoinPoint proceedingJoinPoint, final Object arg) throws Throwable {
            log.info("[logArgs1]{}, arg={}", proceedingJoinPoint.getSignature(), arg);
            return proceedingJoinPoint.proceed();
        }

        @Before(value = "allMember()&&args(arg,..)")
        public void logArgs3(final String arg) {
            log.info("[logArgs3] arg={}", arg);
        }

        @Before(value = "allMember()&&this(obj)")
        public void thisArgs(final JoinPoint joinPoint, MemberService obj) {
            log.info("[This]{}, obj={}", joinPoint.getSignature(), obj.getClass());
        }

        @Before(value = "allMember()&&target(obj)")
        public void targetArgs(final JoinPoint joinPoint, MemberService obj) {
            log.info("[Target]{}, obj={}", joinPoint.getSignature(), obj.getClass());
        }

        @Before(value = "allMember()&&@target(annotation)")
        public void atTarget(final JoinPoint joinPoint, final ClassAop annotation) {
            log.info("[@Target]{}, obj={}", joinPoint.getSignature(), annotation);
        }

        @Before(value = "allMember()&&@within(annotation)")
        public void atWithin(final JoinPoint joinPoint, final ClassAop annotation) {
            log.info("[@Within]{}, obj={}", joinPoint.getSignature(), annotation);
        }

        @Before(value = "allMember()&&@annotation(annotation))")
        public void atAnnotation(final JoinPoint joinPoint, final MethodAop annotation) {
            log.info("[@Annotation]{}, obj={}", joinPoint.getSignature(), annotation.value());
        }
    }
}
