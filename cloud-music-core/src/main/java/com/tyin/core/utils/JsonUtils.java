package com.tyin.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Tyin
 * @date 2022/3/26 2:42
 * @description ...
 */
@Slf4j
public class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /*
      设置一些通用的属性
     */
    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 允许key没有双引号
        MAPPER.configure(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES.mappedFeature(), true);
        // 允许key有单引号
        MAPPER.configure(JsonReadFeature.ALLOW_SINGLE_QUOTES.mappedFeature(), true);
        // 允许整数以0开头
        MAPPER.configure(JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS.mappedFeature(), true);
        // 允许字符串中存在回车换行控制符
        MAPPER.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private static ObjectMapper getMapper() {
        return MAPPER;
    }

    public static ObjectNode getObjectNode() {
        return MAPPER.createObjectNode();
    }

    public static ObjectNode getObjectNode(Object obj) {
        return MAPPER.valueToTree(obj);
    }

    public static String toJSONString(ObjectNode node) throws JsonProcessingException {
        return MAPPER.writeValueAsString(node);
    }

    public static JsonNode toJsonNode(String json) throws JsonProcessingException {
        return MAPPER.readTree(json);
    }

    public static ObjectNode toObjectNode(String json) throws JsonProcessingException {
        return (ObjectNode) MAPPER.readTree(json);
    }

    public static String toJSONString(Object obj) {
        return obj != null ? toJSONString(obj, () -> "", false) : "";
    }

    public static String toFormatJSONString(Object obj) {
        return obj != null ? toJSONString(obj, () -> "", true) : "";
    }

    public static String toJSONString(Object obj, Supplier<String> defaultSupplier, boolean format) {
        try {
            if (obj == null) {
                return defaultSupplier.get();
            }
            if (obj instanceof String) {
                return obj.toString();
            }
            if (obj instanceof Number) {
                return obj.toString();
            }
            if (format) {
                return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            }
            return MAPPER.writeValueAsString(obj);
        } catch (Throwable e) {
            log.error(String.format("toJSONString %s", obj != null ? obj.toString() : "null"), e);
        }
        return defaultSupplier.get();
    }

    public static <T> T toJavaObject(String value, Class<T> tClass) {
        return StringUtils.isNotBlank(value) ? toJavaObject(value, tClass, () -> null) : null;
    }

    public static <T> T toJavaObject(Object obj, Class<T> tClass) {
        return obj != null ? toJavaObject(toJSONString(obj), tClass, () -> null) : null;
    }

    public static <T> T toJavaObject(String value, Class<T> tClass, Supplier<T> defaultSupplier) {
        try {
            if (StringUtils.isBlank(value)) {
                return defaultSupplier.get();
            }
            return MAPPER.readValue(value, tClass);
        } catch (Throwable e) {
//            log.error(String.format("toJavaObject exception: \n %s\n %s", value, tClass), e);
        }
        return defaultSupplier.get();
    }

    public static <T> List<T> toJavaObjectList(String value, Class<T> tClass) {
        return StringUtils.isNotBlank(value) ? toJavaObjectList(value, tClass, () -> null) : null;
    }

    public static <T> List<T> toJavaObjectList(Object obj, Class<T> tClass) {
        return obj != null ? toJavaObjectList(toJSONString(obj), tClass, () -> null) : null;
    }

    public static <T> List<T> toJavaObjectList(String value, Class<T> tClass, Supplier<List<T>> defaultSupplier) {
        try {
            if (StringUtils.isBlank(value)) {
                return defaultSupplier.get();
            }
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, tClass);
            return MAPPER.readValue(value, javaType);
        } catch (Throwable e) {
//            log.error(String.format("toJavaObjectList exception \n%s\n%s", value, tClass), e);
        }
        return defaultSupplier.get();
    }

    // 简单地直接用json复制或者转换(Cloneable)
    public static <T> T jsonCopy(Object obj, Class<T> tClass) {
        return obj != null ? toJavaObject(toJSONString(obj), tClass) : null;
    }

    public static Map<String, Object> toMap(String value) {
        return StringUtils.isNotBlank(value) ? toMap(value, () -> null) : null;
    }

    public static Map<String, Object> toMap(Object value) {
        return value != null ? toMap(value, () -> null) : null;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(Object value, Supplier<Map<String, Object>> defaultSupplier) {
        if (value == null) {
            return defaultSupplier.get();
        }
        try {
            if (value instanceof Map) {
                return (Map<String, Object>) value;
            }
        } catch (Exception e) {
//            log.info("fail to convert" + toJSONString(value), e);
        }
        return toMap(toJSONString(value), defaultSupplier);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(String value, Supplier<Map<String, Object>> defaultSupplier) {
        if (StringUtils.isBlank(value)) {
            return defaultSupplier.get();
        }
        try {
            return toJavaObject(value, HashMap.class);
        } catch (Exception e) {
//            log.error(String.format("toMap exception\n%s", value), e);
        }
        return defaultSupplier.get();
    }

    public static List<Object> toList(String value) {
        return StringUtils.isNotBlank(value) ? toList(value, () -> null) : null;
    }

    public static List<Object> toList(Object value) {
        return value != null ? toList(value, () -> null) : null;
    }

    @SuppressWarnings("unchecked")
    public static List<Object> toList(String value, Supplier<List<Object>> defaultSuppler) {
        if (StringUtils.isBlank(value)) {
            return defaultSuppler.get();
        }
        try {
            return toJavaObject(value, List.class);
        } catch (Exception e) {
            log.error("toList exception\n" + value, e);
        }
        return defaultSuppler.get();
    }

    @SuppressWarnings("unchecked")
    public static List<Object> toList(Object value, Supplier<List<Object>> defaultSuppler) {
        if (value == null) {
            return defaultSuppler.get();
        }
        if (value instanceof List) {
            return (List<Object>) value;
        }
        return toList(toJSONString(value), defaultSuppler);
    }
}
