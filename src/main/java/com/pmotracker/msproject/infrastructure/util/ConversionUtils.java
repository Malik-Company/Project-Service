/*
 * ©2020 Nisum Technologies, Inc. All Rights Reserved.
 */
package com.pmotracker.msproject.infrastructure.util;

/*
 *  Created by IntelliJ IDEA
 *  User: Malik Imran (msabir@nisum.com)
 *  Date: 3/20/2020
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ConversionUtils {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * <p>Note: outClass object must have default constructor with no arguments</p>
     *
     * @param entityList list of entities that needs to be mapped
     * @param outCLass   class of result list element
     * @param <D>        type of objects in result list
     * @param <T>        type of entity in <code>entityList</code>
     * @return list of mapped object with <code><D></code> type.
     */
    public <D, T> List<D> mapListOfEntityToListOfDto(Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> mapEntityToDto(entity, outCLass))
                .collect(Collectors.toList());
    }

    /**
     * This method is used to convert DTO to Entity.
     *
     * @param dtoObject    source Object which will be convert into Entity.
     * @param entityObject target Object.
     * @param <T>
     * @return Entity Object.
     */
    public <T> T mapDtoToEntity(Object dtoObject, Class<T> entityObject) {
        return modelMapper.map(dtoObject, entityObject);
    }

    /**
     * This method is used to convert Entity To DTO.
     *
     * @param entityObject source Object which will be convert into DTO.
     * @param dtoObject    target object.
     * @param <T>
     * @return DTO Object.
     */
    public <T> T mapEntityToDto(Object entityObject, Class<T> dtoObject) {
        return modelMapper.map(entityObject, dtoObject);
    }

    /**
     * This method is used to convert object.class To its respective class
     */
    public <T> T convertFromObject(Object obj, Class<T> ObjectType) {
        return modelMapper.map(obj, ObjectType);
    }

    /**
     * This method is used to Serialize Object into JSON.
     *
     * @param requestObject request Object.
     * @return T
     */
    public String getJSONFromObject(Object requestObject) {
        String newJson = null;
        try {
            newJson = objectMapper.writeValueAsString(requestObject);
        } catch (JsonProcessingException e) {
            log.error("Error occurred" + String.format("%s ", e.getMessage()));
        }
        return newJson;
    }

    public void mapSourceModelToDestinationModel(Object source, Object destination) {
        modelMapper.map(source, destination);
    }

    public <T> T convertStringToObject(String str, Class<T> objectType) {

        try {
            return objectMapper.readValue(str, objectType);
        } catch (Exception e) {
            log.error("Error occurred" + String.format("%s ", e.getMessage()), e);
        }
        return null;
    }
    public String convertListToString(List<Object> value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            log.error("Exception while converting name dto to string", e);
        }

        return StringUtils.EMPTY;
    }

    public List<Object> convertStringToObjectList(String value) {
        try {
            return objectMapper.readValue(value, new TypeReference<List<Object>>(){});
        } catch (JsonProcessingException e) {
            log.error("Exception while converting string to name dto", e);
        } catch (IOException e) {
            log.error("Exception while converting string to name dto", e);
        } catch (Exception e) {
            log.error("Exception while converting string to name dto", e);
        }

        return new ArrayList<>();
    }

    public List<String> convertStringToStringList(String value) {
        try {
            return objectMapper.readValue(value, new TypeReference<List<String>>(){});
        } catch (JsonProcessingException e) {
            log.error("Exception while converting string to name dto", e);
        } catch (IOException e) {
            log.error("Exception while converting string to name dto", e);
        }

        return new ArrayList<>();
    }
    public String convertStringListToString(List<String> value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            log.error("Exception while converting name dto to string", e);
        }

        return StringUtils.EMPTY;
    }

}
