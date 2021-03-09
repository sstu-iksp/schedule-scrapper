package instruments.helper.interfaces;

import com.fasterxml.jackson.core.type.TypeReference;

public interface MockMvcResponse {
    String readBodyAsString();

    <T> T readBodyAsJsonByType(Class<T> type);
}
