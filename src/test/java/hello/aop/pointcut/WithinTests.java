package hello.aop.pointcut;

import hello.aop.order.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import java.lang.reflect.Method;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class WithinTests {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        //java.lang.String hello.aop.order.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}", helloMethod);
    }

    @Test
    @DisplayName(value = "withinExact")
    void withinExact() {
        //given
        //when
        pointcut.setExpression("within(hello.aop.order.member.MemberServiceImpl)");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName(value = "withinStar")
    void withinStar() {
        //given
        //when
        pointcut.setExpression("within(hello.aop.order.member.*Service*)");

        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName(value = "withinSubPackage")
    void withinSubPackage() {
        //given
        //when
        pointcut.setExpression("within(hello.aop.order.aop..*)");

        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName(value = "타입의 타입에만 직접 적용, 인터페이스를 선정하면 안된다.")
    void withinSuperTypeFalse() {
        //given
        //when
        pointcut.setExpression("within(hello.aop.order.member.MemberService)");

        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName(value = "execution은 타입 기반, 인터페이스를 선정가능.")
    void executionSuperTypeTrue() {
        //given
        //when
        pointcut.setExpression("execution(* hello.aop.order.member.MemberService.*(..))");

        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
