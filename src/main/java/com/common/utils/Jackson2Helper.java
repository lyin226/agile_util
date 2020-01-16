package com.common.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * @author liuyi
 * @since 2020/1/15
 */
public class Jackson2Helper {



	private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 对象转为json
     * @param obj
     * @return
     */
	public static final String toJsonString(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new GenericBusinessException(e);
		}
	}

    /**
     * json字符串转为对象
     * @param jsonString
     * @param cls
     * @param <T>
     * @return
     * @throws GenericBusinessException
     */
	public static final <T> T parsingObject(String jsonString, Class<T> cls) throws GenericBusinessException {
		try {
		    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
			return mapper.readValue(jsonString, cls);
		} catch (JsonParseException e) {
			throw new GenericBusinessException(e);
		} catch (JsonMappingException e) {
			throw new GenericBusinessException(e);
		} catch (IOException e) {
			throw new GenericBusinessException(e);
		}
	}

    /**
     * json字符串转为jsonNode
     * @param jsonString
     * @return
     * @throws GenericBusinessException
     */
	public static final JsonNode parsingObject(String jsonString) throws GenericBusinessException {
		if (StringUtils.isBlank(jsonString)) {
			return null;
		}
		try {
		    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
			return mapper.readTree(jsonString);
		} catch (JsonParseException e) {
			throw new GenericBusinessException(e);
		} catch (JsonMappingException e) {
			throw new GenericBusinessException(e);
		} catch (IOException e) {
			throw new GenericBusinessException(e);
		}
	}

    /**
     * 支持泛型
     * @param jsonString
     * @param valueTypeRef
     * @param <T>
     * @return
     */
	public static final <T> T parsingObject(String jsonString, TypeReference<T> valueTypeRef) {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        }
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
            return mapper.readValue(jsonString, valueTypeRef);
        } catch (IOException e) {
            throw new GenericBusinessException(e);
        }
    }
}
