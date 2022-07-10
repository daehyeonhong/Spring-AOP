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
class ArgsTests {
    Method helloMethod;

    @BeforeEach
    void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    private AspectJExpressionPointcut pointcut(final String expression) {
        final AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        return pointcut;
    }

    @Test
    @DisplayName(value = "args")
    void args() {
        //hello(String)과 매칭
        assertThat(this.pointcut("args(String)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(this.pointcut("args(Object)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(this.pointcut("args()").matches(helloMethod, MemberServiceImpl.class)).isFalse();
        assertThat(this.pointcut("args(..)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(this.pointcut("args(*)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(this.pointcut("args(String,..)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /**
     * execution(* *(java.io.Serializable)): Method Signature로 판단(static)
     * args((java.io.Serializable)): runtime args로 판단(dynamic)
     */
    @Test
    @DisplayName(value = "argsVsExecution")
    void argsVsExecution() {
        //Args
        assertThat(this.pointcut("args(String)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(this.pointcut("args(java.io.Serializable)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(this.pointcut("args(Object)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
        //Execution
        assertThat(this.pointcut("execution(* *(String))").matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(this.pointcut("execution(* *(java.io.Serializable))").matches(helloMethod, MemberServiceImpl.class)).isFalse();
        assertThat(this.pointcut("execution(* *(Object))").matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }
}
