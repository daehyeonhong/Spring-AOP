package hello.aop.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void orderItem(final String itemId) {
        log.info("[orderService] execute");
        log.info("{}", this.orderRepository.save(itemId));
    }
}
