
package com.pmotracker.msproject.infrastructure.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConversionUtilsTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ConversionUtils conversionUtils;

    private static class Entity {
        private String data;
        public String getData() { return data; }
        public void setData(String data) { this.data = data; }
    }

    private static class Dto {
        private String data;
        public String getData() { return data; }
        public void setData(String data) { this.data = data; }
    }
    
    private Entity entity;
    private Dto dto;

    @BeforeEach
    void setUp() {
        entity = new Entity();
        entity.setData("entityData");

        dto = new Dto();
        dto.setData("dtoData");
    }

    @Test
    void testMapListOfEntityToListOfDto() {
        when(modelMapper.map(any(Entity.class), eq(Dto.class))).thenReturn(dto);
        List<Dto> dtoList = conversionUtils.mapListOfEntityToListOfDto(Collections.singletonList(entity), Dto.class);
        assertEquals(1, dtoList.size());
        assertEquals("dtoData", dtoList.get(0).getData());
        verify(modelMapper).map(entity, Dto.class);
    }

    @Test
    void testMapDtoToEntity() {
        when(modelMapper.map(dto, Entity.class)).thenReturn(entity);
        Entity result = conversionUtils.mapDtoToEntity(dto, Entity.class);
        assertEquals("entityData", result.getData());
        verify(modelMapper).map(dto, Entity.class);
    }

    @Test
    void testMapEntityToDto() {
        when(modelMapper.map(entity, Dto.class)).thenReturn(dto);
        Dto result = conversionUtils.mapEntityToDto(entity, Dto.class);
        assertEquals("dtoData", result.getData());
        verify(modelMapper).map(entity, Dto.class);
    }

    @Test
    void testConvertFromObject() {
        when(modelMapper.map(entity, Dto.class)).thenReturn(dto);
        Dto result = conversionUtils.convertFromObject(entity, Dto.class);
        assertEquals("dtoData", result.getData());
        verify(modelMapper).map(entity, Dto.class);
    }

    @Test
    void testGetJSONFromObject() throws JsonProcessingException {
        String jsonString = "{\"data\":\"dtoData\"}";
        when(objectMapper.writeValueAsString(dto)).thenReturn(jsonString);
        String result = conversionUtils.getJSONFromObject(dto);
        assertEquals(jsonString, result);
        verify(objectMapper).writeValueAsString(dto);
    }

    @Test
    void testGetObjectFromJson() throws IOException {
        String jsonString = "{\"data\":\"dtoData\"}";
        when(objectMapper.readValue(jsonString, Dto.class)).thenReturn(dto);
        Dto result = conversionUtils.getObjectFromJson(jsonString, Dto.class);
        assertEquals("dtoData", result.getData());
        verify(objectMapper).readValue(jsonString, Dto.class);
    }
}
