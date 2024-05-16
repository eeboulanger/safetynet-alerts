package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.IFireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FireStationService implements IFireStationService {

    @Autowired
    IFireStationRepository repository;

    @Override
    public Optional<List<FireStation>> findByStationNumber(int stationNumber) {
        return repository.findByStationNumber(stationNumber);
    }

    @Override
    public Optional<FireStation> findStationByAddress(String address) {
        return repository.findStationByAddress(address);
    }

    @Override
    public boolean create(FireStation fireStation) {
        return repository.create(fireStation);
    }

    @Override
    public boolean update(FireStation fireStation) {
        return repository.update(fireStation);
    }

    @Override
    public boolean delete(Map<String, String> stationId) {
        return repository.delete(stationId);
    }

    @Override
    public Optional<List<FireStation>> findAll() {
        return repository.findAll();
    }
}
