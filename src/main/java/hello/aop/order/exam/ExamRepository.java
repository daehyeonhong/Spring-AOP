package hello.aop.order.exam;

import hello.aop.order.exam.annotation.Trace;
import org.springframework.stereotype.Repository;

@Repository
public class ExamRepository {

    private static int seq = 0;

    /**
     * 5번에 1번 실패하는 로직
     */
    @Trace
    public String save(final String itemId) {
        seq++;
        if (seq % 5 == 0) {
            throw new IllegalStateException();
        }
        return "ok";
    }

}
