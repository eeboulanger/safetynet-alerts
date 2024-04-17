package com.safetynet.alerts.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FireStationTest {

    @Test
    public void getFireStationInfoTest(){
        FireStation fireStation = new FireStation("123 Street", 1);

        assertEquals("123 Street", fireStation.getAddress());
        assertEquals(1, fireStation.getStation());
    }
}
