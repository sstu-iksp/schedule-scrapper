package edu.put_the_machine.scrapper.controller;

import instruments.ObjectMapperWrapper;
import instruments.helper.interfaces.MockMvcHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = {"instruments"})
public abstract class ControllerTest {
    protected @Autowired MockMvcHelper mockMvc;
    protected @Autowired ObjectMapperWrapper objectMapper;
}
