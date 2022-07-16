package hello.aop.proxydiff;

import hello.aop.order.member.MemberService;
import hello.aop.proxydiff.proxy.ProxyDIAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"}) // JDK Dynamic Proxy
@Import(value = {ProxyDIAspect.class})
class ProxyDITests {
    @Autowired
    MemberService memberService;
//    @Autowired
//    private MemberServiceImpl memberServiceImpl;

    @Test
    @DisplayName(value = "go")
    void go() throws Exception {
        log.info("memberService class {}", memberService.getClass());
//        log.info("memberServiceImpl class {}", memberServiceImpl.getClass());
//        memberServiceImpl.hello("hello");
    }
}
