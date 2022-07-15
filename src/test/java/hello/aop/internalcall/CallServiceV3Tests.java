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
class CallServiceV3Tests {
    @Autowired
    private CallServiceV3 callServiceV3;

    @Test
    @DisplayName(value = "internal")
    void internal() {
        this.callServiceV3.external();
    }
}
