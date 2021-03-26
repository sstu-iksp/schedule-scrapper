package instruments.helper.impl;

import instruments.ObjectMapperWrapper;
import instruments.helper.MockMvcResponseException;
import instruments.helper.interfaces.MockMvcResponse;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class MockMvcResponseImpl implements MockMvcResponse {
    private final ObjectMapperWrapper objectMapper;
    private final ResultActions resultActions;

    public MockMvcResponseImpl(ObjectMapperWrapper objectMapper, ResultActions resultActions) {
        this.objectMapper = objectMapper;
        this.resultActions = resultActions;
    }

    @Override
    public String readBodyAsString() {
        try {
            return resultActions
                    .andReturn()
                    .getResponse()
                    .getContentAsString(StandardCharsets.UTF_8);
        } catch (UnsupportedEncodingException e) {
            throw new MockMvcResponseException("Encoding of gotten body isn't supported.", e);
        }
    }

    @Override
    public <T> T readBodyAsJsonByType(Class<T> type) {
        return objectMapper.readValue(
                readBodyAsString(),
                type
        );
    }

}
