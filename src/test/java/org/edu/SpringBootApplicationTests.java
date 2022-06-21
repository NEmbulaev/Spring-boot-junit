package org.edu;

import org.edu.entity.Person;
import org.edu.repository.PersonRepository;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Main.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)

class SpringBootApplicationTests {

    @Autowired
    private PersonRepository pRepo;

    @Autowired
    private MockMvc mvc;

    @After
    public void resetDb() {
        pRepo.deleteAll();
    }

    @Test
    public void whenValidInput_thenCreatePerson() throws IOException, Exception {
        Person bob = new Person(23, "bob");
        mvc.perform(post("/api/persons").contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(bob)));

        List<Person> found = pRepo.findAll();
        assertThat(found).extracting(Person::getName)
                .containsOnly("bob");
    }

    @Test
    public void givenEmployees_whenGetEmployees_thenStatus200() throws Exception {
        createTestPerson(1, 23, "bob");
        createTestPerson(2, 32, "alex");

        mvc.perform(get("/api/persons").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].name", is("bob")))
                .andExpect(jsonPath("$[1].name", is("alex")));

    }


    //TODO Delete Tests
    //TODO Update Tests
    //TODO Wrong input data Tests


    private void createTestPerson(long id, int age, String name) {
        Person p = new Person(id, age, name);
        pRepo.saveAndFlush(p);
    }
}
