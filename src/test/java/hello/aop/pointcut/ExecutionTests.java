package hello.aop.pointcut;

import hello.aop.order.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import java.lang.reflect.Method;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ExecutionTests {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        //java.lang.String hello.aop.order.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}", helloMethod);
    }

    @Test
    @DisplayName(value = "execution Pointcut 가장 정확")
    void exactMatch() {
        //given
        //when
        pointcut.setExpression("execution(String hello.aop.order.aop.member.MemberServiceImpl.hello(String))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName(value = "allMatch")
    void allMatch() {
        //given
        //when
        pointcut.setExpression("execution(* *(..))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName(value = "nameMatch")
    void nameMatch() {
        //given
        //when
        pointcut.setExpression("execution(* hello(..))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName(value = "patternMatch")
    void patternMatch() {
        //given
        //when
        pointcut.setExpression("execution(* hel*(..))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName(value = "patternMatch2")
    void patternMatch2() {
        //given
        //when
        pointcut.setExpression("execution(* *el*(..))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName(value = "patternMatchFalse")
    void patterMatchFalse() {
        //given
        //when
        pointcut.setExpression("execution(* seoul(..))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName(value = "packageExactMatch1")
    void packageExactMatch1() {
        //given
        //when
        pointcut.setExpression("execution(* hello.aop.order.aop.member.MemberServiceImpl.hello(..))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName(value = "packageExactFalse")
    void packageExactFalse() {
        //given
        //when
        pointcut.setExpression("execution(* hello.aop.order.aop.*.*(..))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName(value = "packageMatchSubPackage1")
    void packageMatchSubPackage1() {
        //given
        //when
        pointcut.setExpression("execution(* hello.aop.member..*.*(..))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName(value = "packageMatchSubPackage2")
    void packageMatchSubPackage2() {
        //given
        //when
        pointcut.setExpression("execution(* hello.aop..*.*(..))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName(value = "typeExactMatch")
    void typeExactMatch() {
        //given
        //when
        pointcut.setExpression("execution(* hello.aop.order.aop.member.MemberServiceImpl.hello(..))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName(value = "typeMatchSuperType")
    void typeMatchSuperType() {
        //given
        //when
        pointcut.setExpression("execution(* hello.aop.order.aop.member.MemberService.*(..))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName(value = "typeMatchInternal")
    void typeMatchInternal() throws NoSuchMethodException {
        //given
        pointcut.setExpression("execution(* hello.aop.order.aop.member.MemberService.*(..))");
        //when
        final Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        //then
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName(value = "typeMatchNoSuperTypeMethodFalse")
    void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {
        //given
        pointcut.setExpression("execution(* hello.aop.order.aop.member.MemberServiceImpl.*(..))");
        //when
        final Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        //then
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName(value = "argsMatch")
    void argsMatch() {
        //given
        pointcut.setExpression("execution(* *(String))");
        //when
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
        //then
    }

    @Test
    @DisplayName(value = "argsMatch")
    void argsMatchWithoutArgs() {
        //given
        pointcut.setExpression("execution(* *())");
        //when
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
        //then
    }

    /**
     * 모든 타입의 파라미터 한개
     */
    @Test
    @DisplayName(value = "argsMatchStar")
    void argsMatchStar() {
        //given
        //when
        pointcut.setExpression("execution(* *(*))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /**
     * 개수와 무관하게 모든 파라미터, 모든 타입 허용
     * (String), (String, Object), (String, Object, int)
     */
    @Test
    @DisplayName(value = "argsMatchAll")
    void argsMatchAll() {
        //given
        //when
        pointcut.setExpression("execution(* *(..))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /**
     * String Type으로 시작, 숫자와 무관하게 모든 파라미터, 모든 타입 허용
     * (String), (String, Object), (String, Object, int)
     */
    @Test
    @DisplayName(value = "argsMatchComplex")
    void argsMatchComplex() {
        //given
        //when
        pointcut.setExpression("execution(* *(String, ..))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
