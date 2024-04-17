package com.safetynet.alerts.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonTest {

    private static Person person;
    @BeforeAll
    public static void setUp(){
         person = new Person("Paul", "Bloom", "123 Street", "NoWhereCity", 12345, "111-222-3344", "my@gmail.com");
    }
    @Test
    public void getPersonInfoTest(){

        assertEquals("Paul", person.getFirstName());
        assertEquals("Bloom", person.getLastName());
        assertEquals("123 Street", person.getAddress());
        assertEquals("NoWhereCity", person.getCity());
        assertEquals(12345, person.getZip());
        assertEquals("111-222-3344", person.getPhone());
        assertEquals("my@gmail.com", person.getEmail());
    }

}
