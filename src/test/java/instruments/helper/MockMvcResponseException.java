package instruments.helper;

import java.io.UnsupportedEncodingException;

public class MockMvcResponseException extends RuntimeException {
    public MockMvcResponseException(String message, UnsupportedEncodingException e) {
        super(message, e);
    }

    public MockMvcResponseException(Exception e) {
        super(e);
    }
}
