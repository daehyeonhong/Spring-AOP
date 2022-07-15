package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
@Import(value = {CallLogAspect.class})
class CallServiceV0Tests {
    @Autowired
    private CallServiceV0 callServiceV0;

    @Test
    @DisplayName(value = "internal")
    void internal() {
        this.callServiceV0.internal();
    }

    @Test
@DisplayName(value = "external")
    void external() {
        this.callServiceV0.external();
    }
}
