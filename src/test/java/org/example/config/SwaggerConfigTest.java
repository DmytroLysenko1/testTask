package org.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.example.constant.TestMessages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SwaggerConfigTest {
    @Autowired
    private OpenAPI customOpenAPI;

    @Test
    public void testOpenApiBeanCreation() {
        assertNotNull(customOpenAPI);
        assertEquals(TestMessages.SWAGGER_TITLE, customOpenAPI.getInfo().getTitle());
        assertEquals(TestMessages.SWAGGER_VERSION, customOpenAPI.getInfo().getVersion());
        assertEquals(TestMessages.SWAGGER_DESCRIPTION, customOpenAPI.getInfo().getDescription());
    }
}
