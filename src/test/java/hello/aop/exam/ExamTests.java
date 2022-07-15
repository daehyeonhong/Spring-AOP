package hello.aop.exam;

import hello.aop.order.exam.ExamService;
import hello.aop.order.exam.aop.RetryAspect;
import hello.aop.order.exam.aop.TraceAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import(value = {TraceAspect.class, RetryAspect.class})
class ExamTests {
    @Autowired
    private ExamService examService;

    @Test
    @DisplayName(value = "test")
    void test() {
        for (int i = 0; i < 5; i++) {
            log.info("client request i={}", i);
            this.examService.request("data" + i);
        }
        //given

        //when

        //then
    }
}
