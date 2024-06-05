package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AppConfig.class);
        HelloWorld bean =
                (HelloWorld) applicationContext.getBean("helloworld");
        HelloWorld bean2 =
                (HelloWorld) applicationContext.getBean("helloworld");
        Cat catbean1 = applicationContext.getBean(Cat.class);
        Cat catbean2 = applicationContext.getBean(Cat.class);

        System.out.println(bean.getMessage());
        System.out.println(bean == bean2);
        System.out.println(catbean1 == catbean2);
    }
}
