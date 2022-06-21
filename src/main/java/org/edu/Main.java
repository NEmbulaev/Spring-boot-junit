package org.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {
    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(Main.class, args);

    }

   /* @Autowired
    private PersonRepository repository;

    @PostConstruct
    public void init() {
        repository.save(new Person(37,"Pushkin"));
    }*/
}
