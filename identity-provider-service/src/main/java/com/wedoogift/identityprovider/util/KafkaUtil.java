package com.wedoogift.identityprovider.util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wedoogift.identityprovider.exception.custom.WedoogiftInvalidMessageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaUtil {

    @Autowired
    private  ObjectMapper objectMapper;


    public  Map<String, Object> readJsonAsMap(final String json) {
        try{
            final TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};
            return objectMapper.readValue(json, typeRef);
        } catch(JsonProcessingException ex) {
            throw new WedoogiftInvalidMessageException("exception.invalid.message",json);
        }
    }



}
