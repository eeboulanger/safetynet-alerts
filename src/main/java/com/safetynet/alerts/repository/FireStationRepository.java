package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.FireStation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Primary
public class FireStationRepository implements IFireStationRepository {

    private List<FireStation> fireStationList;
    private static final Logger logger = LogManager.getLogger(FireStationRepository.class);

    public boolean saveAll(List<FireStation> fireStationList) {
        this.fireStationList = fireStationList;
        return fireStationList != null;
    }

    @Override
    public boolean create(FireStation fireStation) {
        boolean isCreated = false;
        if (fireStationList != null) {
            for (FireStation f : fireStationList) {
                if (f.getAddress().equals(fireStation.getAddress())
                        && f.getStation() == fireStation.getStation()) {
                    logger.error("Failed to create new fire station. " +
                            "There's already a fire station wih the same address and station number.");
                    return false;
                }
            }
            fireStationList.add(fireStation);
            isCreated = true;
        } else {
            logger.error("Failed to create new fire station. There's no fire station list.");
        }
        return isCreated;
    }

    @Override
    public boolean update(FireStation station) {
        boolean isUpdated = false;
        if (fireStationList != null) {
            for (int i = 0; i < fireStationList.size(); i++) {
                if (station.getAddress().equals(fireStationList.get(i).getAddress())) {
                    fireStationList.set(i, station);
                    isUpdated = true;
                    break;
                }
            }
            if (!isUpdated) {
                logger.error("Failed to update fire station. " +
                        "There's no fire station covering the given address");
            }
        } else {
            logger.error("Failed to update fire station. There's no list of fire stations.");
        }
        return isUpdated;
    }

    @Override
    public boolean delete(Map<String, String> identifier) {
        boolean isDeleted = false;
        if (fireStationList != null) {
            isDeleted = fireStationList.removeIf(s -> String.valueOf(s.getStation()).equals(identifier.get("station"))
                    && s.getAddress().equals(identifier.get("address")));

            if (!isDeleted) {
                logger.error("Failed to delete fire station. " +
                        "There's no fire station with the given address and station number.");
            }
        } else {
            logger.error("Failed to delete fire station. There is no list of fire stations.");
        }
        return isDeleted;
    }

    @Override
    public Optional<List<FireStation>> findAll() {
        if (fireStationList == null) {
            logger.error("There's no list of firestations");
            return Optional.empty();
        }
        return Optional.of(fireStationList);
    }

    @Override
    public Optional<List<FireStation>> findByStationNumber(int number) {
        if (fireStationList == null) {
            logger.error("There is no list of fire stations.");
            return Optional.empty();
        }
        List<FireStation> list = fireStationList.stream()
                .filter(f -> f.getStation() == number)
                .collect(Collectors.toList());
        return list.isEmpty() ? Optional.empty() : Optional.of(list);
    }

    @Override
    public Optional<FireStation> findStationByAddress(String address) {
        if (fireStationList == null) {
            logger.error("There is no list of fire stations.");
            return Optional.empty();
        }
        return fireStationList.stream()
                .filter(fireStation -> fireStation.getAddress().equals(address))
                .findFirst();
    }
}
