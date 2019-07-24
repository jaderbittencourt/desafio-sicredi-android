package com.jaderbittencourt.sicredidigital.util;

import com.jaderbittencourt.sicredidigital.model.CheckInBody;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommonJsonParserTest {

    @Test
    public void shouldReturnNullForInvalidInput() {
        assertEquals("null", CommonJsonParser.objectToJson(null));
    }

    @Test
    public void shouldReturnEmptyStringForEmptyString() {
        assertEquals("\"\"", CommonJsonParser.objectToJson(""));
    }

    @Test
    public void shouldConvertObjectToAValidJson() {
        CheckInBody obj = new CheckInBody();
        obj.setEventId("1");
        obj.setName("John");
        obj.setEmail("john@email");
        String expectedJson = "{\"eventId\":\"1\",\"name\":\"John\",\"email\":\"john@email\"}";

        assertEquals(expectedJson, CommonJsonParser.objectToJson(obj));
    }

    @Test
    public void shouldConvertJtonToAValidObject() {
        CheckInBody expectedObject = new CheckInBody();
        expectedObject.setEventId("1");
        expectedObject.setName("John");
        expectedObject.setEmail("john@email");

        String json = "{\"eventId\":\"1\",\"name\":\"John\",\"email\":\"john@email\"}";
        CheckInBody parsedObject = CommonJsonParser.jsonToObject(json, CheckInBody.class);

        assertTrue(expectedObject.getName().equals(parsedObject.getName()));
        assertTrue(expectedObject.getEmail().equals(parsedObject.getEmail()));
        assertTrue(expectedObject.getEventId().equals(parsedObject.getEventId()));
    }
}
