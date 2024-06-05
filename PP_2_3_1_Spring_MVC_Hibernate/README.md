Практическая задача

**Условие:**  
Перейдем к созданию рабочего web-приложения. Все ключевые моменты были рассмотрены в предыдущих задачах. Теперь вам требуется их сопоставить и связать в один проект.  
Используя наработки по mvc и hibernate соберите CRUD-приложение.

**Задание:**  
1. Написать CRUD-приложение. Использовать Spring MVC + Hibernate.  
2. Должен быть класс `User` с произвольными полями (id, name и т.п.).  
3. В приложении должна быть страница, на которую выводятся все юзеры с возможностью добавлять, удалять и изменять юзера.  
4. Конфигурация Spring через `JavaConfig` и аннотации, по аналогии с предыдущими проектами. Без использования xml. Без Spring Boot.  
5. Внесите изменения в конфигурацию для работы с базой данных. Вместо SessionFactory должен использоваться EntityManager.  
6. Используйте только GET/POST маппинги  
7. Используйте ReqestParam аннотацию, использование аннотации PathVariable запрещено

Ссылки:  
[https://javastudy.ru/spring-mvc/java-config-web-xml/](https://java-master.com/spring-mvs-%D0%BD%D0%B0%D1%81%D1%82%D1%80%D0%BE%D0%B9%D0%BA%D0%B0-%D0%B1%D0%B5%D0%B7-xml-web-xml/)  
[https://habr.com/ru/post/222579/](https://habr.com/ru/post/222579/)  
[https://www.baeldung.com/the-persistence-layer-with-spring-and-jpa](https://www.baeldung.com/the-persistence-layer-with-spring-and-jpa)
