package instruments;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;

public class ObjectMapperWrapper {
    private final ObjectMapper objectMapper;

    public ObjectMapperWrapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T readValue(File file, Class<T> valueType) {
        try {
            return objectMapper.readValue(file, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T readValue(String content, Class<T> valueType) {
        try {
            return objectMapper.readValue(content, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T readValue(String content, TypeReference<T> valueType) {
        try {
            return objectMapper.readValue(content, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void findAndRegisterModules() {
        objectMapper.findAndRegisterModules();
    }

    public void registerModule(SimpleModule simpleModule) {
        objectMapper.registerModule(simpleModule);
    }

    public void registerModules(SimpleModule... simpleModule) {
        objectMapper.registerModules(simpleModule);
    }

    public byte[] writeValueAsBytes(Object object) {
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
