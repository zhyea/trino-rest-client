package org.chobit.trino;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.chobit.commons.json.LocalDateTimeModule;
import org.chobit.commons.utils.JsonKit;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.reflect.Type;

import static java.util.Objects.requireNonNull;

/**
 * json序列化反序列化处理
 *
 * @author zhangrui
 * @since 2025/3/25
 */
public class JsonCodec<T> {



    public static <T> JsonCodec<T> jsonCodec(Class<T> type) {
        ObjectMapper mapper = JsonKit.mapper();
        mapper.registerModule(new LocalDateTimeModule());
        return new JsonCodec<>(mapper, type);
    }

    private final ObjectMapper mapper;
    private final Type type;
    private final JavaType javaType;

    private JsonCodec(ObjectMapper mapper, Type type) {
        this.mapper = requireNonNull(mapper, "mapper is null");
        this.type = requireNonNull(type, "type is null");
        this.javaType = mapper.getTypeFactory().constructType(type);
    }

    public Type getType() {
        return type;
    }

    public T fromJson(String json)
            throws JsonProcessingException {
        try (JsonParser parser = mapper.createParser(json)) {
            T value = mapper.readerFor(javaType).readValue(parser);
            if (null != parser.nextToken()) {
                throw new IllegalArgumentException("Found characters after the expected end of input");
            }
            return value;
        } catch (JsonProcessingException e) {
            throw e;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public T fromJson(InputStream inputStream)
            throws IOException, JsonProcessingException {
        try (JsonParser parser = mapper.createParser(inputStream)) {
            T value = mapper.readerFor(javaType).readValue(parser);
            if (null != parser.nextToken()) {
                throw new IllegalArgumentException("Found characters after the expected end of input");
            }
            return value;
        }
    }
}
