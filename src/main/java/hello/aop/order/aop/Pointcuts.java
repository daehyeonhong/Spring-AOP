package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    @Pointcut(value = "execution(* hello.aop.order..*(..))")
    public void allOrder() {
    }//Pointcut signature

    @Pointcut(value = "execution(* *..*Service.*(..))")
    public void allService() {
    }

    @Pointcut(value = "allOrder()&&allService()")
    public void orderAndService() {
    }
}
