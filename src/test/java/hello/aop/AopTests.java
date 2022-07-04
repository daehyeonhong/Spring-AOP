package hello.aop;

import hello.aop.order.OrderRepository;
import hello.aop.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.aop.support.AopUtils.isAopProxy;

@Slf4j
@SpringBootTest
class AopTests {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    void aopInfo() {
        log.info("isAopProxy, orderService={}", isAopProxy(this.orderService));
        log.info("isAopProxy, orderRepository={}", isAopProxy(this.orderRepository));
    }

    @Test
    void success() {
        this.orderService.orderItem("itemA");
    }

    @Test
    void exception() {
        assertThatThrownBy(() -> this.orderService.orderItem("ex")).isInstanceOf(IllegalStateException.class);
    }
}
