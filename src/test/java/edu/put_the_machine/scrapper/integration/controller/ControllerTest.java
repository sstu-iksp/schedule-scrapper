package edu.put_the_machine.scrapper.integration.controller;

import edu.put_the_machine.scrapper.model.Teacher;
import instruments.ObjectMapperWrapper;
import instruments.factory.interfaces.EntitiesDbFactory;
import instruments.helper.interfaces.MockMvcHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = {"instruments"})
public abstract class ControllerTest {
    protected @Autowired MockMvcHelper mockMvc;
    protected @Autowired ObjectMapperWrapper objectMapper;
    protected @Autowired EntitiesDbFactory entitiesDbFactory;


    protected <T> void getByIdTest(String addressPart, Supplier<T> entitySupplier, Long id, Class<T> type) {
        T expectedEntity = entitySupplier.get();

        T returnedEntity = mockMvc.sendRequest(
                get("/api/" + addressPart + "/" + id),
                MockMvcHelper.expectJsonAndOkStatus()
        ).readBodyAsJsonByType(type);

        assertAll(
                () -> assertNotNull(returnedEntity),
                () -> assertEquals(expectedEntity, returnedEntity)
        );
    }
}
