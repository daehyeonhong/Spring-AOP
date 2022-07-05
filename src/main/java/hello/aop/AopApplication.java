package hello.aop;

import hello.aop.order.aop.AspectV4Pointcut;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
//@Import(value = {AspectV1.class})
//@Import(value = {AspectV2.class})
//@Import(value = {AspectV3.class})
@Import(value = {AspectV4Pointcut.class})
public class AopApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopApplication.class, args);
    }

}
