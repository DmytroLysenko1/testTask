package org.example.config;

import org.example.constant.TestMessages;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ModelMapperConfigTest {

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testModelMapperBeanCreation() {
        assertNotNull(modelMapper, TestMessages.MODEL_MAPPER_OK);
    }
}