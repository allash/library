package ru.otus.library.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.library.LibraryApplication;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = { LibraryApplication.class },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class DerpTest {


    @Test
    public void canRunSomething() {

        int a = 3;
        int b =  a + 3;

        System.out.println("hello");


        assertThat(a + 3).isEqualTo(b);
    }
}
