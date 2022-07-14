package hello.aop.order.member;

import hello.aop.order.member.annotation.ClassAop;
import hello.aop.order.member.annotation.MethodAop;
import org.springframework.stereotype.Component;

@ClassAop
@Component
public class MemberServiceImpl implements MemberService {
    @Override
    @MethodAop(value = "test value")
    public String hello(final String param) {
        return "ok";
    }

    public String internal(final String param) {
        return "ok";
    }
}
