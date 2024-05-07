package com.safetynet.alerts.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.util.IJsonDataReader;
import com.safetynet.alerts.util.JsonDataReaderFromFile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FireStationRepository implements DataRepository<FireStation> {

    IJsonDataReader reader = new JsonDataReaderFromFile();

    @Override
    public Optional<List<FireStation>> findAll() {
        return reader.findAll("firestations", new TypeReference<>() {
        });
    }

    @Override
    public boolean delete(FireStation entity) {
        return false;
    }

    @Override
    public boolean create(FireStation entity) {
        return false;
    }

    @Override
    public boolean update(FireStation entity) {
        return false;
    }

    public Optional<List<FireStation>> findByStationNumber(int number) {

        Optional<List<FireStation>> optionalList = this.findAll();
        return optionalList.map(fireStations -> fireStations.stream()
                .filter(f -> f.getStation() == number)
                .collect(Collectors.toList()));
    }

    public Optional<FireStation> findStationByAddress(String address) {
        return this.findAll()
                .flatMap(fireStations -> fireStations.stream()
                        .filter(fireStation -> fireStation.getAddress().equals(address))
                        .findFirst()
                );
    }
}
