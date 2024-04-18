package com.safetynet.alerts.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.util.JsonDataReader;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FireStationRepository implements DataRepository<FireStation> {
    @Override
    public Optional<List<FireStation>> findAll() {
        return JsonDataReader.findAll("firestations", new TypeReference<>() {
        });
    }

    @Override
    public void delete(FireStation entity) {

    }

    @Override
    public void create(FireStation entity) {

    }

    @Override
    public void update(FireStation entity) {

    }

    public Optional<List<FireStation>> findByStationNumber(int number) {

        Optional<List<FireStation>> optionalList = this.findAll();
        return optionalList.map(fireStations -> fireStations.stream()
                .filter(f -> f.getStation() == number)
                .collect(Collectors.toList()));
    }
}
