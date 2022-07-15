package hello.aop.proxydiff;

import hello.aop.order.member.MemberService;
import hello.aop.order.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
class ProxyCastingTests {
    @Test
    @DisplayName(value = "JDKProxy")
    void JDKProxy() {
        final MemberServiceImpl target = new MemberServiceImpl();
        final ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false);

        //Proxy casting to Interface
        final MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();
        log.info("proxy class={}", memberServiceProxy.getClass());

        //JDKDynamicProxy to Impl class
        Assertions.assertThrows(ClassCastException.class, () -> {
            final MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        });
    }

    @Test
    @DisplayName(value = "CGLIBProxy")
    void CGLIBProxy() {
        final MemberServiceImpl target = new MemberServiceImpl();
        final ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);

        //Proxy casting to Interface
        final MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();
        log.info("proxy class={}", memberServiceProxy.getClass());
        //CGLIB to Impl class
        final MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
    }
}
