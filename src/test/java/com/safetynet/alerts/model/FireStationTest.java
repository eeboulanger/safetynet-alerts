package com.safetynet.alerts.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FireStationTest {

    @Test
    void testConstructorAndFieldAccess() {
        String address = "123 Main Street";
        int station = 1;

        FireStation fireStation = new FireStation(address, station);

        assertEquals(address, fireStation.getAddress());
        assertEquals(station, fireStation.getStation());
    }

    @Test
    void testEquality() {
        FireStation fireStation1 = new FireStation("123 Main Street", 1);
        FireStation fireStation2 = new FireStation("123 Main Street", 1);

        assertEquals(fireStation1, fireStation2);
    }

    @Test
    void testHashCode() {
        FireStation fireStation1 = new FireStation("123 Main Street", 1);
        FireStation fireStation2 = new FireStation("123 Main Street", 1);

        assertEquals(fireStation1.hashCode(), fireStation2.hashCode(), "Hash codes should be equal for equal objects.");
    }
}
