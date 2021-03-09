package instruments.helper.impl;

import instruments.ObjectMapperWrapper;
import instruments.helper.MockMvcResponseException;
import instruments.helper.impl.MockMvcResponseImpl;
import instruments.helper.interfaces.MockMvcHelper;
import instruments.helper.interfaces.MockMvcResponse;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@TestComponent
public class MockMvcHelperImpl implements MockMvcHelper {
    private final MockMvc mockMvc;
    private final ObjectMapperWrapper objectMapper;

    public MockMvcHelperImpl(MockMvc mockMvc, ObjectMapperWrapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Override
    public MockMvcResponse sendRequest(MockHttpServletRequestBuilder requestBuilder, ResultMatcher... resultMatchers) {
        try {
            return buildMockMvcResponse(requestBuilder, resultMatchers);
        } catch (Exception e) {
            throw new MockMvcResponseException(e);
        }
    }

    private MockMvcResponse buildMockMvcResponse(MockHttpServletRequestBuilder requestBuilder, ResultMatcher[] resultMatchers) throws Exception {
        return new MockMvcResponseImpl(
                objectMapper,
                sendRequestAndGetResult(requestBuilder, resultMatchers)
        );
    }

    private ResultActions sendRequestAndGetResult(MockHttpServletRequestBuilder requestBuilder,
                                                  ResultMatcher... resultMatchers) throws Exception {
        ResultActions request =  mockMvc.perform(requestBuilder);

        for (ResultMatcher resultMatcher : resultMatchers) {
            request.andExpect(resultMatcher);
        }

        return request;
    }
}
