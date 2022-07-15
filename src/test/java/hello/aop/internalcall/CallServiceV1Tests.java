package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest(properties = {"spring.main.allow-circular-references=true"})
@Import(value = {CallLogAspect.class})
class CallServiceV1Tests {
    @Autowired
    private CallServiceV1 callServiceV1;

    @Test
    @DisplayName(value = "internal")
    void internal() {
        this.callServiceV1.internal();
    }

    @Test
    @DisplayName(value = "external")
    void external() {
        this.callServiceV1.external();
    }
}
