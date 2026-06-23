
package com.pmotracker.msproject.infrastructure.util;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ObjectMapperUtilTest {

    private static class TestObject {
        private String name;
        private int value;

        public TestObject() {}

        public TestObject(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    @Test
    void testToJSONObject() throws Exception {
        String jsonString = "{\"name\":\"test\", \"value\":123}";
        JSONObject jsonObject = ObjectMapperUtil.toJSONObject(jsonString);
        assertNotNull(jsonObject);
        assertEquals("test", jsonObject.getString("name"));
        assertEquals(123, jsonObject.getInt("value"));
    }

    @Test
    void testToJSONObject_invalidJson() {
        String jsonString = "{\"name\":\"test\", \"value\":123";
        assertThrows(RuntimeException.class, () -> ObjectMapperUtil.toJSONObject(jsonString));
    }

    @Test
    void testToMap() {
        TestObject obj = new TestObject("test", 123);
        Map<String, Object> map = ObjectMapperUtil.toMap(obj);
        assertNotNull(map);
        assertEquals("test", map.get("name"));
        assertEquals(123, map.get("value"));
    }
    
    @Test
    void testToMapFromString() {
        String jsonString = "{\"name\":\"test\", \"value\":123}";
        Map<String, Object> map = ObjectMapperUtil.toMap(jsonString);
        assertNotNull(map);
        assertEquals("test", map.get("name"));
        assertEquals(123, ((Number)map.get("value")).intValue());
    }

    @Test
    void testToJSONString() {
        TestObject obj = new TestObject("test", 123);
        String jsonString = ObjectMapperUtil.toJSONString(obj);
        assertNotNull(jsonString);
        assertTrue(jsonString.contains("\"name\":\"test\""));
        assertTrue(jsonString.contains("\"value\":123"));
    }

    @Test
    void testToObject() {
        String jsonString = "{\"name\":\"test\", \"value\":123}";
        TestObject obj = ObjectMapperUtil.toObject(jsonString, TestObject.class, true);
        assertNotNull(obj);
        assertEquals("test", obj.getName());
        assertEquals(123, obj.getValue());
    }
}
