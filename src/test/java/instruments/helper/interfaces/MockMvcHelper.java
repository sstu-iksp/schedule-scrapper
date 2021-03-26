package instruments.helper.interfaces;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public interface MockMvcHelper {
    static ResultMatcher[] expectJsonAndOkStatus() {
        return new ResultMatcher[] {status().isOk(), content().contentType(MediaType.APPLICATION_JSON)};
    }

    MockMvcResponse sendRequest(MockHttpServletRequestBuilder requestBuilder,
                                ResultMatcher... resultMatchers);

}