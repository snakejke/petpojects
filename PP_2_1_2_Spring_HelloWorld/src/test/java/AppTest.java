import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spring.AppConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class AppTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void mainTest() {
        spring.HelloWorld tree =
                applicationContext.getBean(spring.HelloWorld.class);
        spring.HelloWorld leaf =
                applicationContext.getBean(spring.HelloWorld.class);

        spring.Cat one = applicationContext.getBean(spring.Cat.class);
        spring.Cat two = applicationContext.getBean(spring.Cat.class);

        Assert.assertSame("Тест провален, не корректная настройка бина spring.HelloWorld", tree, leaf);
        Assert.assertNotSame("Тест провален, не корректная настройка бина spring.Cat", one, two);
    }
}
