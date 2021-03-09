package instruments;

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import org.springframework.boot.test.context.TestComponent
import java.io.File

@TestComponent
class ObjectMapperWrapper(val objectMapper: ObjectMapper) {
    fun <T> readValue(file: File?, valueType: Class<T>?): T = suppressChecked {
        objectMapper.readValue(file, valueType)
    }

    fun <T> readValue(content: String?, valueType: Class<T>?): T = suppressChecked {
        objectMapper.readValue(content, valueType)
    }

    fun <T> readValue(content: String?, valueType: TypeReference<T>?): T = suppressChecked {
        objectMapper.readValue(content, valueType)
    }

    fun writeValueAsString(value: Any?): String? = suppressChecked { objectMapper.writeValueAsString(value) }

    fun findAndRegisterModules() =
        objectMapper.findAndRegisterModules()

    fun registerModule(simpleModule: SimpleModule?) =
        objectMapper.registerModule(simpleModule)

    fun registerModules(vararg simpleModule: SimpleModule?) =
        objectMapper.registerModules(*simpleModule)

    fun writeValueAsBytes(`object`: Any?): ByteArray? = suppressChecked {
        objectMapper.writeValueAsBytes(`object`)
    }

    companion object {
        inline fun <T> suppressChecked(f: () -> T) = try {
            f()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}


