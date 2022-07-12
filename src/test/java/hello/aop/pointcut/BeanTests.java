package hello.aop.pointcut;

import hello.aop.order.OrderService;
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
@Import(value = {BeanTests.BeanAspect.class})
@SpringBootTest
class BeanTests {
    @Autowired
    OrderService orderService;

    @Test
    @DisplayName(value = "success")
    void success() {
        this.orderService.orderItem("itemA");
    }

    @Aspect
    static class BeanAspect {
        @Around(value = "bean(orderService)||bean(*Repository)")
        public Object doLog(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            log.info("[Bean] {}", proceedingJoinPoint.getSignature());
            return proceedingJoinPoint.proceed();
        }
    }
}
