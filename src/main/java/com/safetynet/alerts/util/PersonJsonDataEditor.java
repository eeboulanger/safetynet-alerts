package com.safetynet.alerts.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataContainer;
import com.safetynet.alerts.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class PersonJsonDataEditor implements IJsonDataEditor<Person> {
    private static final Logger logger = LogManager.getLogger(PersonJsonDataEditor.class);
    private static final String JSON_DATA_PATH = "./data/data.json";
    @Autowired
    private final ObjectMapper mapper;
    private final File jsonFile;
    private DataContainer data;
    private List<Person> personList;


    @Autowired
    public PersonJsonDataEditor(ObjectMapper mapper) {
        this.mapper = mapper;
        this.jsonFile = new File(JSON_DATA_PATH);

    }

    private void readData() {
        if (jsonFile.exists()) {
            try {
                data = mapper.readValue(jsonFile, DataContainer.class);
                personList = data.getPersons();
            } catch (IOException e) {
                logger.error("Failed to initialize person editor: " + e);
            }
        }
    }

    @Override
    public boolean create(Person person) {
        readData();
        if (personList != null) {
            //Check person doesn't exist already
            for (Person value : personList) {
                if (value.getFirstName().equals(person.getFirstName())
                        && value.getLastName().equals(person.getLastName())) {
                    logger.info("Failed to create new person. There's already a person with the same name");
                    return false;
                }
            }
            personList.add(person);
            try {
                saveData();
                return true;
            } catch (Exception e) {
                logger.error("Failed to create new person: " + e);
                return false;
            }
        } else {
            logger.debug("Failed to create new person. There's no list of persons");
            return false;
        }
    }

    @Override
    public boolean update(Person person) {
        readData();
        boolean isUpdated = false;
        if (personList != null) {

            logger.info(person.getFirstName());

            logger.info(personList.get(0));
            //check if person exists
            for (int i = 0; i < personList.size(); i++) {
                if (personList.get(i).getFirstName().equals(person.getFirstName())
                        && personList.get(i).getLastName().equals(person.getLastName())) {
                    personList.set(i, person);
                    isUpdated = true;
                    break;
                }
            }
            if (isUpdated) {
                try {
                    saveData();
                } catch (Exception e) {
                    logger.error("Failed to update person: " + e);
                    return false;
                }
            } else {
                logger.info("Failed to update person. There's no person with the given name.");
            }
        } else {
            logger.info("Failed to update person. There's no list of persons.");
        }
        return isUpdated;
    }

    @Override
    public boolean delete(Map<String, String> map) {
        readData();
        boolean isDeleted = false;
        if (personList != null) {
            //check if person exists
            isDeleted = personList.removeIf(p -> p.getFirstName().equals(map.get("firstName"))
                    && p.getLastName().equals(map.get("lastName")));
            if (isDeleted) {
                try {
                    saveData();
                } catch (IOException e) {
                    logger.error("Failed to delete person: " + e);
                    isDeleted = false;
                }
            } else {
                logger.debug("Failed to delete person. There's no person with the given name.");
            }
        } else {
            logger.debug("Failed to delete person. There's no list of persons.");
        }
        return isDeleted;
    }

    private void saveData() throws IOException {
        if (personList != null) {
            mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, data);
        }
    }
}
