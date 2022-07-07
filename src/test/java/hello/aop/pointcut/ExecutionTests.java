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
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        //public java.lang.String hello.aop.order.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}", helloMethod);
    }

    @Test
    @DisplayName(value = "execution Pointcut 가장 정확")
    public void exactMatch() {
        //given
        //when
        pointcut.setExpression("execution(public String hello.aop.order.aop.member.MemberServiceImpl.hello(String))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName(value = "allMatch")
    public void allMatch() {
        //given
        //when
        pointcut.setExpression("execution(* *(..))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName(value = "nameMatch")
    public void nameMatch() {
        //given
        //when
        pointcut.setExpression("execution(* hello(..))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName(value = "patternMatch")
    public void patternMatch() {
        //given
        //when
        pointcut.setExpression("execution(* hel*(..))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName(value = "patternMatch2")
    public void patternMatch2() {
        //given
        //when
        pointcut.setExpression("execution(* *el*(..))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName(value = "patternMatchFalse")
    public void patterMatchFalse() {
        //given
        //when
        pointcut.setExpression("execution(* seoul(..))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName(value = "packageExactMatch1")
    public void packageExactMatch1() {
        //given
        //when
        pointcut.setExpression("execution(* hello.aop.order.aop.member.MemberServiceImpl.hello(..))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName(value = "packageExactFalse")
    public void packageExactFalse() {
        //given
        //when
        pointcut.setExpression("execution(* hello.aop.order.aop.*.*(..))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName(value = "packageMatchSubPackage1")
    public void packageMatchSubPackage1() {
        //given
        //when
        pointcut.setExpression("execution(* hello.aop.member..*.*(..))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName(value = "packageMatchSubPackage2")
    public void packageMatchSubPackage2() {
        //given
        //when
        pointcut.setExpression("execution(* hello.aop..*.*(..))");
        //then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
