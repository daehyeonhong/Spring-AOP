package hello.aop.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class OrderRepository {
    public String save(final String itemId) {
        log.info("[orderRepository] execute");
        if (itemId.equals("ex")) {
            throw new IllegalStateException();
        }
        return "ok";
    }
}
