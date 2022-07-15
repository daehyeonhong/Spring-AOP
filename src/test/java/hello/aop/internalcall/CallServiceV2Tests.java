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
class CallServiceV2Tests {
    @Autowired
    private CallServiceV2 callServiceV2;

    @Test
    @DisplayName(value = "internal")
    void internal() {
        this.callServiceV2.internal();
    }

    @Test
    @DisplayName(value = "external")
    void external() {
        this.callServiceV2.external();
    }
}
