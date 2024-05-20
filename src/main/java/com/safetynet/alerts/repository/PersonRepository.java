package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PersonRepository implements IPersonRepository {
    private List<Person> personList;
    private static final Logger logger = LogManager.getLogger(PersonRepository.class);

    public boolean saveAll(List<Person> personList) {
        this.personList = personList;
        return personList != null;
    }

    @Override
    public boolean create(Person person) {
        boolean isCreated = false;
        if (personList != null) {
            //Check person doesn't exist already
            for (Person value : personList) {
                if (value.getFirstName().equals(person.getFirstName())
                        && value.getLastName().equals(person.getLastName())) {
                    logger.error("Failed to create new person. There's already a person with the same name");
                    return false;
                }
            }
            personList.add(person);
            isCreated = true;
        } else {
            logger.error("Failed to create new person. There's no list of persons");
        }
        return isCreated;
    }

    @Override
    public boolean update(Person person) {
        boolean isUpdated = false;
        if (personList != null) {
            //check if person exists
            for (int i = 0; i < personList.size(); i++) {
                if (personList.get(i).getFirstName().equals(person.getFirstName())
                        && personList.get(i).getLastName().equals(person.getLastName())) {
                    personList.set(i, person);
                    isUpdated = true;
                    break;
                }
            }
            if (!isUpdated) {
                logger.error("Failed to update person. There's no person with the given name.");
            }
        } else {
            logger.error("Failed to update person. There's no list of persons.");
        }
        return isUpdated;
    }

    @Override
    public boolean delete(Map<String, String> map) {
        boolean isDeleted = false;
        if (personList != null) {
            //check if person exists
            isDeleted = personList.removeIf(p -> p.getFirstName().equals(map.get("firstName"))
                    && p.getLastName().equals(map.get("lastName")));
            if (!isDeleted) {
                logger.error("Failed to delete person. There's no person with the given name.");
            }
        } else {
            logger.error("Failed to delete person. There's no list of persons.");
        }
        return isDeleted;
    }

    @Override
    public Optional<List<Person>> findAll() {
        if (personList == null) {
            logger.error("There's no list of persons");
            return Optional.empty();
        }
        return Optional.of(personList);
    }

    @Override
    public Optional<List<Person>> findByAddress(String address) {
        if (personList == null) {
            logger.error("There's no list of persons");
            return Optional.empty();
        }
        List<Person> list = personList.stream()
                .filter(f -> f.getAddress().equals(address))
                .collect(Collectors.toList());
        return list.isEmpty() ? Optional.empty() : Optional.of(list);
    }

    @Override
    public Optional<List<Person>> findByName(String firstName, String lastName) {
        List<Person> list = personList.stream()
                .filter(person -> person.getFirstName().equals(firstName)
                        && person.getLastName().equals(lastName))
                .collect(Collectors.toList());
        return list.isEmpty() ? Optional.empty() : Optional.of(list);
    }
}
