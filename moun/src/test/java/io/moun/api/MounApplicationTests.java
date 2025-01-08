package io.moun.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes=MounApplication.class)
@ActiveProfiles("test")
class MounApplicationTests {

    @Test
    void contextLoads() {
    }

}
