package hello.aop.order.exam;

import hello.aop.order.exam.annotation.Trace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExamService {
    private final ExamRepository examRepository;

    @Trace
    public void request(final String itemId) {
        final String savedItem = this.examRepository.save(itemId);
        log.info("{}", savedItem);
    }
}
