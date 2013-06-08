package org.jongo.jersey.provider;

import java.io.IOException;
import java.lang.annotation.Annotation;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.bson.types.ObjectId;

@Provider
public class JacksonMapperProvider implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper;

    public JacksonMapperProvider() {
        mapper = createMapper();
    }

    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }

    private static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.AUTO_DETECT_GETTERS);
        mapper.disable(MapperFeature.AUTO_DETECT_SETTERS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);

        VisibilityChecker<?> checker = mapper.getSerializationConfig().getDefaultVisibilityChecker();
        mapper.setVisibilityChecker(checker.withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        mapper.registerModule(new SimpleModule("jersey") //
                        .addSerializer(_id, _idSerializer()) //
                        .addDeserializer(_id, _idDeserializer()));

        mapper.setAnnotationIntrospector(new ObjectIdIntrospector());
        return mapper;
    }

    private static Class<ObjectId> _id = ObjectId.class;

    private static JsonDeserializer<ObjectId> _idDeserializer() {
        return new JsonDeserializer<ObjectId>() {
            @Override
            public ObjectId deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                return new ObjectId(jsonParser.readValueAs(String.class));
            }
        };
    }

    private static JsonSerializer<Object> _idSerializer() {
        return new JsonSerializer<Object>() {
            @Override
            public void serialize(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                jsonGenerator.writeString(obj == null ? null : obj.toString());
            }
        };
    }

    private static class ObjectIdIntrospector extends JacksonAnnotationIntrospector {
        @Override
        public boolean isAnnotationBundle(Annotation ann) {
            if(ann.annotationType().equals(org.jongo.marshall.jackson.oid.ObjectId.class)) {
                return false;
            }
            return super.isAnnotationBundle(ann);
        }
    }
}
