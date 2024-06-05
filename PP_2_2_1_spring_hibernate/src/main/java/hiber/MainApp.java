package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {

    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        userService.add(new User("Боря", "Lastname1", "user1@mail.ru", new Car("Зил", 1)));
        userService.add(new User("Вася", "Lastname2", "user2@mail.ru", new Car("Камаз", 2)));
        userService.add(new User("Петя", "Lastname3", "user3@mail.ru", new Car("Урал", 3)));
        userService.add(new User("Дима", "Lastname4", "user4@mail.ru", new Car("ВАЗ", 4)));

        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println("Car = " + user.getCar());
        }

        context.close();
    }
}
